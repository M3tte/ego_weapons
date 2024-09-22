package net.m3tte.tcorp.specialParticles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class SolemnLamentButterfly extends HitParticle {
    private final IAnimatedSprite spriteSet;
    private float angularVelocity;
    int startFrame = 0;
    public SolemnLamentButterfly(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        Random rand = new Random();
        this.angularVelocity = (rand.nextFloat() - 0.5f) * 0.1f;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.quadSize = 0.15F;
        this.lifetime = 20 + rand.nextInt(40);
        this.gravity = (float) -0.2;
        this.xd = vx * 0.7;
        this.yd = vy * 0.4 + Math.abs(vy * 0.8);
        this.zd = vz * 0.7;
        this.startFrame = rand.nextInt(3) * 2 + 1;
        this.spriteSet = animatedSprite;

        int val = startFrame + ((this.age / 5) % 2);
        this.setSprite(this.spriteSet.get(val, 6));

        float angle = rand.nextBoolean() ? 0 : 110;

        angle = (float)Math.toRadians(rand.nextFloat() * 20.0F + angle - 20f);
        this.oRoll = angle;
        this.roll = angle;
    }


    @Override
    public void tick() {
        super.tick();


        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        int val = startFrame + ((this.age / 3) % 2);
        this.setSprite(this.spriteSet.get(val, 6));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }



        @Nullable
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SolemnLamentButterfly particle = new SolemnLamentButterfly(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            return particle;
        }
    }
}
