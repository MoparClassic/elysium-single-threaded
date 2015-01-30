import io.netty.buffer.ByteBuf;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class BufferTest {

    public static void dump(ByteBuf buffer) {
        StringBuilder sb = new StringBuilder(1000);
        sb.append(buffer.getUnsignedByte(0));
        for (int i = 1; i < buffer.readableBytes(); i++) {
            sb.append(", ").append(buffer.getUnsignedByte(i));
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {

    }
}
