package net.m3tte.ego_weapons.specialParticles.numberParticle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
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

public final class ClashLabelParticle extends TexturedParticle {

    int number = 0;
    DamageTypes damageType;
    boolean cracked;

    float speedUp = 0.05f;

    float size = 0.001F;
    float targetSize = 0.020F;
    float newSize = 0;
    float oSize = 0;

    String damageIdentifier = "";





    public ClashLabelParticle(ClientWorld world, double x, double y, double z, float number, int type, boolean cracked) {
        super(world, x, y, z, 0, 0, 0);
        //velocityMultiplier = 0.99F;
        //gravityStrength = 0.75F;


        this.damageType = DamageTypes.values()[Math.min(DamageTypes.values().length-1, type)];
        this.cracked = cracked;
        this.number = Math.round(number * 2.5f);
        this.lifetime = 30;
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

        if (this.age < 5) {
            this.newSize = (targetSize / 5) * this.age;
        } else if (this.age >= this.lifetime-10) {
            this.newSize = (this.lifetime - this.age) * (targetSize / 10);
        } else {
            this.newSize = targetSize;
        }

        this.size = newSize;
        this.oSize = newSize;
    }

    public String fetchIconLabel(DamageTypes type, boolean cracked) {

        switch (type) {
            case RED:
                return cracked ? "\uECC1" : "\uECD1";
            case WHITE:
                return cracked ? "\uECC2" : "\uECD2";
            case BLACK:
                return cracked ? "\uECC3" : "\uECD3";
            case PALE:
                return cracked ? "\uECC4" : "\uECD4";
        }

        return "";
    }


    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo activeRenderInfo, float tickDelta) {
        Vector3d cameraPos = activeRenderInfo.getPosition();

        float particleX = (float) (this.xo + (x - this.xo) * (double) tickDelta - cameraPos.x);
        float particleY = (float) (this.yo + (y - this.yo) * (double) tickDelta - cameraPos.y);
        float particleZ = (float) (this.zo + (z - this.zo) * (double) tickDelta - cameraPos.z);

        this.size = (this.oSize + (this.newSize - this.oSize) * tickDelta);

        FontRenderer textRenderer = Minecraft.getInstance().font;

        float value = (((float) ((int) (number * 10))) / 10);
        String text;
        if (((int)value) == value) {
            text = ""+((int)value);
        } else {
            text = ""+(value);
        }





        MatrixStack matrixStack = new MatrixStack();
        float textX = (textRenderer.width(text) / 3F) ;
        matrixStack.pushPose(); // 1

        matrixStack.translate(particleX, particleY, particleZ);
        matrixStack.scale(-size, -size, size);
        matrixStack.translate(-10 * size,0, 0f);

        Quaternion angledRot = Vector3f.ZP.rotationDegrees(-20);
        Quaternion secondRot = Vector3f.YP.rotationDegrees(activeRenderInfo.getYRot());
        Quaternion thirdRot = Vector3f.XP.rotationDegrees(-activeRenderInfo.getXRot());
        secondRot.mul(thirdRot);

        matrixStack.mulPose(secondRot);
        matrixStack.translate(1f, 1f, 1f);
        matrixStack.pushPose(); // 2


        matrixStack.pushPose();
        matrixStack.scale(2.5f,2.5f,2.5f);
        matrixStack.translate(0,-2, 0.08f);
        textRenderer.draw(matrixStack, fetchIconLabel(this.damageType, this.cracked), 0, 0, 0);
        matrixStack.popPose();
        textRenderer.draw(matrixStack, text, -textX + 10f, 0, 0);

        matrixStack.popPose(); // 1


        matrixStack.pushPose(); // 2
        matrixStack.scale(2.5f,2.5f,2.5f);
        matrixStack.translate(0,-2, 0.05f);
        textRenderer.draw(matrixStack, fetchIconLabel(this.damageType, this.cracked), 0, 0, 16773613);

        matrixStack.popPose(); // 1
        textRenderer.draw(matrixStack, text, -textX + 10f, 0, 16773613);

        matrixStack.popPose(); // 0
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
            System.out.println("CREATING PARTICLE WITH DATA : "+xSpeed+" "+ySpeed+" "+zSpeed);
            ClashLabelParticle particle = new ClashLabelParticle(worldIn, x, y, z, (float) xSpeed, (int)ySpeed, zSpeed > 0);
            return particle;
        }


    }
}
