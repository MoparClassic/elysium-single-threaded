package org.moparscape.elysium.net.codec.decoder;

import io.netty.buffer.ByteBuf;
import org.moparscape.elysium.net.codec.decoder.message.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 14/01/2015.
 */
public final class TradeMessageDecoders {

    public static final class TradeAcceptMessageDecoder extends AbstractMessageDecoder<TradeAcceptMessage> {

        public TradeAcceptMessageDecoder() {
            super(TradeAcceptMessage.class, 211);
        }

        public TradeAcceptMessage decode(ByteBuf buffer, int length) {
            return new TradeAcceptMessage();
        }
    }

    public static final class TradeConfirmAcceptMessageDecoder extends AbstractMessageDecoder<TradeConfirmAcceptMessage> {

        public TradeConfirmAcceptMessageDecoder() {
            super(TradeConfirmAcceptMessage.class, 53);
        }

        public TradeConfirmAcceptMessage decode(ByteBuf buffer, int length) {
            return new TradeConfirmAcceptMessage();
        }
    }

    public static final class TradeDeclineMessageDecoder extends AbstractMessageDecoder<TradeDeclineMessage> {

        public TradeDeclineMessageDecoder() {
            super(TradeDeclineMessage.class, 216);
        }

        public TradeDeclineMessage decode(ByteBuf buffer, int length) {
            return new TradeDeclineMessage();
        }
    }

    public static final class TradeInformationMessageDecoder extends AbstractMessageDecoder<TradeInformationMessage> {

        public TradeInformationMessageDecoder() {
            super(TradeInformationMessage.class, 70);
        }

        public TradeInformationMessage decode(ByteBuf buffer, int length) {
            int itemCount = buffer.readByte();

            List<TradeInformationMessage.TradeItem> items = new ArrayList<>(itemCount);
            for (int i = 0; i < itemCount; i++) {
                int itemId = buffer.readShort();
                int amount = buffer.readInt();

                TradeInformationMessage.TradeItem item = new TradeInformationMessage.TradeItem(itemId, amount);
                items.add(item);
            }

            return new TradeInformationMessage(itemCount, items);
        }
    }

    public static final class TradeRequestMessageDecoder extends AbstractMessageDecoder<TradeRequestMessage> {

        public TradeRequestMessageDecoder() {
            super(TradeRequestMessage.class, 166);
        }

        public TradeRequestMessage decode(ByteBuf buffer, int length) {
            int playerIndex = buffer.readShort();
            return new TradeRequestMessage(playerIndex);
        }
    }
}
