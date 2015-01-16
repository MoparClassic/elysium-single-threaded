package org.moparscape.elysium.entity.component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Skills extends AbstractComponent {

    private static final int SKILL_COUNT = 18;
    private int combatLevel = 3;
    private int[] curStat = new int[SKILL_COUNT];
    private int[] exp = new int[SKILL_COUNT];
    private int fatigue;
    private int lastDamage = 0;
    private int[] maxStat = new int[SKILL_COUNT];

    public Skills() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            curStat[i] = 99;
            maxStat[i] = 99;
            exp[i] = 14000000;
        }
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public int getCurStat(int id) {
        return curStat[id];
    }

    public int[] getCurStats() {
        return curStat;
    }

    public int getExp(int id) {
        return exp[id];
    }

    public int[] getExps() {
        return exp;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getHits() {
        return curStat[3];
    }

    public int getLastDamage() {
        return lastDamage;
    }

    public int getMaxHits() {
        return maxStat[3];
    }

    public int getMaxStat(int id) {
        return maxStat[id];
    }

    public int[] getMaxStats() {
        return maxStat;
    }
}
