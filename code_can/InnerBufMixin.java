package net.m3tte.ego_weapons.mixin.rendering;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;

import java.util.Objects;
import java.util.Optional;

import static yesman.epicfight.client.renderer.EpicFightRenderTypes.enchantedAnimatedArmor;

@OnlyIn(Dist.CLIENT)
@Mixin(value = IRenderTypeBuffer.Impl.class, remap = false)
public abstract class InnerBufMixin implements IRenderTypeBuffer {



    // Placeholder Module
    @Inject(at = @At(value = "RETURN"), method = "getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void overrideReturn(RenderType bufIn, CallbackInfoReturnable<IVertexBuilder> cir, BufferBuilder buf) {
        Optional<RenderType> optional = bufIn.asOptional();
        IRenderTypeBuffer.Impl self = ((IRenderTypeBuffer.Impl)(Object) this);

        if (Objects.equals(((RenderStateAccessor) bufIn).getName(), "epicfight:animated_model")) {
            cir.setReturnValue(VertexBuilderUtils.create(self.getBuffer(enchantedAnimatedArmor()), buf));


        }
    }
}
