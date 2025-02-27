package net.m3tte.ego_weapons.procedures.abilities;

import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;

public class ReloadAbility {

    public void trigger(PlayerEntity player, PlayerVariables playerVars, ItemStack ammoItem) {
        if (playerVars.blips > getBlipCost(player, playerVars)) {
            if (!player.level.isClientSide())
                player.displayClientMessage(new StringTextComponent("No reload ability.").withStyle(TextFormatting.ITALIC), (true));
        }
    }

    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {
        return null;
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
