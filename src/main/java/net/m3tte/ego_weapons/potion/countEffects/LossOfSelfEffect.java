//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class LossOfSelfEffect extends PotencyOnlyStatus {
    public LossOfSelfEffect() {
        super(EffectType.HARMFUL, "loss_of_self",-16777216, true, 99, 1000);
    }
    @Override
    public String getDescriptionId() {
        return "effect.loss_of_self";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        super.applyEffectTick(entity,amplifier);
        if (entity.hasEffect(this)) {
            int cnt = (amplifier + 1) / 20;

            entity.addEffect(new EffectInstance(EgoWeaponsEffects.TIANSHIA_STAR.get(), 100, cnt));
        }
    }
}
