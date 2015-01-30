package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemPlayerMessage extends AbstractMessage {

    private final int inventoryIndex;
    private final int targetPlayerIndex;

    public ItemPlayerMessage(int targetPlayerIndex, int inventoryIndex) {
        this.targetPlayerIndex = targetPlayerIndex;
        this.inventoryIndex = inventoryIndex;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

    public int getTargetPlayerIndex() {
        return targetPlayerIndex;
    }
}
