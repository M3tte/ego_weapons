package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.command.FunctionObject;

import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Optional;
import java.util.Map;

public class FreischutzarmorabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Freischutzarmorability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure Freischutzarmorability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure Freischutzarmorability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure Freischutzarmorability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Freischutzarmorability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (!entity.level.isClientSide && entity.level.getServer() != null) {
			Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("tcorp:freischutzarmorshoot"));
			if (_fopt.isPresent()) {
				FunctionObject _fobj = _fopt.get();
				entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
			}
		}
		{
			double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).light - 4);
			entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.light = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlipeffectParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
					(entity.getZ()), (int) 4, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
		}
		if (!world.isClientSide()) {
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.crit")),
					SoundCategory.NEUTRAL, (float) 1, (float) 1);
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.activate")),
					SoundCategory.NEUTRAL, (float) 1, (float) 1);
			((World) world).playSound(null, new BlockPos(x, y, z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:woosh")),
					SoundCategory.NEUTRAL, (float) 1, (float) 1);
		}
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).blipcooldown < 60) {
			{
				double _setval = 60;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
