package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class AttackNpcMessage extends AbstractMessage {

    private final int npcIndex;

    public AttackNpcMessage(int npcIndex) {
        this.npcIndex = npcIndex;
    }

    public int getNpcIndex() {
        return npcIndex;
    }
}
