package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TCorpParticleRegistry;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.entities.DawnOfGreenDoubtEntity;
import net.m3tte.tcorp.entities.MagicBulletProjectile;
import net.m3tte.tcorp.entities.NothingThere2Entity;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.gameasset.TCorpMobAnimations;
import net.m3tte.tcorp.network.packages.AbilityPackages;
import net.m3tte.tcorp.potion.*;
import net.m3tte.tcorp.world.capabilities.EmotionSystem;
import net.m3tte.tcorp.world.capabilities.SanitySystem;
import net.m3tte.tcorp.world.capabilities.StaggerSystem;
import net.m3tte.tcorp.world.capabilities.entitypatch.DoubtAPatch;
import net.m3tte.tcorp.world.capabilities.entitypatch.NothingTherePatch;
import net.m3tte.tcorp.world.capabilities.entitypatch.StaggerableEntity;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.tcorp.world.capabilities.StaggerSystem.*;
import static net.m3tte.tcorp.world.capabilities.StaggerSystem.isStaggered;

public class SharedFunctions {
    public static SimpleSound[] warningSounds = {SimpleSound.forMusic(TCorpSounds.FIRST_WARNING), SimpleSound.forMusic(TCorpSounds.SECOND_WARNING), SimpleSound.forMusic(TCorpSounds.THIRD_WARNING), SimpleSound.forMusic(TCorpSounds.FOURTH_WARNING)};


    public static void pummelDownEntity(LivingEntityPatch<?> patch, int strength) {
        patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
            patch.playAnimationSynchronized(TCorpAnimations.PUMMEL_DOWN, 0);
            return;
        }

        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getGroundAnimation(strength);

