package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.PlayerState;
import org.moparscape.elysium.entity.component.*;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;
import org.moparscape.elysium.util.Formulae;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.World;

/**
 * Created by daniel on 24/01/2015.
 */
public final class DuelMessageHandlers {

    private static boolean busy(Player player) {
        return false;
//        return player.isBusy() || player.isRanging() || player.accessingBank() || player.isDueling();
    }

    public static final class DuelAcceptMessageHandler extends MessageHandler<DuelAcceptMessage> {
        @Override
        public boolean handle(Session session, Player player, DuelAcceptMessage message) {
            Player target = player.getWishToDuel();
            if (target == null || busy(target) ||
                    !player.isDueling() ||
                    !target.isDueling()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetDueling();
                target.resetDueling();
                return true;
            }

            player.setDuelOfferAccepted(true);

            Packets.sendDuelAcceptUpdate(player);
            Packets.sendDuelAcceptUpdate(target);

            if (target.isDuelOfferAccepted()) {
                Packets.sendDuelAccept(player);
                Packets.sendDuelAccept(target);
            }
            return true;
        }
    }

    public static final class DuelConfirmAcceptMessageHandler extends MessageHandler<DuelConfirmAcceptMessage> {
        @Override
        public boolean handle(Session session, Player player, DuelConfirmAcceptMessage message) {
            Player target = player.getWishToDuel();
            if (target == null || busy(target) ||
                    !player.isDueling() ||
                    !target.isDueling() ||
                    !player.isDuelOfferAccepted() ||
                    !target.isDuelOfferAccepted()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetDueling();
                target.resetDueling();
                return true;
            }
            player.setDuelConfirmAccepted(true);

            if (target.isDuelConfirmAccepted()) {
                Packets.sendDuelWindowClose(player);
                Packets.sendMessage(player, "Commencing Duel");
                Packets.sendDuelWindowClose(target);
                Packets.sendMessage(target, "Commencing Duel");

                player.resetAllExceptDueling();
                player.setBusy(true);
                player.setState(PlayerState.DUELING_PLAYER);

                target.resetAllExceptDueling();
                target.setBusy(true);
                target.setState(PlayerState.DUELING_PLAYER);

                if (player.getDuelSetting(3)) {
                    PlayerSprite playerSprite = player.getSprite();
                    Appearance playerAppearance = playerSprite.getAppearance();
                    for (InvItem item : player.getInventory().getItems()) {
                        if (item.isWielded()) {
                            item.setWielded(false);
                            playerSprite.setWornItem(item.getWieldableDef().getWieldPos(),
                                    playerAppearance.getSprite(item.getWieldableDef().getWieldPos()));
                        }
                    }
                    Packets.sendSound(player, "click");
                    Packets.sendInventory(player);
                    Packets.sendEquipmentStats(player);

                    PlayerSprite targetSprite = target.getSprite();
                    Appearance targetAppearance = targetSprite.getAppearance();
                    for (InvItem item : target.getInventory().getItems()) {
                        if (item.isWielded()) {
                            item.setWielded(false);
                            targetSprite.setWornItem(item.getWieldableDef().getWieldPos(),
                                    targetAppearance.getSprite(item.getWieldableDef().getWieldPos()));
                        }
                    }
                    Packets.sendSound(target, "click");
                    Packets.sendInventory(target);
                    Packets.sendEquipmentStats(target);

                }

                if (player.getDuelSetting(2)) {
                    for (int x = 0; x < 14; x++) {
                        if (player.isPrayerActivated(x)) {
                            player.removePrayerDrain(x);
                            player.setPrayer(x, false);
                        }
                        if (target.isPrayerActivated(x)) {
                            target.removePrayerDrain(x);
                            target.setPrayer(x, false);
                        }
                    }
                    Packets.sendPrayers(player);
                    Packets.sendPrayers(target);
                }

//                player.setFollowing(target);
//                WalkToMobEvent walking = new WalkToMobEvent(player, target, 1) {
//                    public void arrived() {
//                        world.getDelayedEventHandler().add(new SingleEvent(owner, 1000) {
//                            public void action() {
//                                Player affectedPlayer = (Player) affectedMob;
//                                owner.getMovement().resetPath();
//                                if (!owner.nextTo(affectedPlayer)) {
//                                    unsetOptions(owner);
//                                    unsetOptions(affectedPlayer);
//                                    return;
//                                }
//                                affectedPlayer.resetPath();
//
//                                owner.resetAllExceptDueling();
//                                affectedPlayer.resetAllExceptDueling();
//
//                                owner.setLocation(affectedPlayer.getLocation(), true);
//                                for (Player p : owner.getViewArea().getPlayersInView()) {
//                                    p.removeWatchedPlayer(owner);
//                                }
//
//                                owner.setSprite(9);
//                                owner.setOpponent(affectedMob);
//                                owner.setCombatTimer();
//
//                                affectedPlayer.setSprite(8);
//                                affectedPlayer.setOpponent(owner);
//                                affectedPlayer.setCombatTimer();
//
//                                Player attacker, opponent;
//                                if (owner.getCombatLevel() > affectedPlayer.getCombatLevel()) {
//                                    attacker = affectedPlayer;
//                                    opponent = owner;
//                                } else if (affectedPlayer.getCombatLevel() > owner.getCombatLevel()) {
//                                    attacker = owner;
//                                    opponent = affectedPlayer;
//                                } else if (DataConversions.random(0, 1) == 1) {
//                                    attacker = owner;
//                                    opponent = affectedPlayer;
//                                } else {
//                                    attacker = affectedPlayer;
//                                    opponent = owner;
//                                }
//                                DuelEvent dueling = new DuelEvent(attacker, opponent);
//                                dueling.setLastRun(0);
//                                world.getDelayedEventHandler().add(dueling);
//                            }
//                        });
//                    }
//
//                    public void failed() {
//                        Player affectedPlayer = (Player) affectedMob;
//                        owner.getActionSender().sendMessage("Error walking to " + affectedPlayer.getUsername() + " (known bug)");
//                        affectedPlayer.getActionSender().sendMessage("Error walking to " + owner.getUsername() + " (known bug)");
//                        unsetOptions(owner);
//                        unsetOptions(affectedPlayer);
//                        owner.setBusy(false);
//                        affectedPlayer.setBusy(false);
//                    }
//                };
//                walking.setLastRun(System.currentTimeMillis() + 500);
//                world.getDelayedEventHandler().add(walking);
            }
            return true;
        }
    }

