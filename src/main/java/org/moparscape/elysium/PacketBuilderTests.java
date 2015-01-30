package org.moparscape.elysium;

/**
 * Created by daniel on 18/01/2015.
 */
public class PacketBuilderTests {

//    public static void main(String[] args) {
//        PacketBuilderTests tests = new PacketBuilderTests();
////        tests.runPacketBuilderTests();
//        tests.runBitpackerTests();
//    }
//
//    public void populateBitpacker(Bitpacker pb) {
//        for (int i = 0; i < 10; i++) {
//            pb.addBits(2047, 11);
//            pb.addBits(8191, 13);
//            pb.addBits(15, 4);
//            pb.addBits(255, 8);
//        }
//    }
//
//    public void populateBitpacker(org.moparscape.elysium.net.Bitpacker pb) {
//        for (int i = 0; i < 10; i++) {
//            pb.addBits(2047, 11);
//            pb.addBits(8191, 13);
//            pb.addBits(15, 4);
//            pb.addBits(255, 8);
//        }
//        pb.finalisePacket();
//    }
//
//    public void populateBitpackerLarge(Bitpacker pb) {
//        for (int i = 0; i < 40; i++) {
//            pb.addBits(2047, 11);
//            pb.addBits(8191, 13);
//            pb.addBits(15, 4);
//            pb.addBits(255, 8);
//        }
//    }
//
//    public void populateBitpackerLarge(org.moparscape.elysium.net.Bitpacker pb) {
//        for (int i = 0; i < 40; i++) {
//            pb.addBits(2047, 11);
//            pb.addBits(8191, 13);
//            pb.addBits(15, 4);
//            pb.addBits(255, 8);
//        }
//        pb.finalisePacket();
//    }
//
//    public void populatePacketBuilder(PacketBuilder pb) {
//        pb.writeByte(1);
//        pb.writeShort(2);
//        pb.writeInt(3);
//        pb.writeLong(4);
//
//        pb.writeBytes("String 1".getBytes());
//
//        String s = "String 2";
//        byte[] sBytes = s.getBytes();
//        pb.writeBytes(sBytes, 0, sBytes.length);
//        pb.finalisePacket();
//    }
//
//    public void populatePacketBuilder(PacketBuilder pb) {
//        pb.writeByte(1);
//        pb.writeShort(2);
//        pb.writeInt(3);
//        pb.writeLong(4);
//
//        pb.writeBytes("String 1".getBytes());
//
//        String s = "String 2";
//        byte[] sBytes = s.getBytes();
//        pb.writeBytes(sBytes, 0, sBytes.length);
//    }
//
//    public void populatePacketBuilderLarge(PacketBuilder pb) {
//        for (int i = 0; i < 21; i++) {
//            pb.writeLong(i + 1);
//        }
//    }
//
//    public void populatePacketBuilderLarge(PacketBuilder pb) {
//        for (int i = 0; i < 21; i++) {
//            pb.writeLong(i + 1);
//        }
//        pb.finalisePacket();
//    }
//
//    public void print(ByteBuf buf) {
//        buf.markReaderIndex();
//        int readable = buf.readableBytes();
//        if (readable < 1) return;
//
//        byte[] dump = new byte[readable];
//        buf.readBytes(dump);
//
//        StringBuilder sb = new StringBuilder(2000);
//        for (byte b : dump) {
//            sb.append(b).append(", ");
//        }
//
//        String result = sb.substring(0, sb.length() - 2);
//        System.out.println(result);
//
//        buf.resetReaderIndex();
//    }
//
//    public void runBitpackerTests() {
//        ByteBuf buf = Unpooled.buffer(16384);
//        Bitpacker pb;
//        org.moparscape.elysium.net.Bitpacker epb;
//
//        System.out.println("Packets, data length < 160");
//        pb = new Bitpacker();
//        pb.setId(99);
//        populateBitpacker(pb);
//        print(pb.toPacket());
//
//        pb = new Bitpacker();
//        pb.setId(99);
//        populateBitpacker(pb);
//        print(pb.toPacket());
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        populateBitpacker(epb);
//        print(buf);
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        populateBitpacker(epb);
//        print(buf);
//
//        buf.clear();
//
//        System.out.println("Packets, data length >= 160");
//        pb = new Bitpacker();
//        pb.setId(99);
//        populateBitpackerLarge(pb);
//        print(pb.toPacket());
//
//        pb = new Bitpacker();
//        pb.setId(99);
//        populateBitpackerLarge(pb);
//        print(pb.toPacket());
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        populateBitpackerLarge(epb);
//        print(buf);
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        populateBitpackerLarge(epb);
//        print(buf);
//
//        buf.clear();
//
//        System.out.println("Zero-length packets");
//        pb = new Bitpacker();
//        pb.setId(99);
//        print(pb.toPacket());
//
//        pb = new Bitpacker();
//        pb.setId(99);
//        print(pb.toPacket());
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        epb.finalisePacket();
//        print(buf);
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        epb.finalisePacket();
//        print(buf);
//
//        buf.clear();
//
//        System.out.println("Now all together");
//        pb = new Bitpacker();
//        pb.setId(99);
//        populateBitpacker(pb);
//        print(pb.toPacket());
//
//        pb = new Bitpacker();
//        pb.setId(99);
//        populateBitpackerLarge(pb);
//        print(pb.toPacket());
//
//        pb = new Bitpacker();
//        pb.setId(99);
//        print(pb.toPacket());
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        populateBitpacker(epb);
//        print(buf);
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        populateBitpackerLarge(epb);
//        print(buf);
//
//        epb = new org.moparscape.elysium.net.Bitpacker(buf, 99);
//        epb.finalisePacket();
//        print(buf);
//
//        buf.clear();
//    }
//
//    public void runPacketBuilderTests() {
//        System.out.println("Packets, data length < 160");
//        PacketBuilder pb = new PacketBuilder();
//        pb.setId(99);
//        populatePacketBuilder(pb);
//        print(pb.toPacket());
//
//        pb = new PacketBuilder();
//        pb.setId(99);
//        populatePacketBuilder(pb);
//        print(pb.toPacket());
//
//        ByteBuf buf = Unpooled.buffer(16384);
//        PacketBuilder epb =
//                new PacketBuilder(buf, 99);
//
//        populatePacketBuilder(epb);
//        print(buf);
//
//        epb = new PacketBuilder(buf, 99);
//        populatePacketBuilder(epb);
//        print(buf);
//
//        buf.clear();
//
//        System.out.println("Packets, data length >= 160");
//        pb = new PacketBuilder();
//        pb.setId(99);
//        populatePacketBuilderLarge(pb);
//        print(pb.toPacket());
//
//        pb = new PacketBuilder();
//        pb.setId(99);
//        populatePacketBuilderLarge(pb);
//        print(pb.toPacket());
//
//        epb = new PacketBuilder(buf, 99);
//        populatePacketBuilderLarge(epb);
//        print(buf);
//
//        epb = new PacketBuilder(buf, 99);
//        populatePacketBuilderLarge(epb);
//        print(buf);
//
//        buf.clear();
//
//        System.out.println("Zero-length packets");
//
//        pb = new PacketBuilder(0);
//        pb.setId(171);
//        print(pb.toPacket());
//
//        pb = new PacketBuilder(0);
//        pb.setId(171);
//        print(pb.toPacket());
//
//        epb = new PacketBuilder(buf, 171);
//        epb.finalisePacket();
//        print(buf);
//
//        epb = new PacketBuilder(buf, 171);
//        epb.finalisePacket();
//        print(buf);
//
//        buf.clear();
//
//        System.out.println("Now all together");
//
//        pb = new PacketBuilder();
//        pb.setId(99);
//        populatePacketBuilder(pb);
//        print(pb.toPacket());
//
//        pb = new PacketBuilder();
//        pb.setId(99);
//        populatePacketBuilderLarge(pb);
//        print(pb.toPacket());
//
//        pb = new PacketBuilder(0);
//        pb.setId(171);
//        print(pb.toPacket());
//
//        epb = new PacketBuilder(buf, 99);
//        populatePacketBuilder(epb);
//        print(buf);
//
//        epb = new PacketBuilder(buf, 99);
//        populatePacketBuilderLarge(epb);
//        print(buf);
//
//        epb = new PacketBuilder(buf, 171);
//        epb.finalisePacket();
//        print(buf);
//
//        buf.clear();
//    }
}
