package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

public class RatPipeMovesetAnims {
    public static StaticAnimation RAT_PIPE_IDLE;
    public static StaticAnimation RAT_PIPE_WALK;
    public static StaticAnimation RAT_PIPE_RUN;
    public static StaticAnimation RAT_PIPE_AUTO_1;
    public static StaticAnimation RAT_PIPE_AUTO_2;
    public static StaticAnimation RAT_PIPE_AUTO_3;
    public static StaticAnimation RAT_PIPE_GUARD;
    public static StaticAnimation RAT_PIPE_GUARD_HIT;
    public static StaticAnimation RAT_PIPE_GUARD_BREAK;

    public static StaticAnimation RAT_PIPE_JUMP_ATTACK;
    public static StaticAnimation RAT_PIPE_DASH;
    public static StaticAnimation RAT_PIPE_SPECIAL_1;
    public static StaticAnimation RAT_PIPE_SPECIAL_2;
    public static StaticAnimation RAT_PIPE_SPECIAL_3;
    public static StaticAnimation RAT_PIPE_INNATE;

    public static StaticAnimation RAT_PIPE_EQUIP;

    public static void build(Model biped) {
        System.out.println("Building RAT_PIPE Animations");
        RAT_PIPE_IDLE = new StaticAnimation(true, "biped/rat_pipe/idle", biped);
        RAT_PIPE_WALK = new MovementAnimation(true, "biped/rat_pipe/walk", biped);
        RAT_PIPE_RUN = new MovementAnimation(true, "biped/rat_pipe/run", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);

        RAT_PIPE_DASH = new EgoAttackAnimation(0.08F, 0.2F, 0.5F, 1F, 1.5F, null, "Leg_R", "biped/rat_pipe/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);

        RAT_PIPE_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/rat_pipe/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.66f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.5f);


        RAT_PIPE_SPECIAL_1 = new EgoAttackAnimation(0.08F, 0.4F, 0.5F, 0.8F, 2f, null, "Tool_R", "biped/rat_pipe/special_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_sp_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(3f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ratPipeFollowup(1f, false));

        RAT_PIPE_SPECIAL_2 = new EgoAttackAnimation(0.02F, 0.2F, 0.65F, 0.8F, 2F, null, "Tool_R", "biped/rat_pipe/special_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_sp_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HEAVY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, ratPipeFollowup(1.1333333f, true));



        RAT_PIPE_SPECIAL_3 = new EgoAttackAnimation(0.02F, 0.2F, 0.66F, 1F, 2.2F, EgoWeaponsCapabilityPresets.FSTL_BLADE, "Tool_R", "biped/rat_pipe/special_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_sp_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HEAVY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, finalEffect());


        RAT_PIPE_INNATE = new EgoAttackAnimation(0.08F, 0.3F, 0.66F, 1.2F, 1.5F, null, "Tool_R", "biped/rat_pipe/innate", biped)
                .addProperty(EgoWeaponsAttackProperty.CLASH_KNOCK, false)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HEAVY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackAnimationProperty.COLLIDER_ADDER, 5)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.3f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);


        RAT_PIPE_AUTO_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.5F, 0.9F, 1.3F, null, "Tool_R", "biped/rat_pipe/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);

        RAT_PIPE_JUMP_ATTACK = new BasicEgoAttackAnimation(0.02F, 0.2F, 0.33F, 0.6F, 1.3F, null, "Tool_R", "biped/rat_pipe/jump_attack", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_jump")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);


        RAT_PIPE_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.5F, 0.75F, 0.95f, null, "Tool_R", "biped/rat_pipe/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);

        RAT_PIPE_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.5f, 0.75F, 1.33f, null, "Tool_R", "biped/rat_pipe/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.BLUNT)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "rat_pipe_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.RAT_PIPE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.RAT_PIPE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);

        RAT_PIPE_GUARD = new StaticAnimation( true, "biped/rat_pipe/guard", biped);
        RAT_PIPE_GUARD_HIT = new GuardAnimation(0.05f,0.1f, "biped/rat_pipe/guard_hit", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        RAT_PIPE_GUARD_BREAK = new LongHitAnimation(0.05f, "biped/rat_pipe/guard_break",biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.3f);

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

    private static StaticAnimation.Event[] finalEffect() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0.05f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (entity.level.isClientSide())
                entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_CHAIN, 1, 1, 1);
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1, 1);
            DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.rat_pipe.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

            TremorEffect.decrementTremor(entity, 5, 0);


        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    public static StaticAnimation.Event[] equipEffect(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
            entitypatch.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    private static StaticAnimation.Event[] ratPipeFollowup(float time, boolean second) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("followUpHit") > 0) {
                if (!second) {

                    entitypatch.playAnimationSynchronized(RAT_PIPE_SPECIAL_2, 0);
                } else {

                    TremorEffect tremor = TremorEffect.detectTremorType(entity);

                    int count = tremor != null ? tremor.getCount(entity) : 0;

                    if (count >= 5) {


                        entitypatch.playAnimationSynchronized(RAT_PIPE_SPECIAL_3, 0);
                    }

                }
            }


        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }



}
