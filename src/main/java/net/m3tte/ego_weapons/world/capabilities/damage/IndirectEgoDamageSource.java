package net.m3tte.ego_weapons.world.capabilities.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.api.utils.IndirectEpicFightDamageSource;

public class IndirectEgoDamageSource extends IndirectEpicFightDamageSource implements GenericEgoDamage {

    private final AttackTypes attackType;
    private final DamageTypes damageType;
    private final String attackIdentifier;
    public IndirectEgoDamageSource(String damageTypeIn, Entity owner, Entity projectile, StunType stunType, AttackTypes attackType, DamageTypes damageType, String attackIdentifier) {
        super(damageTypeIn, owner, projectile, stunType);
        this.attackType = attackType;
        this.damageType = damageType;
        this.attackIdentifier = attackIdentifier;
    }

    public IndirectEgoDamageSource(IndirectEpicFightDamageSource oldSource, AttackTypes attackType, DamageTypes damageType, boolean consumesStatus, String attackIdentifier) {
        super(oldSource.getType(), oldSource.getEntity(), oldSource.getDirectEntity(), oldSource.getStunType());
        this.attackType = attackType;
        this.damageType = damageType;
        this.status = consumesStatus;
        this.attackIdentifier = attackIdentifier;
    }

    @Override
    public String toString() {
        return "IndirectEgoDamageSource{" +
                "attackType=" + attackType +
                ", damageType=" + damageType +
                '}';
    }

    @Override
    public @NotNull ITextComponent getLocalizedDeathMessage(LivingEntity targetEntity) {
        LivingEntity livingentity = targetEntity.getKillCredit();

        if (this.attackIdentifier == null)
            return super.getLocalizedDeathMessage(targetEntity);

        String s = "death.attack.egoweapons." + this.attackIdentifier;
        String s1 = s + ".player";


        return livingentity != null ? new TranslationTextComponent(s1, targetEntity.getDisplayName(), livingentity.getDisplayName()) : new TranslationTextComponent(s, targetEntity.getDisplayName());
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
