package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.ChatMessage;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;
import org.moparscape.elysium.world.World;

import java.util.Set;

/**
 * Created by daniel on 31/01/2015.
 */
public final class CommunicationMessageHandlers {

    public static final class FriendAddMessageHandler extends MessageHandler<FriendAddMessage> {
        @Override
        public boolean handle(Session session, Player player, FriendAddMessage message) {
            Set<Long> friends = player.getFriendList();

            if (friends.size() >= Player.COMMUNICATION_MAX_FRIENDS) {
                Packets.sendMessage(player, "Your friend list is too full");
                return true;
            }

            long friendHash = message.getFriendHash();
            if (friends.contains(friendHash)) {
                Packets.sendMessage(player, "That player is already on your friend list");
                return true;
            }

            player.addFriend(message.getFriendHash());
            return true;
        }
    }

    public static final class FriendPmMessageHandler extends MessageHandler<FriendPmMessage> {
        @Override
        public boolean handle(Session session, Player player, FriendPmMessage message) {
            Player target = World.getInstance().getPlayerByUsernameHash(message.getFriendHash());

            // TODO: Handle player mutes and administrator/moderator override of ignore list.

            // If the target isn't in the player list (i.e., not logged on)
            // OR the target isn't flagged as being logged in
            // OR the sender doesn't have the target on their friend list
            // OR the target has the sender on their ignore list
            // then we don't send the message, and let the sender know that
            // the target is not online.
            if (target == null ||
                    !target.isLoggedIn() ||
                    !player.getFriendList().contains(target.getUsernameHash()) ||
                    target.getIgnoreList().contains(player.getUsernameHash())) {
                Packets.sendMessage(player, "The target is not online");
                return true;
            }

            Packets.sendPrivateMessage(target, message.getFriendHash(), message.getMessage());
            return true;
        }
    }

    public static final class FriendRemoveMessageHandler extends MessageHandler<FriendRemoveMessage> {
        @Override
        public boolean handle(Session session, Player player, FriendRemoveMessage message) {
            player.removeFriend(message.getRemovedFriendHash());
            return true;
        }
    }

    public static final class IgnoreAddMessageHandler extends MessageHandler<IgnoreAddMessage> {
        @Override
        public boolean handle(Session session, Player player, IgnoreAddMessage message) {
            Set<Long> ignores = player.getIgnoreList();

            if (ignores.size() >= Player.COMMUNICATION_MAX_IGNORES) {
                Packets.sendMessage(player, "Your ignore list is too full");
                return true;
            }

            long ignoreHash = message.getIgnoreHash();
            if (ignores.contains(ignoreHash)) {
                Packets.sendMessage(player, "That player is already on your ignore list");
                return true;
            }

            player.addIgnore(ignoreHash);
            return true;
        }
    }

    public static final class IgnoreRemoveMessageHandler extends MessageHandler<IgnoreRemoveMessage> {
        @Override
        public boolean handle(Session session, Player player, IgnoreRemoveMessage message) {
            player.removeIgnore(message.getRemovedIgnoreHash());
            return true;
        }
    }

    public static final class PrivacySettingMessageHandler extends MessageHandler<PrivacySettingMessage> {
        @Override
        public boolean handle(Session session, Player player, PrivacySettingMessage message) {
            player.updateCommunicationSettings(
                    message.getBlockChatMessages(),
                    message.getBlockPrivateMessages(),
                    message.getBlockTradeRequests(),
                    message.getBlockDuelRequests());

            return true;
        }
    }

    public static final class PublicChatMessageHandler extends MessageHandler<PublicChatMessage> {
        @Override
        public boolean handle(Session session, Player player, PublicChatMessage message) {
            ChatMessage cm = new ChatMessage(player, message.getMessagePayload());
            player.addChatMessage(cm);

            return true;
        }
    }
}
