package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeRepMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.HeishouMaoBranchAnims;
import net.m3tte.ego_weapons.gameasset.movesets.LiuSouth6MovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.DamageDefaults;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.gamerules.EgoWeaponsGamerules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.ServerAnimator;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.*;

public class OnEntityHit {
    @Mod.EventBusSubscriber
    private static class GlobalTrigger {




        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onEntityAttacked(LivingAttackEvent event) {



            World world = event.getEntity().level;

            float amount = event.getAmount();

            LivingEntity living = event.getEntityLiving();

            // Clashing, only occurs when enabled in gamerule
            if (event.getSource().getDirectEntity() instanceof LivingEntity && event.getEntityLiving().level.getGameRules().getBoolean(EgoWeaponsGamerules.ENABLE_CLASHING)) {
                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) event.getSource().getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) event.getEntityLiving().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                boolean targetCracked = false;
                boolean sourceCracked = false;

                if (targetPatch != null && sourcePatch != null) {
                    if (targetPatch.getServerAnimator() != null && sourcePatch.getServerAnimator() != null) {
                        DynamicAnimation targetAnim = targetPatch.getServerAnimator().animationPlayer.getAnimation();
                        DynamicAnimation sourceAnim = sourcePatch.getServerAnimator().animationPlayer.getAnimation();

                        if (targetAnim != null && sourceAnim != null) {


                            boolean targetKnock = true;
                            boolean sourceKnock = true;

                            boolean targetClash = true;
                            boolean sourceClash = true;

                            AttackAnimation.Phase targetPhase = null;

                            if (targetAnim instanceof AttackAnimation)
                                targetPhase = ((AttackAnimation) targetAnim).getPhaseByTime(targetPatch.getAnimator().getPlayerFor(targetAnim).getElapsedTime());


                            AttackAnimation.Phase sourcePhase = null;

                            if (sourceAnim instanceof AttackAnimation)
                                sourcePhase = ((AttackAnimation) sourceAnim).getPhaseByTime(sourcePatch.getAnimator().getPlayerFor(sourceAnim).getElapsedTime());



                            float targetImpact = targetPatch.getImpact(Hand.MAIN_HAND);
                            float sourceImpact = sourcePatch.getImpact(Hand.MAIN_HAND);

                            if (targetAnim instanceof EgoAttackAnimation) {
                                targetImpact = ((EgoAttackAnimation) targetAnim).getExtendedDamageSource(targetPatch, sourcePatch.getOriginal(), targetPhase).getImpact();
                                targetKnock = (targetAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK).orElse(true);
                                targetClash = (targetAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SHOULD_CLASH).orElse(true);
                            } else if (targetAnim instanceof BasicEgoAttackAnimation) {
                                targetImpact = ((BasicEgoAttackAnimation) targetAnim).getExtendedDamageSource(targetPatch, sourcePatch.getOriginal(), targetPhase).getImpact();
                                targetKnock = (targetAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK).orElse(true);
                                targetClash = (targetAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SHOULD_CLASH).orElse(true);
                            }

                            if (sourceAnim instanceof EgoAttackAnimation) {
                                sourceImpact = ((EgoAttackAnimation) sourceAnim).getExtendedDamageSource(sourcePatch, targetPatch.getOriginal(), sourcePhase).getImpact();
                                sourceKnock = (sourceAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK).orElse(true);
                                sourceClash = (sourceAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SHOULD_CLASH).orElse(true);
                            } else if (sourceAnim instanceof BasicEgoAttackAnimation) {
                                sourceImpact = ((BasicEgoAttackAnimation) sourceAnim).getExtendedDamageSource(sourcePatch, targetPatch.getOriginal(), sourcePhase).getImpact();
                                sourceKnock = (sourceAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK).orElse(true);
                                sourceClash = (sourceAnim).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SHOULD_CLASH).orElse(true);
                            }

                            System.out.println("Source Knock Stats : "+sourceKnock+ " Source is : "+sourcePatch.getOriginal());
                            System.out.println("Target Knock Stats : "+targetKnock+ " Target is : "+targetPatch.getOriginal());

                            if (sourcePatch.getEntityState().attacking() && targetPatch.getEntityState().attacking() && (targetClash && sourceClash)) {


                                float difference = sourceImpact - targetImpact;

                                LivingEntityPatch<?> loserPatch = difference < 0 ? sourcePatch : targetPatch;
                                LivingEntityPatch<?> winnerPatch = difference > 0 ? sourcePatch : targetPatch;

                                boolean winnerKnock = difference > 0 ? sourceKnock : targetKnock;


                                targetPatch.playSound(EgoWeaponsSounds.CLASH, 1, 1);

                                difference = Math.abs(difference);

                                if (difference > 0.5f) {
                                    System.out.println("Knock values : W"+winnerKnock+" | s"+sourceKnock+" | t"+targetKnock);
                                    if (winnerKnock)
                                        loserPatch.knockBackEntity(winnerPatch.getOriginal().position().add(0,1.5,0), Math.min(0.8f,difference*0.05f + 0.5f));


                                    // Condition for breaking coins
                                    if (difference >= 1.4f) {

                                        // When on the defending side
                                        if (!winnerPatch.equals(sourcePatch)) {
                                            event.setCanceled(true);
                                            sourceCracked = true;
                                        } else {
                                            targetCracked = true;
                                        }

                                        winnerPatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 10, 5));
                                        clashStunEntity(loserPatch, 3);

                                    } else {
                                        winnerPatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 5, 8));

                                    }


