package net.m3tte.ego_weapons.mixin.rendering;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.client.renderer.patched.layer.WearableItemLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@Mixin(value = WearableItemLayer.class, remap = false)
public abstract class RenderWearableItemMixin<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends BipedModel<E>> extends PatchedLayer<E, T, M, BipedArmorLayer<E, M, M>> {

    @Unique
    ObjectArrayFIFOQueue<IVertexBuilder> tcorp$queuedBloodOverlays = new ObjectArrayFIFOQueue<>(50);

    /*@ModifyVariable(method = "renderArmor(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLyesman/epicfight/api/client/model/ClientModel;FFFLnet/minecraft/util/ResourceLocation;[Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V", at = @At("LOAD"), ordinal = 0)
    private IVertexBuilder injected(IVertexBuilder oldBuilder) {

        if (tcorp$queuedBloodOverlays.size() > 50)
            tcorp$queuedBloodOverlays.clear();

        if (!tcorp$queuedBloodOverlays.isEmpty()) {
            return VertexBuilderUtils.create(tcorp$queuedBloodOverlays.dequeue(), oldBuilder);
        }

        return oldBuilder;
    }*/

    @Inject(method = "renderArmor(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLyesman/epicfight/api/client/model/ClientModel;FFFLnet/minecraft/util/ResourceLocation;[Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V", at = @At("HEAD"))
    private void injected(MatrixStack matStack, IRenderTypeBuffer multiBufferSource, int packedLightIn, boolean hasEffect, ClientModel model, float r, float g, float b, ResourceLocation armorTexture, OpenMatrix4f[] poses, CallbackInfo ci) {

        if (tcorp$queuedBloodOverlays.size() > 50)
            tcorp$queuedBloodOverlays.clear();

        if (!tcorp$queuedBloodOverlays.isEmpty()) {
            model.drawAnimatedModel(matStack, tcorp$queuedBloodOverlays.dequeue(), packedLightIn, r, g, b, 1.0F, OverlayTexture.NO_OVERLAY, poses);
        }

    }


    @Inject(at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/renderer/patched/layer/WearableItemLayer;renderArmor(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLyesman/epicfight/api/client/model/ClientModel;FFFLnet/minecraft/util/ResourceLocation;[Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V"), method = "renderLayer(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/layers/BipedArmorLayer;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I[Lyesman/epicfight/api/utils/math/OpenMatrix4f;FFF)V")
    private void injectPreModelRender(T entitypatch, E entityliving, BipedArmorLayer<E, M, M> originalRenderer, MatrixStack poseStack, IRenderTypeBuffer buf, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks, CallbackInfo ci) {

        if (entityliving instanceof PlayerEntity) {

            EgoWeaponsModVars.PlayerVariables entityData = entityliving.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

            if (entityData != null) {

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

                if (bloodIDX >= 0) {
                    tcorp$queuedBloodOverlays.enqueue(buf.getBuffer(EgoWeaponsRenderTypes.bloodOverlay(bloodIDX)));

                }

            }

        }

    }

}