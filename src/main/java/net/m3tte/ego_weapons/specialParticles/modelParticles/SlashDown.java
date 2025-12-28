package net.m3tte.ego_weapons.specialParticles.modelParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SlashDown extends RotationAttackParticle {

    private Entity sourceEntity;
    public SlashDown(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glow) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider);
        this.flipY = true;
        this.flipX = true;
        this.invertY = false;

        this.quadSize = quadsize;
        this.lifetime = lifetime;
        this.offset = offset;
        this.offsetRate = offsetRate;
        this.glowRenderType = glow;
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
            this.offsetRate = new Vector3f(0.1f,0,0);
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
            SlashDown particle = new SlashDown(worldIn, x, y, z, 0, targetID, sourceID, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glow);
            return particle;
        }
    }
}
