package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class FriendRemoveMessage extends AbstractMessage {

    private final long removedFriendHash;

    public FriendRemoveMessage(long removedFriendHash) {
        this.removedFriendHash = removedFriendHash;
    }

    public long getRemovedFriendHash() {
        return removedFriendHash;
    }
}
