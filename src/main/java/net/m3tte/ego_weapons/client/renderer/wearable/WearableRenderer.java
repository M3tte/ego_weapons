package net.m3tte.ego_weapons.client.renderer.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;

import javax.annotation.Nullable;

public abstract class WearableRenderer<R extends LivingEntity, S extends BipedModel<R>, W extends BipedModel<R>> {
    public WearableRenderer() {
    }

    @Nullable
    public abstract W getWearableModel(R var1);

    @Nullable
    public abstract ResourceLocation getWearableTexture(R entityTarget);

    public void render(R living, ItemStack wearable, S parentModel, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float animationPosition, float animationSpeed, float partialTick) {
        // W model = this.getWearableModel(living);
        /*ResourceLocation texture = this.getWearableTexture(living);
        if (model != null && texture != null) {
            parentModel.copyPropertiesTo(model);
            this.preRender(living, wearable, parentModel, model, texture, matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);
            IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(buffer, EpicFightRenderTypes.animatedArmor(texture, true), false, false);
            model.renderToBuffer(matrixStack, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }*/
    }

    protected void preRender(R living, ItemStack wearable, S parentModel, W model, ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float animationPosition, float animationSpeed, float partialTick) {
    }
}
