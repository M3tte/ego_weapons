package net.m3tte.ego_weapons.specialParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
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
public class OutgoingEmberParticle extends HitParticle {


    float oQuadSize = 0;
    float nQuadSize = 0;

    float orCol = 0;
    float ogCol = 0;
    float obCol = 0;
    float rColorSpeedMultiplier = 0f;
    float bColorSpeedMultiplier = 0f;
    float gColorSpeedMultiplier = 0f;
    float rollSpeed = 0f;

    double startX = 0;
    double startY = 0;
    double startZ = 0;

    double targetX = 0;
    double targetY = 0;
    double targetZ = 0;



    private float calculateProgressEaseOut(float time) {
        float progress = time / this.lifetime;


        return 1 - (1 - progress) * (1 - progress);
    }

    private float calculatedQuadSizeMult(float time) {
        float progress = time / (this.lifetime - 5);
        if (progress < 0.3f) {
            return MathHelper.lerp(progress / 0.3f, 0, 1);
        } else if (progress > 0.4f) {
            return MathHelper.lerp(Math.min(1, (progress-0.7f) / 0.3f),1, 0);
        } else {
            return 1;
        }
    }

    public OutgoingEmberParticle(ClientWorld world, double x, double y, double z, double horizontalOffset, double verticalOffset, double time, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        this.rCol = 1.0F;


        float gColValue = world.random.nextFloat() * 0.2f;

        this.gCol = 0.35F + gColValue;

        this.bCol = 0.2F + world.random.nextFloat() * gColValue;

        this.orCol = rCol;
        this.ogCol = gCol;
        this.obCol = bCol;

        this.quadSize = 0.0001f;
        this.oQuadSize = 0.02f + world.random.nextFloat() * 0.02f;
        this.lifetime = (int) time + world.random.nextInt(6);



        this.rColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        this.bColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        this.gColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;
        this.rollSpeed = world.random.nextFloat() - 0.5f;

        this.startX  = x;
        this.startY = y;
        this.startZ = z;

        this.targetX = (x + rand.nextFloat() * horizontalOffset * 2)-horizontalOffset;
        this.targetZ = (z + rand.nextFloat() * horizontalOffset * 2)-horizontalOffset;
        this.targetY = (y + rand.nextFloat() * verticalOffset * 2)-verticalOffset;

        this.x = this.startX;
        this.y = this.startY;
        this.z = this.startZ;
        this.xo = this.startX;
        this.yo = this.startY;
        this.zo = this.startZ;
    }

    @Override
    public void tick() {
        super.tick();
        this.oRoll = roll;
        this.roll += 0.05f * this.rollSpeed;

        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float partialTicks) {
        super.render(vertexBuilder, renderInfo, partialTicks);

        this.quadSize = this.oQuadSize * calculatedQuadSizeMult(this.age + partialTicks);

        float progress = calculateProgressEaseOut(this.age + partialTicks);

        this.x = MathHelper.lerp(progress, this.startX, this.targetX);
        this.y = MathHelper.lerp(progress, this.startY, this.targetY);
        this.z = MathHelper.lerp(progress, this.startZ, this.targetZ);


        this.rCol = this.orCol * (1 - ((this.age + partialTicks) / this.lifetime) * 0.3f);
        this.bCol = this.obCol * (1 - ((this.age + partialTicks) / this.lifetime));
        this.gCol = this.ogCol * (1 - ((this.age + partialTicks) / this.lifetime));

        this.rCol = Math.max(0, Math.min(1, this.rCol + (float) Math.sin((this.age + partialTicks) * this.rColorSpeedMultiplier) * 0.2f));
        this.gCol = Math.max(0, Math.min(1, this.gCol + (float) Math.sin((this.age + partialTicks) * this.gColorSpeedMultiplier) * 0.1f));
        this.bCol = Math.max(0, Math.min(this.gCol, this.bCol + (float) Math.sin((this.age + partialTicks) * this.bColorSpeedMultiplier) * 0.1f));


    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            OutgoingEmberParticle particle = new OutgoingEmberParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }


    }
}
