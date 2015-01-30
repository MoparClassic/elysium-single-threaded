package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ShopBuyMessage extends AbstractMessage {

    private final int buyPrice;
    private final int itemId;

    public ShopBuyMessage(int itemId, int buyPrice) {
        this.itemId = itemId;
        this.buyPrice = buyPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getItemId() {
        return itemId;
    }
}
