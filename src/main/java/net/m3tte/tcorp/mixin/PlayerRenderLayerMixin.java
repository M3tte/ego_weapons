package net.m3tte.tcorp.mixin;

import net.m3tte.tcorp.client.renderLayers.SunshowerFoxRenderLayer;
import net.m3tte.tcorp.procedures.SharedFunctions;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.ParrotVariantLayer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRenderLayerMixin {



    @Inject(at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererManager;Z)V")
    public void addCustomRenderLayers(EntityRendererManager rendererManager, boolean tr, CallbackInfo ci) {

        PlayerRenderer self = ((PlayerRenderer) (Object)this);
        self.addLayer(new SunshowerFoxRenderLayer<>(self));

    }



}
