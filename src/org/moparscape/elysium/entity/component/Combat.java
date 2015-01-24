package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Combat extends AbstractComponent {

    private final Player owner;
    private final Inventory inventory;
    private int combatStyle = 0;

    public Combat(Player owner, Inventory inventory) {
        this.owner = owner;
        this.inventory = inventory;
    }

    public int getCombatStyle() {
        return combatStyle;
    }

    public void setCombatStyle(int style) {
        this.combatStyle = style;
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

    public int getMagicPoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getMagicPoints();
            }
        }
        return Math.max(points, 1);
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

    public int getRangePoints() {
        int points = 1;
        for (InvItem item : inventory.getItems()) {
            if (item.isWielded()) {
                points += item.getWieldableDef().getRangePoints();
            }
        }
        return Math.max(points, 1);
    }
}
