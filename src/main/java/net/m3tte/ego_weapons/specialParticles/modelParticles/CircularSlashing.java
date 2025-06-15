package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CircularSlashing extends SlashDownInvert {

    private Entity sourceEntity;

    float oRot = 0;
    public CircularSlashing(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glow) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider, quadsize, lifetime, offset, offsetRate, glow);
        this.flipY = false;
        this.flipX = true;
        this.invertY = true;
        this.invertX = true;

        this.quadSize = quadsize;
        this.lifetime = lifetime;
        this.offset = offset;
        this.offsetRate = offsetRate;
        this.glowRenderType = glow;
    }


    @Override
    public void tick() {
        super.tick();

        oRot = this.rotation.z();

    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        super.render(vertexBuilder, renderInfo, tickDelta);
        this.rotation.setZ(oRot + 45*tickDelta);



    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        private Vector3f offset;
        private Vector3f offsetRate;
        private int lifetime;
        private float quadSize;
        private boolean glow;


        public Provider(IAnimatedSprite spriteSet, float quadSize, int lifeTime, Vector3f offsets, boolean glow) {
            this.spriteSet = spriteSet;
            this.quadSize = quadSize; //2.5f;
            this.lifetime = lifeTime; // 5;
            this.offset = offsets; // new Vector3f(0.3f,1f,0);
            this.offsetRate = new Vector3f(0f,0,0);
            this.glow = glow;
        }

        public Provider(IAnimatedSprite spriteSet, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate) {
            this.spriteSet = spriteSet;
            this.quadSize = quadsize;
            this.lifetime = lifetime;
            this.offset = offset;
            this.offsetRate = offsetRate;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            CircularSlashing particle = new CircularSlashing(worldIn, x, y, z, 0, targetID, sourceID, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glow);
            return particle;
        }
    }
}
