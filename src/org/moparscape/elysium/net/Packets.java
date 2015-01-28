package org.moparscape.elysium.net;

import io.netty.channel.ChannelFuture;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Combat;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.entity.component.Skills;
import org.moparscape.elysium.util.Formulae;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Packets {

//    public static ChannelFuture hideBank(Player player) {
//        PacketBuilder pb = new PacketBuilder(0);
//        pb.setId(171);
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture hideMenu(Player player) {
//        PacketBuilder pb = new PacketBuilder(0);
//        pb.setId(127);
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture hideShop(Player player) {
//        PacketBuilder pb = new PacketBuilder(0);
//        pb.setId(220);
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture sendAlert(Player player, String message, boolean big) {
//        PacketBuilder pb = new PacketBuilder(message.length());
//        pb.setId(big ? 64 : 148);
//        pb.writeBytes(message.getBytes());
//        return player.getSession().write(pb.toPacket());
//    }

    public static void sendAppearanceScreen(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 207);
        pb.finalisePacket();
    }

    public static void sendCantLogout(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 136);
        pb.finalisePacket();
    }

//    public static ChannelFuture sendCombatStyle(Player player) {
//        Combat combat = player.getCombat();
//        PacketBuilder pb = new PacketBuilder(1);
//        pb.setId(129);
//        pb.writeByte(combat.getCombatStyle()); // Combat style
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture sendDied(Player player) {
//        PacketBuilder pb = new PacketBuilder(0);
//        pb.setId(11);
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture showShop(Player player, Shop shop) {
//        throw new UnsupportedOperationException();
//    }

    public static void sendDuelAccept(Player player) {
        Player duelPartner = player.getWishToDuel();
        if (duelPartner == null) return;

        Session s = player.getSession();
        List<InvItem> playerOffer = player.getDuelOffer();
        List<InvItem> duelPartnerOffer = duelPartner.getDuelOffer();

        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 147);
        pb.writeLong(duelPartner.getCredentials().getUsernameHash());
        pb.writeByte(duelPartnerOffer.size());
        for (InvItem item : duelPartnerOffer) {
            pb.writeShort(item.getItemId());
            pb.writeInt(item.getAmount());
        }

        pb.writeByte(playerOffer.size());
        for (InvItem item : playerOffer) {
            pb.writeShort(item.getItemId());
            pb.writeInt(item.getAmount());
        }

        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_NO_RETREAT_INDEX) ? 1 : 0); // Can't retreat.
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_ALLOW_MAGIC_INDEX) ? 1 : 0); // Allow magic.
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_ALLOW_PRAYER_INDEX) ? 1 : 0); // Allow prayer.
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_ALLOW_WEAPONS_INDEX) ? 1 : 0); // Allow weapons.

        pb.finalisePacket();
    }

    public static void sendDuelAcceptUpdate(Player player) {
        Player duelPartner = player.getWishToDuel();
        if (duelPartner == null) return;

        Session s = player.getSession();
        PacketBuilder one = new PacketBuilder(s.getByteBuf(), 97);
        one.writeByte(player.isDuelOfferAccepted() ? 1 : 0);
        one.finalisePacket();

        PacketBuilder two = new PacketBuilder(s.getByteBuf(), 65);
        two.writeByte(duelPartner.isDuelOfferAccepted() ? 1 : 0);
        two.finalisePacket();
    }

    public static void sendDuelItems(Player player) {
        Player duelPartner = player.getWishToDuel();
        if (duelPartner == null) return;

        List<InvItem> itemsOffered = duelPartner.getDuelOffer();
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 63);
        pb.writeByte(itemsOffered.size());
        for (InvItem item : itemsOffered) {
            pb.writeShort(item.getItemId());
            pb.writeInt(item.getAmount());
        }
        pb.finalisePacket();
    }

    public static void sendDuelSettingUpdate(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 198);
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_NO_RETREAT_INDEX) ? 1 : 0);
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_ALLOW_MAGIC_INDEX) ? 1 : 0);
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_ALLOW_PRAYER_INDEX) ? 1 : 0);
        pb.writeByte(player.getDuelSetting(Player.DUEL_OPTION_ALLOW_WEAPONS_INDEX) ? 1 : 0);
        pb.finalisePacket();
    }

    public static void sendDuelWindowClose(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 160);
        pb.finalisePacket();
    }

    public static void sendDuelWindowOpen(Player player) {
        Player duelPartner = player.getWishToDuel();
        if (duelPartner == null) return;

        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 229);
        pb.writeShort(duelPartner.getIndex());
        pb.finalisePacket();
    }

    public static void sendFatigue(Player player) {
        Session s = player.getSession();
        Skills skills = player.getSkills();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 126);
        pb.writeShort(skills.getFatigue());
        pb.finalisePacket();
    }

