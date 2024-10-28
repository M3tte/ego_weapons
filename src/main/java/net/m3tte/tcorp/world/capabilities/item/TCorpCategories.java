//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum TCorpCategories implements WeaponCategory {
    ZELKOVA_AXE,
    ZELKOVA_MACE,
    DURANDAL,
    RANGA,
    RANGA_DAGGER,
    ALLAS_WORKSHOP,
    ATELIER_REVOLVER,
    MOOK_WORKSHOP,
    ATELIER_SHOTGUN,
    OLD_BOYS,
    WHEELS_INDUSTRY,
    CRYSTAL_ATELIER,
    MIMICRY,
    SOLEMN_LAMENT,
    SUNSHOWER,
    MAGIC_BULLET;

    final int id;

    private TCorpCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }
}
