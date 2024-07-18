//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp;

import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.gameasset.TCorpSkills;
import net.m3tte.tcorp.world.capabilities.item.TCorpCapabilityPresets;
import net.m3tte.tcorp.world.capabilities.item.TCorpCategories;
import net.minecraftforge.eventbus.api.IEventBus;

public class TCorpEpicFightLoader {
    public TCorpEpicFightLoader() {
    }

    public static void registerStuffs(IEventBus bus) {
        bus.addListener(TCorpAnimations::registerAnimations);
        bus.addListener(TCorpSkills::registerSkills);
        bus.addListener(TCorpCapabilityPresets::register);

        TCorpParticleRegistry.PARTICLES.register(bus);

        TCorpCategories.ENUM_MANAGER.loadPreemptive(TCorpCategories.class);
    }
}
