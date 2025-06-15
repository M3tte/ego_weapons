package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.item.blackSilence.weapons.AtelierLogicRevolver;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.atelierShotgunEvent;

public class AtelierLogicMovesetAnims {


    public static StaticAnimation ATELIER_REVOLVER_AUTO_1a;
    public static StaticAnimation ATELIER_REVOLVER_AUTO_2a;
    public static StaticAnimation ATELIER_REVOLVER_SPECIAL_EMPTY_1;
    public static StaticAnimation ATELIER_REVOLVER_SPECIAL_EMPTY_2;
    public static StaticAnimation ATELIER_REVOLVER_SPECIAL;
    public static StaticAnimation ATELIER_REVOLVER_AUTO_1;
    public static StaticAnimation ATELIER_REVOLVER_AUTO_2;
    public static StaticAnimation ATELIER_REVOLVER_AUTO_3;
    public static StaticAnimation ATELIER_REVOLVER_JUMP_ATTACK;
    public static StaticAnimation ATELIER_REVOLVER_IDLE;
    public static StaticAnimation ATELIER_REVOLVER_WALK;
    public static StaticAnimation ATELIER_REVOLVER_RUN;
    public static StaticAnimation ATELIER_REVOLVER_RELOAD;
    public static StaticAnimation ATELIER_REVOLVER_COUNTER_EMPTY;
    public static StaticAnimation ATELIER_REVOLVER_COUNTER_FULL;

    public static StaticAnimation ATELIER_REVOLVER_JUMP_ATTACK_A;
    public static StaticAnimation ATELIER_REVOLVER_JUMP_ATTACK_B;
    public static StaticAnimation ATELIER_REVOLVER_GUARD;
    public static StaticAnimation ATELIER_REVOLVER_GUARD_HIT;

    public static StaticAnimation ATELIER_SHOTGUN_IDLE;

    public static StaticAnimation ATELIER_SHOTGUN_WALK;
    public static StaticAnimation ATELIER_SHOTGUN_RUN;
    public static StaticAnimation ATELIER_SHOTGUN_FIRE;

    public static StaticAnimation ATELIER_SHOTGUN_GUARD;

    public static StaticAnimation ATELIER_SHOTGUN_GUARD_HIT;
    public static StaticAnimation ATELIER_SHOTGUN_AUTO_1;
    public static StaticAnimation ATELIER_SHOTGUN_AUTO_2;

