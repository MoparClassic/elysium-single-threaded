package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Observer;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 13/02/2015.
 */
public final class PlayerMiscMessageHandlers {

    public static final class AppearanceUpdateMessageHandler extends MessageHandler<AppearanceUpdateMessage> {

        public boolean handle(Session session, Player player, AppearanceUpdateMessage message) {
            player.setAppearance(message.getAppearance());
            return true;
        }
    }

    public static final class AppearancesMessageHandler extends MessageHandler<AppearancesMessage> {

        public boolean handle(Session session, Player player, AppearancesMessage message) {
            Observer o = player.getObserver();
            o.addPlayerAppearanceIds(message.getIndicies(), message.getAppearanceIds());

            return true;
        }
    }

    public static final class GameSettingMessageHandler extends MessageHandler<GameSettingMessage> {
        @Override
        public boolean handle(Session session, Player player, GameSettingMessage message) {
            player.updateGameSetting(message.getSettingIndex(), message.getFlag());
            return true;
        }
    }

    public static final class InventoryActionMessageHandler extends MessageHandler<InventoryActionMessage> {
        @Override
        public boolean handle(Session session, Player player, InventoryActionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class MenuActionMessageHandler extends MessageHandler<MenuActionMessage> {
        @Override
        public boolean handle(Session session, Player player, MenuActionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }
}
