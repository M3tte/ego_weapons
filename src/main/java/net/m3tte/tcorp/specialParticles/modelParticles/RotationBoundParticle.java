package net.m3tte.tcorp.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class RotationBoundParticle extends SpriteTexturedParticle {
    protected final IAnimatedSprite spriteProvider;
    protected Vector3f offset = new Vector3f(0,0,0);
    protected Vector3f offsetRate = new Vector3f(0,0,0);

    protected Vector3f rotation = new Vector3f(0,0,0);
    protected Vector3f rotationOffs = new Vector3f(0,180,0);

    protected double ox;
    protected double oy;
    protected double oz;

    protected Vector3f[] generateVectorArray(boolean inv) {
        int invVar = inv ? -1 : 1;

        return new Vector3f[]{new Vector3f(1.0F * invVar, 1.0F * invVar, 0.0F), new Vector3f(1.0F * invVar, -1.0F * invVar, 0.0F), new Vector3f(-1.0F * invVar, -1.0F * invVar, 0.0F), new Vector3f(-1.0F * invVar, 1.0F * invVar, 0.0F)};
    }


    Entity boundEntity = null;
    public RotationBoundParticle(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite spriteProvider) {
        super(world, x, y, z);

        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        this.spriteProvider = spriteProvider;

        int scaleAgeModifier = 1 + new Random().nextInt(10);
        this.quadSize = 3f;
        this.lifetime = 12;


        if (entityUUID == 0) {
            if (world.isClientSide()) {
                boundEntity = Minecraft.getInstance().player;
            }
        } else {
            boundEntity = this.level.getEntity((int)entityUUID);
        }

        if (boundEntity != null && this.level.isClientSide()) {
            //this.x = newPos.x;
            //this.y = newPos.y + boundEntity.getBbHeight() * 0.5f;
            //this.z = newPos.z;
            ox = x;
            oy = y;
            oz = z;
            rotation.setY(-boundEntity.getRotationVector().y);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        Vector3d newPos = Minecraft.getInstance().player.position();
        /*this.x = newPos.x;
        this.y = newPos.y;
        this.z = newPos.z;*/

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        this.setSpriteFromAge(spriteProvider);

        Vector3d vec3d = renderInfo.getPosition();
        float f = (float) (MathHelper.lerp(tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float) (MathHelper.lerp(tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float) (MathHelper.lerp(tickDelta, this.zo, this.z) - vec3d.z());

        Vector3f[] Vector3fs = generateVectorArray(true);
        float j = this.getQuadSize(tickDelta);
        Vector3f transposeVector = offset.copy();
        Vector3f offsetVector = offsetRate.copy();
        offsetVector.mul(this.age);
        transposeVector.add(offsetVector);

        transposeVector.transform(new Quaternion(rotation.x(), rotation.y(), rotation.z(), true));
        for (int k = 0; k < 4; ++k) {
            Vector3f Vector3f2 = Vector3fs[k];
            Vector3f2.transform(new Quaternion(rotation.x() + rotationOffs.x(), rotation.y() + rotationOffs.y(), rotation.z() + rotationOffs.z(), true));
            Vector3f2.transform(new Quaternion(0, 0, 0, true));
            Vector3f2.mul(j);
            Vector3f2.add(f, g, h);
            Vector3f2.add(transposeVector);
        }

        Vector3f[] Vector3fsb = generateVectorArray(false);

        j = this.getQuadSize(tickDelta);

        for (int k = 0; k < 4; ++k) {
            Vector3f Vector3f2 = Vector3fsb[k];
            Vector3f2.transform(new Quaternion(rotation.x(), rotation.y(), rotation.z(), true));
            Vector3f2.transform(new Quaternion(0, 0, 0, true));
            Vector3f2.mul(j);
            Vector3f2.add(f, g, h);
            Vector3f2.add(transposeVector);
        }
        float minU = this.getU0();
        float maxU = this.getU1();
        float minV = this.getV0();
        float maxV = this.getV1();
        int l = this.getLightColor(tickDelta);

        vertexBuilder.vertex(Vector3fs[0].x(), Vector3fs[0].y(), Vector3fs[0].z()).uv(maxU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[1].x(), Vector3fs[1].y(), Vector3fs[1].z()).uv(maxU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[2].x(), Vector3fs[2].y(), Vector3fs[2].z()).uv(minU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[3].x(), Vector3fs[3].y(), Vector3fs[3].z()).uv(minU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();

        vertexBuilder.vertex(Vector3fsb[0].x(), Vector3fsb[0].y(), Vector3fsb[0].z()).uv(maxU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fsb[1].x(), Vector3fsb[1].y(), Vector3fsb[1].z()).uv(maxU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fsb[2].x(), Vector3fsb[2].y(), Vector3fsb[2].z()).uv(minU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fsb[3].x(), Vector3fsb[3].y(), Vector3fsb[3].z()).uv(minU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double zSpeed) {
            RotationBoundParticle particle = new RotationBoundParticle(worldIn, x, y, z, 0, targetID, 0, spriteSet);
            return particle;
        }


    }
}
