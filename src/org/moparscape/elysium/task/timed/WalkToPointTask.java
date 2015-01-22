package org.moparscape.elysium.task.timed;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.world.Point;

/**
 * Created by daniel on 22/01/2015.
 */
public abstract class WalkToPointTask extends AbstractTimedTask {

    protected final Point location;
    protected final Player owner;
    protected final int originalActionCount;
    private int radius;
    private boolean stop;
    protected boolean running = true;

    public WalkToPointTask(Player owner, Point location, int radius, boolean stop) {
        super(Server.getInstance().getHighResolutionTimestamp() + 1, 0);
        this.owner = owner;
        this.originalActionCount = owner.getActionCount();
        this.location = location;
        this.radius = radius;
        this.stop = stop;
        if (stop && owner.getLocation().withinRange(location, radius)) {
            owner.getMovement().resetPath();
            arrived();
            running = false;
        }
    }

    public final void run() {
        if (originalActionCount != owner.getActionCount()) {
            running = false;
            return;
        }

        Point ownerLoc = owner.getLocation();
        if (stop && ownerLoc.withinRange(location, radius)) {
            owner.getMovement().resetPath();
            arrived();
        } else if (owner.getMovement().hasMoved()) {
            return; // We're still moving
        } else if (ownerLoc.withinRange(location, radius)) {
            arrived();
        }
        running = false;
    }

    public boolean shouldRepeat() {
        return running;
    }

    public abstract void arrived();
}
