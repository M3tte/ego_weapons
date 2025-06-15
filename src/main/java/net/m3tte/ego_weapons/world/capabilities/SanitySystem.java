package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class SanitySystem {


    public static void damageSanity(PlayerEntity player, float amnt) {
        EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());

        // When wearing sunshower, half any damage to sanity.
        if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUNSHOWER_CLOAK.get())) {
            amnt *= 0.5f;
        }

        playerVariables.sanity -= amnt;

        if (player.hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get()) && playerVariables.sanity < 1)
            playerVariables.sanity = 1;

        playerVariables.syncSanity(player);
    }

    public static void healSanity(PlayerEntity player, float amnt) {
        EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());

        playerVariables.sanity = Math.min(amnt + playerVariables.sanity, EgoWeaponsAttributes.getMaxSanity(player));

        playerVariables.syncSanity(player);
    }

    public static double getSanity(PlayerEntity player) {
        EgoWeaponsModVars.PlayerVariables playerVariables = player.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());
        return playerVariables.sanity;
    }
}
