package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoAttackPhase;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;



public class MimicryMovesetAnims {
    public static StaticAnimation KALI_EGO_AUTO_1;
    public static StaticAnimation KALI_EGO_AUTO_2;
    public static StaticAnimation KALI_AUTO_1;
    public static StaticAnimation KALI_AUTO_2;
    public static StaticAnimation KALI_AUTO_3;
    public static StaticAnimation KALI_AUTO_4;
    public static StaticAnimation KALI_DASH;
    public static StaticAnimation KALI_ONRUSH;
    public static StaticAnimation KALI_REND;
    public static StaticAnimation KALI_JUMP;
    public static StaticAnimation KALI_IMPACT;
    public static StaticAnimation KALI_GUARD;
    public static StaticAnimation KALI_GUARD_HIT;
    public static StaticAnimation KALI_IDLE;
    public static StaticAnimation GREAT_SPLIT_HORIZONTAL;
    public static StaticAnimation GREAT_SPLIT_VERTICAL;
    public static StaticAnimation KALI_KNEEL;
    public static StaticAnimation KALI_PARRY_1;
    public static StaticAnimation KALI_PARRY_2;
    public static StaticAnimation KALI_PARRY_EVADE;
    public static StaticAnimation KALI_RUN;
    public static StaticAnimation KALI_SNEAK;
    public static StaticAnimation KALI_WALK;


    public static StaticAnimation MIMICRY_AUTO_1;
    public static StaticAnimation MIMICRY_AUTO_2;
    public static StaticAnimation MIMICRY_AUTO_3;
    public static StaticAnimation MIMICRY_RUN;
    public static StaticAnimation MIMICRY_SNEAK;
    public static StaticAnimation MIMICRY_KNEEL;
    public static StaticAnimation MIMICRY_WALK;
    public static StaticAnimation MIMICRY_IDLE;
    public static StaticAnimation MIMICRY_JUMP;
    public static StaticAnimation MIMICRY_GUARD;
    public static StaticAnimation MIMICRY_GUARD_HIT;
    public static StaticAnimation MIMICRY_DASH;
    public static StaticAnimation MIMICRY_HELLO;
    public static StaticAnimation MIMICRY_GOODBYE;
    public static StaticAnimation MIMICRY_GOODBYE_ENHANCED;


    public static StaticAnimation KALI_EGO_IDLE;
    public static StaticAnimation KALI_EGO_KNEEL;
    public static StaticAnimation KALI_EGO_WALK;

    public static StaticAnimation KALI_EGO_RUN;
    public static StaticAnimation KALI_EGO_SNEAK;
    public static StaticAnimation MIMICRY_EQUIP;

