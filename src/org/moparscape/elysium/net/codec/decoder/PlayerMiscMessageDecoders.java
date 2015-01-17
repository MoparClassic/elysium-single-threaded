package org.moparscape.elysium.net.codec.decoder;

import io.netty.buffer.ByteBuf;
import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 14/01/2015.
 */
public final class PlayerMiscMessageDecoders {

    public static final class AppearanceUpdateMessageDecoder extends AbstractMessageDecoder<AppearanceUpdateMessage> {

        public AppearanceUpdateMessageDecoder() {
            super(AppearanceUpdateMessage.class, 218);
        }

        public AppearanceUpdateMessage decode(ByteBuf buffer, int length) {
            int headGender = buffer.readByte();
            int headType = buffer.readByte();
            int bodyGender = buffer.readByte();
            int unknown = buffer.readByte();
            int hairColour = buffer.readByte();
            int topColour = buffer.readByte();
            int trouserColour = buffer.readByte();
            int skinColour = buffer.readByte();

            int headSprite = headType + 1;
            int bodySprite = bodyGender + 1;
            boolean male = headGender == 1;

            // TODO: Can't make my avatar female. Fix.
            Appearance app = new Appearance(hairColour, topColour, trouserColour, skinColour, headSprite, bodySprite, male);
            return new AppearanceUpdateMessage(app);
        }
    }

    public static final class AppearancesMessageDecoder extends AbstractMessageDecoder<AppearancesMessage> {

        public AppearancesMessageDecoder() {
            super(AppearancesMessage.class, 83);
        }

        public AppearancesMessage decode(ByteBuf buffer, int length) {
            int mobCount = buffer.readShort();
            int[] indicies = new int[mobCount];
            int[] appearanceIds = new int[mobCount];

            for (int i = 0; i < mobCount; i++) {
                indicies[i] = buffer.readShort();
                appearanceIds[i] = buffer.readShort();
            }

            return new AppearancesMessage(indicies, appearanceIds);
        }
    }

    public static final class GameSettingMessageDecoder extends AbstractMessageDecoder<GameSettingMessage> {

        public GameSettingMessageDecoder() {
            super(GameSettingMessage.class, 157);
        }

        public GameSettingMessage decode(ByteBuf buffer, int length) {
            int settingIndex = buffer.readByte();
            boolean flag = buffer.readByte() == 1;
            return new GameSettingMessage(settingIndex, flag);
        }
    }

    public static final class InventoryActionMessageDecoder extends AbstractMessageDecoder<InventoryActionMessage> {

        public InventoryActionMessageDecoder() {
            super(InventoryActionMessage.class, 89);
        }

        public InventoryActionMessage decode(ByteBuf buffer, int length) {
            int itemIndex = buffer.readShort();
            return new InventoryActionMessage(itemIndex);
        }
    }

    public static final class MenuActionMessageDecoder extends AbstractMessageDecoder<MenuActionMessage> {

        public MenuActionMessageDecoder() {
            super(MenuActionMessage.class, 154);
        }

        public MenuActionMessage decode(ByteBuf buffer, int length) {
            int option = buffer.readByte();
            return new MenuActionMessage(option);
        }
    }
}
