package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.item.HeavensprotectorItem;
import net.m3tte.tcorp.item.HeavenswatcherItem;
import net.m3tte.tcorp.particle.PowerdownParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;

public class PutridrootstouchProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Putridrootstouch!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Putridrootstouch!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if (entity.isAlive() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) > 0) {
			if (!(HeavensprotectorItem.body == ((entity instanceof LivingEntity)
					? ((LivingEntity) entity).getItemBySlot(EquipmentSlotType.CHEST)
					: ItemStack.EMPTY).getItem()
					|| HeavenswatcherItem.block == ((entity instanceof LivingEntity)
							? ((LivingEntity) entity).getMainHandItem().getItem()
							: ItemStack.EMPTY.getItem()))) {
				entity.setDeltaMovement((entity.getDeltaMovement().x * 0), (entity.getDeltaMovement().y * 0), (entity.getDeltaMovement().z * 0));
				if (!(new Object() {
					boolean check(Entity _entity) {
						if (_entity instanceof LivingEntity) {
							return ((LivingEntity) entity).hasEffect(Effects.DIG_SLOWDOWN);
						}
						return false;
					}
				}.check(entity))) {
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 60, (int) 2, (false), (false)));
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 2, (false), (false)));
					if (entity instanceof LivingEntity)
						((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 1, (false), (false)));
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(PowerdownParticle.particle, (entity.getX()),
								(entity.getY() + entity.getBbHeight() / 2), (entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4),
								(entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
					}
				}
			} else {
				entity.setDeltaMovement((entity.getDeltaMovement().x() * 0.01), (entity.getDeltaMovement().y() * 0.01), (entity.getDeltaMovement().z() * 0.01));
			}
		}
	}
}
