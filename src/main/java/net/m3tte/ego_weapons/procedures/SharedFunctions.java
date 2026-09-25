package net.m3tte.ego_weapons.procedures;

import com.google.common.collect.Lists;
import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.entities.EGOTargetingEntity;
import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.m3tte.ego_weapons.entities.OnHitOnKillEntity;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.MimicryMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.item.ardor_blossom.ArdorBlossomBat;
import net.m3tte.ego_weapons.item.ardor_blossom.ArdorBlossomSuit;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperArmor;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperWeapon;
import net.m3tte.ego_weapons.item.heishou_mao.HeishouMaoRobe;
import net.m3tte.ego_weapons.item.heishou_mao.HeishouMaoSword;
import net.m3tte.ego_weapons.item.liu.LiuFireGauntlet;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBullet;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBulletArmor;
import net.m3tte.ego_weapons.item.mimicry.MimicryArmor;
import net.m3tte.ego_weapons.item.mimicry.MimicryItem;
import net.m3tte.ego_weapons.item.oeufi.OeufiArmor;
import net.m3tte.ego_weapons.item.rat.RatBluntJacket;
import net.m3tte.ego_weapons.item.rat.RatJacket;
import net.m3tte.ego_weapons.item.rat.RatKnife;
import net.m3tte.ego_weapons.item.rat.RatPipe;
import net.m3tte.ego_weapons.item.relics.Arayashiki;
import net.m3tte.ego_weapons.item.relics.SpidersTracksuit;
import net.m3tte.ego_weapons.item.solemn_lament.SolemnLament;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSuit;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword;
import net.m3tte.ego_weapons.item.sunshower.Sunshower;
import net.m3tte.ego_weapons.item.sunshower.SunshowerArmor;
import net.m3tte.ego_weapons.item.udjat.*;
import net.m3tte.ego_weapons.network.packages.CapabilityPackages;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.potion.*;
import net.m3tte.ego_weapons.potion.countEffects.*;
import net.m3tte.ego_weapons.specialParticles.texturedAfterImage.TexturedAfterImagePresets;
import net.m3tte.ego_weapons.world.capabilities.*;
import net.m3tte.ego_weapons.world.capabilities.damage.*;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.DoubtAPatch;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.StaggerableEntity;
import net.m3tte.ego_weapons.world.capabilities.gamerules.EgoWeaponsGamerules;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.IndirectEpicFightDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.*;
import java.util.function.Consumer;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.canProcEffects;
import static net.m3tte.ego_weapons.item.fullstop_rep.FullstopRepWeapon.critDamageCalculations;
import static net.m3tte.ego_weapons.world.capabilities.DialogueSystem.getPersonality;
import static net.m3tte.ego_weapons.world.capabilities.DialogueSystem.speakEvalDialogue;
import static net.m3tte.ego_weapons.world.capabilities.StaggerSystem.*;
import static net.m3tte.ego_weapons.world.capabilities.StaggerSystem.isStaggered;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.getHandFromAnim;

public class SharedFunctions {



    public static void resetTargetsFor(LivingEntity target) {
        if (!(target instanceof MobEntity))
            return;

        MobEntity targetMob = (MobEntity) target;

        if (target instanceof EGOTargetingEntity) {
            ((EGOTargetingEntity) target).setTargetingResetTimestamp(targetMob.tickCount);
        } else {
            targetMob.getPersistentData().putInt("resetTargetingTimestamp", targetMob.tickCount);
        }


        targetMob.setTarget(null);
    }

    private static LinkedList<Integer> toHideEntities = new LinkedList<>();

    public static LinkedList<Integer> getToHideEntities() {
        return toHideEntities;
    }

