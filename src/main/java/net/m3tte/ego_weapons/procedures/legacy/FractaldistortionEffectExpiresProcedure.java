package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.particle.PurpleteleportParticle;
import net.m3tte.ego_weapons.particle.ClockparticleParticle;
import net.m3tte.ego_weapons.block.ClocksigilBlock;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;
import java.util.Collections;

public class FractaldistortionEffectExpiresProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure FractaldistortionEffectExpires!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure FractaldistortionEffectExpires!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure FractaldistortionEffectExpires!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure FractaldistortionEffectExpires!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure FractaldistortionEffectExpires!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if ((world.getBlockState(new BlockPos(
				(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
				(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
				(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz)))
				.getBlock() == ClocksigilBlock.block) {
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
				((ServerWorld) world).sendParticles(ClockparticleParticle.particle, x, (y + 1), z, (int) 10, 0.2, 0.5, 0.2, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PurpleteleportParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(ClockparticleParticle.particle,
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry + 1),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
						(int) 10, 0.2, 0.5, 0.2, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PurpleteleportParticle.particle,
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry + 1),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
						(int) 1, 0, 0, 0, 0);
			}
			{
				Entity _ent = entity;
				_ent.setPos(
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz));
				if (_ent instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) _ent).connection.teleport(
							((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx),
							((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry),
							((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
							_ent.xRot, _ent.yRot, Collections.emptySet());
				}
			}
			world.destroyBlock(new BlockPos(
					(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
					(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
					(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
					false);
			if (world instanceof World && !world.isClientSide()) {
				((World) world).playSound(null,
						new BlockPos(
								(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx,
								(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry,
								(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirx),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savediry),
						((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).savedirz),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 1, (float) 1, false);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).savedhealth));
			{
				double _setval = 0;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedhealth = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 0;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedirx = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 0;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savediry = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 0;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedirz = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
