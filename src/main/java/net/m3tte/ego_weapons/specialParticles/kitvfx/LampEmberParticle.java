package net.m3tte.ego_weapons.specialParticles.kitvfx;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class LampEmberParticle extends HitParticle {

    private int variation = 0;
    private final int maxVariations = 4;

    private double xSpeed = 0;
    private double ySpeed = 0;
    private double zSpeed = 0;

    float targetQuadSize = 0;
    public LampEmberParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        Random rnd = new Random();
        this.targetQuadSize = 0.3f + rnd.nextFloat() * 0.1f;
        this.quadSize = 0.01f;
        this.lifetime = 10 + rnd.nextInt(10);
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;


        this.variation = rnd.nextInt(maxVariations);

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }


    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAgeModified(this.animatedSprite);
        }

        this.x += this.xSpeed;
        this.y += this.ySpeed;
        this.z += this.zSpeed;

        this.xSpeed *= 0.9f;
        this.ySpeed *= 0.9f;
        this.zSpeed *= 0.9f;
    }


    @Override
    public void render(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float p_225606_3_) {


        this.quadSize = MathHelper.lerp(Math.min(1,((float)this.age / this.lifetime) * 2f), 0, this.targetQuadSize);

        super.render(p_225606_1_, p_225606_2_, p_225606_3_);


    }

    public void setSpriteFromAgeModified(IAnimatedSprite p_217566_1_) {

        int maxVal = maxVariations * this.lifetime;

        int progress = (this.age) + this.variation * this.lifetime;

        this.setSprite(p_217566_1_.get(progress, maxVal));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;



        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LampEmberParticle particle = new LampEmberParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }


    }
}
