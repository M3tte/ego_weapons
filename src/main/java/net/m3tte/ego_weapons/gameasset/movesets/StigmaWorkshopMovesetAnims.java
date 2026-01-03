package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

import static net.m3tte.ego_weapons.item.firefist.FirefistGauntlet.firefistFinalHitEvent;
import static net.m3tte.ego_weapons.item.firefist.FirefistGauntlet.firefistHitEvent;
import static net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword.*;

public class StigmaWorkshopMovesetAnims {
    public static StaticAnimation STIGMA_SWORD_IDLE;
    public static StaticAnimation STIGMA_SWORD_WALK;
    public static StaticAnimation STIGMA_SWORD_RUN;
    public static StaticAnimation STIGMA_SWORD_AUTO_1;
    public static StaticAnimation STIGMA_SWORD_AUTO_2;
    public static StaticAnimation STIGMA_SWORD_AUTO_3;
    public static StaticAnimation STIGMA_SWORD_GUARD;
    public static StaticAnimation STIGMA_SWORD_GUARD_HIT;
    public static StaticAnimation STIGMA_SWORD_PARRY_1;
    public static StaticAnimation STIGMA_SWORD_PARRY_2;
    public static StaticAnimation STIGMA_SWORD_EVADE;
    public static StaticAnimation STIGMA_SWORD_KNEEL;

