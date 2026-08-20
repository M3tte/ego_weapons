//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;

public class PotencyOnlyStatus extends CountPotencyStatus {


    private int limit = 99;
    private boolean decrementsNormally = true;
    private int effectDuration = 300;

    public int getLimit() {
        return limit;
    }

    public boolean isDecrementsNormally() {
        return decrementsNormally;
    }

    public int getEffectDuration() {
        return effectDuration;
    }



    public PotencyOnlyStatus(EffectType type, String name, int col, boolean decrementsNormally, int limit, int duration) {
        super(type, name,col);
        this.limit = Math.min(limit,99);
        this.decrementsNormally = decrementsNormally;
        this.effectDuration = duration;
    }

    public PotencyOnlyStatus(EffectType type, String name, int col) {
        super(type, name,col);
    }


    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (this.decrementsNormally) {
            if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
                decrement(entity, 0, 1);
            }
        }
    }


    @Override
    public void increment(LivingEntity entity, int cap, int potency) {

        if (cap == 0)
            cap = 99;

        if (entity.level.isClientSide)
            return;
        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, this.effectDuration, Math.min(Math.min(this.limit-1,cap-1),Math.max(potency,0))));
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(this.limit-1,cap-1));

            entity.getEffect(this).update(new EffectInstance(this, this.effectDuration, potency));
        }
        syncEffect(entity);
    }

    @Override
    public void decrement(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;

        if (cap == 0)
            cap = 99;

        if (entity.hasEffect(this)) {
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,cap-1);
            entity.removeEffect(this);
            if (potency >= 0) {
                entity.addEffect(new EffectInstance(this, this.effectDuration, potency));
            }
            syncEffect(entity);
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
