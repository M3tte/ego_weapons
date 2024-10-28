package net.m3tte.tcorp.specialParticles.hit;


import net.m3tte.tcorp.TCorpParticleRegistry;
import net.m3tte.tcorp.particle.RobotShrapnelParticle;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class DoubtDeathExplosion extends MetaParticle {
    public DoubtDeathExplosion(ClientWorld level, double x, double y, double z, double width, double height, double _null) {
        super(level, x, y, z);
        this.x = x + (this.level.random.nextDouble() - 0.5D) * width;
        this.y = y + (this.level.random.nextDouble() + height) * 0.5;
        this.z = z + (this.level.random.nextDouble() - 0.5D) * width;
        this.level.addParticle(TCorpParticleRegistry.DOUBT_EXPLOSION_EFFECT.get(), this.x, this.y + 1, this.z, 0.0D, 0.0D, 0.0D);
        double d = 0.2F;

        for(int i = 0; i < 12; i++) {
            double particleMotionY = this.level.random.nextFloat() * d;
            double particleMotionX = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionZ = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            if (i%2 == 0) {
                this.level.addParticle(ParticleTypes.LAVA, this.x, this.y + 1, this.z, particleMotionX, 0.0D, particleMotionZ);
                this.level.addParticle(ParticleTypes.FLAME, this.x, this.y + 1, this.z, particleMotionX, 0.0D, particleMotionZ);
            }

            this.level.addParticle(RobotShrapnelParticle.particle, this.x, this.y + this.level.random.nextFloat() * 1.5 + 0.3, this.z, particleMotionX * 2, particleMotionY * 2, particleMotionZ * 2);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DoubtDeathExplosion particle = new DoubtDeathExplosion(levelIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}