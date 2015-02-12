package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Item;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;
import org.moparscape.elysium.world.Region;

/**
 * Created by daniel on 13/02/2015.
 */
public final class MiscMessageHandlers {

    public static final class BotDetectionMessageHandler extends MessageHandler<BotDetectionMessage> {

        public boolean handle(Session session, Player player, BotDetectionMessage message) {
            return true;
        }
    }

    public static final class CommandMessageHandler extends MessageHandler<CommandMessage> {

        public boolean handle(Session session, Player player, CommandMessage message) {
            System.out.println("Command received: " + message.getCommand());
            String[] args = message.getArguments();

            if (message.hasArguments()) {
                StringBuilder sb = new StringBuilder(200);
                sb.append("Args: ");
                for (String s : args) {
                    sb.append(s).append(", ");
                }
                System.out.println(sb.substring(0, sb.length() - 2));
            }

            // TODO: Execute an appropriate command handler
            if (message.getCommand().equals("item")) {
                System.out.println("Adding item to floor");
                Region r = Region.getRegion(player.getLocation());
                r.addItem(new Item(10, 2000, player.getLocation(), null));
                r.addItem(new Item(11, 1, player.getLocation(), null));
                return true;
            }

            if (message.getCommand().equals("region")) {
                System.out.println(Region.getRegion(player.getLocation()));
                return true;
            }

            return true;
        }
    }

    public static final class ExceptionMessageHandler extends MessageHandler<ExceptionMessage> {
        @Override
        public boolean handle(Session session, Player player, ExceptionMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class PingMessageHandler extends MessageHandler<PingMessage> {
        @Override
        public boolean handle(Session session, Player player, PingMessage message) {
            // Nothing needs to be done here.
            return true;
        }
    }

    public static final class ReportMessageHandler extends MessageHandler<ReportMessage> {
        @Override
        public boolean handle(Session session, Player player, ReportMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class SleepwordMessageHandler extends MessageHandler<SleepwordMessage> {
        @Override
        public boolean handle(Session session, Player player, SleepwordMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class TrapMessageHandler extends MessageHandler<TrapMessage> {
        @Override
        public boolean handle(Session session, Player player, TrapMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }
}
