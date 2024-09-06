package net.m3tte.tcorp.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.PurpleteleportParticle;
import net.m3tte.tcorp.particle.GalaxyparticleParticle;
import net.m3tte.tcorp.block.AnchorpointBlock;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;
import java.util.Collections;

public class TptosavepointProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Tptosavepoint!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Tptosavepoint!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Tptosavepoint!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Tptosavepoint!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Tptosavepoint!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if ((world.getBlockState(new BlockPos(
				(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables())).savedirx,
				(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables())).savediry,
				(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).savedirz)))
				.getBlock() == AnchorpointBlock.block) {
			if (world instanceof World && !world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2, false);
			}
			if (world instanceof World && !world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 1, (float) 1, false);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(GalaxyparticleParticle.particle, x, (y + 1), z, (int) 10, 0.2, 0.5, 0.2, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PurpleteleportParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(GalaxyparticleParticle.particle,
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirx),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savediry + 1),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
						(int) 10, 0.2, 0.5, 0.2, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PurpleteleportParticle.particle,
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirx),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savediry + 1),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
						(int) 1, 0, 0, 0, 0);
			}
			{
				Entity _ent = entity;
				_ent.setPos(
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirx),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savediry),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirz));
				if (_ent instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) _ent).connection.teleport(
							((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirx),
							((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savediry),
							((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
							_ent.xRot, _ent.yRot, Collections.emptySet());
				}
			}
			world.destroyBlock(new BlockPos(
					(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
					(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new TcorpModVariables.PlayerVariables())).savediry,
					(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
					false);
			if (world instanceof World && !world.isClientSide()) {
				((World) world).playSound(null,
						new BlockPos(
								(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new TcorpModVariables.PlayerVariables())).savedirx,
								(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new TcorpModVariables.PlayerVariables())).savediry,
								(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirx),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savediry),
						((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).savedirz),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 1, (float) 1, false);
			}
			{
				double _setval = 0;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedirx = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 0;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savediry = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 0;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedirz = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 0;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedhealth = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
