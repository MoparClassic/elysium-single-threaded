package org.moparscape.elysium.net.codec.decoder;

import io.netty.buffer.ByteBuf;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 14/01/2015.
 */
public final class TradeMessageDecoders {

    public static final class TradeAcceptMessageDecoder extends AbstractMessageDecoder<TradeAcceptMessage> {

        public TradeAcceptMessageDecoder() {
            super(TradeAcceptMessage.class, 211);
        }

        public TradeAcceptMessage decode(ByteBuf buffer, int length) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class TradeConfirmAcceptMessageDecoder extends AbstractMessageDecoder<TradeConfirmAcceptMessage> {

        public TradeConfirmAcceptMessageDecoder() {
            super(TradeConfirmAcceptMessage.class, 53);
        }

        public TradeConfirmAcceptMessage decode(ByteBuf buffer, int length) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class TradeDeclineMessageDecoder extends AbstractMessageDecoder<TradeDeclineMessage> {

        public TradeDeclineMessageDecoder() {
            super(TradeDeclineMessage.class, 216);
        }

        public TradeDeclineMessage decode(ByteBuf buffer, int length) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class TradeInformationMessageDecoder extends AbstractMessageDecoder<TradeInformationMessage> {

        public TradeInformationMessageDecoder() {
            super(TradeInformationMessage.class, 70);
        }

        public TradeInformationMessage decode(ByteBuf buffer, int length) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class TradeRequestMessageDecoder extends AbstractMessageDecoder<TradeRequestMessage> {

        public TradeRequestMessageDecoder() {
            super(TradeRequestMessage.class, 166);
        }

        public TradeRequestMessage decode(ByteBuf buffer, int length) {
            throw new UnsupportedOperationException();
        }
    }
}
