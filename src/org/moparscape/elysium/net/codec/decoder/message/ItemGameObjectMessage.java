package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemGameObjectMessage extends AbstractMessage {

    private final int actionX;
    private final int actionY;
    private final int itemId;

    public ItemGameObjectMessage(int actionX, int actionY, int itemId) {
        this.actionX = actionX;
        this.actionY = actionY;
        this.itemId = itemId;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }

    public int getItemId() {
        return itemId;
    }
}
