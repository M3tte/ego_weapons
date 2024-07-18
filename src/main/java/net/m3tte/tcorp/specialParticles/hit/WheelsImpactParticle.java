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
public class WheelsImpactParticle extends MetaParticle {
    public WheelsImpactParticle(ClientWorld world, double x, double y, double z, double width, double height, double _null) {
        super(world, x, y, z);
        this.x = x + (this.level.random.nextDouble() - 0.5D) * width;
        this.y = y + (this.level.random.nextDouble() + height) * 0.5;
        this.z = z + (this.level.random.nextDouble() - 0.5D) * width;
        this.level.addParticle(TCorpParticleRegistry.WHEELS_SMASH.get(), this.x, this.y + 2, this.z, 0.0D, 0.0D, 0.0D);
        // this.level.addParticle(ParticleTypes.FLASH, this.posX, this.posY + 1.4, this.posZ, 0.0D, 0.0D, 0.0D);
        double d = 0.2F;

        for(int i = 0; i < 20; i++) {
            d = d * (this.level.random.nextBoolean() ? 2.0D : -2.0D);
            double particleMotionX = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 2.0D : -2.0D);
            double particleMotionZ = this.level.random.nextDouble() * d;

            this.level.addParticle(EpicFightParticles.DUST_EXPANSIVE.get(), this.x + ranBetween(-1, 1), this.y + 1 + ranBetween(-0.5f, 0.5f), this.z + ranBetween(-1, 1), particleMotionX, 0.0D, particleMotionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            WheelsImpactParticle particle = new WheelsImpactParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }

    private float ranBetween(float min, float max) {
        return this.level.random.nextFloat()*(max-min) + min;
    }
}