package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.GalaxyparticleParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GalaxyGarbArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVars) {
        return 6;
    }


    @Override
    public void trigger(PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVars) {
        World world = player.level;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();



        if (playerVars.savedhealth == -1) {
            playerVars.syncPlayerVariables(player);
        } else if (playerVars.light > 5) {
            playerVars.light -= 5;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
                ((ServerWorld) world).sendParticles(GalaxyparticleParticle.particle, x, (y + 1), z, (int) 8, 0.2, 0.5, 0.2, 0.05);
            }
            playerVars.savedirx = x;
            playerVars.savediry = y;
            playerVars.savedirz = z;
            playerVars.savedhealth = -1;

            player.playSound(EgoWeaponsSounds.FINGER_SNAP, 1, 1);
            AbilityUtils.applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);
        }
    }
}
