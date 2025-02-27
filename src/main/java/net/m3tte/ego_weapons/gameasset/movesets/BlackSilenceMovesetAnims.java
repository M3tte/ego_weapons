package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.entities.AtelierPistolsBullet;
import net.m3tte.ego_weapons.execFunctions.AtelierCooldownHandler;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.item.blackSilence.weapons.AtelierLogicRevolver;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.BlipTick;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.*;
import static net.m3tte.ego_weapons.item.blackSilence.weapons.DurandalItem.sheathableIdleReset;
import static net.m3tte.ego_weapons.item.blackSilence.weapons.DurandalItem.sheathableWeaponStateManager;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.pummelDownEntity;
import static yesman.epicfight.api.animation.property.AnimationProperty.*;

public class BlackSilenceMovesetAnims {

    public static StaticAnimation RANGA_IDLE;

    public static StaticAnimation RANGA_WALK;

    public static StaticAnimation RANGA_RUN;

    public static StaticAnimation RANGA_KNEEL;

    public static StaticAnimation RANGA_SNEAK;

    public static StaticAnimation RANGA_GUARD;

    public static StaticAnimation RANGA_GUARD_HIT;

    public static StaticAnimation RANGA_GUARD_STAGGER;

    public static StaticAnimation RANGA_ATTACK_1;
    public static StaticAnimation RANGA_ATTACK_2;
    public static StaticAnimation RANGA_ATTACK_3;

    // Identical to the above but faster, deadlier and they stun
    public static StaticAnimation RANGA_FURIOSO_1;
    public static StaticAnimation RANGA_FURIOSO_2;
    public static StaticAnimation RANGA_FURIOSO_3;

    public static StaticAnimation RANGA_ATTACK_3_DEBOUNCE;

    public static StaticAnimation RANGA_EVISCERATE_1;

    public static StaticAnimation RANGA_EVISCERATE_2;

    public static StaticAnimation ALLAS_ATTACK_1;
    public static StaticAnimation ALLAS_DASH;
    public static StaticAnimation ALLAS_DASH_DEB;

    public static StaticAnimation ALLAS_GUARD;
    public static StaticAnimation ALLAS_GUARD_HIT;

    public static StaticAnimation ALLAS_GUARD_COUNTER;

    public static StaticAnimation CRYSTAL_ATELIER_INSTANT_DASH;

    public static StaticAnimation CRYSTAL_ATELIER_FURIOSO;

    public static StaticAnimation CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE;

    public static StaticAnimation CRYSTAL_ATELIER_SKILL;

    public static StaticAnimation CRYSTAL_ATELIER_DODGE;

    public static StaticAnimation ZELKOVA_IDLE;
    public static StaticAnimation ZELKOVA_WALK;
    public static StaticAnimation ZELKOVA_RUN;
    public static StaticAnimation ZELKOVA_ATTACK_1;

    public static StaticAnimation ZELKOVA_ATTACK_2;
    public static StaticAnimation ZELKOVA_JUMP_ATTACK;
    public static StaticAnimation ZELKOVA_DASH_1;
    public static StaticAnimation ZELKOVA_DASH_2;

    public static StaticAnimation ZELKOVA_FURIOSO_1;

    public static StaticAnimation ZELKOVA_FURIOSO_2;

    public static StaticAnimation ZELKOVA_SPECIAL_1;
    public static StaticAnimation ZELKOVA_SPECIAL_2;

    public static StaticAnimation ATELIER_SHOTGUN_EQUIP;
    public static StaticAnimation ALLAS_SPEAR_EQUIP;
    public static StaticAnimation OLD_BOYS_EQUIP;
    public static StaticAnimation ORLANDO_TRIGGER;

    public static StaticAnimation MOOK_IDLE;
    public static StaticAnimation MOOK_WALK;
    public static StaticAnimation MOOK_RUN;
    public static StaticAnimation MOOK_SNEAK;
    public static StaticAnimation MOOK_KNEEL;
    public static StaticAnimation MOOK_AUTO_1;
    public static StaticAnimation MOOK_AUTO_2;
    public static StaticAnimation MOOK_AUTO_3;

