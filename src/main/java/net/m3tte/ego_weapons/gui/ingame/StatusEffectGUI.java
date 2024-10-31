package net.m3tte.ego_weapons.gui.ingame;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.renderer.TCorpRenderTypes;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.EntityIndicator;

import java.util.Collection;
import java.util.Iterator;

import static net.m3tte.ego_weapons.gui.ingame.ThreatLevelGUI.OVERLAY_TEXTURES;

@OnlyIn(Dist.CLIENT)
public class StatusEffectGUI extends EntityIndicator {

    @Override
    public boolean shouldDraw(ClientPlayerEntity player, LivingEntity entityIn) {

        if (!entityIn.canChangeDimensions() || entityIn.isInvisible() || entityIn == player.getVehicle()) {
            return false;
        } else if (entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) >= 64) {
            return false;
        }
        return true;
    }

    @Override
    public void drawIndicator(LivingEntity entityIn, MatrixStack matStackIn, IRenderTypeBuffer bufferIn, float partialTicks) {
        Matrix4f mvMatrix = super.getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, true, partialTicks);
        Collection<EffectInstance> activeEffects = entityIn.getActiveEffects();
        int column;
        float startX;
        float startY;



        if (!activeEffects.isEmpty()) {
            Iterator<EffectInstance> iter = activeEffects.iterator();
            int acives = activeEffects.size();
            column = (acives - 1);
            startX = -0.6F - entityIn.getBbWidth() / 2;
            startY = -0.2F + 0.15F * (float)column;

            for(int j = 0; j <= acives; ++j) {
                if (!iter.hasNext())
                    break;

                EffectInstance effectInst = iter.next();
                Effect effect = effectInst.getEffect();

                if (effect instanceof CountPotencyStatus) { //
                    ResourceLocation rl;
                    rl = ((CountPotencyStatus)effect).getIcon();

                    Minecraft.getInstance().getTextureManager().bind(rl);
                    float y = startY + -0.3F * (float)j;
                    IVertexBuilder vertexBuilder1 = bufferIn.getBuffer(TCorpRenderTypes.overlayTextures(rl));

                    this.drawTexturedModalRect2DPlane(mvMatrix, vertexBuilder1, startX, y, startX + 0.3F, y + 0.3F, 0.0F, 0.0F, 256.0F, 256.0F);
                    IVertexBuilder numberVertex = bufferIn.getBuffer(TCorpRenderTypes.overlayTextures(OVERLAY_TEXTURES));
                    int count = ((CountPotencyStatus) effect).getCount(effectInst);
                    int potency = ((CountPotencyStatus) effect).getPotency(effectInst);

                    if (potency > 0)
                        renderNumber(potency, mvMatrix, numberVertex, startX-0.01f, y, 0.045f);

                    if (count > 0)
                        renderNumber(count, mvMatrix, numberVertex, startX+0.25f, y,0.01f);






                    if (!iter.hasNext()) {
                        break;
                    }
                }
            }


        }
    }

    public void renderNumber(int number, Matrix4f mvMatrix, IVertexBuilder vertexBuilder, float x, float y, float offsetMult) {
        int index = 0;

        int len = (int)Math.ceil(Math.log10(number + 1));
        x += len * offsetMult;

        while (number > 0) {
            renderDigit(number % 10, mvMatrix, vertexBuilder, x - 0.07f*index, y);

            number = number / 10;
            index++;
        }
    }

    private void renderDigit(int digit, Matrix4f mvMatrix, IVertexBuilder vertexBuilder, float x, float y) {
        this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, x, y,0.05f, x+0.06f, y+0.1f, 0.05f,5*digit, 248, 5+5*digit, 256);
    }
}


