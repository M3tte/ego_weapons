package net.m3tte.tcorp.mixin;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.potion.ILoveYou;
import net.m3tte.tcorp.potion.Shell;
import net.m3tte.tcorp.potion.Terror;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.tcorp.world.capabilities.stagger.StaggerSystem.*;

@Mixin(PlayerEntity.class)
public class PlayerEntityDamageMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V")
    public void applyStaggerDamage(DamageSource src, float amount, CallbackInfo ci) {
        LivingEntity self = ((LivingEntity) (Object)this);
        if (self.isAlive()) {
            if (isStaggered(self)) {
                stagger(self);
            } else {
                reduceStagger(self, amount * 2f);
            }
        }

    }
    @ModifyVariable(at = @At(value = "HEAD"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", ordinal = 0, argsOnly = true)
    public float modifyDamageMixin(float amount, DamageSource source) {

        LivingEntity self = ((LivingEntity) (Object)this);
        // Modifier for Mimicry
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
            stagger(self);
            amount *= 1.5f;
        }

        return amount;
    }



}
