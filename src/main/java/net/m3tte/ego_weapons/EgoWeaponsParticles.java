package net.m3tte.ego_weapons;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.particle.HitParticleType;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EgoWeaponsParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,  EgoWeaponsMod.MODID);

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
    public static final RegistryObject<BasicParticleType> TAKE_AIM = PARTICLES.register("aim_taken", () -> new BasicParticleType(true));

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
    public static final RegistryObject<BasicParticleType> BURN_APPLY = PARTICLES.register("burn_apply", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DARK_BURN_APPLY = PARTICLES.register("dark_burn_apply", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SINKING_APPLY = PARTICLES.register("sinking_apply", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DURANDAL_SWIPE_DOWN = PARTICLES.register("durandal_swipe_down", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DURANDAL_SWIPE_UP = PARTICLES.register("durandal_swipe_up", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DURANDAL_SWIPE_HORIZONTAL = PARTICLES.register("durandal_swipe_horizontal", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> EFFECT_NUMBER = PARTICLES.register("stagger_number", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DAMAGE_NUMBER = PARTICLES.register("damage_number", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> CLASH_NUMBER = PARTICLES.register("clash_number", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> OUFI_AUTO_STRIKE = PARTICLES.register("oufi_auto_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> OUFI_AUTO_HIT = PARTICLES.register("oufi_auto_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> OUFI_DASH_STRIKE = PARTICLES.register("oufi_dash_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> OUFI_DASH_HIT = PARTICLES.register("oufi_dash_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> OUFI_SWIPE_DOWN = PARTICLES.register("oufi_swipe_down", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> OUFI_PIERCE = PARTICLES.register("oufi_pierce", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BASIC_BULLET_IMPACT = PARTICLES.register("basic_bullet_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BASIC_BULLET_FIRE = PARTICLES.register("basic_bullet_fire", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BASIC_BULLET_FIRE_SIDE = PARTICLES.register("basic_bullet_fire_side", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> INCENDIARY_BULLET_IMPACT = PARTICLES.register("incendiary_bullet_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> INCENDIARY_BULLET_FIRE = PARTICLES.register("incendiary_bullet_fire", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> INCENDIARY_BULLET_FIRE_SIDE = PARTICLES.register("incendiary_bullet_fire_side", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> MOONSTONE_BULLET_IMPACT = PARTICLES.register("moonstone_bullet_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MOONSTONE_BULLET_FIRE = PARTICLES.register("moonstone_bullet_fire", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MOONSTONE_BULLET_FIRE_SIDE = PARTICLES.register("moonstone_bullet_fire_side", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> RIFLE_BASIC_BULLET_FIRE_SIDE = PARTICLES.register("rifle_standard_bullet_fire_side", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> RIFLE_SHOCKWAVE = PARTICLES.register("rifle_fire_shockwave", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> RIFLE_ALHV_BULLET_FIRE_SIDE = PARTICLES.register("alhv_bullet_fire_side", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> RIFLE_ALHV_SHOCKWAVE = PARTICLES.register("rifle_fire_alhv_shockwave", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> FULLSTOP_MACHETE_STRIKE = PARTICLES.register("fullstop_machete_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> FULLSTOP_MACHETE_HIT = PARTICLES.register("fullstop_machete_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> CRIT = PARTICLES.register("crit_strike", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> TEXTURED_AFTER_IMAGE = PARTICLES.register("textured_after_image", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_FIRE = PARTICLES.register("magic_bullet_fire", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_FIRE_SIDE = PARTICLES.register("magic_bullet_fire_side", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_SHOCKWAVE = PARTICLES.register("magic_bullet_shockwave", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> LIU_S6_AUTO_STRIKE = PARTICLES.register("liu_s6_impact", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> LIU_S6_AUTO_HIT = PARTICLES.register("liu_s6_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> LIU_S6_AUTO_SIDE = PARTICLES.register("liu_s6_sidehit", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> LIU_PUNCH_SHOCKWAVE = PARTICLES.register("liu_punch_shockwave", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> FIREFIST_STRIKE = PARTICLES.register("firefist_impact", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> FIREFIST_HIT = PARTICLES.register("firefist_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> FIREFIST_FLAME_SPEW = PARTICLES.register("firefist_flame_spew", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> STIGMA_WORKSHOP_SWORD_STRIKE = PARTICLES.register("stigma_workshop_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> STIGMA_WORKSHOP_SWORD_HIT = PARTICLES.register("stigma_workshop_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> STIGMA_WORKSHOP_SWORD_DASH_STRIKE = PARTICLES.register("stigma_workshop_dash_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> STIGMA_WORKSHOP_SWORD_DASH_HIT = PARTICLES.register("stigma_workshop_dash_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> STIGMA_WORKSHOP_SWORD_IGNITE = PARTICLES.register("stigma_workshop_ignite", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> STIGMA_WORKSHOP_SWORD_IGNITE_SIDE = PARTICLES.register("stigma_workshop_ignite_side", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SIMPLE_EMBER = PARTICLES.register("simple_ember", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MAO_PARTICLE = PARTICLES.register("mao_particle", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> STIGMA_WORKSHOP_SLASH_UP = PARTICLES.register("stigma_workshop_slash_up", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> STIGMA_WORKSHOP_SLASH_DOWN = PARTICLES.register("stigma_workshop_slash_down", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> MAO_BRANCH_STRIKE = PARTICLES.register("mao_branch_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> MAO_BRANCH_HIT = PARTICLES.register("mao_branch_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<BasicParticleType> MAO_BRANCH_CIRCULAR_HIT = PARTICLES.register("mao_branch_circular_hit", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MAO_BRANCH_REUSE = PARTICLES.register("mao_branch_reuse", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> JUSTITIA_STRIKE = PARTICLES.register("justitia_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> JUSTITIA_HIT = PARTICLES.register("justitia_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> JUSTITIA_SCALE_STRIKE = PARTICLES.register("justitia_scale_strike", () -> new BasicParticleType(true));
    public static final RegistryObject<HitParticleType> JUSTITIA_SCALE_HIT = PARTICLES.register("justitia_scale_hit", () -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<BasicParticleType> JUSTITIA_SCALE = PARTICLES.register("justitia_scale", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> JUSTITIA_PARTICLE_SCALE = PARTICLES.register("justitia_particle_scale", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> JUSTITIA_PARTICLE = PARTICLES.register("justitia_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> JUSTITIA_PIECE = PARTICLES.register("justitia_piece", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> JUSTITIA_REUSE_STRIKE = PARTICLES.register("justitia_reuse_hit", () -> new BasicParticleType(true));

    public static final RegistryObject<BasicParticleType> HORIZONTAL_SHOCKWAVE = PARTICLES.register("horizontal_shockwave", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SLASH_SHOCKWAVE = PARTICLES.register("slash_shockwave", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SOLEMN_LAMENT_SHOCKWAVE = PARTICLES.register("solemn_lament_shockwave", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_CIRCLE_SHORT = PARTICLES.register("magic_bullet_circle_short", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> MAGIC_BULLET_CIRCLE_LONG = PARTICLES.register("magic_bullet_circle_long", () -> new BasicParticleType(true));

}
