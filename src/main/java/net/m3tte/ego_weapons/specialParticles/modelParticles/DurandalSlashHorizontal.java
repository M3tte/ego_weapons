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

public class DurandalSlashHorizontal extends RotationAttackParticle {

    private Entity sourceEntity;
    public DurandalSlashHorizontal(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider);
        this.flipY = true;
        this.flipX = true;
        this.invertX = true;
        this.invertY = false;
        this.quadSize = 2f;
        this.lifetime = 5;
    }



    @Override
    protected Vector3f[] generateVectorArray(boolean inv) {
        //int invVar = inv ? -1 : 1;
        // return new Vector3f[]{new Vector3f(1.0F * invVar, 0, 1.0F * invVar), new Vector3f(1.0F * invVar, 0, -1.0F * invVar), new Vector3f(-1.0F * invVar, 0, -1.0F * invVar), new Vector3f(-1.0F * invVar, 0, 1.0F * invVar)};
        if (inv) {
            return new Vector3f[]{new Vector3f(1.0F, 0, -1.0F), new Vector3f(1.0F, 0, 1.0F), new Vector3f(-1.0F, 0.2F, 1.0F), new Vector3f(-1.0F, 0.2F, -1.0F)};
        } else {
            return new Vector3f[]{new Vector3f(1.0F, 0.2f, 1.0F), new Vector3f(1.0F, 0.2f, -1.0F), new Vector3f(-1.0F, 0, -1.0F), new Vector3f(-1.0F, 0, 1.0F)};
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            DurandalSlashHorizontal particle = new DurandalSlashHorizontal(worldIn, x, y, z, 0, targetID, sourceID, spriteSet);
            return particle;
        }
    }
}
