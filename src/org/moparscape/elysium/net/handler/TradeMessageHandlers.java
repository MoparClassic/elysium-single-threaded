package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.*;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.World;

import java.util.List;

/**
 * Created by daniel on 24/01/2015.
 */
public final class TradeMessageHandlers {

    public static final class TradeAcceptMessageHandler extends MessageHandler<TradeAcceptMessage> {
        @Override
        public boolean handle(Session session, Player player, TradeAcceptMessage message) {
            Player target = player.getWishToTrade();

            if (target == null || busy(target) ||
                    !player.isTrading() ||
                    !target.isTrading()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetTrading();
                target.resetTrading();
                return true;
            }

            player.setTradeOfferAccepted(true);

            Packets.sendTradeAcceptUpdate(player);
            Packets.sendTradeAcceptUpdate(target);

            if (target.isTradeOfferAccepted()) {
                Packets.sendTradeAccept(player);
                Packets.sendTradeAccept(target);
            }
            return true;
        }
    }

    public static final class TradeConfirmAcceptMessageHandler extends MessageHandler<TradeConfirmAcceptMessage> {
        @Override
        public boolean handle(Session session, Player player, TradeConfirmAcceptMessage message) {
            Player target = player.getWishToTrade();
            if (target == null || busy(target) ||
                    !player.isTrading() ||
                    !target.isTrading() ||
                    !player.isTradeOfferAccepted() ||
                    !target.isTradeOfferAccepted()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetTrading();
                target.resetTrading();
                return true;
            }

            player.setTradeConfirmAccepted(true);

            if (!target.isTradeConfirmAccepted()) return true;

            List<InvItem> myOffer = player.getTradeOffer();
            List<InvItem> theirOffer = target.getTradeOffer();

            int myRequiredSlots = player.getInventory().getRequiredSlots(theirOffer);
            int myAvailableSlots = (30 - player.getInventory().size()) + player.getInventory().getFreedSlots(myOffer);

            int theirRequiredSlots = target.getInventory().getRequiredSlots(myOffer);
            int theirAvailableSlots = (30 - target.getInventory().size()) + target.getInventory().getFreedSlots(theirOffer);

            if (theirRequiredSlots > theirAvailableSlots) {
                Packets.sendMessage(player, "The other player does not have room to accept your items.");
                Packets.sendMessage(target, "You do not have room in your inventory to hold those items.");
                player.resetTrading();
                target.resetTrading();
                return true;
            }
            if (myRequiredSlots > myAvailableSlots) {
                Packets.sendMessage(player, "You do not have room in your inventory to hold those items.");
                Packets.sendMessage(target, "The other player does not have room to accept your items.");
                player.resetTrading();
                target.resetTrading();
                return true;
            }

            PlayerSprite playerSprite = player.getSprite();
            Appearance playerAppearance = playerSprite.getAppearance();
            for (InvItem offeredItem : myOffer) {
                InvItem affectedItem = player.getInventory().get(offeredItem);
                if (affectedItem == null || affectedItem.getAmount() < offeredItem.getAmount()) {
//                    player.setSuspiciousPlayer(true);
                    player.resetTrading();
                    target.resetTrading();
                    return true;
                }
                if (affectedItem.isWielded()) {
                    affectedItem.setWielded(false);
                    playerSprite.setWornItem(
                            affectedItem.getWieldableDef().getWieldPos(),
                            playerAppearance.getSprite(affectedItem.getWieldableDef().getWieldPos()));
                }
                player.getInventory().remove(offeredItem);
            }

            PlayerSprite targetSprite = target.getSprite();
            Appearance targetAppearance = targetSprite.getAppearance();
            for (InvItem offeredItem : theirOffer) {
                InvItem affectedItem = target.getInventory().get(offeredItem);
                if (affectedItem == null || affectedItem.getAmount() < offeredItem.getAmount()) {
//                    target.setSuspiciousPlayer(true);
                    player.resetTrading();
                    target.resetTrading();
                    return true;
                }
                if (affectedItem.isWielded()) {
                    affectedItem.setWielded(false);
                    targetSprite.setWornItem(
                            affectedItem.getWieldableDef().getWieldPos(),
                            targetAppearance.getSprite(affectedItem.getWieldableDef().getWieldPos()));
                }
                target.getInventory().remove(offeredItem);
            }
            for (InvItem item : myOffer) {
                target.getInventory().add(item);
            }
            for (InvItem item : theirOffer) {
                player.getInventory().add(item);
            }

            Packets.sendInventory(player);
            Packets.sendEquipmentStats(player);
            Packets.sendMessage(player, "Trade completed.");

            Packets.sendInventory(target);
            Packets.sendEquipmentStats(target);
            Packets.sendMessage(target, "Trade completed.");

            player.resetTrading();
            target.resetTrading();
            return true;
        }
    }

