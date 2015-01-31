package org.moparscape.elysium.script;

/**
 * Created by daniel on 31/01/2015.
 */
final class ItemOnItemKey implements Key {

    private final int key;

    public ItemOnItemKey(int itemIdA, int itemIdB) {
        this.key = (Math.max(itemIdA, itemIdB) << 16) | Math.min(itemIdA, itemIdB);
    }

    @Override
    public int hashCode() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        ItemOnItemKey other = (ItemOnItemKey) o;
        return this.key == other.key;
    }
}
