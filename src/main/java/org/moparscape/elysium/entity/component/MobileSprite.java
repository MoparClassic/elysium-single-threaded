package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.MobileEntity;
import org.moparscape.elysium.world.Point;

/**
 * Created by daniel on 18/01/2015.
 */
public class MobileSprite extends AbstractComponent {

    protected final int[][] mobSprites = new int[][]{
            {3, 2, 1},
            {4, -1, 0},
            {5, 6, 7}
    };
    protected MobileEntity owner;
    protected int sprite = 1;
    protected boolean spriteChanged = true;

    public MobileSprite(MobileEntity owner) {
        this.owner = owner;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.spriteChanged = true;
        this.sprite = sprite;
    }

    public void resetSpriteChanged() {
        this.spriteChanged = false;
    }

    public boolean spriteChanged() {
        return spriteChanged;
    }

    public void updateSprite(Point newLocation) {
        Point curLoc = this.owner.getLocation();

        try {
            int xIndex = curLoc.getX() - newLocation.getX() + 1;
            int yIndex = curLoc.getY() - newLocation.getY() + 1;
            setSprite(mobSprites[xIndex][yIndex]);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
