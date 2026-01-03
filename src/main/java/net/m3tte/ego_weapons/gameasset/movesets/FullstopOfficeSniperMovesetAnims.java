package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.abilities.armorAbilities.FullstopSniperFocus;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperWeapon.*;
import static net.m3tte.ego_weapons.gameasset.abilities.assistAttacks.FullstopRifleWeaponAssistAttack.fireFSRailgunAssistAttack;
import static net.m3tte.ego_weapons.gameasset.abilities.reloadAbilities.FullstopRifleReloadAbility.reloadFSRound;


public class FullstopOfficeSniperMovesetAnims {


    public static StaticAnimation FULLSTOP_SNIPER_IDLE;
    public static StaticAnimation FULLSTOP_SNIPER_WALK;
    public static StaticAnimation FULLSTOP_SNIPER_RUN;
    public static StaticAnimation FULLSTOP_SNIPER_KNEEL;
    public static StaticAnimation FULLSTOP_SNIPER_SNEAK;
    public static StaticAnimation FULLSTOP_SNIPER_JUMP;
    public static StaticAnimation FULLSTOP_SNIPER_GUARD;
    public static StaticAnimation FULLSTOP_SNIPER_HIT_SHORT;
    public static StaticAnimation FULLSTOP_SNIPER_HIT_LONG;
    public static StaticAnimation FULLSTOP_SNIPER_DASH;
    public static StaticAnimation FULLSTOP_SNIPER_DASH_G;
    public static StaticAnimation FULLSTOP_SNIPER_JUMP_ATTACK;
    public static StaticAnimation FULLSTOP_SNIPER_AUTO_G_1;
    public static StaticAnimation FULLSTOP_SNIPER_AUTO_G_2;
    public static StaticAnimation FULLSTOP_SNIPER_AUTO_G_3;
    public static StaticAnimation FULLSTOP_SNIPER_INNATE;
    public static StaticAnimation FULLSTOP_SNIPER_ASSIST;
    public static StaticAnimation FULLSTOP_SNIPER_SPECIAL;
    public static StaticAnimation FULLSTOP_SNIPER_AUTO_G_FINAL;
    public static StaticAnimation FULLSTOP_SNIPER_RELOAD;
    public static StaticAnimation FULLSTOP_SPECIAL_G;
    public static StaticAnimation FULLSTOP_SPECIAL_B;
    public static StaticAnimation FULLSTOP_GUARD;
    public static StaticAnimation FULLSTOP_SNIPER_GUARD_HIT;
    public static StaticAnimation FULLSTOP_SNIPER_PARRY;
    public static StaticAnimation FULLSTOP_SNIPER_FOCUS;
    public static StaticAnimation FULLSTOP_SNIPER_EQUIP;


    public static void build(Model biped) {

        FULLSTOP_SNIPER_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/fullstop_sniper/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.75f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        FULLSTOP_SNIPER_IDLE = new StaticAnimation(true, "biped/fullstop_sniper/idle", biped);
        FULLSTOP_SNIPER_WALK = new MovementAnimation(true, "biped/fullstop_sniper/walk", biped);
        FULLSTOP_SNIPER_RUN = new MovementAnimation(true, "biped/fullstop_sniper/run", biped);
        FULLSTOP_SNIPER_KNEEL = new StaticAnimation(true, "biped/fullstop_sniper/kneel", biped);
        FULLSTOP_SNIPER_SNEAK = new MovementAnimation(true, "biped/fullstop_sniper/sneak", biped);
        FULLSTOP_SNIPER_GUARD = new StaticAnimation(true, "biped/fullstop_sniper/guard", biped);
        FULLSTOP_SNIPER_GUARD_HIT = new GuardAnimation(0.05f,0.3f, "biped/fullstop_sniper/guard_hit", biped);
        FULLSTOP_SNIPER_PARRY = new GuardAnimation(0.01f,0.4f, "biped/fullstop_sniper/guard_parry", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);

        FULLSTOP_SNIPER_FOCUS = (new ActionAnimation(0.1f, 1.5f,   "biped/fullstop_sniper/focus", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, FullstopSniperFocus.activateFullstopFocus())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);


        FULLSTOP_SNIPER_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/fullstop_sniper/load_round", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, reloadFSRound(0.5f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        FULLSTOP_SNIPER_JUMP = new MovementAnimation(0.3f,false, "biped/fullstop_sniper/jump", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.0f);

        FULLSTOP_SNIPER_AUTO_G_1 = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.63f, 0.67F, 1.35f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/fullstop_sniper/auto_1", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_sn_auto_1_g")
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireFSRailgunFirst(0.63f));

        FULLSTOP_SNIPER_AUTO_G_2 = (new BasicEgoAttackAnimation(0.01F, 0.1F, 0.11f, 0.16F, 0.85F, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/fullstop_sniper/auto_2", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_sn_auto_2_g")
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireFSRailgun(0.11f, false));

        FULLSTOP_SNIPER_AUTO_G_3 = (new BasicEgoAttackAnimation(0.01F, 0.1F, 0.11f, 0.16F, 1.25f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/fullstop_sniper/auto_3", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_sn_auto_3_g")
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireFSRailgun(0.11f, true));

        FULLSTOP_SNIPER_INNATE = (new BasicEgoAttackAnimation(0.02F, 0.2F, 1.9f, 1.95F, 3.1f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/fullstop_sniper/innate", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_sn_innate")
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireFSRailgunInnate(1.9f));

        FULLSTOP_SNIPER_ASSIST = (new BasicEgoAttackAnimation(0.02F, 0.2F, 1.9f, 1.95F, 3.1f, ColliderPreset.FIST, "Tool_R", "biped/fullstop_sniper/innate", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_sn_innate")
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireFSRailgunAssistAttack(1.9f));

        FULLSTOP_SNIPER_SPECIAL = (new EgoAttackAnimation(0.05F, 0.15F, 3.45f, 3.55F, 5.1f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX_SP, "Tool_R", "biped/fullstop_sniper/special", biped))
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "fs_sn_special")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.75f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireFSRailgunSpecial(2.5f, 3.4f));


        // Set as long hit animations because short stun animations dont seem to work well
        FULLSTOP_SNIPER_HIT_SHORT = new LongHitAnimation(0.02f, "biped/fullstop_sniper/hurt_short", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2f);
        FULLSTOP_SNIPER_HIT_LONG = new LongHitAnimation(0.02f, "biped/fullstop_sniper/hurt_long", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2f);


    }
}
