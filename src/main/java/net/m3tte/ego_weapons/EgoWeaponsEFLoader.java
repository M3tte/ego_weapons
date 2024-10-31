//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.world.capabilities.item.TCorpCapabilityPresets;
import net.m3tte.ego_weapons.world.capabilities.item.TCorpCategories;
import net.minecraftforge.eventbus.api.IEventBus;

public class EgoWeaponsEFLoader {
    public EgoWeaponsEFLoader() {
    }

    public static void registerStuffs(IEventBus bus) {
        bus.addListener(EgoWeaponsAnimations::registerAnimations);
        bus.addListener(EgoWeaponsSkills::registerSkills);
        bus.addListener(TCorpCapabilityPresets::register);

        TCorpCategories.ENUM_MANAGER.loadPreemptive(TCorpCategories.class);

        EgoWeaponsItems.ITEMS.register(bus);
        EgoWeaponsParticles.PARTICLES.register(bus);
        EgoWeaponsEntities.ENTITIES.register(bus);


    }
}
