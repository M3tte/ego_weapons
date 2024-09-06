package net.m3tte.tcorp.procedures.legacy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;

import net.m3tte.tcorp.particle.JustiziaparticleParticle;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class JustiziafxActiveTickConditionProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure JustiziafxActiveTickCondition!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure JustiziafxActiveTickCondition!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure JustiziafxActiveTickCondition!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure JustiziafxActiveTickCondition!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(JustiziaparticleParticle.particle, x, (y + 2), z, (int) 1, 3, 3, 3, 0);
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 2, 0.2, 0.5, 0.2, 0);
		}
	}
}
