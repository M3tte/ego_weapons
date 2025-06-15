package net.m3tte.ego_weapons.world.capabilities.threatlevel;

import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.minecraft.entity.EntityType;

import java.util.HashMap;

public class ThreatLevelSystem {
    private static HashMap<EntityType<?>, ThreatLevels> threatLevelRegistry = new HashMap<>();

    public static void registerThreatLevels() {
        threatLevelRegistry.put(EgoWeaponsEntities.DAWN_OF_GREEN_DOUBT.get(), ThreatLevels.TETH);
        threatLevelRegistry.put(EgoWeaponsEntities.CRAVING_BLOODBAG.get(), ThreatLevels.TETH);
        threatLevelRegistry.put(EgoWeaponsEntities.NOTHING_THERE.get(), ThreatLevels.ALEPH);
    }

    public static HashMap<EntityType<?>, ThreatLevels> getThreatRegistry() {
        return threatLevelRegistry;
    }
}
