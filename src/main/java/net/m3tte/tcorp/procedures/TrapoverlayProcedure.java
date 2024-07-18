package net.m3tte.tcorp.procedures;

import net.minecraft.world.IWorld;

import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class TrapoverlayProcedure {

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Trapoverlay!");
			return false;
		}
		IWorld world = (IWorld) dependencies.get("world");
		return (TcorpModVariables.MapVariables.get(world).screenoverlaytype).equals("entrapment");
	}
}
