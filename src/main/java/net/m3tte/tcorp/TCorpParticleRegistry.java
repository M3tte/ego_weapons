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
    public static final RegistryObject<HitParticleType> MIMICRY_VERTICAL_HIT = PARTICLES.register("vertical_split_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ATTACKER_XY_ROTATION));

}
