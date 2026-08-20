package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.potion.countEffects.BleedEffect;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.specialParticles.texturedAfterImage.TexturedAfterImagePresets;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.item.blackSilence.weapons.DurandalItem.*;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.*;

public class ArayashikiMovesetAnims {

    public static StaticAnimation ARAYASHIKI_IDLE;
    public static StaticAnimation ARAYASHIKI_RUN;
    public static StaticAnimation ARAYASHIKI_WALK;
    public static StaticAnimation ARAYASHIKI_S_SNEAK;
    public static StaticAnimation ARAYASHIKI_S_KNEEL;
    public static StaticAnimation ARAYASHIKI_AUTO_1_S;
    public static StaticAnimation ARAYASHIKI_AUTO_2_S;
    public static StaticAnimation ARAYASHIKI_AUTO_3_S;
    public static StaticAnimation ARAYASHIKI_INNATE;
    public static StaticAnimation ARAYASHIKI_INNATE_1_S;
    public static StaticAnimation ARAYASHIKI_INNATE_2_S;
    public static StaticAnimation ARAYASHIKI_SP_1_S;
    public static StaticAnimation ARAYASHIKI_SP_2_S;
    public static StaticAnimation ARAYASHIKI_SP_3_S;
    public static StaticAnimation ARAYASHIKI_SP_3_S_B;
    public static StaticAnimation ARAYASHIKI_DASH_S;

    public static StaticAnimation ARAYASHIKI_AUTO_1_U;
    public static StaticAnimation ARAYASHIKI_AUTO_2_U;
    public static StaticAnimation ARAYASHIKI_AUTO_3_U;
    public static StaticAnimation ARAYASHIKI_AUTO_4_U;

    public static StaticAnimation ARAYASHIKI_FURIOSO_SHEATH;
    public static StaticAnimation ARAYASHIKI_GUARD;
    public static StaticAnimation ARAYASHIKI_GUARD_HIT;
    public static StaticAnimation ARAYASHIKI_PARRY_1;
    public static StaticAnimation ARAYASHIKI_PARRY_2;
    public static StaticAnimation ARAYASHIKI_PARRY_3;
    public static StaticAnimation ARAYASHIKI_PARRY_4;
    public static StaticAnimation ARAYASHIKI_INNATE_1_U;
    public static StaticAnimation ARAYASHIKI_INNATE_2_U;
    public static StaticAnimation ARAYASHIKI_SP_ER_1;
    public static StaticAnimation ARAYASHIKI_SP_ER_2;
    public static StaticAnimation ARAYASHIKI_SP_ER_3;
    public static StaticAnimation ARAYASHIKI_SP_ER_4;
    public static StaticAnimation ARAYASHIKI_AUTO_EVADE;



