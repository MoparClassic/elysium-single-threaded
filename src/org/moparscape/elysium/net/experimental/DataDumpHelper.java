package org.moparscape.elysium.net.experimental;

import io.netty.buffer.ByteBuf;

/**
 * Created by daniel on 17/01/2015.
 */
public class DataDumpHelper {

    public static void dumpByteBuf(int packetId, int packetLen, int dataLen, ByteBuf buf) {
        buf.markReaderIndex();
        int readable = buf.readableBytes();
        byte[] dump = new byte[readable];
        buf.readBytes(dump);

        StringBuilder sb = new StringBuilder(2000);
        sb.append(String.format("[PacketID = %d, PacketLen = %d, DataLen = %d] ",
                packetId, packetLen, dataLen))
                .append(packetId).append("\t");

        for (byte b : dump) {
            sb.append(b).append(", ");
        }

        String result = sb.substring(0, sb.length() - 2);
//        System.out.println(result);

        buf.resetReaderIndex();
    }
}
