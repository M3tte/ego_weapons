package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.entities.DawnOfGreenDoubtEntity;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.gameasset.TCorpMobAnimations;
import net.m3tte.tcorp.potion.OrlandoPotionEffect;
import net.m3tte.tcorp.world.capabilities.entitypatch.DoubtAPatch;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
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
                if (!world.isClientSide() && !player.getCooldowns().isOnCooldown(TCorpItems.PERCEPTION_BLOCKING_MASK.get()) && entitypatch.getStamina() > entitypatch.getMaxStamina() * 0.2f) {
                    if (amount > 6 && living.getRandom().nextFloat() > 0.7) {
                        entitypatch.setStamina(entitypatch.getStamina() - entitypatch.getMaxStamina() * 0.4f);
                        player.getCooldowns().addCooldown(TCorpItems.PERCEPTION_BLOCKING_MASK.get(), 240);
                        entitypatch.playAnimationSynchronized(TCorpAnimations.BS_DODGE, 0);

                        event.setCanceled(true);
                    } else if (amount > 6) {
                        player.getCooldowns().addCooldown(TCorpItems.PERCEPTION_BLOCKING_MASK.get(), 60);
                    }
                }
            }
        }
    }

}
