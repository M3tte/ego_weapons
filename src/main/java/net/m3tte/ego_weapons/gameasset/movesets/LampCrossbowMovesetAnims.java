package net.m3tte.ego_weapons.gameasset.movesets;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.abilities.armorAbilities.ArdorBlossomArmorAbility;
import net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities.ArdorBlossomBatWeaponAbility;
import net.m3tte.ego_weapons.item.ardor_blossom.ArdorBlossomSuit;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.animation.types.GuardAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;

import static net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities.ArdorBlossomBatWeaponAbility.castOverclockExplosion;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.basicSwingEvent;

public class LampCrossbowMovesetAnims {
    public static StaticAnimation LAMP_CB_IDLE;
    public static StaticAnimation LAMP_CB_WALK;
    public static StaticAnimation LAMP_CB_RUN;
    public static StaticAnimation LAMP_CB_KNEEL;
    public static StaticAnimation LAMP_CB_SNEAK;

    public static StaticAnimation LAMP_CB_GUARD;
    public static StaticAnimation LAMP_CB_GUARD_HIT;
    public static StaticAnimation LAMP_CB_PARRY_1;
    public static StaticAnimation LAMP_CB_PARRY_2;
    public static StaticAnimation LAMP_CB_PARRY_3;


    public static StaticAnimation LAMP_CB_AUTO_M_1;
    public static StaticAnimation LAMP_CB_AUTO_M_2;

    public static StaticAnimation LAMP_CB_AUTO_R_1;
    public static StaticAnimation LAMP_CB_AUTO_R_2;
    public static StaticAnimation LAMP_CB_AUTO_R_3;
    public static void build(Model biped) {
        System.out.println("Building LAMP_CROSSBOW Animations");

        LAMP_CB_IDLE = new StaticAnimation(true, "biped/lamp_cb/idle", biped);
        LAMP_CB_WALK = new MovementAnimation(true, "biped/lamp_cb/walk", biped);
        LAMP_CB_RUN = new MovementAnimation(true, "biped/lamp_cb/run", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.9f);
        LAMP_CB_KNEEL = new StaticAnimation(true, "biped/lamp_cb/kneel", biped);
        LAMP_CB_SNEAK = new MovementAnimation(true, "biped/lamp_cb/sneak", biped);

        LAMP_CB_GUARD = new StaticAnimation( true, "biped/lamp_cb/guard", biped);
        LAMP_CB_GUARD_HIT = new GuardAnimation(0.05f,0.6f, "biped/lamp_cb/guard_hit", biped);
        LAMP_CB_PARRY_1 = new GuardAnimation(0.05f,0.3f, "biped/lamp_cb/parry_1", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        LAMP_CB_PARRY_2 = new GuardAnimation(0.05f,0.3f, "biped/lamp_cb/parry_2", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);
        LAMP_CB_PARRY_3 = new GuardAnimation(0.05f,0.3f, "biped/lamp_cb/parry_3", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1f);

        LAMP_CB_AUTO_M_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, null, "Tool_R", "biped/lamp_cb/auto_m_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_m_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        LAMP_CB_AUTO_M_2 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, null, "Tool_R", "biped/lamp_cb/auto_m_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_m_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        LAMP_CB_AUTO_R_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, null, "Tool_R", "biped/lamp_cb/auto_r_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_r_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        LAMP_CB_AUTO_R_2 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, null, "Tool_R", "biped/lamp_cb/auto_r_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_r_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        LAMP_CB_AUTO_R_3 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.38f, 0.55F, 0.85F, null, "Tool_R", "biped/lamp_cb/auto_r_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.FINAL_COIN, true)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_r_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.ARDOR_BLOSSOM_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.FIREFIST_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

    }


}
