package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.ArrayList;
import java.util.List;

public class JustitiaMovesetAnims {
    public static StaticAnimation JUSTITIA_IDLE;
    public static StaticAnimation JUSTITIA_WALK;
    public static StaticAnimation JUSTITIA_RUN;
    public static StaticAnimation JUSTITIA_AUTO_1;
    public static StaticAnimation JUSTITIA_AUTO_2;
    public static StaticAnimation JUSTITIA_AUTO_3;
    public static StaticAnimation JUSTITIA_AUTO_4;
    public static StaticAnimation JUSTITIA_AUTO_JUMP;
    public static StaticAnimation JUSTITIA_GUARD;
    public static StaticAnimation JUSTITIA_GUARD_HIT;
    public static StaticAnimation JUSTITIA_PARRY_1;
    public static StaticAnimation JUSTITIA_HIT_SHORT;
    public static StaticAnimation JUSTITIA_HIT_LONG;
    public static StaticAnimation JUSTITIA_HIT_HANG;
    public static StaticAnimation JUSTITIA_DASH;
    public static StaticAnimation JUSTITIA_INNATE_2;
    public static StaticAnimation JUSTITIA_INNATE_1;
    public static StaticAnimation JUSTITIA_SPECIAL_1;
    public static StaticAnimation JUSTITIA_ARMOR_ABILITY;
    public static StaticAnimation JUSTITIA_EQUIP;

