package net.m3tte.tcorp.procedures.abilities.armorAbilities;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.block.ClocksigilBlock;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.particle.ClockparticleParticle;
import net.m3tte.tcorp.potion.FractaldistortionPotionEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import static net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import static net.m3tte.tcorp.procedures.abilities.AbilityUtils.applyBlipCooldown;
import static net.m3tte.tcorp.procedures.abilities.AbilityUtils.findValidPlacement;

public class BetaSuitArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 6;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) { // TODO: Improve / Update / Fix

        if (playerVars.blips > 9) {
            playerVars.blips -= 9;
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            if (!world.isClientSide()) {
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
                }
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(ClockparticleParticle.particle, x, (y + 1), z, (int) 8, 0.2, 0.5, 0.2, 0);
                }
            }

            player.playSound(TCorpSounds.CLOCK_TICKING, 1, 1);
            player.playSound(TCorpSounds.RESULT_POSITIVE, 1, 1);

            BlockPos pos = findValidPlacement(world, player.blockPosition(), 0.6f);

            if (world.isEmptyBlock(pos)) {
                world.setBlock(pos, ClocksigilBlock.block.defaultBlockState(), 1);
            }

            playerVars.savedhealth = player.getHealth();
            playerVars.savedirx = player.getX();
            playerVars.savediry = player.getY();
            playerVars.savedirz = player.getZ();

            player.addEffect(new EffectInstance(FractaldistortionPotionEffect.potion, (int) 300, (int) 0, (false), (false)));

            applyBlipCooldown(40, playerVars);

            playerVars.syncPlayerVariables(player);
        }
    }
}
