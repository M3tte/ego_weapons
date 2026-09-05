package net.m3tte.ego_weapons.skill;

public enum ExtendedBlockTypes {
    LIG_BREAK(1),
    MED_BREAK(0),
    HVY_BREAK(-1),
    LIG_BLOCK(1),
    MED_BLOCK(0),
    HVY_BLOCK(-1),
    LIG_PARRY(1),
    MED_PARRY(0),
    HVY_PARRY(-1);

    ExtendedBlockTypes(int unresolvedOffset) {
        this.unresolvedOffset = unresolvedOffset;
    }

    private int unresolvedOffset = 0;


    public int unresolvedOffset() {
        return this.unresolvedOffset;
    }
}
