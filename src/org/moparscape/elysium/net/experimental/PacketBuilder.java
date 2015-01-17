package org.moparscape.elysium.net.experimental;

import io.netty.buffer.ByteBuf;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public class PacketBuilder {

    private final ByteBuf buffer;
    private final int headerPlaceholderIndex;
    private final int id;
    private final int payloadPlaceholderIndex;
    private int bytesWritten = 0;
    private boolean finalised = false;

    public PacketBuilder(ByteBuf buf, int id) {
        this.buffer = buf;
        this.id = id;
        this.headerPlaceholderIndex = buf.writerIndex();

        // Skip 3 bytes - packet header placeholder
        this.buffer.writerIndex(this.headerPlaceholderIndex + 3);
        this.payloadPlaceholderIndex = buf.writerIndex();
    }

    public PacketBuilder finalisePacket() {
        int dataLen = bytesWritten; // Length of payload
        int packetLen = dataLen + 1;          // Length of opcode followed by payload
        int currentWriteIndex = buffer.writerIndex();
        int lastByteIndex = currentWriteIndex - 1;

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
        return this;
    }

    public PacketBuilder writeByte(int value) {
        buffer.writeByte(value);
        bytesWritten++;
        return this;
    }

    public PacketBuilder writeBytes(byte[] src, int startIndex, int length) {
        buffer.writeBytes(src, startIndex, length);
        bytesWritten += length;
        return this;
    }

    public PacketBuilder writeBytes(byte[] src) {
        buffer.writeBytes(src);
        bytesWritten += src.length;
        return this;
    }

    public PacketBuilder writeInt(int value) {
        buffer.writeInt(value);
        bytesWritten += 4;
        return this;
    }

    public PacketBuilder writeLong(long value) {
        buffer.writeLong(value);
        bytesWritten += 8;
        return this;
    }

    public PacketBuilder writeShort(int value) {
        buffer.writeShort(value);
        bytesWritten += 2;
        return this;
    }
}
