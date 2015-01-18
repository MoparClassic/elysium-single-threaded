package org.moparscape.elysium.entity;

/**
 * @author lothy
 */
public abstract class Entity implements Indexable, Locatable {

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Index: " + String.valueOf(getIndex());
    }
}
