package net.m3tte.tcorp.specialParticles.hit;


import net.m3tte.tcorp.TCorpParticleRegistry;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.particle.EpicFightParticles;

@OnlyIn(Dist.CLIENT)
public class MimicryVerticalSplitHit extends MetaParticle {
    public MimicryVerticalSplitHit(ClientWorld level, double x, double y, double z, double width, double height, double _null) {
        super(level, x, y, z);
        this.level.addParticle(TCorpParticleRegistry.MIMICRY_VERTICAL_STRIKE.get(), this.x, this.y + 2, this.z, 0.0D, 0.0D, 0.0D);
        double d = 0.4F;

        for(int i = 0; i < 50; i++) {
            Vector3d rot = MathUtils.getVectorForRotation(0, (float)height);
            double particleMotionX = rot.x * this.random.nextFloat() * (this.random.nextFloat() * 1.6 - 1);
            double particleMotionZ = rot.z * this.random.nextFloat() * (this.random.nextFloat() * 1.6 - 1);
            double particleMotionY = this.random.nextFloat() * 0.6F -0.3;
            this.level.addParticle(EpicFightParticles.BLOOD.get(), this.x, this.y, this.z, particleMotionX, 0, particleMotionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MimicryVerticalSplitHit particle = new MimicryVerticalSplitHit(levelIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }
    }
}