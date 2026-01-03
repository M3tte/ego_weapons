package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.potion.ManifestEgoPotionEffect;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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

        World world = player.level;
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), 6, 0, 0.3, 0, 0.05);
            ((ServerWorld) player.level).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
        }



        player.playSound(SoundEvents.FIRECHARGE_USE, 1, 1);
        player.addEffect(new EffectInstance(ManifestEgoPotionEffect.potion, (int) 3600, (int) 0, (false), (false)));
        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.red_mist.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);

    }
}
