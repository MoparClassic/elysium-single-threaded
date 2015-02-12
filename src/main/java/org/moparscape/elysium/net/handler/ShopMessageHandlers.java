package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ShopBuyMessage;
import org.moparscape.elysium.net.codec.decoder.message.ShopCloseMessage;
import org.moparscape.elysium.net.codec.decoder.message.ShopSellMessage;

/**
 * Created by daniel on 13/02/2015.
 */
public final class ShopMessageHandlers {

    public static final class ShopBuyMessageHandler extends MessageHandler<ShopBuyMessage> {
        @Override
        public boolean handle(Session session, Player player, ShopBuyMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ShopCloseMessageHandler extends MessageHandler<ShopCloseMessage> {
        @Override
        public boolean handle(Session session, Player player, ShopCloseMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ShopSellMessageHandler extends MessageHandler<ShopSellMessage> {
        @Override
        public boolean handle(Session session, Player player, ShopSellMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }
}
