package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;

/**
 * Created by daniel on 31/01/2015.
 */
public final class CombatMessageHandlers {

    public static final class AttackNpcMessageHandler extends MessageHandler<AttackNpcMessage> {

        public boolean handle(Session session, Player player, AttackNpcMessage message) {
            return true;
        }
    }

    public static final class AttackPlayerMessageHandler extends MessageHandler<AttackPlayerMessage> {

        public boolean handle(Session session, Player player, AttackPlayerMessage message) {
            return true;
        }
    }

    public static final class AttackStyleMessageHandler extends MessageHandler<AttackStyleMessage> {

        public boolean handle(Session session, Player player, AttackStyleMessage message) {
            return true;
        }
    }

    public static final class PrayerActivateMessageHandler extends MessageHandler<PrayerActivateMessage> {
        @Override
        public boolean handle(Session session, Player player, PrayerActivateMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class PrayerDeactivateMessageHandler extends MessageHandler<PrayerDeactivateMessage> {
        @Override
        public boolean handle(Session session, Player player, PrayerDeactivateMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }
}
