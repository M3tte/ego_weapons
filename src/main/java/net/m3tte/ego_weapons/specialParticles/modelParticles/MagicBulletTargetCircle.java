package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
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

public class MagicBulletTargetCircle extends QuarternionBoundParticle {

    float baseQuadSize = 0;
    public MagicBulletTargetCircle(ClientWorld world, double x, double y, double z, double sizeMultiplier, double targetEntityID, double offsetModifier, IAnimatedSprite spriteProvider, int lifeTime) {
        super(world, x, y, z, sizeMultiplier, targetEntityID, offsetModifier, spriteProvider);

        this.glowRenderType = true;

        if (sizeMultiplier == 0)
            sizeMultiplier = 1;

        if (offsetModifier == 0)
            offsetModifier = 0;

        if (this.boundEntity instanceof LivingEntity) {

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) this.boundEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            Quaternion quart = new Quaternion(0,0,0, true);

            quart.conj();


            // quart.mul(new Quaternion(0, -boundEntity.getRotationVector().y, 0, true));

            this.rotation = quart;

            Vector3d targetPos = this.boundEntity.position();

            this.x = targetPos.x();
            this.y = targetPos.y() + 0.03f + offsetModifier;
            this.z = targetPos.z();

            this.ox = this.x;
            this.oy = this.y;
            this.oz = this.z;


        }



        //this.rotationOffs.set(180,0,0);
        this.quadSize = 0.01f;
        this.baseQuadSize = 1.5f * (float)sizeMultiplier;
        this.lifetime = lifeTime;
        this.offset = new Vector3f(0, 0,(float) (0));
        this.rotation.mul(new Quaternion(92,0,0,true));
        this.offsetRate = new Vector3f(0f,0,0);
    }


    @Override
    public void tick() {
        super.tick();
        Vector3d targetPos = this.boundEntity.position();

        this.x = targetPos.x();
        this.y = targetPos.y() + 0.2f;
        this.z = targetPos.z();

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
            MagicBulletTargetCircle particle = new MagicBulletTargetCircle(worldIn, x, y, z, xSpeed, targetID, sourceID, spriteSet, this.lifeTime);
            return particle;
        }
    }
}
