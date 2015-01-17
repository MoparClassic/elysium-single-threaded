package org.moparscape.elysium.net.codec.decoder;

import io.netty.buffer.ByteBuf;
import org.moparscape.elysium.net.codec.decoder.message.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 14/01/2015.
 */
public final class DuelMessageDecoders {

    public static final class DuelAcceptMessageDecoder extends AbstractMessageDecoder<DuelAcceptMessage> {

        public DuelAcceptMessageDecoder() {
            super(DuelAcceptMessage.class, 252);
        }

        public DuelAcceptMessage decode(ByteBuf buffer, int length) {
            return new DuelAcceptMessage();
        }
    }

    public static final class DuelConfirmAcceptMessageDecoder extends AbstractMessageDecoder<DuelConfirmAcceptMessage> {

        public DuelConfirmAcceptMessageDecoder() {
            super(DuelConfirmAcceptMessage.class, 87);
        }

        public DuelConfirmAcceptMessage decode(ByteBuf buffer, int length) {
            return new DuelConfirmAcceptMessage();
        }
    }

    public static final class DuelDeclineMessageDecoder extends AbstractMessageDecoder<DuelDeclineMessage> {

        public DuelDeclineMessageDecoder() {
            super(DuelDeclineMessage.class, 35);
        }

        public DuelDeclineMessage decode(ByteBuf buffer, int length) {
            return new DuelDeclineMessage();
        }
    }

    public static final class DuelInformationMessageDecoder extends AbstractMessageDecoder<DuelInformationMessage> {

        public DuelInformationMessageDecoder() {
            super(DuelInformationMessage.class, 123);
        }

        public DuelInformationMessage decode(ByteBuf buffer, int length) {
            int itemCount = buffer.readByte();

            List<DuelInformationMessage.DuelItem> items = new ArrayList<>(itemCount);
            for (int i = 0; i < itemCount; i++) {
                int itemId = buffer.readShort();
                int amount = buffer.readInt();

                DuelInformationMessage.DuelItem item = new DuelInformationMessage.DuelItem(itemId, amount);
                items.add(item);
            }

            return new DuelInformationMessage(itemCount, items);
        }
    }

    public static final class DuelOptionsMessageDecoder extends AbstractMessageDecoder<DuelOptionsMessage> {

        public DuelOptionsMessageDecoder() {
            super(DuelOptionsMessage.class, 225);
        }

        public DuelOptionsMessage decode(ByteBuf buffer, int length) {
            boolean noRetreating = buffer.readByte() == 1;
            boolean noMagic = buffer.readByte() == 1;
            boolean noPrayer = buffer.readByte() == 1;
            boolean noWeapons = buffer.readByte() == 1;

            return new DuelOptionsMessage(noRetreating, noMagic, noPrayer, noWeapons);
        }
    }

    public static final class DuelRequestMessageDecoder extends AbstractMessageDecoder<DuelRequestMessage> {

        public DuelRequestMessageDecoder() {
            super(DuelRequestMessage.class, 222);
        }

        public DuelRequestMessage decode(ByteBuf buffer, int length) {
            int playerIndex = buffer.readShort();
            return new DuelRequestMessage(playerIndex);
        }
    }
}
