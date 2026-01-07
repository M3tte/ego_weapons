package net.m3tte.ego_weapons.mixin.rendering;


import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.minecraft.client.renderer.*;
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
    private static OutlineLayerBuffer savedOutlineBuffers = null;
    @Inject(at = @At(value = "TAIL"), method = "bufferSource", cancellable = true)
    private void injectBufferSource(CallbackInfoReturnable<IRenderTypeBuffer.Impl> cir) {

        if (savedBufferSource == null) {
            System.out.println("First Override of Render Types");
            RenderTypeBuffers renderBuffers = ((RenderTypeBuffers)(Object)this);


            SortedMap<RenderType, BufferBuilder> fixedBuffers = ((renderBufferInterface) renderBuffers).getFixedBuffers();
            put(fixedBuffers, EgoWeaponsRenderTypes.bloodOverlay(0));
            put(fixedBuffers, EgoWeaponsRenderTypes.bloodOverlay(1));
            put(fixedBuffers, EgoWeaponsRenderTypes.bloodOverlay(2));
            put(fixedBuffers, EgoWeaponsRenderTypes.bloodOverlay(3));
            put(fixedBuffers, EgoWeaponsRenderTypes.bloodOverlay(4));
            put(fixedBuffers, EgoWeaponsRenderTypes.entityBloodOverlay(0));
            put(fixedBuffers, EgoWeaponsRenderTypes.entityBloodOverlay(1));
            put(fixedBuffers, EgoWeaponsRenderTypes.entityBloodOverlay(2));
            put(fixedBuffers, EgoWeaponsRenderTypes.entityBloodOverlay(3));
            put(fixedBuffers, EgoWeaponsRenderTypes.entityBloodOverlay(4));
            savedBufferSource = immediateWithBuffers(fixedBuffers, new BufferBuilder(256));
            savedOutlineBuffers = new OutlineLayerBuffer(savedBufferSource);
        }
        cir.setReturnValue(savedBufferSource);
    }

    @Inject(at = @At(value = "TAIL"), method = "outlineBufferSource", cancellable = true)
    private void injectOutlineBuffer(CallbackInfoReturnable<OutlineLayerBuffer> cbk) {
        if (savedOutlineBuffers != null) {
            cbk.setReturnValue(savedOutlineBuffers);
        }
    }

    private static void put(SortedMap<RenderType, BufferBuilder> p_110102_, RenderType p_110103_) {
        p_110102_.put(p_110103_, new BufferBuilder(p_110103_.bufferSize()));
    }
}
