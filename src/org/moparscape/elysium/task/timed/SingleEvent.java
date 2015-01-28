package org.moparscape.elysium.task.timed;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.entity.Player;

public abstract class SingleEvent extends AbstractTimedTask {

    private boolean running = true;

    public SingleEvent(Player owner, int delay) {
        super(Server.getInstance().getHighResolutionTimestamp() + 1, delay);
    }

    public void run() {
        action();
        running = false;
    }

    public abstract void action();

}