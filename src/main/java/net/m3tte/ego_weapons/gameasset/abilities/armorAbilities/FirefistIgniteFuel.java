package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class FirefistIgniteFuel extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("fuel_ignition");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.HE;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Fuel\nIgnition";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 6) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            entitypatch.playAnimationSynchronized(FirefistMovesetAnims.FIREFIST_IGNITION, 0.1f);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            playerVars.light -= getBlipCost(player, playerVars);

            DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.firefist_armor.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);

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



}