    public static StaticAnimation ATELIER_SHOTGUN_AUTO_3;
    public static void build(Model biped) {

        ATELIER_REVOLVER_IDLE = new StaticAnimation( 0.2f, true, "biped/atelier_revolver/idle", biped);
        ATELIER_REVOLVER_WALK = new MovementAnimation(0.2f, true, "biped/atelier_revolver/walk", biped);
        ATELIER_REVOLVER_RUN = new MovementAnimation(true, "biped/atelier_revolver/run", biped);
        ATELIER_REVOLVER_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/atelier_revolver/reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverStandardReload())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        ATELIER_REVOLVER_COUNTER_EMPTY = (new DodgeAnimation(0.1f, "biped/atelier_revolver/counter_empty", 0.5f, 2f , biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverParryReload())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        ATELIER_REVOLVER_COUNTER_FULL = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.75F, 0.8F, 1.4F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_R", "biped/atelier_revolver/counter_full", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.REFENSIVE_RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ATELIER_PISTOL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverFire(0.75f, "Tool_R"));

        ATELIER_REVOLVER_JUMP_ATTACK_A = (new EgoAttackAnimation(0.16F, 0.2F, 0.25F, 0.3F, 1.1F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_L", "biped/atelier_revolver/jump_attack_1a", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ATELIER_PISTOL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverFire(0.25f, "Tool_L"));

        ATELIER_REVOLVER_JUMP_ATTACK_B = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.25F, 0.3F, 1.1F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_R", "biped/atelier_revolver/jump_attack_1b", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ATELIER_PISTOL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverFire(0.25f, "Tool_R"));


        ATELIER_REVOLVER_AUTO_1a = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.55F, 0.6F, 1F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_R", "biped/atelier_revolver/auto_1a", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ATELIER_PISTOL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.firstRevolverFire(0.5f, "Tool_R"));

        ATELIER_REVOLVER_AUTO_2a = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.5F, 0.55F, 1F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_L", "biped/atelier_revolver/auto_2a", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ATELIER_PISTOL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverFire(0.5f, "Tool_L"));

        ATELIER_REVOLVER_SPECIAL_EMPTY_1 = (new EgoAttackAnimation(0.1F, 0.05F, 0.4F, 0.66F, 1.1F, ColliderPreset.FIST, "Tool_L", "biped/atelier_revolver/special_empty_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        ATELIER_REVOLVER_SPECIAL_EMPTY_2 = (new EgoAttackAnimation(0.1F, 0.05F, 0.35F, 0.5F, 1F, ColliderPreset.FIST, "Tool_L", "biped/atelier_revolver/special_empty_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.setter(4))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverAutoReload());

        ATELIER_REVOLVER_SPECIAL = (new EgoAttackAnimation(0.1F, "biped/atelier_revolver/special", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.5F, 0.55F, 2F, 2F, "Tool_L", EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT),
                new EgoAttackAnimation.EgoAttackPhase(0.63F, 0.65F, 0.71F, 0.75F, 2F, 2F, "Tool_R", EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT).addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.LAST_OF_COMBO, true))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ATELIER_REVOLVER)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ATELIER_PISTOL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.dualRevolverFire(0.5f, "Tool_L", 0.72f, "Tool_R", 1.5f)));

        ATELIER_REVOLVER_AUTO_1 = (new BasicEgoAttackAnimation(0.1F, 0.05F, 0.4F, 0.75F, 1F, ColliderPreset.FIST, "Tool_L", "biped/atelier_revolver/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        ATELIER_REVOLVER_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.05F, 0.3F, 0.66F, 0.9F, ColliderPreset.FIST, "Tool_L", "biped/atelier_revolver/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        ATELIER_REVOLVER_AUTO_3 = (new BasicEgoAttackAnimation(0.1F, 0.05F, 0.45F, 0.8F, 1.5F, ColliderPreset.FIST, "Tool_R", "biped/atelier_revolver/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        ATELIER_REVOLVER_JUMP_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.25F, 0.55F, 0.9F, ColliderPreset.FIST, "Leg_L", "biped/atelier_revolver/jump_attack", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, AtelierLogicRevolver.revolverJumpEval());

        ATELIER_REVOLVER_GUARD = new StaticAnimation( true, "biped/atelier_revolver/guard", biped);
        ATELIER_REVOLVER_GUARD_HIT = new GuardAnimation(0.05f, "biped/atelier_revolver/guard_hit", biped);



        ATELIER_SHOTGUN_IDLE = new StaticAnimation(true, "biped/atelier_shotgun/idle", biped);

        ATELIER_SHOTGUN_WALK = new MovementAnimation(true, "biped/atelier_shotgun/walk", biped);

        ATELIER_SHOTGUN_RUN = new StaticAnimation(true, "biped/atelier_shotgun/run", biped);

        ATELIER_SHOTGUN_FIRE = new ActionAnimation(0.35f, "biped/atelier_shotgun/fire", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, atelierShotgunEvent())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.6f);

        ATELIER_SHOTGUN_GUARD = new StaticAnimation( true, "biped/atelier_shotgun/guard", biped);

        ATELIER_SHOTGUN_GUARD_HIT = new GuardAnimation(0.02f, "biped/atelier_shotgun/guard_hit", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f);

        ATELIER_SHOTGUN_AUTO_1 = new BasicAttackAnimation(0.08F, 0.05F, 0.28F, 0.5F, 0.7F, null, "Tool_R", "biped/atelier_shotgun/auto_1", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F);

        ATELIER_SHOTGUN_AUTO_2 = new BasicAttackAnimation(0.08F, 0.05F, 0.33F, 0.4F, 0.9F, null, "Tool_R", "biped/atelier_shotgun/auto_2", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F);

        ATELIER_SHOTGUN_AUTO_3 = new BasicAttackAnimation(0.01F, 0.05F, 0.53F, 0.68F, 1F, null, "Tool_R", "biped/atelier_shotgun/auto_3", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-4.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F);
    }
}
