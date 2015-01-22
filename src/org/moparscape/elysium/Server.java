package org.moparscape.elysium;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.UnregistrableSession;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.ElysiumChannelInitializer;
import org.moparscape.elysium.task.IssueUpdatePacketsTask;
import org.moparscape.elysium.task.timed.TimedTask;
import org.moparscape.elysium.util.Config;
import org.moparscape.elysium.world.World;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public class Server {

    private static final Config CONFIG = new Config();
    private static final Server INSTANCE = new Server();
    private static final int PULSE_RUN_TIME_MILLISECONDS = 600;
    private final EventLoopGroup bossGroup;
    private final ExecutorService dataExecutorService;
    private final Set<Session> sessions;
    private final PriorityQueue<UnregistrableSession> sessionsToUnregister;
    /**
     * We used to use a PriorityBlockingQueue here. But upon inspecting the source code
     * I've learned that it acquires and release a lock EVERY TIME you peek/poll.
     * We're better off just synchronizing externally which is what's done now.
     */
    private final PriorityQueue<TimedTask> taskQueue;
    private final IssueUpdatePacketsTask updatePacketBuilder;
    private final EventLoopGroup workerGroup;
    /**
     * An epoch timestamp (set using System.currentTimeMillis()) that can be used
     * for storing and comparing database timestamps.
     */
    private long epochTimestamp;
    /**
     * A higher resolution (on most systems) timer that is used to accurately detect
     * when an update should be performed.
     */
    private long highResolutionTimestamp;
    private long lastPositionUpdate = 0L;
    private long lastPulse = 0L;
    private boolean running = true;
    private Set<Session> sessionsToRegister;
    private GameStateUpdater updater;

    private Server() {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + cores);

        this.epochTimestamp = System.currentTimeMillis();
        this.highResolutionTimestamp = getNanoTimeAsMilliseconds();

        this.bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        this.workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        this.dataExecutorService = Executors.newSingleThreadExecutor();
        this.sessions = new HashSet<>(1500);

        this.sessionsToRegister = new HashSet<>(10);
        this.sessionsToUnregister = new PriorityQueue<>();
        this.taskQueue = new PriorityQueue<>();
        this.updatePacketBuilder = new IssueUpdatePacketsTask(sessions);
    }

    public static Server getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        World.getInstance().seedWithEntities();
        Server server = Server.getInstance();
        server.setGameStateUpdater(new GameStateUpdater());
        ChannelFuture cf = server.listen();

        System.out.println("Server has started. :)");

        // Enter the game loop, and stay there until shutdown
        server.gameLoop();

        System.out.println("Shutdown complete.");
    }

    private void flushSessions() {
        for (Session s : sessions) {
            s.writeAndFlush();
        }
    }

    private void gameLoop() {
        System.out.println("Game loop started");
        long tickCount = 0;

        ParentLoop:
        while (true) {
            try {
                while (running) {
                    // Update the cached highResolutionTimestamp, and see if more than 600ms have passed
                    // If 600ms have passed then do another update; otherwise, sleep and try again
                    updateTimestamps();
                    long stampDiff = highResolutionTimestamp - lastPulse;
                    if (stampDiff < PULSE_RUN_TIME_MILLISECONDS) {
                        try {
                            Thread.sleep(1); // Surrender this time slice
                        } catch (InterruptedException e) {
                            System.err.println("Error occurred while sleeping between game pulses");
                        }

                        continue;
                    }

                    registerNewSessions();
                    pulseSessions();
                    unregisterOldSessions();

                    processTasks(getRemainingPulseTime() / 2);

                    // NOTE: This would need more work if we were to make the game pulse
                    // more frequent. Maybe I'll do it in the future.
                    long positionUpdateDiff = highResolutionTimestamp - lastPositionUpdate;
                    boolean positionUpdateRequired = positionUpdateDiff >= 600;
                    if (positionUpdateRequired) {
                        lastPositionUpdate = highResolutionTimestamp;
                    } else {

                    }

                    updater.updateState();
                    updatePacketBuilder.prepareUpdatePackets();
                    flushSessions();
                    updater.updateCollections();

                    // Update the time that the last pulse took place before finishing
                    lastPulse = highResolutionTimestamp;
                    tickCount++;

//                    long elapsed = getNanoTimeAsMilliseconds() - highResolutionTimestamp;
//                    System.out.printf("Tick = %d, Pulse time = %dms\n", tickCount, elapsed);
                }

                // If we reach this point then shutdown has been triggered.
                // Break out of the parent loop so that the application can cleanup and shut down
                break ParentLoop;
            } catch (Exception e) {
                System.out.println("Game loop exception: " + e.getCause());
                e.printStackTrace();
            }
        }

        // TODO: Implement shutdown procedure and cleanup here
        try {
            System.out.println("Game loop stopped. Shutting down.");

            // Block on the shutdown until it's complete for each one.
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        } catch (Exception e) {
            throw new IllegalStateException("Graceful shutdown failed.");
        }
    }

    public long getEpochTimestamp() {
        return epochTimestamp;
    }

    public long getHighResolutionTimestamp() {
        return highResolutionTimestamp;
    }

    private long getNanoTimeAsMilliseconds() {
        return System.nanoTime() / 1000000;
    }

    private int getRemainingPulseTime() {
        int elapsed = (int) (getNanoTimeAsMilliseconds() - highResolutionTimestamp);
        return PULSE_RUN_TIME_MILLISECONDS - elapsed;
    }

    private ChannelFuture listen() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ElysiumChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            // Bind and start to accept incoming connections.
            return bootstrap.bind(new InetSocketAddress(43594));
        } catch (Exception e) {
            throw new IllegalStateException("netty down");
        }
    }

    private void prepareUpdatePackets() throws Exception {
        IssueUpdatePacketsTask task = new IssueUpdatePacketsTask(sessions);
        task.prepareUpdatePackets();
    }

    private void processTasks(int timeout) throws Exception {
        long time = getHighResolutionTimestamp();
        TimedTask task = null;

        // We use a separate requeue list to ensure that each task
        // only runs once per pulse. This protects against any
        // timed tasks that forget to update the next execution time.
        List<TimedTask> requeueList = new ArrayList<>(taskQueue.size());

        int processedCount = 0;
        while ((task = taskQueue.peek()) != null && task.getExecutionTime() <= time) {
            // If we've processed a batch of 100 tasks then it's time to make sure
            // we haven't breached the provided timeout.
            // If the difference between the current time and the pulse start time
            // is greater than the specified timeout then we have breached, which
            // means it's time to finish processing tasks.
            if (processedCount % 100 == 0) {
                long currentTime = getNanoTimeAsMilliseconds();
                if (currentTime - highResolutionTimestamp >= timeout) break;
            }

            // This task is due to prepareUpdatePackets -- remove from the priority queue
            // and add it to the list of tasks to execute
            task = taskQueue.poll();
            try {
                task.run();
            } catch (Exception e) {
                // TODO: Handle errors here somehow.
            }

            if (task.shouldRepeat()) {
                requeueList.add(task);
            }
        }

        for (TimedTask t : requeueList) {
            taskQueue.offer(t);
        }
    }

    private void pulseSessions() throws Exception {
        for (Session s : sessions) {
            s.pulse();
        }
    }

    /**
     * Requires synchronization as this method is called by netty threads.
     *
     * @param session
     */
    public void queueRegisterSession(Session session) {
        if (session == null) return;

        synchronized (sessionsToRegister) {
            sessionsToRegister.add(session);
        }
    }

    /**
     * Requires synchronization as this method is called by netty threads.
     *
     * @param us
     */
    public void queueUnregisterSession(UnregistrableSession us) {
        if (us == null) return;

        Session s = us.getSession();
        if (s.isRemoving()) return;

        synchronized (sessionsToUnregister) {
            if (s.isRemoving()) return;

            s.setRemoving(true);
            sessionsToUnregister.add(us);
        }
    }

    /**
     * Requires synchronization as this method accesses a field
     * which is updated by netty threads.
     */
    private void registerNewSessions() {
        Set<Session> temp = null;

        synchronized (sessionsToRegister) {
            int size = sessionsToRegister.size();
            if (size == 0) return;

            temp = sessionsToRegister;
            sessionsToRegister = new HashSet<>(size);
        }

        for (Session s : temp) {
            sessions.add(s);
        }

        temp.clear();
    }

    public void setGameStateUpdater(GameStateUpdater updater) {
        this.updater = updater;
    }

    public void shutdown() {
        running = false;
    }

    public void submitTimedTask(TimedTask task) {
        taskQueue.offer(task);
    }

    public void unregisterOldSessions() {
        World world = World.getInstance();
        List<Session> unregisterList = null;

        synchronized (sessionsToUnregister) {
            int size = sessionsToUnregister.size();
            if (size == 0) return; // No work to do, release the lock quickly.

            // Create the ArrayList large enough to hold everything.
            // This ensures that there will be no extra copying due
            // to the ArrayList needing to grow in size.
            unregisterList = new ArrayList<>(size);
            long time = getHighResolutionTimestamp();
            UnregistrableSession us = null;

            while ((us = sessionsToUnregister.peek()) != null &&
                    us.getExecutionTime() <= time) {
                us = sessionsToUnregister.poll();

                Session s = us.getSession();
                unregisterList.add(s);
            }
        }

        for (Session s : unregisterList) {
            Player p = s.getPlayer();
            if (p != null) {
                world.unregisterPlayer(p);
            }

            sessions.remove(s);
            s.close();
            System.out.println("Session removed: " + s);
        }
    }

    public void unregisterSession(Session session) {
        System.out.println("Session removed: " + sessions.remove(session));
        session.close();
    }

    private void updateTimestamps() {
        highResolutionTimestamp = getNanoTimeAsMilliseconds();
        epochTimestamp = System.currentTimeMillis();
    }
}
