package net.m3tte.ego_weapons.specialParticles;

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
public class SolemnLamentParticle extends HitParticle {
    public SolemnLamentParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.quadSize = 2F;
        this.lifetime = 5;
        Random rand = new Random();

        float angle = rand.nextBoolean() ? 0 : 110;

        angle = (float)Math.toRadians(rand.nextFloat() * 20.0F + angle - 20f);
        this.oRoll = angle;
        this.roll = angle;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }



        @Nullable
        @Override
        public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
            SolemnLamentParticle particle = new SolemnLamentParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, spriteSet);
            return particle;
        }
    }
}
