package net.m3tte.ego_weapons.gameasset.mobMovesets;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.*;
import static net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER;

public class NothingThereMovesetAnimations {




    public static StaticAnimation NT_IDLE;
    public static StaticAnimation NT_WALK;
    public static StaticAnimation NT_AUTO_1;
    public static StaticAnimation NT_AUTO_2;
    public static StaticAnimation NT_AUTO_B1;
    public static StaticAnimation NT_AUTO_B2;
    public static StaticAnimation NT_AUTO_STAB;
    public static StaticAnimation NT_DASH_C;
    public static StaticAnimation NT_DASH_C_F;
    public static StaticAnimation NT_DASH_B;
    public static StaticAnimation NT_DASH_B_F;
    public static StaticAnimation NT_GOODBYE;
    public static StaticAnimation NT_GOODBYE_ENH;
    public static StaticAnimation NT_STOMP;
    public static StaticAnimation NT_SCREECH;
    public static StaticAnimation NT_CHARGE_B_1;
    public static StaticAnimation NT_CHARGE_B_2;

    public static StaticAnimation NT_CHARGE_RUN;
    public static StaticAnimation NT_STAGGER;

    public static void build(Model nothing_there) {
        System.out.println("Building ENTITY Animations");

        NT_IDLE = new StaticAnimation(true, "nothing_there/idle", nothing_there);

        NT_WALK = new MovementAnimation(true, "nothing_there/walk", nothing_there)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.7f);

        NT_STAGGER = new LongHitAnimation(0.05f, "nothing_there/stagger", nothing_there);

        NT_AUTO_1 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.2F, 0.6F, 1.2F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Forearm", "nothing_there/attack_1", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_auto_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.6f);

        NT_AUTO_2 = (new BasicEgoAttackAnimation(0.02F, 0.06F, 0.08F, 0.4F, 2.5F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Forearm", "nothing_there/attack_2", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_auto_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 2)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.6f);

        NT_AUTO_B1 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.26F, 0.5F, 0.9F, EgoWeaponsCapabilityPresets.CUBE_1X, "Right_Bulb", "nothing_there/attack_b1", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_auto_b_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto_b")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        NT_AUTO_B2 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.26F, 0.5F, 2.5F, EgoWeaponsCapabilityPresets.CUBE_1X, "Right_Bulb", "nothing_there/attack_b2", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_auto_b_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto_b")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 8)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        NT_CHARGE_B_1 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 1.1666666f, 1.5F, 3F, EgoWeaponsCapabilityPresets.CUBE_1X, "Right_Bulb", "nothing_there/charged_attack", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_charge_b_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto_b")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEAVY_WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.FINISHER, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f)
                .addProperty(StaticAnimationProperty.EVENTS, chargedAttackFollowup(1.3f, 1.86f));

