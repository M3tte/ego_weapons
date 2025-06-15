package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsMobAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.MimicryMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperArmor;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperWeapon;
import net.m3tte.ego_weapons.item.heishou_mao.HeishouMaoSword;
import net.m3tte.ego_weapons.item.liu.LiuFireGauntlet;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBullet;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBulletArmor;
import net.m3tte.ego_weapons.item.mimicry.MimicryArmor;
import net.m3tte.ego_weapons.item.mimicry.MimicryItem;
import net.m3tte.ego_weapons.item.oeufi.OeufiHalberd;
import net.m3tte.ego_weapons.item.stigma_workshop.StigmaWorkshopSword;
import net.m3tte.ego_weapons.item.sunshower.Sunshower;
import net.m3tte.ego_weapons.network.packages.AbilityPackages;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.potion.*;
import net.m3tte.ego_weapons.potion.countEffects.*;
import net.m3tte.ego_weapons.world.capabilities.*;
import net.m3tte.ego_weapons.world.capabilities.damage.*;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.DoubtAPatch;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.NothingTherePatch;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.StaggerableEntity;
import net.m3tte.ego_weapons.world.capabilities.gamerules.EgoWeaponsGamerules;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.IndirectEpicFightDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Random;
import java.util.function.Consumer;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.canProcEffects;
import static net.m3tte.ego_weapons.item.fullstop_rep.FullstopRepWeapon.critDamageCalculations;
import static net.m3tte.ego_weapons.world.capabilities.StaggerSystem.*;
import static net.m3tte.ego_weapons.world.capabilities.StaggerSystem.isStaggered;

