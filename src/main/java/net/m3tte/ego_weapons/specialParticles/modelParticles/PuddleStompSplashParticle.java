package net.m3tte.ego_weapons.specialParticles.modelParticles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.models.particles.AbstractParticleModel;
import net.m3tte.ego_weapons.client.models.particles.PuddleStompOuterSplashModel;
import net.m3tte.ego_weapons.client.models.particles.PuddleStompSplashModel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PuddleStompSplashParticle extends SpriteTexturedParticle {

    public static AbstractParticleModel DEFAULT_WAVE_MODEL = new PuddleStompSplashModel();
    public static AbstractParticleModel DEFAULT_RIM_MODEL = new PuddleStompOuterSplashModel();

    private AbstractParticleModel WAVE_MODEL = new PuddleStompSplashModel();
    private AbstractParticleModel WAVE_RIM_MODEL = new PuddleStompOuterSplashModel();
    private final IAnimatedSprite sprites;
    private float widthMultiplier;
    private final float heightMultiplier;
    private final int wave1End;
    private final int wave2Start;
    private final int wave2End;
    private final float r;
    private final float g;
    private final float b;

    public PuddleStompSplashParticle(ClientWorld level, double x, double y, double z, IAnimatedSprite spriteSet, AbstractParticleModel waveModel, AbstractParticleModel rimModel, float widthMult, float heightMult, int wave1End, int wave2Start, int wave2End, float r, float g, float b) {
        super(level, x, y, z);
        this.sprites = spriteSet;
        this.gravity = 0.0F;
        this.widthMultiplier = widthMult;
        this.heightMultiplier = heightMult;
        this.r = (float) r;
        this.g = (float) g;
        this.b = (float) b;

        alpha = 0.5f;

        this.wave1End = wave1End;// 10;
        this.wave2Start = wave2Start;// 2;
        this.wave2End = wave2End;// 12;

        this.WAVE_MODEL = waveModel;
        this.WAVE_RIM_MODEL = rimModel;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        this.widthMultiplier *= 1.03f;
        float widthMultiplier = this.widthMultiplier;

        if (this.age++ >= this.wave2End) {
            this.remove();
        }

        ClientWorld level = this.level;


        /*if (this.age == 1) {
            for (int i = 0; i < widthMultiplier * 10f; i++) {
                EffectiveFgParticles.DROPLET.create(level,
                        this.x + (this.random.nextGaussian() * widthMultiplier / 10f),
                        this.y,
                        this.z + (this.random.nextGaussian() * widthMultiplier / 10f),
                        random.nextGaussian() / 10f * widthMultiplier / 2.5f,
                        random.nextFloat() / 10f + this.heightMultiplier / 2.8f,
                        random.nextGaussian() / 10f * widthMultiplier / 2.5f);
            }
        } else if (this.age == wave2Start) {
            for (int i = 0; i < widthMultiplier * 5f; i++) {
                EffectiveFgParticles.DROPLET.create(level,
                        this.x + (this.random.nextGaussian() * widthMultiplier / 10f * .5f),
                        this.y,
                        this.z + (this.random.nextGaussian() * widthMultiplier / 10f * .5f),
                        random.nextGaussian() / 10f * widthMultiplier / 5f,
                        random.nextFloat() / 10f + this.heightMultiplier / 2.2f,
                        random.nextGaussian() / 10f * widthMultiplier / 5f);
            }
        }*/
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
        final float yOffset = 0.001F;
        final float bottomYOffset = -0.1F;
        int light = getLightColor(tickDelta);

        Vector3d vec3d = renderInfo.getPosition();
        float viewX = (float) (MathHelper.lerp(tickDelta, this.xo, this.x) - vec3d.x());
        float viewY = (float) (MathHelper.lerp(tickDelta, this.yo, this.y) - vec3d.y()) + yOffset;
        float viewZ = (float) (MathHelper.lerp(tickDelta, this.zo, this.z) - vec3d.z());

        MatrixStack matrixStack = new MatrixStack();
        float widthMultiplier = this.widthMultiplier;
        float heightMultiplier = this.heightMultiplier;

        if (age <= this.wave1End) {
            int frameForFirstSplash = Math.round(((float) this.age / (float) this.wave1End) * 12);
            setSprite(frameForFirstSplash);

            float minU = this.getU0();
            float maxU = this.getU1();
            float minV = this.getV0();
            float maxV = this.getV1();
            matrixStack.pushPose();
            matrixStack.translate(viewX, viewY + 1.35, viewZ);
            matrixStack.scale(widthMultiplier, -heightMultiplier, widthMultiplier);
            WAVE_RIM_MODEL.renderToBuffer(matrixStack, vertexBuilder, light, minU, maxU, minV, maxV, r, g, b, 1);
            //WAVE_RIM_MODEL.renderToBuffer(matrixStack, vertexBuilder, light, minU, maxU, minV, maxV, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.pushPose();
                matrixStack.translate(0,-0.9f,0);
                matrixStack.scale(1 + (widthMultiplier/2), 1.6f, 1 + (widthMultiplier/2));
                WAVE_RIM_MODEL.renderToBuffer(matrixStack, vertexBuilder, light, minU, maxU, minV, maxV, r, g, b, 1);
                matrixStack.popPose();
            matrixStack.popPose();
        }

        if (age >= this.wave2Start) {
            int frameForSecondSplash = Math.round(((float) (this.age - wave2Start) / (float) (this.wave2End - this.wave2Start)) * 12);
            setSprite(frameForSecondSplash);

            float minU = this.getU0();
            float maxU = this.getU1();
            float minV = this.getV0();
            float maxV = this.getV1();

            matrixStack.pushPose();
            matrixStack.translate(viewX, viewY, viewZ);

            matrixStack.pushPose();
            matrixStack.translate(0,1.3f,0);
            matrixStack.scale(widthMultiplier * 0.6f, -heightMultiplier, widthMultiplier * 0.6f);
            WAVE_MODEL.renderToBuffer(matrixStack, vertexBuilder, light, minU, maxU, minV, maxV, r, g, b, 1);
                matrixStack.pushPose();
                matrixStack.translate(0,-0.8f,0);
                matrixStack.scale(0.7f, 1.5f, 0.97f);
                WAVE_MODEL.renderToBuffer(matrixStack, vertexBuilder, light, minU, maxU, minV, maxV, r, g, b, 1);
                matrixStack.popPose();
            matrixStack.popPose();

        }
    }

    public void setSprite(int index) {
        if (!this.removed) {
            this.setSprite(sprites.get(index, 12));
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        private final AbstractParticleModel waveModel;
        private final AbstractParticleModel waveRimModel;
        private final float waveWidth;
        private final float waveHeight;
        private final int wave1End;
        private final int wave2Start;
        private final int wave2End;

        private final float r;
        private final float g;
        private final float b;


        public Provider(IAnimatedSprite spriteSet, AbstractParticleModel waveModel, AbstractParticleModel waveRimModel, float waveWidth, float waveHeight, int wave1End, int wave2Start, int wave2End, float r, float g, float b) {
            this.spriteSet = spriteSet;
            this.waveModel = waveModel;
            this.waveRimModel = waveRimModel;
            this.waveWidth = waveWidth;
            this.waveHeight = waveHeight;
            this.wave1End = wave1End;
            this.wave2Start = wave2Start;
            this.wave2End = wave2End;
            this.r = r;
            this.g = g;
            this.b = b;

        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PuddleStompSplashParticle particle = new PuddleStompSplashParticle(worldIn, x, y, z, spriteSet, waveModel, waveRimModel, waveWidth, waveHeight, wave1End, wave2Start, wave2End, this.r, this.g, this.b);
            return particle;
        }


    }



}
