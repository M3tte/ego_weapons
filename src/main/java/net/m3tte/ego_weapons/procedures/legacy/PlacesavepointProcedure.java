package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.block.AnchorpointBlock;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;

public class PlacesavepointProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Placesavepoint!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Placesavepoint!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx != 0
				&& (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry != 0
				&& (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz != 0) {
			if (world
					.getBlockState(new BlockPos(
							(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
							(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
							(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz))
					.getDestroySpeed(world,
							new BlockPos(
									(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
									(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
									(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz)) < 2
					|| world.isEmptyBlock(new BlockPos(
							(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
							(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
							(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz))) {
				world.destroyBlock(new BlockPos(
						(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
						(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
						(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
						false);
				world.setBlock(new BlockPos(
						(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
						(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
						(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
						AnchorpointBlock.block.defaultBlockState(), 3);
				if (world instanceof World && !((World) world).isClientSide) {
					((World) world).playSound(null,
							new BlockPos(
									(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
									(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
									(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
									.getValue(new ResourceLocation("block.conduit.attack.target")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1);
				} else {
					((World) world).playLocalSound(
							((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx),
							((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry),
							((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
									.getValue(new ResourceLocation("block.conduit.attack.target")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
				}
			}
		}
	}
}
