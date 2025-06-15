package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
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

public class FirefistIgniteFuel extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("obligation_fullfillment");
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

            if (player.level instanceof ServerWorld) {
                ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, player.getX(), (player.getY() + 1), player.getZ(), (int) 8, 0.4, 0.6, 0.4, 0);
            }

            playerVars.light -= getBlipCost(player, playerVars);

            if (!player.level.isClientSide()) {
                MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
                if (mcserv != null)
                    mcserv.getPlayerList().broadcastMessage(
                            new StringTextComponent((player.getDisplayName().getString() + ">")).withStyle(TextFormatting.DARK_RED).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(" Let's see how much more of this you can take.").withStyle(TextFormatting.RED).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
                            player.getUUID());
            }

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
