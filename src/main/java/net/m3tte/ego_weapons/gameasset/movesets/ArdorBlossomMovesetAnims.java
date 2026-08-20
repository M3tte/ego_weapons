package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.abilities.armorAbilities.ArdorBlossomArmorAbility;
import net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities.ArdorBlossomBatWeaponAbility;
import net.m3tte.ego_weapons.item.ardor_blossom.ArdorBlossomSuit;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;

import static net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities.ArdorBlossomBatWeaponAbility.castOverclockExplosion;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.basicSwingEvent;

public class ArdorBlossomMovesetAnims {
    public static StaticAnimation ARDOR_BLOSSOM_IDLE;
    public static StaticAnimation ARDOR_BLOSSOM_WALK;
    public static StaticAnimation ARDOR_BLOSSOM_RUN;
    public static StaticAnimation ARDOR_BLOSSOM_GUARD;
    public static StaticAnimation ARDOR_BLOSSOM_GUARD_HIT;
    public static StaticAnimation ARDOR_BLOSSOM_PARRY_1;
    public static StaticAnimation ARDOR_BLOSSOM_PARRY_2;
    public static StaticAnimation ARDOR_BLOSSOM_PARRY_3;
    public static StaticAnimation ARDOR_BLOSSOM_EVADE;
    public static StaticAnimation ARDOR_BLOSSOM_COUNTER;
    public static StaticAnimation ARDOR_BLOSSOM_KNEEL;
    public static StaticAnimation ARDOR_BLOSSOM_AUTO_1;
    public static StaticAnimation ARDOR_BLOSSOM_AUTO_2;
    public static StaticAnimation ARDOR_BLOSSOM_AUTO_3;
    public static StaticAnimation ARDOR_BLOSSOM_DASH;
    public static StaticAnimation ARDOR_BLOSSOM_JUMP_ATTACK;
    public static StaticAnimation ARDOR_BLOSSOM_INNATE_1;
    public static StaticAnimation ARDOR_BLOSSOM_INNATE_2;
    public static StaticAnimation ARDOR_BLOSSOM_INNATE_3;
    public static StaticAnimation ARDOR_BLOSSOM_SPECIAL_1;
    public static StaticAnimation ARDOR_BLOSSOM_SPECIAL_2;
    public static StaticAnimation ARDOR_BLOSSOM_ARMOR_SKILL;
    public static StaticAnimation DASH_FORWARD;
    public static StaticAnimation DASH_BACKWARD;
    public static StaticAnimation WEAVE_1;
    public static StaticAnimation WEAVE_2;
    public static StaticAnimation DASH_R;
    public static StaticAnimation DASH_L;
    public static StaticAnimation ARDOR_BLOSSOM_SPECIAL_B_1;
    public static StaticAnimation ARDOR_BLOSSOM_SPECIAL_B_2;



