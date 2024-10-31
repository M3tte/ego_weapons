package net.m3tte.ego_weapons.specialParticles.modelParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GreatSplitHorizontal extends RotationBoundParticle {
    public GreatSplitHorizontal(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite spriteProvider) {
        super(world, x, y, z, xSpeed, entityUUID, zSpeed, spriteProvider);

        this.rotation.add(0,180,0);
        //this.rotationOffs.set(180,0,0);
        this.quadSize = 4f;
        this.lifetime = 30;
        this.offset = new Vector3f(0f,0.3f,-3f);
        this.offsetRate = new Vector3f(0f,0,0);
    }

    @Override
    protected Vector3f[] generateVectorArray(boolean inv) {
        int invVar = inv ? -1 : 1;

        return new Vector3f[]{new Vector3f(1.0F * invVar, 0, 1.0F * invVar), new Vector3f(1.0F * invVar, 0, -1.0F * invVar), new Vector3f(-1.0F * invVar, 0, -1.0F * invVar), new Vector3f(-1.0F * invVar, 0, 1.0F * invVar)};
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double zSpeed) {
            GreatSplitHorizontal particle = new GreatSplitHorizontal(worldIn, x, y, z, 0, Minecraft.getInstance().player.getId(), 0, spriteSet);
            return particle;
        }


    }
}
