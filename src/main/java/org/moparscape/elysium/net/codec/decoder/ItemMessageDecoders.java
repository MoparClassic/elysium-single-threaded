package org.moparscape.elysium.net.codec.decoder;

import io.netty.buffer.ByteBuf;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 14/01/2015.
 */
public final class ItemMessageDecoders {

    public static final class ItemDoorMessageDecoder extends AbstractMessageDecoder<ItemDoorMessage> {

        public ItemDoorMessageDecoder() {
            super(ItemDoorMessage.class, 36);
        }

        public ItemDoorMessage decode(ByteBuf buffer, int length) {
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            int direction = buffer.readByte();
            int itemId = buffer.readShort();
            return new ItemDoorMessage(actionX, actionY, direction, itemId);
        }
    }

    public static final class ItemDropMessageDecoder extends AbstractMessageDecoder<ItemDropMessage> {

        public ItemDropMessageDecoder() {
            super(ItemDropMessage.class, 147);
        }

        public ItemDropMessage decode(ByteBuf buffer, int length) {
            int index = buffer.readShort();
            return new ItemDropMessage(index);
        }
    }

    public static final class ItemGameObjectMessageDecoder extends AbstractMessageDecoder<ItemGameObjectMessage> {

        public ItemGameObjectMessageDecoder() {
            super(ItemGameObjectMessage.class, 94);
        }

        public ItemGameObjectMessage decode(ByteBuf buffer, int length) {
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            int itemId = buffer.readShort();
            return new ItemGameObjectMessage(actionX, actionY, itemId);
        }
    }

    public static final class ItemGroundItemMessageDecoder extends AbstractMessageDecoder<ItemGroundItemMessage> {

        public ItemGroundItemMessageDecoder() {
            super(ItemGroundItemMessage.class, 34);
        }

        public ItemGroundItemMessage decode(ByteBuf buffer, int length) {
            int actionX = buffer.readShort();
            int actionY = buffer.readShort();
            int itemId = buffer.readShort();
            int inventoryIndex = buffer.readShort();
            return new ItemGroundItemMessage(actionX, actionY, itemId, inventoryIndex);
        }
    }

    public static final class ItemItemMessageDecoder extends AbstractMessageDecoder<ItemItemMessage> {

        public ItemItemMessageDecoder() {
            super(ItemItemMessage.class, 27);
        }

        public ItemItemMessage decode(ByteBuf buffer, int length) {
            int itemOneInventoryIndex = buffer.readShort();
            int itemTwoInventoryIndex = buffer.readShort();
            return new ItemItemMessage(itemOneInventoryIndex, itemTwoInventoryIndex);
        }
    }

    public static final class ItemNpcMessageDecoder extends AbstractMessageDecoder<ItemNpcMessage> {

        public ItemNpcMessageDecoder() {
            super(ItemNpcMessage.class, 142);
        }

        public ItemNpcMessage decode(ByteBuf buffer, int length) {
            int npcIndex = buffer.readShort();
            int inventoryIndex = buffer.readShort();
            return new ItemNpcMessage(npcIndex, inventoryIndex);
        }
    }

    public static final class ItemPickupMessageDecoder extends AbstractMessageDecoder<ItemPickupMessage> {

        public ItemPickupMessageDecoder() {
            super(ItemPickupMessage.class, 245);
        }

        public ItemPickupMessage decode(ByteBuf buffer, int length) {
            int x = buffer.readShort();
            int y = buffer.readShort();
            int itemId = buffer.readShort();
            int unknown = buffer.readShort();
            return new ItemPickupMessage(itemId, x, y, unknown);
        }
    }

    public static final class ItemPlayerMessageDecoder extends AbstractMessageDecoder<ItemPlayerMessage> {

        public ItemPlayerMessageDecoder() {
            super(ItemPlayerMessage.class, 16);
        }

        public ItemPlayerMessage decode(ByteBuf buffer, int length) {
            int targetPlayerIndex = buffer.readShort();
            int inventoryIndex = buffer.readShort();
            return new ItemPlayerMessage(targetPlayerIndex, inventoryIndex);
        }
    }

    public static final class ItemUnwieldMessageDecoder extends AbstractMessageDecoder<ItemUnwieldMessage> {

        public ItemUnwieldMessageDecoder() {
            super(ItemUnwieldMessage.class, 92);
        }

        public ItemUnwieldMessage decode(ByteBuf buffer, int length) {
            int itemIndex = buffer.readShort();
            return new ItemUnwieldMessage(itemIndex);
        }
    }

    public static final class ItemWieldMessageDecoder extends AbstractMessageDecoder<ItemWieldMessage> {

        public ItemWieldMessageDecoder() {
            super(ItemWieldMessage.class, 181);
        }

        public ItemWieldMessage decode(ByteBuf buffer, int length) {
            int itemIndex = buffer.readShort();
            return new ItemWieldMessage(itemIndex);
        }
    }
}
