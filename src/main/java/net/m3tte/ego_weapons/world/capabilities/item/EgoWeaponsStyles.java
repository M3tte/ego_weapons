package net.m3tte.ego_weapons.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.Style;

public enum EgoWeaponsStyles implements Style {

    FURIOSO(false),
    KALI(false),
    LEFT_HANDED(true),
    RIGHT_HANDED(true),
    DUAL_WIELDED(false),
    KALI_EGO(false);


    @Override
    public boolean canUseOffhand() {
        return canUseOffhand;
    }

    @Override
    public int universalOrdinal() {
        return 0;
    }

    final boolean canUseOffhand;
    final int id;

    private EgoWeaponsStyles(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }
}
