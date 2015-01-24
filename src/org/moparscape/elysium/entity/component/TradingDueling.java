package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.PlayerState;
import org.moparscape.elysium.net.Packets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 24/01/2015.
 */
public final class TradingDueling extends AbstractComponent {

    private final Player owner;
    private Player wishToTrade;
    private Player wishToDuel;
    private long lastTradeDuelRequest;

    private List<InvItem> tradeOffer = new ArrayList<>(9);
    private boolean isTrading = false;
    private boolean tradeOfferAccepted = false;
    private boolean tradeConfirmAccepted = false;

    private List<InvItem> duelOffer = new ArrayList<>(9);
    private boolean isDueling = false;
    private boolean duelOfferAccepted = false;
    private boolean duelConfirmAccepted = false;
    private boolean[] duelOptions = new boolean[4];

    public TradingDueling(Player owner) {
        this.owner = owner;
    }

    public void setWishToTrade(Player target) {
        this.wishToTrade = target;
    }

    public Player getWishToTrade() {
        return wishToTrade;
    }

    public Player getWishToDuel() {
        return wishToDuel;
    }

    public boolean isTrading() {
        return isTrading;
    }

    public void setTrading(boolean trading) {
        this.isTrading = trading;
    }

    public void setDueling(boolean dueling) {
        this.isDueling = dueling;
    }

    public void resetAllExceptTrading() {
//        resetAllExceptTradeOrDuel();
        resetDuel();
    }

    public void resetAllExceptDueling() {
//        resetAllExceptTradeOrDuel();
        resetTrade();
    }

    public void resetTrade() {
        Player opponent = getWishToTrade();
        if (opponent != null) {
            opponent.getTradingDueling().resetTrading();
        }
        resetTrading();
    }

    public void resetDuel() {
        Player opponent = getWishToDuel();
        if (opponent != null) {
            opponent.getTradingDueling().resetDueling();
        }
        resetDueling();
    }

    public void resetTrading() {
        if (isTrading) {
            Packets.sendTradeWindowClose(owner);
            owner.setState(PlayerState.IDLE);
        }
        setWishToTrade(null);
        setTrading(false);
        setTradeOfferAccepted(false);
        setTradeConfirmAccepted(false);
        resetTradeOffer();
    }

    public void resetDueling() {
        if (isDueling) {
            Packets.sendDuelWindowClose(owner);
            owner.setState(PlayerState.IDLE);
        }
        setWishToDuel(null);
        setDueling(false);
        setDuelOfferAccepted(false);
        setDuelConfirmAccepted(false);
        resetDuelOffer();
        clearDuelOptions();
    }

    public void setWishToDuel(Player target) {
        this.wishToDuel = target;
    }

    public void setTradeOfferAccepted(boolean accepted) {
        this.tradeOfferAccepted = accepted;
    }

    public void setDuelOfferAccepted(boolean accepted) {
        this.duelOfferAccepted = accepted;
    }

    public void setTradeConfirmAccepted(boolean accepted) {
        this.tradeConfirmAccepted = accepted;
    }

    public boolean isTradeConfirmAccepted() {
        return tradeConfirmAccepted;
    }

    public void setDuelConfirmAccepted(boolean accepted) {
        this.duelConfirmAccepted = accepted;
    }

    public void resetTradeOffer() {
        tradeOffer.clear();
    }

    public void resetDuelOffer() {
        duelOffer.clear();
    }

    public void clearDuelOptions() {
        for (int i = 0; i < 4; i++) {
            duelOptions[i] = false;
        }
    }

    public boolean tradeDuelThrottling() {
        long now = Server.getInstance().getHighResolutionTimestamp();
        if (now - lastTradeDuelRequest > 1000) {
            lastTradeDuelRequest = now;
            return false;
        }
        return true;
    }

    public void addToTradeOffer(InvItem item) {
        tradeOffer.add(item);
    }

    public void addToDuelOffer(InvItem item) {
        duelOffer.add(item);
    }

    public List<InvItem> getTradeOffer() {
        return tradeOffer;
    }

    public List<InvItem> getDuelOffer() {
        return duelOffer;
    }

    public boolean isTradeOfferAccepted() {
        return tradeOfferAccepted;
    }

    public boolean isDuelOfferAccepted() {
        return duelOfferAccepted;
    }
}
