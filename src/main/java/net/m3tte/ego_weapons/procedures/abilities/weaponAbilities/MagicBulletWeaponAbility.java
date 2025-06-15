package net.m3tte.ego_weapons.procedures.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.movesets.MagicBulletMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.DamagefxParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class MagicBulletWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        extra = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(player) / 2;
        return 6 + extra;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("magic_bullet_fire");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {
        int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(player) + 1;

        if (playerVars.sanity <= ampl * 1.5f) {
            return AbilityUtils.getOverlay("warning");
        }

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Fire Magic\nBullet";
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
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(player) + 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }
            if (!world.isClientSide()) {
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")),
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        potency >= 3 ? EgoWeaponsSounds.MAGIC_BULLET_AIM_2 : EgoWeaponsSounds.MAGIC_BULLET_AIM_1,
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);
            }


            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            player.getCooldowns().addCooldown(EgoWeaponsItems.MAGIC_BULLET.get(), (int) 20);
            playerVars.globalcooldown = 100;
            entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0));

            entitypatch.playAnimationSynchronized((potency >= 6) ? MagicBulletMovesetAnims.MAGIC_BULLET_AIM_2 : MagicBulletMovesetAnims.MAGIC_BULLET_AIM_1, 0.1f);
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }
            applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }


}
