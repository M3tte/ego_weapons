package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;

import java.util.UUID;

import static net.m3tte.ego_weapons.world.capabilities.DialogueSystem.onEmotionUpDialogueEvaluation;

public class EmotionSystem {

    public static int getEmotionRequired(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return getEmotionRequired(playerVariables);
    }

    public static int getEmotionLevel(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return playerVariables.emotionLevel;
    }

    public static double getEmotionPoints(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return playerVariables.emotionLevelProgress;
    }

    public static int getEmotionRequired(PlayerVariables playerVars) {
        return playerVars.emotionLevel * 35 + 35;
    }


    static AttributeModifier lightMod = new AttributeModifier(UUID.fromString("c1804411-accb-4b72-a4d7-ea21ba0828b4"),"emotionLight", 1, AttributeModifier.Operation.ADDITION);

    public static int getMaxEnergy(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return getMaxEnergy(playerVariables);
    }

    public static int getMaxEnergy(PlayerVariables playerVars) {
        return (int)((playerVars.emotionLevel + 1) * 1.5);
    }
    public static void updateMaxEnergy(PlayerEntity player, PlayerVariables vars) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        System.out.println("MAX ENERGY FLAG 0");

        ModifiableAttributeInstance lightInstance = player.getAttribute(EgoWeaponsAttributes.MAX_LIGHT.get());

        System.out.println("MAX ENERGY FLAG 1");

        if (lightInstance == null)
            return;

        System.out.println("MAX ENERGY FLAG 2");

        lightInstance.removeModifier(lightMod.getId());

        if (vars.emotionLevel > 0)
            lightInstance.addPermanentModifier(new AttributeModifier(lightMod.getId(), "lightMod " + 0, getMaxEnergy(vars), lightMod.getOperation()));
        System.out.println("MAX ENERGY FLAG 3");

        lightInstance.save();
    }

    /*public static int updateMaxEnergy(PlayerEntity player, PlayerVariables playerVars) {



        return (int)((playerVars.emotionLevel + 1) * 1.5) + 5;
    }*/

    /***
     * Adds a given amount of emotion points to a player.
     * @param player
     * @param value
     * @return Returns true when emotion level is increased.
     */
    public static boolean increaseEmotionPoints(PlayerEntity player, int value, boolean respectModifiers) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        boolean levelChange = false;

        if (respectModifiers) {
            if (player.getItemInHand(Hand.MAIN_HAND).getItem().equals(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get())) {
                value = (int) (value * 1.75f);
            }
        }




        playerVariables.emotionLevelProgress += value;

        if (playerVariables.emotionLevel >= 5) {
            playerVariables.emotionLevelProgress = Math.min(20, playerVariables.emotionLevelProgress);

            return false;
        }

        if (playerVariables.emotionLevelProgress >= getEmotionRequired(playerVariables)) {
            playerVariables.emotionLevelProgress -= getEmotionRequired(playerVariables);

            playerVariables.emotionLevel += 1;
            EntityTick.chargeBlips(player, playerVariables, 1);
            updateMaxEnergy(player, playerVariables);
            increaseEmotionLevel(player, playerVariables.emotionLevel);

            levelChange = true;
            playerVariables.syncPlayerVariables(player);
        }

        playerVariables.syncEmotionLevel(player);
        return levelChange;
    }

    /***
     * Removes a given amount of emotion points from a player.
     * @param player
     * @param value
     * @return Returns true when emotion level is decreased.
     */
    public static boolean decreaseEmotionPoints(PlayerEntity player, float value) {
        PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        boolean levelChange = false;
        playerVariables.emotionLevelProgress -= value;

        if (playerVariables.emotionLevel <= 0) {
            playerVariables.emotionLevelProgress = Math.max(playerVariables.emotionLevelProgress, 0);
        }

        if (playerVariables.emotionLevelProgress <= -10) {
            playerVariables.emotionLevel -= 1;
            playerVariables.emotionLevelProgress += getEmotionRequired(playerVariables) + 9;
            updateMaxEnergy(player, playerVariables);
            decreaseEmotionLevel(player, playerVariables.emotionLevel);

            levelChange = true;
            playerVariables.syncPlayerVariables(player);
        }

        playerVariables.syncEmotionLevel(player);

        return levelChange;
    }


    public static void increaseEmotionLevel(PlayerEntity player, int newLevel) {
        if (!player.level.isClientSide) {
            player.level.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), EgoWeaponsSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1.0F);
        } else {
            player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), EgoWeaponsSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1.0F, false);
        }

        if (!player.level.isClientSide())
            onEmotionUpDialogueEvaluation(player, newLevel);


        ItemStack chestPlateItem = player.getItemBySlot(EquipmentSlotType.CHEST);
        if (!chestPlateItem.isEmpty()) {
            if (chestPlateItem.getItem().equals(EgoWeaponsItems.FULLSTOP_SNIPER_SUIT.get()))
                EgoWeaponsEffects.POISE.get().increment(player, newLevel, newLevel * 2);

            if (chestPlateItem.getItem().equals(EgoWeaponsItems.STIGMA_WORKSHOP_SUIT.get()))
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(player, 0, newLevel);
        }
    }

    public static void decreaseEmotionLevel(PlayerEntity player, int newLevel) {

        if (newLevel == 0) {
            player.removeEffect(EgoWeaponsEffects.MAGIC_BULLET.get());
            player.removeEffect(EgoWeaponsEffects.POISE.get());
        }

    }
    public static void handleGuard(PlayerEntity player, float damage, float impact, boolean parried, float staggerMult, Entity source) {
        float mult = 1;
        if (parried) {
            StaggerSystem.healStagger(player, impact * 0.8f * staggerMult);
            mult = 1.25f;
        } else {
            StaggerSystem.reduceStagger(player, impact * 0.8f * staggerMult, true);
        }


        increaseEmotionPoints(player, (int)(damage*mult*2), true);
    }
    public static void handleGuard(PlayerEntity player, float damage, float impact, boolean parried, Entity source) {
        EmotionSystem.handleGuard(player, damage, impact, parried, 1, source);
    }
}
