package org.moparscape.elysium.script.npc;

/**
 * Created by daniel on 3/02/2015.
 */
public class PlayerBusyException extends NpcTalkInterruptedException {

    public PlayerBusyException() {
        super();
    }

    public PlayerBusyException(String message) {
        super(message);
    }
}
