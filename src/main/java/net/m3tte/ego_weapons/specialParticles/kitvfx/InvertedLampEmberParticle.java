package net.m3tte.ego_weapons.specialParticles.kitvfx;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class InvertedLampEmberParticle extends HitParticle {

    private int variation = 0;
    private final int maxVariations = 4;

    Vector3d targetPosition;
    Vector3d positionOffset;
    float targetQuadSize = 0;
    public InvertedLampEmberParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        Random rnd = new Random();
        this.targetQuadSize = 0.2f + rnd.nextFloat() * 0.1f;
        this.quadSize = 0.01f;
        this.lifetime = 6 + rnd.nextInt(6);
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;

        this.variation = rnd.nextInt(maxVariations);

        targetPosition = new Vector3d(x,y,z);
        positionOffset = new Vector3d(rnd.nextFloat() * xSpeed * 2 - xSpeed, rnd.nextFloat() * ySpeed * 2 - ySpeed, rnd.nextFloat() * zSpeed * 2 - zSpeed);

        this.lifetime = (int) (this.lifetime + positionOffset.length());

        this.x += positionOffset.x();
        this.y += positionOffset.y();
        this.z += positionOffset.z();

    }


    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        float easeValue = easeIn((float) this.age / this.lifetime);

        this.x = targetPosition.x() + positionOffset.x() * easeValue;
        this.y = targetPosition.y() + positionOffset.y() * easeValue;
        this.z = targetPosition.z() + positionOffset.z() * easeValue;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAgeModified(this.animatedSprite);
        }
    }


    @Override
    public void render(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float p_225606_3_) {

        float completion = (float)this.age / this.lifetime;

        if (completion < 0.5) {
            this.quadSize = MathHelper.lerp(Math.min(1,completion * 3f), 0, this.targetQuadSize);

        } else {
            this.quadSize = MathHelper.lerp(Math.min(1,completion - 1), this.targetQuadSize, 0);
        }




        super.render(p_225606_1_, p_225606_2_, p_225606_3_);


    }

    private float easeIn(float inputvalue) {

        if (inputvalue >= 1)
            return 1;

        if (inputvalue <= 0)
            return 0;

        inputvalue += 1;
        return (2*inputvalue - inputvalue * inputvalue);
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
            InvertedLampEmberParticle particle = new InvertedLampEmberParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }


    }
}
