package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PlayerSprite extends MobileSprite {

    private static final int MAX_WORN_ITEMS = 12;

    private Appearance appearance = new Appearance();
    private final int[] wornItems = appearance.getSprites();
    private boolean appearanceChanged = true;
    private int appearanceId = 0;
    private boolean skulled = false;

    public PlayerSprite(Player owner) {
        super(owner);
    }

    public boolean appearanceChanged() {
        return appearanceChanged;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance app) {
        appearance = app;
        setAppearanceChanged(true);
    }

    public int getAppearanceId() {
        return appearanceId;
    }

    public int[] getSprites() {
        return appearance.getSprites();
    }

    public int[] getWornItems() {
        return wornItems;
    }

    public boolean isSkulled() {
        return skulled;
    }

    public void setSkulled(boolean skulled) {
        this.skulled = skulled;
    }

    public void setAppearanceChanged(boolean changed) {
        this.appearanceChanged = changed;
    }

    public void setWornItem(int index, int itemId) {
        wornItems[index] = itemId;
        appearanceChanged = true;
    }

    public void updateAppearanceId() {
        if (appearanceChanged) {
            appearanceId++;
        }
    }
}
