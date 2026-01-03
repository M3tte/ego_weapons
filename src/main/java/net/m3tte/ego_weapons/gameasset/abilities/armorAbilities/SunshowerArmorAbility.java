package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.SunshowerMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class SunshowerArmorAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("get_away");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.HE;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Spreading \nSorrow";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= getBlipCost(player, playerVars)) {




            playerVars.light -= getBlipCost(player, playerVars);
            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;


            entitypatch.playAnimationSynchronized(SunshowerMovesetAnims.SUNSHOWER_GET_AWAY, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            //EgoWeaponsEffects.SINKING.get().increment(player, 1, 5);
            AbilityUtils.applyBlipCooldown(5, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }


}
