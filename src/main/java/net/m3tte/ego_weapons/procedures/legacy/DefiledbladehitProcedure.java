package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.particle.ReddamageslashParticle;
import net.m3tte.ego_weapons.particle.PoisonapplyParticle;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;
import java.util.Collection;

public class DefiledbladehitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Defiledbladehit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Defiledbladehit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Defiledbladehit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(ReddamageslashParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
					(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
		}
		if (!(new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
					for (EffectInstance effect : effects) {
						if (effect.getEffect() == Effects.POISON)
							return true;
					}
				}
				return false;
			}
		}.check(entity))) {
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PoisonapplyParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
						(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, (int) 120, (int) 1, (false), (true)));
			if (sourceentity instanceof LivingEntity)
				((LivingEntity) sourceentity).addEffect(new EffectInstance(Effects.DIG_SPEED, (int) 20, (int) 1, (false), (true)));
		}
	}
}
