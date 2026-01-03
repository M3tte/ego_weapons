package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.entities.MagicBulletProjectile;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.particle.MagicBulletShell;
import net.m3tte.ego_weapons.potion.countEffects.DarkFlameEffect;
import net.m3tte.ego_weapons.gameasset.abilities.assistAttacks.MagicBulletAssistAttack;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Random;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.gameasset.abilities.armorAbilities.MagicBulletPipe.magicBulletPipe;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.basicSwingEvent;

public class MagicBulletMovesetAnims {

    public static StaticAnimation MAGIC_BULLET_IDLE;
    public static StaticAnimation MAGIC_BULLET_WALK;
    public static StaticAnimation MAGIC_BULLET_RUN;
    public static StaticAnimation MAGIC_BULLET_KNEEL;
    public static StaticAnimation MAGIC_BULLET_SNEAK;
    public static StaticAnimation MAGIC_BULLET_JUMP;
    public static StaticAnimation MAGIC_BULLET_EVADE_FORWARD;
    public static StaticAnimation MAGIC_BULLET_EVADE_BACKWARD;
    public static StaticAnimation MAGIC_BULLET_EVADE_RIGHT;
    public static StaticAnimation MAGIC_BULLET_EVADE_LEFT;

    public static StaticAnimation MAGIC_BULLET_AUTO_1;
    public static StaticAnimation MAGIC_BULLET_AUTO_2;
    public static StaticAnimation MAGIC_BULLET_AUTO_3;
    public static StaticAnimation MAGIC_BULLET_DASH;
    public static StaticAnimation MAGIC_BULLET_JUMP_ATTACK;
    public static StaticAnimation MAGIC_BULLET_GUARD_HIT;
    public static StaticAnimation MAGIC_BULLET_GUARD;
    public static StaticAnimation MAGIC_BULLET_AIM_1;
    public static StaticAnimation MAGIC_BULLET_AIM_2;
    public static StaticAnimation MAGIC_BULLET_ASSIST_1;
    public static StaticAnimation MAGIC_BULLET_ASSIST_2;
    public static StaticAnimation MAGIC_BULLET_SPIN_1;
    public static StaticAnimation MAGIC_BULLET_SPIN_2;
    public static StaticAnimation MAGIC_BULLET_PIPE;
    public static StaticAnimation MAGIC_BULLET_EQUIP;

