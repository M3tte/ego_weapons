//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class BleedEffect extends CountPotencyStatus {
    public BleedEffect() {
        super(EffectType.HARMFUL, "bleed",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.bleed";
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
