package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class WallObjectPrimaryActionMessage extends AbstractMessage {

    private final int actionType;
    private final int actionX;
    private final int actionY;

    public WallObjectPrimaryActionMessage(int actionX, int actionY, int actionType) {
        this.actionX = actionX;
        this.actionY = actionY;
        this.actionType = actionType;
    }

    public int getActionType() {
        return actionType;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }
}
