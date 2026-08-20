package net.m3tte.ego_weapons.client.renderLayers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UdjatKhopeshRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    public UdjatKhopeshRenderer(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer rtb, int p_225628_3_, T livingEntity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {

        if (livingEntity.getPersistentData().contains("lcaKhopeshL")) {

            ItemStack suitcaseStack = EgoWeaponsItems.LCA_UDJAT_KHOPESH.get().getDefaultInstance();

            int diff = livingEntity.getPersistentData().getInt("lcaKhopeshL") - livingEntity.tickCount;

            if (diff < 210 && diff > 0) {
                p_225628_1_.pushPose();


                this.renderItem(livingEntity, suitcaseStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, p_225628_1_, rtb, p_225628_3_);
                p_225628_1_.popPose();
            }
        }


    }

    private void renderItem(Entity entity, ItemStack item, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer itemBuffer, int p_229135_7_) {
        if (!item.isEmpty() && entity instanceof LivingEntity) {

            float width = entity.getBbWidth() + 0.25f;
            float height = entity.getBbHeight()*-0.15f;

            if (entity instanceof PlayerEntity)
                height *= -1;

            matrixStack.pushPose();
            matrixStack.translate(0, height, -0.75);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(80));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(175F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(0F));
            matrixStack.scale(1, 1, 1);

            Minecraft.getInstance().getItemRenderer().renderStatic((LivingEntity) entity, item, transformType, false, matrixStack, itemBuffer, entity.level, p_229135_7_, OverlayTexture.NO_OVERLAY);
            //Minecraft.getInstance().getItemRenderer().renderStatic(item, transformType, p_229135_7_, OverlayTexture.NO_OVERLAY, matrixStack, itemBuffer);
            //Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, item, transformType, false, matrixStack, itemBuffer, p_229135_7_);
            matrixStack.popPose();
        }
    }
}