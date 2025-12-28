package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoAttackPhase;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.EgoWeaponsEffects.extendEffect;

public class HeishouMaoBranchAnims {
    public static StaticAnimation HEISHOU_MAO_IDLE;
    public static StaticAnimation HEISHOU_MAO_WALK;
    public static StaticAnimation HEISHOU_MAO_RUN;
    public static StaticAnimation HEISHOU_MAO_AUTO_1;
    public static StaticAnimation HEISHOU_MAO_AUTO_2;
    public static StaticAnimation HEISHOU_MAO_AUTO_3;
    public static StaticAnimation HEISHOU_MAO_AUTO_4;
    public static StaticAnimation HEISHOU_MAO_AUTO_JUMP;
    public static StaticAnimation HEISHOU_MAO_GUARD;
    public static StaticAnimation HEISHOU_MAO_GUARD_HIT;
    public static StaticAnimation HEISHOU_MAO_PARRY_1;
    public static StaticAnimation HEISHOU_MAO_PARRY_2;
    public static StaticAnimation HEISHOU_MAO_PARRY_3;
    public static StaticAnimation HEISHOU_MAO_KNEEL;

    public static StaticAnimation HEISHOU_MAO_SNEAK;
    public static StaticAnimation HEISHOU_MAO_JUMP;
    public static StaticAnimation HEISHOU_MAO_DASH;
    public static StaticAnimation HEISHOU_MAO_INNATE_1;
    public static StaticAnimation HEISHOU_MAO_INNATE_2;
    public static StaticAnimation HEISHOU_MAO_INNATE_3;
    public static StaticAnimation HEISHOU_MAO_INNATE_1E;
    public static StaticAnimation HEISHOU_MAO_INNATE_2E;
    public static StaticAnimation HEISHOU_MAO_STRIDER_MAO;
    public static StaticAnimation HEISHOU_MAO_PARRY_EVADE;
    public static StaticAnimation HEISHOU_MAO_PARRY_ATTACK;
    public static StaticAnimation HEISHOU_MAO_SPECIAL_1;
    public static StaticAnimation HEISHOU_MAO_SPECIAL_2;
    public static StaticAnimation HEISHOU_MAO_EQUIP;