    public static StaticAnimation MOOK_AUTO_4;
    public static StaticAnimation MOOK_SHEATH;
    public static StaticAnimation MOOK_AUTO_JUMP;
    public static StaticAnimation MOOK_JUMP_SHEATH;
    public static StaticAnimation MOOK_CUT;
    public static StaticAnimation MOOK_GUARD;

    public static StaticAnimation MOOK_GUARD_HIT;

    public static StaticAnimation MOOK_GUARD_HIT_PARRY;

    public static StaticAnimation OLD_BOYS_AUTO_1;
    public static StaticAnimation OLD_BOYS_AUTO_2;
    public static StaticAnimation OLD_BOYS_AUTO_3;
    public static StaticAnimation OLD_BOYS_FURIOSO;
    public static StaticAnimation OLD_BOYS_GUARD;
    public static StaticAnimation OLD_BOYS_PARRY;


    public static StaticAnimation WHEELS_GUARD;
    public static StaticAnimation WHEELS_GUARD_HIT;
    public static StaticAnimation WHEELS_GUARD_COUNTER;
    public static StaticAnimation WHEELS_AUTO_1;
    public static StaticAnimation WHEELS_AUTO_2;
    public static StaticAnimation WHEELS_AUTO_3;
    public static StaticAnimation WHEELS_CROUCH;
    public static StaticAnimation WHEELS_DASH;
    public static StaticAnimation WHEELS_IDLE;
    public static StaticAnimation WHEELS_RUN;
    public static StaticAnimation WHEELS_SNEAK;
    public static StaticAnimation WHEELS_WALK;
    public static StaticAnimation WHEELS_HEAVY;


