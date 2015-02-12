package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ObjectPrimaryActionMessage;
import org.moparscape.elysium.net.codec.decoder.message.ObjectSecondaryActionMessage;
import org.moparscape.elysium.net.codec.decoder.message.WallObjectPrimaryActionMessage;
import org.moparscape.elysium.net.codec.decoder.message.WallObjectSecondaryActionMessage;

/**
 * Created by daniel on 13/02/2015.
 */
public final class WorldMessageHandlers {

    public static final class ObjectPrimaryActionMessageHandler extends MessageHandler<ObjectPrimaryActionMessage> {
        @Override
        public boolean handle(Session session, Player player, ObjectPrimaryActionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ObjectSecondaryActionMessageHandler extends MessageHandler<ObjectSecondaryActionMessage> {
        @Override
        public boolean handle(Session session, Player player, ObjectSecondaryActionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class WallObjectPrimaryActionMessageHandler extends MessageHandler<WallObjectPrimaryActionMessage> {
        @Override
        public boolean handle(Session session, Player player, WallObjectPrimaryActionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class WallObjectSecondaryActionMessageHandler extends MessageHandler<WallObjectSecondaryActionMessage> {
        @Override
        public boolean handle(Session session, Player player, WallObjectSecondaryActionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }
}
