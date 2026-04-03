package net.m3tte.ego_weapons.specialParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class UdjatBlueSandParticle extends HitParticle {


    float quadSizeDecay = 0;
    float oQuadSize = 0.01f;
    float nQuadSize = 0.01f;
    float targetQuadSize = 0;

    float orCol = 0;
    float ogCol = 0;
    float obCol = 0;
    double oYOffs = 0;
    double floatSpeedMultiplier = 0f;
    float rColorSpeedMultiplier = 0f;
    float bColorSpeedMultiplier = 0f;
    float gColorSpeedMultiplier = 0f;
    float rollSpeed = 0f;

    double amplitude = 0;
    double gravityVal = 0;

    double xVel = 0;
    double yVel = 0;
    double zVel = 0;

    double tickTimeMult = 0;

    double xOffs = 0;
    double yOffs = 0;
    double zOffs = 0;

    Entity targetEntity = null;



    public UdjatBlueSandParticle(ClientWorld world, double x, double y, double z, double targetEntityId, double speed, double zs, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);

        float gColValue = world.random.nextFloat() * 0.15f;
        float rColValue = world.random.nextFloat() * 0.15f;
        float bColValue = world.random.nextFloat() * 0.15f;
        this.rCol = 0.65F + rColValue;

        this.gCol = 0.55F + gColValue;

        this.bCol = 0.3F + bColValue;

        this.orCol = rCol;
        this.ogCol = gCol;
        this.obCol = bCol;

        this.targetEntity = world.getEntity((int)targetEntityId);

        if (this.targetEntity == null && world.isClientSide()) {
            this.targetEntity = Minecraft.getInstance().player;

        }

        if (this.targetEntity != null) {
            Vector3d targetEntPos = this.targetEntity.position();

            this.xOffs = x - targetEntPos.x();
            this.yOffs = y - targetEntPos.y();
            this.zOffs = z - targetEntPos.z();
        }



        this.quadSize = 0.0000f;
        this.targetQuadSize = 0.0085f + world.random.nextFloat() * 0.02f;
        this.lifetime = 50 + world.random.nextInt(20);
        this.quadSizeDecay = this.targetQuadSize / (this.lifetime - 6);

        this.gravityVal = -0.03f + random.nextFloat() * -0.01f;

        this.amplitude = 0.3f;

        this.tickTimeMult = world.random.nextFloat() * 10;

        this.xVel = (random.nextFloat() - 0.5f) * speed * 2;
        this.yVel = (random.nextFloat() - 0.5f) * speed * 2;
        this.zVel = (random.nextFloat() - 0.5f) * speed * 1.3f;

        this.floatSpeedMultiplier = 0.2f + world.random.nextFloat() * 0.2f * 1;
        this.rColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        this.bColorSpeedMultiplier = 0.4f + world.random.nextFloat() * 0.4f;
        this.gColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;
        this.rollSpeed = world.random.nextFloat() - 0.5f;
        this.oYOffs = this.yOffs;


    }

    @Override
    public void tick() {
        super.tick();
        this.oQuadSize = this.nQuadSize;

        if (this.age <= 6 && this.age > 0) {
            this.nQuadSize += this.targetQuadSize / 6;

        } else {
            this.nQuadSize -= this.quadSizeDecay;

        }

        this.oRoll = roll;
        this.roll += 0.05f * this.rollSpeed;
        this.yOffs += this.gravityVal + this.yVel;
        this.xOffs += xVel;
        this.zOffs += zVel;
        this.xVel *= 0.8f;
        this.yVel *= 0.9f;
        this.zVel *= 0.8f;

        if (this.targetEntity != null) {
            this.x = this.targetEntity.getX() + xOffs;
            this.y = this.targetEntity.getY() + yOffs;
            this.z = this.targetEntity.getZ() + zOffs;


        } else {
            this.y += yOffs;
        }


    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float partialTicks) {
        super.render(vertexBuilder, renderInfo, partialTicks);

        float time = (float) (this.age + partialTicks + this.tickTimeMult);

        this.quadSize = this.oQuadSize + (this.nQuadSize - this.oQuadSize) * partialTicks;

        this.yOffs = this.oYOffs + Math.sin((time) * this.floatSpeedMultiplier * (1 - ((time) / this.lifetime) * 0.5f)) * amplitude;

        this.rCol = this.orCol * (1 - (time / this.lifetime) * 0.5f);
        this.bCol = this.obCol * (1 - (time / this.lifetime) * 0.5f);
        this.gCol = this.ogCol * (1 - (time / this.lifetime) * 0.5f);

        float colorDecay = 1 - 0.3f*(time) / this.lifetime;

        double colorMod = Math.min(1, Math.sin(time) * colorDecay);
        this.rCol = (float) Math.max(this.rCol, colorMod);
        this.gCol = (float) Math.max(this.gCol, colorMod);
        this.bCol = (float) Math.max(this.bCol, Math.min(1,colorMod * 1.5f));

        //this.rCol = Math.max(0, Math.min(1, this.rCol + (float) Math.sin((this.age + partialTicks) * this.rColorSpeedMultiplier) * 0.1f));
        //this.gCol = Math.max(0, Math.min(1, this.gCol + (float) Math.sin((this.age + partialTicks) * this.gColorSpeedMultiplier) * 0.1f));
        //this.bCol = Math.max(0, Math.min(this.gCol, this.bCol + (float) Math.sin((this.age + partialTicks) * this.bColorSpeedMultiplier) * 0.1f));
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
            UdjatBlueSandParticle particle = new UdjatBlueSandParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }


    }
}
