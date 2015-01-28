package org.moparscape.elysium.entity;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.entity.component.*;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
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
    private final Bank bank = new Bank(this);
    private final Communication communication = new Communication();
    private final Credentials credentials = new Credentials();
    private final Inventory inventory = new Inventory(this);
    private final Session session;
    private final Settings settings = new Settings();
    private final Skills skills = new Skills();
    private final Combat combat = new Combat(this, inventory, skills);
    private final PlayerSprite sprite = new PlayerSprite(this);
    private final Movement movement = new Movement(this, sprite);
    private final Observer observer = new Observer(this, sprite);
    private final UpdateProxy updateProxy = new UpdateProxy(communication, credentials, movement, observer, skills, sprite);
    private int actionCount = 0;
    private boolean busy = false;
    private boolean duelConfirmAccepted = false;
    private List<InvItem> duelOffer = new ArrayList<>(9);
    private boolean duelOfferAccepted = false;
    private boolean[] duelOptions = new boolean[4];
    private boolean isDueling = false;
    private boolean isTrading = false;
    private long lastTradeDuelRequest;
    private boolean loggedIn = false;
    private PlayerState playerState;
    private Region region = null;
    private boolean tradeConfirmAccepted = false;
    private List<InvItem> tradeOffer = new ArrayList<>(9);
    private boolean tradeOfferAccepted = false;
    private Player wishToDuel;
    private Player wishToTrade;

    public Player(Session session) {
        this.session = session;
        this.setLocation(new Point(329, 552));
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

    public Bank getBank() {
        return bank;
    }

    public Combat getCombat() {
        return combat;
    }

    public Communication getCommunication() {
        return communication;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public List<InvItem> getDuelOffer() {
        return duelOffer;
    }

    public boolean getDuelSetting(int i) {
        return duelOptions[i];
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

    public Movement getMovement() {
        return movement;
    }

    public Observer getObserver() {
        return observer;
    }

    public Session getSession() {
        return session;
    }

    public Settings getSettings() {
        return settings;
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

    @Override
    public String toString() {
        return credentials + " (" + getLocation().getX() + ", " + getLocation().getY() + ")";
    }

    public boolean tradeDuelThrottling() {
        long now = Server.getInstance().getHighResolutionTimestamp();
        if (now - lastTradeDuelRequest > 1000) {
            lastTradeDuelRequest = now;
            return false;
        }
        return true;
    }
}
