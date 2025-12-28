package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

import static net.m3tte.ego_weapons.world.capabilities.DialogueSystem.onHitSanityDialogueEvaluation;

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

        onHitSanityDialogueEvaluation(player, (float) (playerVariables.sanity / EgoWeaponsAttributes.getMaxSanity(player)));
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
