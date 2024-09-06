package net.m3tte.tcorp.procedures.legacy;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.particle.PowerdownParticle;
import net.m3tte.tcorp.particle.ReddamageslashParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;

public class RegretHitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure RegretHit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure RegretHit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(ReddamageslashParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
					(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
		}
		if (!(new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					return ((LivingEntity) entity).hasEffect(Effects.WEAKNESS);
				}
				return false;
			}
		}.check(entity))) {
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PowerdownParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
						(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 30, (int) 0, (false), (true)));
		}
	}
}
