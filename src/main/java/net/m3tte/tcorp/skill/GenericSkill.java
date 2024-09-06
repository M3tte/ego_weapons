package net.m3tte.tcorp.skill;

import yesman.epicfight.skill.SkillCategory;

public enum GenericSkill implements SkillCategory {
    TC_EVADE(false, false, false),
    TC_GUARD(false, false, false);

    boolean shouldSaved;
    boolean shouldSyncronized;
    boolean modifiable;
    int id;

    private GenericSkill(boolean shouldSave, boolean shouldSyncronized, boolean modifiable) {
        this.shouldSaved = shouldSave;
        this.shouldSyncronized = shouldSyncronized;
        this.modifiable = modifiable;
        this.id = SkillCategory.ENUM_MANAGER.assign(this);
    }

    public boolean shouldSaved() {
        return this.shouldSaved;
    }

    public boolean shouldSynchronized() {
        return this.shouldSyncronized;
    }

    public boolean learnable() {
        return this.modifiable;
    }

    public int universalOrdinal() {
        return this.id;
    }
}
