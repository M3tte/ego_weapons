package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class VertSlashShockwaveEffect extends SlashShockwaveEffect {

    float targetQuadSize = 0;
    Random r = new Random();


    public VertSlashShockwaveEffect(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider);
    }

    @Override
    protected Vector3f[] generateVectorArray() {

        return new Vector3f[]{new Vector3f(0.0F, 1.0F, -1.0F), new Vector3f(0.0F, 1.0F, 1.0F), new Vector3f(0.0F, -1.0F, 1.0F), new Vector3f(0.0F, -1.0F, -1.0F)};
    }



    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            VertSlashShockwaveEffect particle = new VertSlashShockwaveEffect(worldIn, x, y, z, 0, targetID, sourceID, spriteSet);
            return particle;
        }
    }
}
