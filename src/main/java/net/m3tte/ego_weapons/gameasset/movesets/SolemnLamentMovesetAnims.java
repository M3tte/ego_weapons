package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;

public class SolemnLamentMovesetAnims {

    public static StaticAnimation SOLEMN_LAMENT_IDLE;
    public static StaticAnimation SOLEMN_LAMENT_WALK;
    public static StaticAnimation SOLEMN_LAMENT_RUN;
    public static StaticAnimation SOLEMN_LAMENT_KNEEL;
    public static StaticAnimation SOLEMN_LAMENT_SNEAK;
    public static StaticAnimation SOLEMN_LAMENT_JUMP;
    public static StaticAnimation SOLEMN_LAMENT_JUMP_ATTACK;
    public static StaticAnimation SOLEMN_LAMENT_MELEE_ATTACK;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_D0;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_D1;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_D2;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_D3;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_D4;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_D5;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_R0;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_R1;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_R2;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_L0;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_L1;
    public static StaticAnimation SOLEMN_LAMENT_AUTO_L2;
    public static StaticAnimation SOLEMN_LAMENT_RELOAD;
    public static StaticAnimation SOLEMN_LAMENT_DASH_R;
    public static StaticAnimation SOLEMN_LAMENT_DASH_L;
    public static StaticAnimation SOLEMN_LAMENT_GUARD;
    public static StaticAnimation SOLEMN_LAMENT_GUARD_HIT;
    public static StaticAnimation SOLEMN_LAMENT_GUARD_PARRY1;
    public static StaticAnimation SOLEMN_LAMENT_GUARD_PARRY2;
    public static StaticAnimation SOLEMN_LAMENT_GUARD_COUNTERATTACK;
    public static StaticAnimation SOLEMN_LAMENT_GUARD_RELOAD;
    public static StaticAnimation SOLEMN_LAMENT_SPECIAL_ATTACK;
    public static StaticAnimation SOLEMN_LAMENT_SPECIAL_ATTACK_RELOAD;


    public static void build(Model biped) {
        SOLEMN_LAMENT_IDLE = new StaticAnimation(0.3f,true, "biped/solemn_lament/idle", biped);
        SOLEMN_LAMENT_WALK = new MovementAnimation(true, "biped/solemn_lament/walk", biped);
        SOLEMN_LAMENT_RUN = new MovementAnimation(true, "biped/solemn_lament/run", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        SOLEMN_LAMENT_KNEEL = new StaticAnimation(true, "biped/solemn_lament/kneel", biped);
        SOLEMN_LAMENT_SNEAK = new MovementAnimation(true, "biped/solemn_lament/sneak", biped);
        SOLEMN_LAMENT_JUMP = new MovementAnimation(0.3f,false, "biped/solemn_lament/jump", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.0f);

        SOLEMN_LAMENT_JUMP_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.45F, 0.6F, 1F, ColliderPreset.FIST, "Leg_L", "biped/solemn_lament/auto_jump", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65F);

        SOLEMN_LAMENT_MELEE_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.45F, 0.6F, 1.6F, ColliderPreset.FIST, "Leg_L", "biped/solemn_lament/auto_jump", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);