                                    loserPatch.currentlyAttackedEntity.add(winnerPatch.getOriginal());
                                    StaggerSystem.healStagger(winnerPatch.getOriginal(), Math.min(5, difference));
                                    StaggerSystem.reduceStagger(loserPatch.getOriginal(), Math.min(5, difference), true);

                                    loserPatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 15, 11));
                                    winnerPatch.playSound(EgoWeaponsSounds.CLASH_WIN, 1, 1);


                                } else {
                                    if (sourceKnock)
                                        targetPatch.knockBackEntity(sourcePatch.getOriginal().position().add(0,1.5,0), 0.5f);

                                    if (targetKnock)
                                        sourcePatch.knockBackEntity(targetPatch.getOriginal().position().add(0,1.5,0), 0.5f);


                                    sourcePatch.currentlyAttackedEntity.add(targetPatch.getOriginal());
                                    targetPatch.currentlyAttackedEntity.add(sourcePatch.getOriginal());
                                    event.setCanceled(true);

                                    sourcePatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 10, 8));
                                    targetPatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.CLASH_STUN.get(), 10, 8));
                                    targetPatch.playSound(EgoWeaponsSounds.CLASH_DRAW, 1, 1);



                                }
                                if (!living.level.isClientSide()) {
                                    ((ServerWorld) living.level).sendParticles(EpicFightParticles.HIT_BLUNT.get(), living.getX(), living.getY()+1, living.getZ(), 2, 0.01f, 0.1f, 0.1f, 0.1f);
                                    if (targetPatch.getOriginal().level.getGameRules().getBoolean(EgoWeaponsGamerules.ENABLE_CLASHINDICATOR)) {
                                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.ClashLabelParticle(targetPatch.getOriginal().position().add(0, 2, 0), evaluateDamageType(targetPatch), targetCracked, targetImpact));
                                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.ClashLabelParticle(sourcePatch.getOriginal().position().add(0, 2, 0), evaluateDamageType(sourcePatch), sourceCracked, sourceImpact));
                                    }
                                }

                            }
                        }
                    }
                }



            }




            if (living.hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get())) {
                PlayerPatch<?> entitypatch = (PlayerPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


                if (entitypatch.getAnimator() instanceof ServerAnimator) {
                    DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

                    if (currentanim != null) {
                        if (currentanim.getId() == OeufiAssocMovesetAnims.OEUFI_OPEN_CONTRACT.getId()) {
                            if (event.getSource().getDirectEntity() instanceof LivingEntity) {
                                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) event.getSource().getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                                SharedFunctions.staggerEntity(sourcePatch, 2, false);
                            }

                            entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_EVADE_CONTRACT, 0);

                            event.setCanceled(true);
                        }



                    }
                }
            }

            if (living.hasEffect(EgoWeaponsEffects.RESILIENCE.get())) {
                LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


                if (entitypatch.getAnimator() instanceof ServerAnimator) {
                    DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

                    if (currentanim != null) {




                        if (currentanim.getId() == LiuSouth6MovesetAnims.LIU_S6_FOCUS_EMBERS.getId() && event.getSource().getEntity() instanceof LivingEntity) {
                            if (event.getSource().getDirectEntity() instanceof LivingEntity) {
                                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) event.getSource().getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                                SharedFunctions.staggerEntity(sourcePatch, 2, false);
                            }

                            int targetPotency = EgoWeaponsEffects.BURN.get().getPotency((LivingEntity) event.getSource().getEntity());

                            if (living.getAbsorptionAmount() < 7) {
                                living.setAbsorptionAmount(Math.min(7, living.getAbsorptionAmount() + targetPotency));
                            }

                            EgoWeaponsEffects.POWER_UP.get().increment(living, 0, 1);
                            EgoWeaponsEffects.BURN.get().increment((LivingEntity) event.getSource().getEntity(), 2, 3);

                            entitypatch.playSound(EgoWeaponsSounds.METAL_CLASH, 1, 1);
                            entitypatch.playSound(EgoWeaponsSounds.FIREFIST_SPECIAL_FLARE, 1, 1);

                            if (!living.level.isClientSide()) {
                                ((ServerWorld) living.level).sendParticles(ParticleTypes.FLAME, living.getX(), living.getY()+1, living.getZ(), 10, 0.05f, 0.2f, 0.5f, 0.2f);
                                ((ServerWorld) living.level).sendParticles(EgoWeaponsParticles.FIREFIST_STRIKE.get(), living.getX(), living.getY()+1, living.getZ(), 1, 0.05f, 0.2f, 0.0f, 0.2f);
                            }

                            entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 10, 0));
                            entitypatch.playAnimationSynchronized(LiuSouth6MovesetAnims.LIU_S6_FOCUS_EMBERS_BASH, 0);
                        }

                        if (currentanim.getId() == HeishouMaoBranchAnims.HEISHOU_MAO_STRIDER_MAO.getId()) {
                            if (event.getSource().getDirectEntity() instanceof LivingEntity) {
                                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) event.getSource().getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                                SharedFunctions.hitstunEntity(sourcePatch, 2, false, 0.2f);
                            }

                            if (entitypatch.getOriginal() instanceof PlayerEntity)
                                BlipTick.chargeBlips((PlayerEntity) entitypatch.getOriginal(), 1, true);

                            entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_PARRY, 1, 1);
                            entitypatch.playSound(EgoWeaponsSounds.FIREFIST_SPECIAL_FLARE, 1, 1);
                            EgoWeaponsEffects.RESILIENCE.get().decrement(entitypatch.getOriginal(), 1, 1);
                            if (!living.level.isClientSide()) {
                                ((ServerWorld) living.level).sendParticles(EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), living.getX(), living.getY()+1, living.getZ(), 1, 0.05f, 0.2f, 0.0f, 0.2f);
                            }

                            entitypatch.playAnimationSynchronized(HeishouMaoBranchAnims.HEISHOU_MAO_PARRY_3, 0);
                            event.setCanceled(true);
                        }
                    }
                }
            }



            if (EgoWeaponsItems.FULLSTOP_REP_CLOAK.get().equals(living.getItemBySlot(EquipmentSlotType.CHEST).getItem())) {
                LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                if (EgoWeaponsEffects.POISE.get().getPotency(living) >= 27 && !(living.hasEffect(EpicFightMobEffects.STUN_IMMUNITY.get()))) {

                    boolean cooldownFlag = true;
                    if (living instanceof PlayerEntity) {
                        cooldownFlag = !((PlayerEntity) living).getCooldowns().isOnCooldown(EgoWeaponsItems.FULLSTOP_REP_CLOAK.get());
                    }


                    if (amount > 4 && cooldownFlag) {
                        if (living instanceof PlayerEntity) {
                            ((PlayerEntity) living).getCooldowns().addCooldown(EgoWeaponsItems.FULLSTOP_REP_CLOAK.get(), 100);
                        }
                        EgoWeaponsEffects.POISE.get().decrement(living, 0, 7);
                        entitypatch.playAnimationSynchronized(FullstopOfficeRepMovesetAnims.FULLSTOP_REP_EVADE, 0);
                        event.setCanceled(true);
                    }
                }
            }

            if (living.hasEffect(OrlandoPotionEffect.potion) && living instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) living;

                PlayerPatch<?> entitypatch = (PlayerPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                // Blacksilence Auto Dodge, has a 30% Chance
                if (!world.isClientSide() && !player.getCooldowns().isOnCooldown(EgoWeaponsItems.PERCEPTION_BLOCKING_MASK.get()) && entitypatch.getStamina() > entitypatch.getMaxStamina() * 0.2f) {
                    if (amount > 6 && living.getRandom().nextFloat() > 0.7) {
                        entitypatch.setStamina(entitypatch.getStamina() - entitypatch.getMaxStamina() * 0.4f);
                        player.getCooldowns().addCooldown(EgoWeaponsItems.PERCEPTION_BLOCKING_MASK.get(), 240);
                        entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.BS_DODGE, 0);

                        event.setCanceled(true);
                        return;
                    } else if (amount > 6) {
                        player.getCooldowns().addCooldown(EgoWeaponsItems.PERCEPTION_BLOCKING_MASK.get(), 60);
                    }
                }
            }



        }
    }

}
