package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class SolemnLamentShockwave extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteProvider;

    public SolemnLamentShockwave(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteProvider) {
        super(world, x, y, z);

        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        this.spriteProvider = spriteProvider;

        int scaleAgeModifier = 1 + new Random().nextInt(10);
        this.quadSize = 4f;
        this.lifetime = 8;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        this.setSpriteFromAge(spriteProvider);

        Vector3d vec3d = renderInfo.getPosition();
        float f = (float) (MathHelper.lerp(tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float) (MathHelper.lerp(tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float) (MathHelper.lerp(tickDelta, this.zo, this.z) - vec3d.z());
        Quaternion quaternion2;
        if (this.roll == 0.0F) {
            quaternion2 = renderInfo.rotation();
        } else {
            quaternion2 = new Quaternion(renderInfo.rotation());
            float i = MathHelper.lerp(tickDelta, this.oRoll, this.roll);
            quaternion2.mul(Vector3f.ZP.rotationDegrees(i));
        }

        Vector3f Vector3f = new Vector3f(-1.0F, -1.0F, 0.0F);
        Vector3f.transform(quaternion2);
        Vector3f[] Vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float j = this.getQuadSize(tickDelta);

        for (int k = 0; k < 4; ++k) {
            Vector3f Vector3f2 = Vector3fs[k];
            Vector3f2.transform(new Quaternion(90f, 0f, 0f, true));
            Vector3f2.mul(j);
            Vector3f2.add(f, g, h);
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


        vertexBuilder.vertex(Vector3fs[3].x(), Vector3fs[3].y(), Vector3fs[3].z()).uv(minU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[2].x(), Vector3fs[2].y(), Vector3fs[2].z()).uv(minU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[1].x(), Vector3fs[1].y(), Vector3fs[1].z()).uv(maxU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[0].x(), Vector3fs[0].y(), Vector3fs[0].z()).uv(maxU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SolemnLamentShockwave particle = new SolemnLamentShockwave(worldIn, x, y, z, spriteSet);
            return particle;
        }


    }
}