    public static StaticAnimation STIGMA_SWORD_SNEAK;
    public static StaticAnimation STIGMA_SWORD_JUMP;
    public static StaticAnimation STIGMA_SWORD_DASH;
    public static StaticAnimation STIGMA_SWORD_SPECIAL_1;
    public static StaticAnimation STIGMA_SWORD_SPECIAL_1B;
    public static StaticAnimation STIGMA_SWORD_SPECIAL_2;
    public static StaticAnimation STIGMA_SWORD_INNATE_1;
    public static StaticAnimation STIGMA_SWORD_INNATE_1E;
    public static StaticAnimation STIGMA_SWORD_INNATE_2;
    public static StaticAnimation STIGMA_SWORD_INNATE_2E;
    public static StaticAnimation RUEFUL_EVENTIDE;
    public static StaticAnimation STIGMA_SWORD_INNATE_3;
    public static StaticAnimation STIGMA_SWORD_EQUIP;
    public static void build(Model biped) {
        System.out.println("Building STIGMA_SWORD Animations");

        STIGMA_SWORD_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/stigma_w_s/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, equipEffect(0.6f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        STIGMA_SWORD_IDLE = new StaticAnimation(true, "biped/stigma_w_s/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        STIGMA_SWORD_WALK = new MovementAnimation(true, "biped/stigma_w_s/walk", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        STIGMA_SWORD_RUN = new MovementAnimation(true, "biped/stigma_w_s/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        STIGMA_SWORD_KNEEL = new StaticAnimation(true, "biped/stigma_w_s/kneel", biped);
        STIGMA_SWORD_SNEAK = new MovementAnimation(true, "biped/stigma_w_s/sneak", biped);
        STIGMA_SWORD_JUMP = new StaticAnimation(false, "biped/stigma_w_s/jump", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());

        STIGMA_SWORD_DASH = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.25F, 0.65F, 1.4F, null, "Tool_R", "biped/stigma_w_s/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        STIGMA_SWORD_SPECIAL_1B = new BasicEgoAttackAnimation(0.08F, 0.5F, 0.8F, 1.22F, 1.33F, null, "Tool_R", "biped/stigma_w_s/special_1b", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_sp_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, stigmatizeEventsB(1.2f));

        STIGMA_SWORD_SPECIAL_1 = new BasicEgoAttackAnimation(0.08F, 1F, 2F, 2.25F, 2.35F, null, "Tool_R", "biped/stigma_w_s/special_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_sp_1")
                .addProperty(EgoWeaponsAttackProperty.DEATH_MESSAGE, "stigma_sword_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, stigmatizeEvents(2.35f));

        STIGMA_SWORD_SPECIAL_2 = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.33F, 0.88F, 1.33F, null, "Tool_R", "biped/stigma_w_s/special_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_sp_2")
                .addProperty(EgoWeaponsAttackProperty.DEATH_MESSAGE, "stigma_sword_special")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, stigmatizeEvents2());


        STIGMA_SWORD_INNATE_1 = new EgoAttackAnimation(0.08F, 0.3F, 0.4F, 0.66F, 0.8F, null, "Tool_R", "biped/stigma_w_s/innate_1", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_innate_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        STIGMA_SWORD_INNATE_1E = new ActionAnimation(0.01f, "biped/stigma_w_s/innate_1e", biped);
        STIGMA_SWORD_INNATE_2E = new ActionAnimation(0.01f, "biped/stigma_w_s/innate_2e", biped);
        RUEFUL_EVENTIDE = new ActionAnimation(0.01f, "biped/stigma_w_s/rueful_eventide", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ruefulEventideEvent());

        STIGMA_SWORD_INNATE_2 = new EgoAttackAnimation(0.02F, 0.45F, 0.33F, 0.5F, 0.66F, null, "Tool_R", "biped/stigma_w_s/innate_2", biped)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_innate_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        STIGMA_SWORD_INNATE_3 = new EgoAttackAnimation(0.02F, 0.45F, 0.25F, 0.6F, 1.33F, null, "Tool_R", "biped/stigma_w_s/innate_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_innate_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        STIGMA_SWORD_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 0.92F, null, "Tool_R", "biped/stigma_w_s/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);

        STIGMA_SWORD_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.25F, 0.5F, 0.75F, null, "Tool_R", "biped/stigma_w_s/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        STIGMA_SWORD_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.33F, 0.66F, 1.4F, null, "Tool_R", "biped/stigma_w_s/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "stigma_w_s_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);

        STIGMA_SWORD_GUARD = new StaticAnimation( true, "biped/stigma_w_s/guard", biped);
        STIGMA_SWORD_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/stigma_w_s/guard_hit", biped);
        STIGMA_SWORD_PARRY_1 = new GuardAnimation(0.05f,0.3f, "biped/stigma_w_s/guard_parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        STIGMA_SWORD_PARRY_2 = new GuardAnimation(0.05f,0.3f, "biped/stigma_w_s/guard_parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        STIGMA_SWORD_EVADE = new DodgeAnimation(0.05f,0.5f, "biped/stigma_w_s/guard_evade",0.5f, 0.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, stigmaGuardEvadeEvent());

        /*STIGMA_SWORD_INNATE = new EgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, EgoWeaponsCapabilityPresets.FirefistSpew, "Tool_R", "biped/firefist/innate", biped)
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

    private static StaticAnimation.Event[] stigmaGuardEvadeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            if (world.isClientSide()){
                entitypatch.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);
            }

        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    public static StaticAnimation.Event[] equipEffect(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1, 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
            entitypatch.playSound(EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_IGNITE, 1, 1, 1);
            if (!entitypatch.getOriginal().level.isClientSide) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entitypatch.getOriginal().getId(), entitypatch.getOriginal().getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
            }
        }, StaticAnimation.Event.Side.SERVER);


        return events;
    }

    public static StaticAnimation.Event[] ruefulEventideEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_HIT,
                    SoundCategory.PLAYERS, (float) 2, (float) 1);
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(1.3f, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_SPECIAL_IGNITE,
                    SoundCategory.PLAYERS, (float) 2, (float) 1);
            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(entitypatch.getOriginal(), 0, 3);

            entitypatch.getOriginal().setAbsorptionAmount(Math.min(entitypatch.getOriginal().getAbsorptionAmount() + 4, 8));

            if (entitypatch.getOriginal() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();

                SanitySystem.damageSanity(player, (float) (EgoWeaponsAttributes.getMaxSanity(player) * 0.25f));
                double sanity = SanitySystem.getSanity(player);

                if (sanity <= EgoWeaponsAttributes.getMaxSanity(player) * 0.3f) {
                    EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(entitypatch.getOriginal(), 0, 5);
                    EgoWeaponsEffects.POWER_UP.get().increment(entitypatch.getOriginal(), 0, 2);
                }

                if (!entitypatch.getOriginal().level.isClientSide)
                    EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 14, player.getX(), player.getY() + player.getBbHeight()/2, player.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));

            }


        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
}
