package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.movesets.SolemnLamentMovesetAnims;
import net.m3tte.ego_weapons.potion.EternalRestPotionEffect;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsStyles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class SolemnLamentArmorAbilityC extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 7;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("lament");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Eternal Rest";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 8) {
            manifestLament(player);
            playerVars.light -= 8;
            playerVars.syncPlayerVariables(player);
        }
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }


    private static void manifestLament(PlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        player.inventory.setChanged();
        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_BURST_HIT.get(), x, y, z, (int) 1, 0.4, 0.6, 0.4, 0);
        }

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch.getHoldingItemCapability(Hand.MAIN_HAND).getStyle(entitypatch).equals(EgoWeaponsStyles.DUAL_WIELDED))
            entitypatch.playAnimationSynchronized(SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_D2, 0f);

        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.solemn_lament_armor.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

        SanitySystem.healSanity(player, 10);

        player.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPECIAL_RELOAD, 1, 1);
        player.addEffect(new EffectInstance(EternalRestPotionEffect.get(), (int) 160, (int) 0, (false), (false)));

    }
}
