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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

@Mixin(LivingEntity.class)
public class DamageMixin {

    @ModifyVariable(at = @At(value = "HEAD"), method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", ordinal = 0, argsOnly = true)
    public float modifyDamageMixin(float amount, DamageSource source) {

        LivingEntity self = ((LivingEntity) (Object)this);
        System.out.println("Hit for: "+amount);
        // Modifier for Mimicry
        if(self.hasEffect(ILoveYou.get())) {
            System.out.println("Loveyou initiated");
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



        return amount;
    }
}