    public static final class DuelDeclineMessageHandler extends MessageHandler<DuelDeclineMessage> {
        @Override
        public boolean handle(Session session, Player player, DuelDeclineMessage message) {
            Player target = player.getWishToDuel();
            if (target == null || busy(target) ||
                    !player.isDueling() ||
                    !target.isDueling()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetDueling();
                target.resetDueling();
                return true;
            }

            Packets.sendMessage(target, player.getCredentials().getUsername() + " has declined the duel.");

            player.resetDueling();
            target.resetDueling();
            return true;
        }
    }

    public static final class DuelInformationMessageHandler extends MessageHandler<DuelInformationMessage> {
        @Override
        public boolean handle(Session session, Player player, DuelInformationMessage message) {
            Player target = player.getWishToDuel();
            if (target == null || busy(target) ||
                    !player.isDueling() ||
                    !target.isDueling() ||
                    (player.isDuelOfferAccepted() && target.isDuelOfferAccepted()) ||
                    player.isDuelConfirmAccepted() ||
                    target.isDuelConfirmAccepted()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetDueling();
                target.resetDueling();
                return true;
            }

            player.setDuelOfferAccepted(false);
            player.setDuelConfirmAccepted(false);
            target.setDuelOfferAccepted(false);
            target.setDuelConfirmAccepted(false);

            Packets.sendDuelAcceptUpdate(player);
            Packets.sendDuelAcceptUpdate(target);

            player.resetDuelOffer();

            Inventory inventory = player.getInventory();
            for (DuelInformationMessage.DuelItem item : message.getItems()) {
                if (item.getAmount() < 1) {
                    // Cheating?
                    continue;
                }

                int available = inventory.countById(item.getItemId());
                if (item.getAmount() > available) {
                    // Cheating?
                    continue;
                }

                player.addToDuelOffer(new InvItem(item.getItemId(), item.getAmount()));
            }

            Packets.sendDuelSettingUpdate(player);
            Packets.sendDuelSettingUpdate(target);
            Packets.sendDuelItems(target);

            return true;
        }
    }

