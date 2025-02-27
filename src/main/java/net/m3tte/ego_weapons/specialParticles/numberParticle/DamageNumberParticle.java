package net.m3tte.ego_weapons.specialParticles.numberParticle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public final class DamageNumberParticle extends TexturedParticle {

    double number = 0;
    DamageTypes damageType;
    AttackTypes attackType;

    boolean crit;
    float multiplier = 1;
    int bonusMultiplier = 0;

    float speedUp = 0.15f;

    float size = 0.001F;
    float targetSize = 0.020F;

    String damageIdentifier = "";

    public DamageNumberParticle(ClientWorld world, double x, double y, double z, float number, int type, float multiplier) {
        super(world, x, y, z, multiplier, number, type);
        //velocityMultiplier = 0.99F;
        //gravityStrength = 0.75F;

        type -= 1;

        int damageTypeIdx = type%10;
        int attackTypeIdx = (type / 10) % 10;

        int zeroMult = type < 0 ? -1 : 1;

        type *= zeroMult;

        int bonusCorrector = multiplier < 0 ? -1 : 1;

        multiplier *= bonusCorrector;

        this.damageType = DamageTypes.values()[Math.min(DamageTypes.values().length-1, damageTypeIdx)];
        this.attackType = AttackTypes.values()[Math.min(AttackTypes.values().length-1, attackTypeIdx)];
        this.crit = type >= 100;
        this.multiplier = (multiplier % 1000) * zeroMult;
        this.bonusMultiplier = (int)((multiplier / 10000) * bonusCorrector) - 100;
        this.number = number;
        this.lifetime = (int) (30 + Math.min(40, this.number));
        this.damageIdentifier = getDamageIdentifier((multiplier % 1000) * zeroMult);

    }

    private String getDamageIdentifier(float mult) {
        System.out.println("MULT IS : "+mult);
        if (mult < 0) {
            return "Absorb.";
        }

        if (mult <= 0.05f) {
            return "Immune";
        }

        if (mult < 0.3f) {
            return "Ineff.";
        }

        if (mult < 0.7f) {
            return "Endured";
        }

        if (mult > 2f) {
            return "Fatal";
        }

        if (mult > 1.2f) {
            return "Weak";
        }

        return "";

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

        float value = (((float) ((int) (number * 10))) / 10);
        String text;
        if (((int)value) == value) {
            text = " "+((int)value);
        } else {
            text = " "+(value);
        }





        MatrixStack matrixStack = new MatrixStack();
        float textX = (textRenderer.width(text) / -2.5F) ;
        float identifierX = textRenderer.width(this.damageIdentifier) / 4.0F;
        matrixStack.pushPose();

        matrixStack.translate(particleX, particleY, particleZ);
        matrixStack.scale(-size, -size, size);

        Quaternion angledRot = Vector3f.ZP.rotationDegrees(-20);
        Quaternion secondRot = Vector3f.YP.rotationDegrees(activeRenderInfo.getYRot());
        Quaternion thirdRot = Vector3f.XP.rotationDegrees(-activeRenderInfo.getXRot());
        secondRot.mul(thirdRot);

        matrixStack.mulPose(secondRot);
        int multiplier = (int)((this.multiplier - 1) * 100) + bonusMultiplier;

        int textColor = crit ? 16746263 : 16773613;
        matrixStack.translate(0.4f, 0.4f, 0.4f);
        matrixStack.pushPose();


        matrixStack.pushPose();
        matrixStack.scale(1.4f,1.4f,1.4f);
        textRenderer.draw(matrixStack, this.damageType.getDamageBorder(), -4, 0, 0);
        matrixStack.pushPose();
        matrixStack.scale(0.9f,0.9f,0.9f);
        matrixStack.translate(0f, 0f, 0.05f);
        textRenderer.draw(matrixStack, this.attackType.getIcon(), -4, 0, 0);
        matrixStack.popPose();

        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.scale(0.7f,0.7f,0.7f);
        matrixStack.translate(-0.5f, 0f, -0.08f);
        matrixStack.mulPose(angledRot);
        if (multiplier != 0)
            textRenderer.draw(matrixStack, (multiplier > 0 ? "+" : "") + multiplier + "%", textX - 2, -8, 0);
        matrixStack.popPose();
        textRenderer.draw(matrixStack, text, -textX + 6, 0, 0);

        if (crit) {
            textRenderer.draw(matrixStack, "CRITICAL!", -textX-20, 18, 0);
        }


        matrixStack.popPose();


        matrixStack.pushPose();
        matrixStack.scale(1.4f,1.4f,1.4f);
        textRenderer.draw(matrixStack, this.damageType.getDamageBorder(), -4, 0, 16773613);
        matrixStack.pushPose();
        matrixStack.scale(0.9f,0.9f,0.9f);
        matrixStack.translate(0f, 0f, -0.05f);
        textRenderer.draw(matrixStack, this.attackType.getIcon(), -4, 0, 16773613);
        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.translate(-identifierX + 0.5, 6, 0);
        matrixStack.scale(0.4f,0.4f,0.4f);
        matrixStack.translate(0f, 0f, -0.05f);
        textRenderer.draw(matrixStack, this.damageIdentifier, 0, 0, 16773613);
        matrixStack.popPose();

        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.scale(0.7f,0.7f,0.7f);
        matrixStack.translate(-0.5f, 0f, -0.08f);
        matrixStack.mulPose(angledRot);
        if (multiplier != 0)
            textRenderer.draw(matrixStack, (multiplier > 0 ? "+" : "") + multiplier + "%", textX - 2, -8, 16773613);
        matrixStack.popPose();

        textRenderer.draw(matrixStack, text, -textX + 6, 0, textColor);

        if (crit) {
            textRenderer.draw(matrixStack, "CRITICAL!", -textX-20, 18, textColor);
        }

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
            DamageNumberParticle particle = new DamageNumberParticle(worldIn, x, y, z, (float) xSpeed, (int)ySpeed, (float)zSpeed);
            return particle;
        }


    }
}
