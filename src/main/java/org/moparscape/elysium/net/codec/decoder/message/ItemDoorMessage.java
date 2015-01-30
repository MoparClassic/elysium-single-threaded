package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemDoorMessage extends AbstractMessage {

    private final int actionX;
    private final int actionY;
    private final int direction;
    private final int itemId;

    public ItemDoorMessage(int actionX, int actionY, int direction, int itemId) {
        this.actionX = actionX;
        this.actionY = actionY;
        this.direction = direction;
        this.itemId = itemId;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }

    public int getDirection() {
        return direction;
    }

    public int getItemId() {
        return itemId;
    }
}
