package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class FriendAddMessage extends AbstractMessage {

    private final long friendHash;

    public FriendAddMessage(long friendHash) {
        this.friendHash = friendHash;
    }

    public long getFriendHash() {
        return friendHash;
    }
}
