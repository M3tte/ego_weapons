package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.item.SuitItem;
import net.m3tte.tcorp.potion.OrlandoPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class BlacksilencesuitHelmetTickEventProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure BlacksilencesuitHelmetTickEvent!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (!(new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					return (((LivingEntity) _entity).hasEffect(OrlandoPotionEffect.potion));
				}
				return false;
			}
		}.check(entity))) {
			if (entity instanceof PlayerEntity) {
				ItemStack _stktoremove = new ItemStack(SuitItem.helmet);
				((PlayerEntity) entity).inventory.removeItem(_stktoremove);
			}
		}
	}
}
