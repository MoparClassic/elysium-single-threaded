package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PrivacySettingMessage extends AbstractMessage {

    private final boolean blockChatMessages;
    private final boolean blockDuelRequests;
    private final boolean blockPrivateMessages;
    private final boolean blockTradeRequests;

    public PrivacySettingMessage(boolean blockChatMessages, boolean blockPrivateMessages,
                                 boolean blockTradeRequests, boolean blockDuelRequests) {
        this.blockChatMessages = blockChatMessages;
        this.blockPrivateMessages = blockPrivateMessages;
        this.blockTradeRequests = blockTradeRequests;
        this.blockDuelRequests = blockDuelRequests;
    }

    public boolean getBlockChatMessages() {
        return blockChatMessages;
    }

    public boolean getBlockDuelRequests() {
        return blockDuelRequests;
    }

    public boolean getBlockPrivateMessages() {
        return blockPrivateMessages;
    }

    public boolean getBlockTradeRequests() {
        return blockTradeRequests;
    }
}
