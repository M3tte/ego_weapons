package net.m3tte.tcorp.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.BlackdamageParticle;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class DisposalcutterhitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Disposalcutterhit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Disposalcutterhit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
					(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
		}
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 4));
	}
}
