package net.m3tte.tcorp.specialParticles.hit;


import net.m3tte.tcorp.TCorpParticleRegistry;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class DurandalHitEffect extends MetaParticle {
    public DurandalHitEffect(ClientWorld level, double x, double y, double z, double width, double height, double _null) {
        super(level, x, y, z);
        this.x = x + (this.level.random.nextDouble() - 0.5D) * width;
        this.y = y + (this.level.random.nextDouble() + height) * 0.5;
        this.z = z + (this.level.random.nextDouble() - 0.5D) * width;
        this.level.addParticle(TCorpParticleRegistry.DURANDAL_STRIKE.get(), this.x, this.y + 1, this.z, 0.0D, 0.0D, 0.0D);
        double d = 0.2F;

        for(int i = 0; i < 8; i++) {
            double particleMotionX = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionZ = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            this.level.addParticle(EpicFightParticles.BLOOD.get(), this.x, this.y + 1, this.z, particleMotionX, 0.0D, particleMotionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DurandalHitEffect particle = new DurandalHitEffect(levelIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}