public class SharedFunctions {


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
    public static void pummelDownEntity(LivingEntityPatch<?> patch, int strength) {
        if (patch == null)
            return;

        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                patch.playAnimationSynchronized(EgoWeaponsAnimations.PUMMEL_DOWN, 0);
                return;
            }
        }


        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getGroundAnimation(strength);

            if (stunAnim != null)
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

    public static void hitstunEntity(LivingEntityPatch<?> patch, int strength, boolean stunImmunity, float time) {

        if (patch == null)
            return;

        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (stunImmunity)
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
            if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
                patch.playAnimationSynchronized(EgoWeaponsAnimations.LONG_HITSTUN, time);
                return;
            }
        }


        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getHitstunAnimation(strength);

            if (stunAnim != null) {
                if (stunImmunity)
                    patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
                patch.playAnimationSynchronized(stunAnim, time);
            }

        }

    }

    public static void staggerEntity(LivingEntityPatch<?> patch, int strength, boolean stunImmunity) {
        if (patch == null)
            return;

        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN) != null) {
            if (stunImmunity)
                patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
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

    public static void incrementBonusDamage(DamageSource source, float factor) {
        if (source instanceof GenericEgoDamage) {
            ((GenericEgoDamage) source).setBonusMult(((GenericEgoDamage) source).getBonusMult() + (factor));
        }
    }

    public static void incrementResistanceDamage(DamageSource source, float factor) {
        if (source instanceof GenericEgoDamage) {
            System.out.println("Prev Resistance Val: "+((GenericEgoDamage) source).getResistanceMult());
            ((GenericEgoDamage) source).setResistanceMult(((GenericEgoDamage) source).getResistanceMult() + factor);

            System.out.println("Post Resistance Val: "+((GenericEgoDamage) source).getResistanceMult());
        }
    }


    public static float modifyDamageGeneric(float amount, DamageSource source, LivingEntity self) {

        // Poise Damage Increase
        // If source has poise, 5% crit chance per poise


        boolean hitCooldownStart = false;

        boolean doesProcEffects = canProcEffects(source.getEntity() != null ? (LivingEntity) source.getEntity() : null);

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





        // Damage Modify Effects
        if (source.getEntity() instanceof LivingEntity) {
            // Animation Bound Effects
            multiplier = evaluateAnimationEffects(self, (LivingEntity) source.getEntity(), multiplier, source);

            if (((LivingEntity)source.getEntity()).getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.MAGIC_BULLET_CLOAK.get())) {
                MagicBulletArmor.poiseEffect(self, (LivingEntity) source.getEntity(), amount, source);
            }

            Item mainHandItem = ((LivingEntity)source.getEntity()).getItemBySlot(EquipmentSlotType.MAINHAND).getItem();

            if (mainHandItem != null) {
                if (mainHandItem.equals(EgoWeaponsItems.MIMICRY.get())) {
                    multiplier = MimicryItem.modifyDamageNormal(self, (LivingEntity) source.getEntity(), multiplier, source);

                }

                if (mainHandItem.equals(EgoWeaponsItems.OEUFI_HALBERD.get())) {
                    multiplier = OeufiHalberd.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                }

                if (mainHandItem.equals(EgoWeaponsItems.MAGIC_BULLET.get())) {
                    multiplier = MagicBullet.damageMultiplier((LivingEntity) source.getEntity(), self, multiplier, source);
                }

                if (mainHandItem.equals(EgoWeaponsItems.LIU_FIRE_GAUNTLET.get())) {
                    multiplier = LiuFireGauntlet.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                }

                if (mainHandItem.equals(EgoWeaponsItems.FIREFIST_GAUNTLET.get())) {
                    multiplier = FirefistGauntlet.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                }

                if (mainHandItem.equals(EgoWeaponsItems.STIGMA_WORKSHOP_SWORD.get())) {
                    multiplier = StigmaWorkshopSword.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                }

                if (mainHandItem.equals(EgoWeaponsItems.HEISHOU_MAO_SWORD.get())) {
                    multiplier = HeishouMaoSword.modifyDamageAmount(self, (LivingEntity) source.getEntity(), multiplier, source);
                }
            }



            if (((LivingEntity)source.getEntity()).getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FULLSTOP_SNIPER_SUIT.get()) && source.getEntity() instanceof LivingEntity) {
                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) source.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                if (sourcePatch != null) {
                    multiplier = FullstopSniperArmor.hitDamageBonus(sourcePatch, multiplier, source);
                }
            }
        }


        // Apply protection from sunshower sinking.
        if (EgoWeaponsEffects.SINKING.get().getPotency(self) > 1 && source.getEntity() instanceof LivingEntity && self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUNSHOWER_CLOAK.get())) {
           ((ProtectionEffect) EgoWeaponsEffects.PROTECTION.get()).increment(self, Math.min((EgoWeaponsEffects.SINKING.get().getPotency(self) / 3)-1, 4));
        }

        // Sunshower interrupt effect
        if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUNSHOWER.get())) {
            Sunshower.interruptedAttack(self);
        }


        // Apply protection from PROTECTION stacks.
        if (self.hasEffect(EgoWeaponsEffects.PROTECTION.get()) && source.getEntity() instanceof LivingEntity) {
            incrementBonusDamage(source, -multiplier*EgoWeaponsEffects.PROTECTION.get().getPotency(self)*0.1f);
            multiplier *= (1 - EgoWeaponsEffects.PROTECTION.get().getPotency(self)*0.1f);
        }

        // Decrement resilience stacks
        if (self.hasEffect(EgoWeaponsEffects.RESILIENCE.get()) && doesProcEffects) {
            EgoWeaponsEffects.RESILIENCE.get().decrement(self, 0, 1);
        }

        // Apply SINKING damage
        if (self.hasEffect(EgoWeaponsEffects.SINKING.get()) && doesProcEffects) {
            SinkingEffect.applyOnHit(self);
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


        if (source.getEntity() instanceof NothingThere2Entity) {
            if (((NothingThere2Entity) source.getEntity()).hasEffect(Shell.get())) {
                ((NothingThere2Entity) source.getEntity()).heal(1.5f * (((NothingThere2Entity) source.getEntity()).getEffect(Shell.get()).getAmplifier()+1));
            }
        }

        // Special followup attacks for DOUBT
        if (source.getEntity() instanceof DawnOfGreenDoubtEntity) {
            DawnOfGreenDoubtEntity doubtEntity = (DawnOfGreenDoubtEntity) source.getEntity();
            DoubtAPatch doubtAPatch = (DoubtAPatch) doubtEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {
                if (doubtAPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.DOUBT_AUTO_B1.getId()) {
                    pummelDownEntity(entitypatch, 2);
                    doubtAPatch.reserveAnimation(EgoWeaponsMobAnimations.DOUBT_AUTO_B2);
                    doubtEntity.getPersistentData().putInt("pounceHits", 0);
                }
                int hitCount = 0;
                if (doubtEntity.getPersistentData().contains("pounceHits")) {
                    hitCount = doubtEntity.getPersistentData().getInt("pounceHits");
                }


                if (hitCount < 3 && doubtAPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.DOUBT_AUTO_B2.getId() && !self.level.isClientSide() && self.level.random.nextFloat() < 0.75f) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2);
                    doubtAPatch.reserveAnimation(EgoWeaponsMobAnimations.DOUBT_AUTO_B2);
                    doubtEntity.getPersistentData().putInt("pounceHits", hitCount+1);
                } else if (hitCount > 0)  {
                    doubtEntity.getPersistentData().remove("pounceHits");
                }
            }


        }

        // Special Followup attacks for NOTHING THERE
        if (source.getEntity() instanceof NothingThere2Entity) {
            NothingThere2Entity ntEntity = (NothingThere2Entity) source.getEntity();
            NothingTherePatch ntPatch = (NothingTherePatch) ntEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {

                if (ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.NT_AUTO_STAB.getId()) {
                    ntEntity.heal(10);
                    ntEntity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 200, 1));
                    ntEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 200, 1));
                }

                if (ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.NT_DASH_C.getId()) {
                    ntPatch.playAnimationSynchronized(EgoWeaponsMobAnimations.NT_DASH_C_F, 0.01f);
                }
                
                if (ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.NT_DASH_B.getId() && !self.hasEffect(EpicFightMobEffects.STUN_IMMUNITY.get())) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2);
                    ntPatch.playAnimationSynchronized(EgoWeaponsMobAnimations.NT_DASH_B_F, 0.01f);
                    StaggerSystem.reduceStagger(self, 6, false);
                    ntEntity.getPersistentData().putInt("pounceHits", 0);
                }
                int hitCount = 0;
                if (ntEntity.getPersistentData().contains("pounceHits")) {
                    hitCount = ntEntity.getPersistentData().getInt("pounceHits");
                }


                if (hitCount < 2 && ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.NT_DASH_B_F.getId() && !self.level.isClientSide() && (self.level.random.nextFloat() < 0.75f || self.hasEffect(Staggered.get()))) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2);
                    ntPatch.playAnimationSynchronized(EgoWeaponsMobAnimations.NT_DASH_B_F, 0.01f);
                    StaggerSystem.reduceStagger(self, 3, false);
                    ntEntity.getPersistentData().putInt("pounceHits", hitCount+1);
                } else if (hitCount > 0)  {
                    ntEntity.getPersistentData().remove("pounceHits");
                }
            }


        }



        // 30% Damage reduction if the player has "I love you"
        if(self.hasEffect(ILoveYou.get())) {
            ILoveYou.onHit(source.getEntity(), self);

            incrementResistanceDamage(source, -multiplier * 0.3f);
            multiplier *= 0.7f;
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
        if (self.hasEffect(Shell.get())) {



            if (source.getEntity() instanceof LivingEntity) {
                if (((LivingEntity) source.getEntity()).hasEffect(Terror.get())) {
                    incrementResistanceDamage(source, -0.2f);
                    multiplier -= 0.2f;
                }
            }

            int potency = self.getEffect(Shell.get()).getAmplifier() + 1;
            if (potency > 5) potency = 5; // Top off potency so it cant block all damage

            incrementResistanceDamage(source, -0.1f * potency);
            multiplier -= (0.1f*potency);
            self.level.playSound(null,self.blockPosition(), EgoWeaponsSounds.BLACK_SILENCE_ZELKOVA_MACE, SoundCategory.PLAYERS, 1, 1);
            if (self instanceof PlayerEntity) {
                PlayerPatch<?> entitypatch = (PlayerPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                self.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 5 * potency, 0));
                entitypatch.playAnimationSynchronized(BlackSilenceMovesetAnims.RANGA_GUARD_HIT, 0);
            }
        }

        multiplier = evaluateOnHitEffects(self, (LivingEntity) source.getEntity(), multiplier, source, isStaggered(self), hitCooldownStart);

        // Apply all flat damage modifiers
        multiplier = DamageResistanceSystem.processDamageForEntity(self, (LivingEntity) source.getEntity(), multiplier, source, isStaggered(self));

        amount *= multiplier;


        // Attacker gains emotion points
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity srcEntity = (PlayerEntity) source.getEntity();
            increaseSkillResource(source, (PlayerEntity) source.getEntity(), 5);
            EmotionSystem.increaseEmotionPoints(srcEntity, (int) amount / 2 + 3, true);
        }



        // Stagger Logic
        if (isStaggered(self)) {
            stagger(self, (n) -> {
                if (source.getEntity() instanceof PlayerEntity)
                    onStaggered((PlayerEntity) source.getEntity(), self);
            });
        }

        return amount;
    }

    private static float evaluateAnimationEffects(LivingEntity target, LivingEntity source, float amount, DamageSource damageSource) {
        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch == null)
            return amount;

        DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();


        boolean finale = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);

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
        if (src.getEntity() instanceof PlayerEntity) {
            PlayerEntity source = (PlayerEntity) src.getEntity();

            PlayerPatch<?> entitypatch = (PlayerPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();
            final int anim_id = currentanim.getId();

            Item it = source.getItemBySlot(EquipmentSlotType.CHEST).getItem();

            if (anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_3.getId() || anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_2.getId() || anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_1.getId()) {
                source.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().putBoolean("ffkilled", true);
                if (src.getEntity() instanceof LivingEntity) {
                    if (src.getEntity() instanceof PlayerEntity)
                        BlipTick.chargeBlips((PlayerEntity) src.getEntity(), 1, true);
                    EgoWeaponsEffects.POWER_UP.get().increment((LivingEntity) src.getEntity(), 4, 2);
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
                        new AbilityPackages.SyncOnrushData(source.getId(), 3));
            }
        }
    }

    public static void onStaggered(PlayerEntity source, LivingEntity self) {
        // On Stagger / On Death effects

        if (source == null)
            return;

        PlayerPatch<?> entitypatch = (PlayerPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();
        final int anim_id = currentanim.getId();

        if (anim_id == FirefistMovesetAnims.FIREFIST_SPECIAL_3.getId()) {
            EgoWeaponsEffects.POWER_UP.get().increment(source, 4, 2);
        }

        if (anim_id == MimicryMovesetAnims.KALI_ONRUSH.getId()) {
            if (!self.level.isClientSide()) {
                source.level.playSound(null,  self.getX(), self.getY(), self.getZ(), EgoWeaponsSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1);
            }

            source.getPersistentData().putInt("onrushChain", 3);
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                    new AbilityPackages.SyncOnrushData(source.getId(), 3));
        }
    }

    /*public static void checkBeforeDamageApply(DamageSource src, float amount, CallbackInfo ci, LivingEntity self) {

    }*/


    public static DamageSource evaluateDamageSource(DamageSource src) {


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

        ItemStack itemHandIdent = patch.getValidItemInHand(hand);

        if (dynAnim instanceof AttackAnimation) {
            AttackAnimation.Phase phase = ((AttackAnimation)dynAnim).getPhaseByTime(patch.getAnimator().getPlayerFor(dynAnim).getElapsedTime());
            hand = phase.getHand();
        }
        boolean ammoconsume = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
        boolean consumesStatus = dynAnim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TRIGGERS_EFFECTS).orElse(true);

        if (ammoconsume) {
            AmmoType lastFired = null;
            if (patch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("lastFired"))
                lastFired = AmmoType.values()[patch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("lastFired")];

            if (patch.getValidItemInHand(Hand.OFF_HAND).getOrCreateTag().contains("lastFired"))
                lastFired = AmmoType.values()[patch.getValidItemInHand(Hand.OFF_HAND).getOrCreateTag().getInt("lastFired")];

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

    public static void applyStaggerDamageGeneric(DamageSource src, float amount, CallbackInfo ci, LivingEntity self) {

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


        if (src instanceof GenericEgoDamage) {
            GenericEgoDamage conv = (GenericEgoDamage) src;
            Random random = new Random();

            float randomX = random.nextFloat() * self.getBbWidth() * 1.5f - self.getBbWidth() * 0.75f;
            float randomZ = random.nextFloat() * self.getBbWidth() * 1.5f - self.getBbWidth() * 0.75f;
            float randomY = random.nextFloat() * self.getBbHeight() / 2 - self.getBbHeight() / 4 + self.getBbHeight()/2.3f;

            if (!conv.getAttackType().equals(AttackTypes.HIDDEN) && self.level.getGameRules().getBoolean(EgoWeaponsGamerules.ENABLE_DAMAGEINDICATORS))
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DamageLabelParticle(self.position().add(randomX,randomY,randomZ), conv.getDamageType(), conv.getAttackType(), conv.getCrit(), amount, conv.getResistanceMult(), conv.getBonusMult()));
        }

        if (src.getEntity() instanceof NothingThere2Entity) {
            LivingEntityPatch<?> livingPatch = (LivingEntityPatch<?>) src.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            int animid = livingPatch.getServerAnimator().animationPlayer.getAnimation().getId();
            if (livingPatch != null) {
                if (animid == EgoWeaponsMobAnimations.NT_GOODBYE.getId() || animid == MimicryMovesetAnims.MIMICRY_GOODBYE.getId()) {
                    if (self.getHealth() <= amount) {

                        if (self.level instanceof ServerWorld) {
                            ((ServerWorld) self.level).sendParticles(EgoWeaponsParticles.MEAT_CHUNK_EXPLOSION.get(), self.position().x, self.position().y, self.position().z, (int) 1, 0, 0, 0, 0);
                        }
                        self.level.playSound(null, self.blockPosition(),
                                EgoWeaponsSounds.NOTHING_THERE_GOODBYE_KILL,
                                SoundCategory.PLAYERS, (float) 1, (float) 1.5);
                    }
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
                if (src.getEntity() instanceof PlayerEntity)
                    onStaggered((PlayerEntity) src.getEntity(), self);

            } else {
                reduceStagger(self, amount * 1.3f, src.getEntity(), true);
            }
        } else {
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
