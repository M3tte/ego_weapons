package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;

import static net.m3tte.ego_weapons.item.firefist.FirefistGauntlet.firefistFinalHitEvent;
import static net.m3tte.ego_weapons.item.firefist.FirefistGauntlet.firefistHitEvent;

public class FirefistMovesetAnims {
    public static StaticAnimation FIREFIST_IDLE;
    public static StaticAnimation FIREFIST_WALK;
    public static StaticAnimation FIREFIST_RUN;
    public static StaticAnimation FIREFIST_AUTO_1;
    public static StaticAnimation FIREFIST_AUTO_2;
    public static StaticAnimation FIREFIST_AUTO_3;
    public static StaticAnimation FIREFIST_GUARD;
    public static StaticAnimation FIREFIST_GUARD_HIT;
    public static StaticAnimation FIREFIST_PARRY;
    public static StaticAnimation FIREFIST_COUNTER;

    public static StaticAnimation FIREFIST_INNATE;
    public static StaticAnimation FIREFIST_INNATE_B;
    public static StaticAnimation FIREFIST_DASH;
    public static StaticAnimation FIREFIST_SPECIAL_1;
    public static StaticAnimation FIREFIST_SPECIAL_1B;
    public static StaticAnimation FIREFIST_SPECIAL_2;
    public static StaticAnimation FIREFIST_SPECIAL_3;
    public static StaticAnimation FIREFIST_RELOAD;
    public static StaticAnimation FIREFIST_IGNITION;
    public static StaticAnimation FIREFIST_EQUIP;

    public static void build(Model biped) {
        System.out.println("Building FIREFIST Animations");
        FIREFIST_IDLE = new StaticAnimation(true, "biped/firefist/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FirefistGauntlet.ignitionReset());
        FIREFIST_WALK = new MovementAnimation(true, "biped/firefist/walk", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FirefistGauntlet.ignitionReset());
        FIREFIST_RUN = new MovementAnimation(true, "biped/firefist/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);

        FIREFIST_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/firefist/reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FirefistGauntlet.reloadEvent())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.3f);

        FIREFIST_IGNITION = (new ActionAnimation(0.1f, 2.5f,   "biped/firefist/fuel_ignition", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FirefistGauntlet.ignitionEvent())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.95f);

        FIREFIST_DASH = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.45F, 0.75F, 1.5F, null, "Tool_R", "biped/firefist/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_LIGHT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);


        FIREFIST_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.33F, 0.6F, 1.1F, null, "Tool_R", "biped/firefist/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_LIGHT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);

        FIREFIST_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.33F, 0.56F, 0.8F, null, "Tool_R", "biped/firefist/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_LIGHT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        FIREFIST_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.5F, 0.65F, 1.5F, null, "Tool_R", "biped/firefist/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        FIREFIST_GUARD = new StaticAnimation( true, "biped/firefist/guard", biped);
        FIREFIST_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/firefist/guard_hit", biped);
        FIREFIST_PARRY = new GuardAnimation(0.05f,0.3f, "biped/firefist/guard_parry", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        FIREFIST_COUNTER = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.3F, 0.5F, 1.25F, null, "Tool_R", "biped/firefist/counter", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_counter")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_LIGHT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        /*FIREFIST_INNATE = new EgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, EgoWeaponsCapabilityPresets.FirefistSpew, "Tool_R", "biped/firefist/innate", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);*/


        FIREFIST_INNATE = (new EgoAttackAnimation(0.01F, "biped/firefist/innate", biped,
                new AttackAnimation.Phase(0.0F, 0.0F, 1F, 1.1F, 1.2F, 1.2F, "Tool_R", EgoWeaponsCapabilityPresets.FirefistSpew)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "firefist_innate_a_start")
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT),
                new AttackAnimation.Phase(1.2F, 1.25F, 1.3F, 1.4F, 1.5F, 1.5F, "Tool_R", EgoWeaponsCapabilityPresets.FirefistSpew)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT),
                new AttackAnimation.Phase(1.5F, 1.55f, 1.6F, 1.7F, 1.8F, 1.8F, "Tool_R", EgoWeaponsCapabilityPresets.FirefistSpew)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FIREFIST_SPIT_FIRE_2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT),
                new AttackAnimation.Phase(1.8f, 1.85f, 1.9f, 2f, 2.1f, 2.1f, "Tool_R", EgoWeaponsCapabilityPresets.FirefistSpew)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT),
                new EgoAttackAnimation.EgoAttackPhase(2.1f, 2.15f, 2.2f, 2.3f, 2F, 3F, "Tool_R", EgoWeaponsCapabilityPresets.FirefistSpew)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "firefist_innate_a_final")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.7f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
        )
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_innate_a")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FIREFIST_SPIT_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FirefistGauntlet.spewFireAnimation()));


        FIREFIST_INNATE_B = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.4F, 0.6F, 1.0F, null, "Tool_R", "biped/firefist/innate_b", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_innate_b")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_LIGHT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FirefistGauntlet.fireCounter());

        FIREFIST_SPECIAL_1 = new EgoAttackAnimation(0.08F, 0.1F, 0.95F, 1.2F, 2F, EgoWeaponsCapabilityPresets.SUNSHOWER_COL, "Tool_R", "biped/firefist/special_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "firefist_sp1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "firefist_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_SPECIAL_PUNCH_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, firefistHitEvent(0, 1.35f, EgoWeaponsSounds.FIREFIST_SPECIAL_START, null, 1.95f));

        FIREFIST_SPECIAL_1B = new EgoAttackAnimation(0.08F, 0.1F, 0.45F, 0.65F, 1.5F, null, "Tool_R", "biped/firefist/special_1b", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "firefist_sp1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "firefist_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_SPECIAL_PUNCH_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, firefistHitEvent(0, 1f, EgoWeaponsSounds.FIREFIST_SPECIAL_START, null, 1.45f));

        FIREFIST_SPECIAL_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.35F, 0.55F, 1.6F, null, "Tool_R", "biped/firefist/special_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "firefist_sp2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "firefist_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_SPECIAL_PUNCH_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, firefistHitEvent(1, 1.1f, null, null, 1.55f));

        FIREFIST_SPECIAL_3 = new EgoAttackAnimation(0.01F, 0.1F, 0.70F, 1.1F, 2.5F, null, "Tool_R", "biped/firefist/special_3", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.FIREFIST_3)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "firefist_sp3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "firefist_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_SPECIAL_PUNCH_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, firefistFinalHitEvent(2, 1.1f, 2.5f));

        FIREFIST_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/firefist/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.66f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


    }
}
