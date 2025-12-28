//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class SinEffect extends CountPotencyStatus {
    public SinEffect() {
        super(EffectType.BENEFICIAL, "sin",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.sin";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (entity.hasEffect(this)) {
            if (entity.getEffect(this).getDuration() < 10) {
                decrement(entity, 1, 0);
            }
        }

    }


    @Override
    public void increment(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 400, Math.min(6,Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,6);

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
