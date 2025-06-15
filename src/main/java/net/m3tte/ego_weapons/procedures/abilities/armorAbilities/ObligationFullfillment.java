package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
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

public class ObligationFullfillment extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 8;
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
        return "Obligation\nFulfillment";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 8) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_OPEN_CONTRACT, 0.1f);

            if (player.level instanceof ServerWorld) {
                ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, player.getX(), (player.getY() + 1), player.getZ(), (int) 8, 0.4, 0.6, 0.4, 0);
            }

            playerVars.light -= 8;
            playerVars.sanity -= EgoWeaponsAttributes.getMaxSanity(player) / 2;
            if (playerVars.sanity < 1)
                playerVars.sanity = 1;

            playerVars.stagger -= EgoWeaponsAttributes.getMaxStagger(player) / 2;
            if (playerVars.stagger < 1)
                playerVars.stagger = 1;

            if (!player.level.isClientSide()) {
                MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
                if (mcserv != null)
                    mcserv.getPlayerList().broadcastMessage(
                            new StringTextComponent((player.getDisplayName().getString() + ">")).withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(" Another broken contract, eh?").withStyle(TextFormatting.LIGHT_PURPLE).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
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