        NT_CHARGE_B_2 = (new BasicEgoAttackAnimation(0.01F, 0.03F, 0.55F, 1.1F, 2.53f, EgoWeaponsCapabilityPresets.CUBE_1X, "Right_Bulb", "nothing_there/charged_attack_followup", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_charge_b_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto_b")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        NT_CHARGE_RUN = (new EgoAttackAnimation(0.1F, "nothing_there/run_attack", nothing_there,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.7F, 1.5F, 1.505F, 1.51F, "Lower_Torso", EgoWeaponsCapabilityPresets.NOTHING_THERE_CHARGE)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.BYPASS_GUARD_AND_DODGE, true)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(6))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.ANVIL_LAND)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "nt_run_attack_1"),
                new EgoAttackAnimation.EgoAttackPhase(1.51F, 1.52F, 1.53F, 2F, 4F, 4.1666665f, "Right_Forearm", EgoWeaponsCapabilityPresets.NOTHING_THERE_CHARGE)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "nt_run_attack_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HORIZONTAL_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND,  EgoWeaponsSounds.KALI_HARD_HORIZONTAL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_run_attack")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_run_attack")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.FINISHER, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, runAttackEvent(1.15f, 1.3666667f, 1.6666666f)))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        NT_AUTO_STAB = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.26F, 0.5F, 2F, EgoWeaponsCapabilityPresets.NTBladePierce, "Left_Claw", "nothing_there/attack_stab", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_auto_stab")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto_stab")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 8)
                .addProperty(StaticAnimationProperty.EVENTS, chargeSound(EgoWeaponsSounds.NOTHING_THERE_HELLO))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.8f);

        NT_DASH_C = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.5F, 0.8F, 1.0F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Forearm", "nothing_there/attack_dash_claw", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_dash_claw")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        NT_DASH_C_F = (new EgoAttackAnimation(0.02F, 0.35F, 0.2F, 1F, 1.5F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Claw", "nothing_there/attack_dash_claw_f", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_dash_claw_f")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        NT_DASH_B = (new EgoAttackAnimation(0.1F, 0.11F, 0.5F, 1.2F, 1.5F, EgoWeaponsCapabilityPresets.CUBE_1X, "Right_Bulb", "nothing_there/dash_blunt", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_dash_blunt")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(StaticAnimationProperty.EVENTS, chargeSound(EgoWeaponsSounds.NOTHING_THERE_HI))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        NT_DASH_B_F = (new EgoAttackAnimation(0.1F, 0.11F, 0.75F, 1.5F, 1.6F, EgoWeaponsCapabilityPresets.CUBE_1X, "Right_Bulb", "nothing_there/dash_blunt_f", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_dash_blunt_f")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_auto")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        NT_GOODBYE = (new EgoAttackAnimation(0.02F, 0.03F, 0.6F, 1F, 2F, EgoWeaponsCapabilityPresets.NTBlade, "Right_Blade", "nothing_there/goodbye", nothing_there))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_goodbye")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_goodbye")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MEAT_EXPLOSION_KILL)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.FINISHER, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.EVENTS, chargeSound(EgoWeaponsSounds.NOTHING_THERE_GOODBYE))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        NT_GOODBYE_ENH = (new EgoAttackAnimation(0.1F, "nothing_there/goodbye_enh", nothing_there,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.83f, 1.35F, 1.505F, 1.51F, "Right_Blade", EgoWeaponsCapabilityPresets.NTBlade)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "nt_goodbye_enh_1")
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(6))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.ANVIL_LAND)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new EgoAttackAnimation.EgoAttackPhase(1.51F, 1.52F, 1.53F, 2F, 2F, 2.66f, "Right_Blade", EgoWeaponsCapabilityPresets.NTBlade)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "nt_goodbye_enh_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HORIZONTAL_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND,  EgoWeaponsSounds.KALI_HARD_HORIZONTAL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "nt_goodbye_enh")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "nt_goodbye")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MEAT_EXPLOSION_KILL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.FINISHER, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.EVENTS, goodbyeEnhCharge(1.51f))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f));

        NT_SCREECH = (new ActionAnimation(0.1f, 6f,   "nothing_there/screech", nothing_there))
                .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(StaticAnimationProperty.EVENTS, ntScreech())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        NT_STOMP = (new ActionAnimation(0.1f, 1.6f,   "nothing_there/stomp", nothing_there))
                .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(StaticAnimationProperty.EVENTS, ntStompEvent())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);
    }

    private static StaticAnimation.Event[] chargeSound(SoundEvent e) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.playSound(e, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] goodbyeEnhCharge(float p2) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            EgoWeaponsEffects.IMITATION.get().decrement(entitypatch.getOriginal(), 0, 6);
            if (!entitypatch.getOriginal().level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_GOODBYE, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(p2, (entitypatch) -> {
            if (!entitypatch.getOriginal().level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_HI, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] chargedAttackFollowup(float impact, float reuse) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(impact, (entitypatch) -> {
            if (entitypatch.getOriginal().level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_STOMP, 1, 1);
                entitypatch.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 0.5f, 0.5f);
            }
            ntChargedImpact(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal());

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(reuse, (entitypatch) -> {
            if (entitypatch.getOriginal().hasEffect(EgoWeaponsEffects.SHELL.get())) {
                if (!entitypatch.getOriginal().level.isClientSide()) {
                    entitypatch.playAnimationSynchronized(NothingThereMovesetAnimations.NT_CHARGE_B_2, 0);
                }
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] runAttackEvent(float step1, float step2, float step3) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];

        events[0] = StaticAnimation.Event.create(step1, (entitypatch) -> {
            if (entitypatch.getOriginal().level.isClientSide()) {
                entitypatch.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 0.5f, 0.5f);
            }
            ntChargedImpact(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(step2, (entitypatch) -> {
            if (entitypatch.getOriginal().level.isClientSide()) {
                entitypatch.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 0.5f, 0.5f);
            }
            ntChargedImpact(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(step3, (entitypatch) -> {
            if (entitypatch.getOriginal().level.isClientSide()) {
                entitypatch.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 0.5f, 0.5f);
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_STOMP, 1, 1);
            }
            ntChargedImpact(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(step3 + 0.4f, (entitypatch) -> {
            SharedFunctions.resetTargetsFor(entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }

    private static StaticAnimation.Event[] ntScreech() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[7];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.getOriginal().getPersistentData().remove("windupCharge");
            entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_WINDUP, 0.7f,0.3f, 0.3f);
            entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_FLESH, 1, 1);
            entitypatch.getOriginal().getPersistentData().putFloat("windupCharge", 0);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(3.2f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            float power = 0.6f;
            int mult = Math.min((int) (charge / 13),4);
            if (!entitypatch.getOriginal().level.isClientSide())
                EgoWeaponsEffects.SHELL.get().increment(entitypatch.getOriginal(), 0, mult);

            if (mult > 3) {
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_HIGH, 1, 1);
                power += 0.2f;
            } else {
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_LOW, 1, 1);
            }
            ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, power, true);
            spawnScreechParticles(30, entitypatch.getOriginal());

            entitypatch.getOriginal().getPersistentData().remove("windupCharge");
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(3.2f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");

            float power = 0.4f;

            if (charge > 6) {
                power += 0.1f;
            }
            ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, power, charge > 6);
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(3.5f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            float power = 0.4f;

            if (charge > 15) {
                power += 0.1f;
            }
            ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, power, charge > 20);
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(3.8f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            float power = 0.2f;

            if (charge > 15) {
                power += 0.1f;
            }
            ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, power, false);
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(4.1f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            if (charge > 15) {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 10, 0.3f, false);
            }
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[6] = StaticAnimation.Event.create(4.3f, (entitypatch) -> {
            SharedFunctions.resetTargetsFor(entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    private static void spawnScreechParticles(int count, LivingEntity ent) {
        Random r = ent.getRandom();
        Vector3d pos = ent.position();
        pos = pos.add(0, 3.8f, 0);
        for (int i = 0; i < 20; i++) {
            ent.level.addParticle(EpicFightParticles.BLOOD.get(), pos.x, pos.y, pos.z, r.nextFloat()*2-1, r.nextFloat()*2-1,r.nextFloat()*2-1);
        }
    }

    private static StaticAnimation.Event[] ntStompEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.75F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 10);
            entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_STOMP, 0.5f, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.8F, (entitypatch) -> {
            Random r = new Random();
            LivingEntity entity = entitypatch.getOriginal();
            World l = entity.level;
            Vector3d pos = getArmaturePosition(entitypatch, 0, new Vector3d(0, 0, 0), 20, "Left_Foot");
            BlockPos pos1 = new BlockPos(pos.x, pos.y, pos.z);
            ntStompEffect(entity.level, pos1, entity);
            SharedFunctions.resetTargetsFor(entitypatch.getOriginal());
            for (int x = 0; x < 40; x++) {
                l.addParticle(new BlockParticleData(ParticleTypes.BLOCK, l.getBlockState(pos1)), pos.x() + ranBetween(-1, 1), ((int)pos.y())+ ranBetween(1, 2), pos.z() + ranBetween(-1, 1), ranBetween(-1, 1),ranBetween(-1, 1),ranBetween(-1, 1));
                if (x % 2 == 0) {
                    l.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x() + ranBetween(-1, 1), ((int)pos.y()) + ranBetween(1, 2), pos.z() + ranBetween(-1, 1), ranBetween(-0.35f, 0.35f),ranBetween(-0.35f, 0.35f),ranBetween(-0.35f, 0.35f));
                }
            }
            //spawnBlockImpactParticle(entitypatch, 0, new Vector3d(0, -1, -1.8), 20, TCorpParticleRegistry.WHEELS_IMPACT.get(), 0.5f, new Vector3i(0,2.3f,0));
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    private static void ntChargedImpact(World world, BlockPos pos, LivingEntity source) {
        if (world instanceof ServerWorld) {
            List<LivingEntity> nearby = SharedFunctions.getNearbyEntities(source, 6, 6);

            for (LivingEntity e : nearby) {
                float dist = e.distanceTo(source);

                if (e instanceof PlayerEntity) {
                    float power = Math.max(0,1.2f - dist * 0.2f);
                    if (power > 0) {
                        UtilitySystems.sendShockwavePacket((PlayerEntity) e, power, power, power * 0.33f, power * 0.5f);
                    }
                }

                LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) e.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                if (targetPatch != null) {
                    targetPatch.knockBackEntity(source.position(), 0.4f);
                }
            }
        }
    }

    private static void ntStompEffect(World world, BlockPos pos, LivingEntity entity) {
        if (world instanceof ServerWorld) {
            List<Entity> _entfound = world
                    .getNearbyEntities(LivingEntity.class,
                            EntityPredicate.DEFAULT, null, new AxisAlignedBB(pos.getX() - (5f), pos.getY() - (5f), pos.getZ() - (5f), pos.getX() + (5f), pos.getY() + (5f), pos.getZ() + (5f)))
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                        }
                    }.compareDistOf(pos.getX(), pos.getY(), pos.getZ())).collect(Collectors.toList());

            if (!_entfound.isEmpty()) {
                for (Entity e : _entfound) {
                    if (e instanceof LivingEntity) {

                        if (!(e.equals(entity))) {
                            LivingEntity living = (LivingEntity) e;

                            LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                            if (targetPatch != null) {

                                StaggerSystem.reduceStagger(living, 6, entity, true);

                                if (targetPatch.getCurrentLivingMotion().equals(LivingMotions.BLOCK)) {
                                    targetPatch.playSound(EgoWeaponsSounds.STAGGER, 1, -0.05F, 0.1F);

                                    if (e instanceof PlayerEntity) {
                                        float power = 0.4f;
                                        UtilitySystems.sendShockwavePacket((PlayerEntity) e, power, power, power * 0.33f, power * 0.5f);
                                    }

                                    if (targetPatch instanceof PlayerPatch<?>) {
                                        PlayerPatch<?> playerPatch = (PlayerPatch<?>) targetPatch;
                                        playerPatch.setStamina(0);
                                    }

                                    if (targetPatch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
                                        targetPatch.playAnimationSynchronized(RANGA_GUARD_STAGGER, 0.0F);
                                    }
                                } else {

                                    if (e instanceof PlayerEntity) {
                                        float power = 0.6f;
                                        UtilitySystems.sendShockwavePacket((PlayerEntity) e, power, power, power * 0.33f, power * 0.5f);
                                    }

                                    if (entity instanceof PlayerEntity)
                                        living.hurt(DamageSource.playerAttack((PlayerEntity) entity), (float) 6);
                                    else
                                        living.hurt(DamageSource.mobAttack(entity), (float) 6);

                                    if (targetPatch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
                                        targetPatch.playAnimationSynchronized(RANGA_GUARD_STAGGER, 0f);
                                        targetPatch.knockBackEntity(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), 1f);
                                    }

                                }


                            }
                        }


                    }
                }
            }
        }
    }

    private static void ntScreechEvent(World world, BlockPos pos, LivingEntity entity, float distance, float power, boolean panics) {
        if (world instanceof ServerWorld) {

            List<LivingEntity> _entfound = SharedFunctions.getNearbyEntities(entity, distance, distance);

            if (!_entfound.isEmpty()) {
                for (LivingEntity e : _entfound) {
                    if (!(e.equals(entity))) {

                        LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) e.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                        if (panics)
                            EgoWeaponsEffects.TERROR.get().increment(e, 3, 1);

                        if (e instanceof PlayerEntity) {
                            UtilitySystems.sendShockwavePacket((PlayerEntity) e, power * 2.5f, power, power * 0.33f, power * 0.5f);
                        }

                        if (targetPatch != null) {
                            targetPatch.knockBackEntity(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), power * 4);
                        }
                    }
                }
            }
        }
    }
}
