package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.entity.DefaultEntityFactory;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.UnregistrableSession;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.LoginMessage;
import org.moparscape.elysium.net.codec.decoder.message.LogoutMessage;
import org.moparscape.elysium.net.codec.decoder.message.LogoutRequestMessage;
import org.moparscape.elysium.net.codec.decoder.message.SessionRequestMessage;
import org.moparscape.elysium.util.Formulae;
import org.moparscape.elysium.world.World;

/**
 * Created by daniel on 13/02/2015.
 */
public final class SessionMessageHandlers {

    public static final class LoginMessageHandler extends MessageHandler<LoginMessage> {

        @Override
        public boolean handle(Session session, Player player, LoginMessage message) {
            if (message.isReconnecting()) {
                System.out.println("Reconnecting player -- rejecting them");

                UnregistrableSession us = new UnregistrableSession(session, false);
                Server.getInstance().queueUnregisterSession(us);
                return false;
            }

            System.out.printf("Login message received! %d %d %s %s\n",
                    message.getUid(), message.getVersion(),
                    message.getUsername(), message.getPassword());

            Player p = DefaultEntityFactory.getInstance().newPlayer(session);
            p.setUsername(message.getUsername());
            p.setPassword(message.getPassword());

            session.setPlayer(p);

            // TODO: Actually load a player and such

            Packets.sendLoginResponse(p, Packets.LoginResponse.SUCCESS);

            // This MUST happen before sending things such as the server and
            // world info to the client
            World.getInstance().registerPlayer(p);

            Packets.sendServerInfo(p);
            Packets.sendFatigue(p);
            Packets.sendWorldInfo(p);
            Packets.sendStats(p);
            Packets.sendLoginBox(p);
//        Packets.sendAppearanceScreen(p);

            session.setAllowedToDisconnect(false);
            p.setLoggedIn(true);

            return true;
        }
    }

    public static final class LogoutMessageHandler extends MessageHandler<LogoutMessage> {

        @Override
        public boolean handle(Session session, Player player, LogoutMessage message) {
            Server server = Server.getInstance();

            // TODO: Verify that permission to logout has been granted before removing them from the world
            // This ensures that they can't cheat by closing their client etc
            if (session.isAllowedToDisconnect()) {
                UnregistrableSession us = new UnregistrableSession(session, false);
                server.queueUnregisterSession(us);
            } else {
                UnregistrableSession us = new UnregistrableSession(session, true);
                server.queueUnregisterSession(us);
            }

            return false;
        }
    }

    public static final class LogoutRequestMessageHandler extends MessageHandler<LogoutRequestMessage> {
        @Override
        public boolean handle(final Session session, Player player, LogoutRequestMessage message) {
            // TODO: Determine if the player is allowed to logout, and respond accordingly
            if (true /* The player is allowed to log out */) {
                session.setAllowedToDisconnect(true);
                Packets.sendLogout(player);
            } else {
                session.setAllowedToDisconnect(false);
                Packets.sendCantLogout(player);
            }

            return true;
        }
    }

    public static final class SessionRequestMessageHandler extends MessageHandler<SessionRequestMessage> {
        @Override
        public boolean handle(Session session, Player player, SessionRequestMessage message) {
            System.out.printf("SessionRequest: %s %d\n", message.getClassName(), message.getUserByte());

            long serverKey = Formulae.generateSessionKey(message.getUserByte());
            session.getByteBuf().writeLong(serverKey);

            return true;
        }
    }
}
