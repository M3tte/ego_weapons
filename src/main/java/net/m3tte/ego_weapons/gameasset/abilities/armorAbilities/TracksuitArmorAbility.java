package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.LiuSouth6MovesetAnims;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class TracksuitArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return isAscended(player, playerVars) ? 5 : 4;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return isAscended(player, vars) ? AbilityUtils.getAbilityIcon("focus_embers") : AbilityUtils.getAbilityIcon("focus_embers");
    }

    @Override
    public AbilityTier getAbilityTier(PlayerEntity player, PlayerVariables playerVars) {
        return AbilityTier.ALEPH;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return isAscended(player, playerVars) ? "    Recall \n Memories" : "    Battle \n Instinct";
    }

    private static boolean isAscended(PlayerEntity player, PlayerVariables playerVars) {
        return EgoWeaponsEffects.LOSS_OF_SELF.get().getPotency(player) >= 25 && playerVars.firingMode;
    }


    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (!canTrigger(player, playerVars))
            return;

        if (isAscended(player, playerVars) && playerVars.light >= 5) {
            triggerAlt(player, playerVars);
        } else if (playerVars.light >= 4) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(player, 0, 3);
            EgoWeaponsEffects.PROTECTION.get().increment(player, 0, 1);
            EgoWeaponsEffects.POISE.get().increment(player, 3, 3);

            player.playSound(EgoWeaponsSounds.DICE_ROLL, 1, 1);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            playerVars.light -= getBlipCost(player, playerVars);

            playerVars.syncPlayerVariables(player);
        }
    }

    private void triggerAlt(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light >= 4) {
            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(player, 0, 5);
            EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(player, 0, 5);
            EgoWeaponsEffects.LOSS_OF_SELF.get().decrement(player, 0, 5);

            player.playSound(EgoWeaponsSounds.DICE_ROLL, 1, 1);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
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
