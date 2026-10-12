package net.m3tte.ego_weapons.client.renderer.delegatedEntityRendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.client.renderLayers.AccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.PatchedAccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.ValidRenderTypes;
import net.m3tte.ego_weapons.mixin.epicfight.PatchedLivingEntityRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class AccessoryRenderLayerRequest extends RenderRequest {

    private PatchedAccessoryRenderLayer savedRenderLayer = null;
    private AccessoryRenderLayer renderLayer = null;
    private LivingEntity entity = null;
    private ValidRenderTypes renderType = null;
    private Matrix4f lastPose = null;
    private int packedLightIn;
    private OpenMatrix4f[] poses;
    private float netYawHead;
    private float pitchHead;
    private float partialTicks;
    private String accessoryIdentifier;

    float polygonOffsetFactor = 0;
    float polygonOffsetUnits = 0;
    Vector3f cols = new Vector3f(1,1,1);


    /*
    float netYawHead, float pitchHead, float partialTicks
     */

    public AccessoryRenderLayerRequest(PatchedAccessoryRenderLayer savedRenderLayer, AccessoryRenderLayer renderLayer, LivingEntity entity, ValidRenderTypes renderType, MatrixStack poseStack, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks, String accessoryIdentifier, float polygonOffsetFactor, float polygonOffsetUnits) {

        // Matrix3f normalMatrix = new Matrix3f(poseStack.last().normal());
        this.savedRenderLayer = savedRenderLayer;
        this.renderLayer = renderLayer;
        this.entity = entity;
        this.renderType = renderType;
        this.packedLightIn = packedLightIn;
        this.poses = poses;
        this.netYawHead = netYawHead;
        this.pitchHead = pitchHead;
        this.partialTicks = partialTicks;
        this.accessoryIdentifier = accessoryIdentifier;
        this.polygonOffsetFactor = polygonOffsetFactor;
        this.polygonOffsetUnits = polygonOffsetUnits;
    }

    public AccessoryRenderLayerRequest(PatchedAccessoryRenderLayer savedRenderLayer, AccessoryRenderLayer renderLayer, LivingEntity entity, ValidRenderTypes renderType, MatrixStack poseStack, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks, String accessoryIdentifier, float polygonOffsetFactor, float polygonOffsetUnits, Vector3f cols) {

        // Matrix3f normalMatrix = new Matrix3f(poseStack.last().normal());
        this.cols = cols;
        this.savedRenderLayer = savedRenderLayer;
        this.renderLayer = renderLayer;
        this.entity = entity;
        this.renderType = renderType;
        this.packedLightIn = packedLightIn;
        this.poses = poses;
        this.netYawHead = netYawHead;
        this.pitchHead = pitchHead;
        this.partialTicks = partialTicks;
        this.accessoryIdentifier = accessoryIdentifier;
        this.polygonOffsetFactor = polygonOffsetFactor;
        this.polygonOffsetUnits = polygonOffsetUnits;
    }

    @Override
    public void render(IRenderTypeBuffer bufferIn, MatrixStack poseStack) {
        RenderSystem.polygonOffset(polygonOffsetFactor, polygonOffsetUnits);
        Vector3d pos = this.entity.getPosition(this.partialTicks);
        Vector3d camera =
                Minecraft.getInstance()
                        .gameRenderer
                        .getMainCamera()
                        .getPosition();

        //poseStack.pushPose();

        // Translation due to how epic fight handles shift
        //if (this.entity.isShiftKeyDown()) {
        //    poseStack.translate(0.0, 0.15, 0.0);
        //}

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch)entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);




        PatchedEntityRenderer<?,?,?> renderer = ClientEngine.instance.renderEngine.getEntityRenderer(this.entity);

        if (entitypatch != null && renderer instanceof PatchedLivingEntityRenderer) {



            // OpenMatrix4f modelMatrix = new OpenMatrix4f();
            // modelMatrix.mulFront(((ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT)).getArmature().searchJointById(((PatchedLivingEntityRendererAccessor) renderer).invokeGetRootJointIndex()).getAnimatedTransform());
            // OpenMatrix4f transpose = OpenMatrix4f.transpose(modelMatrix, (OpenMatrix4f)null);
            poseStack.pushPose();
            // MathUtils.translateStack(poseStack, modelMatrix);
            // MathUtils.rotateStack(poseStack, transpose);
            ClientModel model = (ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT);
            Armature armature = model.getArmature();

            poseStack.translate(
                    pos.x - camera.x,
                    pos.y - camera.y - (this.entity.isShiftKeyDown() ? 0.12 : 0),
                    pos.z - camera.z
            );
            //OpenMatrix4f[] poseMatrices = ((PatchedEntityRendererAccessor) renderer).getPoseMatrices(entitypatch, armature, partialTicks);

            poseStack.pushPose();

            ((PatchedLivingEntityRendererAccessor) renderer).invokeMulPoseStack(poseStack, armature, this.entity, entitypatch, partialTicks);

            // double bodyYaw = MathUtils.lerpBetween(this.entity.yBodyRotO, this.entity.yBodyRot, partialTicks - Minecraft.getInstance().getDeltaFrameTime());
            //float bodyYaw;
            /*poseStack.mulPose(
                    Vector3f.YP.rotationDegrees((float) (180.0F - bodyYaw))
            );*/
            // System.out.println("F: "+EgoWeaponsRenderSystem.frames%100+" | DETECTED ROTATION FOR FRAME : "+this.entity.yBodyRot);

            savedRenderLayer.renderWearableModel(this.renderLayer.getRenderer(accessoryIdentifier), this.entity, bufferIn, this.renderType, poseStack, this.packedLightIn, this.poses, this.netYawHead, this.pitchHead, this.partialTicks, this.cols.x(), this.cols.y(), this.cols.z());
            poseStack.popPose();
            poseStack.popPose();
        }

    }


}
