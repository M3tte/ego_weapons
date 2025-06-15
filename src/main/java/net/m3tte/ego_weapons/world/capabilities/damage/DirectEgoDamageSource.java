package net.m3tte.ego_weapons.world.capabilities.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.EpicFightDamageSource;

public class DirectEgoDamageSource extends EpicFightDamageSource implements GenericEgoDamage {

    private final AttackTypes attackType;
    private final DamageTypes damageType;
    private final String attackIdentifier;


    public DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, AttackTypes attackType, DamageTypes damageType, String attackIdentifier) {
        super(damageTypeIn, damageSourceEntityIn, stunType, animation);
        this.attackType = attackType;
        this.damageType = damageType;
        this.attackIdentifier = attackIdentifier;
    }

    public DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, Hand hand, AttackTypes attackType, DamageTypes damageType, String attackIdentifier) {
        super(damageTypeIn, damageSourceEntityIn, stunType, animation, hand);
        this.attackType = attackType;
        this.damageType = damageType;
        this.attackIdentifier = attackIdentifier;
    }

    public DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, float impact, float armorNegation, AttackTypes attackType, DamageTypes damageType, String attackIdentifier) {
        super(damageTypeIn, damageSourceEntityIn, stunType, impact, armorNegation);
        this.attackType = attackType;
        this.damageType = damageType;
        this.attackIdentifier = attackIdentifier;
    }

    public DirectEgoDamageSource(EpicFightDamageSource oldSource, AttackTypes attackType, DamageTypes damageType, StaticAnimation animation, Hand hand, boolean consumesStatus, String attackIdentifier) {
        super(oldSource.getType(), oldSource.getEntity(), oldSource.getStunType(), animation, hand);
        this.attackType = attackType;
        this.damageType = damageType;
        this.status = consumesStatus;
        this.attackIdentifier = attackIdentifier;
    }



    @Override
    public String toString() {
        return "DirectEgoDamageSource{" +
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
