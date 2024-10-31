package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraft.world.IWorld;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;

public class TrespasseroverlayDisplayOverlayIngameProcedure {

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure TrespasseroverlayDisplayOverlayIngame!");
			return false;
		}
		IWorld world = (IWorld) dependencies.get("world");
		return (EgoWeaponsModVars.MapVariables.get(world).screenoverlaytype).equals("judgement");
	}
}
