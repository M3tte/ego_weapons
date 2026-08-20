package net.m3tte.ego_weapons.mixin.rendering;


import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(value = GameRenderer.class, remap = false)
public class GameRendererMixin {




    // Lnet/minecraft/client/shader/ShaderGroup;process(F)V
        // TODO: REMOVE IF REALLY NOT NEEDED
    //@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;bindWrite(Z)V"), method = "render(FJZ)V")
    private void enqueueIfCorrect(float partialTicks, long p_195458_2_, boolean p_195458_4_, CallbackInfo ci) throws IOException {


        if (EgoWeaponsRenderSystem.getDistortionParticles().isEmpty())
            return;




        //Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        //getDistortionMask().blitToScreen(Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height);

    }

}