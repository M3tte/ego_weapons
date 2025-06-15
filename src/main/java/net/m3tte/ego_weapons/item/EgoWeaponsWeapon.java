package net.m3tte.ego_weapons.item;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class EgoWeaponsWeapon extends SwordItem {
    public EgoWeaponsWeapon(IItemTier tier, int dmg, float attackSpeed, Properties props) {
        super(tier, dmg, attackSpeed, props);
    }

    public String getDefaultKillIdentifier() {
        return null;
    }

    public StaticAnimation getDefaultStunAnim(int strength) {
        return EgoWeaponsAnimations.CLASH_STUN_BASIC;
    }
}
