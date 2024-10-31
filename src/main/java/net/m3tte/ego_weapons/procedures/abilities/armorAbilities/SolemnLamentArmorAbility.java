package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.potion.EthernalRestPotionEffect;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.item.TCorpStyles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class SolemnLamentArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 8;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("lament");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.BETA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Lament";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.blips >= 8) {
            manifestLament(player);
            playerVars.blips -= 8;
            playerVars.syncPlayerVariables(player);
        }
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
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

        if (entitypatch.getHoldingItemCapability(Hand.MAIN_HAND).getStyle(entitypatch).equals(TCorpStyles.DUAL_WIELDED))
            entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D2, 0f);

        SanitySystem.healSanity(player, 10);

        player.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPECIAL_RELOAD, 1, 1);
        player.addEffect(new EffectInstance(EthernalRestPotionEffect.get(), (int) 160, (int) 0, (false), (false)));

    }
}
