package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.potion.Terror;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
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

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.*;
import static net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims.RANGA_GUARD_STAGGER;
import static net.m3tte.ego_weapons.potion.countEffects.Shell.incrementShell;

public class EgoWeaponsMobAnimations {


    public static StaticAnimation DOUBT_A_IDLE;
    public static StaticAnimation DOUBT_A_WALK;
    public static StaticAnimation DOUBT_DASH;
    public static StaticAnimation DOUBT_AUTO_1;
    public static StaticAnimation DOUBT_AUTO_2;
    public static StaticAnimation DOUBT_AUTO_B1;
    public static StaticAnimation DOUBT_AUTO_B2;
    public static StaticAnimation DOUBT_GUARD;
    public static StaticAnimation DOUBT_AUTO_BLUNT;
    public static StaticAnimation DOUBT_STUN_SHORT_1;
    public static StaticAnimation DOUBT_STUN_SHORT_2;
    public static StaticAnimation DOUBT_STUN_LONG;
    public static StaticAnimation DOUBT_STUN_KNOCKDOWN;
    public static StaticAnimation DOUBT_DEATH;
    public static StaticAnimation DOUBT_STUN_STAGGER;
    public static StaticAnimation DOUBT_PINDOWN;
    public static StaticAnimation DOUBT_LIFTUP;


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
    public static StaticAnimation NT_STOMP;
    public static StaticAnimation NT_SCREECH;

    public static StaticAnimation SUNSHOWER_FOX_IDLE;
    public static void build() {
        EgoWeaponsModels<?> models = FMLEnvironment.dist == Dist.CLIENT ? EgoWeaponsClientModels.LOGICAL_CLIENT : EgoWeaponsModels.LOGICAL_SERVER;
        Model doubt = models.doubt;
        Model nothing_there = models.nothing_there;
        Model sunshower_fox = models.sunshower_fox;


        SUNSHOWER_FOX_IDLE = new StaticAnimation(true, "sunshower_fox/idle", sunshower_fox);

        DOUBT_A_IDLE = new StaticAnimation(true, "doubt/idle", doubt);
        DOUBT_A_WALK = new MovementAnimation(true, "doubt/walk", doubt)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.7f);

