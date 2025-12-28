package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.CrimsonfanparticleParticle;
import net.m3tte.ego_weapons.particle.RedpowerupParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class CrimsonKimonoArmorAbility extends ItemAbility {

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


        if (playerVars.light > 4) {
            playerVars.light -= 4;
            List<Entity> nearbyEntities = world
                    .getEntities(player,
                            new AxisAlignedBB(x - 6, y - 6, z - 6, x + 6, y + 6, z + 6), null)
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
                        }
                    }.compareDistOf(x, y, z)).collect(Collectors.toList());


            for (Entity entity : nearbyEntities) {
                if ((entity instanceof PlayerEntity || entity instanceof ServerPlayerEntity)) {
                    PlayerEntity playerFound = (PlayerEntity) entity;
                    EgoWeaponsModVars.PlayerVariables foundVars = playerFound.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

                    if (foundVars.teamid == playerVars.teamid) {
                        player.heal(6);
                        player.addEffect(new EffectInstance(Effects.REGENERATION, (int) 160, (int) 1, (false), (false)));
                        if (world instanceof ServerWorld) {
                            ((ServerWorld) world).sendParticles(CrimsonfanparticleParticle.particle, (entity.getX()),
                                    (entity.getY() + entity.getBbHeight() / 2), (entity.getZ()), (int) 8, 0.1, 0.1, 0.1,
                                    0.04);
                            ((ServerWorld) world).sendParticles(RedpowerupParticle.particle, (entity.getX()),
                                    (entity.getY() + entity.getBbHeight() / 2), (entity.getZ()), (int) 1, 0.1, 0.1, 0.1,
                                    0);
                        }
                    }
                }
            }
            player.playSound(EgoWeaponsSounds.FINGER_SNAP, 1, 1);
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(CrimsonfanparticleParticle.particle, x, (y + player.getBbHeight() / 2), z, (int) 8, 0.1, 0.4, 0.1,
                        0.04);
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
                ((ServerWorld) world).sendParticles(RedpowerupParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
            }

            AbilityUtils.applyBlipCooldown(15, playerVars);
            playerVars.syncPlayerVariables(player);
        }
    }
}
