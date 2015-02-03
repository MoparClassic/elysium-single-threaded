package org.moparscape.elysium.script;

/**
 * Created by daniel on 4/02/2015.
 */
public final class IntegerKey implements Key {

    private final int key;

    public IntegerKey(int keyValue) {
        this.key = keyValue;
    }

    @Override
    public int hashCode() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        IntegerKey other = (IntegerKey) o;
        return this.key == other.key;
    }
}
