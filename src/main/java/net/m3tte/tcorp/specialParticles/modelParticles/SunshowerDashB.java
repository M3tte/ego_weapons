package net.m3tte.tcorp.specialParticles.modelParticles;

import net.m3tte.tcorp.TCorpParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SunshowerDashB extends RotationBoundParticle {
    public SunshowerDashB(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite spriteProvider) {
        super(world, x, y, z, xSpeed, entityUUID, zSpeed, spriteProvider);
        this.level.addParticle(TCorpParticleRegistry.SUNSHOWER_DRIFT.get(), this.x, this.y-0f, this.z, 0.0D, entityUUID, 0.0D);

        this.rotation.add(0,-90,0);
        this.quadSize = 4f;
        this.lifetime = 14;
        this.offset = new Vector3f(2f,0,0);
        this.offsetRate = new Vector3f(0.1f,0,0);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double zSpeed) {
            SunshowerDashB particle = new SunshowerDashB(worldIn, x, y, z, 0, Minecraft.getInstance().player.getId(), 0, spriteSet);
            return particle;
        }


    }
}
