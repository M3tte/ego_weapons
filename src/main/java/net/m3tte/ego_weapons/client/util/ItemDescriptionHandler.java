package net.m3tte.ego_weapons.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder;
import net.m3tte.ego_weapons.item.descriptionSystem.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.item.descriptionSystem.GenericEgoWeaponsArmor;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.ArmorMetadata;
import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

import static net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem.getDamageIdentifierCol;

@Mod.EventBusSubscriber({Dist.CLIENT})
@OnlyIn(Dist.CLIENT)
public class ItemDescriptionHandler {

    // ItemTooltipEvent

    //@SubscribeEvent
    public static void renderEvent(ItemTooltipEvent event) throws IOException {
        event.getToolTip().add(new StringTextComponent(": TEST DIALOGUE LINE :"));
    }

    static ResourceLocation ICON_BORDER = new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/element_icon_b.png");
    static ResourceLocation ICON_BORDER_GLOW = new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/element_glow_70x70.png");
    static ResourceLocation LIVING_BULLET = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/the_living_bullet.png");
    static ResourceLocation DEFENSE_LEVEL_ICON = new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/defense_level.png");
    static ResourceLocation PLAQUE_SILVER = new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/plaque_silver.png");

    @SubscribeEvent
    public static void renderTooltipPostBG(RenderTooltipEvent.PostBackground event) {

        Item item = event.getStack().getItem();

        int offsetLine = 0;
        boolean found = false;
        if (item instanceof EgoWeaponsWeapon || item instanceof GenericEgoWeaponsArmor) {


            float tickTime = 0;

            if (Minecraft.getInstance().level != null) {
                tickTime = Minecraft.getInstance().getFrameTime() + Minecraft.getInstance().level.getGameTime();
            }

            IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
            Matrix4f mat = event.getMatrixStack().last().pose();

            for (ITextProperties line : event.getLines()) {
                if (line instanceof AttributeTextPlaceholder) {
                    AttributeTextPlaceholder.PlaceholderType type = ((AttributeTextPlaceholder) line).getType();





                    switch (type) {
                        case ARMOR:
                            if (((AttributeTextPlaceholder) line).getMetadata() instanceof ArmorMetadata) {
                                renderDamageResistances(event, offsetLine, renderType, mat, tickTime, 0.8f, (ArmorMetadata) ((AttributeTextPlaceholder) line).getMetadata());
                            }

                            break;
                        case WEAPON:
                            renderWeaponStats(event, offsetLine);
                            break;
                        case OTHER:

                            break;
                    }
                }
                offsetLine++;
            }

            renderType.endBatch();
        }




        if (found) {

        }

        // Every line offsets y by 10
        //AbstractGui.blit(event.getMatrixStack(), event.getX(), event.getY(), 0, 0, 16, 16, 16, 16);
        //font.drawInBatch(LanguageMap.getInstance().getVisualOrder(line), (float)tooltipX, (float)tooltipY, -1, true, mat, renderType, false, 0, 15728880);
    }

