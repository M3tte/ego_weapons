package net.m3tte.tcorp.world.capabilities.threatlevel;

import net.m3tte.tcorp.TCorpModEntities;
import net.minecraft.entity.EntityType;

import java.util.HashMap;

public class ThreatLevelSystem {
    private static HashMap<EntityType<?>, ThreatLevels> threatLevelRegistry = new HashMap<>();

    public static void registerThreatLevels() {
        threatLevelRegistry.put(TCorpModEntities.DAWN_OF_GREEN_DOUBT.get(), ThreatLevels.TETH);
        threatLevelRegistry.put(TCorpModEntities.NOTHING_THERE.get(), ThreatLevels.ALEPH);
    }

    public static HashMap<EntityType<?>, ThreatLevels> getThreatRegistry() {
        return threatLevelRegistry;
    }
}
