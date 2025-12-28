package net.m3tte.ego_weapons.gui.ingame;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;
import yesman.epicfight.client.gui.EntityIndicator;

import javax.xml.soap.Text;
import java.util.*;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.gui.ingame.ThreatLevelGUI.OVERLAY_TEXTURES;

@OnlyIn(Dist.CLIENT)
public class DialogueGUI extends EntityIndicator {

    public static final ResourceLocation OVERLAY_TEXTURES = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/overlays/dialogue_box_overlay.png");

    @Override
    public boolean shouldDraw(ClientPlayerEntity player, LivingEntity entityIn) {

        if (!entityIn.canChangeDimensions() || entityIn == player.getVehicle()) {
            return false;
        } else return !(entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) >= 64);
    }

    @Override
    public void drawIndicator(LivingEntity entityIn, MatrixStack matStackIn, IRenderTypeBuffer bufferIn, float partialTicks) {

        IVertexBuilder vertexBuilder = bufferIn.getBuffer(EgoWeaponsRenderTypes.overlayTextures(OVERLAY_TEXTURES));

        matStackIn.pushPose();
        matStackIn.scale(0.95f, 0.95f, 0.95f);
        matStackIn.translate(0,0.3,0);
        LinkedList<DialogueEntry> toRenderDialogues = new LinkedList<>();

        //Fetch Render System

        GL11.glPushMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.defaultBlendFunc();
        int tickCount = entityIn.tickCount;


        if (entityIn.getPersistentData().contains("dialogueMetaData")) {
            CompoundNBT data  = entityIn.getPersistentData().getCompound("dialogueMetaData");


            List<Integer> metaDatas = data.getAllKeys().stream().map(Integer::parseInt).sorted((a, b) -> b - a).collect(Collectors.toList());

            for (int dialogueTimestamps : metaDatas) {


                String[] array = data.getString(dialogueTimestamps+"").split("\\|\\|");

                String formatting = array[0];
                array[0] = "";

                int largestLength = 0;

                FontRenderer rend = Minecraft.getInstance().font;

                for (String string : array) {
                    largestLength = Math.max(rend.width(string), largestLength);
                }

                float fillSize = (float) largestLength / 95;
                toRenderDialogues.add(new DialogueEntry(dialogueTimestamps, array, formatting, fillSize));
            }
        }

        float upOffset = 0.15f;

        for (DialogueEntry entry : toRenderDialogues) {
            float sizeMult = (1 + (float) entry.getLines().length / 4) / 2;

            Matrix4f mvMatrix = super.getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.8F + upOffset, 0.0F, true, partialTicks);

            float alphaRed = 0;

            if (tickCount - entry.getTimeStamp() > 20 + 80 * entry.getSize()) {
                alphaRed = Math.min(1,(float) (tickCount - entry.getTimeStamp() - (20 + 80 * entry.getSize())) / 40);
            }





            if (alphaRed < 0.95f) {
                GL11.glPushMatrix();

                float evalSize = Math.max(entry.getSize() - 1.3f, 0);
                //GL11.glColor4f(1,1,1,1 - alphaRed);
                this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, -0.4f - 0.5f*sizeMult - evalSize, -0.4f * sizeMult, -0.03f,0.4f + 0.5f*sizeMult + evalSize, 0.4f * sizeMult, -0.03f,0, 0, 256, 100);
                GL11.glPopMatrix();
            }



            upOffset += 0.65f * sizeMult;
        }

        upOffset = 0f;

        for (DialogueEntry entry : toRenderDialogues) {
            float secondUpOffs = entry.getLines().length * 0.05f;


            float alphaRed = 0;

            if (tickCount - entry.getTimeStamp() > 20 + 70 * entry.getSize() ) {
                alphaRed = Math.min(1,(float) (tickCount - entry.getTimeStamp() - (20 + 70 * entry.getSize())) / 40);
            }

            if (alphaRed < 0.92f) {
                for (String line : entry.getLines()) {
                    renderNameTag(entityIn, new StringTextComponent(line).withStyle(TextFormatting.ITALIC).withStyle(entry.getFormatting()), matStackIn, bufferIn, (int) (230 + 150 * alphaRed), 16777215, upOffset ,  + secondUpOffs, 1);
                    secondUpOffs -= 0.1f;
                }
            }
            System.out.println("ENTRY LINES IS : "+entry.getLines().length);
            upOffset += 0.09f * (entry.getLines().length - 2) + 0.48f;
        }
        // this.drawTexturedModalRect2DPlane(mvMatrix, vertexBuilder, -1.2F, -0.4F, 1.2f, 0.3F, 0, 0, 256, 100);


        GL11.glPopMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.defaultBlendFunc();

        matStackIn.popPose();
    }


    protected void renderNameTag(LivingEntity ent, ITextComponent p_225629_2_, MatrixStack mtStack, IRenderTypeBuffer p_225629_4_, int noIdeaWhatThisParameterDoes, int color, float upOffs, float suboffset, float fillSize) {

        EntityRendererManager entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        boolean flag = false;
        float f = ent.getBbHeight() + 0.95F + upOffs;
        mtStack.pushPose();
        mtStack.translate(0.0D, (double)f, 0.0D);
        mtStack.mulPose(entityRenderDispatcher.cameraOrientation());
        mtStack.scale(-0.01F / fillSize, -0.01F / fillSize, 0.01F / fillSize);

        mtStack.pushPose();
        mtStack.translate(0.0D, (double)-0.05f, 0.0);

        Matrix4f matrix4f = mtStack.last().pose();
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        FontRenderer fontrenderer = Minecraft.getInstance().font;
        float f2 = (float)(-fontrenderer.width(p_225629_2_) / 2);
        fontrenderer.drawInBatch(p_225629_2_, f2, (float)suboffset * -100 * fillSize, color, false, matrix4f, p_225629_4_, false, 0, noIdeaWhatThisParameterDoes);

        mtStack.popPose();
        mtStack.popPose();
    }


    public void renderNumber(int number, Matrix4f mvMatrix, IVertexBuilder vertexBuilder, float x, float y, float offsetMult, boolean renderZero) {
        int index = 0;

        int len = (int)Math.ceil(Math.log10(number + 1));
        x += len * offsetMult;

        while (number > 0 || renderZero) {
            renderDigit(number % 10, mvMatrix, vertexBuilder, x - 0.07f*index, y);

            number = number / 10;
            index++;
            renderZero = false;
        }
    }

    private void renderDigit(int digit, Matrix4f mvMatrix, IVertexBuilder vertexBuilder, float x, float y) {
        this.drawTexturedModalRect3DPlane(mvMatrix, vertexBuilder, x, y,0.05f, x+0.06f, y+0.1f, 0.05f,5*digit, 248, 5+5*digit, 256);
    }
}


