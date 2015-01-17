package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class TradeInformationMessage extends AbstractMessage {

    private final int itemCount;
    private final List<TradeItem> items;

    public TradeInformationMessage(int itemCount, List<TradeItem> items) {
        this.itemCount = itemCount;
        this.items = items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public List<TradeItem> getItems() {
        return items;
    }

    public static class TradeItem {

        private final int amount;
        private final int itemId;

        public TradeItem(int itemId, int amount) {
            this.itemId = itemId;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public int getItemId() {
            return itemId;
        }
    }
}
