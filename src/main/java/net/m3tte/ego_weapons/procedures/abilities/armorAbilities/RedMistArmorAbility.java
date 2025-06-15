package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.potion.ManifestEgoPotionEffect;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class RedMistArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 10;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("manifest_ego");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALEPH;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Manifest E.G.O";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 10) {
            manifestEGO(player);
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


    private static void manifestEGO(PlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        player.setItemSlot(EquipmentSlotType.LEGS, EgoWeaponsItems.RED_MIST_EGO_LEGGINGS.get().getDefaultInstance());
        player.setItemSlot(EquipmentSlotType.CHEST, EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get().getDefaultInstance());
        player.setItemSlot(EquipmentSlotType.HEAD, EgoWeaponsItems.RED_MIST_EGO_HELMET.get().getDefaultInstance());
        player.inventory.setChanged();
        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
            ((ServerWorld) player.level).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
        }

        player.playSound(SoundEvents.FIRECHARGE_USE, 1, 1);
        player.addEffect(new EffectInstance(ManifestEgoPotionEffect.potion, (int) 3600, (int) 0, (false), (false)));
        if (!player.level.isClientSide()) {
            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            if (mcserv != null)
                mcserv.getPlayerList().broadcastMessage(
                        new StringTextComponent((player.getDisplayName().getString() + ">")).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.DARK_RED).append(new StringTextComponent(" The real fight starts now...").withStyle(TextFormatting.RED).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
                        player.getUUID());
        }
    }
}
