package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.potion.countEffects.BleedEffect;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Consumer;

@Mixin(value = AttackAnimation.class, remap = false)
public class AttackAnimationMixin {


    // Lyesman/epicfight/client/gui/EntityIndicator;init()V
    // Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;playSound(Lnet/minecraft/util/SoundEvent;FF)V
    @Inject(at = @At(value = "INVOKE", target = "Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;playSound(Lnet/minecraft/util/SoundEvent;FF)V"), method = "tick(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;)V")
    private void initializeCustomIndicators(LivingEntityPatch<?> entitypatch, CallbackInfo ci) {
        LivingEntity target = entitypatch.getOriginal();

        if (target != null) {
            AttackAnimation self = ((AttackAnimation) ((Object)this));

            AttackAnimation.Phase phase = null;
            if (self instanceof EgoAttackAnimation) {
                phase = ((EgoAttackAnimation)self).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(self).getElapsedTime());
            }

            boolean triggersEffects = (self.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.TRIGGERS_EFFECTS).orElse(true);
            Consumer<LivingEntityPatch<?>> swingTriggerEvent = (self.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.SWING_EFFECT).orElse(null);


            if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
                Boolean elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EVENT).orElse(null);
                Consumer<LivingEntityPatch<?>> plc = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.SWING_EFFECT).orElse(null);

                if (elp != null)
                    triggersEffects = elp;

                if (plc != null)
                    swingTriggerEvent = plc;
            }

            if (target.hasEffect(EgoWeaponsEffects.BLEED.get())) {







                if (triggersEffects)
                    BleedEffect.apply(target);
            }


            if (swingTriggerEvent != null) {
                swingTriggerEvent.accept(entitypatch);
            }

        }


    }
}
