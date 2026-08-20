package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.potion.NoAmmo;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.skill.udjat.BurstFireSkill;
import net.m3tte.ego_weapons.specialParticles.texturedAfterImage.TexturedAfterImagePresets;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;


public class LCARifleMovesetAnims {


    public static StaticAnimation LCA_RIFLE_IDLE;
    public static StaticAnimation LCA_RIFLE_WALK;
    public static StaticAnimation LCA_RIFLE_RUN;
    public static StaticAnimation LCA_RIFLE_KNEEL;
    public static StaticAnimation LCA_RIFLE_SNEAK;
    public static StaticAnimation LCA_RIFLE_AUTO_G_1;
    public static StaticAnimation LCA_RIFLE_AUTO_G_2;
    public static StaticAnimation LCA_RIFLE_AUTO_G_3;
    public static StaticAnimation LCA_RIFLE_INNATE_1;
    public static StaticAnimation LCA_RIFLE_INNATE_2;
    public static StaticAnimation LCA_RIFLE_INNATE_3;
    public static StaticAnimation LCA_RIFLE_SPECIAL_O_1;
    public static StaticAnimation LCA_RIFLE_SPECIAL_O_2;
    public static StaticAnimation LCA_RIFLE_SPECIAL_O_3;
    public static StaticAnimation LCA_RIFLE_DASH;
    public static StaticAnimation LCA_RIFLE_SPECIAL_1;
    public static StaticAnimation LCA_RIFLE_SPECIAL_2;
    public static StaticAnimation LCA_RIFLE_AUTO_M_1;
    public static StaticAnimation LCA_RIFLE_AUTO_M_2;
    public static StaticAnimation LCA_UDJAT_BRACE;
    public static StaticAnimation LCA_UDJAT_ARMOR_ABILITY;
    public static StaticAnimation LCA_UDJAT_RELOAD_MH;
    public static StaticAnimation LCA_UDJAT_RELOAD_OH;
    public static StaticAnimation LCA_RIFLE_SPECIAL_B_1;
    public static StaticAnimation LCA_RIFLE_SPECIAL_B_2;


