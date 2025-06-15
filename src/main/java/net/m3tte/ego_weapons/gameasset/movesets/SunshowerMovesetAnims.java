package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.item.sunshower.Sunshower;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.attackSound;
import static net.m3tte.ego_weapons.item.sunshower.Sunshower.getAwayEvent;
import static net.m3tte.ego_weapons.item.sunshower.Sunshower.setOpenstate;
import static net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets.SUNSHOWER_COL;
import static net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets.SUNSHOWER_COL_LARGE;

public class SunshowerMovesetAnims {

    public static StaticAnimation SUNSHOWER_IDLE;
    public static StaticAnimation SUNSHOWER_WALK;
    public static StaticAnimation SUNSHOWER_SNEAK;
    public static StaticAnimation SUNSHOWER_GUARD;
    public static StaticAnimation SUNSHOWER_GUARD_HIT;
    public static StaticAnimation SUNSHOWER_PARRY_HIT;
    public static StaticAnimation SUNSHOWER_PARRY_HIT_2;
    public static StaticAnimation SUNSHOWER_PARRY_HIT_3;
    public static StaticAnimation SUNSHOWER_COUNTER_EVADE;
    public static StaticAnimation SUNSHOWER_COUNTER_ATTACK;
    public static StaticAnimation SUNSHOWER_RUN;
    public static StaticAnimation SUNSHOWER_JUMP;
    public static StaticAnimation SUNSHOWER_JUMP_ATTACK;
    public static StaticAnimation SUNSHOWER_JUMP_ATTACK_F;
    public static StaticAnimation SUNSHOWER_KNEEL;
    public static StaticAnimation SUNSHOWER_AUTO_1;
    public static StaticAnimation SUNSHOWER_AUTO_2;
    public static StaticAnimation SUNSHOWER_AUTO_3;
    public static StaticAnimation SUNSHOWER_AUTO_4;
    public static StaticAnimation SUNSHOWER_PUDDLE_STOMP_1;
    public static StaticAnimation SUNSHOWER_PUDDLE_STOMP_2;
    public static StaticAnimation SUNSHOWER_PUDDLE_STOMP_3;
    public static StaticAnimation SUNSHOWER_SPREAD_OUT_1;
    public static StaticAnimation SUNSHOWER_SPREAD_OUT_2;
    public static StaticAnimation SUNSHOWER_SPREAD_OUT_3;
    public static StaticAnimation SUNSHOWER_DASH;
    public static StaticAnimation SUNSHOWER_GET_AWAY;

