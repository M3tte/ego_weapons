//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp.gameasset;

import net.m3tte.tcorp.TCorpParticleRegistry;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.execFunctions.AtelierCooldownHandler;
import net.m3tte.tcorp.execFunctions.BlackSilenceEvaluator;
import net.m3tte.tcorp.item.blackSilence.*;
import net.m3tte.tcorp.item.magic_bullet.MagicBulletProjectile;
import net.m3tte.tcorp.particle.MagicBulletAimParticle;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.particle.MagicBulletShell;
import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.m3tte.tcorp.potion.MagicBulletPotionEffect;
import net.m3tte.tcorp.procedures.BlipTick;
import net.m3tte.tcorp.specialParticles.StaggerParticle;
import net.m3tte.tcorp.world.capabilities.SanitySystem;
import net.m3tte.tcorp.world.capabilities.StaggerSystem;
import net.m3tte.tcorp.world.capabilities.item.TCorpCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.m3tte.tcorp.procedures.SharedFunctions.onStaggered;
import static yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import static yesman.epicfight.api.utils.ExtendedDamageSource.StunType;

public class TCorpAnimations {
    public static StaticAnimation DURANDAL_IDLE;
    public static StaticAnimation DURANDAL_RUN;
    public static StaticAnimation DURANDAL_DRAW;
    public static StaticAnimation DURANDAL_ATTACK_1;

    public static StaticAnimation DURANDAL_FURIOSO_1;
    public static StaticAnimation DURANDAL_FURIOSO_2;
    public static StaticAnimation DURANDAL_FURIOSO_3;

    public static StaticAnimation DURANDAL_FURIOSO_SHEATH;
    public static StaticAnimation DURANDAL_GUARD;
    public static StaticAnimation DURANDAL_GUARD_HIT;
    public static StaticAnimation DURANDAL_GUARD_COUNTER;

    public static StaticAnimation CRYSTAL_ATELIER_INSTANT_DASH;

    public static StaticAnimation CRYSTAL_ATELIER_FURIOSO;

    public static StaticAnimation CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE;

    public static StaticAnimation CRYSTAL_ATELIER_SKILL;

    public static StaticAnimation CRYSTAL_ATELIER_DODGE;

    public static StaticAnimation ZELKOVA_IDLE;
    public static StaticAnimation ZELKOVA_ATTACK_1;

    public static StaticAnimation ZELKOVA_ATTACK_2;

    public static StaticAnimation ZELKOVA_FURIOSO_1;

    public static StaticAnimation ZELKOVA_FURIOSO_2;

    public static StaticAnimation RANGA_IDLE;

    public static StaticAnimation RANGA_WALK;

    public static StaticAnimation RANGA_RUN;

    public static StaticAnimation RANGA_KNEEL;

    public static StaticAnimation RANGA_SNEAK;

    public static StaticAnimation RANGA_GUARD;

    public static StaticAnimation RANGA_GUARD_HIT;

    public static StaticAnimation RANGA_GUARD_STAGGER;

    public static StaticAnimation STAGGER;
    public static StaticAnimation RANGA_ATTACK_1;
    public static StaticAnimation RANGA_ATTACK_2;
    public static StaticAnimation RANGA_ATTACK_3;

    // Identical to the above but faster, deadlier and they stun
    public static StaticAnimation RANGA_FURIOSO_1;
    public static StaticAnimation RANGA_FURIOSO_2;
    public static StaticAnimation RANGA_FURIOSO_3;

    public static StaticAnimation RANGA_ATTACK_3_DEBOUNCE;

    public static StaticAnimation RANGA_EVISCERATE_1;

    public static StaticAnimation RANGA_EVISCERATE_2;

    public static StaticAnimation ALLAS_ATTACK_1;
    public static StaticAnimation ALLAS_DASH;
    public static StaticAnimation ALLAS_DASH_DEB;

    public static StaticAnimation ALLAS_GUARD;
    public static StaticAnimation ALLAS_GUARD_HIT;

    public static StaticAnimation ALLAS_GUARD_COUNTER;

    public static StaticAnimation ATELIER_REVOLVER_FIRE_1;
    public static StaticAnimation ATELIER_REVOLVER_FIRE_2;

    public static StaticAnimation ATELIER_REVOLVER_FURIOSO_1;
    public static StaticAnimation ATELIER_REVOLVER_FURIOSO_2;
    public static StaticAnimation ATELIER_REVOLVER_GUARD;
    public static StaticAnimation ATELIER_REVOLVER_GUARD_HIT;

    public static StaticAnimation ATELIER_REVOLVER_COUNTER;

    public static StaticAnimation ATELIER_SHOTGUN_IDLE;

    public static StaticAnimation ATELIER_SHOTGUN_WALK;
    public static StaticAnimation ATELIER_SHOTGUN_RUN;
    public static StaticAnimation ATELIER_SHOTGUN_FIRE;

    public static StaticAnimation ATELIER_SHOTGUN_GUARD;

    public static StaticAnimation ATELIER_SHOTGUN_GUARD_HIT;
    public static StaticAnimation ATELIER_SHOTGUN_AUTO_1;
    public static StaticAnimation ATELIER_SHOTGUN_AUTO_2;

    public static StaticAnimation ATELIER_SHOTGUN_AUTO_3;

    public static StaticAnimation DURANDAL_EQUIP;
    public static StaticAnimation DUAL_EQUIP;

    public static StaticAnimation BS_DODGE;
    public static StaticAnimation ATELIER_SHOTGUN_EQUIP;
    public static StaticAnimation ALLAS_SPEAR_EQUIP;
    public static StaticAnimation OLD_BOYS_EQUIP;
    public static StaticAnimation ORLANDO_TRIGGER;

    public static StaticAnimation MOOK_IDLE;
    public static StaticAnimation MOOK_WALK;
    public static StaticAnimation MOOK_RUN;
    public static StaticAnimation MOOK_SNEAK;
    public static StaticAnimation MOOK_KNEEL;
    public static StaticAnimation MOOK_AUTO_1;
    public static StaticAnimation MOOK_AUTO_2;
    public static StaticAnimation MOOK_AUTO_3;

    public static StaticAnimation MOOK_AUTO_4;
    public static StaticAnimation MOOK_SHEATH;
    public static StaticAnimation MOOK_AUTO_JUMP;
    public static StaticAnimation MOOK_JUMP_SHEATH;
    public static StaticAnimation MOOK_CUT;
    public static StaticAnimation MOOK_GUARD;

    public static StaticAnimation MOOK_GUARD_HIT;

    public static StaticAnimation MOOK_GUARD_HIT_PARRY;

    public static StaticAnimation OLD_BOYS_AUTO_1;
    public static StaticAnimation OLD_BOYS_AUTO_2;
    public static StaticAnimation OLD_BOYS_AUTO_3;
    public static StaticAnimation OLD_BOYS_FURIOSO;
    public static StaticAnimation OLD_BOYS_GUARD;
    public static StaticAnimation OLD_BOYS_PARRY;


    public static StaticAnimation WHEELS_AUTO_1;
    public static StaticAnimation WHEELS_AUTO_2;
    public static StaticAnimation WHEELS_AUTO_3;
    public static StaticAnimation WHEELS_CROUCH;
    public static StaticAnimation WHEELS_DASH;
    public static StaticAnimation WHEELS_IDLE;
    public static StaticAnimation WHEELS_RUN;
    public static StaticAnimation WHEELS_SNEAK;
    public static StaticAnimation WHEELS_WALK;
    public static StaticAnimation WHEELS_HEAVY;

