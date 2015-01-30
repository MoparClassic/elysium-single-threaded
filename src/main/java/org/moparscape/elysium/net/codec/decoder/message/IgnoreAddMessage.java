package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class IgnoreAddMessage extends AbstractMessage {

    private final long ignoreHash;

    public IgnoreAddMessage(long ignoreHash) {
        this.ignoreHash = ignoreHash;
    }

    public long getIgnoreHash() {
        return ignoreHash;
    }
}
