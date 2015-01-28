package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.def.PrayerDef;
import org.moparscape.elysium.entity.DefinitionHandler;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.task.timed.PrayerDrainTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Combat extends AbstractComponent {

    private final Player owner;
    private final Inventory inventory;
    private final Skills skills;
    private final PrayerDrainTask drainer = new PrayerDrainTask();
    private int combatStyle = 0;
    private int drainRate = 0;
    private boolean[] activatedPrayers = new boolean[14];

    public Combat(Player owner, Inventory inventory, Skills skills) {
        this.owner = owner;
        this.inventory = inventory;
        this.skills = skills;
    }

    public int getCombatStyle() {
        return combatStyle;
    }

    public void setCombatStyle(int style) {
        this.combatStyle = style;
    }

    public boolean isPrayerActivated(int pID) {
        return activatedPrayers[pID];
    }

    public void setPrayer(int pID, boolean b) {
        activatedPrayers[pID] = b;
    }

    public void addPrayerDrain(int prayerID) {
        PrayerDef prayer = DefinitionHandler.getPrayerDef(prayerID);
        drainRate += prayer.getDrainRate();
        drainer.setDelay((int) (240000 / drainRate));
    }

    public void removePrayerDrain(int prayerID) {
        PrayerDef prayer = DefinitionHandler.getPrayerDef(prayerID);
        drainRate -= prayer.getDrainRate();
        if (drainRate <= 0) {
            drainRate = 0;
            drainer.setDelay(Integer.MAX_VALUE);
        } else {
            drainer.setDelay((int) (240000 / drainRate));
        }
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
