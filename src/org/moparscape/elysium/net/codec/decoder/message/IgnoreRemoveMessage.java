package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class IgnoreRemoveMessage extends AbstractMessage {

    private final long removedIgnoreHash;

    public IgnoreRemoveMessage(long removedIgnoreHash) {
        this.removedIgnoreHash = removedIgnoreHash;
    }

    public long getRemovedIgnoreHash() {
        return removedIgnoreHash;
    }
}
