//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class ProtectionEffect extends CountPotencyStatus {
    public ProtectionEffect() {
        super(EffectType.BENEFICIAL, "protection",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.protection";
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
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.
    }

    public void increment(LivingEntity entity, int potency) {
        if (entity.level.isClientSide)
            return;



        if (potency > 9)
            potency = 9;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 200, potency));
        } else {
            entity.getEffect(this).update(new EffectInstance(this, 200, potency));
        }
        syncEffect(entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }
}