    private  static void renderDamageResistances(RenderTooltipEvent.PostBackground event, int offsetLine, IRenderTypeBuffer.Impl renderType, Matrix4f mat, float gameTime, float scaleFactor, ArmorMetadata metadata) {
        event.getMatrixStack().pushPose();

        event.getMatrixStack().translate(0,0,405);
        event.getMatrixStack().scale(scaleFactor, scaleFactor, scaleFactor);

        // Invert the scale factor
        scaleFactor = 1 / scaleFactor;

        float scaledOffs = ((event.getX()) * (scaleFactor)) ;
        float scaledWidth = (event.getWidth() / 2f) * scaleFactor;
        float lineDist = 10 * scaleFactor;
        float yOffs = event.getY() * (scaleFactor);

        renderResistanceIcon(scaledOffs + scaledWidth - 80, (float) (yOffs + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f    ) * 2f)), GenericEgoDamage.DamageTypes.RED.getIconLoc(), 20, 20, metadata.getRedRes(), event, renderType, mat, gameTime);
        renderResistanceIcon(scaledOffs + scaledWidth - 40, (float) (yOffs + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 1) * 2f)), GenericEgoDamage.DamageTypes.WHITE.getIconLoc(), 20, 20, metadata.getWhiteRes(), event, renderType, mat, gameTime);
        renderResistanceIcon(scaledOffs + scaledWidth,      (float) (yOffs + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 2) * 2f)), GenericEgoDamage.DamageTypes.BLACK.getIconLoc(), 20, 20, metadata.getBlackRes(), event, renderType, mat, gameTime);
        renderResistanceIcon(scaledOffs + scaledWidth + 40, (float) (yOffs + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 3) * 2f)), GenericEgoDamage.DamageTypes.PALE.getIconLoc(), 20, 20, metadata.getPaleRes(), event, renderType, mat, gameTime);
        //renderResistanceIcon(event.getWidth() / 2 - -36, 10, GenericEgoDamage.DamageTypes.RED.getIconLoc(), 20, 20, 0.6f, event, renderType, mat);

        renderValueIcon     (scaledOffs + scaledWidth - 80, (float) (yOffs + 60 + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 0.5f) * 2f)), DEFENSE_LEVEL_ICON, 20, 20, metadata.getDefenseLevel(), event, "desc.ego_weapons.defense_level", gameTime);
        renderResistanceIcon(scaledOffs + scaledWidth - 40, (float) (yOffs + 60 + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 1.5f) * 2f)), GenericEgoDamage.AttackTypes.SLASH.getIconLoc(), 20, 20, metadata.getSlashRes(), event, renderType, mat, gameTime);
        renderResistanceIcon(scaledOffs + scaledWidth     , (float) (yOffs + 60 + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 2.5f) * 2f)), GenericEgoDamage.AttackTypes.PIERCE.getIconLoc(), 20, 20, metadata.getPierceRes(), event, renderType, mat, gameTime);
        renderResistanceIcon(scaledOffs + scaledWidth + 40, (float) (yOffs + 60 + lineDist*offsetLine + Math.min(0, Math.sin(gameTime * 0.1f + 3.5f) * 2f)), GenericEgoDamage.AttackTypes.BLUNT.getIconLoc(), 20, 20, metadata.getBluntRes(), event, renderType, mat, gameTime);

        int firstElementSize = renderExtraAttribute(scaledOffs + scaledWidth, yOffs + lineDist*(offsetLine + 10), event, new Tuple<>("desc.ego_weapons.sanity_bonus", 1f), new Tuple<>("desc.ego_weapons.stagger_bonus", 3f));
        event.getMatrixStack().popPose();
    }


    private  static void renderWeaponStats(RenderTooltipEvent.PostBackground event, int offsetLine) {
        event.getMatrixStack().pushPose();

        event.getMatrixStack().translate(0,0,401);
        Minecraft.getInstance().getTextureManager().bind(ICON_BORDER);
        AbstractGui.blit(event.getMatrixStack(), event.getX(), event.getY()-6+10*offsetLine, 0, 0, 34, 34, 34, 34);

        event.getMatrixStack().popPose();
    }

    @SafeVarargs
    private static int renderExtraAttribute(float offsX, float offsY, RenderTooltipEvent.PostBackground event, Tuple<String, Float>... values) {
        event.getMatrixStack().pushPose();

        IFormattableTextComponent[] formattedTexts = new IFormattableTextComponent[values.length];
        int[] textSize = new int[values.length];

        int totalSize = 0;

        int i = 0;
        for (Tuple<String, Float> value : values) {
            IFormattableTextComponent translationText = new TranslationTextComponent(value.getA()).append(new StringTextComponent("  "+(value.getB() > 0 ? "+" : "-")+value.getB()+"   "));
            int size = event.getFontRenderer().width(translationText);


            formattedTexts[i] = translationText;
            totalSize += size + 10;
            textSize[i] = size;
            i++;
        }

        // RenderSystem.enableAlphaTest();
        event.getMatrixStack().translate(offsX - (double) totalSize /2, offsY, 0);

        i = 0;

        int horizontalOffset = 0;

        for (IFormattableTextComponent textComponent : formattedTexts) {

            Minecraft.getInstance().getTextureManager().bind(PLAQUE_SILVER);
            AbstractGui.blit(event.getMatrixStack(), horizontalOffset, 0, 1, 0, textSize[i] +2, 16, textSize[i] +4, 16);

            event.getFontRenderer().draw(event.getMatrixStack(), textComponent, 6 + horizontalOffset, 4, 0xFFFFFF);

            horizontalOffset += textSize[i] + 10;
            i++;
        }






        //AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, 1, 16, 64, 16);

        //AbstractGui.blit(event.getMatrixStack(), size, 0, 0, 0, 1, 16, 64, 16);



        event.getMatrixStack().popPose();

        return totalSize;
    }

    private static void renderResistanceIcon(float offsX, float offsY, ResourceLocation iconLoc, int iconX, int iconY, float value, RenderTooltipEvent.PostBackground event, IRenderTypeBuffer.Impl renderType, Matrix4f mat, float gameTime) {
        event.getMatrixStack().pushPose();

        // RenderSystem.enableAlphaTest();
        event.getMatrixStack().translate(offsX, offsY, 0);

        if (value > 1f) {
            event.getMatrixStack().pushPose();
            float correctedValue = (float) Math.min(1, (value - 1f) * 0.9f + (value - 1f) * 0.1f * Math.sin(gameTime * 0.1f));
            //RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.color4f(Math.min(correctedValue, 1),0,0, 1);


            Minecraft.getInstance().getTextureManager().bind(ICON_BORDER_GLOW);
            AbstractGui.blit(event.getMatrixStack(), - (18), - 14, 0, 0, 70, 70, 70, 70);

            //RenderSystem.enableAlphaTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();

            RenderSystem.color4f(1,1,1,1);
            event.getMatrixStack().popPose();
        }



        //Minecraft.getInstance().getTextureManager().bind(ICON_BORDER);
        //AbstractGui.blit(event.getMatrixStack(), event.getX() + offsX, event.getY() + offsY, 0, 0, 34, 34, 34, 34);

        Minecraft.getInstance().getTextureManager().bind(ICON_BORDER);
        AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, 34, 34, 34, 34);


        // Place the damage type icon in the middle.
        Minecraft.getInstance().getTextureManager().bind(iconLoc);
        AbstractGui.blit(event.getMatrixStack(), (17 - iconX/2), (17-iconY/2), 0, 0, iconX, iconY, iconX, iconY);

        StringTextComponent multiplier = new StringTextComponent(DamageResistanceSystem.getDamageIdentifier(value, true));
        StringTextComponent multiplier2 = new StringTextComponent((value)+"x");
        int size = event.getFontRenderer().width(multiplier);
        int size2 = event.getFontRenderer().width(multiplier2);

        int col = getDamageIdentifierCol(value);

        //event.getFontRenderer().drawInBatch(multiplier, event.getX() + offsX + (17f - size/2f), (float)event.getY() + offsY + 36, -1, true, mat, renderType, false, 0, 15728880);
        event.getMatrixStack().pushPose();
        event.getMatrixStack().scale(0.8f, 0.8f, 0.8f);
        // 2 / 0.8f
        event.getMatrixStack().translate((17f - size/(2.5f)), 0, 0);
        // 36 / 0.8f
        event.getFontRenderer().draw(event.getMatrixStack(), multiplier, 0, 45.0f, col);
        event.getMatrixStack().popPose();
        event.getFontRenderer().draw(event.getMatrixStack(), multiplier2, (17f - size2/2f), 46, col);

        event.getMatrixStack().popPose();
    }

    private static void renderValueIcon(float offsX, float offsY, ResourceLocation iconLoc, int iconX, int iconY, float value, RenderTooltipEvent.PostBackground event, String title, float gameTime) {
        event.getMatrixStack().pushPose();

        // RenderSystem.enableAlphaTest();
        event.getMatrixStack().translate(offsX, offsY, 0);

        //Minecraft.getInstance().getTextureManager().bind(ICON_BORDER);
        //AbstractGui.blit(event.getMatrixStack(), event.getX() + offsX, event.getY() + offsY, 0, 0, 34, 34, 34, 34);

        Minecraft.getInstance().getTextureManager().bind(ICON_BORDER);
        AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, 34, 34, 34, 34);


        // Place the damage type icon in the middle.
        Minecraft.getInstance().getTextureManager().bind(iconLoc);
        AbstractGui.blit(event.getMatrixStack(), (17 - iconX/2), (17-iconY/2), 0, 0, iconX, iconY, iconX, iconY);

        IFormattableTextComponent valueText = new StringTextComponent("+"+((int)value)).withStyle(TextFormatting.UNDERLINE).withStyle(TextFormatting.ITALIC);
        IFormattableTextComponent titleTExt = new TranslationTextComponent(title).withStyle(TextFormatting.ITALIC);
        int titleSize = event.getFontRenderer().width(titleTExt);
        int size2 = event.getFontRenderer().width(valueText);

        //event.getFontRenderer().drawInBatch(multiplier, event.getX() + offsX + (17f - size/2f), (float)event.getY() + offsY + 36, -1, true, mat, renderType, false, 0, 15728880);
        event.getMatrixStack().pushPose();
        event.getMatrixStack().scale(0.8f, 0.8f, 0.8f);
        // 2 / 0.8f
        event.getMatrixStack().translate((17f - titleSize/(2.5f)), 0, 0);
        // 36 / 0.8f
        event.getFontRenderer().draw(event.getMatrixStack(), titleTExt, 0, 45.0f, 0xAAAAAA);
        event.getMatrixStack().popPose();
        event.getFontRenderer().draw(event.getMatrixStack(), valueText, (17f - size2/2f), 46, 0xDDDDDD);

        event.getMatrixStack().popPose();
    }


}
