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
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.EpicFightParticleRenderTypes;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class MaoParticle extends HitParticle {


    float quadSizeDecay = 0;
    float oQuadSize = 0;
    float nQuadSize = 0;

    float orCol = 0;
    float ogCol = 0;
    float obCol = 0;
    double oYPos = 0;
    double floatSpeedMultiplier = 0f;
    double rotSpeedMult = 0f;
    float rColorSpeedMultiplier = 0f;
    float bColorSpeedMultiplier = 0f;
    float gColorSpeedMultiplier = 0f;
    float rollSpeed = 0f;

    double amplitude = 0;
    double gravityVal = 0;
    int startIdxTime = 0;
    Entity sourceEntity;

    public MaoParticle(ClientWorld world, double x, double y, double z, double entityId, double ySpeed, double zSpeed, IAnimatedSprite animatedSprite) {
        super(world, x, y, z, animatedSprite);
        float gColValue = world.random.nextFloat() * 0.3f;
        float rColValue = world.random.nextFloat() * 0.1f - gColValue;
        this.rCol = 1.0F - rColValue;


        this.gCol = 1.0F - gColValue;

        this.bCol = 0F + world.random.nextFloat() * gColValue;

        this.orCol = rCol;
        this.ogCol = gCol;
        this.obCol = bCol;

        this.quadSize = 0.04f + world.random.nextFloat() * 0.02f;
        this.oQuadSize = quadSize;
        this.nQuadSize = quadSize;
        this.lifetime = 100 + world.random.nextInt(20);
        this.quadSizeDecay = this.quadSize / this.lifetime;

        this.gravityVal = gravity;

        this.amplitude = 0.05f + world.random.nextFloat() * 0.1f;
        this.rotSpeedMult = 0.2f + world.random.nextFloat() * 0.1f;
        this.startIdxTime = world.random.nextInt(50);

        this.floatSpeedMultiplier = 0.2f + world.random.nextFloat() * 0.2f;
        this.rColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        this.bColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        this.gColorSpeedMultiplier = 0.3f + world.random.nextFloat() * 0.3f;
        Random rand = new Random();
        float angle = (float)Math.toRadians((double)(rand.nextFloat() * 90.0F));
        this.oRoll = angle;
        this.roll = angle;
        this.rollSpeed = world.random.nextFloat() - 0.5f;
        this.oYPos = this.y;

        if (entityId == 0) {
            if (world.isClientSide()) {
                sourceEntity = Minecraft.getInstance().player;
            }
        } else {
            sourceEntity = this.level.getEntity((int)entityId);
        }



        if (sourceEntity != null && this.level.isClientSide()) {
            //this.x = newPos.x;
            //this.y = newPos.y + boundEntity.getBbHeight() * 0.5f;
            //this.z = newPos.z;
            this.x = sourceEntity.getX();
            this.y = sourceEntity.getY();
            this.z = sourceEntity.getZ();
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
        }

        this.quadSize = 0;
        this.setAlpha(0.2f + random.nextFloat() * 0.4f);
    }

    @Override
    public void tick() {
        super.tick();
        this.oQuadSize = this.nQuadSize;
        this.nQuadSize -= this.quadSizeDecay;
        this.oRoll = roll;
        this.roll += 0.05f * this.rollSpeed;
        this.oYPos += this.gravityVal;

        if (!(sourceEntity instanceof LivingEntity)) {
            this.remove();
        }
    }



    @Override
    public IParticleRenderType getRenderType() {
        return EpicFightParticleRenderTypes.BLEND_LIGHTMAP_PARTICLE;
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float partialTicks) {
        super.render(vertexBuilder, renderInfo, partialTicks);

        if (!(sourceEntity instanceof LivingEntity)) {
            this.remove();
            return;
        }

        this.quadSize = this.oQuadSize + (this.nQuadSize - this.oQuadSize) * partialTicks;

        this.y = sourceEntity.getY() + 0.4f + Math.sin((this.age + partialTicks + this.startIdxTime) * this.floatSpeedMultiplier * (1 - ((this.age + partialTicks) / this.lifetime) * 0.5f)) * amplitude;
        double outWardsValue = 1 + Math.sin((this.age + partialTicks + this.startIdxTime) * 0.2f) * 0.1f;

        //outWardsValue *= Math.min(1, this.age / 40);

        this.x = sourceEntity.getX() + Math.sin((this.age + partialTicks + this.startIdxTime) * this.rotSpeedMult) * outWardsValue;
        this.z = sourceEntity.getZ() + Math.cos((this.age + partialTicks + this.startIdxTime) * this.rotSpeedMult) * outWardsValue;

        this.rCol = this.orCol * (1 - ((this.age + partialTicks) / this.lifetime) * 0.2f);
        this.bCol = this.obCol * (1 - ((this.age + partialTicks) / this.lifetime) * 0.2f);
        this.gCol = this.ogCol * (1 - ((this.age + partialTicks) / this.lifetime) * 0.2f);

        this.rCol = Math.max(0, Math.min(1, this.rCol + (float) Math.sin((this.age + partialTicks) * this.rColorSpeedMultiplier) * 0.1f));
        this.gCol = Math.max(0, Math.min(1, this.gCol + (float) Math.sin((this.age + partialTicks) * this.gColorSpeedMultiplier) * 0.1f));
        this.bCol = Math.max(0, Math.min(this.gCol, this.bCol + (float) Math.sin((this.age + partialTicks) * this.bColorSpeedMultiplier) * 0.1f));


    }

    @Override
    public boolean shouldCull() {
        return false;
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
            MaoParticle particle = new MaoParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }


    }
}
