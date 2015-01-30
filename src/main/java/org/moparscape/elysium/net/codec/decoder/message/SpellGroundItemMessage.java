package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class SpellGroundItemMessage extends AbstractMessage {

    private final int actionType;
    private final int actionVariable;
    private final int actionX;
    private final int actionY;

    public SpellGroundItemMessage(int actionVariable, int actionX, int actionY, int actionType) {
        this.actionVariable = actionVariable;
        this.actionX = actionX;
        this.actionY = actionY;
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }

    public int getActionVariable() {
        return actionVariable;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }
}
