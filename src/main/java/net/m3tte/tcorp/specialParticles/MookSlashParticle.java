package net.m3tte.tcorp.specialParticles;

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
public class MookSlashParticle extends HitParticle {
    public MookSlashParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        Random rand = new Random();
        float colormult = 0.5f + rand.nextFloat() * 0.5f;
        this.rCol = colormult;
        this.gCol = colormult;
        this.bCol = colormult;
        this.quadSize = 0.8F + rand.nextFloat() * 0.6f;
        this.lifetime = 5 + rand.nextInt(4);
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 330.0F));
        this.oRoll = angle;
        this.roll = angle;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            if (EpicFightMod.CLIENT_INGAME_CONFIG.offBloodEffects.getValue()) {
                return null;
            }
            MookSlashParticle particle = new MookSlashParticle(worldIn, x, y, z, spriteSet);
            return particle;
        }




    }
}
