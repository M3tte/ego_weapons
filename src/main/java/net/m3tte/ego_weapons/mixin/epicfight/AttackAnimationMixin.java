package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.gui.ingame.PlayerStatsIndicator;
import net.m3tte.ego_weapons.gui.ingame.StatusEffectGUI;
import net.m3tte.ego_weapons.gui.ingame.ThreatLevelGUI;
import net.m3tte.ego_weapons.potion.countEffects.BleedEffect;
import net.m3tte.ego_weapons.world.capabilities.threatlevel.ThreatLevelSystem;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.client.gui.EntityIndicator;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = AttackAnimation.class, remap = false)
public class AttackAnimationMixin {
    // Lyesman/epicfight/client/gui/EntityIndicator;init()V
    // Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;playSound(Lnet/minecraft/util/SoundEvent;FF)V
    @Inject(at = @At(value = "INVOKE", target = "Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;playSound(Lnet/minecraft/util/SoundEvent;FF)V"), method = "tick(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;)V")
    private void initializeCustomIndicators(LivingEntityPatch<?> entitypatch, CallbackInfo ci) {
        LivingEntity target = entitypatch.getOriginal();
        if (target.hasEffect(EgoWeaponsEffects.BLEED.get())) {
            BleedEffect.apply(entitypatch.getOriginal());
        }
    }
}
