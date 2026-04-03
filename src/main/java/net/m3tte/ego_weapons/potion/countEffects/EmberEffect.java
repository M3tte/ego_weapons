//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class EmberEffect extends CountPotencyStatus {
    public EmberEffect() {
        super(EffectType.HARMFUL, "embers",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.embers";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (entity.hasEffect(this)) {
            if (entity.getEffect(this).getDuration() < 10) {
                decrement(entity, 0, 1);
            }
        }

    }

    static AttributeModifier redResistanceMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b92-83d1-6ce88ebfc984"), "redResistanceMod", 0.1f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance redDMGInstance = attrman.getInstance(EgoWeaponsAttributes.RED_RESISTANCE.get());

        if (redDMGInstance != null) {
            redDMGInstance.removeModifier(redResistanceMod);
            redDMGInstance.addPermanentModifier(new AttributeModifier(redResistanceMod.getId(), this.getDescriptionId() + " " + 0, redResistanceMod.getAmount(), redResistanceMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance redDMGInstance = attrman.getInstance(EgoWeaponsAttributes.RED_RESISTANCE.get());

        if (redDMGInstance != null)
            redDMGInstance.removeModifier(redResistanceMod);

        attrman.save();
    }


    @Override
    public void increment(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 400, Math.min(4,Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,4);

            entity.getEffect(this).update(new EffectInstance(this, 400, potency));

            syncEffect(entity);
        }
    }

    @Override
    public void decrement(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;



        if (entity.hasEffect(this)) {
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,99);
            entity.removeEffect(this);
            if (potency >= 0) {
                entity.addEffect(new EffectInstance(this, 400, potency));
                syncEffect(entity);
            }
        }
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

}
