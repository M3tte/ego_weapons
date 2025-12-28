package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword.*;

public class RatShankMovesetAnims {
    public static StaticAnimation RAT_KNIFE_IDLE;
    public static StaticAnimation RAT_KNIFE_WALK;
    public static StaticAnimation RAT_KNIFE_RUN;
    public static StaticAnimation RAT_KNIFE_AUTO_1;
    public static StaticAnimation RAT_KNIFE_AUTO_2;
    public static StaticAnimation RAT_KNIFE_AUTO_3;
    public static StaticAnimation RAT_KNIFE_GUARD;
    public static StaticAnimation RAT_KNIFE_PARRY_1;
    public static StaticAnimation RAT_KNIFE_PARRY_2;
    public static StaticAnimation RAT_KNIFE_EVADE;

    public static StaticAnimation RAT_KNIFE_JUMP;
    public static StaticAnimation RAT_KNIFE_JUMP_ATTACK;
    public static StaticAnimation RAT_KNIFE_DASH;
    public static StaticAnimation RAT_KNIFE_SPECIAL_1;
    public static StaticAnimation RAT_KNIFE_SPECIAL_2a;
    public static StaticAnimation RAT_KNIFE_SPECIAL_2b;
    public static StaticAnimation RAT_KNIFE_INNATE;
    public static StaticAnimation RAT_KNIFE_EQUIP;

    public static void build(Model biped) {
        System.out.println("Building RAT_KNIFE Animations");
        RAT_KNIFE_IDLE = new StaticAnimation(true, "biped/rat_shank/idle", biped);
        RAT_KNIFE_WALK = new MovementAnimation(true, "biped/rat_shank/walk", biped);
        RAT_KNIFE_RUN = new MovementAnimation(true, "biped/rat_shank/run", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);
        RAT_KNIFE_JUMP = new StaticAnimation(false, "biped/rat_shank/jump", biped);

        RAT_KNIFE_DASH = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.25F, 0.65F, 1.6F, null, "Tool_R", "biped/rat_shank/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        RAT_KNIFE_SPECIAL_1 = new BasicEgoAttackAnimation(0.08F, 0.5F, 0.5F, 0.8F, 1.183f, null, "Tool_R", "biped/rat_shank/special_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_sp_1")
                .addProperty(EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ratShankFollowup(1.1f));

        RAT_KNIFE_SPECIAL_2a = new BasicEgoAttackAnimation(0.0F, 0.2F, 0.22F, 0.5F, 1.5F, null, "Tool_R", "biped/rat_shank/special_2a", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_sp_2a")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);



        RAT_KNIFE_SPECIAL_2b = new BasicEgoAttackAnimation(0.08F, 0.2F, 1.76F, 2F, 2.5F, EgoWeaponsCapabilityPresets.FSTL_BLADE, "Tool_R", "biped/rat_shank/special_2b", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_sp_2b")
                .addProperty(EgoWeaponsAttackProperty.DISABLE_COLLISION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ratShankStick());


        RAT_KNIFE_INNATE = new EgoAttackAnimation(0.08F, 0.3F, 0.5F, 0.8F, 1.5F, null, "Chest", "biped/rat_shank/innate", biped)
                .addProperty(EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, hasteEffect(0.5f));

        RAT_KNIFE_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/rat_shank/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.85f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f);

        RAT_KNIFE_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.33F, 0.55F, 1F, null, "Tool_R", "biped/rat_shank/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        RAT_KNIFE_JUMP_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.33F, 0.6F, 1.3F, null, "Tool_R", "biped/rat_shank/jump_attack", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_jump")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);


        RAT_KNIFE_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.25F, 0.5F, 0.83f, null, "Tool_R", "biped/rat_shank/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        RAT_KNIFE_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.41f, 0.82F, 1.6F, null, "Tool_R", "biped/rat_shank/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_shank_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        RAT_KNIFE_GUARD = new StaticAnimation( true, "biped/rat_shank/guard", biped);
        RAT_KNIFE_PARRY_1 = new GuardAnimation(0.05f,0.3f, "biped/rat_shank/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        RAT_KNIFE_PARRY_2 = new GuardAnimation(0.05f,0.3f, "biped/rat_shank/parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        RAT_KNIFE_EVADE = new DodgeAnimation(0.05f,0.5f, "biped/rat_shank/evade",0.5f, 0.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, afterImage(0, true));

        /*RAT_KNIFE_INNATE = new EgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, EgoWeaponsCapabilityPresets.FirefistSpew, "Tool_R", "biped/firefist/innate", biped)
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

    private static StaticAnimation.Event[] afterImage(float time, boolean sound) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            if (world.isClientSide() && sound){
                entitypatch.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);
            }

        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] hasteEffect(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (EgoWeaponsEffects.SPEED_UP.get().getPotency(entity) >= 3 && entity.level.isClientSide()) {
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            }


        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] ratShankFollowup(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("followUpHit") > 0) {
                if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("followUpHit") > 1) {
                    entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                    if (!entity.level.isClientSide())
                        entitypatch.reserveAnimation(RAT_KNIFE_SPECIAL_2b);
                } else {
                    if (!entity.level.isClientSide())
                        entitypatch.reserveAnimation(RAT_KNIFE_SPECIAL_2a);
                }
            }


        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] ratShankStick() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];

        events[0] = StaticAnimation.Event.create(0.1f, RatShankMovesetAnims::bringEntity, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.5f, RatShankMovesetAnims::bringEntity, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(0.9f, RatShankMovesetAnims::bringEntity, StaticAnimation.Event.Side.BOTH);
        events[3] = StaticAnimation.Event.create(1.3f, RatShankMovesetAnims::bringEntity, StaticAnimation.Event.Side.BOTH);
        events[4] = StaticAnimation.Event.create(1.6f, RatShankMovesetAnims::bringEntity, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static void bringEntity(LivingEntityPatch<?> source) {
        LivingEntity entity = source.getOriginal();
        World world = entity.level;

        if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("stickEntityId") > 0) {
            Entity stuckEntity = world.getEntity(entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("stickEntityId"));

            if (stuckEntity != null) {
                stuckEntity.setDeltaMovement(0,0,0);
            }

        }

    }


}
