package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class SpellSelfMessage extends AbstractMessage {

    private final int actionType;

    public SpellSelfMessage(int actionType) {
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }
}