    public static void build(Model biped) {
        System.out.println("Building HEISHOU_MAO Animations");
        HEISHOU_MAO_IDLE = new StaticAnimation(true, "biped/heishou_mao/idle", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        HEISHOU_MAO_WALK = new MovementAnimation(true, "biped/heishou_mao/walk", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        HEISHOU_MAO_RUN = new MovementAnimation(true, "biped/heishou_mao/run", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());
        HEISHOU_MAO_KNEEL = new StaticAnimation(true, "biped/heishou_mao/kneel", biped);
        HEISHOU_MAO_SNEAK = new MovementAnimation(true, "biped/heishou_mao/sneak", biped);
        //HEISHOU_MAO_JUMP = new StaticAnimation(false, "biped/heishou_mao/jump", biped)
        //        .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, StigmaWorkshopSword.bladeCheckEvent());

        /*HEISHOU_MAO_DASH = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.25F, 0.65F, 1.4F, null, "Tool_R", "biped/heishou_mao/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.RED)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.FIREFIST_AUTO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(2f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.STIGMA_WORKSHOP_SWORD_DASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);*/

        HEISHOU_MAO_SPECIAL_1 = new EgoAttackAnimation(0.08F, 0.45F, 0.66F, 1F, 2.3F, null, "Tool_R", "biped/heishou_mao/special_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_special_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.65F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialEvent1());

        HEISHOU_MAO_SPECIAL_2 = (new EgoAttackAnimation(0.01F, "biped/heishou_mao/special_2", biped,
                new EgoAttackPhase(0.0F, 0.5F, 0.75F, 1F, 1.12F, 1.12F, "Tool_R", EgoWeaponsCapabilityPresets.CURSEWRIT_BUTCHERBLADE)
                        .addProperty(EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "heishou_mao_special_2")
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_SPIN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT),
                new EgoAttackPhase(1.12F, 1.12F, 1.15F, 1.33F, 1.4F, 1.4F, "Tool_R", EgoWeaponsCapabilityPresets.CURSEWRIT_BUTCHERBLADE)
                        .addProperty(EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "heishou_mao_special_3")
                        .addProperty(EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT),
                new EgoAttackPhase(1.4F, 1.42f, 1.44F, 1.65F, 1.9F, 1.9F, "Tool_R", EgoWeaponsCapabilityPresets.CURSEWRIT_BUTCHERBLADE)
                        .addProperty(EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "heishou_mao_special_4")
                        .addProperty(EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SWING)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT),
                new EgoAttackPhase(1.9f, 2.4f, 2.76f, 3.1f, 5f, 5f, "Torso", EgoWeaponsCapabilityPresets.LARGE_CUBE)
                        .addProperty(EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER, "heishou_mao_special_fin")
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.4f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_DASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_DASH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)

        )
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "firefist_innate_a")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, specialEvent2()));

        HEISHOU_MAO_INNATE_1 = new EgoAttackAnimation(0.08F, 0.2F, 0.65F, 1.05F, 1.4F, EgoWeaponsCapabilityPresets.SUNSHOWER_COL_LARGE, "Torso", "biped/heishou_mao/innate_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_innate_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateAttack(0.1f, 0.7f, true));

        HEISHOU_MAO_INNATE_2 = new EgoAttackAnimation(0.08F, 0.2F, 0.5F, 1F, 1.2F, EgoWeaponsCapabilityPresets.SUNSHOWER_COL_LARGE, "Torso", "biped/heishou_mao/innate_2b", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_innate_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateAttack(0.1f, 0.55f, true));

        HEISHOU_MAO_INNATE_3 = new EgoAttackAnimation(0.08F, 0.2F, 0.43F, 0.92F, 1.3F, EgoWeaponsCapabilityPresets.SUNSHOWER_COL_LARGE, "Torso", "biped/heishou_mao/innate_3b", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_innate_3")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateAttack(0.1f, 0.3f, true));

        HEISHOU_MAO_INNATE_1E = new ActionAnimation(0.01f, "biped/heishou_mao/innate_1e", biped);
        HEISHOU_MAO_INNATE_2E = new ActionAnimation(0.01f, "biped/heishou_mao/innate_2e", biped);

        HEISHOU_MAO_STRIDER_MAO = new ActionAnimation(0.01f, "biped/heishou_mao/trigger_strider", biped).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, triggerStriderMao()).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);

        HEISHOU_MAO_DASH = new BasicEgoAttackAnimation(0.08F, 0.2F, 0.15F, 0.6F, 1.5F, null, "Tool_R", "biped/heishou_mao/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_DASH)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.25F);

        HEISHOU_MAO_EQUIP = (new ActionAnimation(0f, 1.5f,   "biped/heishou_mao/equip", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, RatPipeMovesetAnims.equipEffect(0.6f))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);


        HEISHOU_MAO_AUTO_1 = new BasicEgoAttackAnimation(0.08F, 0.45F, 0.9F, 1.2F, 1.4F, null, "Tool_R", "biped/heishou_mao/auto_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_auto1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.65F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, auto1Swing());

        HEISHOU_MAO_AUTO_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.33F, 0.66F, 0.85F, null, "Tool_R", "biped/heishou_mao/auto_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_auto2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.HEISHOU_MAO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F);

        HEISHOU_MAO_AUTO_3 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.33F, 0.55F, 2F, null, "Tool_R", "biped/heishou_mao/auto_3", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_auto3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, auto3Swing());

        HEISHOU_MAO_AUTO_4 = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.5F, 0.66F, 1.5F, null, "Tool_R", "biped/heishou_mao/auto_4", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_auto4")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HEAVY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateAttack(0.1f, 0.4f, false));

        HEISHOU_MAO_AUTO_JUMP = new BasicEgoAttackAnimation(0.01F, 0.08F, 0.5F, 0.66F, 1.5F, null, "Tool_R", "biped/heishou_mao/auto_4", biped)
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_jump")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_HEAVY_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(1.5f))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateAttack(0.1f, 0.4f, false));


        HEISHOU_MAO_GUARD = new StaticAnimation( true, "biped/heishou_mao/guard", biped);
        HEISHOU_MAO_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/heishou_mao/guard_hit", biped);
        HEISHOU_MAO_PARRY_1 = new GuardAnimation(0.05f,0.5f, "biped/heishou_mao/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        HEISHOU_MAO_PARRY_2 = new DodgeAnimation(0.05f,0.4f, "biped/heishou_mao/parry_2",0.5f, 0.8f, biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f).addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, heishouGuardEvadeEvent());
        HEISHOU_MAO_PARRY_3 = new GuardAnimation(0.05f,0.5f, "biped/heishou_mao/parry_3", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        HEISHOU_MAO_PARRY_EVADE = new DodgeAnimation(0.01F, "biped/heishou_mao/counter_1", 0.5f, 1f,  biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, counterEvent1());

        HEISHOU_MAO_PARRY_ATTACK = new BasicEgoAttackAnimation(0.08F, 0.05F, 0.2F, 0.5F, 1.55F, null, "Tool_R", "biped/heishou_mao/counter_2", biped)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "heishou_mao_counter")
                .addProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO, true)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.HEISHOU_MAO_PARRY)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.STIGMA_WORKSHOP_SWORD_AUTO_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(2))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(4f))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.7f))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.MAO_BRANCH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.4F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, counterEvent2());


        /*HEISHOU_MAO_INNATE = new EgoAttackAnimation(0.08F, 0.45F, 0.5F, 0.7F, 1.5F, EgoWeaponsCapabilityPresets.FirefistSpew, "Tool_R", "biped/firefist/innate", biped)
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

    private static StaticAnimation.Event[] heishouGuardEvadeEvent() {
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

    private static StaticAnimation.Event[] auto1Swing() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().remove("active");

            if (world.isClientSide()){
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_CIRC_SWING, 1, 1);
            }

        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    private static StaticAnimation.Event[] auto3Swing() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(0.85F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()){
                if (EgoWeaponsEffects.speedMult(entity) >= 5) {
                    entitypatch.playAnimationSynchronized(HEISHOU_MAO_AUTO_4 ,0);
                }
            }

        }, StaticAnimation.Event.Side.BOTH);



        return events;
    }

    public static StaticAnimation.Event[] specialEvent1() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0.66f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            extendEffect(entity, EgoWeaponsEffects.SPEED_UP.get(), 40);
            extendEffect(entity, EgoWeaponsEffects.SPEED_DOWN.get(), 40);

            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(1.25f, (entitypatch) -> {
            entitypatch.playAnimationSynchronized(HEISHOU_MAO_SPECIAL_2, 0);
        }, StaticAnimation.Event.Side.SERVER);
        return events;
    }


    public static StaticAnimation.Event[] specialEvent2() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[7];
        events[0] = StaticAnimation.Event.create(0.3f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().putInt("active", 1);
            if (!entity.level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_JUMP, 1, 1);
            }

            entity.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0));
            extendEffect(entity, EgoWeaponsEffects.SPEED_UP.get(), 100);
            extendEffect(entity, EgoWeaponsEffects.SPEED_DOWN.get(), 100);

            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.25, 0));
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.75f, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("lastTargeted");
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, -0.05, 0));
            if (!entity.level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_SPIN, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(1f, (entitypatch) -> {

            LivingEntity entity = entitypatch.getOriginal();
            if (entity.level.isClientSide()) {
                entity.level.addParticle(EgoWeaponsParticles.MAO_BRANCH_CIRCULAR_HIT.get(), entity.getX(), entity.getY() + 1.5, entity.getZ(), 0, entity.getId(), entity.getId());
            }
        }, StaticAnimation.Event.Side.CLIENT);

        events[3] = StaticAnimation.Event.create(2.8f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            entity.addEffect(new EffectInstance(Effects.SLOW_FALLING, 40, 0));
            if (!entity.level.isClientSide()) {
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_JUMP, 1, 1);
                entitypatch.playSound(EpicFightSounds.FAST_MOVE, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(3.2f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("lastTargeted") && EgoWeaponsEffects.speedMult(entity) >= 7) {
                Entity targetEntity = entity.level.getEntity(entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("lastTargeted"));

                if (targetEntity instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) targetEntity;

                    if (entity.level.isClientSide()) {
                        entity.level.addParticle(EgoWeaponsParticles.MAO_BRANCH_REUSE.get(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0, 0, 0);
                    }

                    DialogueSystem.speakEvalDialogue(entity, "dialogue.ego_weapons.skills.mao_branch_sword.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.YELLOW);

                    target.playSound(EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_REUSE_HIT, 2, 1);
                }
            }




        }, StaticAnimation.Event.Side.BOTH);

        events[5] = StaticAnimation.Event.create(3.8f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            if (entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("lastTargeted") && EgoWeaponsEffects.speedMult(entity) >= 7) {
                Entity targetEntity = entity.level.getEntity(entity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("lastTargeted"));

                if (targetEntity instanceof LivingEntity) {
                    LivingEntity target = (LivingEntity) targetEntity;
                    target.playSound(EgoWeaponsSounds.HEISHOU_MAO_HIT, 2, 1);
                    target.playSound(EgoWeaponsSounds.HEISHOU_MAO_SPECIAL_VOICELINE, 3, 1);
                    target.hurt(new DirectEgoDamageSource("", entity, ExtendedDamageSource.StunType.KNOCKDOWN, HEISHOU_MAO_SPECIAL_2, AttackTypes.SLASH, DamageTypes.PALE, "mao_branch_sword"), 12);

                    EgoWeaponsEffects.DEATHRITE_HASTE.get().increment(target, 3, 3);

                    if (!entity.level.isClientSide()) {
                        ((ServerWorld)entity.level).sendParticles(EgoWeaponsParticles.MAO_BRANCH_STRIKE.get(), targetEntity.getX(), targetEntity.getY() + 1, targetEntity.getZ(), 1, 0, 0, 0, 0);
                        ((ServerWorld)entity.level).sendParticles(EgoWeaponsParticles.SUNSHOWER_OPEN.get(), targetEntity.getX(), targetEntity.getY() + 1, targetEntity.getZ(), 1, 0, 0, 0, 0);
                    }
                }

                EgoWeaponsEffects.STRIDER_MAO.get().increment(entity, 3, 2);
            }
        }, StaticAnimation.Event.Side.BOTH);

        events[6] = StaticAnimation.Event.create(4.2f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().remove("active");
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
    public static StaticAnimation.Event[] counterEvent1() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];
        events[0] = StaticAnimation.Event.create(0f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            if (world.isClientSide()){
                entitypatch.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(0.4f, (entitypatch) -> {
            entitypatch.playAnimationSynchronized(HEISHOU_MAO_PARRY_ATTACK, 0);
        }, StaticAnimation.Event.Side.SERVER);
        return events;
    }

    public static StaticAnimation.Event[] counterEvent2() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.75f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world.isClientSide()){
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_DASH, 1, 1);
            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] triggerStriderMao() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.3f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            EgoWeaponsEffects.STRIDER_MAO.get().increment(entity, 3, 3);
            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(entity, 0, 3);
            EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(entity, 0, 3);
            entity.addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 30, 0));
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
    private static StaticAnimation.Event[] innateAttack(float primeTime, float dashTime, boolean postSound) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(primeTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            extendEffect(entity, EgoWeaponsEffects.SPEED_UP.get(), (int)(20 * (dashTime + 0.5f)));
            extendEffect(entity, EgoWeaponsEffects.SPEED_DOWN.get(), (int)(20 * (dashTime + 0.5f)));


            if (world.isClientSide()){
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_DASH, 1, 1);
            }

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(dashTime, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            if (world.isClientSide() && postSound){
                entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_DASH_SLASH, 1, 1);
            }

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }
}
