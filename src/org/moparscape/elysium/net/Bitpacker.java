package org.moparscape.elysium.net;

import io.netty.buffer.ByteBuf;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Bitpacker {

    /**
     * Bitmasks for <code>addBits()</code>
     */
    private static int bitmasks[] = {
            0, 0x1, 0x3, 0x7,
            0xf, 0x1f, 0x3f, 0x7f,
            0xff, 0x1ff, 0x3ff, 0x7ff,
            0xfff, 0x1fff, 0x3fff, 0x7fff,
            0xffff, 0x1ffff, 0x3ffff, 0x7ffff,
            0xfffff, 0x1fffff, 0x3fffff, 0x7fffff,
            0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff,
            0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff,
            -1
    };
    private final int bodyWriterIndex;
    private final ByteBuf buffer;
    private final int headerPlaceholderIndex;
    private final int id;
    private int bitPosition = 0;
    private boolean finalised = false;

    public Bitpacker(ByteBuf buf, int id) {
        this.buffer = buf;
        this.id = id;
        this.headerPlaceholderIndex = buf.writerIndex();

        // Skip 3 bytes - packet header placeholder
        this.buffer.writerIndex(this.headerPlaceholderIndex + 3);
        this.bodyWriterIndex = buf.writerIndex();
    }

    public Bitpacker addBits(int value, int numBits) {
        checkFinalised();

        int bytePos = bodyWriterIndex + (bitPosition >> 3);
        int bitOffset = 8 - (bitPosition & 7);
        bitPosition += numBits;

        for (; numBits > bitOffset; bitOffset = 8) {
            byte curByte = buffer.getByte(bytePos);
            curByte &= ~bitmasks[bitOffset];
            curByte |= (value >> (numBits - bitOffset)) & bitmasks[bitOffset];

            buffer.setByte(bytePos++, curByte);

            numBits -= bitOffset;
        }
        if (numBits == bitOffset) {
            byte curByte = buffer.getByte(bytePos);
            curByte &= ~bitmasks[bitOffset];
            curByte |= value & bitmasks[bitOffset];

            buffer.setByte(bytePos, curByte);
        } else {
            byte curByte = buffer.getByte(bytePos);
            curByte &= ~(bitmasks[numBits] << (bitOffset - numBits));
            curByte |= (value & bitmasks[numBits]) << (bitOffset - numBits);

            buffer.setByte(bytePos, curByte);
        }

        return this;
    }

    private void checkFinalised() {
        if (finalised) throw new IllegalStateException("Bitpacker already finalised");
    }

    public void finalisePacket() {
        checkFinalised();

        final int dataLen = (bitPosition + 7) / 8;
        final int packetLen = dataLen + 1;
        final int currentWriteIndex = bodyWriterIndex + dataLen; //buffer.writerIndex();
        final int lastByteIndex = currentWriteIndex - 1;

        if (dataLen >= 160) {
            buffer.writerIndex(headerPlaceholderIndex);
            buffer.writeByte(160 + (packetLen / 256));
            buffer.writeByte(packetLen & 0xff);
            buffer.writeByte(id);
            buffer.writerIndex(currentWriteIndex);
        } else {
            if (dataLen > 0) {
                byte lastByte = buffer.getByte(lastByteIndex);

                buffer.writerIndex(headerPlaceholderIndex);
                buffer.writeByte(packetLen);
                buffer.writeByte(lastByte);
                buffer.writeByte(id);

                // Move the writer index back to the index of the
                // last byte, which we've now got at the start
                // of the packet.
                buffer.writerIndex(lastByteIndex);
            } else {
                buffer.writerIndex(headerPlaceholderIndex);
                buffer.writeByte(packetLen);
                buffer.writeByte(id);
                // Don't reset writerIndex to currentWriteIndex.
            }
        }

        finalised = true;
    }
}
