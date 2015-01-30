package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class FriendPmMessage extends AbstractMessage {

    private final long friendHash;
    private final byte[] messageBytes;

    public FriendPmMessage(long friendHash, byte[] messageBytes) {
        this.friendHash = friendHash;
        this.messageBytes = messageBytes;
    }


}
