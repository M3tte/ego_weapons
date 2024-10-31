package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.particle.SparkparticleParticle;
import net.m3tte.ego_weapons.particle.BlackdamageParticle;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;
import java.util.Collection;

public class DuranitahitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Duranitahit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Duranitahit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Duranitahit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
					for (EffectInstance effect : effects) {
						if (effect.getEffect() == FuriosoPotionEffect.potion)
							return true;
					}
				}
				return false;
			}
		}.check(sourceentity)) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 6));
			if (sourceentity.getPersistentData().getDouble("furiosohits") <= 10) {
				sourceentity.getPersistentData().putDouble("furiosohits", (3 + sourceentity.getPersistentData().getDouble("furiosohits")));
				if (sourceentity.getPersistentData().getDouble("furiosohits") > 10) {
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(SparkparticleParticle.particle, (sourceentity.getX()),
								(sourceentity.getY() + sourceentity.getBbHeight() / 1.5), (sourceentity.getZ()), (int) 10, 0, 0, 0, 0.1);
					}
					if (entity instanceof LivingEntity)
						((LivingEntity) entity)
								.setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 3));
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 1, (false), (false)));
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
				}
			}
		} else {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 3));
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
						(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
			}
		}
	}
}
