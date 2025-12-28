//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.world.capabilities.item;

import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum EgoWeaponsCategories implements WeaponCategory {
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
    LIU_FIRE_GAUNTLET,
    FIREFIST_GAUNTLET,
    OEUFI,
    FULLSTOP_REP,
    FULLSTOP_SNIPER,
    STIGMA_WORKSHOP_SWORD,
    HEISHOU_MAO_SWORD,
    JUSTITIA,
    RAT_KNIFE,
    RAT_PIPE,
    MAGIC_BULLET;

    final int id;

    private EgoWeaponsCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    public int universalOrdinal() {
        return this.id;
    }
}
