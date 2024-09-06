package net.m3tte.tcorp.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.block.ClocksigilBlock;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class FractaldistortionEffectStartedappliedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure FractaldistortionEffectStartedapplied!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure FractaldistortionEffectStartedapplied!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables())).savedirx != 0
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).savediry != 0
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).savedirz != 0) {
			if (world
					.getBlockState(new BlockPos(
							(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
							(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savediry,
							(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirz))
					.getDestroySpeed(world,
							new BlockPos(
									(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
									(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new TcorpModVariables.PlayerVariables())).savediry,
									(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new TcorpModVariables.PlayerVariables())).savedirz)) < 2
					|| world.isEmptyBlock(new BlockPos(
							(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
							(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savediry,
							(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirz))) {
				world.destroyBlock(new BlockPos(
						(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
						(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savediry,
						(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
						false);
				world.setBlock(new BlockPos(
						(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
						(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savediry,
						(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
						ClocksigilBlock.block.defaultBlockState(), 3);
				if (world instanceof World && !((World) world).isClientSide) {
					((World) world).playSound(null,
							new BlockPos(
									(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
									(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new TcorpModVariables.PlayerVariables())).savediry,
									(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
									.getValue(new ResourceLocation("block.enchantment_table.use")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1);
				} else {
					((World) world).playLocalSound(
							((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirx),
							((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savediry),
							((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
									.getValue(new ResourceLocation("block.enchantment_table.use")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
				}
			}
		}
	}
}
