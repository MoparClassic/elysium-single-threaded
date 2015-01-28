package org.moparscape.elysium.task.timed;


import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.world.World;

public abstract class DelayedEvent {
    public static final World world = World.getInstance();
    protected int delay = 500;
    protected Player owner;
    protected boolean running = true;
    private long lastRun = System.currentTimeMillis();

    public DelayedEvent(Player owner, int delay) {
        this.owner = owner;
        this.delay = delay;
    }

    public boolean belongsTo(Player player) {
        return owner != null && owner.equals(player);
    }

    public Player getOwner() {
        return owner;
    }

    public boolean hasOwner() {
        return owner != null;
    }

    public abstract void run();

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setLastRun(long time) {
        lastRun = time;
    }

    public final boolean shouldRemove() {
        return !running;
    }

    public final boolean shouldRun() {
        return running && System.currentTimeMillis() - lastRun >= delay;
    }

    public final void stop() {
        running = false;
    }

    public int timeTillNextRun() {
        int time = (int) (delay - (System.currentTimeMillis() - lastRun));
        return time < 0 ? 0 : time;
    }

    public final void updateLastRun() {
        lastRun = System.currentTimeMillis();
    }

}