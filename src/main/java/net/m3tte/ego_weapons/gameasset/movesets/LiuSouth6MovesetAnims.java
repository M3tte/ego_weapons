package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.oeufi.OeufiHalberd;
import net.m3tte.ego_weapons.item.sunshower.Sunshower;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;

import static net.m3tte.ego_weapons.item.liu.LiuFireGauntlet.focusEmbers;
import static net.m3tte.ego_weapons.item.liu.LiuFireGauntlet.specialHitEvent;
import static net.m3tte.ego_weapons.item.oeufi.OeufiHalberd.*;

public class LiuSouth6MovesetAnims {


    public static StaticAnimation LIU_S6_IDLE;
    public static StaticAnimation LIU_S6_WALK;
    public static StaticAnimation LIU_S6_RUN;
    public static StaticAnimation LIU_S6_KNEEL;
    public static StaticAnimation LIU_S6_SNEAK;
    public static StaticAnimation LIU_S6_JUMP;
    public static StaticAnimation LIU_S6_AUTO_1;
    public static StaticAnimation LIU_S6_AUTO_2;
    public static StaticAnimation LIU_S6_AUTO_3;
    public static StaticAnimation LIU_S6_GUARD;
    public static StaticAnimation LIU_S6_GUARD_HIT;
    public static StaticAnimation LIU_S6_PARRY;

    public static StaticAnimation LIU_S6_INNATE_1;
    public static StaticAnimation LIU_S6_INNATE_2;

    public static StaticAnimation LIU_S6_SPECIAL_1;
    public static StaticAnimation LIU_S6_SPECIAL_2;
    public static StaticAnimation LIU_S6_SPECIAL_3;
    public static StaticAnimation LIU_S6_SPECIAL_4;

    public static StaticAnimation LIU_S6_FOCUS_EMBERS;

    public static StaticAnimation LIU_S6_FOCUS_EMBERS_BASH;
    public static StaticAnimation LIU_S6_DASH;
    public static void build(Model biped) {
        System.out.println("Building LIU SOUTH 6 Animations");
        LIU_S6_IDLE = new StaticAnimation(true, "biped/liu_south_6/idle", biped);
        LIU_S6_WALK = new MovementAnimation(true, "biped/liu_south_6/walk", biped);
        LIU_S6_RUN = new MovementAnimation(true, "biped/liu_south_6/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);
        LIU_S6_KNEEL = new StaticAnimation(true, "biped/liu_south_6/kneel", biped);
        LIU_S6_SNEAK = new MovementAnimation(true, "biped/liu_south_6/sneak", biped);
        LIU_S6_JUMP = new StaticAnimation(false, "biped/liu_south_6/jump", biped);

        LIU_S6_DASH = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.45F, 0.75F, 1.5F, null, "Tool_R", "biped/liu_south_6/dash", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);


        LIU_S6_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.45F, 0.75F, 1.3F, null, "Tool_R", "biped/liu_south_6/auto_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(0f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);

        LIU_S6_AUTO_2 = new BasicEgoAttackAnimation(0.08F, 0.1F, 0.33F, 0.56F, 0.8F, null, "Tool_L", "biped/liu_south_6/auto_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F);

        LIU_S6_AUTO_3 = new BasicEgoAttackAnimation(0.08F, 0.08F, 0.25F, 0.45F, 1F, null, "Tool_R", "biped/liu_south_6/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F);

        LIU_S6_GUARD = new StaticAnimation( true, "biped/liu_south_6/guard", biped);
        LIU_S6_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/liu_south_6/guard_hit", biped);
        LIU_S6_PARRY = new GuardAnimation(0.05f,0.1f, "biped/liu_south_6/guard_parry", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        LIU_S6_INNATE_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, null, "Tool_R", "biped/liu_south_6/innate_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_innate1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        LIU_S6_INNATE_2 = new EgoAttackAnimation(0.1F, 0.1F, 0.15F, 0.66F, 1.1F, null, "Tool_R", "biped/liu_south_6/innate_2", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_innate2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_AUTO_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        LIU_S6_SPECIAL_1 = new EgoAttackAnimation(0.08F, 0.1F, 0.5F, 0.7F, 1.3F, null, "Tool_L", "biped/liu_south_6/special_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_sp1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_SPECIAL_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialHitEvent(0, 0.9f));

        LIU_S6_SPECIAL_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.2F, 0.4F, 1.5F, null, "Tool_R", "biped/liu_south_6/special_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_sp2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_SPECIAL_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialHitEvent(1, 0.9f));

        LIU_S6_SPECIAL_3 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.2F, 0.4F, 1.5F, null, "Tool_R", "biped/liu_south_6/special_3", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_sp3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_SPECIAL_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialHitEvent(2, 1f));

        LIU_S6_SPECIAL_4 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.3F, 0.7F, 1.5F, null, "Tool_R", "biped/liu_south_6/special_4", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "liu_s6_sp4")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LIU_S6_SPECIAL_4)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LIU_S6_AUTO_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        LIU_S6_FOCUS_EMBERS = new ActionAnimation(0.1f, "biped/liu_south_6/focus_embers", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, focusEmbers());

        LIU_S6_FOCUS_EMBERS_BASH = new ActionAnimation(0.1f, "biped/liu_south_6/focus_embers_bash", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);
    }
}
