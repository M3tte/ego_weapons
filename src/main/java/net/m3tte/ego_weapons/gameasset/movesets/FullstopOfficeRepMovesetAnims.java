package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.procedures.abilities.armorAbilities.FullstopAssistFire;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.standardDodgeEvent;
import static net.m3tte.ego_weapons.item.fullstop_rep.FullstopRepWeapon.*;

public class FullstopOfficeRepMovesetAnims {


    public static StaticAnimation FULLSTOP_REP_IDLE;
    public static StaticAnimation FULLSTOP_REP_WALK;
    public static StaticAnimation FULLSTOP_REP_RUN;
    public static StaticAnimation FULLSTOP_REP_KNEEL;
    public static StaticAnimation FULLSTOP_REP_SNEAK;
    public static StaticAnimation FULLSTOP_REP_GUARD;
    public static StaticAnimation FULLSTOP_REP_AUTO_B_1;
    public static StaticAnimation FULLSTOP_REP_AUTO_B_2;
    public static StaticAnimation FULLSTOP_REP_AUTO_B_3;
    public static StaticAnimation FULLSTOP_REP_DASH;
    public static StaticAnimation FULLSTOP_REP_DASH_G;
    public static StaticAnimation FULLSTOP_REP_JUMP_ATTACK;
    public static StaticAnimation FULLSTOP_REP_AUTO_G_1;
    public static StaticAnimation FULLSTOP_REP_AUTO_G_2;
    public static StaticAnimation FULLSTOP_REP_AUTO_G_3;
    public static StaticAnimation FULLSTOP_REP_AUTO_G_FINAL;
    public static StaticAnimation FULLSTOP_REP_RELOAD;
    public static StaticAnimation FULLSTOP_REP_EVADE;
    public static StaticAnimation FULLSTOP_REP_ASSIST_FIRE;
    public static StaticAnimation FULLSTOP_INNATE;
    public static StaticAnimation FULLSTOP_SPECIAL_G;
    public static StaticAnimation FULLSTOP_SPECIAL_B;
    public static StaticAnimation FULLSTOP_GUARD;
    public static StaticAnimation FULLSTOP_GUARD_HIT;
    public static StaticAnimation FULLSTOP_PARRY_1;
    public static StaticAnimation FULLSTOP_PARRY_2;
    public static StaticAnimation FULLSTOP_PARRY_3;


