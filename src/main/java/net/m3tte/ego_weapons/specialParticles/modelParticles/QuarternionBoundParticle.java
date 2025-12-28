package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class QuarternionBoundParticle extends SpriteTexturedParticle {
    protected final IAnimatedSprite spriteProvider;
    protected Vector3f offset = new Vector3f(0,0,0);
    protected Vector3f offsetRate = new Vector3f(0,0,0);

    protected Quaternion rotation = new Quaternion(0,0,0, true);

    protected double ox;
    protected double oy;
    protected double oz;

    boolean flipX = true;
    boolean flipY = true;

    boolean invertX = false;
    boolean invertY = false;

    boolean glowRenderType = false;


    protected Vector3f[] generateVectorArray() {

        return new Vector3f[]{new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F)};
    }


    Entity boundEntity = null;


    private void resolveBoundEntity(int entityUUID, ClientWorld world) {
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
            rotation = new Quaternion(0, -boundEntity.getRotationVector().y, 0, true);
        }
    }


    public QuarternionBoundParticle(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite spriteProvider) {
        super(world, x, y, z);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        this.spriteProvider = spriteProvider;
        offset = offset.copy();
        int scaleAgeModifier = 1 + new Random().nextInt(10);
        this.quadSize = 3f;
        this.lifetime = 12;

        resolveBoundEntity((int) entityUUID, world);

    }

    public QuarternionBoundParticle(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows) {
        super(world, x, y, z);

        this.spriteProvider = spriteProvider;
        this.glowRenderType = glows;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.flipX = true;
        this.quadSize = quadsize;
        this.lifetime = lifetime;
        this.offset = offset;
        this.offsetRate = offsetRate;

        resolveBoundEntity((int) entityUUID, world);

    }

    @Override
    public @NotNull IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;



        offset.add(offsetRate);

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

        Vector3f[] Vector3fs = generateVectorArray();
        float j = this.getQuadSize(tickDelta);
        Vector3f transposeVector = offset.copy();
        Vector3f offsetVector = offsetRate.copy();
        offsetVector.mul(tickDelta);
        transposeVector.add(offsetVector);

        transposeVector.transform(this.rotation);

        Quaternion targetQuart = this.rotation.copy();

        for (int k = 0; k < 4; ++k) {
            Vector3f Vector3f2 = Vector3fs[k];
            Vector3f2.transform(targetQuart);
            //Vector3f2.transform(new Quaternion(0, 0, 0, true));
            Vector3f2.mul(j);
            Vector3f2.add(f, g, h);
            Vector3f2.add(transposeVector);
        }

        float minU = this.getU0();
        float maxU = this.getU1();
        float minV = this.getV0();
        float maxV = this.getV1();
        int l = this.glowRenderType ? 15728880 : this.getLightColor(tickDelta);

        if (flipX) {
            float plc = minU;
            minU = maxU;
            maxU = plc;
        }
        if (flipY) {
            float plc = minV;
            minV = maxV;
            maxV = plc;
        }

        vertexBuilder.vertex(Vector3fs[0].x(), Vector3fs[0].y(), Vector3fs[0].z()).uv(minU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[1].x(), Vector3fs[1].y(), Vector3fs[1].z()).uv(minU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[2].x(), Vector3fs[2].y(), Vector3fs[2].z()).uv(maxU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[3].x(), Vector3fs[3].y(), Vector3fs[3].z()).uv(maxU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();

        if (invertX) {
            float plc = minU;
            minU = maxU;
            maxU = plc;
        }
        if (invertY) {
            float plc = minV;
            minV = maxV;
            maxV = plc;
        }

        vertexBuilder.vertex(Vector3fs[3].x(), Vector3fs[3].y(), Vector3fs[3].z()).uv(maxU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[2].x(), Vector3fs[2].y(), Vector3fs[2].z()).uv(maxU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[1].x(), Vector3fs[1].y(), Vector3fs[1].z()).uv(minU, minV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
        vertexBuilder.vertex(Vector3fs[0].x(), Vector3fs[0].y(), Vector3fs[0].z()).uv(minU, maxV).color(rCol, gCol, bCol, alpha).uv2(l).endVertex();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        private Vector3f offset;
        private Vector3f offsetRate;
        private int lifetime;
        private float quadSize;
        private boolean glows;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
            this.quadSize = 2.5f;
            this.lifetime = 5;
            this.offset = new Vector3f(0.3f,1f,0);
            this.offsetRate = new Vector3f(0.1f,0,0);
            this.glows = false;
        }

        public Provider(IAnimatedSprite spriteSet, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows) {
            this.spriteSet = spriteSet;
            this.quadSize = quadsize;
            this.lifetime = lifetime;
            this.offset = offset;
            this.offsetRate = offsetRate;
            this.glows = glows;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            QuarternionBoundParticle particle = new QuarternionBoundParticle(worldIn, x, y, z, 0, sourceID, 0, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glows);
            return particle;
        }


    }
}