//    public static ChannelFuture sendFriendList(Player player) {
//        Communication com = player.getCommunication();
//        List<Long> friendList = com.getFriendList();
//        int size = friendList.size();
//
//        PacketBuilder pb = new PacketBuilder(1 + (size * 9));
//        pb.setId(249);
//        pb.writeByte(size);
//        for (long hash : friendList) {
//            pb.writeLong(hash);
//            pb.writeByte(99); // The world they're on
//        }
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture sendFriendUpdate(Player player, long usernameHash, int world) {
//        PacketBuilder pb = new PacketBuilder(9);
//        pb.setId(25);
//        pb.writeLong(usernameHash);
//        pb.writeByte(99); // World number
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture sendGameSettings(Player player) {
//        throw new UnsupportedOperationException();
//    }

//    public static ChannelFuture sendIgnoreList(Player player) {
//        Communication com = player.getCommunication();
//        List<Long> ignoreList = com.getIgnoreList();
//        int size = ignoreList.size();
//
//        PacketBuilder pb = new PacketBuilder(1 + (size * 8));
//        pb.writeByte(size);
//        for (long hash : ignoreList) {
//            pb.writeLong(hash);
//        }
//        return player.getSession().write(pb.toPacket());
//    }

    public static void sendInventory(Player player) {
        Session s = player.getSession();
        Inventory inventory = player.getInventory();

        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 114);
        pb.writeByte(inventory.size());
        for (InvItem item : inventory.getItems()) {
            pb.writeShort(item.getItemId() + (item.isWielded() ? 32768 : 0));
            if (item.getDef().isStackable()) {
                pb.writeInt(item.getAmount());
            }
        }
        pb.finalisePacket();
    }

    public static void sendLoginBox(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 248);
        pb.writeShort(0); // Days since last login
        pb.writeShort(0); // Subscription time left
        pb.writeBytes("127.0.0.1".getBytes()); // Ip address
        pb.finalisePacket();
    }

    public static void sendLoginResponse(Player player, LoginResponse response) {
        Session s = player.getSession();
        s.getByteBuf().writeByte(response.getResponseCode());
    }

    public static void sendLogout(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 222);
        pb.finalisePacket();
    }

//    public static ChannelFuture sendMenu(Player player, String[] options) {
//        PacketBuilder pb = new PacketBuilder();
//        pb.setId(223);
//        pb.writeByte(options.length);
//        for (String s : options) {
//            pb.writeByte(s.length());
//            pb.writeBytes(s.getBytes());
//        }
//        return player.getSession().write(pb.toPacket());
//    }

    public static void sendMessage(Player player, String message) {
        Session s = player.getSession();
        byte[] msg = message.getBytes();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 48);
        pb.writeBytes(msg);
        pb.finalisePacket();
    }

    public static void sendPrayers(Player player) {
        Session s = player.getSession();
        Combat combat = player.getCombat();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 209);
        for (int i = 0; i < 14; i++) {
            pb.writeByte(combat.isPrayerActivated(i) ? 1 : 0);
        }
        pb.finalisePacket();
    }

    public static ChannelFuture sendPrivacySettings(Player player) {
        throw new UnsupportedOperationException();
    }

//    public static ChannelFuture sendPrivateMessage(Player player, long usernameHash, byte[] message) {
//        PacketBuilder pb = new PacketBuilder(8 + message.length);
//        pb.setId(170);
//        pb.writeLong(usernameHash);
//        pb.writeBytes(message);
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture sendScreenshot(Player player) {
//        PacketBuilder pb = new PacketBuilder(0);
//        pb.setId(181);
//        return player.getSession().write(pb.toPacket());
//    }

    public static void sendServerInfo(Player player) {
        Session s = player.getSession();
        String location = "Australia";
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 110);
        pb.writeLong(System.currentTimeMillis()); // Start time
        pb.writeBytes(location.getBytes());
        pb.finalisePacket();
    }

    public static void sendSound(Player player, String soundName) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 11);
        pb.writeBytes(soundName.getBytes());
        pb.finalisePacket();
    }

//    public static ChannelFuture sendStat(Player player, int stat) {
//        Skills skills = player.getSkills();
//        PacketBuilder pb = new PacketBuilder(7);
//        pb.setId(208);
//        pb.writeByte(stat);
//        pb.writeByte(skills.getCurStat(stat));
//        pb.writeByte(skills.getMaxStat(stat));
//        pb.writeInt(skills.getExp(stat));
//        return player.getSession().write(pb.toPacket());
//    }

    public static void sendStats(Player player) {
        Session s = player.getSession();
        Skills skills = player.getSkills();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 180);

        int[] curStats = skills.getCurStats();
        for (int i = 0; i < curStats.length; i++) {
            pb.writeByte(curStats[i]);
        }

        int[] maxStats = skills.getMaxStats();
        for (int i = 0; i < maxStats.length; i++) {
            pb.writeByte(maxStats[i]);
        }

        int[] exps = skills.getExps();
        for (int i = 0; i < exps.length; i++) {
            pb.writeInt(exps[i]);
        }

        pb.finalisePacket();
    }

