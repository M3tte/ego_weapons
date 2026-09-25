//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import java.util.UUID;

public class TianshiaStarsBlade extends CountPotencyStatus {
    public TianshiaStarsBlade() {
        super(EffectType.BENEFICIAL, "tianshia_star",-16777216);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {


    }


    @Override
    public void increment(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 20, Math.min(Math.min(99,cap),Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(99,cap));

            entity.getEffect(this).update(new EffectInstance(this, 20, potency));

            syncEffect(entity);
        }
    }


    // If the source has the effect
    public static float applyDamageModifiersSource(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource, boolean crit) {
        int lossOfSelfOnSource = EgoWeaponsEffects.LOSS_OF_SELF.get().getPotency(target);

        mult += SharedFunctions.incrementBonusDamage(damageSource, lossOfSelfOnSource * 0.01f);

        return mult;
    }

    // If the target has the effect
    public static float applyDamageModifiersTarget(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource, boolean crit) {
        int lossOfSelfOnTarget = EgoWeaponsEffects.LOSS_OF_SELF.get().getPotency(target);

        mult += SharedFunctions.incrementBonusDamage(damageSource, -lossOfSelfOnTarget * 0.0025f);

        return mult;
    }

    @Override
    public void decrement(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;



        if (entity.hasEffect(this)) {
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,99);
            entity.removeEffect(this);
            if (potency >= 0) {
                entity.addEffect(new EffectInstance(this, 20, potency));
                syncEffect(entity);
            }
        }
    }


    @Override
    public int getPotency(EffectInstance ef) {
        return Math.min(1,super.getPotency(ef));
    }

    @Override
    public int getPotency(LivingEntity entity) {
        return Math.min(1,super.getPotency(entity));
    }

    @Override
    public int getCount(LivingEntity entity) {
        return super.getCount(entity);
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }


    static AttributeModifier max_light = new AttributeModifier(UUID.fromString("fca31f98-930e-2a52-78d9-6ce68ebff984"), "max_light", 1, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);

        ModifiableAttributeInstance maxLight = attrman.getInstance(EgoWeaponsAttributes.MAX_LIGHT.get());

        //float processedPotency = value / (((value - 1) / 200) + 1) - 1;
        float processedPotency = Math.min(3,amplifier);


        if (maxLight != null) {
            maxLight.removeModifier(max_light);
            if (processedPotency >= 0)
                maxLight.addPermanentModifier(new AttributeModifier(max_light.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), max_light), max_light.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance maxLight = attrman.getInstance(EgoWeaponsAttributes.MAX_LIGHT.get());

        if (maxLight != null)
            maxLight.removeModifier(max_light);

        attrman.save();
    }

}
