package net.m3tte.ego_weapons.mixin.rendering;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.ClippingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ParticleManager.class, remap = false)
public class ParticleManagerMixin {



    @Inject(at = @At(value = "HEAD"), method = "renderParticles(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer$Impl;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/renderer/ActiveRenderInfo;FLnet/minecraft/client/renderer/culling/ClippingHelper;)V")
    private void injectBufferSource(MatrixStack p_renderParticles_1_, IRenderTypeBuffer.Impl p_renderParticles_2_, LightTexture p_renderParticles_3_, ActiveRenderInfo ari, float p_renderParticles_5_, ClippingHelper p_renderParticles_6_, CallbackInfo ci) {

        EgoWeaponsRenderSystem.savedRenderInfo = ari;
    }


}
