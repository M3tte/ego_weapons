//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class WhiteFragilityEffect extends CountPotencyStatus {
    public WhiteFragilityEffect() {
        super(EffectType.HARMFUL, "white_fragility",-16777216);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {


    }

    static AttributeModifier whiteResistanceMod = new AttributeModifier(UUID.fromString("fb214f98-930e-4b93-83d1-bdc88ebfc984"), "whiteResistanceMod", 0.1f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);


        ModifiableAttributeInstance whiteDMGInstance = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());

        System.out.println("UPDATING AMPLIFIER AT "+amplifier+" FOR CLIENT ? "+living.level.isClientSide());


        if (whiteDMGInstance != null) {
            whiteDMGInstance.removeModifier(whiteResistanceMod);
            whiteDMGInstance.addPermanentModifier(new AttributeModifier(whiteResistanceMod.getId(), this.getDescriptionId() + " " + 0, whiteResistanceMod.getAmount() * (amplifier + 1D), whiteResistanceMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance whiteDmg = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());

        if (whiteDmg != null)
            whiteDmg.removeModifier(whiteResistanceMod);

        attrman.save();
    }


    @Override
    public void increment(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 300, Math.min(Math.min(9,cap),Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(9,cap));

            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(this, 300, potency));

            syncEffect(entity);
        }
    }

    @Override
    public void decrement(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;

        if (entity.hasEffect(this)) {
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,99);
            entity.removeEffect(this);
            if (potency >= 0) {
                entity.addEffect(new EffectInstance(this, 200, potency));
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
