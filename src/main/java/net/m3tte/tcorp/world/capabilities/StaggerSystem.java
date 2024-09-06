package net.m3tte.tcorp.world.capabilities;

import net.m3tte.tcorp.*;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.network.packages.StaggerPackages;
import net.m3tte.tcorp.particle.StaggerShardParticle;
import net.m3tte.tcorp.potion.Staggered;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.function.Consumer;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;

public class StaggerSystem {


    public static boolean isStaggered(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            TcorpModVariables.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            return entityData.stagger <= 0;
        } else {
            return entity.getPersistentData().getDouble("stagger") > entity.getMaxHealth();
        }
    }
    public static void reduceStagger(LivingEntity entity, float amnt) {
        reduceStagger(entity, amnt, (n) -> {});
    }
    public static void reduceStagger(LivingEntity entity, float amnt, Consumer<?> onStagger) {
        if (entity instanceof PlayerEntity) {
            TcorpModVariables.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            entityData.stagger = Math.min(entityData.stagger, entityData.maxStagger);
            System.out.println("Reducing stagger, threshold is currently at: "+entityData.stagger);
            entityData.stagger -= amnt;



            if (entityData.stagger <= 0) {
                entityData.stagger = 0;
                stagger(entity, onStagger);
            }

            entityData.syncStagger(entity);

        } else {
            entity.getPersistentData().putDouble("stagger", entity.getPersistentData().getDouble("stagger")+amnt);

            if (entity.getPersistentData().getDouble("stagger") > entity.getMaxHealth()) {
                stagger(entity, onStagger);
            }

        }
    }

    public static void healStagger(LivingEntity entity, float amnt) {
        if (entity instanceof PlayerEntity) {
            TcorpModVariables.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

            entityData.stagger += amnt;
            entityData.stagger = Math.min(entityData.stagger, entityData.maxStagger);

            entityData.syncStagger(entity);

        } else {
            entity.getPersistentData().putDouble("stagger", Math.max(entity.getPersistentData().getDouble("stagger")-amnt,0));
        }
    }

    public static void stagger(LivingEntity entity, Consumer<?> onStagger) {
        if (entity instanceof PlayerEntity) {
            System.out.println("Executing stagger");
        }

        if (!entity.hasEffect(Staggered.get()) && entity.isAlive()) {
            entity.addEffect(new EffectInstance(Staggered.get(), 80, 0));
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 80, 0));
            if (!entity.level.isClientSide) {
                ServerWorld serverWorld = (ServerWorld) entity.level;
                serverWorld.playSound(null,  entity.getX(), entity.getY(), entity.getZ(),TCorpSounds.STAGGER, SoundCategory.PLAYERS, 1, 1);
                serverWorld.sendParticles(StaggerShardParticle.particle, entity.getX(), entity.getY()+entity.getBbHeight() * 0.1f, entity.getZ(), 15, 0, 0, 0, 0.3f);
                TcorpMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new StaggerPackages.SendStaggerMessage(entity.getId()));
            }
            onStagger.accept(null);
            entity.playSound(TCorpSounds.STAGGER, 1,1);

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null && !entity.level.isClientSide()) {
                if (Animations.BIPED_HIT_LONG.equals(entitypatch.getHitAnimation(ExtendedDamageSource.StunType.LONG))) {
                    entitypatch.playAnimationSynchronized(TCorpAnimations.STAGGER, 0);
                } else {
                    if (entitypatch.getHitAnimation(ExtendedDamageSource.StunType.LONG) != null)
                        entitypatch.playAnimationSynchronized(entitypatch.getHitAnimation(ExtendedDamageSource.StunType.LONG), 0.25f);
                }
            }



        }
    }
}
