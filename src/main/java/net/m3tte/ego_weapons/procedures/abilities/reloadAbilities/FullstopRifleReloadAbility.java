package net.m3tte.ego_weapons.procedures.abilities.reloadAbilities;

import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ReloadAbility;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class FullstopRifleReloadAbility extends ReloadAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 1;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("reload_sl");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Reload";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars, ItemStack ammoItem) {

        if (playerVars.light >= getBlipCost(player, playerVars)) {

            playerVars.light -= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 25;

            if (!world.isClientSide()) {

                ((World) world).playSound(null, new BlockPos(x, y, z),
                        (net.minecraft.util.SoundEvent) EgoWeaponsSounds.CLICK,
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);

            }


            entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_RELOAD, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);
        }
    }

    public static StaticAnimation.Event[] reloadFSRound(float reloadTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(reloadTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int[] retAmmo = AmmoSystem.loadLimitedFromLeft(entity.getItemInHand(Hand.MAIN_HAND), null, entity, 1);


            if (!world.isClientSide() && retAmmo[1] > 0)
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.FULLSTOP_SNIPER_RELOAD,
                        SoundCategory.PLAYERS, 1f, (float) 1);

            if (retAmmo[0] == 0)
                entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_IDLE, 0.01f);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

}
