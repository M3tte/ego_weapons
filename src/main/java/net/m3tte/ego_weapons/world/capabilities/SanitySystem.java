package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.item.sunshower.Sunshower;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.potion.Panic;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.function.Supplier;

import static net.m3tte.ego_weapons.world.capabilities.DialogueSystem.onHitSanityDialogueEvaluation;

public class SanitySystem {


    public static void damageSanity(PlayerEntity player, float amnt) {
        EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());

        if (player.level.isClientSide())
            return;

        // When wearing sunshower, half any damage to sanity.
        if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUNSHOWER_CLOAK.get())) {
            amnt *= 0.5f;
        }

        playerVariables.sanity -= amnt;

        if (player.hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get()) && playerVariables.sanity < 1)
            playerVariables.sanity = 1;

        if (playerVariables.sanity <= 0.01)
            if (Sunshower.testInsanityEffect(player, playerVariables))
                return;

        if (playerVariables.sanity <= 0.01 && !player.hasEffect(Panic.get())) {
            triggerInsanity(player);
        }

        playerVariables.syncSanity(player);

        if (!player.hasEffect(Panic.get())) {
            onHitSanityDialogueEvaluation(player, (float) (playerVariables.sanity / EgoWeaponsAttributes.getMaxSanity(player)));
        }


    }

    public static void triggerInsanity(PlayerEntity entity) {
        entity.addEffect(new EffectInstance(Effects.BLINDNESS, 200, 0));
        entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 200, 0));
        entity.addEffect(new EffectInstance(Panic.get(), 200, 1));
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch != null) {
            entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.INSANITY_STUN, 0);



            if (entity instanceof ServerPlayerEntity) {

                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity), new ParticlePackages.SendInsanityMessage(entity.getId()));
            }
        }

        new DelayedEvent(185, (e) -> {
            SanitySystem.healSanity(entity, 10);
        });
    }

    public static void healSanity(PlayerEntity player, float amnt) {
        EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());

        playerVariables.sanity = Math.min(amnt + playerVariables.sanity, EgoWeaponsAttributes.getMaxSanity(player));




        playerVariables.syncSanity(player);

        int sanityTier = player.getPersistentData().getInt("sanityDialogueTier");

        // Reset Damagetiers upon regeneration
        if (sanityTier > 0) {
            float sanityPercent = (float) (playerVariables.sanity / EgoWeaponsAttributes.getMaxSanity(player));

            if (sanityPercent > 0.3f && sanityTier > 2) {
                sanityTier = 1;
            }

            if (sanityPercent > 0.75f && sanityTier > 1) {
                sanityTier = 0;
            }
            player.getPersistentData().putInt("sanityDialogueTier", sanityTier);
        }
    }

    public static double getSanity(PlayerEntity player) {
        EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());
        return playerVariables.sanity;
    }
}
