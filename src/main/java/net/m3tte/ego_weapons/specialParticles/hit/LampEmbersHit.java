package net.m3tte.ego_weapons.specialParticles.hit;


import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class LampEmbersHit extends GenericHit {
    public LampEmbersHit(ClientWorld level, double x, double y, double z, double width, double height, double _null, BasicParticleType particleType) {
        super(level, x, y, z, width, height, _null, particleType);
        double d = 0.13F;
        for(int i = 0; i < 12; i++) {
            double particleMotionX = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionY = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionZ = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            this.level.addParticle(EgoWeaponsParticles.LAMP_EMBERS.get(), this.x, this.y + 1, this.z, particleMotionX, particleMotionY, particleMotionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {

        private BasicParticleType sourceParticle;
        public Provider(BasicParticleType sourceParticle) {
            this.sourceParticle = sourceParticle;
        }
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LampEmbersHit particle = new LampEmbersHit(levelIn, x, y, z, xSpeed, ySpeed, zSpeed, this.sourceParticle);
            return particle;
        }
    }
}