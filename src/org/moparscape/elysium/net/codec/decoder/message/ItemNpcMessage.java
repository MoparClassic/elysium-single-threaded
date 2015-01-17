package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemNpcMessage extends AbstractMessage {

    private final int inventoryIndex;
    private final int npcIndex;

    public ItemNpcMessage(int npcIndex, int inventoryIndex) {
        this.npcIndex = npcIndex;
        this.inventoryIndex = inventoryIndex;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

    public int getNpcIndex() {
        return npcIndex;
    }
}
