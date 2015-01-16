package org.moparscape.elysium.entity;

import org.moparscape.elysium.world.Point;

/**
 * @author lothy
 */
public class Entity implements Indexable, Locatable {

    private int index;
    private Point location = null;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Index: " + String.valueOf(getIndex());
    }
}
