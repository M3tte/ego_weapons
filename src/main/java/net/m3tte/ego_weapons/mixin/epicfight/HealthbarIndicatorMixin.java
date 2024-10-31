package net.m3tte.ego_weapons.mixin.epicfight;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.gui.HealthBarIndicator;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(value = HealthBarIndicator.class, remap = false)
public class HealthbarIndicatorMixin {
    // Lyesman/epicfight/client/gui/EntityIndicator;init()V

    @Inject(at = @At(value = "HEAD"), method = "shouldDraw(Lnet/minecraft/client/entity/player/ClientPlayerEntity;Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
    private void disableBar(ClientPlayerEntity player, LivingEntity entityIn, CallbackInfoReturnable<Boolean> cir) {
        // If a player entity, do not ever display the bar as it is replaced.
        if (entityIn instanceof PlayerEntity) {
            cir.setReturnValue(false);
            cir.cancel();
        }


    }

    @ModifyVariable(method = "drawIndicator(Lnet/minecraft/entity/LivingEntity;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;F)V", at = @At("STORE"), ordinal = 0)
    private Collection<EffectInstance> removeEffectRenderer(Collection<EffectInstance> activeEffects) {
        // Completely removes any status displays so that the status effect indicator may render properly.
        return new ArrayList<>();
    }
}
