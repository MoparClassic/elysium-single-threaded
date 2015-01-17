package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ExceptionMessage extends AbstractMessage {

    private final byte[] exceptionBytes;
    private String cached = null;

    public ExceptionMessage(byte[] exceptionBytes) {
        this.exceptionBytes = exceptionBytes;
    }

    public byte[] getExceptionBytes() {
        return exceptionBytes;
    }

    public String getExceptionString() {
        if (cached == null) {
            cached = new String(exceptionBytes, 0, exceptionBytes.length);
        }

        return cached;
    }
}
