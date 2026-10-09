package net.m3tte.ego_weapons.world.capabilities.threatlevel;

import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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


    public static ThreatLevels getThreatLevel(Entity e) {
        if (e.getPersistentData().contains("threatOverride")) {
            String threatLevel = e.getPersistentData().getString("threatOverride");
            ThreatLevels resolvedLevel = ThreatLevels.getFromString(threatLevel);

            if (resolvedLevel != null)
                return resolvedLevel;
        }


        return getThreatRegistry().getOrDefault(e.getType(), null);
    }


    // Sorts entities by risk level using max health as a tiebreaker.
    public static List<LivingEntity> sortEntitiesByRisk(Collection<LivingEntity> inlist) {
        return inlist.stream().sorted(
                Comparator.comparingInt((a) -> {
            // -1 : a < b | 0 : a == b | 1 : a > b
            ThreatLevels levelA = getThreatLevel((Entity) a);
            return levelA != null ? levelA.ordinal() : -1;
        })
                .thenComparingDouble((a) -> ((LivingEntity) a).getMaxHealth()))
                .collect(Collectors.toList());
    }
}
