package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 13/02/2015.
 */
public final class SpellMessageHandlers {

    public static final class SpellDoorMessageHandler extends MessageHandler<SpellDoorMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellDoorMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellGameObjectMessageHandler extends MessageHandler<SpellGameObjectMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellGameObjectMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellGroundItemMessageHandler extends MessageHandler<SpellGroundItemMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellGroundItemMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellGroundMessageHandler extends MessageHandler<SpellGroundMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellGroundMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellInvItemMessageHandler extends MessageHandler<SpellInvItemMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellInvItemMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellNpcMessageHandler extends MessageHandler<SpellNpcMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellNpcMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellPlayerMessageHandler extends MessageHandler<SpellPlayerMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellPlayerMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SpellSelfMessageHandler extends MessageHandler<SpellSelfMessage> {
        @Override
        public boolean handle(Session session, Player player, SpellSelfMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }
}
