package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.movesets.LiuSouth6MovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class FocusEmbers extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 4;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("focus_embers");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ZAYIN;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Focus  \nEmbers";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 4) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            EgoWeaponsEffects.DEFENSE_LEVEL_UP.get().increment(player, 0, 3);
            entitypatch.playAnimationSynchronized(LiuSouth6MovesetAnims.LIU_S6_FOCUS_EMBERS, 0.1f);

            if (player.level instanceof ServerWorld) {
                ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, player.getX(), (player.getY() + 1), player.getZ(), (int) 8, 0.4, 0.6, 0.4, 0);
            }

            playerVars.light -= getBlipCost(player, playerVars);

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
