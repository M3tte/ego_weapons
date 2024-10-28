//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.tcorp.gui.ingame;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Collection;

import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.client.renderer.TCorpRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.EntityIndicator;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PlayerStatsIndicator extends EntityIndicator {
    public PlayerStatsIndicator() {
    }

    public boolean shouldDraw(ClientPlayerEntity player, LivingEntity entityIn) {
        if (!(Boolean)EpicFightMod.CLIENT_INGAME_CONFIG.showHealthIndicator.getValue()) {
            return false;
        } else if (entityIn.canChangeDimensions() && !entityIn.isInvisible() && entityIn != player.getVehicle()) {
            if (entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) >= 400.0) {
                return false;
            } else {
                if (entityIn instanceof PlayerEntity) {
                    PlayerEntity playerIn = (PlayerEntity)entityIn;
                    if (playerIn == player) {
                        return false;
                    }

                    if (playerIn.isCreative() || playerIn.isSpectator()) {
                        return false;
                    }
                } else {
                    return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private TcorpModVariables.PlayerVariables createNew() {
        //System.out.println("ERRORED, NO CAPABILITY FOUND, MAKING NEW ONE WITH BASE STATS");
        return new TcorpModVariables.PlayerVariables();
    }

    public void drawIndicator(LivingEntity entityIn, MatrixStack matStackIn, IRenderTypeBuffer bufferIn, float partialTicks) {
        Matrix4f mvMatrix = super.getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, true, partialTicks);
        IVertexBuilder vertexBuilder = bufferIn.getBuffer(TCorpRenderTypes.overlayTextures(ThreatLevelGUI.OVERLAY_TEXTURES));
        this.drawTexturedModalRect2DPlane(mvMatrix, vertexBuilder, -0.5F, 0F, 0.5f, 0.6F, 95, 0, 157, 36);

        float healthPercent = entityIn.getHealth() / entityIn.getMaxHealth();
        float sanityPercent = 0;
        float staggerPercent = 0;
        int emotionTier = 0;
        if (entityIn instanceof PlayerEntity) {
            TcorpModVariables.PlayerVariables playerVariables = entityIn.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(createNew());
            sanityPercent = (float) (playerVariables.sanity / playerVariables.maxSanity);
            staggerPercent = (float) (playerVariables.stagger / playerVariables.maxStagger);
            emotionTier = playerVariables.emotionLevel;
        }

        if (healthPercent > 0) {
            this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, -0.5f, 0.045F, 0.01f,0.5f, 0.045f + 0.51f * healthPercent , 0.01f,95, 72 + 31 * (1-healthPercent), 157, 103);
        }

        if (sanityPercent > 0) {
            this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, -0.5f, 0.045F, 0.01f,0.5f, 0.045f + 0.51f * sanityPercent, 0.01f,95, 37 + 31 * (1-sanityPercent), 157, 68);
        }

        if (staggerPercent > 0) {
            this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, -0.5f, 0.045F, 0.01f,0.5f, 0.045f + 0.51f * staggerPercent, 0.01f,95F, 107 + 31 * (1-staggerPercent), 157, 138);
        }

        float xMod = -0.34f;
        this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, xMod, 0.01f, 0.01f,xMod + 0.57f, 0.59f, 0.01f,221, 35 * emotionTier, 256, 35 + 35*emotionTier);


    }

}