    public static void build(Model biped) {

        MIMICRY_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/mimicry/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, equipEffect(0.5f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);



        KALI_IDLE = new StaticAnimation(0.3f,true, "biped/kali/idle", biped);
        KALI_KNEEL = new StaticAnimation(0.3f,true, "biped/kali/kneel", biped);
        KALI_WALK = new MovementAnimation(0.3f,true, "biped/kali/walk", biped);

        KALI_EGO_IDLE = new StaticAnimation(0.3f,true, "biped/kali/ego_idle", biped);
        KALI_EGO_KNEEL = new StaticAnimation(0.3f,true, "biped/kali/ego_kneel", biped);
        KALI_EGO_WALK = new MovementAnimation(0.3f,true, "biped/kali/ego_walk", biped);
        KALI_EGO_RUN = new StaticAnimation(0.3f,true, "biped/kali/ego_run", biped);
        KALI_EGO_SNEAK = new MovementAnimation(0.3f,true, "biped/kali/ego_sneak", biped);

        KALI_RUN = new MovementAnimation(0.3f,true, "biped/kali/run", biped);
        KALI_SNEAK = new MovementAnimation(0.3f, true, "biped/kali/sneak", biped);

        KALI_GUARD = new StaticAnimation( true, "biped/kali/guard", biped);
        KALI_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/kali/guard_hit", biped);
        KALI_PARRY_1 = new GuardAnimation(0.05f,0.1f, "biped/kali/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.0f);
        KALI_PARRY_2 = new GuardAnimation(0.05f,0.1f, "biped/kali/parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.0f);
        KALI_PARRY_EVADE = new DodgeAnimation(0.1f, "biped/kali/parry_3",0.5f, 0.7f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);

        KALI_AUTO_1 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.2F, 0.54F, 0.63F, ColliderPreset.FIST, "Leg_L", "biped/kali/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_auto_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f);

        KALI_EGO_AUTO_1 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.2F, 0.54F, 0.63F, ColliderPreset.FIST, "Leg_L", "biped/kali/ego_auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_ego_auto_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_MACE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-1))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f);

        KALI_EGO_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.12F, 0.33F, 0.5F, 1.12F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/ego_auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_ego_auto_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);


        KALI_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.12F, 0.33F, 0.5F, 1.12F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_auto_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        KALI_AUTO_3 = (new BasicEgoAttackAnimation(0.01F, 0.30F, 0.35F, 0.7F, 0.95F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_auto_3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        KALI_AUTO_4 = (new BasicEgoAttackAnimation(0.01F, 0.10F, 0.15F, 0.66F, 1.7F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/auto_4", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_auto_4")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HARD_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HORIZONTAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f);

        KALI_DASH = (new EgoAttackAnimation(0.02F, 0.2F, 0.25F, 0.6F, 1F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/dash", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_dash")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_STAB)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0f);


        KALI_REND = (new BasicEgoAttackAnimation(0.02F, 0.35F, 0.4F, 0.66F, 1F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/rend", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_rend")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0f);

        KALI_JUMP = (new BasicEgoAttackAnimation(0.3F, 0.3F, 0.33F, 0.83F, 1.4F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/jump", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_jump")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_STAB)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);

        KALI_IMPACT = (new BasicEgoAttackAnimation(0.1F, 0.35F, 0.5F, 0.66F, 1F, null, "Tool_R", "biped/kali/impact", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_impact")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95f)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);

        GREAT_SPLIT_HORIZONTAL = (new EgoAttackAnimation(0.1F, "biped/kali/kali_horizontal", biped,
                new EgoAttackPhase(0.0F, 0.1F, 0.16F, 0.5F, 1.01F, 1.01F, "Leg_L", ColliderPreset.FIST).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(6)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.3f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(EgoWeaponsAttackPhaseProperty.IDENTIFIER, "kali_great_split_horizontal_1"),
                new EgoAttackPhase(1.01F, 1.02F, 1.53F, 2F, 2.16F, 2.16F, "Tool_R", EgoWeaponsCapabilityPresets.SPLIT_HORIZONTAL)
                        .addProperty(EgoWeaponsAttackPhaseProperty.IDENTIFIER, "kali_great_split_horizontal_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HORIZONTAL_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND,  EgoWeaponsSounds.KALI_HARD_HORIZONTAL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_great_split_horizontal")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "mimicry_redmist_special")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.GSH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, greatSplitHorizontalEvent()));

        GREAT_SPLIT_VERTICAL = (new EgoAttackAnimation(0.1F, "biped/kali/kali_vertical", biped,
                new EgoAttackPhase(0.0F, 0.3F, 0.4F, 0.9F, 0.91F, 0.91F, "Tool_R", EgoWeaponsCapabilityPresets.LONGER_BLADE).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.2f)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f)).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_SPLIT_VERTICAL_HIT).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.IDENTIFIER, "kali_great_split_vertical_1"),
                new EgoAttackPhase(0.91F, 0.91F, 1.1F, 1.61F, 1.62F, 1.62F, "Tool_R", EgoWeaponsCapabilityPresets.LONGER_BLADE).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND,  EgoWeaponsSounds.KALI_SPLIT_VERTICAL_HIT).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.IDENTIFIER, "kali_great_split_vertical_2"),
                new EgoAttackPhase(1.625F, 1.7F, 2F, 2.18F, 3F, 3F, "Tool_R", EgoWeaponsCapabilityPresets.LONGER_BLADE).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_SPLIT_VERTICAL_SLASH).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                        .addProperty(EgoWeaponsAttackPhaseProperty.IDENTIFIER, "kali_great_split_vertical_3"))

                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_great_split_vertical")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "mimicry_redmist_innate")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85f));


        MIMICRY_AUTO_1 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.33F, 1F, 1.3F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/auto1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_auto_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.55f);

        MIMICRY_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.3F, 0.55F, 0.9F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/auto2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_auto_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75f);

        MIMICRY_AUTO_3 = (new BasicEgoAttackAnimation(0.1F, 0.1F, 0.45F, 0.75F, 1F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/auto3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_auto_3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.6f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75f);

        MIMICRY_HELLO = (new EgoAttackAnimation(0.1F, 0.1F, 0.43F, 0.9F, 1.2F, ColliderPreset.FIST, "Tool_L", "biped/mimicry/hello", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_hello")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_HELLO)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 3)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        MIMICRY_GOODBYE = (new EgoAttackAnimation(0.01F, 0.02F, 0.5F, 0.85F, 1.5F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/goodbye_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_goodbye")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "mimicry_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_HORIZONTAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(25f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3f);

        MIMICRY_GOODBYE_ENHANCED = (new EgoAttackAnimation(0.01F, 0.02F, 0.75F, 1.4F, 1.6F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/goodbye_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_goodbye_enh")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.GSH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "mimicry_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(25f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.setter(15f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, enhancedGoodbyeEvent());

        MIMICRY_DASH = (new EgoAttackAnimation(0.02F, 0.03F, 0.36F, 0.85F, 1.8F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/dash", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "mimicry_dash")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.KALI_STAB)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f);

        MIMICRY_IDLE = new StaticAnimation(0.3f,true, "biped/mimicry/idle", biped);
        MIMICRY_KNEEL = new StaticAnimation(0.3f,true, "biped/mimicry/kneel", biped);
        MIMICRY_WALK = new MovementAnimation(0.3f,true, "biped/mimicry/walk", biped);
        MIMICRY_JUMP = new MovementAnimation(0.3f,true, "biped/mimicry/jump", biped);

        MIMICRY_RUN = new MovementAnimation(0.3f,true, "biped/mimicry/run", biped);
        MIMICRY_SNEAK = new MovementAnimation(0.3f, true, "biped/mimicry/sneak", biped);

        MIMICRY_GUARD = new StaticAnimation( true, "biped/mimicry/guard", biped);
        MIMICRY_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/mimicry/guard_hit", biped);

        KALI_ONRUSH = (new EgoAttackAnimation(0.02F, 0.4F, 0.5F, 0.833F, 1.5F, ColliderPreset.FIST, "Arm_L", "biped/kali/onrush", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "kali_onrush")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_MACE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.BLACK_SILENCE_EVADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, onrushEvent());

    }

    public static StaticAnimation.Event[] equipEffect(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            entitypatch.playSound(SoundEvents.CHORUS_FLOWER_GROW, 4, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] onrushEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 1, 0);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] greatSplitHorizontalEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(1.5f, (entitypatch) -> {
            LivingEntity ent = entitypatch.getOriginal();
            ent.level.addParticle(EgoWeaponsParticles.GREAT_SPLIT_HORIZONTAL.get(), ent.getX(), ent.getY()+0.9f, ent.getZ(), 0, ent.getId(), 0);
        }, StaticAnimation.Event.Side.CLIENT);
        return events;
    }

    public static StaticAnimation.Event[] enhancedGoodbyeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.15f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), (int) 20, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
            }

            entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("morph", 1);

        }, StaticAnimation.Event.Side.CLIENT);

        events[1] = StaticAnimation.Event.create(1.5f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), (int) 20, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
            }

            entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("morph");

        }, StaticAnimation.Event.Side.CLIENT);
        return events;
    }
}
