package net.m3tte.ego_weapons.execFunctions;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.particle.BlackdamageParticle;
import net.m3tte.ego_weapons.particle.SparkparticleParticle;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class BlackSilenceEvaluator {

        public static void addToStatistic(LivingEntity entity, int amount, String statistic) {
            entity.getPersistentData().putDouble(statistic, (entity.getPersistentData().getDouble(statistic) + amount));
        }


        public static void furiosoAddAttackStat(Entity entity, int amnt) {
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                if (living.hasEffect(FuriosoPotionEffect.potion))
                    addToStatistic(living, amnt, "furiosoattacks");
            }
        }
        public static void onHitDurandal(IWorld world, Entity entity, LivingEntity sourceentity) {
            if (entity instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) entity;
                if (sourceentity.hasEffect(FuriosoPotionEffect.potion) && entity instanceof LivingEntity) {
                    if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
                        addToStatistic(sourceentity, 4, "furiosohits");
                        if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {
                            if (world instanceof ServerWorld) {
                                ((ServerWorld) world).sendParticles(SparkparticleParticle.particle, (sourceentity.getX()),
                                        (sourceentity.getY() + sourceentity.getBbHeight() / 1.5), (sourceentity.getZ()), (int) 10, 0, 0, 0, 0.1);
                            }
                            targetEntity.setHealth(targetEntity.getHealth() - 8);
                            targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
                            targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
                        }
                    } else {
                        targetEntity.setHealth(targetEntity.getHealth() - 5);
                    }
                } else {
                    targetEntity.setHealth(targetEntity.getHealth() - 3);

                    ((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
                            (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
                }
            }
        }

    public static void onHitAtelierPistol(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) entity;
            targetEntity.setHealth(targetEntity.getHealth() - 2);
            if (sourceentity.hasEffect(FuriosoPotionEffect.potion) && entity instanceof LivingEntity) {
                if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
                    addToStatistic(sourceentity, 6, "furiosohits");
                    if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {
                        if (world instanceof ServerWorld) {
                            ((ServerWorld) world).sendParticles(SparkparticleParticle.particle, (sourceentity.getX()),
                                    (sourceentity.getY() + sourceentity.getBbHeight() / 1.5), (sourceentity.getZ()), (int) 10, 0, 0, 0, 0.1);
                        }
                        targetEntity.setHealth(targetEntity.getHealth() - 2);
                        targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
                        targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
                    }
                }
            } else {


                ((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
                        (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
            }
        }
    }

    public static void onHitMook(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) entity;
            addToStatistic(sourceentity, 1, "mookStacks");
            if (sourceentity.getPersistentData().getDouble("mookstacks") == 3) {
                targetEntity.setHealth(targetEntity.getHealth() - 4);
                for (int x = 0; x < 3; x++)
                    ((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
                            (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
            }
        }
    }

    public static void onHitStrong(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) entity;
            targetEntity.setHealth(targetEntity.getHealth() - 4);
            if (sourceentity.hasEffect(FuriosoPotionEffect.potion) && entity instanceof LivingEntity) {
                if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
                    addToStatistic(sourceentity, 10, "furiosohits");
                    if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {
                        targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
                        targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
                    }
                }
            } else {


                ((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
                        (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
            }
        }
    }

    public static void onHitWheels(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity) entity;


            targetEntity.setHealth(targetEntity.getHealth() - 4);
            if (sourceentity.hasEffect(FuriosoPotionEffect.potion)) {
                if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
                    addToStatistic(sourceentity, 10, "furiosohits");
                    if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {
                        targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 2, (false), (false)));
                        targetEntity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 60, (int) 2, (false), (false)));
                    }
                }
            } else {


                ((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
                        (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);

            }
        }
    }

    public static void onHitZelkova(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            PlayerEntity player;
            LivingEntity targetEntity = (LivingEntity) entity;
            targetEntity.setHealth(targetEntity.getHealth() - 3);
            if (sourceentity.hasEffect(FuriosoPotionEffect.potion)) {



                if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
                    addToStatistic(sourceentity, 5, "furiosohits");
                    if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {

                        targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
                        targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));

                        if (world instanceof ServerWorld) {
                            ((ServerWorld)world).sendParticles(SparkparticleParticle.particle, sourceentity.getX(), sourceentity.getY() + (double)sourceentity.getBbHeight() / 1.5, sourceentity.getZ(), 10, 0.0, 0.0, 0.0, 0.1);
                        }
                    }
                }
            } else {
                ((ServerWorld)world).sendParticles(BlackdamageParticle.particle, entity.getX(), entity.getY() + (double)entity.getBbHeight() / 1.5, entity.getZ(), 1, (double)entity.getBbWidth() / 1.4, (double)(entity.getBbHeight() / 3.0F), (double)entity.getBbWidth() / 1.4, 0.0);
            }

            if (sourceentity instanceof PlayerEntity) {
                player = (PlayerEntity)sourceentity;
                if (!player.getCooldowns().isOnCooldown(player.getItemInHand(Hand.MAIN_HAND).getItem()) || !player.getCooldowns().isOnCooldown(player.getItemInHand(Hand.OFF_HAND).getItem())) {
                    PlayerPatch<?> entitypatch = (PlayerPatch)player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, (Direction)null).orElse((EntityPatch) null);
                    entitypatch.setStamina(Math.min(entitypatch.getMaxStamina(), entitypatch.getStamina() + entitypatch.getMaxStamina() * 0.15F));

                    //System.out.println(player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new TcorpModVariables.PlayerVariables()).blips + 2.0);

                    if (player.getCooldowns().isOnCooldown(player.getItemInHand(Hand.MAIN_HAND).getItem())) {
                        player.getCooldowns().addCooldown(player.getItemInHand(Hand.OFF_HAND).getItem(), 90);
                        player.getCooldowns().addCooldown(player.getItemInHand(Hand.MAIN_HAND).getItem(), 90);
                        EntityTick.regenerateLight(player, 1, true);
                    } else {
                        player.getCooldowns().addCooldown(player.getItemInHand(Hand.MAIN_HAND).getItem(), 100);
                    }
                }
            }
        }

    }

    public static void onHitOldBoys(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            PlayerEntity player;
            LivingEntity targetEntity = (LivingEntity) entity;
            if (sourceentity.hasEffect(FuriosoPotionEffect.potion) && entity instanceof LivingEntity) {
                if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
                    addToStatistic(sourceentity, 10, "furiosohits");
                    targetEntity.setHealth(targetEntity.getHealth() - 2);
                    targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
                    targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
                    LivingEntityPatch<?> entitypatch = (LivingEntityPatch)entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, (Direction)null).orElse((EntityPatch) null);
                    if (entitypatch != null) {
                        if (entitypatch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
                            entitypatch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER, 0.0F);
                        }
                    }
                    if (world instanceof ServerWorld) {
                        ((ServerWorld)world).sendParticles(SparkparticleParticle.particle, sourceentity.getX(), sourceentity.getY() + (double)sourceentity.getBbHeight() / 1.5, sourceentity.getZ(), 10, 0.0, 0.0, 0.0, 0.1);
                    }
                }
            }

            if (sourceentity instanceof PlayerEntity) {
                player = (PlayerEntity)sourceentity;
                if (!player.getCooldowns().isOnCooldown(player.getItemInHand(Hand.MAIN_HAND).getItem())) {
                    PlayerPatch<?> entitypatch = (PlayerPatch)player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, (Direction)null).orElse((EntityPatch) null);
                    entitypatch.setStamina(Math.min(entitypatch.getMaxStamina(), entitypatch.getStamina() + entitypatch.getMaxStamina() * 0.3F));
                    player.getCooldowns().addCooldown(player.getItemInHand(Hand.MAIN_HAND).getItem(), 60);
                    EntityTick.regenerateLight(player, 1, true);
                }
            }
        }

    }

    public static void onHitRanga(IWorld world, Entity entity, LivingEntity sourceentity) {
        if (entity instanceof LivingEntity) {
            LivingEntity targetEntity = (LivingEntity)entity;
            EgoWeaponsEffects.BLEED.get().increment(targetEntity, 1, 1);

            if (sourceentity.hasEffect(FuriosoPotionEffect.potion)) {
                if (sourceentity.getPersistentData().getDouble("furiosohits") < 10.0) {
                    addToStatistic(sourceentity, 4, "furiosohits");
                    if (sourceentity.getPersistentData().getDouble("furiosohits") >= 10.0) {
                        if (world instanceof ServerWorld) {
                            ((ServerWorld)world).sendParticles(SparkparticleParticle.particle, sourceentity.getX(), sourceentity.getY() + (double)sourceentity.getBbHeight() / 1.5, sourceentity.getZ(), 10, 0.0, 0.0, 0.0, 0.1);
                        }
                        EgoWeaponsEffects.BLEED.get().increment(targetEntity, 2, 2);
                        targetEntity.setHealth(targetEntity.getHealth() - 2.0F);
                        targetEntity.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
                        targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
                        targetEntity.addEffect(new EffectInstance(Effects.WITHER, (int) 60, (int) 1, (false), (false)));
                        LivingEntityPatch<?> entitypatch = (LivingEntityPatch)entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, (Direction)null).orElse((EntityPatch) null);
                        if (entitypatch != null) {
                            if (entitypatch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
                                entitypatch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER, 0.0F);
                            }
                        }
                    }
                } else {
                    targetEntity.setHealth(targetEntity.getHealth() - 1.0F);
                }
            } else {
                targetEntity.setHealth(targetEntity.getHealth() - 1.0F);
                ((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
                        (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);            }
        }
    }




    private static void addBleedIfViable(int amount, LivingEntity targetEntity, int cap) {
        if (targetEntity.hasEffect(EgoWeaponsEffects.BLEED.get())) {
            int currentLevel = targetEntity.getEffect(EgoWeaponsEffects.BLEED.get()).getAmplifier();
            targetEntity.addEffect(new EffectInstance(EgoWeaponsEffects.BLEED.get(), targetEntity.getEffect(EgoWeaponsEffects.BLEED.get()).getDuration() + 20, Math.min(currentLevel + amount, cap)));
        } else {
            targetEntity.addEffect(new EffectInstance(EgoWeaponsEffects.BLEED.get(), 100, 0));
        }

    }

}
