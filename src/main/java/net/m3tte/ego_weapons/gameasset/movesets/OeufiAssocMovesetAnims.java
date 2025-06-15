package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.oeufi.OeufiHalberd;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;

import static net.m3tte.ego_weapons.item.oeufi.OeufiHalberd.*;
import static net.m3tte.ego_weapons.item.sunshower.Sunshower.getAwayEvent;

public class OeufiAssocMovesetAnims {


    public static StaticAnimation OEUFI_IDLE;
    public static StaticAnimation OEUFI_WALK;
    public static StaticAnimation OEUFI_RUN;
    public static StaticAnimation OEUFI_KNEEL;
    public static StaticAnimation OEUFI_SNEAK;
    public static StaticAnimation OEUFI_AUTO_1;
    public static StaticAnimation OEUFI_AUTO_2;
    public static StaticAnimation OEUFI_AUTO_3;
    public static StaticAnimation OEUFI_DASH;
    public static StaticAnimation OEUFI_INNATE;
    public static StaticAnimation OEUFI_INNATE_2;
    public static StaticAnimation OEUFI_SPECIAL_1;
    public static StaticAnimation OEUFI_SPECIAL_2;
    public static StaticAnimation OEUFI_SPECIAL_3;
    public static StaticAnimation OEUFI_GUARD;
    public static StaticAnimation OEUFI_GUARD_HIT;
    public static StaticAnimation OEUFI_PARRY_1;
    public static StaticAnimation OEUFI_PARRY_2;
    public static StaticAnimation OEUFI_PARRY_COUNTER;
    public static StaticAnimation OEUFI_PARRY_COUNTER_ATTACK;

    public static StaticAnimation OEUFI_OPEN_CONTRACT;
    public static StaticAnimation OEUFI_EVADE_CONTRACT;
    public static StaticAnimation OEUFI_COUNTER_CONTRACT;

    public static void build(Model biped) {
        System.out.println("Building OUFI Animations");
        OEUFI_IDLE = new StaticAnimation(true, "biped/oeufi/idle", biped);
        OEUFI_WALK = new MovementAnimation(true, "biped/oeufi/walk", biped);
        OEUFI_RUN = new MovementAnimation(true, "biped/oeufi/run", biped);
        OEUFI_KNEEL = new StaticAnimation(true, "biped/oeufi/kneel", biped);
        OEUFI_SNEAK = new MovementAnimation(true, "biped/oeufi/sneak", biped);

        OEUFI_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.35F, 0.45F, 0.65F, 0.75F, null, "Tool_R", "biped/oeufi/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_auto1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_IMPACT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        OEUFI_AUTO_2 = new BasicEgoAttackAnimation(0.08F, 0.3F, 0.4F, 0.7F, 0.85F, null, "Tool_R", "biped/oeufi/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_IMPACT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        OEUFI_AUTO_3 = new BasicEgoAttackAnimation(0.08F, 0.35F, 0.45F, 0.65F, 1F, null, "Tool_R", "biped/oeufi/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_auto3")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F);

        OEUFI_DASH = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.5F, 0.65F, 1.2F, null, "Tool_R", "biped/oeufi/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_dash")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1F);

        OEUFI_INNATE = new BasicEgoAttackAnimation(0.08F, 0.25F, 0.33F, 0.5F, 1F, null, "Tool_R", "biped/oeufi/innate_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_innate")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_IMPACT_UP)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F);

        OEUFI_INNATE_2 = new BasicEgoAttackAnimation(0.08F, 0.1F, 0.25F, 0.5F, 1.1F, null, "Tool_R", "biped/oeufi/innate_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_innate_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_IMPACT_DOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1F);

        OEUFI_SPECIAL_1 = new BasicEgoAttackAnimation(0.08F, 0.25F, 0.5F, 0.8F, 1.7f, null, "Tool_R", "biped/oeufi/special_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "special1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "oeufi_halberd_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_IMPACT_UP)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special1Event(0.8f));

        OEUFI_SPECIAL_2 = new BasicEgoAttackAnimation(0.08F, 0.25F, 0.58F, 0.83F, 1.6F, null, "Tool_R", "biped/oeufi/special_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "special2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "oeufi_halberd_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_IMPACT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special2Event(1f));

        OEUFI_SPECIAL_3 = new BasicEgoAttackAnimation(0.08F, 0.25F, 0.5F, 0.85F, 1.5F, null, "Tool_R", "biped/oeufi/special_3", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "special3")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "oeufi_halberd_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_PIERCE_HEAVY)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F);

        OEUFI_GUARD = new StaticAnimation( true, "biped/oeufi/guard", biped);
        OEUFI_GUARD_HIT = new GuardAnimation(0.05f,0.2f, "biped/oeufi/guard_hit", biped);
        OEUFI_PARRY_1 = new GuardAnimation(0.05f,0.4f, "biped/oeufi/parry_hit_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);
        OEUFI_PARRY_2 = new GuardAnimation(0.05f,0.4f, "biped/oeufi/parry_hit_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);
        OEUFI_PARRY_COUNTER = new DodgeAnimation(0.01F, "biped/oeufi/parry_counter_1", 0.5f, 1f,  biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, OeufiHalberd.counterEvent1());

        OEUFI_PARRY_COUNTER_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.33F, 0.75F, 1.33F, null, "Tool_R", "biped/oeufi/parry_counter_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_counter_2")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1F);

        OEUFI_OPEN_CONTRACT = new ActionAnimation(0.1f, "biped/oeufi/open_contract", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, contractOpen());

        OEUFI_EVADE_CONTRACT = new DodgeAnimation(0.01F, "biped/oeufi/contract_evade", 0.5f, 1f,  biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, OeufiHalberd.contractEvent());

        OEUFI_COUNTER_CONTRACT = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.25F, 0.5F, 1.4F, null, "Tool_R", "biped/oeufi/contract_counter", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "oeufi_contract_counter")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.OUFI_PIERCE_HEAVY)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.OUFI_SWING_PIERCE)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.OUFI_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);
    }
}
