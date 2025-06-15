package net.m3tte.ego_weapons.specialParticles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class GenericStrike extends HitParticle {
    public GenericStrike(ClientWorld world, double x, double y, double z, IAnimatedSprite animatedSprite, float quadSize, int lifeTime) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.quadSize = quadSize;
        this.lifetime = lifeTime;
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;
        private int lifeTime = 6;
        private float quadSize = 2.5f;



        public Provider(IAnimatedSprite spriteSet, int lifeTime, float quadSize) {
            this.spriteSet = spriteSet;
            this.lifeTime = lifeTime;
            this.quadSize = quadSize;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            if (EpicFightMod.CLIENT_INGAME_CONFIG.offBloodEffects.getValue()) {
                return null;
            }
            GenericStrike particle = new GenericStrike(worldIn, x, y, z, spriteSet, this.quadSize, this.lifeTime);
            return particle;
        }


    }
}
