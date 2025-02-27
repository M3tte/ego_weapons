package net.m3tte.ego_weapons.specialParticles.texturedAfterImage;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.particle.CustomModelParticle;
import yesman.epicfight.client.particle.EpicFightParticleRenderTypes;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class TexturedAfterImage extends CustomModelParticle {
    private OpenMatrix4f[] poseMatrices;
    private Matrix4f modelMatrix;
    private float alphaO;

    float dr;
    float dg;
    float db;
    float da;

    public TexturedAfterImage(ClientWorld level, double x, double y, double z, double xd, double yd, double zd, ClientModel particleMesh, OpenMatrix4f[] matrices, Matrix4f modelMatrix, TexturedAfterImagePresets preset) {
        super(level, x, y, z, xd, yd, zd, particleMesh);
        this.poseMatrices = matrices;
        this.modelMatrix = modelMatrix;
        this.lifetime = preset.getLifetime();
        this.rCol = preset.getR(); //0.9F;
        this.dr = preset.getDr();
        this.dg = preset.getDg();
        this.db = preset.getDb();
        this.da = preset.getDa();
        this.gCol = preset.getG();//0.7F;
        this.bCol = preset.getB(); // 0.5F;
        this.alphaO = preset.getA(); //0.3F;
        this.alpha = preset.getA(); //0.3F;
    }


    public void tick() {
        super.tick();
        this.alphaO = this.alpha;
        //this.alpha = ((float)(this.lifetime - this.age) / (float)this.lifetime) * 0.4F;
        this.alpha = Math.max(0,this.alpha + this.da);
        this.rCol = Math.min(1,this.rCol + this.dr);
        this.gCol = Math.min(1,this.gCol + this.dg);
        this.bCol = Math.min(1,this.bCol + this.db);
    }

    public void render(IVertexBuilder vertexConsumer, ActiveRenderInfo camera, float partialTicks) {
        MatrixStack poseStack = new MatrixStack();
        this.setupMatrixStack(poseStack, camera, partialTicks);
        poseStack.last().pose().multiply(this.modelMatrix);
        float alpha = this.alphaO + (this.alpha - this.alphaO) * partialTicks;
        this.particleMesh.drawAnimatedModel(poseStack, vertexConsumer, this.getLightColor(partialTicks), this.rCol, this.gCol, this.bCol, alpha, OverlayTexture.NO_OVERLAY, this.poseMatrices);
    }

    public IParticleRenderType getRenderType() {
        return EpicFightParticleRenderTypes.TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        public Provider() {
        }

        public Particle createParticle(BasicParticleType typeIn, ClientWorld level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch)level.getEntity((int)Double.doubleToLongBits(xSpeed)).getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse(null);
            if (entitypatch != null && ClientEngine.instance.renderEngine.hasRendererFor(entitypatch.getOriginal())) {

                int index = (int) ySpeed;
                TexturedAfterImagePresets preset = null;
                if (index < 0 || index >= TexturedAfterImagePresets.values().length)
                    preset = TexturedAfterImagePresets.STANDARD;
                else
                    preset = TexturedAfterImagePresets.values()[index];

                PatchedEntityRenderer renderer = ClientEngine.instance.renderEngine.getEntityRenderer(entitypatch.getOriginal());
                Armature armature = ((ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT)).getArmature();
                MatrixStack poseStack = new MatrixStack();
                OpenMatrix4f[] matrices = renderer.getPoseMatrices(entitypatch, armature, 1.0F);
                renderer.mulPoseStack(poseStack, armature, (LivingEntity)entitypatch.getOriginal(), entitypatch, 1.0F);
                TexturedAfterImage particle = new TexturedAfterImage(level, x, y, z, xSpeed, ySpeed, zSpeed, (ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT), matrices, poseStack.last().pose(), preset);
                return particle;
            } else {
                return null;
            }
        }
    }
}

