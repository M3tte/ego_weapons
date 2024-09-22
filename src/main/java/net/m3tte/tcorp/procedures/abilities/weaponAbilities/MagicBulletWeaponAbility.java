package net.m3tte.tcorp.procedures.abilities.weaponAbilities;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.item.mimicry.MimicryItem;
import net.m3tte.tcorp.item.redmist.RedMistEGOSuit;
import net.m3tte.tcorp.item.redmist.RedMistJacket;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.particle.DamagefxParticle;
import net.m3tte.tcorp.potion.MagicBulletPotionEffect;
import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.m3tte.tcorp.procedures.abilities.AbilityUtils;
import net.m3tte.tcorp.procedures.abilities.ItemAbility;
import net.m3tte.tcorp.world.capabilities.SanitySystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.tcorp.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class MagicBulletWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        if (player.hasEffect(MagicBulletPotionEffect.get())) {
            extra = (player.getEffect(MagicBulletPotionEffect.get()).getAmplifier()) / 2;
        }
        return 6 + extra;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("magic_bullet_fire");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {
        int ampl = 1;

        if (player.hasEffect(MagicBulletPotionEffect.get())) {
            ampl = player.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 2;
        }

        if (playerVars.sanity <= ampl * 1.5f) {
            return AbilityUtils.getOverlay("warning");
        }

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.BETA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Fire Bullet";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.blips >= getBlipCost(player, playerVars)) {

            playerVars.blips-= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (player.hasEffect(MagicBulletPotionEffect.get())) {
                potency = player.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 2;
            }
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }
            if (!world.isClientSide()) {
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")),
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        potency >= 3 ? TCorpSounds.MAGIC_BULLET_AIM_2 : TCorpSounds.MAGIC_BULLET_AIM_1,
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);
            }


            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            player.getCooldowns().addCooldown(TCorpItems.MIMICRY.get(), (int) 20);
            playerVars.globalcooldown = 100;


            entitypatch.playAnimationSynchronized((potency >= 6) ? TCorpAnimations.MAGIC_BULLET_AIM_2 : TCorpAnimations.MAGIC_BULLET_AIM_1, 0.1f);
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }
            applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }


}
