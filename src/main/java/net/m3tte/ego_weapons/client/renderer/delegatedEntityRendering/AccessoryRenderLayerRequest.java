package net.m3tte.ego_weapons.client.renderer.delegatedEntityRendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.client.renderLayers.AccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.PatchedAccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.ValidRenderTypes;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.openjdk.nashorn.internal.ir.annotations.Ignore;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

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
    @Ignore
    public void render(IRenderTypeBuffer bufferIn) {
        savedRenderLayer.renderWearableModel(this.renderLayer.getRenderer(accessoryIdentifier), this.entity, bufferIn, this.renderType, this.poseStack, this.packedLightIn, this.poses, this.netYawHead, this.pitchHead, this.partialTicks);
    }
}
