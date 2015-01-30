package org.moparscape.elysium.def;

public class ThievableObjectDef extends EntityDef {

    private int[] loot;
    private int requiredThievingLevel;

    public ThievableObjectDef(int[] loot, int reqLevel) {
        this.loot = loot;
        this.requiredThievingLevel = reqLevel;
    }

    public int[] getLoot() {
        return loot;
    }

    public int getRequiredThievingLevel() {
        return requiredThievingLevel;
    }
}
