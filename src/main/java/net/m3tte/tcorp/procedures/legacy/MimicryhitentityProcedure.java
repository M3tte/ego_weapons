package net.m3tte.tcorp.procedures.legacy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.DamagefxParticle;
import net.m3tte.tcorp.item.mimicry.MimicryarmorItem;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class MimicryhitentityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Mimicryhitentity!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Mimicryhitentity!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				TcorpMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Mimicryhitentity!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				TcorpMod.LOGGER.warn("Failed to load dependency itemstack for procedure Mimicryhitentity!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		Entity source = (Entity) dependencies.get("sourceentity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");






		if (!((source.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).globalcooldown > 0)) {
			if (source instanceof LivingEntity)
				((LivingEntity) source)
						.setHealth((float) (((source instanceof LivingEntity) ? ((LivingEntity) source).getHealth() : -1) + 1.5));
			if (MimicryarmorItem.body == ((source instanceof LivingEntity)
					? ((LivingEntity) source).getItemBySlot(EquipmentSlotType.CHEST)
					: ItemStack.EMPTY).getItem()) {
				if (source instanceof LivingEntity)
					((LivingEntity) source)
							.setHealth((float) (((source instanceof LivingEntity) ? ((LivingEntity) source).getHealth() : -1) + 0.7));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 2));
			}
			if (((source instanceof LivingEntity) ? ((LivingEntity) source).getHealth() : -1) > ((source instanceof LivingEntity)
					? ((LivingEntity) source).getMaxHealth()
					: -1)) {
				if (source instanceof LivingEntity)
					((LivingEntity) source)
							.setHealth((float) ((source instanceof LivingEntity) ? ((LivingEntity) source).getMaxHealth() : -1));
			}
			{
				double _setval = 10;
				source.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.globalcooldown = _setval;
					capability.syncPlayerVariables(source);
				});
			}
			if (source instanceof PlayerEntity)
				((PlayerEntity) source).getCooldowns().addCooldown(itemstack.getItem(), (int) 10);
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(DamagefxParticle.particle, (source.getX()), (source.getY() + 1),
						(source.getZ()), (int) 1, 0.2, 0.4, 0.2, 0);
			}
		}
	}
}
