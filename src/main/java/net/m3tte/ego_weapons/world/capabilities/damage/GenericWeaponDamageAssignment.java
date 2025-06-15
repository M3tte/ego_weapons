package net.m3tte.ego_weapons.world.capabilities.damage;

public class GenericWeaponDamageAssignment {
    GenericEgoDamage.DamageTypes damageType = GenericEgoDamage.DamageTypes.RED;
    GenericEgoDamage.AttackTypes attackType = GenericEgoDamage.AttackTypes.BLUNT;

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

    public GenericWeaponDamageAssignment(GenericEgoDamage.DamageTypes damageType, GenericEgoDamage.AttackTypes attackType) {
        this.damageType = damageType;
        this.attackType = attackType;
    }
}
