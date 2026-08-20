package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static net.minecraft.client.particle.IParticleRenderType.NO_RENDER;
import static net.minecraft.client.particle.IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;


public class ArayashikiShockwaveEffect extends RotationAttackParticle {

    private Entity sourceEntity;
    float targetQuadSize = 0;
    Random r = new Random();

    public ArayashikiShockwaveEffect(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider);
        this.flipY = true;
        this.flipX = true;
        this.invertX = true;
        this.invertY = false;
        this.lifetime = 1000 + r.nextInt(8);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.alpha = 1f;
        this.offset = new Vector3f(0,1f,-0.2f);
        this.targetQuadSize = 4.2f + r.nextFloat();
        this.quadSize = 0.001f;
        this.rotation.add(r.nextInt(40) - 20, 90 + r.nextInt(20) - 10, r.nextInt(40) - 20);

        synchronized (EgoWeaponsRenderSystem.getDistortionParticles()) {
            EgoWeaponsRenderSystem.getDistortionParticles().add(this);
        }

        this.offsetRate = new Vector3f((float) 0, 0, 0.25f + r.nextFloat() * 0.03f);
    }



    @Override
    public @NotNull IParticleRenderType getRenderType() {
        return PARTICLE_SHEET_TRANSLUCENT;
    }


    @Override
    protected Vector3f[] generateVectorArray() {

        return new Vector3f[]{new Vector3f(1.0F, 0.0F, -1.0F), new Vector3f(1.0F, 0F, 1.0F), new Vector3f(-1.0F, 0.0F, 1.0F), new Vector3f(-1.0F, 0.0F, -1.0F)};
    }

    @Override
    public void remove() {
        super.remove();

        synchronized (EgoWeaponsRenderSystem.getDistortionParticles()) {
            EgoWeaponsRenderSystem.getDistortionParticles().remove(this);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.offsetRate.mul(0.8f);
        // this.alpha = Math.min(1f - ((float) this.age / this.lifetime), 0);
    }

    /*
    @Override
    public float getACol(boolean renderState) {
        return super.getACol(renderState) * (renderState ? 1 : 0.66f);
    }
    */
    @Override
    public float getBCol(boolean renderState) {
        return super.getBCol(renderState) * (renderState ? 0.75f : 1);
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        super.render(vertexBuilder, renderInfo, tickDelta);

        this.quadSize = lerpPercentage(this.age + tickDelta, this.targetQuadSize, 1);
        this.alpha = Math.max(0.02f,1 - (this.age + tickDelta) / this.lifetime);
    }

    private float lerpPercentage(float x, float maxValue, float multiplier) {
        return maxValue - maxValue / (Math.max(1f, 1 + x * multiplier));
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            ArayashikiShockwaveEffect particle = new ArayashikiShockwaveEffect(worldIn, x, y, z, 0, targetID, sourceID, spriteSet);
            return particle;
        }
    }
}