            if (stunAnim != null)
                patch.playAnimationSynchronized(stunAnim, 0);
        }

    }

    public static void pummelUpEntity(LivingEntityPatch<?> patch, int strength) {
        patch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
        if (patch.getHitAnimation(ExtendedDamageSource.StunType.KNOCKDOWN).getId() == Animations.BIPED_KNOCKDOWN.getId()) {
            patch.playAnimationSynchronized(TCorpAnimations.LIFT_UP, 0);
            return;
        }

        if (patch instanceof StaggerableEntity) {
            StaticAnimation stunAnim = ((StaggerableEntity) patch).getLiftAnimation(strength);

            if (stunAnim != null)
                patch.playAnimationSynchronized(stunAnim, 0);
        }

    }

    public static float modifyDamageGeneric(float amount, DamageSource source, LivingEntity self) {

        // Reduce inbound damage for nothing there by 30% on ranged attacks.
        if (self instanceof NothingThere2Entity) {
            if (source.getDirectEntity() != source.getEntity() || source.isProjectile()) {
                amount *= 0.7f;
            }
        }

        if (source.isProjectile()) {
            if (source.getDirectEntity() instanceof MagicBulletProjectile.MagicBulletProj) {
                source.setMagic();
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
                if (doubtAPatch.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.DOUBT_AUTO_B1.getId()) {
                    pummelDownEntity(entitypatch, 2);
                    doubtAPatch.reserveAnimation(TCorpMobAnimations.DOUBT_AUTO_B2);
                    doubtEntity.getPersistentData().putInt("pounceHits", 0);
                }
                int hitCount = 0;
                if (doubtEntity.getPersistentData().contains("pounceHits")) {
                    hitCount = doubtEntity.getPersistentData().getInt("pounceHits");
                }


                if (hitCount < 3 && doubtAPatch.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.DOUBT_AUTO_B2.getId() && !self.level.isClientSide() && self.level.random.nextFloat() < 0.75f) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2);
                    doubtAPatch.reserveAnimation(TCorpMobAnimations.DOUBT_AUTO_B2);
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

                if (ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.NT_AUTO_STAB.getId()) {
                    ntEntity.heal(10);
                    ntEntity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 200, 1));
                    ntEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 200, 1));
                }

                if (ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.NT_DASH_C.getId()) {
                    ntPatch.playAnimationSynchronized(TCorpMobAnimations.NT_DASH_C_F, 0.01f);
                }
                
                if (ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.NT_DASH_B.getId() && !self.hasEffect(EpicFightMobEffects.STUN_IMMUNITY.get())) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2);
                    ntPatch.playAnimationSynchronized(TCorpMobAnimations.NT_DASH_B_F, 0.01f);
                    StaggerSystem.reduceStagger(self, 6, false);
                    ntEntity.getPersistentData().putInt("pounceHits", 0);
                }
                int hitCount = 0;
                if (ntEntity.getPersistentData().contains("pounceHits")) {
                    hitCount = ntEntity.getPersistentData().getInt("pounceHits");
                }


                if (hitCount < 2 && ntPatch.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.NT_DASH_B_F.getId() && !self.level.isClientSide() && (self.level.random.nextFloat() < 0.75f || self.hasEffect(Staggered.get()))) {
                    entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    pummelDownEntity(entitypatch, 2);
                    ntPatch.playAnimationSynchronized(TCorpMobAnimations.NT_DASH_B_F, 0.01f);
                    StaggerSystem.reduceStagger(self, 3, false);
                    ntEntity.getPersistentData().putInt("pounceHits", hitCount+1);
                } else if (hitCount > 0)  {
                    ntEntity.getPersistentData().remove("pounceHits");
                }
            }


        }

        // Attacker gains emotion points
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity srcEntity = (PlayerEntity) source.getEntity();
            increaseSkillResource(source, (PlayerEntity) source.getEntity(), 5);
            EmotionSystem.increaseEmotionPoints(srcEntity, (int) amount / 2 + 3);

        }

        // 30% Damage reduction if the player has "I love you"
        if(self.hasEffect(ILoveYou.get())) {
            ILoveYou.onHit(source.getEntity(), self);

            amount *= 0.7f;
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
                    amount *= 0.8f;

                }
            }

            int potency = self.getEffect(Shell.get()).getAmplifier() + 1;
            if (potency > 5) potency = 5; // Top off potency so it cant block all damage

            amount *= (1f - 0.1f*potency);
            self.level.playSound(null,self.blockPosition(), TCorpSounds.BLACK_SILENCE_ZELKOVA_MACE, SoundCategory.PLAYERS, 1, 1);
            if (self instanceof PlayerEntity) {
                PlayerPatch<?> entitypatch = (PlayerPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                self.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 5 * potency, 0));
                entitypatch.playAnimationSynchronized(TCorpAnimations.RANGA_GUARD_HIT, 0);
            }
        }

        // Stagger Logic

        if (isStaggered(self)) {
            stagger(self, (n) -> {
                if (source.getEntity() instanceof PlayerEntity)
                    onStaggered((PlayerEntity) source.getEntity(), self);
            });
            amount *= 1.5f;
        }

        return amount;
    }

    private static void onKilled(DamageSource src, LivingEntity self) {
        if (src.getEntity() instanceof PlayerEntity) {
            PlayerEntity source = (PlayerEntity) src.getEntity();

            PlayerPatch<?> entitypatch = (PlayerPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();
            final int anim_id = currentanim.getId();

            if (anim_id == TCorpAnimations.KALI_ONRUSH.getId()) {
                if (!self.level.isClientSide()) {
                    source.level.playSound(null,  self.getX(), self.getY(), self.getZ(),TCorpSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1);
                }

                source.getPersistentData().putInt("onrushChain", 3);
                TcorpMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
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

        if (anim_id == TCorpAnimations.KALI_ONRUSH.getId()) {
            if (!self.level.isClientSide()) {
                source.level.playSound(null,  self.getX(), self.getY(), self.getZ(),TCorpSounds.FINGER_SNAP, SoundCategory.PLAYERS, 1, 1);
            }

            source.getPersistentData().putInt("onrushChain", 3);
            TcorpMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                    new AbilityPackages.SyncOnrushData(source.getId(), 3));
        }
    }

    /*public static void checkBeforeDamageApply(DamageSource src, float amount, CallbackInfo ci, LivingEntity self) {

    }*/



    public static void applyStaggerDamageGeneric(DamageSource src, float amount, CallbackInfo ci, LivingEntity self) {

        if (src == null)
            return;

        if (src.getEntity() instanceof NothingThere2Entity) {
            LivingEntityPatch<?> livingPatch = (LivingEntityPatch<?>) src.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            int animid = livingPatch.getServerAnimator().animationPlayer.getAnimation().getId();
            if (livingPatch != null) {
                if (animid == TCorpMobAnimations.NT_GOODBYE.getId() || animid == TCorpAnimations.MIMICRY_GOODBYE.getId()) {
                    if (self.getHealth() <= amount) {

                        if (self.level instanceof ServerWorld) {
                            ((ServerWorld) self.level).sendParticles(TCorpParticleRegistry.MEAT_CHUNK_EXPLOSION.get(), self.position().x, self.position().y, self.position().z, (int) 1, 0, 0, 0, 0);
                        }
                        self.level.playSound(null, self.blockPosition(),
                                TCorpSounds.NOTHING_THERE_GOODBYE_KILL,
                                SoundCategory.PLAYERS, (float) 1, (float) 1.5);
                    }
                }
            }
        }

        if (src.getEntity() instanceof LivingEntity) {
            if (((LivingEntity) src.getEntity()).hasEffect(EthernalRestPotionEffect.get()) && !(TCorpItems.SOLEMN_LAMENT_WHITE.get().equals(((LivingEntity) src.getEntity()).getMainHandItem().getItem()))) {

                self.hurt(DamageSource.OUT_OF_WORLD, 2);
                StaggerSystem.reduceStagger(self, 5, false);
                if (self instanceof PlayerEntity)
                    SanitySystem.damageSanity((PlayerEntity) self, 2.5f);

            }
        }

        if (src.getEntity() instanceof PlayerEntity) {
            PlayerPatch<?> playerPatch = (PlayerPatch<?>) src.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            if (playerPatch.getStamina() < playerPatch.getMaxStamina())
                playerPatch.setStamina(Math.min(playerPatch.getStamina() + Math.min(amount * 0.04f, 0.2f), playerPatch.getMaxStamina()));
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
