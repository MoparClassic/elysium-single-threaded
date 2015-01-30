package org.moparscape.elysium.def;

public class ThievableNPCDef extends EntityDef {

    private int[] loot;
    private int requiredThievingLevel;

    public ThievableNPCDef() {
    }

    public ThievableNPCDef(int[] loot, int reqLevel) {
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
