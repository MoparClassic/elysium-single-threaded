package org.moparscape.elysium.entity;

import org.moparscape.elysium.def.ItemDef;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class InvItem {

    private final int id;
    private int amount;
    private boolean wielded = false;

    public InvItem(int itemId, int amount) {
        this.id = itemId;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ItemDef getDef() {
        return DefinitionHandler.getItemDef(id);
    }

    public int getItemId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        InvItem i = (InvItem) o;
        return this.id == i.id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Amount: " + amount;
    }

    public boolean isWielded() {
        return wielded;
    }

    public void setWielded(boolean wield) {
        wielded = wield;
    }
}
