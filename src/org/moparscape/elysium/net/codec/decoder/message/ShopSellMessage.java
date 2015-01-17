package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ShopSellMessage extends AbstractMessage {

    private final int itemId;
    private final int sellPrice;

    public ShopSellMessage(int itemId, int sellPrice) {
        this.itemId = itemId;
        this.sellPrice = sellPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