    public static StaticAnimation ARAYASHIKI_IDLE_U;
    public static void build(Model biped) {


        ARAYASHIKI_IDLE_U = new StaticAnimation(true, "biped/arayashiki/unsheath/idle_u", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableForce1());

        ARAYASHIKI_IDLE = new StaticAnimation(true, "biped/arayashiki/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableIdleReset());
        ARAYASHIKI_RUN = new MovementAnimation(true, "biped/arayashiki/run", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableIdleReset());
        ARAYASHIKI_WALK = new MovementAnimation(true, "biped/arayashiki/walk", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableIdleReset());
        ARAYASHIKI_S_KNEEL = new StaticAnimation(true, "biped/arayashiki/kneel_s", biped);
        ARAYASHIKI_S_SNEAK = new MovementAnimation(true, "biped/arayashiki/sneak_s", biped);

        ARAYASHIKI_GUARD = new StaticAnimation( true, "biped/arayashiki/guard_s", biped);
        ARAYASHIKI_GUARD_HIT = new GuardAnimation(0.05f,0.5f, "biped/arayashiki/guard_hit_s", biped);
        ARAYASHIKI_PARRY_1 = new GuardAnimation(0.05f,0.35f, "biped/arayashiki/parry_s_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        ARAYASHIKI_PARRY_2 = new GuardAnimation(0.05f,0.35f, "biped/arayashiki/parry_s_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        ARAYASHIKI_PARRY_3 = new DodgeAnimation(0.05f,0.35f, "biped/arayashiki/parry_s_3", 0.5f, 1.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, evadeFX());
        ARAYASHIKI_PARRY_4 = (new EgoAttackAnimation(0.01F, 0.11f, 0.15f, 0.6F, 1.1f, null, "Knee_R", "biped/arayashiki/parry_s_4", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_counter")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.3f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, evadeFX());

        ARAYASHIKI_INNATE_1_S = (new EgoAttackAnimation(0.06F, 0.2F, 0.33F, 0.66F, 1.8F, null, "Chest", "biped/arayashiki/innate_1_s", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_innate_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiInnateReuse(0, 0.9f));

        ARAYASHIKI_INNATE_2_S = (new EgoAttackAnimation(0.01F, 0.2F, 0.25F, 0.5F, 1.5f, null, "Chest", "biped/arayashiki/innate_2_s", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_innate_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5f);

        ARAYASHIKI_AUTO_1_S = (new BasicEgoAttackAnimation(0.06F, 0.2F, 0.33F, 0.66F, 0.74F, null, "Tool_L", "biped/arayashiki/auto_1_s", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_1_s")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3f);

        ARAYASHIKI_AUTO_2_S = (new BasicEgoAttackAnimation(0.05F, 0.2F, 0.25F, 0.55F, 0.75f, null, "Tool_L", "biped/arayashiki/auto_2_s", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_2_s")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f);

        ARAYASHIKI_AUTO_3_S = (new BasicEgoAttackAnimation(0.05F, 0.1F, 0.4f, 0.66f, 1.6f, null, "Tool_L", "biped/arayashiki/auto_3_s", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_3_s")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f);

        ARAYASHIKI_INNATE = (new EgoAttackAnimation(0.16F, 0.2F, 0.66F, 1F, 1.2F, null, "Tool_R", "biped/arayashiki/innate", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "muga_innate")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.ARAYASHIKI_INNATE_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 2.8f)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableWeaponStateManager(0.66f, 2));

        ARAYASHIKI_SP_1_S = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.35f, 0.75F, 2F, null, "Tool_L", "biped/arayashiki/sp_1_s", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_1_s")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special1Event());

        ARAYASHIKI_SP_2_S = new EgoAttackAnimation(0.0F, "biped/arayashiki/sp_2_s", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.16f, 0.50f, 0.6f, 0.58f, "Tool_L", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_2_s_a")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_2),
                new EgoAttackAnimation.EgoAttackPhase(0.58F, 0.6F, 0.6f, 0.75f, 1.4f, 1.36f, "Tool_L", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_2_s_b")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_2)
        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_2_s")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special2Event());

        ARAYASHIKI_SP_3_S = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.55f, 0.95f, 3.1f, null, "Tool_L", "biped/arayashiki/sp_3_s", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_3_s")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_3)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.25F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSpecial3(0, 2.16f));

        ARAYASHIKI_DASH_S = (new EgoAttackAnimation(0.06F, 0.2F, 0.3F, 0.66F, 1.66F, null, "Chest", "biped/arayashiki/dash_s", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_innate_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.DASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, dashSheathed(0));

        ARAYASHIKI_SP_3_S_B = new BasicEgoAttackAnimation(0.05F, 0.2F, 2f, 3f, 4.3f, EgoWeaponsCapabilityPresets.DASH_COLLIDER, "Chest", "biped/arayashiki/sp_3_s_b", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_3_sb")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSpecialUnsheathe(0.6f, 2.34f, 4.2f, true));

        ARAYASHIKI_AUTO_1_U = (new BasicEgoAttackAnimation(0.06F, 0.2F, 0.3F, 0.6F, 0.9F, null, "Tool_R", "biped/arayashiki/unsheath/auto_1_u", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_1_u")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, autoU1());

        ARAYASHIKI_AUTO_2_U = (new BasicEgoAttackAnimation(0.05F, 0.2F, 0.25F, 0.55F, 0.75f, null, "Tool_R", "biped/arayashiki/unsheath/auto_2_u", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_2_u")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, autoU2());

        ARAYASHIKI_AUTO_3_U = (new BasicEgoAttackAnimation(0.05F, 0.2F, 0.33F, 0.75F, 2.3f, null, "Tool_R", "biped/arayashiki/unsheath/auto_3_u", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_3_u")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, autoU3());

        ARAYASHIKI_AUTO_4_U = (new BasicEgoAttackAnimation(0.05F, 0.1F, 0.45f, 0.7f, 2.8f, null, "Tool_R", "biped/arayashiki/unsheath/auto_4_u", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_auto_4_u")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SEVER_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS,autoU4());

        ARAYASHIKI_INNATE_1_U = (new EgoAttackAnimation(0.06F, 0.2F, 0.58f, 1.23f, 3F, EgoWeaponsCapabilityPresets.DASH_COLLIDER, "Chest", "biped/arayashiki/unsheath/innate_1_u", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_innate_1_u")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiInnateU(0, 0.8f, 2.22f));

        ARAYASHIKI_INNATE_2_U = new EgoAttackAnimation(0.05F, 0.2F, 1.83f, 2.66f, 4.66f, EgoWeaponsCapabilityPresets.DASH_COLLIDER, "Chest", "biped/arayashiki/unsheath/innate_2_u", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_innate_2_u")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSpecialUnsheathe(0.58f, 2f, 3.91f, false));

        ARAYASHIKI_SP_ER_1 = new EgoAttackAnimation(0.05F, 0.2F, 0.75f, 1.2f, 3.36f, null, "Tool_R", "biped/arayashiki/unsheath/sp_er_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_er_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSP_ER_1(0, 0.81f,1.5f, 1.8f));

        ARAYASHIKI_SP_ER_2 = new EgoAttackAnimation(0.0F, "biped/arayashiki/unsheath/sp_er_2", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.16f, 0.40f, 0.42f, 0.41f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_2_1")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, true)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(0.41F, 0.42F, 0.5f, 0.75f, 0.8f, 0.79f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_2_2")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(0.79f, 0.8f, 0.83f, 1.05f, 1.15f, 1.1f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_2_3")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(1.1f, 1.15f, 1.13f, 1.36f, 1.4f, 1.38f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_3_1")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, true)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(1.38f, 1.4f, 1.5f, 1.66f, 1.7f, 1.66f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_3_2")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(1.66f, 1.7f, 1.75f, 1.95f, 2.16f, 2.16f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_3_3")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT)
        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_er_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSP_ER_MID(2, 2));

        ARAYASHIKI_SP_ER_3 = new EgoAttackAnimation(0.0F, "biped/arayashiki/unsheath/sp_er_3", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.16f, 0.40f, 0.42f, 0.41f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_4_1")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, true)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(0.41F, 0.42F, 0.5f, 0.75f, 0.8f, 0.79f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_4_2")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(0.79f, 0.8f, 0.83f, 1.05f, 1.15f, 1.1f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_4_3")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(1.1f, 1.15f, 1.13f, 1.36f, 1.4f, 1.38f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_5_1")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, true)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(1.38f, 1.4f, 1.5f, 1.66f, 1.7f, 1.66f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_5_2")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT),
                new EgoAttackAnimation.EgoAttackPhase(1.66f, 1.7f, 1.75f, 1.95f, 2.16f, 2.16f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "arayashiki_sp_er_5_3")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_SLASH_HIT)
        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_er_4")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, distortionSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_WOOSH)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSP_ER_MID(2, 3));

        ARAYASHIKI_SP_ER_4 = new EgoAttackAnimation(0.05F, 0.2F, 0.6f, 1.6666666f, 4.0f, EgoWeaponsCapabilityPresets.DASH_COLLIDER, "Chest", "biped/arayashiki/unsheath/sp_er_4", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.PALE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "arayashiki_sp_er_6")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "muga")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MUGA)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.BYPASS_GUARD_AND_DODGE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_ERASE)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARAYASHIKI_UNSHEATH_HEAVY_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, arayashikiSP_ER_END(0.3f, 0.72f, 3.36f, false));

        ARAYASHIKI_AUTO_EVADE = new DodgeAnimation(0.05f,0.5f, "biped/arayashiki/auto_evade",0.5f, 0.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, autoEvadeEvent());

    }
    public static StaticAnimation.Event[] autoEvadeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0.05f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 0.5f, (float) 1);
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP,
                        SoundCategory.PLAYERS, 0.5f, (float) 1);
            }
            if (!entity.level.isClientSide())
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.STANDARD_FAST.ordinal(), 0);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] furiosoSheatheEngage() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
            BlackSilenceEvaluator.furiosoAddAttackStat(entitypatch.getOriginal(), 4);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.55F, (entitypatch) -> {
            if (!entitypatch.getOriginal().level.isClientSide())
                entitypatch.reserveAnimation(ArayashikiMovesetAnims.ARAYASHIKI_FURIOSO_SHEATH);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] autoU1() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.05F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;


            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_GRAB_SCABBARD,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.33f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] autoU2() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] autoU3() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.93f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entitypatch.currentlyAttackedEntity.isEmpty()) {
                int poise = EgoWeaponsEffects.POISE.get().getPotency(entity);

                if (poise >= 25) {
                    entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
                    EgoWeaponsEffects.POISE.get().decrement(entity, 0, 5);
                    if (!entity.level.isClientSide()) {
                        entitypatch.playAnimationSynchronized(ARAYASHIKI_AUTO_4_U, 0);
                    }
                }
            }
        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(1.8f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_SHEATHE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] autoU4() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
            if (!world.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.ARAYASHIKI_REND_SPACE_SLASH_CROSS.get().getRegistryName()));

            }
        }, StaticAnimation.Event.Side.BOTH);



        events[2] = StaticAnimation.Event.create(1.8f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_SHEATHE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] special1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(1.46f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entitypatch.currentlyAttackedEntity.isEmpty() && entity instanceof PlayerEntity) {

                if (!entity.level.isClientSide()) {
                    entitypatch.playAnimationSynchronized(ARAYASHIKI_SP_2_S, 0);
                }
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] evadeFX() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.05F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (entity.level.isClientSide()) {
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.STANDARD_FAST.ordinal(), 0);
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] special2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entitypatch.currentlyAttackedEntity.isEmpty() && entity instanceof PlayerEntity) {

                if (!entity.level.isClientSide()) {
                    int emotionLevel = 0;
                    boolean firemode = false;
                    if (entity instanceof PlayerEntity) {
                        emotionLevel = EmotionSystem.getEmotionLevel((PlayerEntity) entity);
                        firemode = EmotionSystem.getFireMode((PlayerEntity) entity);
                    }


                    if (emotionLevel >= 3 && firemode) {
                        entitypatch.playAnimationSynchronized(ARAYASHIKI_SP_3_S_B, 0);
                    } else {
                        entitypatch.playAnimationSynchronized(ARAYASHIKI_SP_3_S, 0);
                    }

                }
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] arayashikiInnateU(float startupTime, float unsheathTime, float sheathTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];
        events[0] = StaticAnimation.Event.create(startupTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.16f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_GRAB_SCABBARD,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(0.8333333f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.ARAYASHIKI_REND_SPACE_SHORT.get().getRegistryName()));

            }

        }, StaticAnimation.Event.Side.BOTH);


        events[3] = StaticAnimation.Event.create(unsheathTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(sheathTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_SHEATHE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] arayashikiInnateReuse(float startupTime, float reuseTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(startupTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.16f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_GRAB_SCABBARD,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(reuseTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entitypatch instanceof ServerPlayerPatch) {
                ServerPlayerPatch playerpatch = (ServerPlayerPatch)entitypatch;
                reuseEventTest(playerpatch);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static void reuseEventTest(ServerPlayerPatch patch) {

        PlayerEntity player = patch.getOriginal();
        ItemStack mainHandItem = player.getItemInHand(Hand.MAIN_HAND);
        int entitySpeed = EgoWeaponsEffects.speedMult(player);

        SkillContainer container = patch.getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK);
        if (!patch.currentlyAttackedEntity.isEmpty() && (container.isReady() || (container.getExecuter().getOriginal()).isCreative())) {
            container.getSkill().setStackSynchronize(patch, container.getStack()-1);
            patch.currentlyAttackedEntity.clear();
            if (!player.level.isClientSide()) {
                patch.playAnimationSynchronized(ArayashikiMovesetAnims.ARAYASHIKI_INNATE_2_S, 0);
            }
        }
    }

    public static StaticAnimation.Event[] dashSheathed(float startupTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(startupTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] arayashikiSpecial3(float startupTime, float reuseTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(startupTime, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.STANDARD.ordinal(), 0);

            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);


        events[1] = StaticAnimation.Event.create(reuseTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World level = entity.level;
            ItemStack weaponItem = entity.getItemInHand(Hand.MAIN_HAND);


            if (!weaponItem.isEmpty()) {

                if (!level.isClientSide()) {
                    level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.ARAYASHIKI_SPECIAL_SHEATH,
                            SoundCategory.PLAYERS, 1f, (float) 1);
                }


                if (weaponItem.getOrCreateTag().contains("specialCritEffect")) {
                    int entId = weaponItem.getOrCreateTag().getInt("specialCritEffect");
                    if (entId > 0) {
                        new DelayedEvent(5, (a) -> {
                            dealAssistAttackDamage(entity, level, entId);
                        });
                    }
                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] arayashikiSpecialUnsheathe(float unsheathe, float impact, float sheathe, boolean voiceLine) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;


            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.STANDARD.ordinal(), 0);
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 50, 0));

            if (voiceLine)
                DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.arayashiki.trigger_unsheath", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);

                if (voiceLine)
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_VOICE_UNSHEATH_TRIGGER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(unsheathe, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_UNSHEATH,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
            if (world.isClientSide())
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-12,-12), 1, EgoWeaponsParticles.ARAYASHIKI_UNSHEATH_SPARKLE.get(), new Vector3f(0, 0, 0), "Tool_R");

        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(impact, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.ARAYASHIKI_REND_SPACE_SLASH.get().getRegistryName()));

            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(sheathe, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_SHEATHE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    public static StaticAnimation.Event[] arayashikiSP_ER_1(float startupTime, float unsheathTime, float reuseTime, float sheathTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];
        events[0] = StaticAnimation.Event.create(startupTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.1f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_GRAB_SCABBARD,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(unsheathTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(reuseTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entitypatch.currentlyAttackedEntity.isEmpty() && entity instanceof PlayerEntity) {

                if (!entity.level.isClientSide()) {
                    entitypatch.playAnimationSynchronized(ARAYASHIKI_SP_ER_2, 0);
                }
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);
            }
        }, StaticAnimation.Event.Side.BOTH);


        events[4] = StaticAnimation.Event.create(sheathTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (entitypatch.currentlyAttackedEntity.isEmpty()) {

                entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", Math.max(0,entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("unsheathed") - 1));
                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.ARAYASHIKI_SHEATHE,
                            SoundCategory.PLAYERS, 1f, (float) 1);
                }
            }


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] arayashikiSP_ER_MID(float reuseTime, int index) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(reuseTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (!entity.level.isClientSide()) {
                if (index == 2) {
                    entitypatch.playAnimationSynchronized(ARAYASHIKI_SP_ER_3, 0);
                } else {
                    entitypatch.playAnimationSynchronized(ARAYASHIKI_SP_ER_4, 0);
                }
            }
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] arayashikiSP_ER_END(float unsheathe, float impact, float sheathe, boolean voiceLine) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;


            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.STANDARD.ordinal(), 0);
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 50, 0));

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_LEATHER,
                        SoundCategory.PLAYERS, 1f, (float) 1);

                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARDOR_BLOSSOM_FLAP,
                        SoundCategory.PLAYERS, 1f, (float) 1);

            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(unsheathe, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_UNSHEATH,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);
            if (world.isClientSide())
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-12,-12), 1, EgoWeaponsParticles.ARAYASHIKI_UNSHEATH_SPARKLE.get(), new Vector3f(0, 0, 0), "Tool_R");

        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(impact, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.ARAYASHIKI_REND_SPACE_SLASH.get().getRegistryName()));

            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 2);

        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(sheathe, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.ARAYASHIKI_SHEATHE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("unsheathed", 0);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static void dealAssistAttackDamage(LivingEntity source, World world, int entityId) {

        if (entityId > 0) {
            Entity foundEntity = world.getEntity(entityId);
            if (foundEntity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) foundEntity;
                target.playSound(EgoWeaponsSounds.ARAYASHIKI_SPECIAL_CRIT_HIT, 1, 1);

                if (source instanceof PlayerEntity)
                    target.hurt(DamageSource.playerAttack((PlayerEntity) source), 10);
                else
                    target.hurt(DamageSource.mobAttack(source), 10);

                if (target.hasEffect(EgoWeaponsEffects.BLEED.get()) && !world.isClientSide()) {
                    BleedEffect.apply(target);
                }
            }
        }
    }
}