    public static void build(Model biped) {
        RANGA_IDLE = new StaticAnimation(true, "biped/ranga/idle", biped);
        RANGA_WALK = new MovementAnimation(true, "biped/ranga/walk", biped);
        RANGA_RUN = new MovementAnimation(true, "biped/ranga/run", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.5f);
        RANGA_KNEEL = new StaticAnimation(true, "biped/ranga/kneel", biped);
        RANGA_SNEAK = new MovementAnimation(true, "biped/ranga/sneak", biped);

        RANGA_GUARD = new StaticAnimation( true, "biped/ranga/guard", biped);
        RANGA_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/ranga/guard_hit", biped);
        RANGA_GUARD_STAGGER = new LongHitAnimation(0.05f, "biped/ranga/guard_stagger", biped);

        RANGA_ATTACK_1 = (new BasicEgoAttackAnimation(0.1F, 0.05F, 0.25F, 0.6F, 0.7F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaEvents());

        RANGA_ATTACK_2 = (new BasicEgoAttackAnimation(0F, 0F, 0.3F, 0.65F, 0.9F, ColliderPreset.SWORD, "Tool_L", "biped/ranga/attack_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, ranga2Events());

        RANGA_ATTACK_3 = (new BasicEgoAttackAnimation(0F, 0F, 0.3F, 0.65F, 1F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaDaggerShort());

        RANGA_FURIOSO_1 = (new BasicEgoAttackAnimation(0.1F, 0.05F, 0.25F, 0.6F, 0.7F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.RANGA_FURIOSO_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(4f))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaEvents());

        RANGA_FURIOSO_2 = (new BasicEgoAttackAnimation(0F, 0F, 0.3F, 0.65F, 0.9F, ColliderPreset.SWORD, "Tool_L", "biped/ranga/attack_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.RANGA_FURIOSO_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(4f))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, ranga2Events());

        RANGA_FURIOSO_3 = (new EgoAttackAnimation(0F, 0F, 0.3F, 0.65F, 1F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(0.5f))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.8f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaDaggerShort());

        RANGA_ATTACK_3_DEBOUNCE = (new DashAttackAnimation(0F, 0F, 0.3F, 0.65F, 2.5F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_3_deb", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaDaggerEvents());

        RANGA_EVISCERATE_1 = new EgoAttackAnimation(0.08F, 0.05F, 0.34F, 0.6F, 0.8F, null, "Tool_R", "biped/ranga/eviscerate_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:sword_stab", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F);

        RANGA_EVISCERATE_2 = new EgoAttackAnimation(0.05F, 0.0F, 0.3F, 0.4F, 0.8F, null, "Tool_R", "biped/ranga/eviscerate_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        ALLAS_ATTACK_1 = (new BasicEgoAttackAnimation(0.16F, 0.05F, 0.28F, 0.55F, 0.7F, ColliderPreset.SPEAR, "Tool_R", "biped/allas/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:sword_stab", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        ALLAS_DASH_DEB = (new BasicEgoAttackAnimation(0.16F, 0.05F, 0.5F, 0.95F, 1.6F, ColliderPreset.SPEAR, "Tool_R", "biped/allas/dash_deb", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);

        ALLAS_DASH = (new EgoAttackAnimation(0.16F, 0.05F, 0.3F, 0.66F, 1F, ColliderPreset.SPEAR, "Tool_R", "biped/allas/dash_deb", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ALLAS_HIT)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(10));

        ALLAS_GUARD = new StaticAnimation( true, "biped/allas/guard", biped);
        ALLAS_GUARD_HIT = new GuardAnimation(0.05f, 0.4f,"biped/allas/guard_hit", biped);
        ALLAS_GUARD_COUNTER = new GuardAnimation(0.05f, "biped/allas/guard_counter", biped);

        CRYSTAL_ATELIER_INSTANT_DASH = (new BasicEgoAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 0.85F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.crystal_atelier", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35f)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_FURIOSO = (new EgoAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 0.85F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(8))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.crystal_atelier", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.CRYSTAL_ATELIER_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4f)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE = (new EgoAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 2F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant_debounce", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.crystal_atelier", ':')))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2f)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_SKILL = (new EgoAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 2F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant_debounce", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.crystal_atelier.strong", ':')))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.CRYSTAL_ATELIER_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.3f)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_DODGE = (new BasicEgoAttackAnimation(0.04F, 0.05F, 0.08F, 0.07F, 0.45F, ColliderPreset.FIST, "Root", "biped/crystal_atelier/dodge", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.setter(0.3f))
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0f))
                .addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.evade", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(StaticAnimationProperty.EVENTS, atelierDodgeEvent());


        ZELKOVA_IDLE = new StaticAnimation(0.1f, true, "biped/zelkova/idle", biped);
        ZELKOVA_WALK = new StaticAnimation(0.1f, true, "biped/zelkova/walk", biped);
        ZELKOVA_RUN = new StaticAnimation(0.1f, true, "biped/zelkova/run", biped);

        ZELKOVA_JUMP_ATTACK = (new BasicEgoAttackAnimation(0.05F, 0.2F, 0.5F, 0.65F, 1.1F, null, "Tool_R", "biped/zelkova/jump_attack", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(1))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_MACE)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true);

        ZELKOVA_DASH_1 = (new BasicEgoAttackAnimation(0.15F, 0.2F, 0.33F, 0.7F, 1.4F, null, "Tool_R", "biped/zelkova/dash_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(-2))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(4))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(StaticAnimationProperty.EVENTS, zelkovaDashEvents());

        ZELKOVA_DASH_2 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.45F, 0.6F, 1.1F, null, "Tool_R", "biped/zelkova/dash_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_AXE)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f);


        ZELKOVA_ATTACK_1 = (new BasicEgoAttackAnimation(0.16F, 0.1F, 0.66F, 0.83F, 1.1F, null, "Tool_R", "biped/zelkova/attack_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.zelkova.axe", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f);

        ZELKOVA_ATTACK_2 = (new BasicEgoAttackAnimation(0.2F, 0.2F, 0.5F, 0.7F, 1.5F, null, "Tool_L", "biped/zelkova/attack_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.zelkova.mace", ':')))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);


        ZELKOVA_FURIOSO_1 = (new BasicEgoAttackAnimation(0.16F, 0.1F, 0.66F, 0.83F, 1.1F, null, "Tool_R", "biped/zelkova/attack_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ZELKOVA_HIT)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.zelkova.axe", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(5));

        ZELKOVA_FURIOSO_2 = (new EgoAttackAnimation(0.2F, 0.2F, 0.5F, 0.7F, 1.5F, null, "Tool_L", "biped/zelkova/attack_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.zelkova.mace", ':')))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ZELKOVA_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.55f)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(5));

        ZELKOVA_SPECIAL_1 =
                (new EgoAttackAnimation(0.1F, "biped/zelkova/special_1", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.7F, 1F, 1.4F, 1.4F, "Tool_R", EgoWeaponsCapabilityPresets.LONGER_BLADE)
                        .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ZELKOVA_HIT)
                        .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(4f))
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_AXE),
                new AttackAnimation.Phase(0.63F, 0.2F, 0.8F, 1F, 1.4F, 1.4F, "Tool_L", EgoWeaponsCapabilityPresets.LONGER_BLADE)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(4f))
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_AXE))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85f)
                .addProperty(StaticAnimationProperty.EVENTS, zelkovaSpecialEvents()));

        ZELKOVA_SPECIAL_2 = (new EgoAttackAnimation(0.05F, 0.2F, 0.25F, 0.5F, 1F, null, "Tool_R", "biped/zelkova/special_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(2))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_MACE)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true);

        DURANDAL_EQUIP = new ActionAnimation(0.1f, "biped/durandal/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        DUAL_EQUIP = new ActionAnimation(0.1f, "biped/generic/dual_equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        ATELIER_SHOTGUN_EQUIP = new ActionAnimation(0.1f, "biped/atelier_shotgun/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        ALLAS_SPEAR_EQUIP = new ActionAnimation(0.1f, "biped/allas/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        OLD_BOYS_EQUIP = new ActionAnimation(0.1f, 0.4f, "biped/old_boys/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        ORLANDO_TRIGGER = new ActionAnimation(0.1f, "biped/generic/orlando_trigger", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        MOOK_IDLE = new StaticAnimation(true, "biped/mook/idle", biped)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableIdleReset());
        MOOK_WALK = new MovementAnimation(true, "biped/mook/walk", biped)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableIdleReset());
        MOOK_KNEEL = new StaticAnimation(true, "biped/mook/kneel", biped)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableIdleReset());
        MOOK_SNEAK = new MovementAnimation(true, "biped/mook/sneak", biped)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableIdleReset());
        MOOK_RUN = new MovementAnimation(true, "biped/mook/run", biped)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableIdleReset());
        MOOK_AUTO_1 = (new BasicEgoAttackAnimation(0.1F, 0.3F, 0.5F, 0.8F, 1.1F, null, "Tool_R", "biped/mook/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(StaticAnimationProperty.EVENTS, mookFirstEvent());

        MOOK_AUTO_2 = (new BasicEgoAttackAnimation(0.35F, 0.07F, 0.08F, 0.3F, 0.5F, null, "Tool_R", "biped/mook/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableWeaponStateManager(0.1f, 2));

        MOOK_AUTO_3 = (new BasicEgoAttackAnimation(0.5F, 0.2F, 0.2F, 0.5F, 0.55F, null, "Tool_R", "biped/mook/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35f)
                .addProperty(StaticAnimationProperty.EVENTS, sheathableWeaponStateManager(0.1f, 2));

        MOOK_AUTO_4 = (new BasicEgoAttackAnimation(0.1F, 0.5F, 0.5F, 0.8F, 1F, null, "Tool_R", "biped/mook/auto_4", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(4))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(8))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEngage(0.8f, false));

        MOOK_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/mook/auto_sheathe", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEvents(0.4f, 4))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        MOOK_AUTO_JUMP = (new BasicEgoAttackAnimation(0.1F, 0.5F, 0.5F, 0.8F, 1F, null, "Tool_R", "biped/mook/mook_jump", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEngage(0.79f, true));

        MOOK_JUMP_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/mook/mook_jump_sheath", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEvents(0.5f, 2))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);


        MOOK_CUT = (new ActionAnimation(0.1f, 1f,   "biped/mook/cut", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, mookCutEvent())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        MOOK_GUARD = new StaticAnimation( true, "biped/mook/guard", biped);


        MOOK_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/mook/guard_hit", biped);
        MOOK_GUARD_HIT_PARRY = new GuardAnimation(0.05f,0.1f, "biped/mook/guard_parry", biped);

        OLD_BOYS_AUTO_1 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.1F, 0.3F, 0.5F, null, "Tool_R", "biped/old_boys/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        OLD_BOYS_FURIOSO = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.1F, 0.3F, 0.9F, null, "Tool_R", "biped/old_boys/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "OB_FURIOSO")
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(1.3f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7f))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        OLD_BOYS_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.1F, 0.3F, 0.47F, null, "Tool_R", "biped/old_boys/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(1.3f))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        OLD_BOYS_AUTO_3 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.34F, 0.6F, 1.5F, null, "Tool_R", "biped/old_boys/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(1.3f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5f))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(StaticAnimationProperty.EVENTS, oldBoysThreeEvents());

        OLD_BOYS_GUARD = new StaticAnimation( true, "biped/old_boys/guard", biped);

        OLD_BOYS_PARRY = new GuardAnimation(0.05f,0.6f, "biped/old_boys/parry", biped);


        WHEELS_IDLE = new StaticAnimation(true, "biped/wheels/idle", biped);
        WHEELS_CROUCH = new StaticAnimation(true, "biped/wheels/crouch", biped);
        WHEELS_WALK = new MovementAnimation(true, "biped/wheels/walk", biped);
        WHEELS_RUN = new MovementAnimation(true, "biped/wheels/run", biped);
        WHEELS_SNEAK = new MovementAnimation(true, "biped/wheels/sneak", biped);
        WHEELS_GUARD = new StaticAnimation( true, "biped/wheels/guard", biped);
        WHEELS_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/wheels/guard_hit", biped);
        WHEELS_GUARD_COUNTER = new GuardAnimation(0.05f,0.1f, "biped/wheels/guard_counter", biped);

        WHEELS_AUTO_1 = (new BasicEgoAttackAnimation(0.02F, 0.25F, 0.4F, 0.9F, 1.2F, null, "Tool_R", "biped/wheels/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        WHEELS_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.25F, 0.4F, 0.7F, 1.4F, null, "Tool_R", "biped/wheels/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        WHEELS_AUTO_3 = (new BasicEgoAttackAnimation(0.1F, 0.3F, 0.6F, 1F, 1.4F, null, "Tool_R", "biped/wheels/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(0))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(1))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        WHEELS_DASH = (new EgoAttackAnimation(0.1F, 0.4F, 0.5F, 0.9F, 2F, null, "Tool_R", "biped/wheels/dash_attack", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);


        WHEELS_HEAVY = (new EgoAttackAnimation(0.1F, 0.1F, 0.4F, 0.65F, 0.85F, null, "Tool_R", "biped/wheels/heavy", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(9))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 2)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(StaticAnimationProperty.EVENTS, wheelsSmashEvent());

    }

    private static StaticAnimation.Event[] crystalAtelierEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.05F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 10);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    // Adds furioso attack stacks in a generic manner.
    private static StaticAnimation.Event[] genericFuriosoEvent(int amount) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.00F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, amount);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] mookSheatheEvents(float time, int max) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }
            entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.iFrames = 15;
                capability.syncPlayerVariables(entity);
            });

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entity instanceof PlayerEntity) {
                PlayerPatch<?> playerPatch = (PlayerPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                int mookStacks = (int)entity.getPersistentData().getDouble("mookStacks");
                entity.getPersistentData().putDouble("mookStacks", 0);
                entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);
                if (mookStacks > max)
                    mookStacks = max;

                World world = entity.level;
                if (mookStacks > 0) {
                    playerPatch.setStamina(playerPatch.getStamina() + playerPatch.getMaxStamina() * 0.08f * mookStacks);

                    if (mookStacks > 1 && entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new EgoWeaponsModVars.PlayerVariables()).blips < 10) {
                        double _setval = Math.min(entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new EgoWeaponsModVars.PlayerVariables()).blips + (double) (mookStacks - mookStacks % 2) / 2, 10.0);

                        //if (playerPatch.getOriginal().getCooldowns().isOnCooldown(SuitItem.helmet.getItem()))
                        //    playerPatch.getOriginal().getCooldowns().removeCooldown(SuitItem.helmet.getItem());

                        BlipTick.chargeBlips((PlayerEntity) entity, (double) (mookStacks - mookStacks % 2) / 2, true);
                        spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-0.3), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", true);

                    }
                }

            }


            entity.playSound(EpicFightSounds.SWORD_IN, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
    private static StaticAnimation.Event[] oldBoysThreeEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.65F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();

            spawnBlockImpactParticle(entitypatch, 0, new Vector3d(0, -0.4, -0.5), 12, null, 0.1f);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] wheelsSmashEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 10);
            entity.playSound(EgoWeaponsSounds.WHEELS_IMPACT, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            Random r = new Random();
            LivingEntity entity = entitypatch.getOriginal();
            World l = entity.level;
            Vector3d pos = getArmaturePosition(entitypatch, 0, new Vector3d(0, -0.9f, -1), 20, "Tool_R");
            BlockPos pos1 = new BlockPos(pos.x, pos.y, pos.z);
            wheelsSmashEffect(entity.level, pos1, entity);
            if (!l.isEmptyBlock(pos1)) {

                l.addParticle(EgoWeaponsParticles.WHEELS_IMPACT.get(), pos.x(), ((int)pos.y())+0.7, pos.z, 0,0,0);

                for (int x = 0; x < 20; x++) {
                    l.addParticle(new BlockParticleData(ParticleTypes.BLOCK, l.getBlockState(pos1)), pos.x() + ranBetween(-1, 1), ((int)pos.y())+ ranBetween(1, 2), pos.z() + ranBetween(-1, 1), ranBetween(-1, 1),ranBetween(-1, 1),ranBetween(-1, 1));
                    if (x % 3 == 0) {
                        l.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x() + ranBetween(-1, 1), ((int)pos.y()) + ranBetween(1, 2), pos.z() + ranBetween(-1, 1), ranBetween(-0.35f, 0.35f),ranBetween(-0.35f, 0.35f),ranBetween(-0.35f, 0.35f));
                    }
                }
            }
            //spawnBlockImpactParticle(entitypatch, 0, new Vector3d(0, -1, -1.8), 20, TCorpParticleRegistry.WHEELS_IMPACT.get(), 0.5f, new Vector3i(0,2.3f,0));
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static void wheelsSmashEffect(World world, BlockPos pos, LivingEntity entity) {
        if (world instanceof ServerWorld) {
            List<Entity> _entfound = world
                    .getNearbyEntities(LivingEntity.class,
                            EntityPredicate.DEFAULT, null, new AxisAlignedBB(pos.getX() - (4f), pos.getY() - (4f), pos.getZ() - (4f), pos.getX() + (4f), pos.getY() + (4f), pos.getZ() + (4f)))
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                        }
                    }.compareDistOf(pos.getX(), pos.getY(), pos.getZ())).collect(Collectors.toList());


            if (_entfound.size() > 0) {

                for (Entity e : _entfound) {
                    if (e instanceof LivingEntity) {

                        if (!(e.equals(entity))) {
                            LivingEntity living = (LivingEntity) e;

                            ((ServerWorld) world).addParticle(EpicFightParticles.BLOOD.get(), living.getX(), (living.getY() + living.getBbHeight() / 2), (living.getZ()),
                                    living.getBbWidth() / 2, living.getBbHeight() / 2, living.getBbWidth() / 2);


                            LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                            if (targetPatch != null) {

                                TremorEffect tremorType = TremorEffect.incrementTremor(living, 0, 3);
                                tremorType.burst(living);
                                tremorType.decrement(living, 1, 0);
                                if (targetPatch.getCurrentLivingMotion().equals(LivingMotions.BLOCK)) {
                                    targetPatch.playSound(EgoWeaponsSounds.STAGGER, 1, -0.05F, 0.1F);

                                    if (entity instanceof PlayerEntity)
                                        living.hurt(DamageSource.playerAttack((PlayerEntity) entity), (float) 6);
                                    else
                                        living.hurt(DamageSource.mobAttack(entity), (float) 6);

                                    if (targetPatch instanceof PlayerPatch<?>) {
                                        PlayerPatch<?> playerPatch = (PlayerPatch<?>) targetPatch;
                                        playerPatch.setStamina(0);
                                    }

                                    if (targetPatch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
                                        targetPatch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER, 0.0F);
                                    }
                                } else {
                                    if (entity instanceof PlayerEntity)
                                        living.hurt(DamageSource.playerAttack((PlayerEntity) entity), (float) 12);
                                    else
                                        living.hurt(DamageSource.mobAttack(entity), (float) 12);
                                    if (entity.hasEffect(FuriosoPotionEffect.potion.getEffect())) {
                                        System.out.println("Furioso active, doing minor attack.");
                                        pummelDownEntity(targetPatch, 2);
                                    } else {
                                        if (targetPatch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
                                            targetPatch.playAnimationSynchronized(targetPatch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN), 0f);
                                            targetPatch.knockBackEntity(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), 0f);
                                        }
                                    }

                                }


                            }
                            BlackSilenceEvaluator.addToStatistic(entity, 11, "furiosohits");
                        }


                    }
                }
            }
        }
    }
    private static StaticAnimation.Event[] atelierDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }

            entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.iFrames = 15;
                capability.syncPlayerVariables(entity);
            });

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        15, 0.2, 0.5, 0.2, 0);
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(0.8F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        10, 0.2, 0.5, 0.2, 0);
            }
        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] ranga2Events() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.23F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);
        }, StaticAnimation.Event.Side.CLIENT);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("dagger", true);
        }, StaticAnimation.Event.Side.SERVER);

        return events;
    }

    private static StaticAnimation.Event[] rangaDaggerEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.3F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);

            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldowns().addCooldown(EgoWeaponsItems.RANGA_CLAW_L.get(), (int) 80);
                ((PlayerEntity) entity).getCooldowns().addCooldown(EgoWeaponsItems.RANGA_CLAW.get(), (int) 80);
            }

        }, StaticAnimation.Event.Side.CLIENT);

        events[1] = StaticAnimation.Event.create(1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("dagger", false);
        }, StaticAnimation.Event.Side.BOTH);





        return events;
    }

    private static StaticAnimation.Event[] rangaDaggerShort() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.3F, (entitypatch) -> {

            System.out.println("SWAPPING1...");

            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldowns().addCooldown(EgoWeaponsItems.RANGA_CLAW.get(), (int) 80);
                ((PlayerEntity) entity).getCooldowns().addCooldown(EgoWeaponsItems.RANGA_CLAW_L.get(), (int) 80);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("dagger", false);

        }, StaticAnimation.Event.Side.BOTH);





        return events;
    }

    private static StaticAnimation.Event[] rangaEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.23F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] zelkovaDashEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putBoolean("dashHit", false);
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.75F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getBoolean("dashHit")) {
                entitypatch.playAnimationSynchronized(ZELKOVA_DASH_2, 0);
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] zelkovaSpecialEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(1.35F, (entitypatch) -> {
            entitypatch.playAnimationSynchronized(ZELKOVA_SPECIAL_2 , 0);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] mookFirstEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
            entity.getPersistentData().putDouble("mookStacks", 0);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] mookCutEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 11);
            entity.playSound(EgoWeaponsSounds.BLACK_SILENCE_UNSHEATH, 1, 1f);

        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 1);
            entity.playSound(EgoWeaponsSounds.BLACK_SILENCE_MOOK_CUT, 1, 1f);
            entity.playSound(EpicFightSounds.WHOOSH, 1, 1.4f);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-0.3), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", false);
            Vector3d pos = entity.position().add(entity.getLookAngle().multiply(3, 3, 3));

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (pos.x), (pos.y + 1), (pos.z),
                        30, 0.1, 0.1, 0.1, 0);
                Random r = new Random();
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MOOK_JUDGEMENT_CUT.get(), (pos.x), (pos.y + 1), (pos.z),
                        1, 0, 0, 0, 0);


                List<Entity> _entfound = world
                        .getNearbyEntities(LivingEntity.class,
                                EntityPredicate.DEFAULT, null, new AxisAlignedBB(pos.x - (1.5f), pos.y - (1.5f), pos.z - (1.5f), pos.x + (1.5f), pos.y + (1.5f), pos.z + (1.5f)))
                        .stream().sorted(new Object() {
                            Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                            }
                        }.compareDistOf(pos.x, pos.y, pos.z)).collect(Collectors.toList());


                if (_entfound.size() > 0) {
                    Entity chosenEntity = _entfound.get(0);

                    if (chosenEntity instanceof LivingEntity && !(chosenEntity.equals(entity))) {
                        BlackSilenceEvaluator.addToStatistic(entity, 11, "furiosohits");

                        ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (chosenEntity.getX()), (chosenEntity.getY() + chosenEntity.getBbHeight()/2), (chosenEntity.getZ()),
                                15, chosenEntity.getBbWidth()/2, chosenEntity.getBbHeight()/2, chosenEntity.getBbWidth()/2, 0.3);
                        EgoWeaponsEffects.BLEED.get().increment((LivingEntity) chosenEntity, 2, 2);
                        if (entity instanceof PlayerEntity)
                            chosenEntity.hurt(new SimpleEgoDamageSource((EntityDamageSource) DamageSource.playerAttack((PlayerEntity) entity), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.BLACK, true), (float) 20);
                        else
                            chosenEntity.hurt(new SimpleEgoDamageSource((EntityDamageSource) DamageSource.mobAttack(entity), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.BLACK, true), (float) 20);
                        chosenEntity.playSound(EgoWeaponsSounds.BLACK_SILENCE_MOOK_HIT, 1, 1f);
                        LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) chosenEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                        if (targetPatch != null) {
                            if (targetPatch.getHitAnimation(ExtendedDamageSource.StunType.SHORT) != null) {
                                targetPatch.playAnimationSynchronized(targetPatch.getHitAnimation(ExtendedDamageSource.StunType.SHORT), 1f);
                                targetPatch.knockBackEntity(pos, 1);
                            }
                        }

                    }
                }
            }







        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }



    private static StaticAnimation.Event[] mookSheatheEngage(float delay, boolean jumping) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(delay, (entitypatch) -> {
            if (jumping) {
                entitypatch.reserveAnimation(BlackSilenceMovesetAnims.MOOK_JUMP_SHEATH);
            } else {
                entitypatch.reserveAnimation(BlackSilenceMovesetAnims.MOOK_SHEATH);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }






    private static StaticAnimation.Event[] atelierRevolver1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            ServerWorld serverWorld = null;
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 5);
            if (!world.isClientSide())
                serverWorld = (ServerWorld) world;


            Entity _shootFrom = entity;
            World projectileLevel = _shootFrom.level;
            if (!projectileLevel.isClientSide()) {
                ProjectileEntity _entityToSpawn = new Object() {
                    public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
                        AbstractArrowEntity entityToSpawn = new AtelierPistolsBullet.AtelierPistolBulletProj(EgoWeaponsEntities.ATELIER_PISTOL_BULLET.get(), world);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);

                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, entity, 1, 1);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("ego_weapons:blacksilence.atelier.revolver")),
                        SoundCategory.NEUTRAL, (float) 0.3, (float) 1);
            }
            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);
            AtelierCooldownHandler.executePistol(world, entity, entity.getX(), entity.getY(), entity.getZ());

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] atelierRevolver2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            ServerWorld serverWorld = null;
            if (!world.isClientSide())
                serverWorld = (ServerWorld) world;
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 5);
            Entity _shootFrom = entity;
            World projectileLevel = _shootFrom.level;
            if (!projectileLevel.isClientSide()) {
                ProjectileEntity _entityToSpawn = new Object() {
                    public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
                        AbstractArrowEntity entityToSpawn = new AtelierPistolsBullet.AtelierPistolBulletProj(EgoWeaponsEntities.ATELIER_PISTOL_BULLET.get(), world);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);

                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, entity, 1, 1);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER,
                        SoundCategory.NEUTRAL, (float) 0.3, (float) 1);
            }

            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", false);
            AtelierCooldownHandler.executePistol(world, entity, entity.getX(), entity.getY(), entity.getZ());

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
}
