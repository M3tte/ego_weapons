package net.m3tte.ego_weapons.mixin.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
@Mixin(value = PatchedLivingEntityRenderer.class, remap = false)
public abstract class LivingRendererMixin<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedEntityRenderer<E, T, LivingRenderer<E, M>> {

    @Unique
    ObjectArrayFIFOQueue<IVertexBuilder> egoweapons$bloodOverlays = new ObjectArrayFIFOQueue<>(50);

    // Placeholder Module
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/IRenderTypeBuffer;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"), method = "render(Lnet/minecraft/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingRenderer;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lcom/mojang/blaze3d/matrix/MatrixStack;IF)V")
    private void enqueueIfCorrect(E entityIn, T entitypatch, LivingRenderer<E, M> renderer, IRenderTypeBuffer buffer, MatrixStack poseStack, int packedLight, float partialTicks, CallbackInfo ci) {

        if (entityIn instanceof LivingEntity) {
            egoweapons$bloodOverlays.enqueue(buffer.getBuffer(EgoWeaponsRenderTypes.bloodOverlay2()));
        }

    }

    @Redirect(at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/client/model/ClientModel;drawAnimatedModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IFFFFI[Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V"), method = "render(Lnet/minecraft/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingRenderer;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lcom/mojang/blaze3d/matrix/MatrixStack;IF)V")
    private void injectedMethodCall(ClientModel instance, MatrixStack matrixStack, IVertexBuilder originalVertexBuilder, int packedLight, float r, float g, float b, float a, int overlayCoord, OpenMatrix4f[] normal) {

        if (egoweapons$bloodOverlays.size() > 50)
            egoweapons$bloodOverlays.clear();

        if (!egoweapons$bloodOverlays.isEmpty()) {
            originalVertexBuilder = VertexBuilderUtils.create(egoweapons$bloodOverlays.dequeue(), originalVertexBuilder);
            System.out.println("OVERRIDING RENDERER, new is : "+originalVertexBuilder);
        }



        instance.drawAnimatedModel(matrixStack, originalVertexBuilder, packedLight, r, g, b, a, overlayCoord, normal);
    }


}
