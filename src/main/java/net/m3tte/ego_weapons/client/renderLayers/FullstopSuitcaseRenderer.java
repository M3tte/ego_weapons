package net.m3tte.ego_weapons.client.renderLayers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.client.models.entities.SunshowerFoxModel;
import net.m3tte.ego_weapons.client.renderer.entities.mobs.SunshowerFoxRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FullstopSuitcaseRenderer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M> {
    public FullstopSuitcaseRenderer(IEntityRenderer<T, M> p_i50934_1_) {
        super(p_i50934_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ItemStack itemstack = p_225628_4_.getMainHandItem();
        if (EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get().equals(itemstack.getItem())) {

            if (itemstack.getOrCreateTag().getInt("dropped") > 0) {
                ItemStack suitcaseStack = EgoWeaponsItems.FULLSTOP_SNIPER_SUITCASE.get().getDefaultInstance();

                suitcaseStack.getOrCreateTag().putInt("dropped",1);

                p_225628_1_.pushPose();

                this.renderItem(p_225628_4_, suitcaseStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, p_225628_1_, p_225628_2_, p_225628_3_);
                p_225628_1_.popPose();
            }
        }
    }

    private void renderItem(LivingEntity entity, ItemStack item, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer itemBuffer, int p_229135_7_) {
        if (!item.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5, -0.05, -0.9);
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-95.0F));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(185.0F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            matrixStack.scale(0.95f, 1.1f, 1.1f);
            Minecraft.getInstance().getItemRenderer().renderStatic(entity, item, transformType, false, matrixStack, itemBuffer, entity.level, p_229135_7_, OverlayTexture.NO_OVERLAY);
            //Minecraft.getInstance().getItemRenderer().renderStatic(item, transformType, p_229135_7_, OverlayTexture.NO_OVERLAY, matrixStack, itemBuffer);
            //Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, item, transformType, false, matrixStack, itemBuffer, p_229135_7_);
            matrixStack.popPose();
            matrixStack.popPose();
        }
    }
}