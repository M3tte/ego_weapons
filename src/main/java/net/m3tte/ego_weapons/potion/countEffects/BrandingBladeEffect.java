//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.Hand;

public class BrandingBladeEffect extends CountPotencyStatus {
    public BrandingBladeEffect() {
        super(EffectType.BENEFICIAL, "branding_blade",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.branding_blade";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (!entity.getItemInHand(Hand.MAIN_HAND).getItem().equals(EgoWeaponsItems.STIGMA_WORKSHOP_SWORD.get())) {
            entity.removeEffect(this);
        } else {
            if (entity.getEffect(this).getDuration() < 4) {
                entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("glow", 0);
            }
        }

    }

    @Override
    public int getPotency(EffectInstance ef) {
        return super.getCount(ef) + 1;
    }

    @Override
    public int getPotency(LivingEntity entity) {
        return super.getCount(entity) + 1;
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
