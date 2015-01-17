package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemItemMessage extends AbstractMessage {

    private final int itemOneInventoryIndex;
    private final int itemTwoInventoryIndex;

    public ItemItemMessage(int itemOneInventoryIndex, int itemTwoInventoryIndex) {
        this.itemOneInventoryIndex = itemOneInventoryIndex;
        this.itemTwoInventoryIndex = itemTwoInventoryIndex;
    }

    public int getItemOneInventoryIndex() {
        return itemOneInventoryIndex;
    }

    public int getItemTwoInventoryIndex() {
        return itemTwoInventoryIndex;
    }
}
