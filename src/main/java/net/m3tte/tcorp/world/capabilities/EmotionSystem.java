package net.m3tte.tcorp.world.capabilities;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import net.m3tte.tcorp.potion.MagicBulletPotionEffect;
import net.m3tte.tcorp.procedures.BlipTick;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;

public class EmotionSystem {

    public static int getEmotionRequired(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return getEmotionRequired(playerVariables);
    }

    public static int getEmotionLevel(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return playerVariables.emotionLevel;
    }

    public static double getEmotionPoints(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return playerVariables.emotionLevelProgress;
    }

    public static int getEmotionRequired(PlayerVariables playerVars) {
        return playerVars.emotionLevel * 50 + 50;
    }

    public static int getMaxEnergy(PlayerEntity player) {
        PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        return getMaxEnergy(playerVariables);
    }

    public static int getMaxEnergy(PlayerVariables playerVars) {
        return (int)((playerVars.emotionLevel + 1) * 1.5) + 5;
    }

    /***
     * Adds a given amount of emotion points to a player.
     * @param player
     * @param value
     * @return Returns true when emotion level is increased.
     */
    public static boolean increaseEmotionPoints(PlayerEntity player, int value) {
        PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        boolean levelChange = false;
        playerVariables.emotionLevelProgress += value;

        if (playerVariables.emotionLevel >= 5) {
            playerVariables.emotionLevelProgress = Math.min(20, playerVariables.emotionLevelProgress);

            return false;
        }

        if (playerVariables.emotionLevelProgress >= getEmotionRequired(playerVariables)) {
            playerVariables.emotionLevelProgress -= getEmotionRequired(playerVariables);

            playerVariables.emotionLevel += 1;
            BlipTick.chargeBlips(player, playerVariables, 1);
            playerVariables.maxblips = getMaxEnergy(playerVariables);
            increaseEmotionLevel(player);

            levelChange = true;
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
    public static boolean decreaseEmotionPoints(PlayerEntity player, int value) {
        PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        boolean levelChange = false;
        playerVariables.emotionLevelProgress -= value;

        if (playerVariables.emotionLevel <= 0) {
            playerVariables.emotionLevelProgress = Math.max(playerVariables.emotionLevelProgress, 0);
        }

        if (playerVariables.emotionLevelProgress <= -10) {
            playerVariables.emotionLevel -= 1;
            playerVariables.emotionLevelProgress += getEmotionRequired(playerVariables) + 9;

            decreaseEmotionLevel(player, playerVariables.emotionLevel);

            levelChange = true;
        }

        playerVariables.syncEmotionLevel(player);

        return levelChange;
    }


    public static void increaseEmotionLevel(PlayerEntity player) {
        if (!player.level.isClientSide) {
            player.level.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), TCorpSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1.0F);
        } else {
            player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), TCorpSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1.0F, false);
        }
    }

    public static void decreaseEmotionLevel(PlayerEntity player, int newLevel) {

        if (newLevel == 0) {
            player.removeEffect(MagicBulletPotionEffect.get());
        }

    }

}