    public static StaticAnimation ARDOR_BLOSSOM_SNEAK;
    public static void build(Model biped) {
        System.out.println("Building ARDOR_BLOSSOM Animations");

        /*
        ARDOR_BLOSSOM_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/ardor_blossom/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, equipEffect(0.6f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        */

        ARDOR_BLOSSOM_IDLE = new StaticAnimation(true, "biped/ardor_blossom/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());
        ARDOR_BLOSSOM_WALK = new MovementAnimation(true, "biped/ardor_blossom/walk", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());
        ARDOR_BLOSSOM_RUN = new MovementAnimation(true, "biped/ardor_blossom/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());
        ARDOR_BLOSSOM_KNEEL = new StaticAnimation(true, "biped/ardor_blossom/kneel", biped);
        ARDOR_BLOSSOM_SNEAK = new MovementAnimation(true, "biped/ardor_blossom/sneak", biped);


        ARDOR_BLOSSOM_GUARD = new StaticAnimation( true, "biped/ardor_blossom/guard", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());
        ARDOR_BLOSSOM_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/ardor_blossom/guard_hit", biped);
        ARDOR_BLOSSOM_PARRY_1 = new GuardAnimation(0.05f,0.3f, "biped/ardor_blossom/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        ARDOR_BLOSSOM_PARRY_2 = new GuardAnimation(0.05f,0.3f, "biped/ardor_blossom/parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        ARDOR_BLOSSOM_PARRY_3 = new GuardAnimation(0.05f,0.3f, "biped/ardor_blossom/parry_3", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        ARDOR_BLOSSOM_EVADE = new DodgeAnimation(0.05f,0.5f, "biped/ardor_blossom/parry_c_1",0.5f, 0.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomEvadeEvent());

        /*ARDOR_BLOSSOM_INNATE = new EgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, EgoWeaponsCapabilityPresets.FirefistSpew, "Tool_R", "biped/firefist/innate", biped)
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

        ARDOR_BLOSSOM_COUNTER = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.25F, 0.5F, 1.4F, null, "Tool_R", "biped/ardor_blossom/parry_c_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_counter")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.DEFENSIVE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);

        ARDOR_BLOSSOM_AUTO_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, null, "Tool_R", "biped/ardor_blossom/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_auto_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());


        ARDOR_BLOSSOM_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.33F, 0.55F, 0.75f, null, "Tool_R", "biped/ardor_blossom/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_auto_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        ARDOR_BLOSSOM_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.4f, 0.66F, 1.35f, null, "Tool_R", "biped/ardor_blossom/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.FINAL_COIN, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.FINAL_COIN, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_auto_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT_FINAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);

        ARDOR_BLOSSOM_DASH = new EgoAttackAnimation(0.08F, 0.2F, 0.2F, 0.45F, 1.1F, null, "Tool_R", "biped/ardor_blossom/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_dash")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.DASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT_FINAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F);

        ARDOR_BLOSSOM_JUMP_ATTACK = new BasicEgoAttackAnimation(0.02F, 0.2F, 0.3F, 0.55F, 0.95F, null, "Tool_R", "biped/ardor_blossom/jump_attack", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.JUMP_CRIT)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_jump")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT_FINAL)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        ARDOR_BLOSSOM_INNATE_1 = new EgoAttackAnimation(0.08F, 0.3F, 0.5F, 0.66F, 1F, null, "Tool_R", "biped/ardor_blossom/innate_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_innate_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        ARDOR_BLOSSOM_INNATE_2 = new EgoAttackAnimation(0.01F, 0.45F, 0.66F, 0.99F, 1.45F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/ardor_blossom/innate_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_innate_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_HIT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innate2Event());

        ARDOR_BLOSSOM_INNATE_3 = new EgoAttackAnimation(0.01F, 0.45F, 0.5F, 0.9F, 2F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/ardor_blossom/innate_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.FINAL_COIN, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_innate_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_HIT_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innate3Event());

        ARDOR_BLOSSOM_SPECIAL_1 = new EgoAttackAnimation(0.08F, 0.3F, 0.75F, 1F, 1.6F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/ardor_blossom/special_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_special_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special1Event());

        ARDOR_BLOSSOM_SPECIAL_2 = new EgoAttackAnimation(0.01F, 0.45F, 0.55F, 1F, 2F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/ardor_blossom/special_2b", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_special_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_HIT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special2Event());

        ARDOR_BLOSSOM_ARMOR_SKILL = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, ColliderPreset.FIST, "Torso", "biped/ardor_blossom/armor_ability", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_auto_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomArmorAbility());


        DASH_FORWARD = new DodgeAnimation(0f, "biped/ardor_blossom/dash_f", 0.5f, 1.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomDashEvent());

        DASH_BACKWARD = new DodgeAnimation(0f, "biped/ardor_blossom/dash_b", 0.5f, 1.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomDashEventB());

        WEAVE_1 = new DodgeAnimation(0f, "biped/ardor_blossom/weave_1", 0.5f, 1.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, weaveEventGeneric());

        WEAVE_2 = new DodgeAnimation(0f, "biped/ardor_blossom/weave_2", 0.5f, 1.1f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, weaveEventGeneric());


        DASH_R = new DodgeAnimation(0f, "biped/ardor_blossom/dash_r", 0.5f, 1.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomDashEventSide());
        DASH_L = new DodgeAnimation(0f, "biped/ardor_blossom/dash_l", 0.5f, 1.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomDashEventSide());

        ARDOR_BLOSSOM_SPECIAL_B_1 = new EgoAttackAnimation(0.0F, "biped/ardor_blossom/special_b_1", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.5F, 2.1f, 2.25F, 3f, 3.01f, "Tool_L", EgoWeaponsCapabilityPresets.LARGE_BOX_ARDOR)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "ardor_blossom_sp_b_1")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_HIT_1),
                new EgoAttackAnimation.EgoAttackPhase(2.8F, 3.3F, 3.5f, 4.2f, 4.66f, 4.67f, "Tool_R", EgoWeaponsCapabilityPresets.EVEN_LONGER_BLADE)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "ardor_blossom_sp_b_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_WING_SLASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_HIT_1),
                new EgoAttackAnimation.EgoAttackPhase(4.5F, 4.8F, 4.85f, 5.5f, 6.66f, 6.66f, "Tool_R", ColliderPreset.FIST)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "ardor_blossom_sp_b_3")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_SWING_3)

                )
                .addProperty(EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_sp_1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "ardor_blossom_special")
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(6))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomAbility1());

        ARDOR_BLOSSOM_SPECIAL_B_2 = new EgoAttackAnimation(0.0F, "biped/ardor_blossom/special_corr", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.5F, 2.1f, 2.25F, 3f, 3.01f, "Tool_L", EgoWeaponsCapabilityPresets.LARGE_BOX_ARDOR)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "ardor_blossom_sp_u_1")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_HIT_1),
                new EgoAttackAnimation.EgoAttackPhase(2.8F, 3.3F, 3.5f, 4.2f, 4.66f, 4.67f, "Tool_R", EgoWeaponsCapabilityPresets.EVEN_LONGER_BLADE)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "ardor_blossom_sp_u_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_WING_SLASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_D_HIT_1),
                new EgoAttackAnimation.EgoAttackPhase(4.5F, 4.8F, 250 / 60f, 5.5f, 7.33f, 7.3333335f, "Tool_R", ColliderPreset.FIST)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "ardor_blossom_sp_u_3")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_SWING_3)

        )
                .addProperty(EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "ardor_blossom_sp_u_1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "ardor_blossom_special")
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(14))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ardorBlossomAbilityCorrosion());

    }

    private static StaticAnimation.Event[] ardorBlossomDashEventSide() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);
            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 2.3f, 1.5f, 20, 0,0,0));


            ArdorBlossomSuit.setWingRotationState(entity, 0, 6, 5, 3, 0f, 1.5f);
            ArdorBlossomSuit.setWingActivationState(entity, true, 0);
            ArdorBlossomSuit.setWingActivationState(entity, false, 15);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
    private static StaticAnimation.Event[] ardorBlossomDashEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);
            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 2.3f, 1.5f, 20, 0,0,0));


            ArdorBlossomSuit.setWingRotationState(entity, 0, 6, 5, 9, 0f, 1.7f);
            ArdorBlossomSuit.setWingActivationState(entity, true, 0);
            ArdorBlossomSuit.setWingActivationState(entity, false, 18);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }



    private static StaticAnimation.Event[] ardorBlossomAbility1() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[8];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1);

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (!entity.level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_2, 1, 1);
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.INGOING_EMBER.get(), 30, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 4f, 0.3f, 10, 0,0,0));

            }

        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 1);

            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 40, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 2f, 1f, 20, 0,0,0));
            ArdorBlossomSuit.setWingActivationState(entity, true, 0);
            ArdorBlossomSuit.setWingActivationState(entity, false, 100);


            ArdorBlossomBatWeaponAbility.processWeaponAbility(entitypatch, entity, false);
            ArdorBlossomSuit.setWingRotationState(entity, 0, 6, 6, 9, 0f, -1f);

            ArdorBlossomSuit.setWingRotationState(entity, 6, 8, 5, 9, 1.5f, 0f);


        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.setDeltaMovement(0, 0.3f, 0);

            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);


        }, StaticAnimation.Event.Side.BOTH);
        // 0.05f
        events[4] = StaticAnimation.Event.create(2.0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.setDeltaMovement(0, 0.3f, 0);
            ArdorBlossomSuit.setWingRotationState(entity, 0, 5, 4, 9, 0.1f, -1f);

            ArdorBlossomSuit.setWingRotationState(entity, 5, 3, 8, 9, 1.5f, 0f);

        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(3.1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int height = calculateHeightFromFloor(entity);

            entity.setDeltaMovement(0, -0.1f * height, 0);
            ArdorBlossomSuit.setWingRotationState(entity, 3, 3, 8, 14, 0f, 1.5f);

        }, StaticAnimation.Event.Side.BOTH);

        events[6] = StaticAnimation.Event.create(4.0f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.setDeltaMovement(0, 0.1f, 0);

        }, StaticAnimation.Event.Side.BOTH);

        events[7] = StaticAnimation.Event.create(4.66f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            boolean powered = false;

            if (entitypatch.getOriginal() instanceof PlayerEntity) {
                powered = EmotionSystem.getEmotionLevel((PlayerEntity) entitypatch.getOriginal()) >= 5;
            }

            if (powered)
                DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.ardor_blossom_bat.rage.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
            else
                DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.ardor_blossom_bat.unpowered.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

            if (!entity.level.isClientSide())
                entitypatch.playSound(powered ? EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_RAGE_2 : EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_VOICE_2, 1f, 1, 1f);
        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }

    private static StaticAnimation.Event[] ardorBlossomAbilityCorrosion() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[9];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1);

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.INGOING_EMBER.get(), 30, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 4f, 0.3f, 10, 0,0,0));
            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_2, 1, 1);

        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 1);

            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 40, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 2f, 1f, 20, 0,0,0));
            ArdorBlossomSuit.setWingActivationState(entity, true, 0);
            ArdorBlossomSuit.setWingActivationState(entity, false, 100);

            ArdorBlossomBatWeaponAbility.processWeaponAbility(entitypatch, entity, true);
            ArdorBlossomSuit.setWingRotationState(entity, 0, 6, 6, 9, 0f, -1f);

            ArdorBlossomSuit.setWingRotationState(entity, 6, 8, 5, 9, 1.5f, 0f);


        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);


            entity.setDeltaMovement(0, 0.3f, 0);
        }, StaticAnimation.Event.Side.BOTH);
        // 0.05f
        events[4] = StaticAnimation.Event.create(2.0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.setDeltaMovement(0, 0.3f, 0);
            ArdorBlossomSuit.setWingRotationState(entity, 0, 5, 4, 9, 0.1f, -1f);

            ArdorBlossomSuit.setWingRotationState(entity, 5, 3, 8, 9, 1.5f, 0f);

        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(3.1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int height = calculateHeightFromFloor(entity);

            entity.setDeltaMovement(0, -0.1f * height, 0);
            ArdorBlossomSuit.setWingRotationState(entity, 3, 3, 8, 14, 0f, 1.5f);

        }, StaticAnimation.Event.Side.BOTH);

        events[6] = StaticAnimation.Event.create(4.0f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.setDeltaMovement(0, 0.1f, 0);

        }, StaticAnimation.Event.Side.BOTH);

        events[7] = StaticAnimation.Event.create(4.7f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();



            if (!entity.level.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.ARDOR_BLOSSOM_CHARGE.get(), 2, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0f, 0f, 0, 0,0,0));


                DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.ardor_blossom_bat.corrosion.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_CORROSION_2, 1f, 1, 1f);
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_2, 0.5f, 1, 1f);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[8] = StaticAnimation.Event.create(5.7833333f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            castOverclockExplosion(entitypatch, entity);



        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }

    private static int calculateHeightFromFloor(LivingEntity entity) {
        World level = entity.level;

        int offsetHeight = 0;

        while (offsetHeight < 10) {
            offsetHeight++;

            if (!level.isEmptyBlock(entity.blockPosition().below(offsetHeight)))
                return offsetHeight;
        }

        return offsetHeight;
    }

    private static StaticAnimation.Event[] ardorBlossomDashEventB() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);

            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);

            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 2.3f, 1.5f, 20, 0,0,0));


            ArdorBlossomSuit.setWingRotationState(entity, 0, 6, 5, 9, 1.5f, 0f);
            ArdorBlossomSuit.setWingActivationState(entity, true, 0);
            ArdorBlossomSuit.setWingActivationState(entity, false, 18);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] weaveEventGeneric() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);

            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);




            ArdorBlossomSuit.setWingRotationState(entity, 0, 4, 2, 5, 1.5f, 0f);
            ArdorBlossomSuit.setWingActivationState(entity, true, 0);
            ArdorBlossomSuit.setWingActivationState(entity, false, 10);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    private static StaticAnimation.Event[] ardorBlossomArmorAbility() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1);

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.INGOING_EMBER.get(), 30, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 4f, 0.3f, 10, 0,0,0));


            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);

        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);


            if (!entity.level.isClientSide())
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 40, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 2f, 1f, 20, 0,0,0));

            ArdorBlossomArmorAbility.processArmorAbility(entitypatch, entity);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }



    private static StaticAnimation.Event[] ardorBlossomEvadeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!entity.level.isClientSide())
                entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP, 1, 1);

            if (world.isClientSide()){
                entitypatch.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.33F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.playAnimationSynchronized(ARDOR_BLOSSOM_COUNTER, 0);
        }, StaticAnimation.Event.Side.SERVER);



        return events;
    }

    public static StaticAnimation.Event[] innate2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.33F, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_1, 1, 1);

            //EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_OPEN.get(), 0, "Tool_R");
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 1);
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] special1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.33F, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_1, 1, 1);
            //EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_OPEN.get(), 0, "Tool_R");
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(1.66f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.playAnimationSynchronized(ARDOR_BLOSSOM_SPECIAL_2, 0.01f);
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] special2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {

            if (entitypatch.getOriginal() instanceof PlayerEntity) {

                DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.ardor_blossom_bat.unpowered.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);



                if (!entitypatch.getOriginal().level.isClientSide())
                    entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_VOICE_2, 1f, 1, 1);

            }

        }, StaticAnimation.Event.Side.BOTH);



        events[1] = StaticAnimation.Event.create(1.9f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_1, 1, 1);
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 0);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] innate3Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_CHARGE_2, 1, 1);
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);

        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(1.5F, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_PARRY, 1, 1);
            //EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-1.5f), 1, EgoWeaponsParticles.SUNSHOWER_OPEN.get(), 0, "Tool_R");
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 0);
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] idleEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ext", 0);
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
}
