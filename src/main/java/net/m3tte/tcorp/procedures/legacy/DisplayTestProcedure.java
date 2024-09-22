package net.m3tte.tcorp.procedures.legacy;

import net.m3tte.tcorp.TCorpItems;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.item.blackSilence.weapons.AtelierlogicpistolsItem;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class DisplayTestProcedure {

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure DisplayTest!");
			return false;
		}
		Entity entity = (Entity) dependencies.get("entity");
		return TCorpItems.ATELIER_LOGIC_PISTOLS.get() == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY);
	}
}