        SOLEMN_LAMENT_AUTO_D0 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.45F, 0.47F, 0.66F,null, "Tool_R", "biped/solemn_lament/auto_d0", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_READY)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85f)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.45f, false, 0.65f));

        SOLEMN_LAMENT_AUTO_D1 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.45F, 0.47F, 0.66F,null, "Tool_L", "biped/solemn_lament/auto_d1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_departed")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.PAPER_FLIP)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85f)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.45f, true, 0.46f));

        SOLEMN_LAMENT_AUTO_D2 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.06F, 0.07F, 0.7F,ColliderPreset.FIST, "Tool_R", "biped/solemn_lament/auto_d2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_SPIN)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);

        SOLEMN_LAMENT_AUTO_D3 = new BasicEgoAttackAnimation(0.02F, 0.02F, 0.02F, 0.04F, 0.3F,null, "Tool_R", "biped/solemn_lament/auto_d3", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                //.addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.MAGIC_BULLET_SPIN_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.02f, false, 0.29f));

        SOLEMN_LAMENT_AUTO_D4 = new BasicEgoAttackAnimation(0.02F, 0.02F, 0.02F, 0.04F, 0.45F,null, "Tool_L", "biped/solemn_lament/auto_d4", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_departed")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                //.addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.MAGIC_BULLET_SPIN_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.02f, true, 0.44f));

        SOLEMN_LAMENT_AUTO_D5 = new BasicEgoAttackAnimation(0.02F, 0.02F, 0.02F, 0.04F, 0.45F,null, "Tool_R", "biped/solemn_lament/auto_d5", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT)
                //.addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.MAGIC_BULLET_SPIN_SWING)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.02f, false, 0.44f));

        SOLEMN_LAMENT_AUTO_L0 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.36F, 0.47F, 0.73F,null, "Tool_L", "biped/solemn_lament/auto_l0", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_departed")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_READY)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.36f, true, 0.72f));

        SOLEMN_LAMENT_AUTO_L1 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.06F, 0.07F, 0.8F,ColliderPreset.FIST, "Tool_L", "biped/solemn_lament/auto_l1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_departed")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_SPIN)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);

        SOLEMN_LAMENT_AUTO_L2 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.06F, 0.08F, 0.45F,null, "Tool_L", "biped/solemn_lament/auto_l2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_departed")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.PAPER_FLIP)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.06f, true, 0.44f));

        SOLEMN_LAMENT_AUTO_R0 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.32F, 0.33F, 0.73F,null, "Tool_R", "biped/solemn_lament/auto_r0", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_READY)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.32f, false, 0.72f));

        SOLEMN_LAMENT_AUTO_R1 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.06F, 0.07F, 0.9F,ColliderPreset.FIST, "Tool_R", "biped/solemn_lament/auto_r1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_SPIN)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);

        SOLEMN_LAMENT_AUTO_R2 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.06F, 0.08F, 0.45F,null, "Tool_R", "biped/solemn_lament/auto_r2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.PAPER_FLIP)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.06f, false, 0.44f));

        SOLEMN_LAMENT_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/solemn_lament/reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentReloadEvent(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 4, 2, 6))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        SOLEMN_LAMENT_DASH_L = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.61F, 0.63F, 1F,null, "Tool_L", "biped/solemn_lament/dash_l", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_departed")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_READY)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.6f, true, 1.6f));

        SOLEMN_LAMENT_DASH_R = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.61F, 0.63F, 1F,null, "Tool_R", "biped/solemn_lament/dash_r", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_living")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_READY)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentEvent(0.6f, false, 1.6f));

        SOLEMN_LAMENT_GUARD = new StaticAnimation( true, "biped/solemn_lament/guard", biped);
        SOLEMN_LAMENT_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/solemn_lament/guard_hit", biped);
        SOLEMN_LAMENT_GUARD_PARRY1 = new GuardAnimation(0.05f,0.5f, "biped/solemn_lament/parry1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);
        SOLEMN_LAMENT_GUARD_PARRY2 = new GuardAnimation(0.05f,0.5f, "biped/solemn_lament/parry2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);
        SOLEMN_LAMENT_GUARD_COUNTERATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.92F, 0.94F, 1.5F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_R", "biped/solemn_lament/counterattack", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_dual")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.SOLEMN_LAMENT_READY)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 4)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentDualEvent(0.92f));

        SOLEMN_LAMENT_SPECIAL_ATTACK = new EgoAttackAnimation(0.08F, 0.3F, 0.4F, 0.56F, 1.1F,ColliderPreset.TOOLS, "Tool_R", "biped/solemn_lament/burst", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "solemn_lament_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 4)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentCharge(1.05f));

        SOLEMN_LAMENT_SPECIAL_ATTACK_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/solemn_lament/burst_reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentReloadEvent(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 2, 2, 4))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        SOLEMN_LAMENT_GUARD_RELOAD = new DodgeAnimation(0.1f, "biped/solemn_lament/parryreload",0.5f, 0.7f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.15f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, solemnLamentDodgeReloadEvent());

    }


    private static StaticAnimation.Event[] solemnLamentCharge(float timeEnd) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {

            if (entitypatch.getOriginal().getPersistentData().contains("solemnLamentChargeHit"))
                entitypatch.getOriginal().getPersistentData().remove("solemnLamentChargeHit");

            if (entitypatch.getOriginal().getPersistentData().contains("solemnLamentChargeStaggered"))
                entitypatch.getOriginal().getPersistentData().remove("solemnLamentChargeStaggered");
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_READY, 1.3f, 1.3f);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(timeEnd, (entitypatch) -> {
            if (entitypatch.getOriginal().getPersistentData().contains("solemnLamentChargeHit")) {
                //entitypatch.getOriginal().removeEffect(SolemnLamentEffects.getDeparted());
                //entitypatch.getOriginal().removeEffect(SolemnLamentEffects.getLiving());
            }

            if (entitypatch.getOriginal().getPersistentData().contains("solemnLamentChargeStaggered"))
                entitypatch.reserveAnimation(SolemnLamentMovesetAnims.SOLEMN_LAMENT_SPECIAL_ATTACK_RELOAD);


        }, StaticAnimation.Event.Side.SERVER);

        return events;
    }

    private static StaticAnimation.Event[] solemnLamentEvent(float time, boolean departedGun, float timeEnd) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {

            boolean fast = time < 0.2f;

            entitypatch.playSound(departedGun ? (fast ? EgoWeaponsSounds.SOLEMN_LAMENT_FAST_BLACK : EgoWeaponsSounds.SOLEMN_LAMENT_AUTO_BLACK) : (fast ? EgoWeaponsSounds.SOLEMN_LAMENT_FAST_WHITE : EgoWeaponsSounds.SOLEMN_LAMENT_AUTO_WHITE), 1, 1);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.2,-0.4), 1, departedGun ? EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_DEPARTED.get() : EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_LIVING.get(), 0, departedGun ? "Tool_L" : "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.5,-0.4), 2, departedGun ? EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get() : EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, departedGun ? "Tool_L" : "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1,-0.4), 2, departedGun ? EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get() : EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, departedGun ? "Tool_L" : "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.4), 2, departedGun ? EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get() : EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, departedGun ? "Tool_L" : "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.4), 2, departedGun ? EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get() : EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, departedGun ? "Tool_L" : "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3.2,-0.4), 2, departedGun ? EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get() : EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, departedGun ? "Tool_L" : "Tool_R", false);

            if (!entitypatch.getOriginal().level.isClientSide()) {
                SolemnLamentEffects.decrementEffect(entitypatch.getOriginal(), departedGun ? SolemnLamentEffects.getDeparted() : SolemnLamentEffects.getLiving());
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(timeEnd, (entitypatch) -> {
            if (SolemnLamentEffects.getAmmoCount(entitypatch.getOriginal(), departedGun ? SolemnLamentEffects.getDeparted() : SolemnLamentEffects.getLiving()) == 0) {
                if (SolemnLamentEffects.getAmmoCount(entitypatch.getOriginal(), departedGun ? SolemnLamentEffects.getLiving() : SolemnLamentEffects.getDeparted()) > 0) {
                    entitypatch.reserveAnimation(departedGun ? SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_R1 : SolemnLamentMovesetAnims.SOLEMN_LAMENT_AUTO_L1);
                }
            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    private static StaticAnimation.Event[] solemnLamentDualEvent(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {

            boolean fast = time < 0.2f;

            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_AUTO_BLACK, 1, 1);
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_AUTO_WHITE, 1, 1);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.2,-0.4), 1, EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_DEPARTED.get(), 0, "Tool_L", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.5,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L" , false);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.2,-0.4), 1, EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_LIVING.get(), 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.5,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R" , false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R" , false);



            if (!entitypatch.getOriginal().level.isClientSide()) {
                SolemnLamentEffects.decrementEffect(entitypatch.getOriginal(), SolemnLamentEffects.getDeparted());
                SolemnLamentEffects.decrementEffect(entitypatch.getOriginal(), SolemnLamentEffects.getLiving());
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            entitypatch.playSound(EgoWeaponsSounds.GUARD_WIN, 1.3f, 1.3f);
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 1, 1);
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 0.7f, 0.7f);
        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(0.4f, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_READY, 1, 1);
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_READY, 0.7f, 0.7f);
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    private static StaticAnimation.Event[] solemnLamentReloadEvent(SoundEvent startSound, int baseReload, int bonus, int desparityPreset) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.playSound(startSound, 1.3f, 1.3f);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L", false);
            if (!entitypatch.getOriginal().level.isClientSide()) {

                // Calculates Sanity Loss
                if (entitypatch.getOriginal() instanceof PlayerEntity) {
                    int lumpReloadedSum = baseReload*2+desparityPreset;
                    int ammosum = SolemnLamentEffects.getAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving()) + SolemnLamentEffects.getAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving());
                    if (ammosum < lumpReloadedSum)
                        SanitySystem.damageSanity((PlayerEntity) entitypatch.getOriginal(), 0.25f*(lumpReloadedSum - ammosum));
                    else
                        SanitySystem.healSanity((PlayerEntity) entitypatch.getOriginal(), 0.25f*(ammosum - lumpReloadedSum));
                }



                int desparity = entitypatch.getOriginal().level.random.nextInt(desparityPreset);
                int randomInt = entitypatch.getOriginal().level.random.nextInt(2);
                SolemnLamentEffects.setAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getDeparted(), (baseReload + desparityPreset / 2) - desparity + randomInt);
                SolemnLamentEffects.setAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving(), baseReload + desparity);
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.7f, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_RELOAD, 1.3f, 1.3f);
            if (!entitypatch.getOriginal().level.isClientSide()) {
                SolemnLamentEffects.setAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getDeparted(), entitypatch.getOriginal().getEffect(SolemnLamentEffects.getDeparted()).getAmplifier() + bonus);
                SolemnLamentEffects.setAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving(), entitypatch.getOriginal().getEffect(SolemnLamentEffects.getLiving()).getAmplifier() + bonus);
            }

        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    private static StaticAnimation.Event[] solemnLamentDodgeReloadEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            entitypatch.playSound(EgoWeaponsSounds.GUARD_WIN, 1.3f, 1.3f);
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 1.3f, 1.3f);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), 0.1f, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.3,-0.4), 2, EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), 0.1f, "Tool_L", false);



        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 1.3f, 1.3f);
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_SPIN, 1f, 1f);

        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(0.8f, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_RELOAD, 1.3f, 1.3f);
            if (!entitypatch.getOriginal().level.isClientSide()) {
                int desparity = entitypatch.getOriginal().level.random.nextInt(3);
                int randomInt = entitypatch.getOriginal().level.random.nextInt(2);

                // Calculates Sanity Loss
                if (entitypatch.getOriginal() instanceof PlayerEntity) {
                    int lumpReloadedSum = 7;
                    int ammosum = SolemnLamentEffects.getAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving()) + SolemnLamentEffects.getAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving());
                    if (ammosum < lumpReloadedSum)
                        SanitySystem.damageSanity((PlayerEntity) entitypatch.getOriginal(), 0.25f*(lumpReloadedSum - ammosum));
                    else
                        SanitySystem.healSanity((PlayerEntity) entitypatch.getOriginal(), 0.25f*(ammosum - lumpReloadedSum));
                }

                SolemnLamentEffects.setAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getDeparted(), 4 - desparity + randomInt);
                SolemnLamentEffects.setAmmoCount(entitypatch.getOriginal(), SolemnLamentEffects.getLiving(), 2 + desparity);
            }

        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
}
