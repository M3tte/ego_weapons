package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.potion.countEffects.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
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

    private static RegistryObject<Effect> registerEffect(String registryName, Effect status) {
        return EFFECTS.register(registryName, () -> status);
    }

    public static final RegistryObject<CountPotencyStatus> BLEED = registerEffect("bleed", new BleedEffect());
    public static final RegistryObject<CountPotencyStatus> BURN = registerEffect("burn", new BurnEffect());
    public static final RegistryObject<CountPotencyStatus> DARK_BURN = registerEffect("dark_burn", new DarkFlameEffect());
    public static final RegistryObject<CountPotencyStatus> SINKING = registerEffect("sinking", new SinkingEffect());
    public static final RegistryObject<CountPotencyStatus> RUPTURE = registerEffect("rupture", new RuptureEffect());
    public static final RegistryObject<CountPotencyStatus> PROTECTION = registerEffect("protection", new ProtectionEffect());
    public static final RegistryObject<CountPotencyStatus> TREMOR = registerEffect("tremor", new TremorEffect());
    public static final RegistryObject<CountPotencyStatus> TREMOR_DECAY = registerEffect("tremor_decay", new TremorDecayEffect());
    public static final RegistryObject<CountPotencyStatus> OBLIGATION_FULLFILLMENT = registerEffect("obligation_fullfillment", new ObligationFullfillmentEffect());
    public static final RegistryObject<CountPotencyStatus> RESILIENCE = registerEffect("resilience", new ResilienceEffect());
    public static final RegistryObject<CountPotencyStatus> MAGIC_BULLET = registerEffect("magic_bullet", new MagicBulletEffect());
    public static final RegistryObject<CountPotencyStatus> POISE = registerEffect("poise", new PoiseEffect());
    public static final RegistryObject<CountPotencyStatus> TARGET_SPOTTED = registerEffect("target_spotted", new TargetSpottedEffect());
    public static final RegistryObject<CountPotencyStatus> ASSIST_FIRE = registerEffect("assist_fire", new AssistFireEffect());

    public static final RegistryObject<CountPotencyStatus> OFFENSE_LEVEL_DOWN = registerEffect("offense_down", new OffenseDownEffect());
    public static final RegistryObject<CountPotencyStatus> DEFENSE_LEVEL_DOWN = registerEffect("defense_down", new DefenseDownEffect());
    public static final RegistryObject<CountPotencyStatus> OFFENSE_LEVEL_UP = registerEffect("offense_up", new OffenseUpEffect());
    public static final RegistryObject<CountPotencyStatus> DEFENSE_LEVEL_UP = registerEffect("defense_up", new DefenseUpEffect());
    public static final RegistryObject<CountPotencyStatus> POWER_UP = registerEffect("power_up", new PowerUp());
    public static final RegistryObject<CountPotencyStatus> POWER_DOWN = registerEffect("power_down", new PowerDown());
    public static final RegistryObject<CountPotencyStatus> FUEL_IGNITION = registerEffect("fuel_ignition", new FuelIgnitionEffect());
    public static final RegistryObject<CountPotencyStatus> BRANDING_BLADE = registerEffect("branding_blade", new BrandingBladeEffect());
    public static final RegistryObject<CountPotencyStatus> SPEED_UP = registerEffect("speed_up", new SpeedUpEffect());
    public static final RegistryObject<CountPotencyStatus> SPEED_DOWN = registerEffect("speed_down", new SpeedDownEffect());
    public static final RegistryObject<CountPotencyStatus> STRIDER_MAO = registerEffect("strider_mao", new StriderMaoEffect());
    public static final RegistryObject<CountPotencyStatus> DEATHRITE_HASTE = registerEffect("deathrite_haste", new DeathriteHasteEffect());
    public static final RegistryObject<Effect> CLASH_STUN = registerEffect("clashed", new ClashStunEffect());



    public static int speedMult(LivingEntity entity) {
        return SPEED_UP.get().getPotency(entity) - SPEED_DOWN.get().getPotency(entity);
    }

    public static void extendEffect(LivingEntity target, Effect effect, int time) {
        if (target.hasEffect(effect)) {
            target.addEffect(new EffectInstance(effect, time, target.getEffect(effect).getAmplifier()));
        }
    }
}
