package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.particle.IParticleRenderType.NO_RENDER;
import static net.minecraft.client.particle.IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;

public class RendSpaceEffect extends RotationAttackParticle {

    private Entity sourceEntity;
    public RendSpaceEffect(ClientWorld world, double x, double y, double z, double xSpeed, double targetEntityID, double zSpeed, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows, float xRot, float yRot, float zRot) {
        super(world, x, y, z, xSpeed, targetEntityID, zSpeed, spriteProvider);

        float totalZRot = zRot + (float)zSpeed;
        float totalRotRadians = (float) Math.toRadians(totalZRot);
        this.rCol = 0.5f + (float) (Math.cos(totalRotRadians) * xSpeed * 0.5f);
        this.gCol = 0.5f + (float) (Math.sin(totalRotRadians) * xSpeed * 0.5f);

        //System.out.println("Processed rotation is : "+totalZRot+"° in radians: "+totalRotRadians+" - RCol Calc = "+(Math.sin(Math.toRadians(totalRotRadians))+" GCol Calc = "+this.gCol);

        this.bCol = 0f;
        this.flipX = false;
        this.quadSize = quadsize;





        this.rotation.add(xRot,yRot,totalZRot);

        this.lifetime = (int) (lifetime * Math.abs(xSpeed));
        this.offset = offset.copy();
        this.offsetRate = offsetRate;
        this.glowRenderType = glows;



        synchronized (EgoWeaponsRenderSystem.getDistortionParticles()) {
            EgoWeaponsRenderSystem.getDistortionParticles().add(this);
        }
    }

    @Override
    public @NotNull IParticleRenderType getRenderType() {
        return NO_RENDER;
    }

    @Override
    public void tick() {
        super.tick();


    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        this.alpha = (float) (1 - Math.sin(((this.age + tickDelta) / this.lifetime)*1.57f));
        // this.bCol = 0 + 0.30f * Math.min(1, (this.age + tickDelta) / 40f);

        super.render(vertexBuilder, renderInfo, tickDelta);
    }

    @Override
    public void remove() {
        super.remove();

        synchronized (EgoWeaponsRenderSystem.getDistortionParticles()) {
            EgoWeaponsRenderSystem.getDistortionParticles().remove(this);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        private Vector3f offset;
        private Vector3f offsetRate;
        private int lifetime;
        private float quadSize;

        private boolean glows;
        float xRotAdd = 0;
        float yRotAdd = 0;
        float zRotAdd = 0;


        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
            this.quadSize = 2.5f;
            this.lifetime = 5;
            this.offset = new Vector3f(0.3f,1f,0);
            this.offsetRate = new Vector3f(0.1f,0,0);
            this.glows = false;
        }

        public Provider(IAnimatedSprite spriteSet, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows, float xRotAdd, float yRotAdd, float zRotAdd) {
            this.spriteSet = spriteSet;
            this.quadSize = quadsize;
            this.lifetime = lifetime;
            this.offset = offset;
            this.offsetRate = offsetRate;
            this.glows = glows;
            this.xRotAdd = xRotAdd;
            this.zRotAdd = zRotAdd;
            this.yRotAdd = yRotAdd;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double sourceID, double zSpeed) {
            RendSpaceEffect particle = new RendSpaceEffect(worldIn, x, y, z, xSpeed, sourceID, zSpeed, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glows, this.xRotAdd, this.yRotAdd, this.zRotAdd);
            return particle;
        }
    }
}
