package net.m3tte.tcorp.procedures.abilities.armorAbilities;

import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.particle.ShadowpuffParticle;
import net.m3tte.tcorp.potion.ShadowIncarnate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import static net.m3tte.tcorp.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class CursedLabCoatArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, TcorpModVariables.PlayerVariables playerVars) {
        return 6;
    }

    @Override
    public void trigger(PlayerEntity player, TcorpModVariables.PlayerVariables playerVars) {
        if (!player.hasEffect(ShadowIncarnate.potion)) {
            if (playerVars.blips > 5) {
                playerVars.blips -= 5;
                World world = player.level;
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
                    ((ServerWorld) world).sendParticles(ShadowpuffParticle.particle, x, (y + 1), z, (int) 15, 0.2, 0.6, 0.2, 0);
                }

                player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, 1, 1);

                player.addEffect(new EffectInstance(ShadowIncarnate.potion, (int) 60, (int) 0, (false), (false)));
                player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 60, (int) 0, (false), (false)));
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, (int) 60, (int) 1, (false), (false)));
                player.addEffect(new EffectInstance(Effects.INVISIBILITY, (int) 60, (int) 4, (false), (false)));

                applyBlipCooldown(16, playerVars);

                playerVars.syncPlayerVariables(player);
            }
        }
    }
}
