package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;

public class DamageDefaults {
    GenericEgoDamage.DamageTypes damageType;
    GenericEgoDamage.AttackTypes attackType;

    public DamageDefaults(GenericEgoDamage.DamageTypes damageType, GenericEgoDamage.AttackTypes attackType) {
        this.damageType = damageType;
        this.attackType = attackType;
    }

    public GenericEgoDamage.DamageTypes getDamageType() {
        return damageType;
    }

    public void setDamageType(GenericEgoDamage.DamageTypes damageType) {
        this.damageType = damageType;
    }

    public GenericEgoDamage.AttackTypes getAttackType() {
        return attackType;
    }

    public void setAttackType(GenericEgoDamage.AttackTypes attackType) {
        this.attackType = attackType;
    }
}
