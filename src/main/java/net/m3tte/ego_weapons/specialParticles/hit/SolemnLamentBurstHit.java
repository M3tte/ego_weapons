package net.m3tte.ego_weapons.specialParticles.hit;


import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class SolemnLamentBurstHit extends MetaParticle {

    private float randBetween(float min, float max) {
        return (random.nextFloat() * (max-min))+min;
    }

    public SolemnLamentBurstHit(ClientWorld level, double x, double y, double z, double width, double height, double _null) {
        super(level, x, y, z);
        this.x = x + (this.level.random.nextDouble() - 0.5D) * width;
        this.y = y + (height) * 0.5;
        this.z = z + (this.level.random.nextDouble() - 0.5D) * width;
        for (int i = 0; i < 2; i++) {
            this.level.addParticle(EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_DEPARTED.get(), this.x + randBetween(-1f, 1f), this.y + 1 + randBetween(-0.5f, 0.5f), this.z + randBetween(-0.5f, 0.5f), 0.0D, 0.0D, 0.0D);
            this.level.addParticle(EgoWeaponsParticles.SOLEMN_LAMENT_FIRE_LIVING.get(), this.x + randBetween(-1f, 1f), this.y + 1 + randBetween(-0.5f, 0.5f), this.z + randBetween(-0.5f, 0.5f), 0.0D, 0.0D, 0.0D);
        }
        double d = 0.2F;

        for(int i = 0; i < 15; i++) {
            double particleMotionX = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionZ = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionY = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            this.level.addParticle(EpicFightParticles.BLOOD.get(), this.x, this.y + 1, this.z, particleMotionX, 0.0D, particleMotionZ);
            this.level.addParticle(EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), this.x, this.y + 1, this.z, particleMotionX * 0.1, particleMotionY * 0.25, particleMotionZ * 0.1);
            this.level.addParticle(EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), this.x, this.y + 1, this.z, particleMotionX * 0.05, particleMotionY * 0.25, particleMotionZ * 0.05);
            this.level.addParticle(EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), this.x, this.y + 1, this.z, particleMotionX * 0.05, particleMotionY * 0.25, particleMotionZ * 0.05);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SolemnLamentBurstHit particle = new SolemnLamentBurstHit(levelIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}