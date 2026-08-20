package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.specialParticles.texturedAfterImage.TexturedAfterImagePresets;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.gameasset.abilities.armorAbilities.UdjatArmorAbility.activateUdjatArmor;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.basicSwingEvent;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.vertSwingEvent;

public class UdjatKhopeshMovesetAnims {
    public static StaticAnimation KHOPESH_IDLE;
    public static StaticAnimation KHOPESH_WALK;
    public static StaticAnimation KHOPESH_RUN;
    public static StaticAnimation KHOPESH_KNEEL;
    public static StaticAnimation KHOPESH_SNEAK;
    public static StaticAnimation KHOPESH_GUARD;
    public static StaticAnimation KHOPESH_GUARD_HIT;
    public static StaticAnimation KHOPESH_PARRY_1;
    public static StaticAnimation KHOPESH_PARRY_2;
    public static StaticAnimation KHOPESH_PARRY_3;
    public static StaticAnimation KHOPESH_AUTO_1;
    public static StaticAnimation KHOPESH_AUTO_2;
    public static StaticAnimation KHOPESH_AUTO_3;
    public static StaticAnimation KHOPESH_AUTO_3F;

    public static StaticAnimation KHOPESH_INNATE;

    public static StaticAnimation KHOPESH_DASH;
    public static StaticAnimation KHOPESH_SPECIAL_1;
    public static StaticAnimation KHOPESH_SPECIAL_2;
    public static StaticAnimation KHOPESH_SPECIAL_3;
    public static StaticAnimation KHOPESH_ARMOR_ABILITY;
    public static StaticAnimation KHOPESH_ARMOR_MIRAGE;



