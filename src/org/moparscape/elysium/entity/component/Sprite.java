package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.world.Point;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Sprite extends AbstractComponent {

    private static final int MAX_WORN_ITEMS = 12;
    private final int[][] mobSprites = new int[][]{
            {3, 2, 1},
            {4, -1, 0},
            {5, 6, 7}
    };
    private Appearance appearance = new Appearance();
    private final int[] wornItems = appearance.getSprites();
    private boolean appearanceChanged = true;
    private int appearanceId = 0;
    private Player owner;
    private boolean skulled = false;
    private int sprite = 1;
    private boolean spriteChanged = true;

    public Sprite(Player owner) {
        this.owner = owner;
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

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.spriteChanged = true;
        this.sprite = sprite;
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

    public void resetSpriteChanged() {
        this.spriteChanged = false;
    }

    public void setAppearanceChanged(boolean changed) {
        this.appearanceChanged = changed;
    }

    public void setWornItem(int index, int itemId) {
        wornItems[index] = itemId;
    }

    public boolean spriteChanged() {
        return spriteChanged;
    }

    public void updateAppearanceId() {
        if (appearanceChanged) {
            appearanceId++;
        }
    }

    public void updateSprite(Point newLocation) {
        Point curLoc = this.owner.getLocation();
        try {
            int xIndex = curLoc.getX() - newLocation.getX() + 1;
            int yIndex = curLoc.getY() - newLocation.getY() + 1;
            setSprite(mobSprites[xIndex][yIndex]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
