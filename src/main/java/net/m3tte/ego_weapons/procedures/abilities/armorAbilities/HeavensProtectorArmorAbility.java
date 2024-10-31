package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.RedpowerupParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class HeavensProtectorArmorAbility extends ItemAbility {
    @Override
    public void trigger(PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVars) {
        if (playerVars.blips > 10) {
            playerVars.blips -= 10;
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            player.playSound(SoundEvents.CHORUS_FLOWER_GROW, 2, 0.8f);
            player.playSound(SoundEvents.CHAIN_BREAK, 1, 0.8f);

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
                ((ServerWorld) world).sendParticles(RedpowerupParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
            }

            player.addEffect(new EffectInstance(Effects.ABSORPTION, (int) 400, (int) 4, (false), (false)));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 80, (int) 0, (false), (false)));
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 40, (int) 3, (false), (false)));
            player.addEffect(new EffectInstance(Effects.WEAKNESS, (int) 40, (int) 3, (false), (false)));
            player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 200, (int) 0, (false), (false)));

            AbilityUtils.applyBlipCooldown(25, playerVars);

            playerVars.syncPlayerVariables(player);
        }
    }
}
