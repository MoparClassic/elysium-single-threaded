package org.moparscape.elysium.entity;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.def.PrayerDef;
import org.moparscape.elysium.entity.component.*;
import org.moparscape.elysium.entity.component.Observer;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.task.timed.PrayerDrainTask;
import org.moparscape.elysium.util.DataConversions;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.Region;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Player extends MobileEntity implements Moveable {

    public static final int COMMUNICATION_MAX_FRIENDS = 100;
    public static final int COMMUNICATION_MAX_IGNORES = 50;
    public static final int DUEL_OPTION_ALLOW_MAGIC_INDEX = 1;
    public static final int DUEL_OPTION_ALLOW_PRAYER_INDEX = 2;
    public static final int DUEL_OPTION_ALLOW_WEAPONS_INDEX = 3;
    public static final int DUEL_OPTION_NO_RETREAT_INDEX = 0;
    public static final int MAX_WORN_ITEMS = 12;
    public static final int PRIVACY_BLOCK_CHAT_MESSAGES_INDEX = 0;
    public static final int PRIVACY_BLOCK_DUEL_REQUESTS_INDEX = 3;
    public static final int PRIVACY_BLOCK_PRIVATE_MESSAGES_INDEX = 1;
    public static final int PRIVACY_BLOCK_TRADE_REQUESTS_INDEX = 2;
    public static final int SETTING_AUTO_SCREENSHOTS_INDEX = 5;
    public static final int SETTING_CAMERA_ANGLE_MODE_INDEX = 0;
    public static final int SETTING_FIGHTMODE_SELECTOR_INDEX = 6;
    public static final int SETTING_HIDE_ROOFS_INDEX = 4;
    public static final int SETTING_MOUSE_BUTTONS_INDEX = 2;
    public static final int SETTING_SOUND_EFFECTS_INDEX = 3;
    public static final int SKILL_COUNT = 18;
    private final Bank bank = new Bank(this);
    private final PrayerDrainTask drainer = new PrayerDrainTask();
    private final Set<Long> friends = new HashSet<>();
    private final Set<Long> ignores = new HashSet<>();
    private final Inventory inventory = new Inventory(this);
    private final Queue<ChatMessage> messages = new LinkedList<>();
    private final List<ChatMessage> messagesToDisplay = new ArrayList<>();
    private final Movement movement = new Movement(this);
    private final List<ChatMessage> npcMessagesToDisplay = new ArrayList<>();
    private final Observer observer = new Observer(this);
    private final Session session;
    private final UpdateProxy updateProxy = new UpdateProxy(movement, observer);
    private boolean accessingBank = false;
    private int actionCount = 0;
    private boolean[] activatedPrayers = new boolean[14];
    private Appearance appearance = new Appearance();
    private final int[] wornItems = appearance.getSprites();
    private boolean busy = false;
    private int combatLevel = 3;
    private int combatStyle = 0;
    private int drainRate = 0;
    private boolean duelConfirmAccepted = false;
    private List<InvItem> duelOffer = new ArrayList<>(9);
    private boolean duelOfferAccepted = false;
    private boolean[] duelOptions = new boolean[4];
    private int fatigue;
    private boolean[] gameSettings = new boolean[7];
    private long hash;
    private boolean isDueling = false;
    private boolean isTrading = false;
    private int lastDamage = 0;
    private long lastTradeDuelRequest;
    private boolean loggedIn = false;
    private String password;
    private int playerRights = 0;
    private PlayerState playerState;
    private boolean[] privacySettings = new boolean[4];
    private Region region = null;
    private int[] skillsCurrentLevel = new int[SKILL_COUNT];
    private int[] skillsExperience = new int[SKILL_COUNT];
    private int[] skillsMaxLevel = new int[SKILL_COUNT];
    private boolean skulled = false;
    private boolean tradeConfirmAccepted = false;
    private List<InvItem> tradeOffer = new ArrayList<>(9);
    private boolean tradeOfferAccepted = false;
    private String username;
    private Player wishToDuel;
    private Player wishToTrade;

    public Player() {
        // For unit testing.
        this.session = null;
    }

    public void

    public Player(Session session) {
        this.session = session;
        this.setLocation(new Point(329, 552));

        for (int i = 0; i < SKILL_COUNT; i++) {
            skillsCurrentLevel[i] = 99;
            skillsMaxLevel[i] = 99;
            skillsExperience[i] = 14_000_000;
        }
    }

    public boolean accessingBank() {
        return accessingBank;
    }

    public void addChatMessage(ChatMessage message) {
        messages.add(message);
    }

    public boolean addFriend(long usernameHash) {
        if (!friends.contains(usernameHash) && friends.size() < COMMUNICATION_MAX_FRIENDS) {
            return friends.add(usernameHash);
        }

        return false;
    }

    public boolean addIgnore(long usernameHash) {
        if (!ignores.contains(usernameHash) && ignores.size() < COMMUNICATION_MAX_IGNORES) {
            return ignores.add(usernameHash);
        }

        return false;
    }

    public void addPrayerDrain(int prayerID) {
        PrayerDef prayer = EntityHandler.getPrayerDef(prayerID);
        drainRate += prayer.getDrainRate();
        drainer.setDelay(240000 / drainRate);
    }

    public void addToDuelOffer(InvItem item) {
        duelOffer.add(item);
    }

    public void addToTradeOffer(InvItem item) {
        tradeOffer.add(item);
    }

    public void clearChatLists() {
        clearChatMessagesNeedingDisplayed();
        clearNpcMessagesNeedingDisplayed();
    }

    public void clearChatMessagesNeedingDisplayed() {
        messagesToDisplay.clear();
    }

    public void clearDuelOptions() {
        for (int i = 0; i < 4; i++) {
            duelOptions[i] = false;
        }
    }

    public void clearNpcMessagesNeedingDisplayed() {
        npcMessagesToDisplay.clear();
    }

    public int getActionCount() {
        return actionCount;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance app) {
        appearance = app;
        setAppearanceChanged(true);
    }

    public int getArmourPoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getArmourPoints();
            }
        }
        return Math.max(points, 1);
    }

    public Bank getBank() {
        return bank;
    }

    public List<ChatMessage> getChatMessagesNeedingDisplayed() {
        return messagesToDisplay;
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public int getCombatStyle() {
        return combatStyle;
    }

    public void setCombatStyle(int style) {
        this.combatStyle = style;
    }

    public int getCurStat(int id) {
        return skillsCurrentLevel[id];
    }

    public int[] getCurStats() {
        return skillsCurrentLevel;
    }

    public List<InvItem> getDuelOffer() {
        return duelOffer;
    }

    public boolean getDuelSetting(int i) {
        return duelOptions[i];
    }

    public int getExp(int id) {
        return skillsExperience[id];
    }

    public int[] getExps() {
        return skillsExperience;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public Set<Long> getFriendList() {
        return friends;
    }

    public boolean getGameSetting(int index) {
        return gameSettings[index];
    }

    public boolean[] getGameSettings() {
        return gameSettings;
    }

    public int getHits() {
        return skillsCurrentLevel[3];
    }

    public Set<Long> getIgnoreList() {
        return ignores;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getLastDamage() {
        return lastDamage;
    }

    public Point getLocation() {
        return movement.getLocation();
    }

    @Override
    public void setLocation(Point location) {
        movement.setLocation(location);
    }

    @Override
    public void setLocation(Point location, boolean teleported) {
        movement.setLocation(location, teleported);
    }

    @Override
    public void updateRegion(Region oldRegion, Region newRegion) {
        if (oldRegion != newRegion) {
            if (oldRegion != null) {
                oldRegion.removePlayer(this);
            }

            newRegion.addPlayer(this);
        }
    }

    public int getMagicPoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getMagicPoints();
            }
        }
        return Math.max(points, 1);
    }

    public int getMaxHits() {
        return skillsMaxLevel[3];
    }

    public int getMaxStat(int id) {
        return skillsMaxLevel[id];
    }

    public int[] getMaxStats() {
        return skillsMaxLevel;
    }

    public Movement getMovement() {
        return movement;
    }

    public ChatMessage getNextChatMessage() {
        return messages.poll();
    }

    public List<ChatMessage> getNpcMessagesNeedingDisplayed() {
        return npcMessagesToDisplay;
    }

    public Observer getObserver() {
        return observer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlayerRights() {
        return playerRights;
    }

    public int getPrayerPoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getPrayerPoints();
            }
        }
        return Math.max(points, 1);
    }

    public boolean getPrivacySetting(int index) {
        return privacySettings[index];
    }

    public boolean[] getPrivacySettings() {
        return privacySettings;
    }

    public int getRangePoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getRangePoints();
            }
        }
        return Math.max(points, 1);
    }

    public Session getSession() {
        return session;
    }

    public int[] getSprites() {
        return appearance.getSprites();
    }

    public PlayerState getState() {
        return playerState;
    }

    public void setState(PlayerState state) {
        this.playerState = state;
        this.actionCount++;
    }

    public List<InvItem> getTradeOffer() {
        return tradeOffer;
    }

    public UpdateProxy getUpdateProxy() {
        return updateProxy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.hash = DataConversions.usernameToHash(username);
    }

    public long getUsernameHash() {
        return hash;
    }

    public int getWeaponAimPoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getWeaponAimPoints();
            }
        }
        return Math.max(points, 1);
    }

    public int getWeaponPowerPoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getWeaponPowerPoints();
            }
        }
        return Math.max(points, 1);
    }

    public Player getWishToDuel() {
        return wishToDuel;
    }

    public void setWishToDuel(Player target) {
        this.wishToDuel = target;
    }

    public Player getWishToTrade() {
        return wishToTrade;
    }

    public void setWishToTrade(Player target) {
        this.wishToTrade = target;
    }

    public int[] getWornItems() {
        return wornItems;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    public void informOfChatMessage(ChatMessage message) {
        messagesToDisplay.add(message); // Use offer, it doesn't block
    }

    public void informOfNpcChatMessage(ChatMessage message) {
        npcMessagesToDisplay.add(message);
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isDuelConfirmAccepted() {
        return duelConfirmAccepted;
    }

    public void setDuelConfirmAccepted(boolean accepted) {
        this.duelConfirmAccepted = accepted;
    }

    public boolean isDuelOfferAccepted() {
        return duelOfferAccepted;
    }

    public void setDuelOfferAccepted(boolean accepted) {
        this.duelOfferAccepted = accepted;
    }

    public boolean isDueling() {
        return isDueling;
    }

    public void setDueling(boolean dueling) {
        this.isDueling = dueling;
    }

    public boolean isFriendsWith(long usernameHash) {
        return friends.contains(usernameHash);
    }

    public boolean isIgnoring(long usernameHash) {
        return ignores.contains(usernameHash);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isPrayerActivated(int pID) {
        return activatedPrayers[pID];
    }

    public boolean isSkulled() {
        return skulled;
    }

    public void setSkulled(boolean skulled) {
        this.skulled = skulled;
    }

    public boolean isTradeConfirmAccepted() {
        return tradeConfirmAccepted;
    }

    public void setTradeConfirmAccepted(boolean accepted) {
        this.tradeConfirmAccepted = accepted;
    }

    public boolean isTradeOfferAccepted() {
        return tradeOfferAccepted;
    }

    public void setTradeOfferAccepted(boolean accepted) {
        this.tradeOfferAccepted = accepted;
    }

    public boolean isTrading() {
        return isTrading;
    }

    public void setTrading(boolean trading) {
        this.isTrading = trading;
    }

    public void removeFriend(long usernameHash) {
        friends.remove(usernameHash);
    }

    public void removeIgnore(long usernameHash) {
        ignores.remove(usernameHash);
    }

    public void removePrayerDrain(int prayerID) {
        PrayerDef prayer = EntityHandler.getPrayerDef(prayerID);
        drainRate -= prayer.getDrainRate();
        if (drainRate <= 0) {
            drainRate = 0;
            drainer.setDelay(Integer.MAX_VALUE);
        } else {
            drainer.setDelay(240000 / drainRate);
        }
    }

    public void resetAllExceptDueling() {
//        resetAllExceptTradeOrDuel();
        resetTrade();
    }

    public void resetAllExceptTrading() {
//        resetAllExceptTradeOrDuel();
        resetDuel();
    }

    public void resetBank() {
        setAccessingBank(false);
        Packets.hideBank(this);
    }

    public void resetDuel() {
        Player opponent = getWishToDuel();
        if (opponent != null) {
            opponent.resetDueling();
        }
        resetDueling();
    }

    public void resetDuelOffer() {
        duelOffer.clear();
    }

    public void resetDueling() {
        if (isDueling) {
            Packets.sendDuelWindowClose(this);
            setState(PlayerState.IDLE);
        }
        setWishToDuel(null);
        setDueling(false);
        setDuelOfferAccepted(false);
        setDuelConfirmAccepted(false);
        resetDuelOffer();
        clearDuelOptions();
    }

    public void resetTrade() {
        Player opponent = getWishToTrade();
        if (opponent != null) {
            opponent.resetTrading();
        }
        resetTrading();
    }

    public void resetTradeOffer() {
        tradeOffer.clear();
    }

    public void resetTrading() {
        if (isTrading) {
            Packets.sendTradeWindowClose(this);
            setState(PlayerState.IDLE);
        }
        setWishToTrade(null);
        setTrading(false);
        setTradeOfferAccepted(false);
        setTradeConfirmAccepted(false);
        resetTradeOffer();
    }

    public void setAccessingBank(boolean b) {
        accessingBank = b;
    }

    public void setDuelSetting(int i, boolean b) {
        duelOptions[i] = b;
    }

    public void setPrayer(int pID, boolean b) {
        activatedPrayers[pID] = b;
    }

    public void setWornItem(int index, int itemId) {
        wornItems[index] = itemId;
        setAppearanceChanged(true);
    }

    @Override
    public String toString() {
        return getUsername() + " (" + getLocation().getX() + ", " + getLocation().getY() + ")";
    }

    public boolean tradeDuelThrottling() {
        long now = Server.getInstance().getHighResolutionTimestamp();
        if (now - lastTradeDuelRequest > 1000) {
            lastTradeDuelRequest = now;
            return false;
        }
        return true;
    }

    public void updateCommunicationSetting(int index, boolean flag) {
        privacySettings[index] = flag;
    }

    public void updateCommunicationSettings(boolean blockChatMessages, boolean blockPrivateMessages,
                                            boolean blockTradeRequests, boolean blockDuelRequests) {
        privacySettings[PRIVACY_BLOCK_CHAT_MESSAGES_INDEX] = blockChatMessages;
        privacySettings[PRIVACY_BLOCK_PRIVATE_MESSAGES_INDEX] = blockPrivateMessages;
        privacySettings[PRIVACY_BLOCK_TRADE_REQUESTS_INDEX] = blockTradeRequests;
        privacySettings[PRIVACY_BLOCK_DUEL_REQUESTS_INDEX] = blockDuelRequests;
    }

    public void updateGameSetting(int index, boolean flag) {
        gameSettings[index] = flag;
    }
}
