package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsShaders;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL13;

public class ShaderParticleTest extends SpriteTexturedParticle {
    protected final IAnimatedSprite spriteProvider;
    protected Vector3f offset = new Vector3f(0,0,0);
    protected Vector3f offsetRate = new Vector3f(0,0,0);

    protected Vector3f rotation = new Vector3f(0,0,0);
    protected Vector3f rotationOffs = new Vector3f(0,180,0);

    protected double ox;
    protected double oy;
    protected double oz;

    boolean flipX = true;
    boolean flipY = true;

    boolean invertX = false;
    boolean invertY = false;

    boolean glowRenderType = false;

    boolean processStartPos = true;


    protected Vector3f[] generateVectorArray() {

        return new Vector3f[]{new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F)};
    }



    public ShaderParticleTest(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite spriteProvider, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows) {
        super(world, x, y, z);

        this.spriteProvider = spriteProvider;
        this.glowRenderType = glows;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.flipX = true;
        this.quadSize = quadsize;
        this.lifetime = lifetime;
        this.offset = offset.copy();
        this.offsetRate = offsetRate;

        // resolveBoundEntity((int) entityUUID, world);



    }


    @Override
    public void tick() {
        //this.xo = this.x;
        //this.yo = this.y;
        //this.zo = this.z;



        //offset.add(offsetRate);

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void remove() {
        super.remove();

    }



    @Override
    public boolean shouldCull() {
        return false;
    }


    IParticleRenderType SHADERED_PARTICLE = new IParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {


            if (EgoWeaponsShaders.setupCompleted) {
                EgoWeaponsShaders.STAR_SHADER.apply();

                // Bind star texture to TEXTURE1
                RenderSystem.activeTexture(GL13.GL_TEXTURE1);
                textureManager.bind(EgoWeaponsShaders.PORTAL_EFFECT);
                RenderSystem.activeTexture(GL13.GL_TEXTURE0);

            }


            textureManager.bind(AtlasTexture.LOCATION_PARTICLES);

            bufferBuilder.begin(7, DefaultVertexFormats.PARTICLE);
        }

        public void end(Tessellator p_217599_1_) {
            p_217599_1_.end();
            if (!EgoWeaponsShaders.setupCompleted)
                return;
            EgoWeaponsShaders.STAR_SHADER.clear();
        }

        public String toString() {
            return "SHADERED_PARTICLE";
        }
    };

    @Override
    public @NotNull IParticleRenderType getRenderType() {
        return SHADERED_PARTICLE;
    }

    @Override
    public void render(IVertexBuilder vertexBuilder,
                       ActiveRenderInfo renderInfo,
                       float tickDelta) {
        this.setSpriteFromAge(spriteProvider);

        super.render(vertexBuilder, renderInfo, tickDelta);
    }




    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        private Vector3f offset;
        private Vector3f offsetRate;
        private int lifetime;
        private float quadSize;
        private boolean glows;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
            this.quadSize = 2.5f;
            this.lifetime = 5;
            this.offset = new Vector3f(0.3f,1f,0);
            this.offsetRate = new Vector3f(0.1f,0,0);
            this.glows = false;
        }

        public Provider(IAnimatedSprite spriteSet, float quadsize, int lifetime, Vector3f offset, Vector3f offsetRate, boolean glows) {
            this.spriteSet = spriteSet;
            this.quadSize = quadsize;
            this.lifetime = lifetime;
            this.offset = offset;
            this.offsetRate = offsetRate;
            this.glows = glows;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double targetID, double sourceID) {
            ShaderParticleTest particle = new ShaderParticleTest(worldIn, x, y, z, 0, sourceID, 0, spriteSet, this.quadSize, this.lifetime, this.offset, this.offsetRate, this.glows);
            return particle;
        }


    }
}
