package net.m3tte.ego_weapons.gameasset.abilities;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;

public class AbilityUtils {
    public static boolean applyBlipCooldown(int threshold, PlayerVariables vars) {
        if (vars.blipcooldown < threshold) {
            vars.blipcooldown = threshold;
            return true;
        }
        return false;
    }

    public static ResourceLocation getAbilityIcon(String ability) {
        return new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/"+ability+".png");
    }

    public static ResourceLocation getOverlay(String overlay) {
        return new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/overlays/"+overlay+".png");
    }









    public static BlockPos findValidPlacement(World world, BlockPos target, float hardnessThreshold) {

        BlockPos positionPlaceholder = target;
        if (world.isEmptyBlock(positionPlaceholder)) {
            if (!world.isEmptyBlock(positionPlaceholder.below()))
                return positionPlaceholder;
        }

        positionPlaceholder = positionPlaceholder.above();

        if (world.isEmptyBlock(positionPlaceholder)) {
            if (!world.isEmptyBlock(positionPlaceholder.below()))
                return positionPlaceholder;
        }

        positionPlaceholder = target.below();

        if (world.isEmptyBlock(positionPlaceholder)) {
            if (!world.isEmptyBlock(positionPlaceholder.below()))
                return positionPlaceholder;
        }


        positionPlaceholder = target;
        if (world.getBlockState(positionPlaceholder).getDestroySpeed(world, positionPlaceholder) <= hardnessThreshold) {
            if (!world.isEmptyBlock(positionPlaceholder.below())) {
                world.levelEvent(2001, positionPlaceholder, Block.getId(world.getBlockState(positionPlaceholder)));
                return positionPlaceholder;
            }

        }

        positionPlaceholder = positionPlaceholder.above();

        if (world.getBlockState(positionPlaceholder).getDestroySpeed(world, positionPlaceholder) <= hardnessThreshold) {
            if (!world.isEmptyBlock(positionPlaceholder.below())) {
                world.levelEvent(2001, positionPlaceholder, Block.getId(world.getBlockState(positionPlaceholder)));
                return positionPlaceholder;
            }
        }

        positionPlaceholder = target.below();

        if (world.getBlockState(positionPlaceholder).getDestroySpeed(world, positionPlaceholder) <= hardnessThreshold) {
            if (!world.isEmptyBlock(positionPlaceholder.below())) {
                world.levelEvent(2001, positionPlaceholder, Block.getId(world.getBlockState(positionPlaceholder)));
                return positionPlaceholder;
            }
        }

        return null;
    }

    public static void t(PlayerEntity player) {

    }
}