    public static void build(Model biped) {
        System.out.println("Building UDJAT_KHOPESH Animations");

        /*
        KHOPESH_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/ardor_blossom/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, equipEffect(0.6f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        */

        KHOPESH_IDLE = new StaticAnimation(true, "biped/udjat/idle", biped);
        KHOPESH_WALK = new MovementAnimation(true, "biped/udjat/walk", biped);
        KHOPESH_RUN = new MovementAnimation(true, "biped/udjat/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);
        KHOPESH_KNEEL = new StaticAnimation(true, "biped/udjat/kneel", biped);
        KHOPESH_SNEAK = new MovementAnimation(true, "biped/udjat/sneak", biped);

        KHOPESH_GUARD = new StaticAnimation( true, "biped/udjat/guard", biped);
        KHOPESH_GUARD_HIT = new GuardAnimation(0.05f,0.5f, "biped/udjat/guard_hit", biped);
        KHOPESH_PARRY_1 = new GuardAnimation(0.05f,0.35f, "biped/udjat/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        KHOPESH_PARRY_2 = new GuardAnimation(0.05f,0.35f, "biped/udjat/parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        KHOPESH_PARRY_3 = new GuardAnimation(0.05f,0.35f, "biped/udjat/parry_3", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        KHOPESH_ARMOR_ABILITY = new ActionAnimation(0.05f, "biped/udjat/armor_ability", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, activateUdjatArmor());

        KHOPESH_ARMOR_MIRAGE = new DodgeAnimation(0.05f,  "biped/udjat/mirage", 1, 0.5f,biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, activateUdjatMirage());

        KHOPESH_AUTO_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.33f, 0.45F, 0.55F, null, "Tool_R", "biped/udjat/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_auto_1")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
                //.addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());

        KHOPESH_INNATE = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.8333333f, 1F, 1.6F, null, "Tool_R", "biped/udjat/innate", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.UDJAT_KHOPESH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.INNATE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_innate")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateEvents());


        KHOPESH_AUTO_2 = new BasicEgoAttackAnimation(0.05F, 0.08F, 0.15f, 0.25F, 0.5F, null, "Tool_R", "biped/udjat/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_auto_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
                //.addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());

        KHOPESH_AUTO_3 = new BasicEgoAttackAnimation(0.05F, 0.07F, 0.25f, 0.45F, 0.75F, null, "Tool_R", "biped/udjat/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_auto_3")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, auto3Event());

        KHOPESH_AUTO_3F = new BasicEgoAttackAnimation(0.05F, 0.07F, 0.2f, 0.35F, 1F, EgoWeaponsCapabilityPresets.SUNSHOWER_COL, "Chest", "biped/udjat/auto_3f", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_auto_3f")
                .addProperty(EgoWeaponsAttackProperty.FINAL_COIN, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.AUTO)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        //.addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());

        KHOPESH_DASH = new EgoAttackAnimation(0.08F, 0.2F, 0.33F, 0.5F, 1F, null, "Tool_R", "biped/udjat/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_dash")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.DASH)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        KHOPESH_SPECIAL_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.35f, 0.5F, 1.1F, null, "Tool_R", "biped/udjat/special_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_special_1")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special1Event());

        KHOPESH_SPECIAL_2 = new EgoAttackAnimation(0.0F, "biped/udjat/special_2", biped,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.1F, 0.18f, 0.40f, 0.42f, 0.43f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "khopesh_special_2_a")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO),
                new EgoAttackAnimation.EgoAttackPhase(0.42F, 0.45F, 0.46f, 0.66f, 0.7f, 0.75f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "khopesh_special_2_b")
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_AUTO),
                new EgoAttackAnimation.EgoAttackPhase(0.7F, 0.71F, 0.75f, 0.9f, 1.5f, 1.5f, "Tool_R", null)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "khopesh_special_2_c")
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_INNATE_HIT)

        )
                .addProperty(EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_special_2")
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE, AttackLogicPredicate.PIERCE_GUARD_DODGE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "khopesh_special")
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.MELEE)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special2Event());

        KHOPESH_SPECIAL_3 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.55f, 0.75F, 1.4F, null, "Tool_R", "biped/udjat/special_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.WHITE)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_CYCLE_TYPE, AttackCycleType.SPECIAL)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, vertSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "khopesh_special_3")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.UDJAT_KHOPESH_INNATE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                //.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.KHOPESH_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.UDJAT_KH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, special3Event());
        //.addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, idleEvent());

    }

    private static StaticAnimation.Event[] auto3Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();

            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();

            if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("hitEntity") >= 1 && entity instanceof PlayerEntity) {

                double light = EntityTick.getLight((PlayerEntity) entity);

                if (light >= 1) {

                    if (!entity.level.isClientSide()) {

                        entitypatch.playAnimationSynchronized(KHOPESH_AUTO_3F, 0);
                        EntityTick.consumeLight((PlayerEntity) entity, 1);
                    } else {
                        entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

                    }
                }
            }
        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }

    public static StaticAnimation.Event[] special1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("hitEntity") >= 1 && entity instanceof PlayerEntity) {


                if (!entity.level.isClientSide()) {

                    entitypatch.playAnimationSynchronized(KHOPESH_SPECIAL_2, 0);
                } else {
                    entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

                }
            }

            if (entity.hasEffect(EgoWeaponsEffects.PROTECTION.get())) {
                if (entity.getEffect(EgoWeaponsEffects.PROTECTION.get()).getDuration() < 80) {
                    entity.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), 80, entity.getEffect(EgoWeaponsEffects.PROTECTION.get()).getAmplifier()));
                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] armorAbility() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] special2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.1F, (entitypatch) -> {
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(1.083f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (entity instanceof PlayerEntity) {

                int protection = EgoWeaponsEffects.PROTECTION.get().getPotency(entity);


                if (protection >= 4) {
                    DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.udjat_armor.special_reuse", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

                    if (!entity.level.isClientSide()) {

                        entitypatch.playAnimationSynchronized(KHOPESH_SPECIAL_3, 0);

                    } else {
                        entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);
                        entity.playSound(EgoWeaponsSounds.UDJAT_SPECIAL_REUSE, 1, 1f);

                    }
                }
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] activateUdjatMirage() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(0.05f, (entitypatch) -> {
            if (entitypatch.getOriginal() != null) {
                LivingEntity entity = entitypatch.getOriginal();



                if (!entity.level.isClientSide()) {
                    entitypatch.playSound(EgoWeaponsSounds.UDJAT_MIRAGE, 1, 1, 1);

                    entitypatch.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1, 1);
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.UDJAT_SAND.get(), 10, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0f, 0.2f, 0.5f, 0,0.4f,0));

                } else {
                    entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);

                }

            }
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.25f, (entitypatch) -> {
            if (entitypatch.getOriginal() != null) {
                LivingEntity entity = entitypatch.getOriginal();



                if (!entity.level.isClientSide()) {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.UDJAT_SAND.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.2f, 0.25f, 0.5f, 0,0,0));

                }

            }
        }, StaticAnimation.Event.Side.BOTH);


        events[2] = StaticAnimation.Event.create(0.75f, (entitypatch) -> {
            if (entitypatch.getOriginal() != null) {
                LivingEntity entity = entitypatch.getOriginal();



                if (!entity.level.isClientSide()) {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.UDJAT_SAND.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.2f, 0.25f, 0.5f, 0,0,0));
                }

            }
        }, StaticAnimation.Event.Side.BOTH);


        return events;
    }
    private static StaticAnimation.Event[] special3Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.13333334f, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();

            if (!entity.level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.UDJAT_KHOPESH_INNATE_SPIN, 0.5f, 1, 1f);
            } else {
                EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0.5,0), 5, EgoWeaponsParticles.UDJAT_SAND.get(), new Vector3d(0, 0.2f, 0.2f), "Tool_R", false);

            }

            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] innateEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];
        events[0] = StaticAnimation.Event.create(0.2F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), TexturedAfterImagePresets.UDJAT.ordinal(), 0);
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
            }
            if (!entity.level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.UDJAT_KHOPESH_INNATE_SPIN, 0.5f, 1, 1f);
            }

            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("hitEntity");
        }, StaticAnimation.Event.Side.BOTH);

// EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,0.2), 20, EpicFightParticles.BLOOD.get(), 0.2f, "Tool_L", false);
        // LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, Vector3d speedOffsets, String jointName, boolean acceptServerSide
        events[1] = StaticAnimation.Event.create(0.3F, (entitypatch) -> {
            EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0.5,0), 5, EgoWeaponsParticles.UDJAT_SAND.get(), new Vector3d(0, 0.2f, 0.2f), "Tool_R", false);
        }, StaticAnimation.Event.Side.CLIENT);
        events[2] = StaticAnimation.Event.create(0.45F, (entitypatch) -> {
            EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0.5,0), 5, EgoWeaponsParticles.UDJAT_SAND.get(), new Vector3d(0, 0.2f, 0.2f), "Tool_R", false);
        }, StaticAnimation.Event.Side.CLIENT);
        events[3] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0.5,0), 5, EgoWeaponsParticles.UDJAT_SAND.get(), new Vector3d(0, 0.2f, 0.2f), "Tool_R", false);
        }, StaticAnimation.Event.Side.CLIENT);
        events[4] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0.5,0), 5, EgoWeaponsParticles.UDJAT_SAND.get(), new Vector3d(0, 0.2f, 0.2f), "Tool_R", false);
        }, StaticAnimation.Event.Side.CLIENT);

        return events;
    }
}
