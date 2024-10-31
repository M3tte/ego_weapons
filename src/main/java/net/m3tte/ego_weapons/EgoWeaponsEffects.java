package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.potion.countEffects.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EgoWeaponsEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, EgoWeaponsMod.MODID);

    public static void register(IEventBus bus) {
       EFFECTS.register(bus);
    }

    private static RegistryObject<CountPotencyStatus> registerEffect(String registryName, CountPotencyStatus status) {
        return EFFECTS.register(registryName, () -> status);
    }



    public static final RegistryObject<CountPotencyStatus> BLEED = registerEffect("bleed", new BleedEffect());
    public static final RegistryObject<CountPotencyStatus> BURN = registerEffect("burn", new BurnEffect());
    public static final RegistryObject<CountPotencyStatus> DARK_BURN = registerEffect("dark_burn", new DarkFlameEffect());
    public static final RegistryObject<CountPotencyStatus> SINKING = registerEffect("sinking", new SinkingEffect());
    public static final RegistryObject<CountPotencyStatus> RUPTURE = registerEffect("rupture", new RuptureEffect());
    public static final RegistryObject<CountPotencyStatus> PROTECTION = registerEffect("protection", new ProtectionEffect());
}