    public static StaticAnimation KALI_EGO_AUTO_1;
    public static StaticAnimation KALI_EGO_AUTO_2;
    public static StaticAnimation KALI_AUTO_1;
    public static StaticAnimation KALI_AUTO_2;
    public static StaticAnimation KALI_AUTO_3;
    public static StaticAnimation KALI_AUTO_4;
    public static StaticAnimation KALI_DASH;
    public static StaticAnimation KALI_ONRUSH;
    public static StaticAnimation KALI_REND;
    public static StaticAnimation KALI_JUMP;
    public static StaticAnimation KALI_IMPACT;
    public static StaticAnimation KALI_GUARD;
    public static StaticAnimation KALI_GUARD_HIT;
    public static StaticAnimation KALI_IDLE;
    public static StaticAnimation GREAT_SPLIT_HORIZONTAL;
    public static StaticAnimation GREAT_SPLIT_VERTICAL;
    public static StaticAnimation KALI_KNEEL;
    public static StaticAnimation KALI_PARRY_1;
    public static StaticAnimation KALI_PARRY_2;
    public static StaticAnimation KALI_PARRY_EVADE;
    public static StaticAnimation KALI_RUN;
    public static StaticAnimation KALI_SNEAK;
    public static StaticAnimation KALI_WALK;


    public static StaticAnimation MIMICRY_AUTO_1;
    public static StaticAnimation MIMICRY_AUTO_2;
    public static StaticAnimation MIMICRY_AUTO_3;
    public static StaticAnimation MIMICRY_RUN;
    public static StaticAnimation MIMICRY_SNEAK;
    public static StaticAnimation MIMICRY_KNEEL;
    public static StaticAnimation MIMICRY_WALK;
    public static StaticAnimation MIMICRY_IDLE;
    public static StaticAnimation MIMICRY_JUMP;
    public static StaticAnimation MIMICRY_GUARD;
    public static StaticAnimation MIMICRY_GUARD_HIT;
    public static StaticAnimation MIMICRY_DASH;
    public static StaticAnimation MIMICRY_HELLO;
    public static StaticAnimation MIMICRY_GOODBYE;


    public static StaticAnimation KALI_EGO_IDLE;
    public static StaticAnimation KALI_EGO_KNEEL;
    public static StaticAnimation KALI_EGO_WALK;

