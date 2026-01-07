package net.m3tte.ego_weapons.mixin.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@OnlyIn(Dist.CLIENT)
@Mixin(value = PatchedLivingEntityRenderer.class, remap = false)
public abstract class LivingRendererMixin<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedEntityRenderer<E, T, LivingRenderer<E, M>> {

    @Unique
    ObjectArrayFIFOQueue<IVertexBuilder> egoweapons$bloodOverlays = new ObjectArrayFIFOQueue<>(50);

    // Placeholder Module
    /*@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/IRenderTypeBuffer;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"), method = "render(Lnet/minecraft/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingRenderer;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lcom/mojang/blaze3d/matrix/MatrixStack;IF)V")
    private void enqueueIfCorrect(E entityIn, T entitypatch, LivingRenderer<E, M> renderer, IRenderTypeBuffer buffer, MatrixStack poseStack, int packedLight, float partialTicks, CallbackInfo ci) {

        if (entityIn instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = entityIn.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

            if (entityData != null) {

                int bloodIDX = tcorp$getBloodIDX(entityData);

                if (bloodIDX >= 0) {
                    egoweapons$bloodOverlays.enqueue(buffer.getBuffer(EgoWeaponsRenderTypes.entityBloodOverlay(bloodIDX)));
                }
            }
        }


    }*/

    @Unique
    private static int tcorp$getBloodIDX(EgoWeaponsModVars.PlayerVariables entityData) {
        int bloodIDX = -1;

        if (entityData.injury_threshold > 2.3f) {
            bloodIDX = 4;
        } else if (entityData.injury_threshold > 1.6f) {
            bloodIDX = 3;
        } else if (entityData.injury_threshold > 1.1f) {
            bloodIDX = 2;
        } else if (entityData.injury_threshold > 0.7f) {
            bloodIDX = 1;
        } else if (entityData.injury_threshold > 0.3f) {
            bloodIDX = 0;
        }
        return bloodIDX;
    }

    /*@Redirect(at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/client/model/ClientModel;drawAnimatedModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IFFFFI[Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V"), method = "render(Lnet/minecraft/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingRenderer;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lcom/mojang/blaze3d/matrix/MatrixStack;IF)V")
    private void injectedMethodCall(ClientModel instance, MatrixStack matrixStack, IVertexBuilder originalVertexBuilder, int packedLight, float r, float g, float b, float a, int overlayCoord, OpenMatrix4f[] normal) {

        if (egoweapons$bloodOverlays.size() > 50)
            egoweapons$bloodOverlays.clear();

        if (!egoweapons$bloodOverlays.isEmpty()) {



            originalVertexBuilder = VertexBuilderUtils.create(egoweapons$bloodOverlays.dequeue(), originalVertexBuilder);
            System.out.println("OVERRIDING TO BLOOD OVERLAY");
        }

        instance.drawAnimatedModel(matrixStack, originalVertexBuilder, packedLight, r, g, b, a, overlayCoord, normal);
    }*/

    @Inject(at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/client/model/ClientModel;drawAnimatedModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IFFFFI[Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V", shift = At.Shift.AFTER), method = "render(Lnet/minecraft/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingRenderer;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lcom/mojang/blaze3d/matrix/MatrixStack;IF)V")
    private void injectedMethodCall(E entityIn, T entitypatch, LivingRenderer<E, M> renderer, IRenderTypeBuffer buffer, MatrixStack poseStack, int packedLight, float partialTicks, CallbackInfo ci) {

        if (entityIn instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = entityIn.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

            if (entityData != null) {

                int bloodIDX = tcorp$getBloodIDX(entityData);

                if (bloodIDX >= 0) {

                    boolean isVisibleToPlayer = true;
                    ClientModel model = (ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT);
                    Armature armature = model.getArmature();
                    OpenMatrix4f[] poseMatrices = this.getPoseMatrices(entitypatch, armature, partialTicks);

                    model.drawAnimatedModel(poseStack, buffer.getBuffer(EgoWeaponsRenderTypes.entityBloodOverlay(bloodIDX)), packedLight, 1.0F, 1.0F, 1.0F, 1, ((LivingRendererInvoker) this).callGetOverlayCoord(entityIn, entitypatch, partialTicks), poseMatrices);

                }
            }
        }

    }
}
