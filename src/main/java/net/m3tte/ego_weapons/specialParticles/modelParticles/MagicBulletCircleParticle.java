package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

public class MagicBulletCircleParticle extends QuarternionBoundParticle {

    float baseQuadSize = 0;
    public MagicBulletCircleParticle(ClientWorld world, double x, double y, double z, double sizeMultiplier, double targetEntityID, double offsetModifier, IAnimatedSprite spriteProvider, int lifeTime) {
        super(world, x, y, z, sizeMultiplier, targetEntityID, offsetModifier, spriteProvider);

        this.glowRenderType = true;


        if (this.boundEntity instanceof LivingEntity) {

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) this.boundEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            Quaternion quart = getQuarternionFromRot(entitypatch, 0, "Tool_R");

            quart.conj();


            // quart.mul(new Quaternion(0, -boundEntity.getRotationVector().y, 0, true));

            this.rotation = quart;
            Vector3d pos = getVec3FromArmature(entitypatch, 0, "Tool_R");
            this.x = pos.x();
            this.y = pos.y() + 0.2f;
            this.z = pos.z();

            this.ox = this.x;
            this.oy = this.y;
            this.oz = this.z;

        }



        //this.rotationOffs.set(180,0,0);
        this.quadSize = 0.01f;
        this.baseQuadSize = 1.5f * (float)sizeMultiplier;
        this.lifetime = lifeTime;
        this.offset = new Vector3f(0, 0,(float) (1f * offsetModifier));
        this.rotation.mul(new Quaternion(92,0,0,true));
        this.offsetRate = new Vector3f(0f,0,0);
    }



    float lastTickDelta = 0;

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        if (tickDelta < lastTickDelta)
            lastTickDelta = -(1-lastTickDelta);

        this.rotation.mul(new Quaternion(0,0,3 * (tickDelta - lastTickDelta),true));

        lastTickDelta = tickDelta;


        if (this.age <= 8) {
            float sizeProgress = (this.age + tickDelta) / 8;
            this.quadSize =  MathHelper.lerp(sizeProgress, 0.01f, this.baseQuadSize);
        }



        super.render(vertexBuilder, renderInfo, tickDelta);
    }

    public static Quaternion getQuarternionFromRot(LivingEntityPatch<?> entityPatch, int partialTicks, String jointName) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        World l = entityPatch.getOriginal().level;

        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulFront(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulFront(entityPatch.getModelMatrix(partialTicks)));
        if (l.isClientSide)
            return Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf).toQuaternion();
        else
            return Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf).toQuaternion();

    }

    public static Vector3d getVec3FromArmature(LivingEntityPatch<?> entityPatch, int partialTicks, String jointName) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        return OpenMatrix4f.transform(middleJointTf, new Vector3d(0,0,0));
    }

    @Override
    public String toString() {
        return "RotationAttackParticle{" +
                "ox=" + this.ox +
                ", oy=" + this.oy +
                ", oz=" + this.oz +
                '}';
    }

    @Override
    public void setSpriteFromAge(IAnimatedSprite p_217566_1_) {

        // Max Age 7 Frames

        int frameNum = 20;

        int frame = 0;
        if (this.age < this.lifetime - frameNum) {
            frame = Math.min(3, this.age / 3);
        } else {
            frame = Math.min(7,(this.age - (this.lifetime - frameNum)) / (frameNum / 4) + 4);
        }
        this.setSprite(p_217566_1_.get(frame, 7));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;
        private int lifeTime = 40;

        public Provider(IAnimatedSprite spriteSet, int lifeTime) {
            this.spriteSet = spriteSet;
            this.lifeTime = lifeTime;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            MagicBulletCircleParticle particle = new MagicBulletCircleParticle(worldIn, x, y, z, xSpeed, targetID, sourceID, spriteSet, this.lifeTime);
            return particle;
        }
    }
}
