package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class DuelInformationMessage extends AbstractMessage {

    private final int itemCount;
    private final List<DuelItem> items;

    public DuelInformationMessage(int itemCount, List<DuelItem> items) {
        this.itemCount = itemCount;
        this.items = items;
    }

    public int getItemCount() {
        return itemCount;
    }

    public List<DuelItem> getItems() {
        return items;
    }

    public static class DuelItem {

        private final int amount;
        private final int itemId;

        public DuelItem(int itemId, int amount) {
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
