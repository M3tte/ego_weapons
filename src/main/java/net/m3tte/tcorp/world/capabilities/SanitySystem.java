package net.m3tte.tcorp.world.capabilities;

import net.m3tte.tcorp.TcorpModVariables;
import net.minecraft.entity.player.PlayerEntity;

public class SanitySystem {


    public static void damageSanity(PlayerEntity player, float amnt) {
        TcorpModVariables.PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables());

        playerVariables.sanity -= amnt;

        playerVariables.syncPlayerVariables(player);
    }

    public static void healSanity(PlayerEntity player, float amnt) {
        TcorpModVariables.PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables());

        playerVariables.sanity = Math.min(amnt + playerVariables.sanity, playerVariables.maxSanity);

        playerVariables.syncPlayerVariables(player);
    }

    public static double getSanity(PlayerEntity player) {
        TcorpModVariables.PlayerVariables playerVariables = player.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables());
        return playerVariables.sanity;
    }
}
