package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.mobMovesets.CravingBloodbagAnims;
import net.m3tte.ego_weapons.gameasset.mobMovesets.NothingThereMovesetAnimations;
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
    public static StaticAnimation DOUBT_HITSTUN;



    public static StaticAnimation SUNSHOWER_FOX_IDLE;
    public static void build() {
        System.out.println("Building ENTITY Animations");
        EgoWeaponsModels<?> models = FMLEnvironment.dist == Dist.CLIENT ? EgoWeaponsClientModels.LOGICAL_CLIENT : EgoWeaponsModels.LOGICAL_SERVER;
        Model doubt = models.doubt;
        Model nothing_there = models.nothing_there;
        Model sunshower_fox = models.sunshower_fox;
        Model craving_bloodbag = models.craving_bloodbag;

        CravingBloodbagAnims.build(craving_bloodbag);
        NothingThereMovesetAnimations.build(nothing_there);

        SUNSHOWER_FOX_IDLE = new StaticAnimation(true, "sunshower_fox/idle", sunshower_fox);

        DOUBT_A_IDLE = new StaticAnimation(true, "doubt/idle", doubt);
        DOUBT_A_WALK = new MovementAnimation(true, "doubt/walk", doubt)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.7f);

        DOUBT_DASH = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.5F, 1F, 2F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/dash", doubt))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "doubt_dash")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_1 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.32F, 0.6F, 1F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_1", doubt))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "doubt_auto_1")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_LIGHT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_2 = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.32F, 0.6F, 1F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_2", doubt))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "doubt_auto_2")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_LIGHT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_BLUNT = (new BasicEgoAttackAnimation(0.02F, 0.03F, 0.32F, 0.6F, 1F, ColliderPreset.FIST, "Foot_R", "doubt/blunt", doubt))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "doubt_auto_blunt")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE, GenericEgoDamage.AttackTypes.BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_B1 = (new EgoAttackAnimation(0.02F, 0.03F, 1.2F, 2.6F, 3F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_c1", doubt))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "doubt_crit")
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.DOUBT_HIT_VERTICAL_FIRST)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(StaticAnimationProperty.EVENTS, chargeSound(EgoWeaponsSounds.DOUBT_CHARGE))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DOUBT_AUTO_B2 = (new EgoAttackAnimation(0.02F, 0.03F, 0.4F, 2F, 2.2F, EgoWeaponsCapabilityPresets.DoubtBlade, "Blade", "doubt/auto_c2", doubt))
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER, "doubt_crit")
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE, "doubt_eviscerate")
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
        DOUBT_HITSTUN = new PushDownAnimation(0.05f, "doubt/hitstun_short", doubt).addProperty(StaticAnimationProperty.PLAY_SPEED, 0.5f);


    }

    private static StaticAnimation.Event[] chargeSound(SoundEvent e) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.playSound(e, 1, 1);
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



}
