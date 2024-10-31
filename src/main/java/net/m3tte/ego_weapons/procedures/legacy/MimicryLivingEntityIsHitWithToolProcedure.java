package net.m3tte.ego_weapons.procedures.legacy;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.particle.DamagefxParticle;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;

public class MimicryLivingEntityIsHitWithToolProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure MimicryLivingEntityIsHitWithTool!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure MimicryLivingEntityIsHitWithTool!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure MimicryLivingEntityIsHitWithTool!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency itemstack for procedure MimicryLivingEntityIsHitWithTool!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (!((sourceentity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).globalcooldown > 0)) {
			if (sourceentity instanceof LivingEntity)
				((LivingEntity) sourceentity)
						.setHealth((float) (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1) + 1.5));
			if (EgoWeaponsItems.MIMICRY_CHESTPLATE.get() == ((sourceentity instanceof LivingEntity)
					? ((LivingEntity) sourceentity).getItemBySlot(EquipmentSlotType.CHEST)
					: ItemStack.EMPTY).getItem()) {
				if (sourceentity instanceof LivingEntity)
					((LivingEntity) sourceentity)
							.setHealth((float) (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1) + 0.7));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 2));
			}
			if (((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHealth() : -1) > ((sourceentity instanceof LivingEntity)
					? ((LivingEntity) sourceentity).getMaxHealth()
					: -1)) {
				if (sourceentity instanceof LivingEntity)
					((LivingEntity) sourceentity)
							.setHealth((float) ((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getMaxHealth() : -1));
			}
			{
				double _setval = 10;
				sourceentity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.globalcooldown = _setval;
					capability.syncPlayerVariables(sourceentity);
				});
			}
			if (sourceentity instanceof PlayerEntity)
				((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 10);
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(DamagefxParticle.particle, (sourceentity.getX()), (sourceentity.getY() + 1),
						(sourceentity.getZ()), (int) 1, 0.2, 0.4, 0.2, 0);
			}
		}
	}
}