        DOUBT_DASH = (new BasicAttackAnimation(0.02F, 0.03F, 0.5F, 1F, 2F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/dash", doubt))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_1 = (new BasicAttackAnimation(0.02F, 0.03F, 0.32F, 0.6F, 1F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_1", doubt))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_LIGHT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_2 = (new BasicAttackAnimation(0.02F, 0.03F, 0.32F, 0.6F, 1F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_2", doubt))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_LIGHT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_BLUNT = (new BasicAttackAnimation(0.02F, 0.03F, 0.32F, 0.6F, 1F, ColliderPreset.FIST, "Foot_R", "doubt/blunt", doubt))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_B1 = (new AttackAnimation(0.02F, 0.03F, 1.2F, 2.6F, 3F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_c1", doubt))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_VERTICAL_FIRST)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(StaticAnimationProperty.EVENTS, chargeSound(EgoWeaponsSounds.DOUBT_CHARGE))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_B2 = (new AttackAnimation(0.02F, 0.03F, 0.4F, 2F, 2.2F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_c2", doubt))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_VERTICAL_SECOND)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.DOUBT_GROUNDSLAM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.7f))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.8f);

        DOUBT_GUARD = (new ActionAnimation(0.1f, 1.3f,   "doubt/guard", doubt))
                .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.8f);

        DOUBT_STUN_SHORT_1 = new HitAnimation(0.1f,  "doubt/hit_short", doubt)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        DOUBT_STUN_SHORT_2 = new HitAnimation(0.1f,  "doubt/hit_shortb", doubt)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        DOUBT_STUN_LONG = new LongHitAnimation(0.1f,  "doubt/hit_long", doubt)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        DOUBT_STUN_KNOCKDOWN = new KnockdownAnimation(0.1f,  1f, "doubt/hit_knockdown", doubt)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        DOUBT_DEATH = new LongHitAnimation(0.16f,   "doubt/hit_knockdown", doubt)
                .addProperty(StaticAnimationProperty.EVENTS, doubtDeathEffect(0.6f))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.8f);

        DOUBT_STUN_STAGGER = new LongHitAnimation(0.1f, "doubt/stagger", doubt)
                .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);


        DOUBT_PINDOWN = new PushDownAnimation(0.05f, "doubt/pindown", doubt);
        DOUBT_LIFTUP = new PushDownAnimation(0.05f, "doubt/stun_up", doubt);

        NT_IDLE = new StaticAnimation(true, "nothing_there/idle", nothing_there);

        NT_WALK = new MovementAnimation(true, "nothing_there/walk", nothing_there)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.7f);

        NT_AUTO_1 = (new BasicAttackAnimation(0.02F, 0.03F, 0.2F, 0.6F, 1.2F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Forearm", "nothing_there/attack_1", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.6f);

        NT_AUTO_2 = (new BasicAttackAnimation(0.02F, 0.06F, 0.08F, 0.4F, 2.5F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Forearm", "nothing_there/attack_2", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 2)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.6f);

        NT_AUTO_B1 = (new BasicAttackAnimation(0.02F, 0.03F, 0.26F, 0.5F, 0.6F, EgoWeaponsCapabilityPresets.DoubtBlade, "Right_Forearm", "nothing_there/attack_b1", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_BLUNT_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        NT_AUTO_B2 = (new BasicAttackAnimation(0.02F, 0.03F, 0.26F, 0.5F, 2.5F, EgoWeaponsCapabilityPresets.DoubtBlade, "Right_Forearm", "nothing_there/attack_b2", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 8)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        NT_AUTO_STAB = (new BasicAttackAnimation(0.02F, 0.03F, 0.26F, 0.5F, 2F, EgoWeaponsCapabilityPresets.LONGER_BLADE, "Left_Claw", "nothing_there/attack_stab", nothing_there))
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

        NT_DASH_C = (new AttackAnimation(0.02F, 0.03F, 0.5F, 0.8F, 1.0F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Forearm", "nothing_there/dash_claw", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.NOTHING_THERE_SLASH_ALT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        NT_DASH_C_F = (new AttackAnimation(0.02F, 0.35F, 0.2F, 1F, 1.5F, EgoWeaponsCapabilityPresets.DoubtBlade, "Left_Claw", "nothing_there/dash_claw_f", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_VERTICAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 6)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        NT_DASH_B = (new AttackAnimation(0.1F, 0.11F, 0.5F, 1.2F, 1.5F, EgoWeaponsCapabilityPresets.DoubtBlade, "Right_Bulb", "nothing_there/dash_blunt", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2f))
                .addProperty(StaticAnimationProperty.EVENTS, chargeSound(EgoWeaponsSounds.NOTHING_THERE_HI))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        NT_DASH_B_F = (new AttackAnimation(0.1F, 0.11F, 0.75F, 1.5F, 1.6F, EgoWeaponsCapabilityPresets.DoubtBlade, "Right_Bulb", "nothing_there/dash_blunt_f", nothing_there))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MIMICRY_DASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        NT_GOODBYE = (new BasicAttackAnimation(0.02F, 0.03F, 0.6F, 1F, 2F, EgoWeaponsCapabilityPresets.NTBlade, "Right_Blade", "nothing_there/goodbye", nothing_there))
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

    private static StaticAnimation.Event[] ntScreech() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[6];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.getOriginal().getPersistentData().remove("windupCharge");
            entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_WINDUP, 0.7f,0.3f, 0.3f);
            entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_FLESH, 1, 1);
            entitypatch.getOriginal().getPersistentData().putFloat("windupCharge", 0);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(3.2f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");

            if (charge > 15) {
                int mult = Math.min((int) (charge / 15),5);
                incrementShell(entitypatch.getOriginal(), mult);
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_HIGH, 1, 1);
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, 0.8f);
            } else {
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SCREECH_LOW, 1, 1);
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, 0.6f);
            }
            spawnScreechParticles(30, entitypatch.getOriginal());

            entitypatch.getOriginal().getPersistentData().remove("windupCharge");
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(3.2f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            if (charge > 15) {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, 0.5f);
            } else {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, 0.4f);
            }
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(3.5f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            if (charge > 15) {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, 0.5f);
            } else {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 8, 0.4f);
            }
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(3.8f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            if (charge > 15) {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 9, 0.3f);
            } else {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 9, 0.2f);
            }
            spawnScreechParticles(30, entitypatch.getOriginal());
        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(4.2f, (entitypatch) -> {
            float charge = entitypatch.getOriginal().getPersistentData().getFloat("windupCharge");
            if (charge > 15) {
                ntScreechEvent(entitypatch.getOriginal().level, entitypatch.getOriginal().blockPosition(), entitypatch.getOriginal(), 10, 0.2f);
            }
            spawnScreechParticles(30, entitypatch.getOriginal());
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

    private static StaticAnimation.Event[] doubtDeathEffect(float delay) {
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

                                    if (targetPatch instanceof PlayerPatch<?>) {
                                        PlayerPatch<?> playerPatch = (PlayerPatch<?>) targetPatch;
                                        playerPatch.setStamina(0);
                                    }

                                    if (targetPatch.getHitAnimation(ExtendedDamageSource.StunType.LONG) == Animations.BIPED_HIT_LONG) {
                                        targetPatch.playAnimationSynchronized(RANGA_GUARD_STAGGER, 0.0F);
                                    }
                                } else {
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

    private static void ntScreechEvent(World world, BlockPos pos, LivingEntity entity, float distance, float power) {
        if (world instanceof ServerWorld) {
            List<Entity> _entfound = world
                    .getNearbyEntities(LivingEntity.class,
                            EntityPredicate.DEFAULT, null, new AxisAlignedBB(pos.getX() - (distance), pos.getY() - (distance), pos.getZ() - (distance), pos.getX() + (distance), pos.getY() + (distance), pos.getZ() + (distance)))
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
                                living.addEffect(new EffectInstance(Terror.get(), 400, 0));
                                targetPatch.knockBackEntity(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), power * 4);
                            }
                        }
                    }
                }
            }
        }
    }
}