//    public static ChannelFuture sendTeleBubble(Player player, int x, int y, boolean grab) {
//        Point loc = player.getLocation();
//        PacketBuilder pb = new PacketBuilder(3);
//        pb.setId(23);
//        pb.writeByte(grab ? 1 : 0);
//        pb.writeByte(x - loc.getX());
//        pb.writeByte(y - loc.getY());
//        return player.getSession().write(pb.toPacket());
//    }

    public static void sendTradeAccept(Player player) {
        Player tradePartner = player.getWishToTrade();
        if (tradePartner == null) return;

        Session s = player.getSession();
        List<InvItem> playerOffer = player.getTradeOffer();
        List<InvItem> tradePartnerOffer = tradePartner.getTradeOffer();

        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 251);
        pb.writeLong(tradePartner.getCredentials().getUsernameHash());
        pb.writeByte(tradePartnerOffer.size());
        for (InvItem item : tradePartnerOffer) {
            pb.writeShort(item.getItemId());
            pb.writeInt(item.getAmount());
        }

        pb.writeByte(playerOffer.size());
        for (InvItem item : playerOffer) {
            pb.writeShort(item.getItemId());
            pb.writeInt(item.getAmount());
        }

        pb.finalisePacket();
    }

    public static void sendTradeAcceptUpdate(Player player) {
        Player tradePartner = player.getWishToTrade();
        if (tradePartner == null) return;

        Session s = player.getSession();
        PacketBuilder one = new PacketBuilder(s.getByteBuf(), 18);
        one.writeByte(player.isTradeOfferAccepted() ? 1 : 0);
        one.finalisePacket();

        PacketBuilder two = new PacketBuilder(s.getByteBuf(), 92);
        two.writeByte(tradePartner.isTradeOfferAccepted() ? 1 : 0);
        two.finalisePacket();
    }

    public static void sendTradeItems(Player player) {
        Player tradePartner = player.getWishToTrade();
        if (tradePartner == null) return;

        List<InvItem> itemsOffered = tradePartner.getTradeOffer();
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 250);
        pb.writeByte(itemsOffered.size());
        for (InvItem item : itemsOffered) {
            pb.writeShort(item.getItemId());
            pb.writeInt(item.getAmount());
        }

        pb.finalisePacket();
    }

    public static void sendTradeWindowClose(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 187);
        pb.finalisePacket();
    }

    public static void sendTradeWindowOpen(Player player) {
        Player target = player.getWishToTrade();
        if (target == null) return;

        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 4);
        pb.writeShort(target.getIndex());
        pb.finalisePacket();
    }

    public static void sendWorldInfo(Player player) {
        Session s = player.getSession();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 131);
        pb.writeShort(player.getIndex());
        pb.writeShort(2304);
        pb.writeShort(1776);
        pb.writeShort(Formulae.getHeight(player.getLocation()));
        pb.writeShort(944);
        pb.finalisePacket();
    }

    public static void sendEquipmentStats(Player player) {
        Session s = player.getSession();
        Combat combat = player.getCombat();
        PacketBuilder pb = new PacketBuilder(s.getByteBuf(), 177);
        pb.writeShort(combat.getArmourPoints());
        pb.writeShort(combat.getWeaponAimPoints());
        pb.writeShort(combat.getWeaponPowerPoints());
        pb.writeShort(combat.getMagicPoints());
        pb.writeShort(combat.getPrayerPoints());
        pb.writeShort(combat.getRangePoints());
        pb.finalisePacket();
    }

    public static ChannelFuture showBank(Player player) {
        throw new UnsupportedOperationException();
    }

//    public static ChannelFuture startShutdown(Player player, int seconds) {
//        PacketBuilder pb = new PacketBuilder(2);
//        pb.setId(172);
//        pb.writeShort((int) (((double) seconds / 32D) * 50));
//        return player.getSession().write(pb.toPacket());
//    }

//    public static ChannelFuture updateBankItem(Player player, int slot, int newId, int amount) {
//        PacketBuilder pb = new PacketBuilder(7);
//        pb.setId(139);
//        pb.writeByte(slot);
//        pb.writeShort(newId);
//        pb.writeInt(amount);
//        return player.getSession().write(pb.toPacket());
//    }

    public static enum LoginResponse {

        UNKNOWN(22),
        SERVER_FULL(10),
        FAILED_PROFILE_DECODE(7),
        CLIENT_UPDATED(4),
        ACCOUNT_DISABLED(6),
        SESSION_REJECTED(5),
        USERNAME_IN_USE(3),
        INVALID_USER_PASS(2),
        SUCCESS(0);

        private final int response;

        LoginResponse(int response) {
            this.response = response;
        }

        public int getResponseCode() {
            return response;
        }
    }
}