    public static void build(Model biped) {

        FULLSTOP_REP_IDLE = new StaticAnimation(true, "biped/fullstop_rep/idle", biped);
        FULLSTOP_REP_WALK = new MovementAnimation(true, "biped/fullstop_rep/walk", biped);
        FULLSTOP_REP_RUN = new MovementAnimation(true, "biped/fullstop_rep/run", biped);
        FULLSTOP_REP_KNEEL = new StaticAnimation(true, "biped/fullstop_rep/kneel", biped);
        FULLSTOP_REP_SNEAK = new MovementAnimation(true, "biped/fullstop_rep/sneak", biped);
        FULLSTOP_REP_GUARD = new StaticAnimation(true, "biped/fullstop_rep/guard", biped);

        FULLSTOP_REP_AUTO_B_1 = new BasicEgoAttackAnimation(0.1F, 0.35F, 0.5F, 0.75F, 1.05F, ColliderPreset.SWORD, "Tool_R", "biped/fullstop_rep/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_SLICE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F);

        FULLSTOP_REP_AUTO_B_2 = new BasicEgoAttackAnimation(0.04F, 0.1F, 0.15F, 0.35F, 0.75F, ColliderPreset.SWORD, "Tool_R", "biped/fullstop_rep/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_SLICE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        FULLSTOP_REP_AUTO_B_3 = new BasicEgoAttackAnimation(0.08F, 0.35F, 0.5F, 0.7F, 1.25F, ColliderPreset.SWORD, "Tool_R", "biped/fullstop_rep/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto3")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_JUMP_SLICE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        FULLSTOP_REP_DASH = new BasicEgoAttackAnimation(0.08F, 0.35F, 0.55F, 0.85F, 1.6F, ColliderPreset.SWORD, "Tool_R", "biped/fullstop_rep/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_dash")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_JUMP_SLICE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        FULLSTOP_REP_JUMP_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.22F, 0.33F, 0.6F, 1.25F, ColliderPreset.SWORD, "Tool_R", "biped/fullstop_rep/jump", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_jump")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_HEAVY_SLICE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        FULLSTOP_REP_AUTO_G_1 = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.5F, 0.55F, 1.1F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX, "Tool_L", "biped/fullstop_rep/auto_g1", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto_1_g")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "gun")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireGun(0.5f));

        FULLSTOP_REP_AUTO_G_2 = (new BasicEgoAttackAnimation(0.01F, 0.1F, 0.116F, 0.26F, 0.5F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX, "Tool_L", "biped/fullstop_rep/auto_g2", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto_2_g")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "gun")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireGun(0.116f));

        FULLSTOP_REP_AUTO_G_3 = (new BasicEgoAttackAnimation(0.01F, 0.1F, 0.116F, 0.26F, 0.5F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX, "Tool_L", "biped/fullstop_rep/auto_g3", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto_3_g")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "gun")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireGun(0.116f));

        FULLSTOP_REP_AUTO_G_FINAL = (new BasicEgoAttackAnimation(0.1F, 0.3F, 0.45F, 0.5F, 1.5F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_L", "biped/fullstop_rep/auto_g_final", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_auto_4_g")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "gun")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_HEAVY_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireGun(0.45f));

        FULLSTOP_REP_DASH_G = (new BasicEgoAttackAnimation(0.1F, 0.3F, 0.6F, 0.66F, 1.5F, EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT, "Tool_L", "biped/fullstop_rep/dash_g", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_rep_dash_g")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "gun")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_HEAVY_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireGun(0.6f));


        FULLSTOP_REP_EVADE = new DodgeAnimation(0f, "biped/fullstop_rep/evade", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1.3f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, standardDodgeEvent());


        FULLSTOP_REP_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/fullstop_rep/reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, reloadEvent())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.3f);

        FULLSTOP_REP_ASSIST_FIRE = (new ActionAnimation(0.1f, 1.5f,   "biped/fullstop_rep/assist_attack", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FullstopAssistFire.activateAssistFire())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        FULLSTOP_INNATE = new BasicEgoAttackAnimation(0.08F, 0.35F, 0.5F, 0.85F, 1.6F, ColliderPreset.SWORD, "Chest", "biped/fullstop_rep/innate", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fullstop_rep_innate")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_JUMP_SLICE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_SLIDE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, slideEvent());


        FULLSTOP_SPECIAL_G = new EgoAttackAnimation(0.2F, "biped/fullstop_rep/special_g", biped,
                new AttackAnimation.Phase(0.0F, 0.5F, 1.05F, 1.15F, 1.25F, 1.25F, "Tool_L", EgoWeaponsCapabilityPresets.FSTL_HITBOX)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_FIRE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(1.25F, 1.25F, 1.3F, 1.4F, 1.5F, 1.5F, "Tool_L", EgoWeaponsCapabilityPresets.FSTL_HITBOX)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_FIRE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(1.5F, 1.5F, 1.53F, 1.6F, 1.8F, 1.8F, "Tool_L", EgoWeaponsCapabilityPresets.FSTL_HITBOX)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BULLET_CRIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_REP_FIRE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(1.8F, 1.8F, 2.05F, 2.25F, 3.6F, 3.6F, "Tool_R", EgoWeaponsCapabilityPresets.FSTL_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_HEAVY_SLICE)
        )
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fullstop_special_g")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "fullstop_rep_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialGun(1, 1.25f, 1.5f, 2f));

        FULLSTOP_SPECIAL_B = new EgoAttackAnimation(0.2F, "biped/fullstop_rep/special_b", biped,
                new AttackAnimation.Phase(0.0F, 0.5F, 1.13f, 1.25F, 1.43f, 1.43f, "Tool_R", EgoWeaponsCapabilityPresets.FSTL_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_SLICE),
                new AttackAnimation.Phase(1.43f, 1.5f, 1.6f, 1.81f, 2F, 2F, "Tool_R", EgoWeaponsCapabilityPresets.FSTL_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_SLICE),
                new AttackAnimation.Phase(2F, 2.1F, 2.36f, 2.5F, 3.86f, 3.86f, "Tool_R", EgoWeaponsCapabilityPresets.FSTL_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FULLSTOP_MACHETE_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_REP_HEAVY_SLICE)
        )
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fullstop_special_b")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "fullstop_rep_special")
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialBlade(1.1f, 2.3f));

        FULLSTOP_GUARD = new StaticAnimation( true, "biped/fullstop_rep/guard", biped);
        FULLSTOP_GUARD_HIT = new GuardAnimation(0.05f,0.3f, "biped/fullstop_rep/guard_hit", biped);
        FULLSTOP_PARRY_1 = new GuardAnimation(0.05f,0.3f, "biped/fullstop_rep/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);
        FULLSTOP_PARRY_2 = new GuardAnimation(0.05f,0.3f, "biped/fullstop_rep/parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);
        FULLSTOP_PARRY_3 = new ActionAnimation(0.05f,0.6f, "biped/fullstop_rep/parry_3", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);


    }
}
