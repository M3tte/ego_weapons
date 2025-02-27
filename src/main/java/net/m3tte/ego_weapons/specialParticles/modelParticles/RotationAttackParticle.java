package net.m3tte.ego_weapons.specialParticles.modelParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RotationAttackParticle extends RotationBoundParticle {

    private Entity sourceEntity;
    public RotationAttackParticle(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double sourceID, IAnimatedSprite spriteProvider) {
        super(world, x, y, z, xSpeed, targetEntityID, sourceID, spriteProvider);

        System.out.println("SOURCE ID IS: "+sourceID);
        if (sourceID == 0) {
            if (world.isClientSide()) {
                sourceEntity = Minecraft.getInstance().player;
            }
        } else {
            sourceEntity = this.level.getEntity((int)sourceID);
        }



        if (sourceEntity != null && this.level.isClientSide()) {
            //this.x = newPos.x;
            //this.y = newPos.y + boundEntity.getBbHeight() * 0.5f;
            //this.z = newPos.z;
            ox = x;
            oy = y;
            oz = z;
            rotation.setY(-sourceEntity.getRotationVector().y);
        }

        this.rotation.add(0,-90,0);
        //this.rotationOffs.set(180,0,0);
        this.quadSize = 2.5f;
        this.lifetime = 5;
        this.offset = new Vector3f(0.3f,1f,0);
        this.offsetRate = new Vector3f(0.1f,0,0);
    }

    @Override
    public String toString() {
        return "RotationAttackParticle{" +
                "ox=" + this.ox +
                ", oy=" + this.oy +
                ", oz=" + this.oz +
                '}';
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            RotationAttackParticle particle = new RotationAttackParticle(worldIn, x, y, z, 0, targetID, sourceID, spriteSet);
            return particle;
        }
    }
}
