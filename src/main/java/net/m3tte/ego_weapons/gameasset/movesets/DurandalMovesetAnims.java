package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsModElements;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.AttackMoveType;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Models;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.furiosoSheatheEvents;
import static net.m3tte.ego_weapons.item.blackSilence.weapons.DurandalItem.*;
import static net.m3tte.ego_weapons.item.blackSilence.weapons.DurandalItem.durandalFuriosoEvent;

public class DurandalMovesetAnims {

    public static StaticAnimation DURANDAL_IDLE;
    public static StaticAnimation DURANDAL_RUN;
    public static StaticAnimation DURANDAL_WALK;
    public static StaticAnimation DURANDAL_DRAW;
    public static StaticAnimation DURANDAL_AUTO_1;
    public static StaticAnimation DURANDAL_AUTO_2;
    public static StaticAnimation DURANDAL_AUTO_3;
    public static StaticAnimation DURANDAL_AUTO_4;
    public static StaticAnimation DURANDAL_SPECIAL_1;
    public static StaticAnimation DURANDAL_SPECIAL_2;
    public static StaticAnimation DURANDAL_SPECIAL_3;
    public static StaticAnimation DURANDAL_DASH_ATTACK;
    public static StaticAnimation DURANDAL_JUMP_ATTACK;
    public static StaticAnimation DURANDAL_AUTO_REC;
    public static StaticAnimation DURANDAL_AUTO_SHEATH;
    public static StaticAnimation DURANDAL_FURIOSO_1;
    public static StaticAnimation DURANDAL_FURIOSO_2;
    public static StaticAnimation DURANDAL_FURIOSO_3;

    public static StaticAnimation DURANDAL_FURIOSO_SHEATH;
    public static StaticAnimation DURANDAL_GUARD;
    public static StaticAnimation DURANDAL_GUARD_HIT;
    public static StaticAnimation DURANDAL_GUARD_COUNTER;
    public static void build(Model biped) {

        DURANDAL_IDLE = new StaticAnimation(true, "biped/durandal/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableIdleReset());
        DURANDAL_RUN = new MovementAnimation(true, "biped/durandal/run", biped);
        DURANDAL_WALK = new MovementAnimation(true, "biped/durandal/walk", biped);
        DURANDAL_DRAW = (new SpecialAttackAnimation(0.16F, 0.1F, 1.0F, 1.4F, 2.0F, null, "Tool_R", "biped/durandal/draw", biped))
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.setter(0))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 2)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:woosh", ':')))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.durandal.strong", ':')))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DURANDAL_GUARD = new StaticAnimation( true, "biped/durandal/guard", biped);
        DURANDAL_GUARD_HIT = new GuardAnimation(0.1f, 0.4f,"biped/durandal/guard_hit", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);
        DURANDAL_GUARD_COUNTER = new GuardAnimation(0.2f,0.5f, "biped/durandal/guard_parry", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);

        DURANDAL_AUTO_REC = (new ActionAnimation(0.1f, 1.5f,   "biped/durandal/auto_rec", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, genericSheathEvent(1f, false))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);

        DURANDAL_AUTO_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/durandal/auto_sheath", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, genericSheathEvent(0.9f, false))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);


        DURANDAL_AUTO_1 = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.4F, 0.8F, 1.1F, null, "Tool_R", "biped/durandal/auto_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.CROSS_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableWeaponStateManager(0.41f, 2));

        DURANDAL_AUTO_2 = (new BasicEgoAttackAnimation(0.1F, 0.2F, 0.33F, 0.5F, 0.7F, null, "Tool_R", "biped/durandal/auto_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.CROSS_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableWeaponStateManager(0.1f, 2));

        DURANDAL_AUTO_3 = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.33F, 0.75F, 1.2F, null, "Tool_R", "biped/durandal/auto_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.CROSS_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, sheathableWeaponStateManager(0.1f, 2));

        DURANDAL_AUTO_4 = (new BasicEgoAttackAnimation(0.16F, 0.2F, 0.58F, 1F, 1.2F, null, "Tool_R", "biped/durandal/auto_4", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.CROSS_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalSheatheWeapon(1.1f, 0));

        DURANDAL_SPECIAL_3 = (new BasicEgoAttackAnimation(0.05F, 0.1F, 0.5F, 0.8F, 1.5F, null, "Tool_L", "biped/durandal/special_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, genericSheathEvent(1f, true));

        DURANDAL_SPECIAL_2 = (new BasicEgoAttackAnimation(0.05F, 0.1F, 0.33F, 0.66F, 1.2F, null, "Tool_R", "biped/durandal/special_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_DURANDAL_UP)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalCleaveConnection(0.9f, DURANDAL_SPECIAL_3));

        DURANDAL_SPECIAL_1 = (new BasicEgoAttackAnimation(0.2F, 0.1F, 0.5F, 0.75F, 1.2F, null, "Tool_R", "biped/durandal/special_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.BLACK_SILENCE_DURANDAL_DOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalCleaveConnection(0.9f, DURANDAL_SPECIAL_2));

        DURANDAL_DASH_ATTACK = (new EgoAttackAnimation(0.16F, 0.3F, 0.6F, 0.9F, 1.5F, null, "Tool_R", "biped/durandal/dash", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.CROSS_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalSheatheWeapon(1.1f, 3));

        DURANDAL_JUMP_ATTACK = (new EgoAttackAnimation(0.16F, 0.2F, 0.4F, 0.75F, 1.4F, null, "Tool_R", "biped/durandal/jump_attack", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.CROSS_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalSheatheWeaponNormal(1.2f));


        DURANDAL_FURIOSO_1 = (new BasicEgoAttackAnimation(0.2F, 0.1F, 0.5F, 0.75F, 0.9F, null, "Tool_R", "biped/durandal/furioso_1", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.durandal.down", ':')))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalFuriosoEvent(4));


        DURANDAL_FURIOSO_2 = (new BasicEgoAttackAnimation(0.05F, 0.05F, 0.04F, 0.33F, 0.45F, null, "Tool_R", "biped/durandal/furioso_2", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.durandal.up", ':')))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.35f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, durandalFuriosoEvent(4));

        DURANDAL_FURIOSO_3 = (new BasicEgoAttackAnimation(0.3F, 0.05F, 0.15F, 0.4F, 1F, null, "Tool_R", "biped/durandal/furioso_3", biped))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AnimationProperty.AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsModElements.sounds.get(ResourceLocation.of("ego_weapons:blacksilence.durandal.strong", ':')))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DURANDAL_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, furiosoSheatheEngage())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.3f);

        DURANDAL_FURIOSO_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/durandal/furioso_sheathe", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, furiosoSheatheEvents())
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);
    }

    private static StaticAnimation.Event[] furiosoSheatheEngage() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
            BlackSilenceEvaluator.furiosoAddAttackStat(entitypatch.getOriginal(), 4);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.55F, (entitypatch) -> {
            if (!entitypatch.getOriginal().level.isClientSide())
                entitypatch.reserveAnimation(DurandalMovesetAnims.DURANDAL_FURIOSO_SHEATH);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
}
