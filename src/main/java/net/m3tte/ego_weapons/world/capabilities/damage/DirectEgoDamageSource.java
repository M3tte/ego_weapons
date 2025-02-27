package net.m3tte.ego_weapons.world.capabilities.damage;

import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.EpicFightDamageSource;

public class DirectEgoDamageSource extends EpicFightDamageSource implements GenericEgoDamage {

    private final AttackTypes attackType;
    private final DamageTypes damageType;


    public DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, AttackTypes attackType, DamageTypes damageType) {
        super(damageTypeIn, damageSourceEntityIn, stunType, animation);
        this.attackType = attackType;
        this.damageType = damageType;
    }

    public DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, Hand hand, AttackTypes attackType, DamageTypes damageType) {
        super(damageTypeIn, damageSourceEntityIn, stunType, animation, hand);
        this.attackType = attackType;
        this.damageType = damageType;
    }

    public DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, float impact, float armorNegation, AttackTypes attackType, DamageTypes damageType) {
        super(damageTypeIn, damageSourceEntityIn, stunType, impact, armorNegation);
        this.attackType = attackType;
        this.damageType = damageType;
    }

    public DirectEgoDamageSource(EpicFightDamageSource oldSource, AttackTypes attackType, DamageTypes damageType, StaticAnimation animation, Hand hand, boolean consumesStatus) {
        super(oldSource.getType(), oldSource.getEntity(), oldSource.getStunType(), animation, hand);
        this.attackType = attackType;
        this.damageType = damageType;
        this.status = consumesStatus;
    }



    @Override
    public String toString() {
        return "DirectEgoDamageSource{" +
                "attackType=" + attackType +
                ", damageType=" + damageType +
                '}';
    }

    @Override
    public AttackTypes getAttackType() {
        return this.attackType;
    }

    @Override
    public DamageTypes getDamageType() {
        return this.damageType;
    }

    private float multiplier = 1;

    @Override
    public float getResistanceMult() {
        return this.multiplier;
    }

    @Override
    public void setResistanceMult(float mult) {
        this.multiplier = mult;
    }

    private float bonusMult = 1;

    @Override
    public float getBonusMult() {
        return this.bonusMult;
    }

    @Override
    public void setBonusMult(float mult) {
        this.bonusMult = mult;
    }


    private boolean crit = false;

    @Override
    public boolean getCrit() {
        return this.crit;
    }

    @Override
    public void setCrit(boolean crit) {
        this.crit = crit;
    }


    private boolean status = true;

    @Override
    public boolean getConsumesStatus() {
        return this.status;
    }

    @Override
    public void setConsumesStatus(boolean status) {
        this.status = status;
    }

}
