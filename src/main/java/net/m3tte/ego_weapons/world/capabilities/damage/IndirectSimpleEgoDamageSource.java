package net.m3tte.ego_weapons.world.capabilities.damage;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import org.jetbrains.annotations.Nullable;

public class IndirectSimpleEgoDamageSource extends IndirectEntityDamageSource implements GenericEgoDamage {

    private final AttackTypes attackType;
    private final DamageTypes damageType;



    public IndirectSimpleEgoDamageSource(String msgid, Entity entity, @Nullable Entity owner, AttackTypes attackType, DamageTypes damageType) {
        super(msgid, entity, owner);
        this.damageType = damageType;
        this.attackType = attackType;
    }

    public IndirectSimpleEgoDamageSource(IndirectEntityDamageSource oldSource, AttackTypes attackType, DamageTypes damageType, boolean consumesStatus) {
        super(oldSource.getMsgId(), oldSource.getDirectEntity(), oldSource.getEntity());
        this.damageType = damageType;
        this.attackType = attackType;
        this.status = consumesStatus;
    }

    @Override
    public String toString() {
        return "IndirectSimpleEgoDamageSource{" +
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

    private boolean crit = false;

    @Override
    public boolean getCrit() {
        return this.crit;
    }

    @Override
    public void setCrit(boolean crit) {
        this.crit = crit;
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

    private boolean status = false;

    @Override
    public boolean getConsumesStatus() {
        return this.status;
    }

    @Override
    public void setConsumesStatus(boolean status) {
        this.status = status;
    }

}
