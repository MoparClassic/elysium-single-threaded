package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class BankDepositMessage extends AbstractMessage {

    private final int amount;
    private final int itemId;

    public BankDepositMessage(int itemId, int amount) {
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
