//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsSkills;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class EgoWeaponsEFLoader {
    public EgoWeaponsEFLoader() {
    }

    public static void registerStuffs(IEventBus bus) {
        System.out.println("Registering stuffs....");
        bus.addListener(EgoWeaponsAnimations::registerAnimations);
        bus.addListener(EgoWeaponsSkills::registerSkills);
        bus.addListener(EgoWeaponsCapabilityPresets::register);

        EgoWeaponsCategories.ENUM_MANAGER.loadPreemptive(EgoWeaponsCategories.class);

        EgoWeaponsAttributes.ATTRIBUTES.register(bus);
        EgoWeaponsParticles.PARTICLES.register(bus);
        EgoWeaponsItems.ITEMS.register(bus);
        EgoWeaponsEntities.ENTITIES.register(bus);


    }
}
