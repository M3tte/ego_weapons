package net.m3tte.ego_weapons.mixin;


import net.m3tte.ego_weapons.client.renderer.CustomRenderTypes;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.SortedMap;

import static net.minecraft.client.renderer.IRenderTypeBuffer.immediateWithBuffers;

@Mixin(value = RenderTypeBuffers.class)
public class RenderBuffersMixin {


    private static IRenderTypeBuffer.Impl savedBufferSource = null;
    // Placeholder Module
    private static boolean hasRendered = false;

    @Inject(at = @At(value = "TAIL"), method = "bufferSource", cancellable = true)
    private void injectBufferSource(CallbackInfoReturnable<IRenderTypeBuffer.Impl> cir) {

        if (savedBufferSource == null) {
            System.out.println("First Override of Render Types");
            RenderTypeBuffers renderBuffers = ((RenderTypeBuffers)(Object)this);


            SortedMap<RenderType, BufferBuilder> fixedBuffers = ((renderBufferInterface) renderBuffers).getFixedBuffers();
            //put(fixedBuffers, CustomRenderTypes.animatedArmorGlint());
            savedBufferSource = immediateWithBuffers(fixedBuffers, new BufferBuilder(256));
        }
        cir.setReturnValue(savedBufferSource);
    }



    private static void put(SortedMap<RenderType, BufferBuilder> p_110102_, RenderType p_110103_) {
        p_110102_.put(p_110103_, new BufferBuilder(p_110103_.bufferSize()));
    }
}