    public static void build(Model biped) {

        SUNSHOWER_IDLE = new StaticAnimation(0.3f,true, "biped/sunshower/idle", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0));
        SUNSHOWER_KNEEL = new StaticAnimation(0.3f,true, "biped/sunshower/kneel", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0));
        SUNSHOWER_WALK = new MovementAnimation(0.3f,true, "biped/sunshower/walk", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0));
        SUNSHOWER_SNEAK = new MovementAnimation(0.3f,true, "biped/sunshower/sneak", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0));
        SUNSHOWER_RUN = new MovementAnimation(0.3f,true, "biped/sunshower/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2f);
        SUNSHOWER_JUMP = new MovementAnimation(0.3f,false, "biped/sunshower/jump", biped);
        SUNSHOWER_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.55F, 0.83F, 1F, null, "Tool_R", "biped/sunshower/auto_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_auto_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.NONE)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(0f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.auto1Event(EgoWeaponsSounds.SUNSHOWER_AUTO_1, 0f));

        SUNSHOWER_AUTO_2 = new BasicEgoAttackAnimation(0.08F, 0.1F, 0.12F, 0.25F, 0.35F, null, "Tool_R", "biped/sunshower/auto_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_auto_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.auto2Event());

        SUNSHOWER_AUTO_3 = new BasicEgoAttackAnimation(0.08F, 0.08F, 0.08F, 0.33F, 0.6F, null, "Tool_R", "biped/sunshower/auto_3", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_auto_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.auto3Event());

        SUNSHOWER_AUTO_4 = new BasicEgoAttackAnimation(0.01F, 0.15F, 0.16F, 0.5F, 1.2F, null, "Tool_R", "biped/sunshower/auto_4", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_auto_4")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-4.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, attackSound(EgoWeaponsSounds.SUNSHOWER_AUTO_4, 0f));

        SUNSHOWER_GUARD = new StaticAnimation( true, "biped/sunshower/guard", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(1, 0.08f));
        SUNSHOWER_GUARD_HIT = new GuardAnimation(0.05f, 0.4f,"biped/sunshower/guard_hit", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(1, 0.09f));
        SUNSHOWER_PARRY_HIT = new GuardAnimation(0.05f, "biped/sunshower/parry_hit", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0, 0.09f));
        SUNSHOWER_PARRY_HIT_2 = new GuardAnimation(0.05f, "biped/sunshower/parry_hit2", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0, 0.09f));
        SUNSHOWER_PARRY_HIT_3 = new GuardAnimation(0.05f, "biped/sunshower/parry_hit3", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, setOpenstate(0, 0.09f));


        SUNSHOWER_COUNTER_EVADE = new DodgeAnimation(0.01F, "biped/sunshower/counter_evade", 0.5f, 1f,  biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.counterEvent1());

        SUNSHOWER_COUNTER_ATTACK = (new EgoAttackAnimation(0.1F, "biped/sunshower/counter_attack", biped,
                new AttackAnimation.Phase(0.0F, 0.2F, 0.33F, 0.83f, 0.9F, 0.9F, "Tool_R", SUNSHOWER_COL).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.2f)).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.SWORD_STAB).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT),
                new AttackAnimation.Phase(0.9F, 0.9F, 1.25f, 1.4f, 2F, 2f, "Tool_R", SUNSHOWER_COL).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND,  EpicFightSounds.BLADE_RUSH_FINISHER).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_counter")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.counterEvent2());

        SUNSHOWER_JUMP_ATTACK = (new BasicEgoAttackAnimation(0.3F, 0.35F, 0.4F, 0.75F, 1F, SUNSHOWER_COL, "Tool_R", "biped/sunshower/jump_attack", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_jump_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.SWORD_STAB)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, attackSound(EgoWeaponsSounds.SUNSHOWER_AUTO_1, 0f));

        SUNSHOWER_JUMP_ATTACK_F = (new BasicEgoAttackAnimation(0.1F, 0.35F, 0.75F, 1F, 1.2F, null, "Tool_R", "biped/sunshower/jump_attack_f", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_jump_2")
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, attackSound(EgoWeaponsSounds.SUNSHOWER_AUTO_4, 0f));

        SUNSHOWER_PUDDLE_STOMP_1 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.4F, 0.8F, 1.1F, null, "Tool_R", "biped/sunshower/puddle_stomp_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_puddle_stomp_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "sunshower_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.auto3Event());

        SUNSHOWER_PUDDLE_STOMP_2 = (new EgoAttackAnimation(0.0F, "biped/sunshower/puddle_stomp_2", biped,
                new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.83f, 0.9F, 0.9F, "Tool_R", SUNSHOWER_COL).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.2f)).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT),
                new AttackAnimation.Phase(0.9F, 0.9F, 1.1f, 1.2f, 1.8F, 1.8f, "Tool_R", SUNSHOWER_COL_LARGE).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND,  EpicFightSounds.BLUNT_HIT_HARD).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT)))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_puddle_stomp_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "sunshower_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.puddleStomp2Event());

        SUNSHOWER_PUDDLE_STOMP_3 = new EgoAttackAnimation(0.00F, 0.4F, 1.45F, 1.55F, 2.5F, SUNSHOWER_COL_LARGE, "Tool_R", "biped/sunshower/puddle_stomp_3", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_puddle_stomp_3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "sunshower_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.puddleStomp3Event());

        SUNSHOWER_SPREAD_OUT_1 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.33F, 0.75F, 1.1F, null, "Chest", "biped/sunshower/spread_out_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_spread_out_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.SWORD_STAB)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(10))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.spreadout1Event());

        SUNSHOWER_SPREAD_OUT_2 = new EgoAttackAnimation(0.08F, 0.05F, 0.33F, 0.75F, 1.1F, null, "Tool_R", "biped/sunshower/spread_out_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_spread_out_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.SUNSHOWER_SPREAD_OUT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.spreadout2Event());

        SUNSHOWER_SPREAD_OUT_3 = new EgoAttackAnimation(0.08F, 0.05F, 0.5F, 0.6F, 1.1F, SUNSHOWER_COL_LARGE, "Tool_R", "biped/sunshower/spread_out_3", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_spread_out_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.SWORD_STAB)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO3_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.spreadout3Event());

        SUNSHOWER_DASH = new EgoAttackAnimation(0.08F, 0.05F, 0.33F, 0.75F, 1.1F, null, "Tool_R", "biped/sunshower/dash", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "sunshower_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.SUNSHOWER_SPREAD_OUT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.SUNSHOWER_AUTO1_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, Sunshower.dashAttackEvent());

        SUNSHOWER_GET_AWAY = new ActionAnimation(0.1f, "biped/sunshower/get_away", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, getAwayEvent());
    }
}
