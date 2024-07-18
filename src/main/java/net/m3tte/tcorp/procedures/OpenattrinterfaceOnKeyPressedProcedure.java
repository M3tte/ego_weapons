package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.TcorpModVariables;
import net.minecraft.entity.Entity;

import java.util.Map;

public class OpenattrinterfaceOnKeyPressedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure OpenattrinterfaceOnKeyPressed!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("vitality", ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).vitality));
		entity.getPersistentData().putDouble("insight", ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).insight));
		entity.getPersistentData().putDouble("determination", ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).determination));
		entity.getPersistentData().putDouble("proficiency", ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).proficiency));
		entity.getPersistentData().putDouble("credits", ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).credits));



	}
}