    public static void build(Model biped) {

        MAGIC_BULLET_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/magic_bullet/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.6f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        MAGIC_BULLET_IDLE = new StaticAnimation(0.3f,true, "biped/magic_bullet/idle", biped);
        MAGIC_BULLET_WALK = new MovementAnimation(true, "biped/magic_bullet/walk", biped);
        MAGIC_BULLET_RUN = new MovementAnimation(true, "biped/magic_bullet/run", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        MAGIC_BULLET_KNEEL = new StaticAnimation(true, "biped/magic_bullet/kneel", biped);
        MAGIC_BULLET_SNEAK = new MovementAnimation(true, "biped/magic_bullet/sneak", biped);
        MAGIC_BULLET_JUMP = new MovementAnimation(0.3f,false, "biped/magic_bullet/jump", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.0f);

        MAGIC_BULLET_EVADE_FORWARD = new DodgeAnimation(0f, "biped/magic_bullet/step_forward", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_EVADE_BACKWARD = new DodgeAnimation(0f, "biped/magic_bullet/step_backward", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_EVADE_LEFT = new DodgeAnimation(0f, "biped/magic_bullet/step_left", 0.5f, 0.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_EVADE_RIGHT = new DodgeAnimation(0f, "biped/magic_bullet/step_right", 0.5f, 0.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.41f, 0.6f, 0.78F, null, "Tool_R", "biped/magic_bullet/auto_1r", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_auto_1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.MAGIC_BULLET_HIT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F);

        MAGIC_BULLET_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.05F, 0.5F, 0.7F, 1F, null, "Tool_R", "biped/magic_bullet/auto_2r", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_auto_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.MAGIC_BULLET_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletSwingsoundHandler(1));

        MAGIC_BULLET_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.05F, 0.5f, 0.85F, 1.5F, null, "Tool_R", "biped/magic_bullet/auto_3r", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_auto_3")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.MAGIC_BULLET_HIT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(-4.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.78F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletSwingsoundHandler(2));

        MAGIC_BULLET_DASH = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.8F, 1.1F, 1.5F, null, "Tool_R", "biped/magic_bullet/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_dash")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.MAGIC_BULLET_HIT_1)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletSwingsoundHandler(1));

        MAGIC_BULLET_JUMP_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.3F, 0.6F, 1F, null, "Tool_R", "biped/magic_bullet/jump_attack", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_jump")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.MAGIC_BULLET_HIT_2)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65F);

        MAGIC_BULLET_GUARD = new StaticAnimation( true, "biped/magic_bullet/guard", biped);
        MAGIC_BULLET_GUARD_HIT = new GuardAnimation(0.05f,0.2f, "biped/magic_bullet/guard_hit", biped);

        MAGIC_BULLET_AIM_1 = new EgoAttackAnimation(0.08F, 0.05F, 1.3F, 1.37F, 2F,EgoWeaponsCapabilityPresets.RAILGUN_HITBOX_SP, "Tool_R", "biped/magic_bullet/aim_1", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_fire_1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MAGIC_BULLET_FIRE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "magic_bullet_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.MAGIC_BULLET_BREATHE)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.0f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletFire1Event())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);

        MAGIC_BULLET_AIM_2 = new EgoAttackAnimation(0.08F, 0.05F, 2F, 2.08F, 2.9F,EgoWeaponsCapabilityPresets.RAILGUN_HITBOX_SP, "Tool_R", "biped/magic_bullet/aim_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_fire_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MAGIC_BULLET_FIRE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "magic_bullet_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.0f))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.MAGIC_BULLET_BREATHE)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletFire2Event())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);

        MAGIC_BULLET_ASSIST_1 = new EgoAttackAnimation(0.08F, 0.05F, 1.3F, 1.37F, 2F, ColliderPreset.FIST, "Tool_R", "biped/magic_bullet/aim_1", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_fire_1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MAGIC_BULLET_FIRE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.MAGIC_BULLET_BREATHE)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, MagicBulletAssistAttack.magicBulletAssistFire1())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.6f);
        MAGIC_BULLET_ASSIST_2 = new EgoAttackAnimation(0.08F, 0.05F, 2F, 2.08F, 2.9F, ColliderPreset.FIST, "Tool_R", "biped/magic_bullet/aim_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_fire_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.MAGIC_BULLET_FIRE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.MAGIC_BULLET_BREATHE)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, MagicBulletAssistAttack.magicBulletAssistFire2())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.8f);

        MAGIC_BULLET_SPIN_1 = new BasicEgoAttackAnimation(0.01F, 0.05F, 1.23F, 1.45F, 1.5F, null, "Tool_R", "biped/magic_bullet/special_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_spin_1")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.MAGIC_BULLET_SPIN_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.WOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletSwingSpamHandler1());

        MAGIC_BULLET_SPIN_2 = (new EgoAttackAnimation(0.01F, "biped/magic_bullet/special_2", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.0F, 0.01F, 0.05F, 0.1F, 0.1F, "Tool_R", EgoWeaponsCapabilityPresets.RIFLE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.15f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.1F, 0.1F, 0.11F, 0.15F, 0.2F, 0.2F, "Tool_R", EgoWeaponsCapabilityPresets.RIFLE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.15f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.21F, 0.25F, 0.3F, 0.3F, "Tool_R", EgoWeaponsCapabilityPresets.RIFLE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.15f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.3F, 0.3F, 0.31F, 0.35F, 0.4F, 0.4F, "Tool_R", EgoWeaponsCapabilityPresets.RIFLE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.15f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.4F, 0.4F, 0.41F, 0.45F, 1F, 1F, "Tool_R", EgoWeaponsCapabilityPresets.RIFLE)
                        .addProperty(EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "magic_bullet_spin_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)

                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAGIC_BULLET_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletSwingSpamHandler2()));

        MAGIC_BULLET_PIPE = (new ActionAnimation(0.1f, 1.5f,   "biped/magic_bullet/pipe", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, magicBulletPipe());
    }

    private static StaticAnimation.Event[] magicBulletDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            entity.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] magicBulletSwingsoundHandler(int stage) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        (stage == 1) ? EgoWeaponsSounds.MAGIC_BULLET_SWING_1 : EgoWeaponsSounds.MAGIC_BULLET_SWING_2,
                        SoundCategory.PLAYERS, (float) 0.6, (float) 1);
            }

        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }

    private static StaticAnimation.Event[] magicBulletSwingSpamHandler1() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_SPIN_SWING,
                        SoundCategory.PLAYERS, (float) 0.6, (float) 1);
            }

        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }

    private static StaticAnimation.Event[] magicBulletSwingSpamHandler2() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_SPIN_SPAM,
                        SoundCategory.PLAYERS, (float) 1.5, (float) 1);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_SPIN_DETONATE,
                        SoundCategory.PLAYERS, (float) 1.5, (float) 1);
            }

            if (EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity) <= 3) {
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.8f, "Tool_R", false);
                EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] magicBulletFire1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];

        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            if (!entitypatch.getOriginal().level.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), 2.3f, 0.8f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_SHORT.get().getRegistryName()));

                if (ampl >= 3) {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), 3.1f, 0.65f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_SHORT.get().getRegistryName()));
                }

                if (entity instanceof PlayerEntity) {
                    if (SanitySystem.getSanity((PlayerEntity) entity) < ampl * 1.5f) {
                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), -3f, 0.8f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_SHORT.get().getRegistryName()));
                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), -3.8f, 0.6f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_SHORT.get().getRegistryName()));
                    }
                }

            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_DIALOGUE_FIRE,
                        SoundCategory.PLAYERS, 1f, (float) 1);

        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(1.37F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE.get(), 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_FIRE_1,
                        SoundCategory.PLAYERS, 1f, (float) 1);

            DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.magic_bullet.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.BLUE);


            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                } else {
                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }

                    spawnArmatureParticle(entitypatch, 0, new Vector3d(0,3,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
                    spawnArmatureParticle(entitypatch, 0, new Vector3d(0,3,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                    ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(entity, ampl-1);
                    entity.hurt(new DirectEgoDamageSource("", entity, ExtendedDamageSource.StunType.LONG, 1, 0.5f, GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.BLACK, "magic_bullet_self"), (7 + ampl) * 0.7f);
                }
            }



        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(1.45F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);
            if (ampl >= 7) {

                entity.removeEffect(EgoWeaponsEffects.MAGIC_BULLET.get());

                if (entitypatch instanceof PlayerPatch<?>) {
                    PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;

                    playerPatch.setStamina(Math.min(playerPatch.getStamina() + 1,playerPatch.getMaxStamina()));
                }
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    private static StaticAnimation.Event[] magicBulletFire2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[6];

        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            if (!entitypatch.getOriginal().level.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), 2.3f, 0.65f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_LONG.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), 3.1f, 0.9f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_LONG.get().getRegistryName()));

                if (ampl >= 6) {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), 3.9f, 0.65f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_LONG.get().getRegistryName()));
                }

                if (entity instanceof PlayerEntity) {
                    if (SanitySystem.getSanity((PlayerEntity) entity) < ampl * 1.5f) {
                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), -3f, 0.8f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_LONG.get().getRegistryName()));
                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.MagicBulletAimPacket(entity.getId(), -3.8f, 0.6f, EgoWeaponsParticles.MAGIC_BULLET_CIRCLE_LONG.get().getRegistryName()));
                    }
                }
            }




        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(1.28F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
                    EgoWeaponsSounds.MAGIC_BULLET_FIRE_SWING,
                    SoundCategory.PLAYERS, 2, 1f / (new Random().nextFloat() * 0.5f + 1));
        }, StaticAnimation.Event.Side.BOTH);
        events[3] = StaticAnimation.Event.create(1.6F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_DIALOGUE_FIRE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
            DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.magic_bullet.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.DARK_BLUE);


        }, StaticAnimation.Event.Side.BOTH);
        events[4] = StaticAnimation.Event.create(2.08F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        ampl >= 6 ? EgoWeaponsSounds.MAGIC_BULLET_FIRE_3 : EgoWeaponsSounds.MAGIC_BULLET_FIRE_2,
                        SoundCategory.PLAYERS, 1f, (float) 1);


            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE.get(), 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.7,-0.15), 1, EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);
            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                } else {

                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }

                    spawnArmatureParticle(entitypatch, 0, new Vector3d(0,3,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
                    spawnArmatureParticle(entitypatch, 0, new Vector3d(0,3,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

                    ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(entity, ampl-1);
                    entity.hurt(new DirectEgoDamageSource("", entity, ExtendedDamageSource.StunType.LONG, 1, 0.5f, GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.BLACK, "magic_bullet_self"), (7 + ampl) * 0.7f);
                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(2.2F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);
                if (ampl >= 7) {

                    entity.removeEffect(EgoWeaponsEffects.MAGIC_BULLET.get());

                    if (entitypatch instanceof PlayerPatch<?>) {
                        PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;

                        playerPatch.setStamina(Math.min(playerPatch.getStamina() + 1,playerPatch.getMaxStamina()));
                    }
                }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

/*


private static StaticAnimation.Event[] magicBulletFire1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];

        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            if (ampl >= 3)
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);



        events[2] = StaticAnimation.Event.create(1.37F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);


            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);


            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                    MagicBulletProjectile.shoot(entity, ampl-1, false);

                } else {
                    if (ampl > 1) {
                        MagicBulletProjectile.shoot(entity, ampl-2, true);
                    }

                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }
                    ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(entity, ampl-1);
                    entity.hurt(DamageSource.playerAttack((PlayerEntity) entity).setMagic(), (7 + ampl) * 0.7f);
                }
            } else {
                MagicBulletProjectile.shoot(entity, ampl-1, false);
            }



        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] magicBulletFire2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[4];

        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            if (ampl >= 6)
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);

        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(1.28F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
                    EgoWeaponsSounds.MAGIC_BULLET_FIRE_SWING,
                    SoundCategory.PLAYERS, 2, 1f / (new Random().nextFloat() * 0.5f + 1));
        }, StaticAnimation.Event.Side.BOTH);
        events[3] = StaticAnimation.Event.create(2.08F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);

            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                    MagicBulletProjectile.shoot(entity, ampl-1, false);
                } else {
                    if (ampl > 1) {
                        MagicBulletProjectile.shoot(entity, ampl-2, true);
                    }

                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }
                    ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(entity, ampl-1);
                    entity.hurt(DamageSource.playerAttack((PlayerEntity) entity).setMagic(), (7 + ampl) * 0.7f);
                }
            } else {
                MagicBulletProjectile.shoot(entity, ampl-1, false);
            }
            System.out.println("Amplifier is: "+ampl);
            if (ampl >= 7) {
                System.out.println("Resetting ampl");
                entity.removeEffect(EgoWeaponsEffects.MAGIC_BULLET.get());

                if (entitypatch instanceof PlayerPatch<?>) {
                    PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;

                    playerPatch.setStamina(Math.min(playerPatch.getStamina() + 1,playerPatch.getMaxStamina()));
                }
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


     */

}
