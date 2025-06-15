package net.m3tte.ego_weapons.specialParticles.hit;


import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class LiuHit extends GenericHit {
    public LiuHit(ClientWorld level, double x, double y, double z, double width, double height, double _null, BasicParticleType particleType) {
        super(level, x, y, z, width, height, _null, particleType);
        this.x = x + (this.level.random.nextDouble() - 0.5D) * width;
        this.y = y + (this.level.random.nextDouble() + height) * 0.5;
        this.z = z + (this.level.random.nextDouble() - 0.5D) * width;
        this.level.addParticle(particleType, this.x, this.y + 1, this.z, 0.0D, 0.0D, 0.0D);
        double d = 0.2F;

        for(int i = 0; i < 12; i++) {
            double particleMotionX = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            double particleMotionZ = this.level.random.nextDouble() * d;
            d = d * (this.level.random.nextBoolean() ? 1.0D : -1.0D);
            this.level.addParticle(EpicFightParticles.BLOOD.get(), this.x, this.y + 1, this.z, particleMotionX, 0.0D, particleMotionZ);
        }
    }

}