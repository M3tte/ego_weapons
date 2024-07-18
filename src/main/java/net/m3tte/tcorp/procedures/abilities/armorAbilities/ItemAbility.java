package net.m3tte.tcorp.procedures.abilities.armorAbilities;

import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static net.m3tte.tcorp.TcorpModVariables.PlayerVariables;

public class ItemAbility {

    public void trigger(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.blips > getBlipCost(player, playerVars)) {
            if (!player.level.isClientSide())
                player.displayClientMessage(new StringTextComponent("No armor ability.").withStyle(TextFormatting.ITALIC), (true));
        }
    }

    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 0;
    }

    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.globalcooldown > 0) {
            return 0.0f;
        }
        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return null;
    }


    public AbilityTier getAbilityTier() {
        return AbilityTier.OMEGA;
    }

    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "";
    }
}
