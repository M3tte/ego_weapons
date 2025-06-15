package net.m3tte.ego_weapons.gameasset.entities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;

public class CravingBloodbagAnims {

    public static StaticAnimation CRAVING_BLOODBAG_IDLE;
    public static StaticAnimation CRAVING_BLOODBAG_WALK;
    public static StaticAnimation CRAVING_BLOODBAG_RUN;
    public static StaticAnimation CRAVING_BLOODBAG_STUN_SHORT;
    public static StaticAnimation CRAVING_BLOODBAG_STUN_LONG;
    public static StaticAnimation CRAVING_BLOODBAG_STUN_KNOCKDOWN;
    public static StaticAnimation CRAVING_BLOODBAG_DEATH;
    public static StaticAnimation CRAVING_BLOODBAG_STAGGER;
    public static StaticAnimation CRAVING_BLOODBAG_STUN_DOWN;
    public static StaticAnimation CRAVING_BLOODBAG_STUN_UP;
    public static StaticAnimation CRAVING_BLOODBAG_STUN_HIT;
    public static StaticAnimation CRAVING_BLOODBAG_ATTACK_1;
    public static StaticAnimation CRAVING_BLOODBAG_ATTACK_2;
    public static StaticAnimation CRAVING_BLOODBAG_EVADE;
    public static StaticAnimation CRAVING_BLOODBAG_COUNTER_START;
    public static StaticAnimation CRAVING_BLOODBAG_COUNTER_TRIGGER;
    public static StaticAnimation CRAVING_BLOODBAG_EXECUTE;
    public static StaticAnimation CRAVING_BLOODBAG_DASH;

    public static void build(Model bloodbagModel) {
        System.out.println("Building BLOODBAG Animations");

        CRAVING_BLOODBAG_IDLE = new StaticAnimation(true, "craving_bloodbag/idle", bloodbagModel);
        CRAVING_BLOODBAG_WALK = new MovementAnimation(true, "craving_bloodbag/walk", bloodbagModel)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);
        CRAVING_BLOODBAG_RUN = new MovementAnimation(true, "craving_bloodbag/run", bloodbagModel)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.1f);


        CRAVING_BLOODBAG_STUN_SHORT = new HitAnimation(0.03f,  "craving_bloodbag/short_stun", bloodbagModel).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.4f);

        CRAVING_BLOODBAG_STUN_LONG = new LongHitAnimation(0.1f,  "craving_bloodbag/long_stun", bloodbagModel);

        CRAVING_BLOODBAG_STUN_KNOCKDOWN = new KnockdownAnimation(0.1f,  1f, "craving_bloodbag/knockdown", bloodbagModel)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        CRAVING_BLOODBAG_DEATH = new LongHitAnimation(0.16f,   "craving_bloodbag/knockdown", bloodbagModel)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, bloodbagDeathEffect(0.6f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.8f);

        CRAVING_BLOODBAG_STAGGER = new LongHitAnimation(0.1f, "craving_bloodbag/stagger", bloodbagModel)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        CRAVING_BLOODBAG_STUN_DOWN = new PushDownAnimation(0.05f, "craving_bloodbag/stun_down", bloodbagModel);
        CRAVING_BLOODBAG_STUN_UP = new PushDownAnimation(0.05f, "craving_bloodbag/stun_up", bloodbagModel);
        CRAVING_BLOODBAG_STUN_HIT = new PushDownAnimation(0.05f, "craving_bloodbag/long_stun", bloodbagModel).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.3f);

        CRAVING_BLOODBAG_ATTACK_1 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.18F, 0.44F, 1F, EgoWeaponsCapabilityPresets.DoubtBlade, "LeftLowerArm", "craving_bloodbag/attack_1", bloodbagModel))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "bloodbag_attack_1")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        CRAVING_BLOODBAG_ATTACK_2 = (new BasicEgoAttackAnimation(0.02F, 0.06F, 0.18F, 0.44F, 1F, EgoWeaponsCapabilityPresets.DoubtBlade, "RightLowerArm", "craving_bloodbag/attack_2", bloodbagModel))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "bloodbag_attack_2")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        CRAVING_BLOODBAG_DASH = (new BasicEgoAttackAnimation(0.02F, 0.06F, 0.5F, 1F, 2F, EgoWeaponsCapabilityPresets.DoubtBlade, "RightLowerArm", "craving_bloodbag/dash", bloodbagModel))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.SLASH)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "bloodbag_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        CRAVING_BLOODBAG_EVADE = (new DodgeAnimation(0.02F, 0.5f, "craving_bloodbag/evade", 0.5f, 1.5f, bloodbagModel))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        CRAVING_BLOODBAG_COUNTER_START = (new ActionAnimation(0.02F, 2f, "craving_bloodbag/counter_start", bloodbagModel))
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)

                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, counterStart(0.25f));

        CRAVING_BLOODBAG_COUNTER_TRIGGER = (new EgoAttackAnimation(0.03F, "craving_bloodbag/counter_trigger", bloodbagModel,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.3F, 0.75F, 1F, 1.2F, 1.2F, "RightLowerArm", EgoWeaponsCapabilityPresets.DoubtBlade)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.SWORD_IN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(1.15F, 1.2F, 1.22F, 1.4F, 3F, 3F, "RightLowerArm", EgoWeaponsCapabilityPresets.DoubtBlade)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CLASH_KNOCK, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SHOULD_CLASH, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "bloodbag_counter")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, counter(0.1f)));

        CRAVING_BLOODBAG_EXECUTE = (new EgoAttackAnimation(0.03F, "craving_bloodbag/execute", bloodbagModel,
                new EgoAttackAnimation.EgoAttackPhase(0.0F, 0.3F, 0.35F, 0.6F, 1F, 1F, "RightLowerArm", EgoWeaponsCapabilityPresets.DoubtBlade)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SHOULD_CLASH, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.SWORD_IN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(1F, 1.1F, 1.1F, 1.3F, 2.5F, 2.5F, "RightLowerArm", EgoWeaponsCapabilityPresets.DoubtBlade)
                        .addProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SHOULD_CLASH, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
        )
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SHOULD_CLASH, false)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "bloodbag_execute")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.PIERCE)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE, GenericEgoDamage.DamageTypes.RED)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));
    }

    private static StaticAnimation.Event[] bloodbagDeathEffect(float delay) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (world.isClientSide()) {
                world.playLocalSound(entity.position().x, entity.position().y, entity.position().z, EgoWeaponsSounds.DOUBT_DEATH, SoundCategory.HOSTILE, 1, 1, true);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(delay, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1,0), 1, EgoWeaponsParticles.DOUBT_EXPLODE.get(), 0, "Root", false);

            if (world.isClientSide()) {
                world.playLocalSound(entity.position().x, entity.position().y, entity.position().z, SoundEvents.GENERIC_EXPLODE, SoundCategory.HOSTILE, 1, 1, true);
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] counterStart(float delay) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(delay, (entitypatch) -> {
            entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 37, 0));

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
    private static StaticAnimation.Event[] counter(float delay) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(delay, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (world.isClientSide()) {
                world.playLocalSound(entity.position().x, entity.position().y, entity.position().z, EgoWeaponsSounds.CLASH_WIN, SoundCategory.HOSTILE, 1, 1, true);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
}
