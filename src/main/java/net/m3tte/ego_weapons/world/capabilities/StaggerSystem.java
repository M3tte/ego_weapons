package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.particle.StaggerShardParticle;
import net.m3tte.ego_weapons.potion.Staggered;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.StaggerableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.CombatRules;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.function.Consumer;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.onStaggered;

public class StaggerSystem {


    public static boolean isStaggered(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            return entityData.stagger <= 0;
        } else {
            return entity.getPersistentData().getDouble("stagger") > entity.getMaxHealth();
        }
    }

    public static void reduceStagger(LivingEntity entity, float amnt, Entity source, boolean bypassArmor) {
        reduceStagger(entity, amnt, (e) -> {
            if (source instanceof PlayerEntity)
                onStaggered((PlayerEntity) source, entity);
        }, bypassArmor);
    }

    public static void reduceStagger(LivingEntity entity, float amnt, boolean bypassArmor) {
        reduceStagger(entity, amnt, (n) -> {}, bypassArmor);
    }
    public static void reduceStagger(LivingEntity entity, float amnt, Consumer<?> onStagger, boolean bypassArmor) {
        if (entity instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            entityData.stagger = Math.min(entityData.stagger, entityData.maxStagger);

            if (!bypassArmor)
                amnt = CombatRules.getDamageAfterAbsorb(amnt, entity.getArmorValue(), 0);

            entityData.stagger -= amnt;

            if (entity.hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get()) && entityData.stagger < 1)
                entityData.stagger = 1;

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
            EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

            entityData.stagger += amnt;
            entityData.stagger = Math.min(entityData.stagger, entityData.maxStagger);

            entityData.syncStagger(entity);

        } else {
            entity.getPersistentData().putDouble("stagger", Math.max(entity.getPersistentData().getDouble("stagger")-amnt,0));
        }
    }

    public static void stagger(LivingEntity entity, Consumer<?> onStagger) {

        if (!entity.isAlive() && entity.getHealth() < 0)
            return;


        if (entity instanceof PlayerEntity) {
            System.out.println("Executing stagger");
        }

        if (!entity.hasEffect(Staggered.get()) && entity.isAlive()) {
            entity.addEffect(new EffectInstance(Staggered.get(), 80, 0));
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 80, 0));
            if (!entity.level.isClientSide) {
                ServerWorld serverWorld = (ServerWorld) entity.level;
                serverWorld.playSound(null,  entity.getX(), entity.getY(), entity.getZ(), EgoWeaponsSounds.STAGGER, SoundCategory.PLAYERS, 1, 1);
                serverWorld.sendParticles(StaggerShardParticle.particle, entity.getX(), entity.getY()+entity.getBbHeight() * 0.1f, entity.getZ(), 15, 0, 0, 0, 0.3f);
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendStaggerMessage(entity.getId()));
            }
            onStagger.accept(null);
            entity.playSound(EgoWeaponsSounds.STAGGER, 1,1);

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null && !entity.level.isClientSide()) {
                StaticAnimation staggerAnim = getStaggerAnimation(entitypatch);

                if (staggerAnim != null)
                    entitypatch.playAnimationSynchronized(staggerAnim, 0.1f);
            }
        }
    }

    public static StaticAnimation getStaggerAnimation(LivingEntityPatch<?> patch) {
        if (patch == null)
            return null;
        // If the entity is currently knocked down, continue playing that animation.
        if (patch instanceof StaggerableEntity) {
            System.out.println("STAGGERING "+patch.getOriginal()+" AS IT IS INSTANCE OF STAGGERABLE");
            if (((StaggerableEntity) patch).getGroundAnimation(99) != null) {
                if (((StaggerableEntity) patch).getGroundAnimation(99).getId() == patch.getServerAnimator().animationPlayer.getAnimation().getId())
                    return ((StaggerableEntity) patch).getGroundAnimation(99);
            }

        }

        if (Animations.BIPED_HIT_LONG.equals(patch.getHitAnimation(ExtendedDamageSource.StunType.LONG))) { // Is a biped like entity
            if (EgoWeaponsAnimations.PUMMEL_DOWN.getId() == patch.getServerAnimator().animationPlayer.getAnimation().getId())
                return EgoWeaponsAnimations.PUMMEL_DOWN;

            return EgoWeaponsAnimations.STAGGER;
        } else if (patch instanceof StaggerableEntity) { // Has a designated stagger animation
            return ((StaggerableEntity) patch).getStaggerAnimation();
        } else {
            return patch.getHitAnimation(ExtendedDamageSource.StunType.LONG);
        }
    }
}