    public static void addHiddenEntity(int entityID) {
        if (toHideEntities.size() > 10) {
            toHideEntities.removeLast();
        }
        toHideEntities.addFirst(entityID);
    }
    public static Consumer<LivingEntityPatch<?>> basicSwingEvent = entityPatch -> {

        if (entityPatch == null)
            return;

        LivingEntity ent = entityPatch.getOriginal();

        if (!entityPatch.getOriginal().level.isClientSide) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
        }
    };

    public static Consumer<LivingEntityPatch<?>> distortionSwingEvent = entityPatch -> {

        if (entityPatch == null)
            return;

        LivingEntity ent = entityPatch.getOriginal();

        if (!entityPatch.getOriginal().level.isClientSide) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.DISTORTION_SHOCKWAVE.get().getRegistryName()));
        }
    };

    public static Consumer<LivingEntityPatch<?>> vertSwingEvent = entityPatch -> {

        if (entityPatch == null)
            return;

        LivingEntity ent = entityPatch.getOriginal();

        if (!entityPatch.getOriginal().level.isClientSide) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.VERTICAL_SLASH_SHOCKWAVE.get().getRegistryName()));
        }
    };

    public static Consumer<LivingEntityPatch<?>> heavySwingEvent = entityPatch -> {

        if (entityPatch == null)
            return;

        LivingEntity ent = entityPatch.getOriginal();

        if (!entityPatch.getOriginal().level.isClientSide) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DirectionalAttackParticle(ent.getId(), ent.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
        }
    };



    public static List<LivingEntity> getNearbyEntities(LivingEntity source, Vector3d pos, float hDist, float vDist, EntityPredicate predicate) {
        return new ArrayList<>(source.level
                .getNearbyEntities(LivingEntity.class,
                        predicate, source, new AxisAlignedBB(pos.x() - (hDist), pos.y() - (vDist), pos.z() - (hDist), pos.x() + (hDist), pos.y() + (vDist), pos.z() + (hDist))));
    }



    public static List<LivingEntity> getNearbyEntities(LivingEntity source, float hDist, float vDist) {
        return getNearbyEntities(source, source.position(), hDist, vDist, EntityPredicate.DEFAULT);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, float hDist, float vDist, EntityPredicate predicate) {
        return getNearbyEntities(source, source.position(), hDist, vDist, predicate);
    }

    public static List<LivingEntity> getLivingEntitiesRadius(World level, LivingEntity source, Vector3d pos, float radius, EntityPredicate predicate) {
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(pos.x() - (radius), pos.y() - (radius), pos.z() - (radius), pos.x() + (radius), pos.y() + (radius), pos.z() + (radius)));
        List<LivingEntity> list1 = Lists.newArrayList();
        for(LivingEntity t : list) {
            if (predicate.test(source, t) && t.position().distanceTo(pos) <= radius) {
                list1.add(t);
            }
        }
        return list1;
    }


    public static void clashStunEntity(LivingEntityPatch<?> patch, int strength) {
        if (patch == null)
            return;

        patch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 5, 0));
        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                StaticAnimation stunAnim = EgoWeaponsAnimations.CLASH_STUN_BASIC;

                if (patch.getValidItemInHand(Hand.MAIN_HAND).getItem() instanceof EgoWeaponsWeapon) {
                    stunAnim = ((EgoWeaponsWeapon) patch.getValidItemInHand(Hand.MAIN_HAND).getItem()).getDefaultStunAnim(strength);
                }

                patch.playAnimationSynchronized(stunAnim, 0);
            }

            if (patch instanceof StaggerableEntity) {
                StaticAnimation stunAnim = ((StaggerableEntity) patch).getClashStunAnim(strength);

                if (stunAnim != null)
                    patch.playAnimationSynchronized(stunAnim, 0);
            }
        }


    }
    public static void pummelDownEntity(LivingEntityPatch<?> patch, int strength, boolean stunImmunity) {
        if (patch == null)
            return;

        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (stunImmunity)
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));

            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                if (!patch.getOriginal().level.isClientSide())
                    patch.playAnimationSynchronized(EgoWeaponsAnimations.PUMMEL_DOWN, 0);
                return;
            }
        }


        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getGroundAnimation(strength);

            if (stunAnim != null)
                if (!patch.getOriginal().level.isClientSide())
                    patch.playAnimationSynchronized(stunAnim, 0);
        }

    }


    public static boolean forLivingEntity(LivingEntity target, Consumer<LivingEntityPatch<?>> then) {
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (target != null) {
            then.accept(entitypatch);
            return true;

        }
        return false;
    }

    public static boolean hitstunEntity(LivingEntityPatch<?> patch, int strength, boolean stunImmunity, float time) {

        if (patch == null)
            return false;

        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (stunImmunity)
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
            else
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 0, false, false, false));

            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                patch.playAnimationSynchronized(EgoWeaponsAnimations.LONG_HITSTUN, time);

                return true;
            }
        }


        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getHitstunAnimation(strength);

            if (stunAnim != null) {
                if (stunImmunity)
                    patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0, false, false, false));
                else
                    patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 0, false, false, false));

                patch.playAnimationSynchronized(stunAnim, time);

            }
            return true;
        }
        return false;
    }

    public static void staggerEntity(LivingEntityPatch<?> patch, int strength, boolean stunImmunity) {
        if (patch == null)
            return;



        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (stunImmunity)
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
            else
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 0));

            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {

                patch.playAnimationSynchronized(EgoWeaponsAnimations.STAGGER, 0);
                return;
            }
        }


        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getStunAnimation(strength);

            if (stunAnim != null) {
                if (stunImmunity)
                    patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
                else
                    patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 0));

                patch.playAnimationSynchronized(stunAnim, 0);
            }

        }

    }

    public static IFormattableTextComponent coloredText(String text, TextFormatting color) {
        return new StringTextComponent(text).withStyle(color);
    }

    public static void pummelUpEntity(LivingEntityPatch<?> patch, int strength) {
        patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));


        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                patch.playAnimationSynchronized(EgoWeaponsAnimations.LIFT_UP, 0);
                return;
            }
        }


        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getLiftAnimation(strength);

            if (stunAnim != null)
                patch.playAnimationSynchronized(stunAnim, 0);
        }

    }

    public static float incrementBonusDamage(DamageSource source, float factor) {
        if (source instanceof GenericEgoDamage) {
            ((GenericEgoDamage) source).setBonusMult(((GenericEgoDamage) source).getBonusMult() + (factor));
        }

        return factor;
    }

    public static void incrementResistanceDamage(DamageSource source, float factor) {
        if (source instanceof GenericEgoDamage) {
            //System.out.println("Prev Resistance Val: "+((GenericEgoDamage) source).getResistanceMult());
            ((GenericEgoDamage) source).setResistanceMult(((GenericEgoDamage) source).getResistanceMult() + factor);

            //System.out.println("Post Resistance Val: "+((GenericEgoDamage) source).getResistanceMult());
        }
    }


    public static float modifyDamageGeneric(float amount, DamageSource source, LivingEntity self) {

        // Poise Damage Increase
        // If source has poise, 5% crit chance per poise


        boolean hitCooldownStart = false;

        if (source.getEntity() == null)
            return amount;

        boolean doesProcEffects = source.getEntity() instanceof LivingEntity && canProcEffects(source.getEntity() != null ? (LivingEntity) source.getEntity() : null);

        if (source.getEntity() instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = source.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

            hitCooldownStart = entityData.onHitCounter <= 0;

        }


        boolean crit = false;
        float multiplier = 1;
        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity sourceEntity = (LivingEntity) source.getEntity();
            if (sourceEntity.hasEffect(EgoWeaponsEffects.POISE.get())) {
                float chance = 0.05f * EgoWeaponsEffects.POISE.get().getPotency(sourceEntity);

                if (!sourceEntity.level.isClientSide()) {
                    float randomVal = sourceEntity.getRandom().nextFloat();
                    if (randomVal < chance) {
                        ((ServerWorld) self.level).sendParticles(EgoWeaponsParticles.CRIT.get(), (self.getX()), (self.getY() + self.getBbHeight() / 2),
                                (self.getZ()), 1, 0, 0, 0, 0);
                        multiplier += 0.2f;
                        if (source instanceof GenericEgoDamage) {
                            ((GenericEgoDamage) source).setCrit(true);
                            incrementBonusDamage(source, 0.2f);
                        }
                        crit = true;

                        if (doesProcEffects)
                            EgoWeaponsEffects.POISE.get().decrement(sourceEntity, 1, 0);
                        ((World) sourceEntity.level).playSound(null, sourceEntity.blockPosition(),
                                (net.minecraft.util.SoundEvent) EgoWeaponsSounds.POISE_CRIT,
                                SoundCategory.PLAYERS, (float) 2, (float) 1);
                    }
                }
            }

            if (crit) {

                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) sourceEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                if (sourcePatch != null) {
                    if (sourcePatch.getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory().equals(EgoWeaponsCategories.FULLSTOP_REP)) {
                        multiplier = critDamageCalculations(self, sourcePatch, multiplier, source);
                    }

                    if (sourcePatch.getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory().equals(EgoWeaponsCategories.FULLSTOP_SNIPER)) {
                        multiplier = FullstopSniperWeapon.critDamageCalculations(self, sourcePatch, multiplier, source);
                    }

                }

            }
        }

        // Calculate additional poise damage for movesets
        // Damage Increase for Target Spotted
        if (self.hasEffect(EgoWeaponsEffects.TARGET_SPOTTED.get()) && source.getEntity() != null) {
            LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) source.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            if (sourcePatch != null) {
                multiplier = TargetSpottedEffect.applyOnHit(sourcePatch, self, multiplier, source);
            }
        }

        // Damage increase for target spotted :: Udjat
        if (self.hasEffect(EgoWeaponsEffects.TARGET_MARK_UDJAT.get()) && source.getEntity() != null) {
            LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) source.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            if (sourcePatch != null) {
                multiplier = UdjatTargetMark.applyOnHit(sourcePatch, self, multiplier, source);
            }
        }

        // Effect based damage multipliers

        if (self.hasEffect(EgoWeaponsEffects.TIANSHIA_STAR.get())) {
            TianshiaStarsBlade.applyDamageModifiersTarget(self, (LivingEntity) source.getEntity(), multiplier, source, crit);
        }
        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) source.getEntity();

            if (livingEntity.hasEffect(EgoWeaponsEffects.TIANSHIA_STAR.get())) {
                TianshiaStarsBlade.applyDamageModifiersSource(self, (LivingEntity) source.getEntity(), multiplier, source, crit);
            }
        }


        // Damage Modify Effects
        if (source.getEntity() instanceof LivingEntity) {
            // Animation Bound Effects
            multiplier = evaluateAnimationEffects(self, (LivingEntity) source.getEntity(), multiplier, source);



            Item qualifiedItem = ((LivingEntity)source.getEntity()).getItemInHand(getHandFromAnim((LivingEntity) source.getEntity())).getItem();

            if (qualifiedItem != null) {

                if (qualifiedItem.getRegistryName() != null) {
                    switch(qualifiedItem.getRegistryName().getPath()) {
                        case "mimicry": multiplier = MimicryItem.modifyDamageNormal(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "magic_bullet": multiplier = MagicBullet.damageMultiplier(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "liu_flame_gauntlet": multiplier = LiuFireGauntlet.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "firefist_gauntlet": multiplier = FirefistGauntlet.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "stigma_workshop_sword": multiplier = StigmaWorkshopSword.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "solemn_lament_departed":
                        case "solemn_lament_living": multiplier = SolemnLament.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "heishou_mao_sword": multiplier = HeishouMaoSword.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "rat_shank": multiplier = RatKnife.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "rat_pipe": multiplier = RatPipe.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "sunshower": multiplier = Sunshower.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "ardor_blossom_bat": multiplier = ArdorBlossomBat.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "udjat_khopesh": multiplier = UdjatKhopesh.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "lca_khopesh": multiplier = LCAKhopesh.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "lca_rifle": multiplier = LCARifle.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source); break;
                        case "arayashiki": multiplier = Arayashiki.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source, crit); break;

                    }
                }
            }

            Item sourceChestItem = ((LivingEntity)source.getEntity()).getItemBySlot(EquipmentSlotType.CHEST).getItem();
            Item targetChestItem = (self).getItemBySlot(EquipmentSlotType.CHEST).getItem();
            LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) source.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


            if (sourcePatch != null) {
                if (sourceChestItem != null) {
                    if (sourceChestItem.getRegistryName() != null) {
                        switch (sourceChestItem.getRegistryName().getPath()) {
                            case "sunshower_cloak":
                                multiplier = SunshowerArmor.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                                break;

                            case "oeufi_association_vest":
                                multiplier = OeufiArmor.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                                break;

                            case "stigma_workshop_suit":
                                multiplier = StigmaWorkshopSuit.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                                break;
                            case "fullstop_office_sniper_suit":
                                multiplier = FullstopSniperArmor.hitDamageBonus(sourcePatch, multiplier, source);
                                break;
                            case "rat_outfit":
                                multiplier = RatJacket.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                                break;
                            case "ardor_blossom_suit":
                                multiplier = ArdorBlossomSuit.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                                break;
                            case "magic_bullet_cloak":
                                multiplier = MagicBulletArmor.poiseEffect(self, (LivingEntity) source.getEntity(), amount, multiplier, source);
                                break;
                            case "heishou_mao_robe":
                                multiplier = HeishouMaoRobe.onHitTargetEffect(self, (LivingEntity) source.getEntity(), amount, multiplier, source);
                                break;
                            case "spider_tracksuit": multiplier = SpidersTracksuit.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source, crit); break;

                        }
                    }
                }

                if (targetChestItem != null) {
                    if (targetChestItem.getRegistryName() != null) {
                        switch (targetChestItem.getRegistryName().getPath()) {
                            case "rat_blunt_outfit":
                                multiplier = RatBluntJacket.modifyDamageAmountInbound(self, (LivingEntity) source.getEntity(), multiplier, source);
                                break;
                            case "heishou_mao_robe":
                                multiplier = HeishouMaoRobe.onHitSelfEffect(self, (LivingEntity) source.getEntity(), amount, multiplier, source);
                                break;
                            case "spider_tracksuit": multiplier = SpidersTracksuit.modifyDamageAmountTarget(self, (LivingEntity) source.getEntity(), multiplier, source, crit); break;

                        }
                    }
                }
            }


            if (((LivingEntity) source.getEntity()).hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get())) {
                LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

                boolean finale = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.FINAL_COIN).orElse(false);

                if (self.hasEffect(EgoWeaponsEffects.TREMOR_DECAY.get()) && finale) {
                    SharedFunctions.incrementBonusDamage(source, 0.25f);
                    multiplier += 0.25f;
                }
            }
        }

        // Apply protection from sunshower sinking.
        if (EgoWeaponsEffects.SINKING.get().getPotency(self) > 1 && source.getEntity() instanceof LivingEntity && self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUNSHOWER_CLOAK.get())) {
           EgoWeaponsEffects.PROTECTION.get().increment(self, 4, Math.min((EgoWeaponsEffects.SINKING.get().getPotency(self) / 3)-1, 4));
        }

        // Sunshower interrupt effect
        if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUNSHOWER.get())) {
            Sunshower.interruptedAttack(self);
        }

        // Tremor Protection from Rat Blunt
        if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.BLUNT_RAT_OUTFIT.get())) {
            TremorEffect tremor = TremorEffect.detectTremorType(self);

            int cnt = tremor != null ? tremor.getCount(self) : 0;
            int pot = tremor != null ? tremor.getPotency(self) : 0;

            incrementBonusDamage(source, -Math.min(0.2f,cnt * 0.02f));
            multiplier -= (Math.min(0.2f,cnt * 0.02f));

            incrementBonusDamage(source, -Math.min(0.1f,pot * 0.01f));
            multiplier -= (Math.min(0.1f,pot * 0.01f));
        }

        // Apply extra damage from shielding alloy Regenerative Cycle.
        if (self.hasEffect(EgoWeaponsEffects.SHIELDING_ALLOY_REGENERATIVE_CYCLE.get())) {
            multiplier += 0.15f;
        }

        // Apply protection from PROTECTION and FRAGILE stacks.
        if (self.hasEffect(EgoWeaponsEffects.PROTECTION.get()) && source.getEntity() instanceof LivingEntity) {
            float totalMult = EgoWeaponsEffects.PROTECTION.get().getPotency(self) * 0.1f + EgoWeaponsEffects.FRAGILE.get().getPotency(self) * 0.1f;

            totalMult = Math.max(0, totalMult);

            incrementBonusDamage(source, -multiplier*totalMult);
            multiplier *= (1 - totalMult);
        }

        // Apply base increased damage from SIN.
        if (self.hasEffect(EgoWeaponsEffects.SIN.get()) && source.getEntity() instanceof LivingEntity) {
            incrementBonusDamage(source, -multiplier*EgoWeaponsEffects.SIN.get().getPotency(self)*0.1f);
            multiplier *= (1 - EgoWeaponsEffects.SIN.get().getPotency(self)*0.1f);
        }

        // Apply base increased damage from SIN.
        float collectiveSin = 0;
        if (self.hasEffect(EgoWeaponsEffects.SIN.get())) {
            collectiveSin += EgoWeaponsEffects.SIN.get().getPotency(self);
        }

        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity livingSource = (LivingEntity) source.getEntity();

            if (livingSource.hasEffect(EgoWeaponsEffects.SIN.get())) {
                collectiveSin += EgoWeaponsEffects.SIN.get().getPotency(livingSource);
            }
        }

        if (collectiveSin > 0) {
            multiplier += incrementBonusDamage(source, collectiveSin * 0.02f);
        }

        // Decrement resilience stacks
        if (self.hasEffect(EgoWeaponsEffects.RESILIENCE.get()) && doesProcEffects) {
            EgoWeaponsEffects.RESILIENCE.get().decrement(self, 0, 1);
        }

        // Apply BUTTERFLY effects

        if (self.hasEffect(EgoWeaponsEffects.THE_LIVING.get()) && doesProcEffects) {
            TheLivingButterflyEffect.applyOnHit(self, source.getEntity() instanceof LivingEntity ? (LivingEntity) source.getEntity() : null);
        }
        if (self.hasEffect(EgoWeaponsEffects.THE_DEPARTED.get()) && doesProcEffects) {
            TheDepartedButterflyEffect.applyOnHit(self, source.getEntity() instanceof LivingEntity ? (LivingEntity) source.getEntity() : null);
        }

        // Apply SINKING damage
        if (self.hasEffect(EgoWeaponsEffects.SINKING.get()) && doesProcEffects) {
            SinkingEffect.applyOnHit(self, source.getEntity() instanceof LivingEntity ? (LivingEntity) source.getEntity() : null);
        }

        // Apply RUPTURE damage
        if (self.hasEffect(EgoWeaponsEffects.RUPTURE.get()) && doesProcEffects) {

            if (EgoWeaponsEffects.speedMult((LivingEntity) source.getEntity()) >= 7 && self.hasEffect(EgoWeaponsEffects.DEATHRITE_HASTE.get())) {
                DeathriteHasteEffect.applyOnHit(self, (LivingEntity) source.getEntity());
            }

            RuptureEffect.applyOnHit(self);


        }

        // Reduce inbound damage for nothing there by 30% on ranged attacks.
        if (self instanceof NothingThere2Entity) {
            if (source.getDirectEntity() != source.getEntity() || source.isProjectile()) {
                amount *= 0.7f;
            }
        }


        // Special followup attacks for DOUBT
        if (source.getEntity() instanceof DawnOfGreenDoubtEntity) {
            DawnOfGreenDoubtEntity doubtEntity = (DawnOfGreenDoubtEntity) source.getEntity();
            DoubtAPatch doubtAPatch = (DoubtAPatch) doubtEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {
                if (doubtAPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.DOUBT_AUTO_B1.getId()) {
                    pummelDownEntity(entitypatch, 2, true);
                    doubtAPatch.reserveAnimation(EgoWeaponsMobAnimations.DOUBT_AUTO_B2);
                    doubtEntity.getPersistentData().putInt("pounceHits", 0);
                }
                int hitCount = 0;
                if (doubtEntity.getPersistentData().contains("pounceHits")) {
                    hitCount = doubtEntity.getPersistentData().getInt("pounceHits");
                }


                if (hitCount < 3 && doubtAPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.DOUBT_AUTO_B2.getId() && !self.level.isClientSide() && self.level.random.nextFloat() < 0.75f) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2, true);
                    doubtAPatch.reserveAnimation(EgoWeaponsMobAnimations.DOUBT_AUTO_B2);
                    doubtEntity.getPersistentData().putInt("pounceHits", hitCount+1);
                } else if (hitCount > 0)  {
                    doubtEntity.getPersistentData().remove("pounceHits");
                }
            }


        }

        // Special Followup attacks for NOTHING THERE
        if (source.getEntity() instanceof OnHitOnKillEntity) {

            multiplier = ((OnHitOnKillEntity) source.getEntity()).onHit(self, source, amount, multiplier);

        }



        // 30% Damage reduction if the player has "I love you"
        if(self.hasEffect(ILoveYou.get())) {
            ILoveYou.onHit(source.getEntity(), self);

            incrementResistanceDamage(source, -multiplier * 0.5f);
            multiplier *= 0.5f;
        }

        // If nothing there is winding up a scream. Increase the scream charge and reduce damage by 30%.
        if (self.getPersistentData().contains("windupCharge")) {
            self.getPersistentData().putFloat("windupCharge", self.getPersistentData().getFloat("windupCharge") + amount);
            amount *= 0.7f;
            if (!self.level.isClientSide()) {
                self.level.playSound(null, self.blockPosition(),
                        EpicFightSounds.BLUNT_HIT,
                        SoundCategory.PLAYERS, (float) 1, (float) 1.5);
            }
        }

        // Damage reduction handling for "SHELL"
        // 20% if the source has "Terror". Further 10% per level of shell.
        if (self.hasEffect(EgoWeaponsEffects.SHELL.get())) {
            if (source.getEntity() instanceof LivingEntity) {
                if (((LivingEntity) source.getEntity()).hasEffect(EgoWeaponsEffects.TERROR.get())) {
                    incrementResistanceDamage(source, -0.2f);
                    multiplier -= 0.2f;
                }
            }

            int potency = EgoWeaponsEffects.SHELL.get().getPotency(self);
            if (potency > 5) potency = 5; // Top off potency so it cant block all damage

            incrementResistanceDamage(source, -0.1f * potency);
            multiplier -= (0.1f*potency);
            if (self instanceof PlayerEntity) {
                PlayerPatch<?> entitypatch = (PlayerPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                self.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 5 * potency, 0));

                if (source.getEntity() instanceof LivingEntity && amount > 3) {
                    entitypatch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_HIT, 0);
                    self.level.playSound(null,self.blockPosition(), EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_MACE, SoundCategory.PLAYERS, 1, 1);
                }

            }
        }

        if (source.getEntity() instanceof LivingEntity)
            multiplier = evaluateOnHitEffects(self, (LivingEntity) source.getEntity(), multiplier, source, isStaggered(self), hitCooldownStart);



        // Apply all flat damage modifiers
        if (source.getEntity() instanceof LivingEntity)
            multiplier = DamageResistanceSystem.processDamageForEntity(self, (LivingEntity) source.getEntity(), multiplier, source, isStaggered(self));





        amount *= multiplier;

    // Test & Apply Damage protection for udjat passive
        if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.LCA_UDJAT_SUIT.get())) {

            float qualifiedDamage = CombatRules.getDamageAfterAbsorb(amount, self.getArmorValue(), (float) self.getAttributeValue(Attributes.ARMOR_TOUGHNESS));

            if (qualifiedDamage >= 10 && qualifiedDamage / 2 < self.getHealth()) {
                if (LCAUdjatArmor.evaluateUdjatBrace(self, source.getEntity())) {
                    amount *= 0.5f;
                }
            }
        }

        // Attacker gains emotion points
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity srcEntity = (PlayerEntity) source.getEntity();
            increaseSkillResource(source, (PlayerEntity) source.getEntity(), 5);
            EmotionSystem.increaseEmotionPoints(srcEntity, (int) amount / 2 + 3, true);
        }



        // Stagger Logic
        if (isStaggered(self)) {
            stagger(self, (n) -> {
                if (source.getEntity() instanceof LivingEntity)
                    onStaggered((LivingEntity) source.getEntity(), self);
            });
        }

        return amount;
    }



    private static float evaluateAnimationEffects(LivingEntity target, LivingEntity source, float amount, DamageSource damageSource) {
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch == null)
            return amount;

        DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();


        boolean finale = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.FINAL_COIN).orElse(false);

        if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.STIGMA_WORKSHOP_SUIT.get())) {
            if (EgoWeaponsEffects.BURN.get().getPotency(target) > 10) {
                SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
                amount += 0.1f;
            }
        }

        if (finale) {
            if (source.hasEffect(EgoWeaponsEffects.OBLIGATION_FULLFILLMENT.get())) {
                TremorEffect.burstTremor(target, true);
            }

            if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.LIU_SOUTH_6_CHESTPLATE.get())) {
                if (target.hasEffect(EgoWeaponsEffects.BURN.get())) {
                    SharedFunctions.incrementBonusDamage(damageSource, 0.1f);
                    amount += 0.1f;
                }

                EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
            }

            if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.RAT_OUTFIT.get())) {
                int healthPercent = (int) ((source.getHealth() / source.getMaxHealth()) * 100);

                int damageMults = healthPercent / 15;

                if (damageMults > 0) {
                    SharedFunctions.incrementBonusDamage(damageSource, 0.05f * damageMults);
                    amount += 0.05f * damageMults;
                }
            }
        }

        return amount;
    }

    public static boolean hasDefenseDown(LivingEntity entity) {
        if (entity.hasEffect(EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get()))
            return true;

        if (entity.hasEffect(EgoWeaponsEffects.TREMOR_DECAY.get())) {
            if (EgoWeaponsEffects.TREMOR_DECAY.get().getPotency(entity) > 3)
                return true;
        }

        if (entity.hasEffect(EgoWeaponsEffects.DARK_BURN.get()))
            return true;

        return false;
    }

    private static void onKilled(DamageSource src, LivingEntity self) {
        //intln("Executing onKILLED for entity "+self+" source entity is : "+src.getEntity());

        // Visual Death Effects
        if (src.getEntity() instanceof LivingEntity) {
            LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) src.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            if (sourcePatch != null) {

                UtilitySystems.EGOAttackContext ctx = new UtilitySystems.EGOAttackContext(sourcePatch);

                if (src.getEntity() instanceof OnHitOnKillEntity) {
                    ((OnHitOnKillEntity) src.getEntity()).onKill(self);
                }

                if (ctx.getLogicPredicate().equals(AttackLogicPredicate.MEAT_EXPLOSION_KILL)) {
                    if (self.level instanceof ServerWorld) {
                        ((ServerWorld) self.level).sendParticles(EgoWeaponsParticles.MEAT_CHUNK_EXPLOSION.get(), self.position().x, self.position().y, self.position().z, (int) 1, 0, 0, 0, 0);

                    } else {
                        toHideEntities.add(self.getId());
                    }
                    self.level.playSound(null, self.blockPosition(),
                            EgoWeaponsSounds.NOTHING_THERE_GOODBYE_KILL,
                            SoundCategory.PLAYERS, (float) 1, (float) 1.5);
                }

                if (ctx.getLogicPredicate().equals(AttackLogicPredicate.MUGA)) {
                    // self.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), self.getX(), self.getY(), self.getZ(), Double.longBitsToDouble(self.getId()), TexturedAfterImagePresets.MUGA.ordinal(), 0);


                    new DelayedEvent(2, (a) -> {
                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), 1, self.getX(), self.getY(), self.getZ(), Double.longBitsToDouble(self.getId()), TexturedAfterImagePresets.MUGA.ordinal(), 0, 0, 0, 0));

                    });
                    new DelayedEvent(4, (a) -> {
                        toHideEntities.add(self.getId());
                        self.teleportTo(self.getX(), 0, self.getY());
                    });
                }

            }
        }


        if (src.getEntity() instanceof LivingEntity) {
            LivingEntity source = (LivingEntity) src.getEntity();

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            DynamicAnimation currentanim = null;
           if (entitypatch != null) {
               currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();
           }



            final int anim_id = currentanim != null ? currentanim.getId() : 0;

            Item it = source.getItemBySlot(EquipmentSlotType.CHEST).getItem();

            String killerPersonality = getPersonality(source);
            int dialogue = self.getRandom().nextInt(5);



            int lastKillTime = source.getPersistentData().getInt("lastKillDialogue");
            int diff = source.getEntity().tickCount - lastKillTime;
            //System.out.println("Lastkilldialoguetime is : "+lastKillTime+" thus diff is : "+diff);
            // Potentially add other bypasses or more config. Default for now is 60 ticks between kill msgs unless its a player kill. Then its 10 ticks

            if (diff <= 0) {
                diff = 99;
            }

            if ((diff > 60 || (self instanceof PlayerEntity && diff > 10)) && killerPersonality.length() > 1) {
                source.getPersistentData().putInt("lastKillDialogue", source.getEntity().tickCount);
                speakEvalDialogue((LivingEntity) source.getEntity(), "dialogue.ego_weapons.generic.kill.", killerPersonality, dialogue, TextFormatting.WHITE, DialogueSystem.DialogueTypes.FILLER);
            }


            if (anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_3.getId() || anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_2.getId() || anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_1.getId()) {
                source.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().putBoolean("ffkilled", true);
                if (src.getEntity() instanceof LivingEntity) {
                    if (src.getEntity() instanceof PlayerEntity)
                        EntityTick.regenerateLight((PlayerEntity) src.getEntity(), 1, true);
                    EgoWeaponsEffects.POWER_UP.get().increment((LivingEntity) src.getEntity(), 4, 2);
                }
            }

            // Solemn Lament on Kill
            if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SOLEMN_LAMENT_CLOAK.get())) {
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 5, 1);

                float maxHealth = Math.min(10, self.getMaxHealth() * 0.2f);

                List<LivingEntity> targets = SharedFunctions.getNearbyEntities(source, self.position(), 6, 5, TeamLockedPredicate.ONLY_HOSTILES);

                for (LivingEntity target : targets) {
                    if (target != self && target != source) {
                        target.hurt(new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.PALE, "butterfly_armor_passive"), maxHealth);
                        target.playSound(EgoWeaponsSounds.SOLEMN_LAMENT_BELL, 1f, 1);

                        if (!target.level.isClientSide()) {
                            ((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_BURST_HIT.get(), target.getX(), target.getY() + target.getBbHeight()/2 - 1, target.getZ(), 1, 0, 0, 0, 0);
                        }
                    }
                }
            }

            if (source.getItemBySlot(EquipmentSlotType.MAINHAND).getItem().equals(EgoWeaponsItems.ARDOR_BLOSSOM_BAT.get())) {
            List<LivingEntity> nearby = SharedFunctions.getNearbyEntities(source, 10, 5, TeamLockedPredicate.ONLY_HOSTILES);

            int burnOnTarget = EgoWeaponsEffects.BURN.get().getPotency(self);

            if (nearby.size() > 1) {
                int splitBurn = Math.max(1, burnOnTarget / (nearby.size()-1));

                for (LivingEntity ent : nearby) {
                    if (ent.getId() != self.getId()) {
                        EgoWeaponsEffects.BURN.get().increment(ent, 0, splitBurn);
                        if (!ent.level.isClientSide())
                            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.INGOING_EMBER.get(), Math.min(20,Math.max(2,splitBurn * 3)), ent.getX(), ent.getY() + ent.getBbHeight()/2, ent.getZ(), 3f, 0.3f, 10, 0,0,0));

                    }
                }
            }

        }

            if (anim_id == MimicryMovesetAnims.MIMICRY_GOODBYE.getId() || anim_id == MimicryMovesetAnims.MIMICRY_GOODBYE_ENHANCED.getId()) {
                if (src.getEntity() instanceof LivingEntity) {

                    if (self.level instanceof ServerWorld) {
                        ((ServerWorld) self.level).sendParticles(EgoWeaponsParticles.MEAT_CHUNK_EXPLOSION.get(), self.position().x, self.position().y, self.position().z, (int) 1, 0, 0, 0, 0);
                        self.getPersistentData().putBoolean("hiddenModel",true);
                    }

                    self.level.playSound(null, self.blockPosition(),
                            EgoWeaponsSounds.NOTHING_THERE_GOODBYE_KILL,
                            SoundCategory.PLAYERS, (float) 1, (float) 1.5);

                    EgoWeaponsEffects.POWER_UP.get().increment((LivingEntity) src.getEntity(), 5, 1);
                    EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment((LivingEntity) src.getEntity(), 10, 2);
                    EgoWeaponsEffects.IMITATION.get().increment((LivingEntity) src.getEntity(), 5, 1);
                }
            }

            if (it.equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) || it.equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get())) {
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 4, 1);
                if (source.hasEffect(ManifestEgoPotionEffect.potion.getEffect())) {
                    EgoWeaponsEffects.POWER_UP.get().increment(source, 3, 1);
                }
            }

            if (anim_id == MimicryMovesetAnims.KALI_ONRUSH.getId()) {
                if (!self.level.isClientSide()) {
                    source.level.playSound(null,  self.getX(), self.getY(), self.getZ(), EgoWeaponsSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1);
                }

                source.getPersistentData().putInt("onrushChain", 3);
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                        new CapabilityPackages.SyncOnrushData(source.getId(), 3));
            }
        }
    }

    public static void onStaggered(LivingEntity source, LivingEntity self) {
        // On Stagger / On Death effects

        if (source == null)
            return;

        if (source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null) == null)
            return;

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch == null)
            return;

        if (entitypatch.getServerAnimator() == null)
            return;

        DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();
        final int anim_id = currentanim.getId();

        if (anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_3.getId()) {
            EgoWeaponsEffects.POWER_UP.get().increment(source, 4, 2);
        }

        if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.UDJAT_SUIT.get())) {
            if (self.hasEffect(EgoWeaponsEffects.BLUE_SAND.get())) {
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 4, 2);
            } else {
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 2, 1);
            }
        }

        if (source.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.LCA_UDJAT_SUIT.get())) {
            EgoWeaponsEffects.UDJAT_VANGUARD.get().increment(source, 3, 1);
            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 4, 2);



            boolean success = DialogueSystem.speakEvalDialogue(source, "dialogue.ego_weapons.skills.udjat_armor.stagger_target", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE, 20);

            if (success) {
                source.level.playSound(null, source.blockPosition(),
                        EgoWeaponsSounds.UDJAT_DIALOGUE_STAGGER,
                        SoundCategory.PLAYERS, (float) 2, (float) 1);

            }
        }


        String selfPersonality = getPersonality(self);

        if (!self.level.isClientSide()) {
            //System.out.println("NOT CLIENT SIDE, TESTING STAGGER");
            if (!selfPersonality.isEmpty()) {

                int dialogue = self.getRandom().nextInt(5);
                //System.out.println("SPEAKING STAGGER FOR SELF");
                speakEvalDialogue(self, "dialogue.ego_weapons.generic.stagger." ,selfPersonality, dialogue, TextFormatting.YELLOW, DialogueSystem.DialogueTypes.FILLER, 40);
            }

            if (source != null) {
                String sourcePersonality = getPersonality(source);
                if (!sourcePersonality.isEmpty()) {

                    //System.out.println("SPEAKING STAGGER FOR ATTACKER");
                    int dialogue = source.getRandom().nextInt(5);
                    speakEvalDialogue(source, "dialogue.ego_weapons.generic.staggerEnemy." ,sourcePersonality, dialogue, TextFormatting.WHITE, DialogueSystem.DialogueTypes.FILLER, 40);
                }
            }
        }




        if (anim_id == MimicryMovesetAnims.KALI_ONRUSH.getId()) {
            if (!self.level.isClientSide()) {
                source.level.playSound(null,  self.getX(), self.getY(), self.getZ(), EgoWeaponsSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1);
            }

            source.getPersistentData().putInt("onrushChain", 3);
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                    new CapabilityPackages.SyncOnrushData(source.getId(), 3));
        }
    }

    /*public static void checkBeforeDamageApply(DamageSource src, float amount, CallbackInfo ci, LivingEntity self) {

    }*/


    public static DamageSource evaluateDamageSource(DamageSource src) {


        if (src instanceof DirectEgoDamageSource) {
            return src;
        }

        if (src.getDirectEntity() instanceof LivingEntity) {
            LivingEntityPatch<?> livingPatch = (LivingEntityPatch<?>) src.getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            if (src instanceof EntityDamageSource)
                src = convertDamagesourceFromEntityPatch(src, livingPatch);
        }

        if (src.getEntity() instanceof LivingEntity) {
            LivingEntityPatch<?> livingPatch = (LivingEntityPatch<?>) src.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (src instanceof EntityDamageSource)
                src = convertDamagesourceFromEntityPatch(src, livingPatch);
        }


        return src;
    }

    private static DamageSource convertDamagesourceFromEntityPatch(DamageSource src, LivingEntityPatch<?> patch) {

        if (patch == null)
            return src;

        if (patch.getServerAnimator() == null)
            return src;

        if (patch.getServerAnimator().animationPlayer == null)
            return src;

        if (patch.getServerAnimator().animationPlayer.getAnimation() == null)
            return src;

        DynamicAnimation dynAnim = patch.getServerAnimator().animationPlayer.getAnimation().getRealAnimation();

        AttackTypes attackType = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_TYPE).orElse(null);
        DamageTypes damageType = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE).orElse(null);


        Hand hand = Hand.MAIN_HAND;

        if (dynAnim instanceof AttackAnimation) {
            hand = ((AttackAnimation) dynAnim).getPhaseByTime(patch.getAnimator().getPlayerFor(dynAnim).getElapsedTime()).getHand();
        }


        ItemStack itemHandIdent = patch.getValidItemInHand(hand);

        boolean ammoconsume = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
        boolean consumesStatus = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TRIGGERS_EFFECTS).orElse(true);

        if (dynAnim instanceof EgoAttackAnimation || dynAnim instanceof BasicEgoAttackAnimation) {
            AttackAnimation.Phase phase = null;
            phase = ((AttackAnimation) dynAnim).getPhaseByTime(patch.getAnimator().getPlayerFor(dynAnim).getElapsedTime());

            if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
                hand = phase.getHand();
                attackType = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.ATTACK_TYPE).orElse(attackType);
                damageType = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.DAMAGE_TYPE).orElse(damageType);
                ammoconsume = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.CONSUMES_AMMO).orElse(ammoconsume);
                consumesStatus = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.TRIGGERS_EFFECTS).orElse(consumesStatus);
            }


        }

        if (ammoconsume) {
            AmmoType lastFired = null;
            if (patch.getOriginal().getItemInHand(hand).getOrCreateTag().contains("lastFired"))
                lastFired = AmmoType.values()[patch.getOriginal().getItemInHand(hand).getOrCreateTag().getInt("lastFired")];

            if (lastFired != null) {
                attackType = lastFired.getAttackType();
                damageType = lastFired.getDamageType();
            }
        }



        // Defaults Fallback
        if (attackType == null || damageType == null) {

            // Item Tag Level Check
            DamageDefaults modifiedDefaults = new DamageDefaults(damageType, attackType);
            ItemStack itemHand = patch.getValidItemInHand(hand);
            if (!itemHand.isEmpty())
                getItemLevelDefaults(itemHand, modifiedDefaults);

            // If nothing is found, do entity level check:
            if (modifiedDefaults.getDamageType() == null || modifiedDefaults.getAttackType() == null) {
                getEntityLevelDefaults(patch.getOriginal(), modifiedDefaults);
            }

            attackType = modifiedDefaults.getAttackType() != null ? modifiedDefaults.getAttackType() : AttackTypes.GENERIC;
            damageType = modifiedDefaults.getDamageType() != null ? modifiedDefaults.getDamageType() : DamageTypes.RED;
        }

        String attackIdentifier = getAttackIdentifierFor(itemHandIdent, dynAnim, attackType, damageType);


        if (src instanceof IndirectEpicFightDamageSource)
            return new IndirectEgoDamageSource((IndirectEpicFightDamageSource) src, attackType, damageType, consumesStatus, attackIdentifier);

        if (src instanceof EpicFightDamageSource)
            return new DirectEgoDamageSource((EpicFightDamageSource) src, attackType, damageType, (StaticAnimation) dynAnim, hand, consumesStatus, attackIdentifier);

        if (src instanceof IndirectEntityDamageSource)
            return new IndirectSimpleEgoDamageSource((IndirectEntityDamageSource) src, attackType, damageType, consumesStatus, attackIdentifier);

        return new SimpleEgoDamageSource((EntityDamageSource) src, attackType, damageType, consumesStatus, attackIdentifier);
    }

    private static String getAttackIdentifierFor(ItemStack itemIn, DynamicAnimation dynAnim, AttackTypes attackType, DamageTypes damageType) {

        if (!(itemIn.getItem() instanceof EgoWeaponsWeapon))
            return null;

        String deathMessage = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DEATH_MESSAGE).orElse(null);

        if (deathMessage == null) {
            deathMessage = ((EgoWeaponsWeapon) itemIn.getItem()).getDefaultKillIdentifier();
        }

        if (deathMessage == null) {
            switch (attackType) {
                case GENERIC: return null;
                case PIERCE: return "pierce";
                case SLASH: return "slash";
                case BLUNT: return "blunt";
            }
        }


        return deathMessage;
    }

    public static GenericEgoDamage.DamageTypes evaluateDamageType(LivingEntityPatch<?> patch) {

        if (patch == null)
            return GenericEgoDamage.DamageTypes.RED;

        if (patch.getServerAnimator() == null)
            return GenericEgoDamage.DamageTypes.RED;

        if (patch.getServerAnimator().animationPlayer == null)
            return GenericEgoDamage.DamageTypes.RED;

        if (patch.getServerAnimator().animationPlayer.getAnimation() == null)
            return GenericEgoDamage.DamageTypes.RED;

        DynamicAnimation dynAnim = patch.getServerAnimator().animationPlayer.getAnimation().getRealAnimation();


        GenericEgoDamage.DamageTypes damageType = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DAMAGE_TYPE).orElse(null);

        Hand hand = Hand.MAIN_HAND;

        ItemStack itemHandIdent = patch.getValidItemInHand(hand);

        if (dynAnim instanceof AttackAnimation) {
            AttackAnimation.Phase phase = ((AttackAnimation) dynAnim).getPhaseByTime(patch.getAnimator().getPlayerFor(dynAnim).getElapsedTime());
            hand = phase.getHand();
        }
        boolean ammoconsume = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);

        if (ammoconsume) {
            AmmoType lastFired = null;
            if (patch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("lastFired"))
                lastFired = AmmoType.values()[patch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("lastFired")];

            if (patch.getValidItemInHand(Hand.OFF_HAND).getOrCreateTag().contains("lastFired"))
                lastFired = AmmoType.values()[patch.getValidItemInHand(Hand.OFF_HAND).getOrCreateTag().getInt("lastFired")];

            if (lastFired != null) {
                damageType = lastFired.getDamageType();
            }
        }


        // Defaults Fallback
        if (damageType == null) {

            // Item Tag Level Check
            DamageDefaults modifiedDefaults = new DamageDefaults(damageType, AttackTypes.GENERIC);
            ItemStack itemHand = patch.getValidItemInHand(hand);
            if (!itemHand.isEmpty())
                getItemLevelDefaults(itemHand, modifiedDefaults);

            // If nothing is found, do entity level check:
            if (modifiedDefaults.getDamageType() == null || modifiedDefaults.getAttackType() == null) {
                getEntityLevelDefaults(patch.getOriginal(), modifiedDefaults);
            }

            damageType = modifiedDefaults.getDamageType() != null ? modifiedDefaults.getDamageType() : GenericEgoDamage.DamageTypes.RED;
        }

        return damageType;
    }
    private static void getItemLevelDefaults(ItemStack item, DamageDefaults dmgDef) {
        DamageDefaults standardDefaults = DamageResistanceSystem.getItemDefaults().getOrDefault(item.getItem(), null);
        DamageTypes damageType = null;
        AttackTypes attackType = null;

        // Item Tag Evaluation
        if (item.hasTag()) {
            assert item.getTag() != null;

            if (item.getTag().contains("attackType"))
                attackType = AttackTypes.fromString(item.getTag().getString("attackType"));

            if (item.getTag().contains("damageType"))
                damageType = DamageTypes.fromString(item.getTag().getString("damageType"));
        }

        if (dmgDef.getAttackType() == null) {
            if (attackType != null)
                dmgDef.setAttackType(attackType);
            else if (standardDefaults != null)
                dmgDef.setAttackType(standardDefaults.getAttackType());
        }

        if (dmgDef.getDamageType() == null) {
            if (damageType != null)
                dmgDef.setDamageType(damageType);
            else if (standardDefaults != null)
                dmgDef.setDamageType(standardDefaults.getDamageType());
        }

    }

    private static void getEntityLevelDefaults(LivingEntity entity, DamageDefaults dmgDef) {
        DamageDefaults standardDefaults = DamageResistanceSystem.getMobDefaults().getOrDefault(entity.getType(), null);
        DamageTypes damageType = null;
        AttackTypes attackType = null;

        // Item Tag Evaluation
        if (entity.getPersistentData().contains("attackType"))
            attackType = AttackTypes.fromString(entity.getPersistentData().getString("attackType"));

        if (entity.getPersistentData().contains("damageType"))
            damageType = DamageTypes.fromString(entity.getPersistentData().getString("damageType"));

        if (dmgDef.getAttackType() == null) {
            if (attackType != null)
                dmgDef.setAttackType(attackType);
            else if (standardDefaults != null)
                dmgDef.setAttackType(standardDefaults.getAttackType());
        }

        if (dmgDef.getDamageType() == null) {
            if (damageType != null)
                dmgDef.setDamageType(damageType);
            else if (standardDefaults != null)
                dmgDef.setDamageType(standardDefaults.getDamageType());
        }

    }

    public static void applyStaggerDamageGeneric(DamageSource src, float amount, LivingDamageEvent evt, LivingEntity self) {

        if (src == null)
            return;


        // Damage Detection here

        if (src.getEntity() instanceof LivingEntity) {
            if (((LivingEntity)src.getEntity()).getItemBySlot(EquipmentSlotType.MAINHAND).getItem().equals(EgoWeaponsItems.MIMICRY.get())) {
                MimicryItem.getDamageHeal(self, (LivingEntity) src.getEntity(), amount, src);
            }

            if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.MIMICRY_CHESTPLATE.get())) {
                MimicryArmor.getDamageReceivedBuff(self, amount);
            }
        }

        EgoWeaponsModVars.PlayerVariables entityData = self.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

        if (self instanceof PlayerEntity && entityData != null) {

            entityData.injury_threshold += amount / self.getMaxHealth();


            entityData.syncInjury(self);
        }


        float beforeModifiedDamagetypes = amount;

        if (src instanceof GenericEgoDamage) {
            GenericEgoDamage conv = (GenericEgoDamage) src;
            Random random = new Random();

            float randomX = random.nextFloat() * self.getBbWidth() * 1.5f - self.getBbWidth() * 0.75f;
            float randomZ = random.nextFloat() * self.getBbWidth() * 1.5f - self.getBbWidth() * 0.75f;
            float randomY = random.nextFloat() * self.getBbHeight() / 2 - self.getBbHeight() / 4 + self.getBbHeight()/2.3f;

            if (!conv.getAttackType().equals(AttackTypes.HIDDEN) && self.level.getGameRules().getBoolean(EgoWeaponsGamerules.ENABLE_DAMAGEINDICATORS))
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.DamageLabelParticle(self.position().add(randomX,randomY,randomZ), conv.getDamageType(), conv.getAttackType(), conv.getCrit(), amount, conv.getResistanceMult(), conv.getBonusMult()));

            // Lore accurate damage types modifier
            int damageConv = self.level.getGameRules().getInt(EgoWeaponsGamerules.LORE_DAMAGE_CONVERSION);
            if (damageConv > 0 && self instanceof PlayerEntity) {
                float percent = Math.min(1,damageConv / 100f);
                float convAmount = 0;
                float newAmount = 0;

                if (conv.getDamageType().equals(DamageTypes.WHITE)) {
                    convAmount = amount * percent;
                } else if (conv.getDamageType().equals(DamageTypes.BLACK)) {
                    convAmount = amount * percent * 0.5f;
                }

                if (convAmount > 0) {
                    newAmount = amount - convAmount;
                    SanitySystem.damageSanity((PlayerEntity) self, convAmount);
                }

                // Update new amount
                if (newAmount > 0 || convAmount > 0)
                    evt.setAmount(newAmount);
            }

            if (conv.getDamageType().equals(DamageTypes.PALE) && self instanceof PlayerEntity) {
                if (self.level.getGameRules().getBoolean(EgoWeaponsGamerules.PALE_DEALS_PERCENT)) {
                    amount = self.getMaxHealth() * amount * 0.01f;
                    evt.setAmount(amount);
                }
            }
        }



        // Ethernal Rest non solemn lament ability
        // Deal extra stagger damage and inflict sinking potency
        if (src.getEntity() instanceof LivingEntity) {
            if (((LivingEntity) src.getEntity()).hasEffect(EternalRestPotionEffect.get()) && !(EgoWeaponsItems.SOLEMN_LAMENT_WHITE.get().equals(((LivingEntity) src.getEntity()).getMainHandItem().getItem()))) {
                self.hurt(DamageSource.OUT_OF_WORLD, 2);
                StaggerSystem.reduceStagger(self, 5, false);
                EgoWeaponsEffects.SINKING.get().increment(self, 0, 2);
            }
        }


        // Stamina Regeneration on hit
        if (src.getEntity() instanceof PlayerEntity) {
            PlayerPatch<?> playerPatch = (PlayerPatch<?>) src.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            if (playerPatch.getStamina() < playerPatch.getMaxStamina())
                playerPatch.setStamina(Math.min(playerPatch.getStamina() + Math.min(amount * 0.15f, 0.3f), playerPatch.getMaxStamina()));
        }

        if (self.isAlive() && self.getHealth() > amount) {
            if (isStaggered(self)) {
                /*if (src.getEntity() instanceof LivingEntity)
                    onStaggered((LivingEntity) src.getEntity(), self);*/

            } else {
                reduceStagger(self, amount * 1.3f, src.getEntity(), true);
            }
        } else {

            if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.UDJAT_SUIT.get())) {
                if (UdjatArmor.evaluateAntiDeath(self, src.getEntity())) {
                    System.out.println("Trying to cancel death event");
                    evt.setCanceled(true);
                    return;
                }
            }
            if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.LCA_UDJAT_SUIT.get())) {
                if (LCAUdjatArmor.evaluateUdjatBrace(self, src.getEntity())) {
                    System.out.println("Trying to cancel death event");
                    evt.setCanceled(true);
                    return;
                }
            }
            if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SPIDER_TRACKSUIT.get())) {
                if (SpidersTracksuit.testTryDeathAvoid(self, amount)) {
                    System.out.println("Trying to cancel death event");
                    evt.setCanceled(true);
                    return;
                }
            }
            if (MugaDeathPrevention.testEffectPresence(self, amount)) {
                evt.setCanceled(true);
                return;
            }


            onKilled(src, self);
        }

    }


    private static float evaluateOnHitEffects(LivingEntity self, LivingEntity sourceEntity, float multiplier, DamageSource source, boolean staggered, boolean onHit) {
        if (onHit) {
            EgoWeaponsModVars.PlayerVariables entityData = sourceEntity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


            if (sourceEntity.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get())) {
                EgoWeaponsEffects.BURN.get().increment(self, 0, 1);

                int burnPotencyTarget = EgoWeaponsEffects.BURN.get().getPotency(self);
                int burnPotencySource = EgoWeaponsEffects.BURN.get().getPotency(self);

                float mult1 = Math.min(burnPotencyTarget * 0.01f, 0.15f);
                float mult2 = Math.min(burnPotencySource * 0.02f, 0.20f);

                SharedFunctions.incrementBonusDamage(source, mult1 + mult2);
                multiplier += mult1 + mult2;

                if (entityData.onHitCounter < 5) {
                    entityData.onHitCounter = 5;
                    entityData.syncPlayerVariables(sourceEntity);
                }

            }
        }

        return multiplier;
    }

    public static void increaseSkillResource(DamageSource src, PlayerEntity plr, float amount) {

        if (!(src instanceof ExtendedDamageSource))
            return;

        if (plr.level.isClientSide())
            return;
        PlayerPatch<?> plrPatch = (PlayerPatch<?>) plr.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        plrPatch.gatherDamageDealt((ExtendedDamageSource) src, amount);
        /*SkillContainer skl = plrPatch.getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK);
        System.out.println("Res is: "+skl.getResource()+" needed is: "+skl.getNeededResource()+" amount is: "+amount);
        skl.setResource(skl.getResource()+Math.min(amount,skl.getNeededResource()));

        if (skl.getNeededResource() <= 0) {
            skl.activate();
        }*/
    }



}
