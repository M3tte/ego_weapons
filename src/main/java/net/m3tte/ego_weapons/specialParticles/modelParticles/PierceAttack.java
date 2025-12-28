package net.m3tte.ego_weapons.specialParticles.modelParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PierceAttack extends RotationAttackParticle {

    private Entity sourceEntity;
    public PierceAttack(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider);

        this.flipX = false;
        this.quadSize = quadsize;
        this.lifetime = lifetime;
        this.offset = offset.copy();
        this.offsetRate = offsetRate;
        this.glowRenderType = glows;

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
            PierceAttack particle = new PierceAttack(worldIn, x, y, z, 0, targetID, sourceID, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glows);
            return particle;
        }
    }
}
