package org.moparscape.elysium.script.npc;

/**
 * Created by daniel on 3/02/2015.
 */
public class NpcTalkInterruptedException extends RuntimeException {

    public NpcTalkInterruptedException() {
        super();
    }

    public NpcTalkInterruptedException(String message) {
        super(message);
    }
}
