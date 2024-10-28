package net.m3tte.tcorp.mixin.epicfight;

import net.m3tte.tcorp.gui.ingame.PlayerStatsIndicator;
import net.m3tte.tcorp.gui.ingame.ThreatLevelGUI;
import net.m3tte.tcorp.world.capabilities.threatlevel.ThreatLevelSystem;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.gui.EntityIndicator;
import yesman.epicfight.client.gui.HealthBarIndicator;

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
}
