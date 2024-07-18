package net.m3tte.tcorp.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.Style;

public enum TCorpStyles implements Style {

    FURIOSO(false),
    KALI(false),
    KALI_EGO(false);


    @Override
    public boolean canUseOffhand() {
        return false;
    }

    @Override
    public int universalOrdinal() {
        return 0;
    }

    final boolean canUseOffhand;
    final int id;

    private TCorpStyles(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }
}
