package net.m3tte.ego_weapons.procedures.abilities.reloadAbilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeRepMovesetAnims;
import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.m3tte.ego_weapons.procedures.abilities.ReloadAbility;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class FullstopReloadAbility extends ReloadAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 4;
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
        return AbilityTier.BETA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Reload";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars, ItemStack ammoItem) {

        if (playerVars.blips >= getBlipCost(player, playerVars)) {

            playerVars.blips-= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;

            Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(player.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;

            AmmoSystem.reloadGun(player.getItemInHand(hand), ammoItem, player);

            if (!world.isClientSide()) {

                ((World) world).playSound(null, new BlockPos(x, y, z),
                        (net.minecraft.util.SoundEvent) EgoWeaponsSounds.CLICK,
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);

            }


            entitypatch.playAnimationSynchronized(FullstopOfficeRepMovesetAnims.FULLSTOP_REP_RELOAD, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);
        }
    }


}
