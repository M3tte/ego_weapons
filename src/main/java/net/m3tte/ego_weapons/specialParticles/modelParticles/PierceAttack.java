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
    public PierceAttack(ClientWorld world, double x, double y, double z, double xSpeed, double sourceID, double zSpeed, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows, float xRotAdd, float yRotAdd, float zRotAdd) {
        super(world, x, y, z, xSpeed, sourceID, zSpeed, spriteProvider);

        this.flipX = false;
        this.quadSize = quadsize;
        this.lifetime = (int) (lifetime + xSpeed);
        this.offset = offset.copy();
        this.offsetRate = offsetRate;
        this.glowRenderType = glows;
        this.rotation.add(xRotAdd, yRotAdd, zRotAdd);
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        private Vector3f offset;
        private Vector3f offsetRate;
        private int lifetime;
        private float quadSize;

        private boolean glows;

        float xRotAdd = 0;
        float yRotAdd = 0;
        float zRotAdd = 0;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
            this.quadSize = 2.5f;
            this.lifetime = 5;
            this.offset = new Vector3f(0.3f,1f,0);
            this.offsetRate = new Vector3f(0.1f,0,0);
            this.glows = false;
        }

        public Provider(IAnimatedSprite spriteSet, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows, float xRotAdd, float yRotAdd, float zRotAdd) {
            this.spriteSet = spriteSet;
            this.quadSize = quadsize;
            this.lifetime = lifetime;
            this.offset = offset;
            this.offsetRate = offsetRate;
            this.glows = glows;
            this.xRotAdd = xRotAdd;
            this.zRotAdd = zRotAdd;
            this.yRotAdd = yRotAdd;
        }



        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double sourceID, double zSpeed) {
            PierceAttack particle = new PierceAttack(worldIn, x, y, z, xSpeed, sourceID, zSpeed, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glows, this.xRotAdd, this.yRotAdd, this.zRotAdd);
            return particle;
        }
    }
}
