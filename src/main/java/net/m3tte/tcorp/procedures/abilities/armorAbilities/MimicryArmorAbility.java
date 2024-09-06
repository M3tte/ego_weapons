package net.m3tte.tcorp.procedures.abilities.armorAbilities;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.potion.ILoveYou;
import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.m3tte.tcorp.procedures.abilities.AbilityUtils;
import net.m3tte.tcorp.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

public class MimicryArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 10;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("i_love_you");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALPHA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "TERROR";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.blips >= 10) {
            playerVars.blips -= 10;
            AbilityUtils.applyBlipCooldown(40, playerVars);
            giveEffect(player);
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


    private static void giveEffect(PlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        player.inventory.setChanged();
        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
            ((ServerWorld) player.level).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
        }
        player.playSound(TCorpSounds.NOTHING_THERE_LOVE_YOU, 1, 1);
        player.playSound(TCorpSounds.OMINOUS_EFFECT, 1, 1);
        player.addEffect(new EffectInstance(ILoveYou.get(), (int) 300, (int) 0, (false), (false)));
    }
}
