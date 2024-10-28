package net.m3tte.tcorp;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.particle.HitParticleType;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCorpParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "tcorp");

    public static final RegistryObject<BasicParticleType> DURANDAL_STRIKE = PARTICLES.register("durandal_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> DURANDAL_SLASH = PARTICLES.register("durandal_slash", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> RANGA_STRIKE = PARTICLES.register("ranga_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> RANGA_FURIOSO_SLASH = PARTICLES.register("ranga_furioso_slash", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> ATELIER_PISTOL_IMPACT = PARTICLES.register("atelier_pistol_impact", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> ATELIER_PISTOL_HIT = PARTICLES.register("atelier_pistol_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> ATELIER_SHOTGUN_IMPACT = PARTICLES.register("atelier_shotgun_impact", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> ATELIER_SHOTGUN_HIT = PARTICLES.register("atelier_shotgun_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> ZELKOVA_STRIKE = PARTICLES.register("zelkova_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> ZELKOVA_HIT = PARTICLES.register("zelkova_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> CRYSTAL_ATELIER_STRIKE = PARTICLES.register("crystal_atelier_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> CRYSTAL_ATELIER_HIT = PARTICLES.register("crystal_atelier_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> BLEED = PARTICLES.register("bleed_fx", () -> new BasicParticleType(true));


    public static final RegistryObject<BasicParticleType> ALLAS_STRIKE = PARTICLES.register("allas_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> ALLAS_HIT = PARTICLES.register("allas_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> WHEELS_SMASH = PARTICLES.register("wheels_smash", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> WHEELS_IMPACT = PARTICLES.register("wheels_impact", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> MOOK_SLASH = PARTICLES.register("mook_slash", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MOOK_JUDGEMENT_CUT = PARTICLES.register("mook_judgement_cut", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> MIMICRY_STRIKE = PARTICLES.register("mimicry_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MIMICRY_HIT = PARTICLES.register("mimicry_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> MIMICRY_HORIZONTAL_STRIKE = PARTICLES.register("mimicry_horizontal_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MIMICRY_HORIZONTAL_HIT = PARTICLES.register("mimicry_horizontal_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> MIMICRY_DASH_STRIKE = PARTICLES.register("mimicry_dash_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MIMICRY_DASH_HIT = PARTICLES.register("mimicry_dash_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> MIMICRY_VERTICAL_STRIKE = PARTICLES.register("vertical_split_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MIMICRY_VERTICAL_HIT = PARTICLES.register("vertical_split_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_IMPACT = PARTICLES.register("magic_bullet_impact", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MAGIC_BULLET_IMPACT_HIT = PARTICLES.register("magic_bullet_impact_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_STRIKE = PARTICLES.register("magic_bullet_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MAGIC_BULLET_HIT = PARTICLES.register("magic_bullet_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> STAGGER = PARTICLES.register("stagger", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> SOLEMN_LAMENT_FIRE_DEPARTED = PARTICLES.register("solemn_lament_fire_departed", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SOLEMN_LAMENT_FIRE_LIVING = PARTICLES.register("solemn_lament_fire_living", () -> new BasicParticleType(true));

    public static final RegistryObject<HitParticleType> SOLEMN_LAMENT_LIVING_HIT = PARTICLES.register("solemn_lament_hit_living", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<HitParticleType> SOLEMN_LAMENT_DEPARTED_HIT = PARTICLES.register("solemn_lament_hit_departed", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<HitParticleType> SOLEMN_LAMENT_BURST_HIT = PARTICLES.register("solemn_lament_burst_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));


    public static final RegistryObject<BasicParticleType> SOLEMN_LAMENT_LIVING_BUTTERFLY = PARTICLES.register("solemn_lament_living_butterflies", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SOLEMN_LAMENT_DEPARTED_BUTTERFLY = PARTICLES.register("solemn_lament_departed_butterflies", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> DOUBT_GROUNDSLAM_STRIKE = PARTICLES.register("doubt_vertical_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> DOUBT_GROUNDSLAM_HIT = PARTICLES.register("doubt_vertical_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> DOUBT_EXPLOSION_EFFECT = PARTICLES.register("doubt_explosion", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> DOUBT_EXPLODE = PARTICLES.register("doubt_death_explosion", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<HitParticleType> MEAT_CHUNK_EXPLOSION = PARTICLES.register("meat_explosion", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> SUNSHOWER_AUTO1_STRIKE = PARTICLES.register("sunshower_auto1_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> SUNSHOWER_AUTO1_HIT = PARTICLES.register("sunshower_auto1_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> SUNSHOWER_OPEN = PARTICLES.register("sunshower_openumbrella", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> SUNSHOWER_AUTO3_STRIKE = PARTICLES.register("sunshower_auto3_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> SUNSHOWER_AUTO3_HIT = PARTICLES.register("sunshower_auto3_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> PUDDLE_STOMP_IMPACT = PARTICLES.register("puddle_stomp_impact", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> PUDDLE_STOMP_RIPPLE = PARTICLES.register("puddle_stomp_ripple", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SUNSHOWER_DRIFT = PARTICLES.register("sunshower_drift", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SUNSHOWER_DRIFT_B = PARTICLES.register("sunshower_drift_b", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> GREAT_SPLIT_HORIZONTAL = PARTICLES.register("great_split_horizontal", () -> new BasicParticleType(true));

}
