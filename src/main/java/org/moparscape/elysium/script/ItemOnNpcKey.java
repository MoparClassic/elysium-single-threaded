package org.moparscape.elysium.script;

/**
 * Created by daniel on 31/01/2015.
 */
final class ItemOnNpcKey implements Key {

    private final int key;

    public ItemOnNpcKey(int itemId, int npcId) {
        this.key = (itemId << 16) | npcId;
    }

    @Override
    public int hashCode() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        ItemOnNpcKey other = (ItemOnNpcKey) o;
        return this.key == other.key;
    }
}