    public static void build(Model biped) {


        LCA_RIFLE_IDLE = new StaticAnimation(true, "biped/lca_rifle/idle", biped);
        LCA_RIFLE_WALK = new MovementAnimation(true, "biped/lca_rifle/walk", biped);
        LCA_RIFLE_RUN = new MovementAnimation(true, "biped/lca_rifle/run", biped);
        LCA_RIFLE_KNEEL = new StaticAnimation(true, "biped/lca_rifle/kneel", biped);
        LCA_RIFLE_SNEAK = new MovementAnimation(true, "biped/lca_rifle/sneak", biped);

        LCA_RIFLE_DASH = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.52f, 0.56F, 1.5f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/dash", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_dash")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_FIRE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleFirst(0.52f));

        LCA_RIFLE_AUTO_G_1 = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.52f, 0.56F, 0.88f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_auto_1_g")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleFirst(0.48f));

        LCA_RIFLE_AUTO_G_2 = (new BasicEgoAttackAnimation(0.01F, 0.1F, 0.36f, 0.4F, 0.75F, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_auto_2_g")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifle(0.33f, false));

        LCA_RIFLE_AUTO_G_3 = (new BasicEgoAttackAnimation(0.01F, 0.1F, 0.35f, 0.4F, 1.25f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_auto_3_g")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifle(0.32f, true));

        LCA_RIFLE_INNATE_1 = (new EgoAttackAnimation(0.16F, 0.2F, 0.41f, 0.5F, 1.18f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/innate_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_innate_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleReuse(true, 0.37f, 0.65f, "innate_1", false));

        LCA_RIFLE_INNATE_2 = (new EgoAttackAnimation(0.01F, 0.1F, 0.08f, 0.15F, 0.78f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/innate_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_innate_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleReuse(false, 0.05f, 0.6f, "innate_2", false));

        LCA_RIFLE_INNATE_3 = (new EgoAttackAnimation(0.05F, 0.1F, 0.08f, 0.15F, 1f, EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_R", "biped/lca_rifle/innate_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_innate_3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleReuse(false, 0.05f, 0.7f, "innate_3", false));

        LCA_RIFLE_SPECIAL_1 = new EgoAttackAnimation(0.0F, "biped/lca_rifle/special_1", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.79f, 0.81f, 0.95f, 0.9f, "Tool_R", EgoWeaponsCapabilityPresets.RAILGUN_HITBOX)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_1")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET),
                new EgoAttackAnimation.EgoAttackPhase(0.9F, 0.92F, 1.15f, 1.22f, 2f, 2f, "Tool_R", EgoWeaponsCapabilityPresets.RAILGUN_HITBOX)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)

        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "lca_rifle_special")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.65F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleSpecial(0.75f, 1.1f, 1.5f));

        LCA_RIFLE_SPECIAL_2 = (new EgoAttackAnimation(0.01F, 0.1F, 1.19f, 1.225F, 2.2166667f, null, "Tool_R", "biped/lca_rifle/special_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp_3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleSpecialFinal(1.18f));

        LCA_RIFLE_AUTO_M_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.25F, 0.45F, 0.65F, EgoWeaponsCapabilityPresets.DoubtBlade, "Tool_R", "biped/lca_rifle/autom_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_autom_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);

        LCA_RIFLE_AUTO_M_2 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.33F, 0.66F, 1.1F, EgoWeaponsCapabilityPresets.DoubtBlade, "Tool_R", "biped/lca_rifle/autom_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_autom_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE,EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F);

        LCA_UDJAT_BRACE = new DodgeAnimation(0.05f,0.4f, "biped/lca_rifle/brace",0.5f, 0.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        LCA_UDJAT_ARMOR_ABILITY = new ActionAnimation(0.05f, "biped/lca_rifle/armor_ability", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, activateLCAArmor());

        LCA_UDJAT_RELOAD_MH = (new ActionAnimation(0.1f, 1.5f,   "biped/lca_rifle/reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, reloadEvent(0.25f, 0.66f, 1.16f, true))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);

        LCA_UDJAT_RELOAD_OH = (new ActionAnimation(0.1f, 3.66f,   "biped/lca_rifle/reload_offh", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, reloadEventOffhand(1f, 1.5f, 3.3f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        LCA_RIFLE_SPECIAL_O_1 = (new EgoAttackAnimation(0.16F, 0.2F, 1.03f, 1.1F, 2.33f, Hand.OFF_HAND , EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_L", "biped/lca_rifle/special_o_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp_o_1")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TARGET_HAND, Hand.OFF_HAND)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleReuse(0.25f, true, 1f, 1.3f, "sp_o_1", true, 2.1f));

        LCA_RIFLE_SPECIAL_O_2 = (new EgoAttackAnimation(0.01F, 0.1F, 0.16f, 0.24F, 1.5f, Hand.OFF_HAND , EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_L", "biped/lca_rifle/special_o_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp_o_2")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TARGET_HAND, Hand.OFF_HAND)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.35f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleReuse(0, false, 0.13f, 0.7f, "sp_o_2", true, 1.4f));

        LCA_RIFLE_SPECIAL_O_3 = (new EgoAttackAnimation(0.05F, 0.1F, 0.3f, 0.35F, 1.5f, Hand.OFF_HAND , EgoWeaponsCapabilityPresets.RAILGUN_HITBOX, "Tool_L", "biped/lca_rifle/special_o_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp_o_3")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TARGET_HAND, Hand.OFF_HAND)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleReuse(0,false, 0.28f, 0.7f, "sp_o_3", true,1.4f));

        LCA_RIFLE_SPECIAL_B_1 = new EgoAttackAnimation(0.1F, "biped/lca_rifle/special_b_1", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 1f, 1.1f, 1.4f, 1.3f, Hand.OFF_HAND, "Tool_L", EgoWeaponsCapabilityPresets.RAILGUN_HITBOX)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_b_1")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET),
                new EgoAttackAnimation.EgoAttackPhase(1.3F, 1.31F, 1.33f, 1.4f, 2.83f, 2.83f, Hand.OFF_HAND, "Tool_L", EgoWeaponsCapabilityPresets.RAILGUN_HITBOX)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_b_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)

        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp_b")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TARGET_HAND, Hand.OFF_HAND)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "lca_rifle_special")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCASpecialB(0.95f, 1.3f, 1.78f));

        LCA_RIFLE_SPECIAL_B_2 = new EgoAttackAnimation(0.0F, "biped/lca_rifle/special_b_2", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.4f, 0.4f, 1.1f, 1f, Hand.MAIN_HAND, "Tool_R", ColliderPreset.FIST)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_b_3")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TARGET_HAND, Hand.MAIN_HAND)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CONSUMES_AMMO, false)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.UDJAT_LCA_KHOPESH_THROW)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET),
                new EgoAttackAnimation.EgoAttackPhase(1F, 1.05F, 1.50f, 1.85f, 1.9f, 1.8f, Hand.MAIN_HAND,"Tool_R", EgoWeaponsCapabilityPresets.GRAB)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_b_4")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TARGET_HAND, Hand.MAIN_HAND)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CONSUMES_AMMO, false)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.WHITE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_LCA_KHOPESH_THROW_RIPOUT),
                new EgoAttackAnimation.EgoAttackPhase(1.6F, 1.7F, 2.12f, 2.33f, 3.66f, 3.66f, Hand.OFF_HAND, "Tool_L", EgoWeaponsCapabilityPresets.SOLEMN_LAMENT_HITBOX_EXT)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "lca_rifle_sp_b_5")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TARGET_HAND, Hand.OFF_HAND)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CONSUMES_AMMO, true)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.CLICK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_RIFLE_HIT_BULLET)

        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "lca_rifle_sp_b")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "lca_rifle_special")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, fireLCARifleBFinal(2f, 3.55f));

    }

    public static StaticAnimation.Event[] reloadEventOffhand(float t1, float t2, float t3) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];
        events[0] = StaticAnimation.Event.create(0.25f, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            LivingEntity entity = entitypatch.getOriginal();
            ItemStack mainHandItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND);

            if (!mainHandItem.isEmpty()) {
                mainHandItem.getOrCreateTag().putInt("forceHandhold", mainHandItem.getOrCreateTag().getInt("forceHandhold") + 1);

                //new DelayedEvent(60, (e) -> {
                //    mainHandItem.getOrCreateTag().putInt("forceHandhold", Math.max(0,mainHandItem.getOrCreateTag().getInt("forceHandhold") - 1));
                //});
            }

            if (!world.isClientSide()) {
                world.playSound(null, entitypatch.getOriginal().blockPosition(),
                        SoundEvents.ARMOR_EQUIP_CHAIN,
                        SoundCategory.PLAYERS, (float) 0.2, (float) 1);
            }

        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(t1, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    SoundCategory.PLAYERS, (float) 0.8, (float) 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[2] = StaticAnimation.Event.create(t2, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            LivingEntity entity = entitypatch.getOriginal();
            AmmoSystem.reloadGun(entity.getItemInHand(Hand.OFF_HAND), ItemStack.EMPTY, entity);

            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    EgoWeaponsSounds.CLICK,
                    SoundCategory.PLAYERS, (float) 0.8, (float) 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[3] = StaticAnimation.Event.create(t3, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();

            ItemStack mainHandItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND);

            if (!mainHandItem.isEmpty()) {
                mainHandItem.getOrCreateTag().putInt("forceHandhold", 0);
            }
            if (!world.isClientSide()) {
                world.playSound(null, entitypatch.getOriginal().blockPosition(),
                        EgoWeaponsSounds.FULLSTOP_REP_RELOAD,
                        SoundCategory.PLAYERS, (float) 0.8, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] reloadEvent(float t1, float t2, float t3, boolean mainHand) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(t1, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    SoundCategory.PLAYERS, (float) 0.8, (float) 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[1] = StaticAnimation.Event.create(t2, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    EgoWeaponsSounds.CLICK,
                    SoundCategory.PLAYERS, (float) 0.8, (float) 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[2] = StaticAnimation.Event.create(t3, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entitypatch.getOriginal().level;

            AmmoSystem.reloadGun(entity.getItemInHand(mainHand ? Hand.MAIN_HAND : Hand.OFF_HAND), ItemStack.EMPTY, entity);


            if (!world.isClientSide()) {
                world.playSound(null, entitypatch.getOriginal().blockPosition(),
                        EgoWeaponsSounds.FULLSTOP_REP_RELOAD,
                        SoundCategory.PLAYERS, (float) 0.8, (float) 1);
            }
        }, StaticAnimation.Event.Side.SERVER);
        return events;
    }

    private static void applyBuffsToNearbyAllies(LivingEntity source, int amount) {
        List<LivingEntity> nearbyFriendlies = SharedFunctions.getNearbyEntities(source, 16, 4, TeamLockedPredicate.ONLY_ALLIES);
        nearbyFriendlies.add(source);




        if (nearbyFriendlies.isEmpty())
            return;

        for (LivingEntity ent : nearbyFriendlies) {

            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(ent, 6, amount);
            EgoWeaponsEffects.SPEED_UP.get().increment(ent, 3, amount);

        }

    }

    public static StaticAnimation.Event[] activateLCAArmor() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.75f, (entitypatch) -> {
            if (entitypatch.getOriginal() != null) {
                LivingEntity entity = entitypatch.getOriginal();

                int emotionLevel = 0;

                if (entity instanceof PlayerEntity) {
                    emotionLevel = EmotionSystem.getEmotionLevel((PlayerEntity) entity);
                }

                if (emotionLevel > 0) {
                    int excess = emotionLevel + EgoWeaponsEffects.UDJAT_VANGUARD.get().getPotency(entity) - 3;

                    EgoWeaponsEffects.UDJAT_VANGUARD.get().increment(entity, 3, emotionLevel);

                    if (excess > 0) {
                        applyBuffsToNearbyAllies(entity, excess);
                    }

                }

                if (!entity.level.isClientSide())
                    entitypatch.playSound(EgoWeaponsSounds.UDJAT_RIFLE_AIM, 1, 1, 1);
            }



        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }


    public static StaticAnimation.Event[] fireLCASpecialB(float time1, float time2, float followup) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];

        events[0] = StaticAnimation.Event.create(0.25f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            ItemStack offhandItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND);

            if (!offhandItem.isEmpty()) {
                offhandItem.getOrCreateTag().putInt("forceHandhold", offhandItem.getOrCreateTag().getInt("forceHandhold") + 1);

                new DelayedEvent(160, (e) -> {
                    offhandItem.getOrCreateTag().putInt("forceHandhold", Math.max(0,offhandItem.getOrCreateTag().getInt("forceHandhold") - 1));
                });
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(time1, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.OFF_HAND), entity, true);

            if (ammo != null) {


                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_L", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_L");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(time2, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.OFF_HAND), entity, true);

            if (ammo != null) {


                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_L", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_L");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(followup, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!entitypatch.currentlyAttackedEntity.isEmpty()) {
                if (!entity.level.isClientSide()) {
                    ItemStack weaponItem = entity.getItemInHand(Hand.OFF_HAND);


                    if (!world.isClientSide()) {
                        DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.lca_rifle.special.fire", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                        world.playSound(null, entity.blockPosition(),
                                EgoWeaponsSounds.UDJAT_DIALOGUE_S3_CHARGE,
                                SoundCategory.PLAYERS, (float) 1, (float) 1);
                    }
                    weaponItem.getOrCreateTag().putInt("forceHandhold", weaponItem.getOrCreateTag().getInt("forceHandhold") + 1);

                    new DelayedEvent(300, (e) -> {
                        weaponItem.getOrCreateTag().putInt("forceHandhold", Math.max(0,weaponItem.getOrCreateTag().getInt("forceHandhold") - 1));
                    });

                    entitypatch.playAnimationSynchronized(LCA_RIFLE_SPECIAL_B_2, 0);
                } else {
                    entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(2.5f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            ItemStack offhandItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND);

            if (!offhandItem.isEmpty()) {
                offhandItem.getOrCreateTag().putInt("forceHandhold", Math.max(0,offhandItem.getOrCreateTag().getInt("forceHandhold") - 1));
            }

            }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    // Events for the Combo Special Version

    private static void dealAssistAttackDamage(LivingEntity source, World world) {

        ItemStack weaponItem = source.getItemInHand(Hand.OFF_HAND);
        int entityId = weaponItem.getOrCreateTag().getInt("specialHitEntity");

        if (entityId > 0) {
            Entity foundEntity = world.getEntity(entityId);
            if (foundEntity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) foundEntity;
                target.playSound(EgoWeaponsSounds.UDJAT_LCA_KHOPESH_THROW_HIT, 1, 1);

                target.getPersistentData().putInt("lcaKhopeshL", target.tickCount + 30);
                if (source instanceof PlayerEntity)
                    target.hurt(DamageSource.playerAttack((PlayerEntity) source), 12);
                else
                    target.hurt(DamageSource.mobAttack(source), 12);
            }
        }
    }
    public static StaticAnimation.Event[] fireLCARifleBFinal(float time, float end) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[6];

        events[0] = StaticAnimation.Event.create(0.33f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            dealAssistAttackDamage(entity, world);
            ItemStack offhandItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND);

            if (!offhandItem.isEmpty()) {
                offhandItem.getOrCreateTag().putInt("forceHandhold", offhandItem.getOrCreateTag().getInt("forceHandhold") + 1);

                new DelayedEvent(120, (e) -> {
                    offhandItem.getOrCreateTag().putInt("forceHandhold", Math.max(0,offhandItem.getOrCreateTag().getInt("forceHandhold") - 1));
                });
            }
        }, StaticAnimation.Event.Side.BOTH);


        events[1] = StaticAnimation.Event.create(0.85f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.BLACK_SILENCE_EVADE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }

            ItemStack weaponItem = entity.getItemInHand(Hand.OFF_HAND);


            if (!weaponItem.isEmpty()) {


                if (weaponItem.getOrCreateTag().contains("specialHitEntity")) {
                    Entity targetEntity = world.getEntity(weaponItem.getOrCreateTag().getInt("specialHitEntity"));

                    if (targetEntity != null) {

                        double oldX = weaponItem.getOrCreateTag().getDouble("oldPosX");
                        double oldY = weaponItem.getOrCreateTag().getDouble("oldPosY");
                        double oldZ = weaponItem.getOrCreateTag().getDouble("oldPosZ");


                        Vector3d oldPos = new Vector3d(oldX, oldY, oldZ);
                        /*
                        Vector3d targetPos = targetEntity.position().add(oldPos.subtract(targetEntity.position()).normalize().scale(2));

                        entity.teleportTo(targetPos.x(), targetPos.y(), targetPos.z());*/


                        float factor = (float) ((oldPos.distanceTo(targetEntity.position())) * 0.12f);
                        //entitypatch.knockBackEntity(targetEntity.position(), - factor);

                        Vector3d targetPos = targetEntity.position().subtract(oldPos).normalize().scale(factor);

                        entity.setDeltaMovement(targetPos);
                    }
                }
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(0.75f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entity.setDeltaMovement(0,0,0);

        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(time - 0.2f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()) {
                DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.lca_rifle.special.fire2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                world.playSound(null, entity.blockPosition(),
                        EgoWeaponsSounds.UDJAT_DIALOGUE_S3_FIRE,
                        SoundCategory.PLAYERS, (float) 1, (float) 1);
            }

        }, StaticAnimation.Event.Side.BOTH);


        events[4] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            AmmoType ammo = null;
            if (AmmoSystem.getAmmoCount(entity.getItemInHand(Hand.OFF_HAND)) >= 2) {
                ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.OFF_HAND), entity, true);
                ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.OFF_HAND), entity, true);

            }


            if (ammo != null) {

                ItemStack weaponItem = entity.getItemInHand(Hand.OFF_HAND);

                if (!weaponItem.isEmpty()) {
                    if (weaponItem.getOrCreateTag().contains("specialHitEntity")) {
                        Entity targetEntity = world.getEntity(weaponItem.getOrCreateTag().getInt("specialHitEntity"));

                        if (targetEntity != null) {
                            entitypatch.knockBackEntity(targetEntity.position(), 0.7f);
                        }
                    }
                }

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_L", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_L");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_L");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                System.out.println("NO AMMO EFFECT ADDED");
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 20, 0));
            }



        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(end, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            ItemStack ohItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND);

            if (!ohItem.isEmpty()) {
                ohItem.getOrCreateTag().putInt("forceHandhold", 0);
            }

        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
    public static StaticAnimation.Event[] fireLCARifleSpecial(float time1, float time2, float followup) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(time1, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            if (ammo != null) {


                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(time2, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            if (ammo != null) {


                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(followup, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!entitypatch.currentlyAttackedEntity.isEmpty()) {
                if (!entity.level.isClientSide()) {

                    DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.lca_rifle.special.fire", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                    world.playSound(null, entity.blockPosition(),
                            EgoWeaponsSounds.UDJAT_DIALOGUE_S3_CHARGE,
                            SoundCategory.PLAYERS, (float) 2, (float) 1);

                    entitypatch.playAnimationSynchronized(LCA_RIFLE_SPECIAL_2, 0);
                } else {
                    entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] fireLCARifle(float time, boolean finalB) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;


            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            if (ammo != null) {


                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));
            }



        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }


    public static StaticAnimation.Event[] fireLCARifleSpecialFinal(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];
        events[0] = StaticAnimation.Event.create(0.16f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.BLACK_SILENCE_EVADE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            }

            ItemStack weaponItem = entity.getItemInHand(Hand.MAIN_HAND);




            if (!weaponItem.isEmpty()) {
                if (weaponItem.getOrCreateTag().contains("specialHitEntity")) {
                    Entity targetEntity = world.getEntity(weaponItem.getOrCreateTag().getInt("specialHitEntity"));

                    if (targetEntity != null) {

                        double oldX = weaponItem.getOrCreateTag().getDouble("oldPosX");
                        double oldY = weaponItem.getOrCreateTag().getDouble("oldPosY");
                        double oldZ = weaponItem.getOrCreateTag().getDouble("oldPosZ");

                        Vector3d oldPos = new Vector3d(oldX, oldY, oldZ);
                        /*
                        Vector3d targetPos = targetEntity.position().add(oldPos.subtract(targetEntity.position()).normalize().scale(2));

                        entity.teleportTo(targetPos.x(), targetPos.y(), targetPos.z());*/

                        if (oldPos.distanceTo(targetEntity.position()) < 15 && targetEntity.isAlive()) {
                            float factor = (float) ((oldPos.distanceTo(targetEntity.position())) * 0.10f);
                            //entitypatch.knockBackEntity(targetEntity.position(), - factor);

                            Vector3d targetPos = targetEntity.position().subtract(oldPos).normalize().scale(factor);

                            entity.setDeltaMovement(targetPos);
                        }

                    }
                }
            }
            }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.75f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entity.setDeltaMovement(0,0,0);

        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(time - 0.2f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.lca_rifle.special.fire2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
            world.playSound(null, entity.blockPosition(),
                    EgoWeaponsSounds.UDJAT_DIALOGUE_S3_FIRE,
                    SoundCategory.PLAYERS, (float) 2, (float) 1);
            entity.setDeltaMovement(0,0,0);

        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;



            AmmoType ammo = null;
            if (AmmoSystem.getAmmoCount(entity.getItemInHand(Hand.MAIN_HAND)) >= 2) {
                ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);
                ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            }

            if (ammo != null) {

                ItemStack weaponItem = entity.getItemInHand(Hand.MAIN_HAND);

                if (!weaponItem.isEmpty()) {
                    if (weaponItem.getOrCreateTag().contains("specialHitEntity")) {
                        Entity targetEntity = world.getEntity(weaponItem.getOrCreateTag().getInt("specialHitEntity"));

                        if (targetEntity != null) {
                            entitypatch.knockBackEntity(targetEntity.position(), 0.7f);
                        }
                    }
                }

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));


            }



        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
    public static StaticAnimation.Event[] fireLCARifleFirst(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.UDJAT_RIFLE_AIM,
                        SoundCategory.NEUTRAL, 1f, (float) 1);
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;



            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            if (ammo != null) {

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));

            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] fireLCARifleReuse(boolean startupSFX, float time, float reuseTime, String animIdentifier, boolean offhandVer) {
        return fireLCARifleReuse(0, startupSFX, time, reuseTime, animIdentifier, offhandVer, 0);
    }
    public static StaticAnimation.Event[] fireLCARifleReuse(float startupTime, boolean startupSFX, float time, float reuseTime, String animIdentifier, boolean offhandVer, float endTime) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];
        events[0] = StaticAnimation.Event.create(startupTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide() && startupSFX) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.UDJAT_RIFLE_AIM,
                        SoundCategory.NEUTRAL, 1f, (float) 1);
            }

            if (offhandVer) {
                ItemStack selectedItem = entity.getItemInHand(Hand.OFF_HAND);

                if (!selectedItem.isEmpty()) {
                    selectedItem.getOrCreateTag().putInt("forceHandhold", selectedItem.getOrCreateTag().getInt("forceHandhold") + 1);

                    //new DelayedEvent((int) ((endTime - startupTime) * 15), (e) -> {
                    //    selectedItem.getOrCreateTag().putInt("forceHandhold", Math.max(0,selectedItem.getOrCreateTag().getInt("forceHandhold") - 1));
                    //});
                }
            }


        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;


            AmmoType ammo = AmmoSystem.getAndRemovelastammo(offhandVer ? entity.getItemInHand(Hand.OFF_HAND) : entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            String handSel = offhandVer ? "Tool_L" : "Tool_R";
            if (ammo != null) {

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, handSel, false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), handSel);

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.UDJAT_RIFLE_FIRE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }

            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(reuseTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entitypatch instanceof ServerPlayerPatch) {
                ServerPlayerPatch playerpatch = (ServerPlayerPatch)entitypatch;
                if (offhandVer)
                    BurstFireSkill.reuseOffhandTest(playerpatch, animIdentifier);
                else
                    BurstFireSkill.reuseEventTest(playerpatch, animIdentifier);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(endTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (offhandVer) {
                ItemStack selectedItem = entity.getItemInHand(Hand.OFF_HAND);

                if (!selectedItem.isEmpty()) {
                    selectedItem.getOrCreateTag().putInt("forceHandhold", 0);

                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }




}