    public static void build(Model biped) {
        System.out.println("Building JUSTITIA Animations");
        JUSTITIA_IDLE = new StaticAnimation(true, "biped/justitia/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        JUSTITIA_WALK = new MovementAnimation(true, "biped/justitia/walk", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        JUSTITIA_RUN = new MovementAnimation(true, "biped/justitia/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        //JUSTITIA_JUMP = new StaticAnimation(false, "biped/justitia/jump", biped)
        //        .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());

        /*JUSTITIA_DASH = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.25F, 0.65F, 1.4F, null, "Tool_R", "biped/justitia/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);*/

        JUSTITIA_SPECIAL_1 = new EgoAttackAnimation(0.08F, 0.45F, 1.8F, 2F, 3.4F, ColliderPreset.FIST, "Torso", "biped/justitia/special", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_special_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.setter(0.01f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialEvent());

        JUSTITIA_INNATE_1 = new EgoAttackAnimation(0.08F, 0.2F, 0.8F, 1.1F, 2F, null, "Tool_R", "biped/justitia/innate_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_innate1")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateFollowup(1.1f));

        JUSTITIA_INNATE_2 = new EgoAttackAnimation(0.08F, 0.2F, 0.75F, 0.85F, 2F, null, "Tool_R", "biped/justitia/innate_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_innate2")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateTwo());





        JUSTITIA_DASH = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.5F, 0.75F, 2F, null, "Tool_R", "biped/justitia/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.85F);

        JUSTITIA_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.66F, 0.9F, 1.2F, null, "Tool_R", "biped/justitia/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        JUSTITIA_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.66F, 0.88F, 1.33F, null, "Tool_R", "biped/justitia/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        JUSTITIA_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 1F, 1.33F, 1.66F, null, "Tool_R", "biped/justitia/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, skill3Event());

        JUSTITIA_AUTO_4 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.66F, 1F, 2F, null, "Tool_R", "biped/justitia/auto_4", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_auto4")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5f);

        JUSTITIA_AUTO_JUMP = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.45F, 0.66F, 1.5F, null, "Tool_R", "biped/justitia/jump_attack", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.PALE)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "justitia_jump")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.JUSTITIA_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2f);


        JUSTITIA_GUARD = new StaticAnimation( true, "biped/justitia/guard", biped);
        JUSTITIA_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/justitia/guard_hit", biped);
        JUSTITIA_PARRY_1 = new GuardAnimation(0.05f,0.5f, "biped/justitia/parry", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        JUSTITIA_HIT_SHORT = new LongHitAnimation(0.02f, "biped/justitia/hit_short", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2f);
        JUSTITIA_HIT_LONG = new LongHitAnimation(0.02f, "biped/justitia/hit_long", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2f);
        JUSTITIA_HIT_HANG = new LongHitAnimation(0.02f, "biped/justitia/hanging", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        JUSTITIA_ARMOR_ABILITY = (new ActionAnimation(0f, 2f,   "biped/justitia/armor_ability", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        JUSTITIA_EQUIP = (new ActionAnimation(0f, 1f,   "biped/justitia/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.5f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.8f);


        /*JUSTITIA_INNATE = new EgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, EgoWeaponsCapabilityPresets.FirefistSpew, "Tool_R", "biped/firefist/innate", biped)
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

    }

    private static StaticAnimation.Event[] innateFollowup(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!entity.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
                entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("followUpHit");
                entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("noTrigger");
            }


        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;


            if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("followUpHit") > 0) {
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 3, 0);

                if (!entity.level.isClientSide())
                    entitypatch.playAnimationSynchronized(JUSTITIA_INNATE_2, 0);
            }
        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] innateTwo() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 3, 0);

            if (!entity.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
                entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("followUpHit");
                entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("noTrigger");
            }


        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] skill3Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 3, 0);
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1);
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 15, 0));
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] specialEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.JUSTITIA_PARTICLE_SCALE.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.JUSTITIA_PIECE.get(), 8, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));
            }

            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 3, 0);
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.6f, 0.6f);
            entitypatch.playSound(EgoWeaponsSounds.JUSTITIA_SPECIAL_TRIGGER, 2, 1, 1);
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 25, 0));
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.3F, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            List<LivingEntity> nearbies = getNearbyEntities(entity);


            if (!world.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.JUSTITIA_SCALE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.JUSTITIA_PARTICLE_SCALE.get(), 20, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0, 0, 0, 0, 0, 0));

                new DelayedEvent(10, (e) -> {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.JUSTITIA_PARTICLE_SCALE.get(), 10, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0, 0, 0, 0, 0, 0));
                });

                new DelayedEvent(20, (e) -> {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.JUSTITIA_PARTICLE_SCALE.get(), 10, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0, 0, 0, 0, 0, 0));
                });

                new DelayedEvent(30, (e) -> {
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.JUSTITIA_PARTICLE_SCALE.get(), 10, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0, 0, 0, 0, 0, 0));
                });
            }



            int i = 0;
            for (LivingEntity ent : nearbies) {
                i++;
                if (ent.hasEffect(EgoWeaponsEffects.SIN.get()) && !ent.equals(entity)) {

                    LivingEntityPatch<?> targetEnt = (LivingEntityPatch<?>) ent.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                    ent.getPersistentData().putInt("justitiaRope", ent.tickCount);
                    ent.playSound(EgoWeaponsSounds.JUSTITIA_SPECIAL_HANG, 6f, 1);
                    if (!world.isClientSide() && targetEnt.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
                        if (targetEnt.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                            targetEnt.playAnimationSynchronized(JUSTITIA_HIT_HANG, 0);
                        } else {
                            ent.addEffect(new EffectInstance(Effects.LEVITATION, 45, 0));
                            SharedFunctions.hitstunEntity(targetEnt, 1, false, 0.5f);
                        }
                    }



                    new DelayedEvent(35 + i, (e) -> {
                        int sinCount = EgoWeaponsEffects.SIN.get().getPotency(ent);

                        if (ent.getArmorValue() > 0)
                            ent.addEffect(new EffectInstance(EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get(), 3, ent.getArmorValue() - 1));

                        EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().increment(ent, 0, sinCount);
                        ent.getPersistentData().remove("justitiaRope");

                        ent.removeEffect(EgoWeaponsEffects.SIN.get());

                        entity.level.addParticle(EgoWeaponsParticles.JUSTITIA_SCALE_STRIKE.get(), ent.getX(), ent.getY() + ent.getBbHeight() / 2, ent.getZ(), 0, 0, 0);

                        for (int c = 0; c < 20; c++) {
                            entity.level.addParticle(EgoWeaponsParticles.JUSTITIA_PIECE.get(), ent.getX() + ent.getRandom().nextFloat() - 0.5f, ent.getY() + ent.getBbHeight() / 2 + ent.getRandom().nextFloat() * 0.2 - 0.1f, ent.getZ() + ent.getRandom().nextFloat() - 0.5f, -0.2 + 0.02 * c, 0.2f, 1.3f);
                            entity.level.addParticle(EgoWeaponsParticles.JUSTITIA_PIECE.get(), ent.getX() + ent.getRandom().nextFloat() * 0.2 - 0.1f, ent.getY() + ent.getBbHeight() / 2 + ent.getRandom().nextFloat() * 0.2 - 0.1f, ent.getZ() + ent.getRandom().nextFloat() * 0.2 - 0.1f, -0.2 + 0.02 * c, 0.2f, 1.3f);

                        }



                        ent.hurt(new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.PALE, "justitia_special"), (float) ent.getMaxHealth() * sinCount * 0.05f + 3 * sinCount);
                        ent.playSound(EgoWeaponsSounds.JUSTITIA_HIT, 6f, 1);
                    });


                }
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(1.8F, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1, 1);
            entitypatch.playSound(EgoWeaponsSounds.JUSTITIA_SPECIAL_HIT, 2, 1, 1);
            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 25, 0));




        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    static float hDist = 16;
    static float vDist = 4;
    private static List<LivingEntity> getNearbyEntities(LivingEntity source) {
        return new ArrayList<>(source.level
                .getNearbyEntities(LivingEntity.class,
                        EntityPredicate.DEFAULT, source, new AxisAlignedBB(source.getX() - (hDist), source.getY() - (vDist), source.getZ() - (hDist), source.getX() + (hDist), source.getY() + (vDist), source.getZ() + (hDist))));
    }

}
