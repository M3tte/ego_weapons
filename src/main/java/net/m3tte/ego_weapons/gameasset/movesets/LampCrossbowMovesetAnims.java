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
import net.m3tte.ego_weapons.potion.NoAmmo;
import net.m3tte.ego_weapons.world.capabilities.*;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.ColliderPreset;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
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


    public static StaticAnimation LAMP_CB_AUTO_M_DASH;
    public static StaticAnimation LAMP_CB_AUTO_M_1;
    public static StaticAnimation LAMP_CB_AUTO_M_2;

    public static StaticAnimation LAMP_CB_AUTO_R_1;
    public static StaticAnimation LAMP_CB_AUTO_R_2;
    public static StaticAnimation LAMP_CB_AUTO_R_3;

    public static StaticAnimation LAMP_CB_INNATE;

    public static StaticAnimation LAMP_CB_RELOAD;
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

        LAMP_CB_AUTO_M_DASH = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.25f, 0.66F, 1.5f, EgoWeaponsCapabilityPresets.LAMP_CROSSBOW_MELEE, "Tool_R", "biped/lamp_cb/dash", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_m_dash")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_MELEE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_MELEE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F);


        LAMP_CB_AUTO_M_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.4f, 0.8F, 1.2f, EgoWeaponsCapabilityPresets.LAMP_CROSSBOW_MELEE, "Tool_R", "biped/lamp_cb/auto_m_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_m_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_MELEE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_MELEE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        LAMP_CB_AUTO_M_2 = new BasicEgoAttackAnimation(0.05F, 0.3f, 0.34f, 0.72F, 1.5F, EgoWeaponsCapabilityPresets.LAMP_CROSSBOW_MELEE, "Tool_R", "biped/lamp_cb/auto_m_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.SLASH)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT, basicSwingEvent)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_m_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_MELEE_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_MELEE_SWING)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        LAMP_CB_AUTO_R_1 = new BasicEgoAttackAnimation(0.05F, 0.2F, 0.73f, 0.8F, 1.16f, null, "Tool_R", "biped/lamp_cb/auto_r_1", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_r_1")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.CROSSBOW_SHOOT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.8f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, genericShootEvent(0.7f, 0f));

        LAMP_CB_AUTO_R_2 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.22f, 0.25F, 0.83f, null, "Tool_R", "biped/lamp_cb/auto_r_2", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_r_2")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.CROSSBOW_SHOOT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.9f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, genericShootEvent(0.21f, 0f));

        LAMP_CB_AUTO_R_3 = new BasicEgoAttackAnimation(0.01F, 0.1F, 0.23f, 0.25F, 1.5F, null, "Tool_R", "biped/lamp_cb/auto_r_3", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.FINAL_COIN, true)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_auto_r_3")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.CROSSBOW_SHOOT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1f))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, genericShootEvent(0.22f, 0.1f));

        LAMP_CB_RELOAD = (new ActionAnimation(0.1f, 1.5f,   "biped/lamp_cb/reload", biped))
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, reloadEvent(0.25f, 0.66f, 1.2f, true))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);

        LAMP_CB_INNATE = new BasicEgoAttackAnimation(0.05F, 0.2F, 1.3f, 1.4f, 2.8f, null, "Tool_R", "biped/lamp_cb/innate", biped)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_TYPE, AttackTypes.PIERCE)
                .addProperty(EgoWeaponsAttackProperty.DAMAGE_TYPE, DamageTypes.BLACK)
                .addProperty(EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE, AttackMoveType.RANGED)
                .addProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO, true)
                .addProperty(EgoWeaponsAttackProperty.IDENTIFIER, "lamp_cb_innate")
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.CROSSBOW_SHOOT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EgoWeaponsParticles.LAMP_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.1F)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, innateShootEvent(0.3f, 1.25f, 0f));


    }

    public static StaticAnimation.Event[] reloadEvent(float t1, float t2, float t3, boolean mainHand) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(t1, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    SoundEvents.ARMOR_EQUIP_LEATHER,
                    SoundCategory.PLAYERS, (float) 0.8, (float) 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[1] = StaticAnimation.Event.create(t2, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;

            world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    SoundEvents.CROSSBOW_LOADING_MIDDLE,
                    SoundCategory.PLAYERS, (float) 0.8, (float) 1);
        }, StaticAnimation.Event.Side.SERVER);

        events[2] = StaticAnimation.Event.create(t3, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entitypatch.getOriginal().level;

            int reloaded = AmmoSystem.forceReloadGun(entity, entity.getItemInHand(mainHand ? Hand.MAIN_HAND : Hand.OFF_HAND), AmmoType.LAMP_CROSSBOW_BOLT);


            if (!world.isClientSide()) {
                world.playSound(null, entitypatch.getOriginal().blockPosition(),
                        EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_RELOAD,
                        SoundCategory.PLAYERS, (float) 0.8, (float) 1);
            }
        }, StaticAnimation.Event.Side.SERVER);
        return events;
    }

    public static StaticAnimation.Event[] genericShootEvent(float fireStamp, float ampl) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(fireStamp, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();

            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);
            System.out.println("Ammo is :: "+ammo);
            if (ammo != null) {

                if (entity instanceof PlayerEntity) {
                    UtilitySystems.sendShockwavePacket((PlayerEntity) entity, 0.7f, 0.35f, 0.6f, 0);
                }

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.2,-0.3), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.8,-0.5), 6, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.25f, "Tool_R");

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.7,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3.4,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-4.1,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-4.8,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-5.5,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-6.2,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");


                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_SHOOT,
                            SoundCategory.NEUTRAL, 1f, (float) 1 +ampl);
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));

            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }

    public static StaticAnimation.Event[] innateShootEvent(float interimStamp, float fireStamp, float ampl) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[3];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.ARMOR_EQUIP_CHAIN,
                        SoundCategory.PLAYERS, 1f, (float) 1 +ampl);
            }
        }, StaticAnimation.Event.Side.BOTH);
        events[1] = StaticAnimation.Event.create(interimStamp, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();

            int ammoCount = AmmoSystem.getAmmoCount(entity.getItemInHand(Hand.MAIN_HAND));

            if (ammoCount >= 2) {
                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_CHARGED,
                            SoundCategory.PLAYERS, 1f, (float) 1 +ampl);
                }
                spawnArmatureParticle(entitypatch, 0, new Vector3d(-0.2f,-1.4f,-0.2), 20, EgoWeaponsParticles.LAMP_EMBERS_INV.get(), 5f, "Tool_R");

            }
        }, StaticAnimation.Event.Side.BOTH);
        events[2] = StaticAnimation.Event.create(fireStamp, (entitypatch) -> {
            World world = entitypatch.getOriginal().level;
            LivingEntity entity = entitypatch.getOriginal();

            AmmoType ammo1 = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);
            AmmoType ammo2 = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

            if (ammo1 != null) {

                if (entity instanceof PlayerEntity) {
                    UtilitySystems.sendShockwavePacket((PlayerEntity) entity, 0.9f, 0.45f, 0.5f, 0);
                }

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.2,-0.3), 1, ammo1.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.8,-0.5), 9, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.25f, "Tool_R");

                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.7,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3.4,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-4.1,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-4.8,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-5.5,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-6.2,-0.5), 2, EgoWeaponsParticles.LAMP_EMBERS.get(), 0.1f, "Tool_R");


                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                            EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_SHOOT,
                            SoundCategory.PLAYERS, 1f, (float) 1 +ampl);
                }

                if (ammo2 != null) {
                    if (!world.isClientSide()) {
                        world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                                EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_SHOOT,
                                SoundCategory.PLAYERS, 1f, (float) 0.7);
                    }
                    if (!world.isClientSide()) {
                        world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                                EgoWeaponsSounds.LAMP_CROSSBOW_RANGED_SHOOT,
                                SoundCategory.PLAYERS, 1f, (float) 0.4);
                    }
                }
            } else {
                entity.addEffect(new EffectInstance(NoAmmo.get().getEffect(), 5, 0));

            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
}
