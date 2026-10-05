package net.m3tte.ego_weapons.client.renderer.delegatedEntityRendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.client.renderLayers.AccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.PatchedAccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.ValidRenderTypes;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

import static net.m3tte.ego_weapons.client.renderer.FramebufferHandlers.getBloomMask;

public class AccessoryRenderLayerRequest extends RenderRequest {

    private PatchedAccessoryRenderLayer savedRenderLayer = null;
    private AccessoryRenderLayer renderLayer = null;
    private LivingEntity entity = null;
    private ValidRenderTypes renderType = null;
    private MatrixStack poseStack = null;
    private int packedLightIn;
    private OpenMatrix4f[] poses;
    private float netYawHead;
    private float pitchHead;
    private float partialTicks;
    private String accessoryIdentifier;

    /*
    float netYawHead, float pitchHead, float partialTicks
     */

    public AccessoryRenderLayerRequest(PatchedAccessoryRenderLayer savedRenderLayer, AccessoryRenderLayer renderLayer, LivingEntity entity, ValidRenderTypes renderType, MatrixStack poseStack, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks, String accessoryIdentifier) {
        this.savedRenderLayer = savedRenderLayer;
        this.renderLayer = renderLayer;
        this.entity = entity;
        this.renderType = renderType;
        this.poseStack = poseStack;
        this.packedLightIn = packedLightIn;
        this.poses = poses;
        this.netYawHead = netYawHead;
        this.pitchHead = pitchHead;
        this.partialTicks = partialTicks;
        this.accessoryIdentifier = accessoryIdentifier;
    }

    @Override
    public void render(IRenderTypeBuffer bufferIn, MatrixStack poseStack) {

       Vector3d pos = this.entity.getPosition(this.partialTicks);
        Vector3d camera =
                Minecraft.getInstance()
                        .gameRenderer
                        .getMainCamera()
                        .getPosition();

        poseStack.pushPose();

        poseStack.translate(
                pos.x - camera.x,
                pos.y - camera.y,
                pos.z - camera.z
        );

        float bodyYaw = entity.yBodyRotO;

        poseStack.mulPose(
                Vector3f.YP.rotationDegrees(180.0F - bodyYaw)
        );

        savedRenderLayer.renderWearableModel(this.renderLayer.getRenderer(accessoryIdentifier), this.entity, bufferIn, this.renderType, poseStack, this.packedLightIn, this.poses, this.netYawHead, this.pitchHead, this.partialTicks);
        poseStack.popPose();
    }


}
