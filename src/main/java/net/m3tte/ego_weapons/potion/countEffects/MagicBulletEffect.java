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
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class MagicBulletEffect extends CountPotencyStatus {
    public MagicBulletEffect() {
        super(EffectType.BENEFICIAL, "magic_bullet",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.magic_bullet";
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        World world = entity.level;

        if (!(entity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) entity;

        if (EmotionSystem.getEmotionLevel(player) == 0 && EmotionSystem.getEmotionPoints(player) < 2 && EmotionSystem.getEmotionPoints(player) > 0) {
            entity.removeEffect(this);
        }



        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ShadowpuffParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                    (entity.getZ()), (int) 2, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
        }

    }

    @Override
    public int getPotency(LivingEntity entity) {
        if (entity.hasEffect(this))
            return entity.getEffect(this).getAmplifier()+1;

        return 0;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return false;
    }

    @Override
    public void increment(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;



        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 9999, Math.min(Math.max(potency-1,0),6)));
            syncEffect(entity);
        } else {
            potency = entity.getEffect(this).getAmplifier() + potency;

            if (potency > 6) {
                entity.removeEffect(this);
                potency = 0;
                entity.addEffect(new EffectInstance(this, 9999, potency));

            } else {
                entity.getEffect(this).update(new EffectInstance(this, 9999, potency));

            }


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
                entity.addEffect(new EffectInstance(this, 99999, potency));
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
