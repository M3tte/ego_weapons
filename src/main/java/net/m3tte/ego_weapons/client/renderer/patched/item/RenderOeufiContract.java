//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.client.renderer.patched.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class RenderOeufiContract extends RenderItemBase {
    private final ItemStack contractStack;

    public RenderOeufiContract() {
        this.contractStack = EgoWeaponsItems.OEUFI_CONTRACT.get().getDefaultInstance();
    }

    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, Hand hand, IRenderTypeBuffer buffer, MatrixStack poseStack, int packedLight) {
        OpenMatrix4f modelMatrix = new OpenMatrix4f(this.mainhandcorrectionMatrix);

        /*modelMatrix.mulFront(((ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT)).getArmature().searchJointByName("Tool_R").getAnimatedTransform());
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer);
        poseStack.popPose();*/


        modelMatrix.mulFront(((ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT)).getArmature().searchJointByName("Tool_L").getAnimatedTransform());
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        Minecraft.getInstance().getItemRenderer().renderStatic(this.contractStack, TransformType.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer);
        poseStack.popPose();
    }
}