    public static final class DuelOptionsMessageHandler extends MessageHandler<DuelOptionsMessage> {
        @Override
        public boolean handle(Session session, Player player, DuelOptionsMessage message) {
            Player target = player.getWishToDuel();
            if (target == null || busy(target) ||
                    !player.isDueling() ||
                    !target.isDueling() ||
                    (player.isDuelOfferAccepted() && target.isDuelOfferAccepted()) ||
                    player.isDuelConfirmAccepted() ||
                    target.isDuelConfirmAccepted()) { // This shouldn't happen
//                player.setSuspiciousPlayer(true);
                player.resetDueling();
                target.resetDueling();
                return true;
            }

            player.setDuelOfferAccepted(false);
            player.setDuelConfirmAccepted(false);
            target.setDuelOfferAccepted(false);
            target.setDuelConfirmAccepted(false);

            Packets.sendDuelAcceptUpdate(player);
            Packets.sendDuelAcceptUpdate(target);

            player.setDuelSetting(Player.DUEL_OPTION_NO_RETREAT_INDEX, message.isRetreatingDisabled());
            player.setDuelSetting(Player.DUEL_OPTION_ALLOW_MAGIC_INDEX, message.isMagicDisabled());
            player.setDuelSetting(Player.DUEL_OPTION_ALLOW_PRAYER_INDEX, message.isPrayerDisabled());
            player.setDuelSetting(Player.DUEL_OPTION_ALLOW_WEAPONS_INDEX, message.areWeaponsDisabled());

            target.setDuelSetting(Player.DUEL_OPTION_NO_RETREAT_INDEX, message.isRetreatingDisabled());
            target.setDuelSetting(Player.DUEL_OPTION_ALLOW_MAGIC_INDEX, message.isMagicDisabled());
            target.setDuelSetting(Player.DUEL_OPTION_ALLOW_PRAYER_INDEX, message.isPrayerDisabled());
            target.setDuelSetting(Player.DUEL_OPTION_ALLOW_WEAPONS_INDEX, message.areWeaponsDisabled());

            Packets.sendDuelSettingUpdate(player);
            Packets.sendDuelSettingUpdate(target);

            return true;
        }
    }

    public static final class DuelRequestMessageHandler extends MessageHandler<DuelRequestMessage> {
        @Override
        public boolean handle(Session session, Player player, DuelRequestMessage message) {
            Player target = World.getInstance().getPlayer(message.getPlayerIndex());
            Point playerLoc = player.getLocation();
            Point targetLoc = target.getLocation();
            if (target == null || !playerLoc.withinRange(targetLoc, 8) ||
                    player.isDueling() || player.tradeDuelThrottling()) {
                player.resetDueling();
                return true;
            }

            Credentials playerCreds = player.getCredentials();
            long playerUsernameHash = playerCreds.getUsernameHash();
            Communication targetCom = target.getCommunication();
            if ((target.getPrivacySetting(Player.PRIVACY_BLOCK_DUEL_REQUESTS_INDEX) &&
                    !targetCom.isFriendsWith(playerUsernameHash)) ||
                    targetCom.isIgnoring(playerUsernameHash)) {
                Packets.sendMessage(player, "This player has duel requests blocked.");
                return true;
            }

            Credentials targetCreds = target.getCredentials();

            player.setWishToDuel(target);
            Packets.sendMessage(player, target.isDueling() ?
                    targetCreds.getUsername() + " is already in a duel" :
                    "Sending duel request");

            Skills playerSkills = player.getSkills();
            Skills targetSkills = target.getSkills();
            Packets.sendMessage(target, playerCreds.getUsername() + " " +
                    Formulae.getLvlDiffColour(targetSkills.getCombatLevel() - playerSkills.getCombatLevel()) +
                    "(level-" + playerSkills.getCombatLevel() + ")@whi@ wishes to duel with you");

            if (!player.isDueling() &&
                    target.getWishToDuel() != null &&
                    target.getWishToDuel().equals(player) &&
                    !target.isDueling()) {
                player.setDueling(true);
                player.getMovement().resetPath();
                player.clearDuelOptions();
                player.resetAllExceptDueling();

                target.setDueling(true);
                target.getMovement().resetPath();
                target.clearDuelOptions();
                target.resetAllExceptDueling();

                Packets.sendDuelWindowOpen(player);
                Packets.sendDuelWindowOpen(target);
            }
            return true;
        }
    }
}
