package org.moparscape.elysium.entity;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.def.PrayerDef;
import org.moparscape.elysium.entity.component.*;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.task.timed.PrayerDrainTask;
import org.moparscape.elysium.util.DataConversions;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Player extends MobileEntity implements Moveable {

    public static final int DUEL_OPTION_ALLOW_MAGIC_INDEX = 1;
    public static final int DUEL_OPTION_ALLOW_PRAYER_INDEX = 2;
    public static final int DUEL_OPTION_ALLOW_WEAPONS_INDEX = 3;
    public static final int DUEL_OPTION_NO_RETREAT_INDEX = 0;
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
    private final Bank bank = new Bank(this);
    private final Communication communication = new Communication();
    private final PrayerDrainTask drainer = new PrayerDrainTask();
    private final Inventory inventory = new Inventory(this);
    private final Session session;
    private final Skills skills = new Skills();
    private final PlayerSprite sprite = new PlayerSprite(this);
    private final Movement movement = new Movement(this, sprite);
    private final Observer observer = new Observer(this, sprite);
    private final UpdateProxy updateProxy = new UpdateProxy(communication, movement, observer, skills, sprite);
    private int actionCount = 0;
    private boolean[] activatedPrayers = new boolean[14];
    private boolean busy = false;
    private int combatStyle = 0;
    private int drainRate = 0;
    private boolean duelConfirmAccepted = false;
    private List<InvItem> duelOffer = new ArrayList<>(9);
    private boolean duelOfferAccepted = false;
    private boolean[] duelOptions = new boolean[4];
    private boolean[] gameSettings = new boolean[7];
    private long hash;
    private boolean isDueling = false;
    private boolean isTrading = false;
    private long lastTradeDuelRequest;
    private boolean loggedIn = false;
    private String password;
    private PlayerState playerState;
    private boolean[] privacySettings = new boolean[4];
    private Region region = null;
    private boolean tradeConfirmAccepted = false;
    private List<InvItem> tradeOffer = new ArrayList<>(9);
    private boolean tradeOfferAccepted = false;
    private String username;
    private Player wishToDuel;
    private Player wishToTrade;

    public Player(Session session) {
        this.session = session;
        this.setLocation(new Point(329, 552));
    }

    public void addPrayerDrain(int prayerID) {
        PrayerDef prayer = DefinitionHandler.getPrayerDef(prayerID);
        drainRate += prayer.getDrainRate();
        drainer.setDelay(240000 / drainRate);
    }

    public void addToDuelOffer(InvItem item) {
        duelOffer.add(item);
    }

    public void addToTradeOffer(InvItem item) {
        tradeOffer.add(item);
    }

    public void clearDuelOptions() {
        for (int i = 0; i < 4; i++) {
            duelOptions[i] = false;
        }
    }

    public int getActionCount() {
        return actionCount;
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

    public int getCombatStyle() {
        return combatStyle;
    }

    public void setCombatStyle(int style) {
        this.combatStyle = style;
    }

    public Communication getCommunication() {
        return communication;
    }

    public List<InvItem> getDuelOffer() {
        return duelOffer;
    }

    public boolean getDuelSetting(int i) {
        return duelOptions[i];
    }

    public boolean getGameSetting(int index) {
        return gameSettings[index];
    }

    public boolean[] getGameSettings() {
        return gameSettings;
    }

    public Inventory getInventory() {
        return inventory;
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

    public Movement getMovement() {
        return movement;
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

    public Skills getSkills() {
        return skills;
    }

    public PlayerSprite getSprite() {
        return sprite;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isPrayerActivated(int pID) {
        return activatedPrayers[pID];
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

    public void removePrayerDrain(int prayerID) {
        PrayerDef prayer = DefinitionHandler.getPrayerDef(prayerID);
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

    public void setDuelSetting(int i, boolean b) {
        duelOptions[i] = b;
    }

    public void setPrayer(int pID, boolean b) {
        activatedPrayers[pID] = b;
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
