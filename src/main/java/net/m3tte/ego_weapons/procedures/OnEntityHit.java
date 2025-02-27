package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeRepMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.ServerAnimator;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class OnEntityHit {
    @Mod.EventBusSubscriber
    private static class GlobalTrigger {

        @SubscribeEvent
        public static void onEntityAttacked(LivingAttackEvent event) {



            World world = event.getEntity().level;

            float amount = event.getAmount();

            LivingEntity living = event.getEntityLiving();


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
