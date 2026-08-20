package net.m3tte.ego_weapons.specialParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class UdjatSymbolParticle extends SpriteTexturedParticle {


    float targetQuadSize;
    protected UdjatSymbolParticle(ClientWorld clientWorld, double x, double y, double z, double xa, double ya, double za, IAnimatedSprite animatedSprite) {
        super(clientWorld, x, y, z, xa, ya, za);

        this.setSprite(animatedSprite.get(clientWorld.random));
        this.targetQuadSize = 0.1f + clientWorld.random.nextFloat() * 0.1f;
        this.quadSize = 0.001f;
        this.lifetime = 3 + clientWorld.random.nextInt(2);

        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;

    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float partialTicks) {
        if (this.age < 1) {
            this.quadSize = MathHelper.lerp(partialTicks, 0, targetQuadSize);
        }
        else if (this.age >= this.lifetime) {
            this.quadSize = MathHelper.lerp(partialTicks, targetQuadSize, 0);
        }
        super.render(p_225606_1_, p_225606_2_, partialTicks);



    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public @NotNull IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            UdjatSymbolParticle particle = new UdjatSymbolParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            return particle;
        }


    }
}
