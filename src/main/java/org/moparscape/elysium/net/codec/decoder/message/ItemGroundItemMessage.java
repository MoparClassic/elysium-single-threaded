package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemGroundItemMessage extends AbstractMessage {

    private final int actionX;
    private final int actionY;
    private final int inventoryIndex;
    private final int itemId;

    public ItemGroundItemMessage(int actionX, int actionY, int itemId, int inventoryIndex) {
        this.actionX = actionX;
        this.actionY = actionY;
        this.itemId = itemId;
        this.inventoryIndex = inventoryIndex;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

    public int getItemId() {
        return itemId;
    }
}
