//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class AssistFireEffect extends CountPotencyStatus {

    public AssistFireEffect() {
        super(EffectType.HARMFUL, "assist_fire",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.assist_fire";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return true;
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }

    @Override
    public int getPotency(EffectInstance ef) {
        return 0;
    }

    @Override
    public int getCount(LivingEntity entity) {
        return 0;
    }

    @Override
    public int getPotency(LivingEntity entity) {
        return 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        EffectInstance selfEffect = entity.getEffect(this);

        if (selfEffect != null) {
            int time = selfEffect.getDuration();

            if (time < 5) {
                entity.getPersistentData().remove("targetMarkedTarget");
                entity.removeEffect(this);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
