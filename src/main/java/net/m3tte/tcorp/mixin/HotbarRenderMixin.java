package net.m3tte.tcorp.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ForgeIngameGui.class, remap = false)
public class HotbarRenderMixin {
    @Inject(at = @At(value = "HEAD"), method = "renderHealth(IILcom/mojang/blaze3d/matrix/MatrixStack;)V", cancellable = true)
    public void catchRenderMaxHealth(int width, int height, MatrixStack mStack, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At(value = "HEAD"), method = "renderArmor(Lcom/mojang/blaze3d/matrix/MatrixStack;II)V", cancellable = true)
    public void catchRenderArmor(MatrixStack mStack, int width, int height, CallbackInfo ci) {
        ci.cancel();
    }
}
