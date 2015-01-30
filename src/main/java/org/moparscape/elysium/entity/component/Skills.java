package org.moparscape.elysium.entity.component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Skills extends AbstractComponent {

    private static final int SKILL_COUNT = 18;
    private int combatLevel = 3;
    private int fatigue;
    private int lastDamage = 0;
    private int[] skillsCurrentLevel = new int[SKILL_COUNT];
    private int[] skillsExperience = new int[SKILL_COUNT];
    private int[] skillsMaxLevel = new int[SKILL_COUNT];

    public Skills() {
        for (int i = 0; i < SKILL_COUNT; i++) {
            skillsCurrentLevel[i] = 1;
            skillsMaxLevel[i] = 1;
            skillsExperience[i] = 0;
        }
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public int getCurStat(int id) {
        return skillsCurrentLevel[id];
    }

    public int[] getCurStats() {
        return skillsCurrentLevel;
    }

    public int getExp(int id) {
        return skillsExperience[id];
    }

    public int[] getExps() {
        return skillsExperience;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getHits() {
        return skillsCurrentLevel[3];
    }

    public int getLastDamage() {
        return lastDamage;
    }

    public int getMaxHits() {
        return skillsMaxLevel[3];
    }

    public int getMaxStat(int id) {
        return skillsMaxLevel[id];
    }

    public int[] getMaxStats() {
        return skillsMaxLevel;
    }
}
