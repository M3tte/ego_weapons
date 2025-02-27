package net.m3tte.ego_weapons.specialParticles.numberParticle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public final class NumberParticle extends TexturedParticle {

    double number = 0;
    NumberParticleTypes type;

    float speedUp = 0.15f;

    float size = 0.001F;
    float targetSize = 0.025F;

    public NumberParticle(ClientWorld world, double x, double y, double z, double velX, double number, double type) {
        super(world, x, y, z, velX, number, type);
        //velocityMultiplier = 0.99F;
        //gravityStrength = 0.75F;
        this.type = NumberParticleTypes.values()[(int) type];
        this.number = number;
        this.lifetime = 50;
    }

    public void setColor(float red, float green, float blue, float alpha) {
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        this.alpha = alpha;
    }


    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.y += speedUp;
            this.speedUp *= 0.9f;
        }

        if (this.age < 10) {
            this.size = (targetSize / 10) * this.age;
        } else if (this.age >= this.lifetime-20) {
            this.size = (this.lifetime - this.age) * (targetSize / 20);
        } else {
            this.size = targetSize;
        }
    }

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo activeRenderInfo, float tickDelta) {
        Vector3d cameraPos = activeRenderInfo.getPosition();

        float particleX = (float) (this.xo + (x - this.xo) * (double) tickDelta - cameraPos.x);
        float particleY = (float) (this.yo + (y - this.yo) * (double) tickDelta - cameraPos.y);
        float particleZ = (float) (this.zo + (z - this.zo) * (double) tickDelta - cameraPos.z);


        FontRenderer textRenderer = Minecraft.getInstance().font;

        String text = ((int)number)+" ";

        MatrixStack matrixStack = new MatrixStack();
        float textX = textRenderer.width(text) / -2.0F;
        matrixStack.pushPose();
        matrixStack.translate(particleX, particleY, particleZ);
        matrixStack.scale(-size, -size, size);
        Quaternion secondRot = Vector3f.YP.rotationDegrees(activeRenderInfo.getYRot());
        Quaternion thirdRot = Vector3f.XP.rotationDegrees(-activeRenderInfo.getXRot());
        secondRot.mul(thirdRot);
        matrixStack.mulPose(secondRot);

        matrixStack.pushPose();
        matrixStack.translate(0.4f, 0.4f, 0.4f);
        textRenderer.draw(matrixStack, type.icon, -textX + 2, 0, 0);
        textRenderer.draw(matrixStack, text, textX, 0, 0);
        matrixStack.popPose();
        textRenderer.draw(matrixStack, type.icon, -textX + 2, 0, 16777215);
        textRenderer.draw(matrixStack, text, textX, 0, type.color);

        matrixStack.popPose();
    }

    @Override
    protected float getU0() {
        return 0;
    }

    @Override
    protected float getU1() {
        return 0;
    }

    @Override
    protected float getV0() {
        return 0;
    }

    @Override
    protected float getV1() {
        return 0;
    }

    @Override
    public @NotNull IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Provider(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            NumberParticle particle = new NumberParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return particle;
        }


    }
}
