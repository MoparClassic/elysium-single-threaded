package org.moparscape.elysium.net.codec.decoder;

import io.netty.buffer.ByteBuf;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 14/01/2015.
 */
public final class SpellMessageDecoders {

    public static final class SpellDoorMessageDecoder extends AbstractMessageDecoder<SpellDoorMessage> {

        public SpellDoorMessageDecoder() {
            super(SpellDoorMessage.class, 67);
        }

        public SpellDoorMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            int actionType = buffer.readByte();
            return new SpellDoorMessage(actionVariable, actionX, actionY, actionType);
        }
    }

    public static final class SpellGameObjectMessageDecoder extends AbstractMessageDecoder<SpellGameObjectMessage> {

        public SpellGameObjectMessageDecoder() {
            super(SpellGameObjectMessage.class, 17);
        }

        public SpellGameObjectMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            return new SpellGameObjectMessage(actionVariable, actionX, actionY);
        }
    }

    public static final class SpellGroundItemMessageDecoder extends AbstractMessageDecoder<SpellGroundItemMessage> {

        public SpellGroundItemMessageDecoder() {
            super(SpellGroundItemMessage.class, 104);
        }

        public SpellGroundItemMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            int actionType = buffer.readShort();
            return new SpellGroundItemMessage(actionVariable, actionX, actionY, actionType);
        }
    }

    public static final class SpellGroundMessageDecoder extends AbstractMessageDecoder<SpellGroundMessage> {

        public SpellGroundMessageDecoder() {
            super(SpellGroundMessage.class, 232);
        }

        public SpellGroundMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            return new SpellGroundMessage(actionVariable, actionX, actionY);
        }
    }

    public static final class SpellInvItemMessageDecoder extends AbstractMessageDecoder<SpellInvItemMessage> {

        public SpellInvItemMessageDecoder() {
            super(SpellInvItemMessage.class, 49);
        }

        public SpellInvItemMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionType = buffer.readShort();
            return new SpellInvItemMessage(actionVariable, actionType);
        }
    }

    public static final class SpellNpcMessageDecoder extends AbstractMessageDecoder<SpellNpcMessage> {

        public SpellNpcMessageDecoder() {
            super(SpellNpcMessage.class, 71);
        }

        public SpellNpcMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionType = buffer.readShort();
            return new SpellNpcMessage(actionVariable, actionType);
        }
    }

    public static final class SpellPlayerMessageDecoder extends AbstractMessageDecoder<SpellPlayerMessage> {

        public SpellPlayerMessageDecoder() {
            super(SpellPlayerMessage.class, 55);
        }

        public SpellPlayerMessage decode(ByteBuf buffer, int length) {
            int actionVariable = buffer.readShort();
            int actionType = buffer.readShort();
            return new SpellPlayerMessage(actionVariable, actionType);
        }
    }

    public static final class SpellSelfMessageDecoder extends AbstractMessageDecoder<SpellSelfMessage> {

        public SpellSelfMessageDecoder() {
            super(SpellSelfMessage.class, 206);
        }

        public SpellSelfMessage decode(ByteBuf buffer, int length) {
            int actionType = buffer.readShort();
            return new SpellSelfMessage(actionType);
        }
    }
}
