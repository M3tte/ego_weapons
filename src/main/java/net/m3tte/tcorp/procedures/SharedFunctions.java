package net.m3tte.tcorp.procedures;

import jdk.nashorn.internal.codegen.CompilerConstants;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.network.packages.AbilityPackages;
import net.m3tte.tcorp.potion.ILoveYou;
import net.m3tte.tcorp.potion.Shell;
import net.m3tte.tcorp.potion.Terror;
import net.m3tte.tcorp.world.capabilities.EmotionSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.tcorp.world.capabilities.StaggerSystem.*;
import static net.m3tte.tcorp.world.capabilities.StaggerSystem.isStaggered;

public class SharedFunctions {

    public static float modifyDamageGeneric(float amount, DamageSource source, LivingEntity self) {
        if(self.hasEffect(ILoveYou.get())) {
            ILoveYou.onHit(source.getEntity(), self);

            amount *= 0.7f;
        }

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

    public static void applyStaggerDamageGeneric(DamageSource src, float amount, CallbackInfo ci, LivingEntity self) {

        if (src == null)
            return;

        if (self.isAlive() && self.getHealth() > amount) {

            if (isStaggered(self)) {
                if (src.getEntity() instanceof PlayerEntity)
                    onStaggered((PlayerEntity) src.getEntity(), self);

            } else {
                reduceStagger(self, amount * 2, (n) -> {
                    if (src.getEntity() != null)
                        onStaggered((PlayerEntity) src.getEntity(), self);
                });
            }
        } else {
            onKilled(src, self);
        }



        if (src.getEntity() instanceof PlayerEntity) {
            PlayerEntity source = (PlayerEntity) src.getEntity();

            EmotionSystem.increaseEmotionPoints(source, (int) amount);
        }
    }

}
