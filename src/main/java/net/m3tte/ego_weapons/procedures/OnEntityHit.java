package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class OnEntityHit {
    @Mod.EventBusSubscriber
    private static class GlobalTrigger {

        @SubscribeEvent
        public static void onEntityAttacked(LivingAttackEvent event) {



            World world = event.getEntity().level;

            float amount = event.getAmount();

            LivingEntity living = event.getEntityLiving();


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
