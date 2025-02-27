//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.particle.ShadowpuffParticle;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PoiseEffect extends CountPotencyStatus {
    public PoiseEffect() {
        super(EffectType.BENEFICIAL, "poise",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.poise";
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        World world = entity.level;

        if (!(entity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) entity;

        if (EmotionSystem.getEmotionLevel(player) == 0 && EmotionSystem.getEmotionPoints(player) < 2 && EmotionSystem.getEmotionPoints(player) > 0) {
            entity.removeEffect(this);
        }


    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
