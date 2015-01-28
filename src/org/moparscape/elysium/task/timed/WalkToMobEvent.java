package org.moparscape.elysium.task.timed;

import org.moparscape.elysium.entity.Moveable;
import org.moparscape.elysium.entity.Player;

public abstract class WalkToMobEvent extends AbstractTimedTask {
    protected Moveable affectedMob;
    protected Player owner;
    protected boolean running = true;
    private int radius;

    public WalkToMobEvent(Player owner, Moveable affectedMob, int radius) {
        super(0, 500);
        this.affectedMob = affectedMob;
        this.radius = radius;
        if (owner.getLocation().withinRange(affectedMob.getLocation(), radius)) {
            arrived();
            running = false;
        }
    }

    public final void run() {
        if (owner.getLocation().withinRange(affectedMob.getLocation(), radius)) {
            arrived();
        } else if (owner.getMovement().hasMoved()) {
            return; // We're still moving
        } else {
            failed();
        }
        running = false;
    }

    public abstract void arrived();

    public void failed() {
    } // Not abstract as isn't required

    public Moveable getAffectedMob() {
        return affectedMob;
    }

}