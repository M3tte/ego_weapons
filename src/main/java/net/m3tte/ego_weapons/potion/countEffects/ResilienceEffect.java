//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class ResilienceEffect extends CountPotencyStatus {
    public ResilienceEffect() {
        super(EffectType.BENEFICIAL, "resilience",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.resilience";
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (entity.tickCount % 4 == 0) {
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 0));
        }

    }

    @Override
    public int getPotency(LivingEntity entity) {
        if (entity.hasEffect(this))
            return entity.getEffect(this).getAmplifier()+1;

        return 0;
    }

    @Override
    public void increment(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;

        if (!entity.hasEffect(this)) {
            int time = entity.getEffect(this).getDuration();
            entity.addEffect(new EffectInstance(this, time, Math.max(potency-1,0)));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,98);

            entity.getEffect(this).update(new EffectInstance(this, 99999, potency));

            syncEffect(entity);
        }
    }

    @Override
    public void decrement(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;

        if (entity.hasEffect(this)) {
            int time = entity.getEffect(this).getDuration();
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,99);
            entity.removeEffect(this);
            if (potency >= 0) {
                entity.addEffect(new EffectInstance(this, time, potency));
                syncEffect(entity);
            }
        }
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
