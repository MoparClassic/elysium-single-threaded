package org.moparscape.elysium.net.experimental;

import io.netty.buffer.ByteBuf;

/**
 * Created by daniel on 17/01/2015.
 */
public final class RawPacketBuilder {

    private final ByteBuf buffer;
    private final int headerPlaceholderIndex;
    private boolean finalised = false;

    public RawPacketBuilder(ByteBuf buf) {
        this.buffer = buf;
        this.headerPlaceholderIndex = buf.writerIndex();
    }

    private void checkFinalisation() {
        if (finalised) throw new IllegalStateException("buffer already finalised.");
    }

    public RawPacketBuilder finalisePacket() {
        checkFinalisation();
        finalised = true;
        return this;
    }

    public RawPacketBuilder writeByte(int value) {
        checkFinalisation();
        buffer.writeByte(value);
        return this;
    }

    public RawPacketBuilder writeLong(long value) {
        checkFinalisation();
        buffer.writeLong(value);
        return this;
    }
}