    public static final class TradeDeclineMessageHandler extends MessageHandler<TradeDeclineMessage> {
        @Override
        public boolean handle(Session session, Player player, TradeDeclineMessage message) {
            Player target = player.getWishToTrade();
            if (target == null ||
                    busy(target) ||
                    !player.isTrading() ||
                    !target.isTrading()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetTrading();
                target.resetTrading();
                return true;
            }

            Credentials playerCreds = player.getCredentials();
            Packets.sendMessage(target, playerCreds.getUsername() + " has declined the trade.");

            player.resetTrading();
            target.resetTrading();
            return true;
        }
    }

    public static final class TradeInformationMessageHandler extends MessageHandler<TradeInformationMessage> {
        @Override
        public boolean handle(Session session, Player player, TradeInformationMessage message) {
            Player target = player.getWishToTrade();
            if (target == null || busy(target) ||
                    !player.isTrading() ||
                    !target.isTrading() ||
                    (player.isTradeOfferAccepted() && target.isTradeOfferAccepted()) ||
                    player.isTradeConfirmAccepted() ||
                    target.isTradeConfirmAccepted()) { // This shouldn't happen
                player.resetTrading();
                target.resetTrading();
                return true;
            }

            player.setTradeOfferAccepted(false);
            player.setTradeConfirmAccepted(false);
            target.setTradeOfferAccepted(false);
            target.setTradeConfirmAccepted(false);

            Packets.sendTradeAcceptUpdate(player);
            Packets.sendTradeAcceptUpdate(target);

            player.resetTradeOffer();

            Inventory inventory = player.getInventory();
            for (TradeInformationMessage.TradeItem item : message.getItems()) {
                if (item.getAmount() < 1) {
                    // Cheating?
                    continue;
                }

                int available = inventory.countById(item.getItemId());
                if (item.getAmount() > available) {
                    // Cheating?
                    continue;
                }

                player.addToTradeOffer(new InvItem(item.getItemId(), item.getAmount()));
            }
//            player.setRequiresOfferUpdate(true);
            Packets.sendTradeItems(target);
            return true;
        }
    }

    public static final class TradeRequestMessageHandler extends MessageHandler<TradeRequestMessage> {
        @Override
        public boolean handle(Session session, Player player, TradeRequestMessage message) {
            Player target = World.getInstance().getPlayer(message.getPlayerIndex());
            Point playerLoc = player.getLocation();
            Point targetLoc = target.getLocation();

            if (target == null || !playerLoc.withinRange(targetLoc, 8) ||
                    player.isTrading() || player.tradeDuelThrottling()) {
                player.resetTrading();
                return true;
            }

            Credentials playerCreds = player.getCredentials();
            long playerUsernameHash = playerCreds.getUsernameHash();
            Communication targetCom = target.getCommunication();
            Settings targetSettings = target.getSettings();
            if ((targetSettings.getPrivacySetting(2) && !targetCom.isFriendsWith(playerUsernameHash)) ||
                    targetCom.isIgnoring(playerUsernameHash)) {
                Packets.sendMessage(player, "This player has trade requests blocked.");
                return true;
            }

            Credentials targetCreds = target.getCredentials();

            player.setWishToTrade(target);
            Packets.sendMessage(player, target.isTrading() ?
                    targetCreds.getUsername() + " is already in a trade" :
                    "Sending trade request");

            Packets.sendMessage(target, playerCreds.getUsername() + " wishes to trade with you");

            if (!player.isTrading() &&
                    target.getWishToTrade() != null &&
                    target.getWishToTrade().equals(player) &&
                    !target.isTrading()) {
                player.setTrading(true);
                player.getMovement().resetPath();
                player.resetAllExceptTrading();
                target.setTrading(true);
                target.getMovement().resetPath();
                target.resetAllExceptTrading();

                Packets.sendTradeWindowOpen(player);
                Packets.sendTradeWindowOpen(target);
            }
            return true;
        }
    }

    private static boolean busy(Player player) {
        return false;
//        throw new NotImplementedException();
//        return player.isBusy() || player.isRanging() || player.accessingBank() || player.isDueling();
    }
}