    public static StaticAnimation KALI_EGO_RUN;
    public static StaticAnimation KALI_EGO_SNEAK;
    public static StaticAnimation PUMMEL_DOWN;




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
    public static StaticAnimation MAGIC_BULLET_SPIN_1;
    public static StaticAnimation MAGIC_BULLET_SPIN_2;
    public TCorpAnimations() {
    }

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put("tcorp", TCorpAnimations::build);
    }

    private static void build() {
        Models<?> models = FMLEnvironment.dist == Dist.CLIENT ? ClientModels.LOGICAL_CLIENT : Models.LOGICAL_SERVER;
        Model biped = models.biped;
        DURANDAL_IDLE = new StaticAnimation(true, "biped/durandal/idle", biped);
        DURANDAL_RUN = new MovementAnimation(true, "biped/durandal/run", biped);
        DURANDAL_DRAW = (new SpecialAttackAnimation(0.16F, 0.1F, 1.0F, 1.4F, 2.0F, null, "Tool_R", "biped/durandal/draw", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(0))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 2)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:woosh", ':')))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.durandal.strong", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        DURANDAL_GUARD = new StaticAnimation( true, "biped/durandal/guard", biped);
        DURANDAL_GUARD_HIT = new GuardAnimation(0.1f, 0.4f,"biped/durandal/guard_hit", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.7f);
        DURANDAL_GUARD_COUNTER = new GuardAnimation(0.2f,0.5f, "biped/durandal/guard_parry", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.7f);


        DURANDAL_ATTACK_1 = (new BasicAttackAnimation(0.16F, 0.1F, 0.4F, 0.8F, 0.95F, null, "Tool_R", "biped/durandal/attack_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f);

        DURANDAL_FURIOSO_1 = (new BasicAttackAnimation(0.2F, 0.1F, 0.5F, 0.75F, 0.9F, null, "Tool_R", "biped/durandal/furioso_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.durandal.down", ':')))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(3f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(4));


        DURANDAL_FURIOSO_2 = (new BasicAttackAnimation(0.05F, 0.05F, 0.04F, 0.33F, 0.45F, null, "Tool_R", "biped/durandal/furioso_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.durandal.up", ':')))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(3f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.35f)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(4));

        DURANDAL_FURIOSO_3 = (new BasicAttackAnimation(0.3F, 0.05F, 0.15F, 0.4F, 1F, null, "Tool_R", "biped/durandal/furioso_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.durandal.strong", ':')))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(0.2f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(StaticAnimationProperty.EVENTS, furiosoSheatheEngage())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        DURANDAL_FURIOSO_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/durandal/furioso_sheathe", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, furiosoSheatheEvents())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.2f);

        CRYSTAL_ATELIER_INSTANT_DASH = (new DashAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 0.85F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.crystal_atelier", ':')))
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_FURIOSO = (new SpecialAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 0.85F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(8))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.crystal_atelier", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.CRYSTAL_ATELIER_HIT)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_INSTANT_DASH_DEBOUNCE = (new DashAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 2F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant_debounce", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.crystal_atelier", ':')))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.3f)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_SKILL = (new DashAttackAnimation(0.16F, 0.05F, 0.08F, 0.7F, 2F, ColliderPreset.DUAL_SWORD_DASH, "Root", "biped/crystal_atelier/dash_instant_debounce", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.crystal_atelier.strong", ':')))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.CRYSTAL_ATELIER_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.3f)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(StaticAnimationProperty.EVENTS, crystalAtelierEvents());

        CRYSTAL_ATELIER_DODGE = (new DashAttackAnimation(0.04F, 0.05F, 0.08F, 0.07F, 0.45F, null, "Root", "biped/crystal_atelier/dodge", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.setter(0.0f))
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0f))
                .addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.evade", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f)
                .addProperty(StaticAnimationProperty.EVENTS, atelierDodgeEvent());


        ZELKOVA_IDLE = new StaticAnimation(true, "biped/zelkova/idle", biped);

        ZELKOVA_ATTACK_1 = (new BasicAttackAnimation(0.16F, 0.1F, 0.66F, 0.83F, 1.1F, null, "Tool_R", "biped/zelkova/attack_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.zelkova.axe", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f);

        ZELKOVA_ATTACK_2 = (new SpecialAttackAnimation(0.2F, 0.2F, 0.5F, 0.7F, 1.5F, null, "Tool_L", "biped/zelkova/attack_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.zelkova.mace", ':')))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);


        ZELKOVA_FURIOSO_1 = (new BasicAttackAnimation(0.16F, 0.1F, 0.66F, 0.83F, 1.1F, null, "Tool_R", "biped/zelkova/attack_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.ZELKOVA_HIT)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.zelkova.axe", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(5));

        ZELKOVA_FURIOSO_2 = (new SpecialAttackAnimation(0.2F, 0.2F, 0.5F, 0.7F, 1.5F, null, "Tool_L", "biped/zelkova/attack_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.zelkova.mace", ':')))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.ZELKOVA_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.55f)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(5));

        RANGA_IDLE = new StaticAnimation(true, "biped/ranga/idle", biped);
        RANGA_WALK = new MovementAnimation(true, "biped/ranga/walk", biped);
        RANGA_RUN = new MovementAnimation(true, "biped/ranga/run", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.5f);
        RANGA_KNEEL = new StaticAnimation(true, "biped/ranga/kneel", biped);
        RANGA_SNEAK = new MovementAnimation(true, "biped/ranga/sneak", biped);

        RANGA_GUARD = new StaticAnimation( true, "biped/ranga/guard", biped);
        RANGA_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/ranga/guard_hit", biped);
        RANGA_GUARD_STAGGER = new LongHitAnimation(0.05f, "biped/ranga/guard_stagger", biped);
        STAGGER = new LongHitAnimation(0.05f, "biped/generic/stagger", biped);
        PUMMEL_DOWN = new LongHitAnimation(0.05f, "biped/generic/pummel_down", biped);

        RANGA_ATTACK_1 = (new DashAttackAnimation(0.1F, 0.05F, 0.25F, 0.6F, 0.7F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaEvents());

        RANGA_ATTACK_2 = (new DashAttackAnimation(0F, 0F, 0.3F, 0.65F, 0.9F, ColliderPreset.SWORD, "Tool_L", "biped/ranga/attack_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, ranga2Events());

        RANGA_ATTACK_3 = (new SpecialAttackAnimation(0F, 0F, 0.3F, 0.65F, 1F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaDaggerShort());

        RANGA_FURIOSO_1 = (new DashAttackAnimation(0.1F, 0.05F, 0.25F, 0.6F, 0.7F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.RANGA_FURIOSO_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(4f))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaEvents());

        RANGA_FURIOSO_2 = (new DashAttackAnimation(0F, 0F, 0.3F, 0.65F, 0.9F, ColliderPreset.SWORD, "Tool_L", "biped/ranga/attack_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.RANGA_FURIOSO_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(4f))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, ranga2Events());

        RANGA_FURIOSO_3 = (new SpecialAttackAnimation(0F, 0F, 0.3F, 0.65F, 1F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(0.5f))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaDaggerShort());

        RANGA_ATTACK_3_DEBOUNCE = (new DashAttackAnimation(0F, 0F, 0.3F, 0.65F, 2.5F, ColliderPreset.SWORD, "Tool_R", "biped/ranga/attack_3_deb", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f)
                .addProperty(StaticAnimationProperty.EVENTS, rangaDaggerEvents());

        RANGA_EVISCERATE_1 = new SpecialAttackAnimation(0.08F, 0.05F, 0.34F, 0.6F, 0.8F, null, "Tool_R", "biped/ranga/eviscerate_1", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:sword_stab", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F);

        RANGA_EVISCERATE_2 = new SpecialAttackAnimation(0.05F, 0.0F, 0.3F, 0.4F, 0.8F, null, "Tool_R", "biped/ranga/eviscerate_2", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        ALLAS_ATTACK_1 = (new DashAttackAnimation(0.16F, 0.05F, 0.28F, 0.55F, 0.7F, ColliderPreset.SPEAR, "Tool_R", "biped/allas/auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5))
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:sword_stab", ':')))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT);
        ALLAS_DASH_DEB = (new SpecialAttackAnimation(0.16F, 0.05F, 0.5F, 0.95F, 1.6F, ColliderPreset.SPEAR, "Tool_R", "biped/allas/dash_deb", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT);

        ALLAS_DASH = (new SpecialAttackAnimation(0.16F, 0.05F, 0.3F, 0.66F, 1F, ColliderPreset.SPEAR, "Tool_R", "biped/allas/dash_deb", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:blacksilence.shortsword", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.ALLAS_HIT)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(StaticAnimationProperty.EVENTS, genericFuriosoEvent(10));

        ALLAS_GUARD = new StaticAnimation( true, "biped/allas/guard", biped);
        ALLAS_GUARD_HIT = new GuardAnimation(0.05f, 0.4f,"biped/allas/guard_hit", biped);
        ALLAS_GUARD_COUNTER = new GuardAnimation(0.05f, "biped/allas/guard_counter", biped);


        ATELIER_REVOLVER_GUARD = new StaticAnimation( true, "biped/atelier_revolver/guard", biped);
        ATELIER_REVOLVER_GUARD_HIT = new GuardAnimation(0.05f, "biped/atelier_revolver/guard_hit", biped);

        ATELIER_REVOLVER_COUNTER = new DodgeAnimation(0f, "biped/atelier_revolver/guard_counter", 0.5f, 0.7f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.EVENTS, atelierRevolver1Event());

        BS_DODGE = new DodgeAnimation(0f, "biped/generic/bs_evade", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED,1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, genericDodgeEvent());

        ATELIER_REVOLVER_FIRE_1 = new ActionAnimation( 0.1f, "biped/atelier_revolver/shoot_1", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.EVENTS, atelierRevolver1Event())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f);
        ATELIER_REVOLVER_FIRE_2 = new ActionAnimation( 0.1f, "biped/atelier_revolver/shoot_2", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.EVENTS, atelierRevolver2Event())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f);

        ATELIER_REVOLVER_FURIOSO_1 = new ActionAnimation( 0.1f, "biped/atelier_revolver/shoot_1", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.EVENTS, atelierRevolver1Event())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 2f);
        ATELIER_REVOLVER_FURIOSO_2 = new ActionAnimation( 0.1f, "biped/atelier_revolver/shoot_2", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.EVENTS, atelierRevolver2Event())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 2f);


        ATELIER_SHOTGUN_IDLE = new StaticAnimation(true, "biped/atelier_shotgun/idle", biped);

        ATELIER_SHOTGUN_WALK = new MovementAnimation(true, "biped/atelier_shotgun/walk", biped);

        ATELIER_SHOTGUN_RUN = new StaticAnimation(true, "biped/atelier_shotgun/run", biped);

        ATELIER_SHOTGUN_FIRE = new ActionAnimation(0.35f, "biped/atelier_shotgun/fire", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.EVENTS, atelierShotgunEvent())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.6f);

        ATELIER_SHOTGUN_GUARD = new StaticAnimation( true, "biped/atelier_shotgun/guard", biped);

        ATELIER_SHOTGUN_GUARD_HIT = new GuardAnimation(0.02f, "biped/atelier_shotgun/guard_hit", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.5f);

        ATELIER_SHOTGUN_AUTO_1 = new BasicAttackAnimation(0.08F, 0.05F, 0.28F, 0.5F, 0.7F, null, "Tool_R", "biped/atelier_shotgun/auto_1", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F);

        ATELIER_SHOTGUN_AUTO_2 = new BasicAttackAnimation(0.08F, 0.05F, 0.33F, 0.4F, 0.9F, null, "Tool_R", "biped/atelier_shotgun/auto_2", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F);

        ATELIER_SHOTGUN_AUTO_3 = new BasicAttackAnimation(0.01F, 0.05F, 0.53F, 0.68F, 1F, null, "Tool_R", "biped/atelier_shotgun/auto_3", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(-4.5f))
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F);


        DURANDAL_EQUIP = new ActionAnimation(0.1f, "biped/durandal/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        DUAL_EQUIP = new ActionAnimation(0.1f, "biped/generic/dual_equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        ATELIER_SHOTGUN_EQUIP = new ActionAnimation(0.1f, "biped/atelier_shotgun/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        ALLAS_SPEAR_EQUIP = new ActionAnimation(0.1f, "biped/allas/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        OLD_BOYS_EQUIP = new ActionAnimation(0.1f, 0.4f, "biped/old_boys/equip", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        ORLANDO_TRIGGER = new ActionAnimation(0.1f, "biped/generic/orlando_trigger", biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        MOOK_IDLE = new StaticAnimation(true, "biped/mook/idle", biped);
        MOOK_WALK = new MovementAnimation(true, "biped/mook/walk", biped);
        MOOK_KNEEL = new StaticAnimation(true, "biped/mook/kneel", biped);
        MOOK_SNEAK = new MovementAnimation(true, "biped/mook/sneak", biped);
        MOOK_RUN = new MovementAnimation(true, "biped/mook/run", biped);
        MOOK_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.3F, 0.5F, 0.8F, 1.1F, null, "Tool_R", "biped/mook/auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(StaticAnimationProperty.EVENTS, mookFirstEvent());

        MOOK_AUTO_2 = (new BasicAttackAnimation(0.35F, 0.07F, 0.08F, 0.3F, 0.5F, null, "Tool_R", "biped/mook/auto_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f);

        MOOK_AUTO_3 = (new BasicAttackAnimation(0.5F, 0.2F, 0.2F, 0.5F, 0.55F, null, "Tool_R", "biped/mook/auto_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35f);

        MOOK_AUTO_4 = (new BasicAttackAnimation(0.1F, 0.5F, 0.5F, 0.8F, 1F, null, "Tool_R", "biped/mook/auto_4", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(4))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(8))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEngage(0.8f, false));

        MOOK_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/mook/auto_sheathe", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEvents(0.4f, 4))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);

        MOOK_AUTO_JUMP = (new BasicAttackAnimation(0.1F, 0.5F, 0.5F, 0.8F, 1F, null, "Tool_R", "biped/mook/mook_jump", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TcorpModElements.sounds.get(ResourceLocation.of("tcorp:cross_slash", ':')))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(6))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEngage(0.79f, true));

        MOOK_JUMP_SHEATH = (new ActionAnimation(0.1f, 1.5f,   "biped/mook/mook_jump_sheath", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, mookSheatheEvents(0.5f, 2))
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);


        MOOK_CUT = (new ActionAnimation(0.1f, 1f,   "biped/mook/cut", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(StaticAnimationProperty.EVENTS, mookCutEvent())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1.3f);

        MOOK_GUARD = new StaticAnimation( true, "biped/mook/guard", biped);


        MOOK_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/mook/guard_hit", biped);
        MOOK_GUARD_HIT_PARRY = new GuardAnimation(0.05f,0.1f, "biped/mook/guard_parry", biped);

        OLD_BOYS_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.1F, 0.1F, 0.3F, 0.5F, null, "Tool_R", "biped/old_boys/auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        OLD_BOYS_FURIOSO = (new BasicAttackAnimation(0.1F, 0.1F, 0.1F, 0.3F, 0.9F, null, "Tool_R", "biped/old_boys/auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(1.3f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(7f))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        OLD_BOYS_AUTO_2 = (new BasicAttackAnimation(0.1F, 0.1F, 0.1F, 0.3F, 0.47F, null, "Tool_R", "biped/old_boys/auto_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(1.3f))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f);

        OLD_BOYS_AUTO_3 = (new BasicAttackAnimation(0.1F, 0.1F, 0.34F, 0.6F, 1.5F, null, "Tool_R", "biped/old_boys/auto_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(1.3f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(5f))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(StaticAnimationProperty.EVENTS, oldBoysThreeEvents());

        OLD_BOYS_GUARD = new StaticAnimation( true, "biped/old_boys/guard", biped);

        OLD_BOYS_PARRY = new GuardAnimation(0.05f,0.6f, "biped/old_boys/parry", biped);


        WHEELS_IDLE = new StaticAnimation(true, "biped/wheels/idle", biped);
        WHEELS_CROUCH = new StaticAnimation(true, "biped/wheels/crouch", biped);
        WHEELS_WALK = new MovementAnimation(true, "biped/wheels/walk", biped);
        WHEELS_RUN = new MovementAnimation(true, "biped/wheels/run", biped);
        WHEELS_SNEAK = new MovementAnimation(true, "biped/wheels/sneak", biped);

        WHEELS_AUTO_1 = (new BasicAttackAnimation(0.02F, 0.25F, 0.4F, 0.9F, 1.2F, null, "Tool_R", "biped/wheels/auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        WHEELS_AUTO_2 = (new BasicAttackAnimation(0.1F, 0.25F, 0.4F, 0.7F, 1.4F, null, "Tool_R", "biped/wheels/auto_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        WHEELS_AUTO_3 = (new BasicAttackAnimation(0.1F, 0.3F, 0.6F, 1F, 1.4F, null, "Tool_R", "biped/wheels/auto_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(0))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.adder(1))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        WHEELS_DASH = (new DashAttackAnimation(0.1F, 0.4F, 0.5F, 0.9F, 2F, null, "Tool_R", "biped/wheels/dash_attack", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.DURANDAL_SLASH)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.4f);


        WHEELS_HEAVY = (new SpecialAttackAnimation(0.1F, 0.1F, 0.4F, 0.65F, 0.85F, null, "Tool_R", "biped/wheels/heavy", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.setter(0))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 2)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(StaticAnimationProperty.EVENTS, wheelsSmashEvent());

        KALI_IDLE = new StaticAnimation(0.3f,true, "biped/kali/idle", biped);
        KALI_KNEEL = new StaticAnimation(0.3f,true, "biped/kali/kneel", biped);
        KALI_WALK = new MovementAnimation(0.3f,true, "biped/kali/walk", biped);

        KALI_EGO_IDLE = new StaticAnimation(0.3f,true, "biped/kali/ego_idle", biped);
        KALI_EGO_KNEEL = new StaticAnimation(0.3f,true, "biped/kali/ego_kneel", biped);
        KALI_EGO_WALK = new MovementAnimation(0.3f,true, "biped/kali/ego_walk", biped);
        KALI_EGO_RUN = new StaticAnimation(0.3f,true, "biped/kali/ego_run", biped);
        KALI_EGO_SNEAK = new MovementAnimation(0.3f,true, "biped/kali/ego_sneak", biped);

        KALI_RUN = new MovementAnimation(0.3f,true, "biped/kali/run", biped);
        KALI_SNEAK = new MovementAnimation(0.3f, true, "biped/kali/sneak", biped);

        KALI_GUARD = new StaticAnimation( true, "biped/kali/guard", biped);
        KALI_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/kali/guard_hit", biped);
        KALI_PARRY_1 = new GuardAnimation(0.05f,0.1f, "biped/kali/parry_1", biped).addProperty(StaticAnimationProperty.PLAY_SPEED, 2.0f);
        KALI_PARRY_2 = new GuardAnimation(0.05f,0.1f, "biped/kali/parry_2", biped).addProperty(StaticAnimationProperty.PLAY_SPEED, 2.0f);
        KALI_PARRY_EVADE = new DodgeAnimation(0.1f, "biped/kali/parry_3",0.5f, 0.7f, biped).addProperty(StaticAnimationProperty.PLAY_SPEED, 1.4f);

        KALI_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.54F, 0.63F, ColliderPreset.FIST, "Leg_L", "biped/kali/auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.3f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(1))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f);

        KALI_EGO_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.54F, 0.63F, ColliderPreset.FIST, "Leg_L", "biped/kali/ego_auto_1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.BLACK_SILENCE_ZELKOVA_MACE)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_DASH_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f);

        KALI_EGO_AUTO_2 = (new BasicAttackAnimation(0.1F, 0.12F, 0.33F, 0.5F, 1.12F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/ego_auto_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);


        KALI_AUTO_2 = (new BasicAttackAnimation(0.1F, 0.12F, 0.33F, 0.5F, 1.12F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/auto_2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65f);

        KALI_AUTO_3 = (new BasicAttackAnimation(0.01F, 0.30F, 0.35F, 0.7F, 0.95F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/auto_3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        KALI_AUTO_4 = (new BasicAttackAnimation(0.01F, 0.10F, 0.15F, 0.66F, 1.7F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/auto_4", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HARD_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HORIZONTAL_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(2))
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7f);

        KALI_DASH = (new DashAttackAnimation(0.02F, 0.2F, 0.25F, 0.6F, 1F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/dash", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_STAB)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_DASH_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75f);


        KALI_REND = (new DashAttackAnimation(0.02F, 0.35F, 0.4F, 0.66F, 1F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/rend", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9f);

        KALI_JUMP = (new BasicAttackAnimation(0.3F, 0.3F, 0.33F, 0.83F, 1.4F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/kali/jump", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_STAB)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_VERTICAL_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);

        KALI_IMPACT = (new DashAttackAnimation(0.1F, 0.35F, 0.5F, 0.66F, 1F, null, "Tool_R", "biped/kali/impact", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95f)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);

        GREAT_SPLIT_HORIZONTAL = (new SpecialAttackAnimation(0.1F, "biped/kali/kali_horizontal", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.16F, 0.5F, 1.01F, 1.01F, "Leg_L", ColliderPreset.FIST).addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(6)).addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.3f)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH).addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2)).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT),
                new AttackAnimation.Phase(1.01F, 1.02F, 1.53F, 2F, 2.16F, 2.16F, "Tool_R", TCorpCapabilityPresets.SPLIT_HORIZONTAL)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HORIZONTAL_HIT)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.KALI_SPLIT_HORIZONTAL_SWING)
                        .addProperty(AttackPhaseProperty.HIT_SOUND,  TCorpSounds.KALI_HARD_HORIZONTAL)
                        .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(99))
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2f))
                        .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(3.5f))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT))
                .addProperty(AttackAnimationProperty.ROTATE_X, false)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f));

        GREAT_SPLIT_VERTICAL = (new SpecialAttackAnimation(0.1F, "biped/kali/kali_vertical", biped,
                new AttackAnimation.Phase(0.0F, 0.3F, 0.4F, 0.9F, 0.91F, 0.91F, "Tool_R", TCorpCapabilityPresets.LONGER_BLADE).addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.2f)).addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_SPLIT_VERTICAL_HIT).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE),
                new AttackAnimation.Phase(0.91F, 0.91F, 1.1F, 1.61F, 1.62F, 1.62F, "Tool_R", TCorpCapabilityPresets.LONGER_BLADE).addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AttackPhaseProperty.HIT_SOUND,  TCorpSounds.KALI_SPLIT_VERTICAL_HIT).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE),
                new AttackAnimation.Phase(1.625F, 1.7F, 2F, 2.18F, 3F, 3F, "Tool_R", TCorpCapabilityPresets.LONGER_BLADE).addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2)).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG).addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_SPLIT_VERTICAL_SLASH).addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_VERTICAL_HIT)))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 1)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85f);


        MIMICRY_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.1F, 0.33F, 1F, 1.3F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/auto1", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.55f);

        MIMICRY_AUTO_2 = (new BasicAttackAnimation(0.1F, 0.1F, 0.3F, 0.55F, 0.9F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/auto2", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75f);

        MIMICRY_AUTO_3 = (new BasicAttackAnimation(0.1F, 0.1F, 0.45F, 0.75F, 1F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/auto3", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_VERTICAL_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.6f))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75f);

        MIMICRY_HELLO = (new DashAttackAnimation(0.1F, 0.1F, 0.43F, 0.9F, 1.2F, ColliderPreset.FIST, "Tool_L", "biped/mimicry/hello", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_DASH_HIT)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.NOTHING_THERE_HELLO)
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(35f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.6f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(4f))
                .addProperty(AttackAnimationProperty.COLLIDER_ADDER, 3)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.3f);

        MIMICRY_GOODBYE = (new SpecialAttackAnimation(0.01F, 0.02F, 0.33F, 0.78F, 1.41F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/goodbye", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_HORIZONTAL)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_VERTICAL_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.KALI_SPLIT_HORIZONTAL_SWING)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(25f))
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(3f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1f))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f);

        MIMICRY_DASH = (new DashAttackAnimation(0.02F, 0.03F, 0.36F, 0.85F, 1.3F, TCorpCapabilityPresets.LONGER_BLADE, "Tool_R", "biped/mimicry/dash", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.KALI_STAB)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_DASH_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5f);

        MIMICRY_IDLE = new StaticAnimation(0.3f,true, "biped/mimicry/idle", biped);
        MIMICRY_KNEEL = new StaticAnimation(0.3f,true, "biped/mimicry/kneel", biped);
        MIMICRY_WALK = new MovementAnimation(0.3f,true, "biped/mimicry/walk", biped);
        MIMICRY_JUMP = new MovementAnimation(0.3f,true, "biped/mimicry/jump", biped);

        MIMICRY_RUN = new MovementAnimation(0.3f,true, "biped/mimicry/run", biped);
        MIMICRY_SNEAK = new MovementAnimation(0.3f, true, "biped/mimicry/sneak", biped);

        MIMICRY_GUARD = new StaticAnimation( true, "biped/mimicry/guard", biped);
        MIMICRY_GUARD_HIT = new GuardAnimation(0.05f,0.4f, "biped/mimicry/guard_hit", biped);

        KALI_ONRUSH = (new DashAttackAnimation(0.02F, 0.4F, 0.5F, 0.833F, 1.5F, ColliderPreset.FIST, "Arm_L", "biped/kali/onrush", biped))
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true).addProperty(AttackAnimationProperty.ROTATE_X, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.BLACK_SILENCE_ZELKOVA_MACE)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MIMICRY_DASH_HIT)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.BLACK_SILENCE_EVADE)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2.5f))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6f)
                .addProperty(StaticAnimationProperty.EVENTS, onrushEvent());


        MAGIC_BULLET_IDLE = new StaticAnimation(0.3f,true, "biped/magic_bullet/idle", biped);
        MAGIC_BULLET_WALK = new MovementAnimation(true, "biped/magic_bullet/walk", biped);
        MAGIC_BULLET_RUN = new MovementAnimation(true, "biped/magic_bullet/run", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);
        MAGIC_BULLET_KNEEL = new StaticAnimation(true, "biped/magic_bullet/kneel", biped);
        MAGIC_BULLET_SNEAK = new MovementAnimation(true, "biped/magic_bullet/sneak", biped);
        MAGIC_BULLET_JUMP = new MovementAnimation(0.3f,false, "biped/magic_bullet/jump", biped)
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 2.0f);

        MAGIC_BULLET_EVADE_FORWARD = new DodgeAnimation(0f, "biped/magic_bullet/step_forward", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_EVADE_BACKWARD = new DodgeAnimation(0f, "biped/magic_bullet/step_backward", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_EVADE_LEFT = new DodgeAnimation(0f, "biped/magic_bullet/step_left", 0.5f, 0.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_EVADE_RIGHT = new DodgeAnimation(0f, "biped/magic_bullet/step_right", 0.5f, 0.8f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED,1f)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletDodgeEvent());

        MAGIC_BULLET_AUTO_1 = new BasicAttackAnimation(0.08F, 0.05F, 0.28F, 0.5F, 0.7F, null, "Tool_R", "biped/magic_bullet/auto_1", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.MAGIC_BULLET_HIT_2)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F);

        MAGIC_BULLET_AUTO_2 = new BasicAttackAnimation(0.08F, 0.05F, 0.8F, 1.1F, 1.2F, null, "Tool_R", "biped/magic_bullet/auto_2", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.MAGIC_BULLET_HIT_1)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.WOOSH)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.6F)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletSwingsoundHandler(1));

        MAGIC_BULLET_AUTO_3 = new BasicAttackAnimation(0.01F, 0.05F, 0.5F, 0.7F, 1.5F, null, "Tool_R", "biped/magic_bullet/auto_3", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.MAGIC_BULLET_HIT_2)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.WOOSH)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(-4.5f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletSwingsoundHandler(2));

        MAGIC_BULLET_DASH = new BasicAttackAnimation(0.08F, 0.05F, 0.8F, 1.1F, 1.5F, null, "Tool_R", "biped/magic_bullet/dash", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.MAGIC_BULLET_HIT_1)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.WOOSH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.7F)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletSwingsoundHandler(1));

        MAGIC_BULLET_JUMP_ATTACK = new BasicAttackAnimation(0.08F, 0.05F, 0.3F, 0.6F, 1F, null, "Tool_R", "biped/magic_bullet/jump_attack", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.MAGIC_BULLET_HIT_2)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.WOOSH)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.65F);

        MAGIC_BULLET_GUARD = new StaticAnimation( true, "biped/magic_bullet/guard", biped);
        MAGIC_BULLET_GUARD_HIT = new GuardAnimation(0.05f,0.2f, "biped/magic_bullet/guard_hit", biped);

        MAGIC_BULLET_AIM_1 = new AttackAnimation(0.08F, 0.05F, 0.3F, 0.3F, 2F,null, "Tool_R", "biped/magic_bullet/aim_1", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0))
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.MAGIC_BULLET_BREATHE)

                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletFire1Event())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.7f);
        MAGIC_BULLET_AIM_2 = new AttackAnimation(0.08F, 0.05F, 0.3F, 0.3F, 2.9F,null, "Tool_R", "biped/magic_bullet/aim_2", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(0))
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.MAGIC_BULLET_BREATHE)

                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletFire2Event())
                .addProperty(StaticAnimationProperty.PLAY_SPEED, 0.9f);

        MAGIC_BULLET_SPIN_1 = new BasicAttackAnimation(0.01F, 0.05F, 1.23F, 1.45F, 1.5F, null, "Tool_R", "biped/magic_bullet/special_1", biped)
                .addProperty(AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AttackPhaseProperty.HIT_SOUND, TCorpSounds.MAGIC_BULLET_SPIN_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, TCorpSounds.WOOSH)
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.adder(4f))
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletSwingSpamHandler1());

        MAGIC_BULLET_SPIN_2 = (new SpecialAttackAnimation(0.01F, "biped/magic_bullet/special_2", biped,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.01F, 0.05F, 0.1F, 0.1F, "Tool_R", TCorpCapabilityPresets.RIFLE)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.1F, 0.1F, 0.11F, 0.15F, 0.2F, 0.2F, "Tool_R", TCorpCapabilityPresets.RIFLE)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.21F, 0.25F, 0.3F, 0.3F, "Tool_R", TCorpCapabilityPresets.RIFLE)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.3F, 0.3F, 0.31F, 0.35F, 0.4F, 0.4F, "Tool_R", TCorpCapabilityPresets.RIFLE)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT),
                new AttackAnimation.Phase(0.4F, 0.4F, 0.41F, 0.45F, 1F, 1F, "Tool_R", TCorpCapabilityPresets.RIFLE)
                        .addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                        .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_IMPACT_HIT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0.4f))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                )
                .addProperty(AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(3))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(2))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AttackPhaseProperty.PARTICLE, TCorpParticleRegistry.MAGIC_BULLET_HIT)
                .addProperty(StaticAnimationProperty.EVENTS, magicBulletSwingSpamHandler2()));
    }

    private static void spawnArmatureParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult, String jointName) {
        spawnArmatureParticle(entityPatch,partialTicks,offsets,amount,particle,speedmult, jointName,false);
    }
    public static void spawnArmatureParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult, String jointName, boolean acceptServerSide) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        float speedMultHalf = speedmult / 2;
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        Vector3d particlePos = OpenMatrix4f.transform(middleJointTf, offsets);
        for (int x = 0; x < amount; x++) {
            if (l.isClientSide()) {
                l.addParticle(particle, particlePos.x, particlePos.y, particlePos.z, r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf); //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
            } else if (acceptServerSide) {
                ((ServerWorld)l).sendParticles(particle,particlePos.x,particlePos.y,particlePos.z, 1, 0,0,0,speedmult);
            }
        }
    }
    private static void spawnBlockImpactParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult) {
        spawnBlockImpactParticle(entityPatch, partialTicks, offsets, amount, particle, speedmult, new Vector3i(0,0,0));
    }


    private static Vector3d getArmaturePosition(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, float speedmult, String jointName) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        float speedMultHalf = speedmult / 2;
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        return OpenMatrix4f.transform(middleJointTf, offsets);
    }

    private static void spawnBlockImpactParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult, Vector3i particleOffSets) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        float speedMultHalf = speedmult / 2;
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;

        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), "Tool_R").mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), "Tool_R").mulFront(middleModelTf);
        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        Vector3d originalPos = OpenMatrix4f.transform(middleJointTf, offsets).add(0, 0.2f, 0);

        BlockPos pos = new BlockPos(originalPos.x, originalPos.y -0.4f, originalPos.z);

        BlockPos particlePos = new BlockPos(originalPos.x, originalPos.y -0.4f, originalPos.z).offset(particleOffSets);
        LivingEntity entity = entityPatch.getOriginal();
        if (!l.isEmptyBlock(pos)) {
            if (particle != null)
                l.addParticle(particle, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0,0,0);
            float blastResistance = l.getBlockState(pos).getBlock().getExplosionResistance();
            if (blastResistance > 2) {
                l.addParticle(EpicFightParticles.HIT_BLUNT.get(), particlePos.getX(), particlePos.getY() + 1, particlePos.getZ(), 0,0,0); //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
                entity.playSound(SoundEvents.ANVIL_LAND, 0.7f, 1.3f);
            } else if (blastResistance > 0.2f) {
                for (int x = 0; x < amount; x++) {
                    l.addParticle(new BlockParticleData(ParticleTypes.BLOCK, l.getBlockState(pos)), particlePos.getX() + r.nextFloat() * 0.2 - 0.1, particlePos.getY() + r.nextFloat() * 0.2 + 1, particlePos.getZ() + r.nextFloat() * 0.2 - 0.1, r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf);
                    if (x % 3 == 0)
                        l.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particlePos.getX() + r.nextFloat() * 0.2 - 0.1, particlePos.getY() + r.nextFloat() * 0.2 + 1, particlePos.getZ() + r.nextFloat() * 0.2 - 0.1, r.nextFloat() * speedmult * 2 - speedMultHalf,r.nextFloat() * speedmult / 2 - speedMultHalf / 2,r.nextFloat() * speedmult * 2 - speedMultHalf);
                }
                 //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
                entity.playSound(l.getBlockState(pos).getSoundType().getBreakSound(), 0.7f, 1f);
            }
        }
    }

    private static Consumer<Void> basicBlastResistanceConsumer() {
        return (blastResistance) -> {

        };
    }

        // Adds furioso attack stacks in a generic manner.
    private static StaticAnimation.Event[] genericFuriosoEvent(int amount) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.00F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, amount);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] oldBoysThreeEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.65F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();

            spawnBlockImpactParticle(entitypatch, 0, new Vector3d(0, -0.4, -0.5), 12, null, 0.1f);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] wheelsSmashEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 10);
            entity.playSound(TCorpSounds.WHEELS_IMPACT, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            Random r = new Random();
            LivingEntity entity = entitypatch.getOriginal();
            World l = entity.level;
            Vector3d pos = getArmaturePosition(entitypatch, 0, new Vector3d(0, -1, -1.8), 20, "Tool_R");
            BlockPos pos1 = new BlockPos(pos.x, pos.y, pos.z);
            if (!l.isEmptyBlock(pos1)) {
                wheelsSmashEffect(entity.level, pos1, entity);
                l.addParticle(TCorpParticleRegistry.WHEELS_IMPACT.get(), pos.x(), ((int)pos.y())+0.7, pos.z, 0,0,0);

                for (int x = 0; x < 20; x++) {
                    l.addParticle(new BlockParticleData(ParticleTypes.BLOCK, l.getBlockState(pos1)), pos.x() + ranBetween(-1, 1), ((int)pos.y())+ ranBetween(1, 2), pos.z() + ranBetween(-1, 1), ranBetween(-1, 1),ranBetween(-1, 1),ranBetween(-1, 1));
                    if (x % 3 == 0) {
                        l.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x() + ranBetween(-1, 1), ((int)pos.y()) + ranBetween(1, 2), pos.z() + ranBetween(-1, 1), ranBetween(-0.35f, 0.35f),ranBetween(-0.35f, 0.35f),ranBetween(-0.35f, 0.35f));
                    }
                }
            }





            //spawnBlockImpactParticle(entitypatch, 0, new Vector3d(0, -1, -1.8), 20, TCorpParticleRegistry.WHEELS_IMPACT.get(), 0.5f, new Vector3i(0,2.3f,0));
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    static Random r = new Random();
    private static float ranBetween(float min, float max) {
        return r.nextFloat()*(max-min) + min;
    }

    private static void wheelsSmashEffect(World world, BlockPos pos, LivingEntity entity) {
        if (world instanceof ServerWorld) {
            List<Entity> _entfound = world
                    .getNearbyEntities(LivingEntity.class,
                            EntityPredicate.DEFAULT, null, new AxisAlignedBB(pos.getX() - (3f), pos.getY() - (3f), pos.getZ() - (3f), pos.getX() + (3f), pos.getY() + (3f), pos.getZ() + (3f)))
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                        }
                    }.compareDistOf(pos.getX(), pos.getY(), pos.getZ())).collect(Collectors.toList());


            if (_entfound.size() > 0) {

                for (Entity e : _entfound) {
                    if (e instanceof LivingEntity) {

                        if (!(e.equals(entity))) {
                            LivingEntity living = (LivingEntity) e;

                            ((ServerWorld) world).addParticle(EpicFightParticles.BLOOD.get(), living.getX(), (living.getY() + living.getBbHeight() / 2), (living.getZ()),
                                    living.getBbWidth() / 2, living.getBbHeight() / 2, living.getBbWidth() / 2);


                            LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                            if (targetPatch != null) {

                                StaggerSystem.reduceStagger(living, 5, (n) -> {
                                    if (entity instanceof PlayerEntity)
                                        onStaggered((PlayerEntity) entity, living);
                                });

                                if (targetPatch.getCurrentLivingMotion().equals(LivingMotions.BLOCK)) {
                                    targetPatch.playSound(TCorpSounds.STAGGER, 1, -0.05F, 0.1F);

                                    if (entity instanceof PlayerEntity)
                                        living.hurt(DamageSource.playerAttack((PlayerEntity) entity), (float) 6);
                                    else
                                        living.hurt(DamageSource.mobAttack(entity), (float) 6);

                                    if (targetPatch instanceof PlayerPatch<?>) {
                                        PlayerPatch<?> playerPatch = (PlayerPatch<?>) targetPatch;
                                        playerPatch.setStamina(0);
                                    }

                                    if (targetPatch.getHitAnimation(StunType.LONG) == Animations.BIPED_HIT_LONG) {
                                        targetPatch.playAnimationSynchronized(TCorpAnimations.RANGA_GUARD_STAGGER, 0.0F);
                                    }
                                } else {
                                    if (entity instanceof PlayerEntity)
                                        living.hurt(DamageSource.playerAttack((PlayerEntity) entity), (float) 12);
                                    else
                                        living.hurt(DamageSource.mobAttack(entity), (float) 12);
                                    if (entity.hasEffect(FuriosoPotionEffect.potion.getEffect())) {
                                        System.out.println("Furioso active, doing minor attack.");
                                        if (targetPatch.getHitAnimation(StunType.LONG) == Animations.BIPED_HIT_LONG) {
                                            targetPatch.playAnimationSynchronized(TCorpAnimations.PUMMEL_DOWN, 0.0F);
                                        }
                                    } else {
                                        if (targetPatch.getHitAnimation(StunType.KNOCKDOWN) != null) {
                                            targetPatch.playAnimationSynchronized(targetPatch.getHitAnimation(StunType.KNOCKDOWN), 0f);
                                            targetPatch.knockBackEntity(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), 0f);
                                        }
                                    }

                                }


                            }
                            BlackSilenceEvaluator.addToStatistic(entity, 11, "furiosohits");
                        }


                    }
                }
            }
        }
    }

    private static StaticAnimation.Event[] crystalAtelierEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.05F, (entitypatch) -> {
                Entity entity = entitypatch.getOriginal();
                entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

                BlackSilenceEvaluator.furiosoAddAttackStat(entity, 10);
            }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] onrushEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] genericDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            if (entity.level instanceof ServerWorld) {
                ((ServerWorld)entity.level).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }

            entity.playSound(TCorpSounds.BLACK_SILENCE_EVADE, 1, 1);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] magicBulletDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            entity.playSound(TCorpSounds.BLACK_SILENCE_EVADE, 1, 1);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] rangaEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.23F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] furiosoSheatheEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }
            entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.iFrames = 15;
                capability.syncPlayerVariables(entity);
            });

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.playSound(EpicFightSounds.SWORD_IN, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] mookSheatheEvents(float time, int max) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }
            entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.iFrames = 15;
                capability.syncPlayerVariables(entity);
            });

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entity instanceof PlayerEntity) {
                PlayerPatch<?> playerPatch = (PlayerPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                int mookStacks = (int)entity.getPersistentData().getDouble("mookStacks");
                entity.getPersistentData().putDouble("mookStacks", 0);

                if (mookStacks > max)
                    mookStacks = max;

                World world = entity.level;
                if (mookStacks > 0) {
                    playerPatch.setStamina(playerPatch.getStamina() + playerPatch.getMaxStamina() * 0.08f * mookStacks);

                    if (mookStacks > 1 && entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new TcorpModVariables.PlayerVariables()).blips < 10) {
                        double _setval = Math.min(entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new TcorpModVariables.PlayerVariables()).blips + (double) (mookStacks - mookStacks % 2) / 2, 10.0);

                        //if (playerPatch.getOriginal().getCooldowns().isOnCooldown(SuitItem.helmet.getItem()))
                        //    playerPatch.getOriginal().getCooldowns().removeCooldown(SuitItem.helmet.getItem());

                        BlipTick.chargeBlips((PlayerEntity) entity, (double) (mookStacks - mookStacks % 2) / 2, true);
                        spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-0.3), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", true);

                    }
                }

            }


            entity.playSound(EpicFightSounds.SWORD_IN, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] mookFirstEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.getPersistentData().putDouble("mookStacks", 0);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

            private static StaticAnimation.Event[] mookCutEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 11);
            entity.playSound(TCorpSounds.BLACK_SILENCE_UNSHEATH, 1, 1f);

                }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.playSound(TCorpSounds.BLACK_SILENCE_MOOK_CUT, 1, 1f);
            entity.playSound(EpicFightSounds.WHOOSH, 1, 1.4f);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,0,-0.3), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", false);
            Vector3d pos = entity.position().add(entity.getLookAngle().multiply(3, 3, 3));

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (pos.x), (pos.y + 1), (pos.z),
                        30, 0.1, 0.1, 0.1, 0);
                Random r = new Random();
                ((ServerWorld) world).sendParticles(TCorpParticleRegistry.MOOK_JUDGEMENT_CUT.get(), (pos.x), (pos.y + 1), (pos.z),
                        1, 0, 0, 0, 0);


                List<Entity> _entfound = world
                        .getNearbyEntities(LivingEntity.class,
                                EntityPredicate.DEFAULT, null, new AxisAlignedBB(pos.x - (1.5f), pos.y - (1.5f), pos.z - (1.5f), pos.x + (1.5f), pos.y + (1.5f), pos.z + (1.5f)))
                        .stream().sorted(new Object() {
                            Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                                return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                            }
                        }.compareDistOf(pos.x, pos.y, pos.z)).collect(Collectors.toList());


                if (_entfound.size() > 0) {
                    Entity chosenEntity = _entfound.get(0);

                    if (chosenEntity instanceof LivingEntity && !(chosenEntity.equals(entity))) {
                        BlackSilenceEvaluator.addToStatistic(entity, 11, "furiosohits");

                        ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (chosenEntity.getX()), (chosenEntity.getY() + chosenEntity.getBbHeight()/2), (chosenEntity.getZ()),
                                15, chosenEntity.getBbWidth()/2, chosenEntity.getBbHeight()/2, chosenEntity.getBbWidth()/2, 0.3);

                        if (entity instanceof PlayerEntity)
                            chosenEntity.hurt(DamageSource.playerAttack((PlayerEntity) entity), (float) 20);
                        else
                            chosenEntity.hurt(DamageSource.mobAttack(entity), (float) 20);
                        chosenEntity.playSound(TCorpSounds.BLACK_SILENCE_MOOK_HIT, 1, 1f);
                        LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) chosenEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                        if (targetPatch != null) {
                            if (targetPatch.getHitAnimation(StunType.SHORT) != null) {
                                targetPatch.playAnimationSynchronized(targetPatch.getHitAnimation(StunType.SHORT), 1f);
                                targetPatch.knockBackEntity(pos, 1);
                            }
                        }

                    }
                }
            }







        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] furiosoSheatheEngage() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.0F, (entitypatch) -> {
            BlackSilenceEvaluator.furiosoAddAttackStat(entitypatch.getOriginal(), 4);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.55F, (entitypatch) -> {
            entitypatch.reserveAnimation(TCorpAnimations.DURANDAL_FURIOSO_SHEATH);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] mookSheatheEngage(float delay, boolean jumping) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(delay, (entitypatch) -> {
            if (jumping) {
                entitypatch.reserveAnimation(TCorpAnimations.MOOK_JUMP_SHEATH);
            } else {
                entitypatch.reserveAnimation(TCorpAnimations.MOOK_SHEATH);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    private static StaticAnimation.Event[] ranga2Events() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.23F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);
        }, StaticAnimation.Event.Side.CLIENT);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(RangadaggerItem.item));
        }, StaticAnimation.Event.Side.SERVER);

        return events;
    }

    private static StaticAnimation.Event[] rangaDaggerEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.3F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);

            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldowns().addCooldown(RangadaggerItem.item.asItem(), (int) 80);
                ((PlayerEntity) entity).getCooldowns().addCooldown(RangaclawlItem.block.asItem(), (int) 80);
                ((PlayerEntity) entity).getCooldowns().addCooldown(RangaclawItem.item.asItem(), (int) 80);
            }

        }, StaticAnimation.Event.Side.CLIENT);

        events[1] = StaticAnimation.Event.create(1.5F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entity.getItemInHand(Hand.MAIN_HAND).equals(new ItemStack(RangadaggerItem.item), false))
                entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(RangaclawItem.item));
        }, StaticAnimation.Event.Side.BOTH);





        return events;
    }

    private static StaticAnimation.Event[] rangaDaggerShort() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0.3F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entitypatch.getOriginal().level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 4);
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).getCooldowns().addCooldown(RangadaggerItem.item.getItem(), (int) 80);
                ((PlayerEntity) entity).getCooldowns().addCooldown(RangaclawlItem.block.getItem(), (int) 80);
                ((PlayerEntity) entity).getCooldowns().addCooldown(RangaclawItem.item.getItem(), (int) 80);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.8F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entity.getItemInHand(Hand.MAIN_HAND).equals(new ItemStack(RangadaggerItem.item), false))
                entity.setItemInHand(Hand.MAIN_HAND, new ItemStack(RangaclawItem.item));
        }, StaticAnimation.Event.Side.BOTH);





        return events;
    }

    private static StaticAnimation.Event[] atelierDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }

            entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.iFrames = 15;
                capability.syncPlayerVariables(entity);
            });

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        15, 0.2, 0.5, 0.2, 0);
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(0.8F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        10, 0.2, 0.5, 0.2, 0);
            }
        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] atelierRevolver1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.6F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            ServerWorld serverWorld = null;
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 5);
            if (!world.isClientSide())
                serverWorld = (ServerWorld) world;


            Entity _shootFrom = entity;
            World projectileLevel = _shootFrom.level;
            if (!projectileLevel.isClientSide()) {
                ProjectileEntity _entityToSpawn = new Object() {
                    public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
                        AbstractArrowEntity entityToSpawn = new AtelierpistolsItem.ArrowCustomEntity(AtelierpistolsItem.pistol_bullet, world);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);

                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, entity, 1, 1);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:blacksilence.atelier.revolver")),
                        SoundCategory.NEUTRAL, (float) 0.3, (float) 1);
            }
            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);
            AtelierCooldownHandler.executePistol(world, entity, entity.getX(), entity.getY(), entity.getZ());

            }, StaticAnimation.Event.Side.BOTH);

            return events;
    }

    private static StaticAnimation.Event[] atelierRevolver2Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            ServerWorld serverWorld = null;
            if (!world.isClientSide())
                serverWorld = (ServerWorld) world;
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 5);
            Entity _shootFrom = entity;
            World projectileLevel = _shootFrom.level;
            if (!projectileLevel.isClientSide()) {
                ProjectileEntity _entityToSpawn = new Object() {
                    public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
                        AbstractArrowEntity entityToSpawn = new AtelierpistolsItem.ArrowCustomEntity(AtelierpistolsItem.pistol_bullet, world);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);

                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, entity, 1, 1);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        TCorpSounds.BLACK_SILENCE_ATELIER_REVOLVER,
                        SoundCategory.NEUTRAL, (float) 0.3, (float) 1);
            }

            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", false);
            AtelierCooldownHandler.executePistol(world, entity, entity.getX(), entity.getY(), entity.getZ());

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] atelierShotgunEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.55F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            ServerWorld serverWorld = null;
            if (!world.isClientSide())
                serverWorld = (ServerWorld) world;
            Entity _shootFrom = entity;
            World projectileLevel = _shootFrom.level;
            if (!projectileLevel.isClientSide()) {
                ProjectileEntity _entityToSpawn = new Object() {
                    public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
                        AbstractArrowEntity entityToSpawn = new AteliershotgunItem.ArrowCustomEntity(AteliershotgunItem.shotgun_slug, world);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);

                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, entity, 2f, 1);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("blacksilence.atelier.shotgun")),
                        SoundCategory.NEUTRAL, (float) 0.3, (float) 1);
            }

            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.1,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", false);
            AtelierCooldownHandler.executeShotgun(world, entity, entity.getX(), entity.getY(), entity.getZ());
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 20);

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
                        (stage == 1) ? TCorpSounds.MAGIC_BULLET_SWING_1 : TCorpSounds.MAGIC_BULLET_SWING_2,
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
                        TCorpSounds.MAGIC_BULLET_SPIN_SWING,
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
                        TCorpSounds.MAGIC_BULLET_SPIN_SPAM,
                        SoundCategory.PLAYERS, (float) 1.5, (float) 1);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.5F, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        TCorpSounds.MAGIC_BULLET_SPIN_DETONATE,
                        SoundCategory.PLAYERS, (float) 1.5, (float) 1);
            }

            if (!entity.hasEffect(MagicBulletPotionEffect.get()) || entity.getEffect(MagicBulletPotionEffect.get()).getAmplifier() < 3) {
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.8f, "Tool_R", false);
                MagicBulletPotionEffect.increaseMagicBullet(entity);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] magicBulletFire1Event() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];

        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            MagicBulletPotionEffect.increaseMagicBullet(entitypatch.getOriginal());

            int ampl = 1;

            if (entity.hasEffect(MagicBulletPotionEffect.get())) {
                ampl = entity.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 1;
            }

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            if (ampl >= 3)
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);



        events[2] = StaticAnimation.Event.create(1.37F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = 1;

            if (entity.hasEffect(MagicBulletPotionEffect.get())) {
                ampl = entity.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 1;
            }

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);


            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                    MagicBulletProjectile.shoot(entity, ampl-1, false);
                } else {
                    ampl--;
                    MagicBulletProjectile.shoot(entity, ampl-1, true);
                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(TCorpParticleRegistry.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }
                    entity.hurt(DamageSource.playerAttack((PlayerEntity) entity).setMagic(), (7 + ampl) * 1.8f);
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
            MagicBulletPotionEffect.increaseMagicBullet(entitypatch.getOriginal());

            int ampl = 1;

            if (entity.hasEffect(MagicBulletPotionEffect.get())) {
                ampl = entity.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 1;
            }

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            if (ampl >= 6)
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);

        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(1.28F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
                    TCorpSounds.MAGIC_BULLET_FIRE_SWING,
                    SoundCategory.PLAYERS, 2, 1f / (new Random().nextFloat() * 0.5f + 1));
        }, StaticAnimation.Event.Side.BOTH);
        events[3] = StaticAnimation.Event.create(2.08F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = 1;

            if (entity.hasEffect(MagicBulletPotionEffect.get())) {
                ampl = entity.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 1;
            }

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);

            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                    MagicBulletProjectile.shoot(entity, ampl-1, false);
                } else {
                    ampl--;
                    MagicBulletProjectile.shoot(entity, ampl-1, true);
                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(TCorpParticleRegistry.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }
                    entity.hurt(DamageSource.playerAttack((PlayerEntity) entity).setMagic(), (7 + ampl) * 1.8f);
                }
            } else {
                MagicBulletProjectile.shoot(entity, ampl-1, false);
            }

            if (ampl >= 7) {
                entity.removeEffect(MagicBulletPotionEffect.get());

                if (entitypatch instanceof PlayerPatch<?>) {
                    PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;

                    playerPatch.setStamina(Math.min(playerPatch.getStamina() + 1,playerPatch.getMaxStamina()));
                }
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
}
