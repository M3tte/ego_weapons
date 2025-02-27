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
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

public class ObligationFullfillmentEffect extends CountPotencyStatus {
    public ObligationFullfillmentEffect() {
        super(EffectType.BENEFICIAL, "obligation_fullfillment",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.obligation_fullfillment";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
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
