package org.moparscape.elysium;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.moparscape.elysium.entity.EntityComparators;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.UnregistrableSession;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.ElysiumChannelInitializer;
import org.moparscape.elysium.task.IssueUpdatePacketsTask;
import org.moparscape.elysium.task.timed.TimedTask;
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

    private static final Server INSTANCE = new Server();

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
    private final ExecutorService dataExecutorService = Executors.newSingleThreadExecutor();
    private final Set<Session> sessions = new HashSet<>(1500);
    private final PriorityQueue<UnregistrableSession> sessionsToUnregister =
            new PriorityQueue<>(new EntityComparators.HeartbeatComparator());
    /**
     * We used to use a PriorityBlockingQueue here. But upon inspecting the source code
     * I've learned that it acquires and release a lock EVERY TIME you peek/poll.
     * We're better off just synchronizing externally which is what's done now.
     */
    private final PriorityQueue<TimedTask> taskQueue = new PriorityQueue<>();
    private final GameStateUpdater updater = new GameStateUpdater();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
    /**
     * An epoch timestamp (set using System.currentTimeMillis()) that can be used
     * for storing and comparing database timestamps.
     */
    private long epochTimestamp = System.currentTimeMillis();
    /**
     * A higher resolution (on most systems) timer that is used to accurately detect
     * when an update should be performed.
     */
    private long highResolutionTimestamp = System.nanoTime() / 1000000;
    private long lastPulse = 0L;
    private boolean running = true;
    private Set<Session> sessionsToRegister = new HashSet<>(10);

    private Server() {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + cores);
    }

    public static Server getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Server server = Server.getInstance();
        ChannelFuture cf = server.listen();

        System.out.println("Server has started. :)");

        // Enter the game loop, and stay there until shutdown
        server.gameLoop();

        System.out.println("Shutdown complete.");
    }

    private void gameLoop() {
        System.out.println("Game loop started");

        World world = World.getInstance();

        ParentLoop:
        while (true) {
            try {
                while (running) {
                    // Update the cached highResolutionTimestamp, and see if more than 600ms have passed
                    // If 600ms have passed then do another update; otherwise, sleep and try again
                    updateTimestamps();
                    long stampDiff = highResolutionTimestamp - lastPulse;
                    if (stampDiff < 600) {
                        try {
                            Thread.sleep(1); // Surrender this time slice
                        } catch (InterruptedException e) {
                            System.err.println("Error occurred while sleeping between game pulses");
                        }

                        continue;
                    }

                    registerNewSessions();
                    pulseSessions(); // This function blocks until all sessions have finished
                    unregisterOldSessions();

                    processTasks(); // This function blocks until all events have been processed
                    updater.updateState(); // This function blocks until all updating has finished
                    issueUpdatePackets(); // This function blocks until all packets have been sent

                    for (Session s : sessions) {
                        s.writeAndFlush();
                    }

                    updater.updateCollections(); // This function blocks until everything is finished

                    // Update the time that the last pulse took place before finishing
                    lastPulse = highResolutionTimestamp;
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

    private void issueUpdatePackets() throws Exception {
        IssueUpdatePacketsTask task = new IssueUpdatePacketsTask(sessions);
        task.run();
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

    private void processTasks() throws Exception {
        long time = getHighResolutionTimestamp();
        TimedTask task = null;

        while ((task = taskQueue.peek()) != null && task.getExecutionTime() <= time) {
            // This task is due to run -- remove from the priority queue
            // and add it to the list of tasks to execute
            task = taskQueue.poll();
            try {
                task.run();
            } catch (Exception e) {
                // TODO: Handle errors here somehow.
            }

            if (task.shouldRepeat()) {
                taskQueue.offer(task);
            }
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
                    us.getScheduledPulseTime() <= time) {
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
        highResolutionTimestamp = System.nanoTime() / 1000000;
        epochTimestamp = System.currentTimeMillis();
    }
}
