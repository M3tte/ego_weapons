package net.m3tte.ego_weapons.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.ItemMetadata;
import net.m3tte.ego_weapons.item.descriptionSystem.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.item.descriptionSystem.GenericEgoWeaponsArmor;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.ArmorMetadata;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.item.TagSystem;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
                        case ARMOR_STATISTICS:
                            if (((AttributeTextPlaceholder) line).getMetadata() instanceof ArmorMetadata) {
                                renderDamageResistances(event, offsetLine, renderType, mat, tickTime, 0.8f, (ArmorMetadata) ((AttributeTextPlaceholder) line).getMetadata());
                            }

                            break;
                        case WEAPON:
                            renderWeaponStats(event, offsetLine);
                            break;
                        case TAGS:
                            if (((AttributeTextPlaceholder) line).getMetadata() instanceof ItemMetadata) {
                                renderTags(event, offsetLine, renderType, tickTime, 0.8f, (ItemMetadata) ((AttributeTextPlaceholder) line).getMetadata());
                            }
                            break;
                        case KEYWORDS:
                            if (((AttributeTextPlaceholder) line).getMetadata() instanceof ItemMetadata) {
                                renderKeywords(event, offsetLine, renderType, tickTime, 0.8f, (ItemMetadata) ((AttributeTextPlaceholder) line).getMetadata());
                            }
                            break;
                    }
                }
                offsetLine++;
            }

            renderType.endBatch();
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

        int firstElementSize = renderExtraAttributes(scaledOffs + scaledWidth, yOffs + lineDist*(offsetLine + 10), event, new Tuple<>("desc.ego_weapons.sanity_bonus", metadata.getBonusSanity()), new Tuple<>("desc.ego_weapons.stagger_bonus", metadata.getBonusStagger()));
        event.getMatrixStack().popPose();
    }


    private  static void renderWeaponStats(RenderTooltipEvent.PostBackground event, int offsetLine) {
        event.getMatrixStack().pushPose();

        event.getMatrixStack().translate(0,0,401);
        Minecraft.getInstance().getTextureManager().bind(ICON_BORDER);
        AbstractGui.blit(event.getMatrixStack(), event.getX(), event.getY()-6+10*offsetLine, 0, 0, 34, 34, 34, 34);

        event.getMatrixStack().popPose();
    }

    private static void renderTags(RenderTooltipEvent.PostBackground event, int offsetLine, IRenderTypeBuffer.Impl renderType, float gameTime, float scaleFactor, ItemMetadata metadata) {
        event.getMatrixStack().pushPose();

        event.getMatrixStack().translate(0,0,405);
        event.getMatrixStack().scale(scaleFactor, scaleFactor, scaleFactor);

        // Invert the scale factor
        scaleFactor = 1 / scaleFactor;

        float scaledOffs = ((event.getX()) * (scaleFactor)) ;
        float scaledWidth = (event.getWidth() / 2f) * scaleFactor;
        float lineDist = 10 * scaleFactor;
        float yOffs = event.getY() * (scaleFactor);

        renderTagsLinewise(scaledOffs + scaledWidth, yOffs + 5 + lineDist*offsetLine, event, metadata.getTargetItem(), scaledWidth * 2, 3);

        //int firstElementSize = renderExtraAttributes(scaledOffs + scaledWidth, yOffs + lineDist*(offsetLine + 10), event, new Tuple<>("desc.ego_weapons.sanity_bonus", 1f), new Tuple<>("desc.ego_weapons.stagger_bonus", 3f));
        event.getMatrixStack().popPose();
    }

    private static void renderKeywords(RenderTooltipEvent.PostBackground event, int offsetLine, IRenderTypeBuffer.Impl renderType, float gameTime, float scaleFactor, ItemMetadata metadata) {
        event.getMatrixStack().pushPose();

        event.getMatrixStack().translate(0,0,405);
        event.getMatrixStack().scale(scaleFactor, scaleFactor, scaleFactor);

        // Invert the scale factor
        scaleFactor = 1 / scaleFactor;

        float scaledOffs = ((event.getX()) * (scaleFactor)) ;
        float scaledWidth = (event.getWidth() / 2f) * scaleFactor;
        float lineDist = 10 * scaleFactor;
        float yOffs = event.getY() * (scaleFactor);

        renderKeywordsLinewise(scaledOffs + scaledWidth, yOffs + 5 + lineDist*offsetLine, event, metadata.getTargetItem(), scaledWidth * 2, 3, metadata.getKeywords());

        //int firstElementSize = renderExtraAttributes(scaledOffs + scaledWidth, yOffs + lineDist*(offsetLine + 10), event, new Tuple<>("desc.ego_weapons.sanity_bonus", 1f), new Tuple<>("desc.ego_weapons.stagger_bonus", 3f));
        event.getMatrixStack().popPose();
    }

    private static void renderKeywordsLinewise(float offsX, float offsY, RenderTooltipEvent.PostBackground event, Item item, float lineWidth, int maxLines, CountPotencyStatus... keywords) {

        int[] textSize = new int[keywords.length];
        int i = 0;

        LinkedList<LinkedList<Tuple<ResourceLocation, IFormattableTextComponent>>> lines = new LinkedList<>();

        LinkedList<Tuple<ResourceLocation, IFormattableTextComponent>> operatingLine = new LinkedList<>();
        LinkedList<Integer> lineSizes = new LinkedList<>();
        int lineIDX = 0;
        int lineSize = 0;
        for (CountPotencyStatus countPotencyStatus : keywords) {

            IFormattableTextComponent translationText = new TranslationTextComponent(countPotencyStatus.getDescriptionId());
            int size = event.getFontRenderer().width(translationText) + 26;
            textSize[i] = size;

            Tuple<ResourceLocation, IFormattableTextComponent> currentElement = new Tuple<>(countPotencyStatus.getIcon() , translationText);

            if ((lineSize + size) >= lineWidth) {
                lineSizes.add(lineSize);
                lines.add(operatingLine);
                operatingLine = new LinkedList<>();
                lineIDX++;
                lineSize = 0;
            }
            i++;
            lineSize += size + 6;
            operatingLine.add(currentElement);
        }

        if (!operatingLine.isEmpty()) {
            lines.add(operatingLine);
            lineSizes.add(lineSize);
        }

        i = 0;
        int lineIdx = 1;
        for (LinkedList<Tuple<ResourceLocation, IFormattableTextComponent>> line : lines) {
            int horizontalOffset = 0;
            event.getMatrixStack().pushPose();

            if (lineSizes.isEmpty())
                break;

            event.getMatrixStack().translate(offsX - lineSizes.removeFirst()/2f, offsY + 18 * (lineIdx - 1), 0);

            for (Tuple<ResourceLocation, IFormattableTextComponent> element : line) {
                Minecraft.getInstance().getTextureManager().bind(TagSystem.TagPresets.WHITE_BORDER.getBackground());
                int offsetFactor = (textSize[i] / 64) + 1;
                AbstractGui.blit(event.getMatrixStack(), horizontalOffset, 5, offsetFactor + 1, 0, textSize[i] - offsetFactor, 16, Math.max(64,textSize[i] + offsetFactor), 16);

                event.getMatrixStack().pushPose();
                event.getMatrixStack().translate(horizontalOffset, -2, 0);
                Minecraft.getInstance().getTextureManager().bind(element.getA());
                AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, 20, 20, 20, 20);
                event.getMatrixStack().popPose();

                event.getFontRenderer().draw(event.getMatrixStack(), element.getB(), 24 + horizontalOffset, 4, 0xff5555);
                horizontalOffset += 6 + textSize[i];
                i++;
            }
            event.getMatrixStack().popPose();

            lineIdx++;

            if (lineIdx > maxLines)
                break;
        }
    }

    private static void renderTagsLinewise(float offsX, float offsY, RenderTooltipEvent.PostBackground event, Item item, float lineWidth, int maxLines) {
        Set<ResourceLocation> tags = item.getTags();

        if (tags.isEmpty())
            return;

        int[] textSize = new int[tags.size()];
        int i = 0;

        LinkedList<LinkedList<Tuple<TagSystem.TagPresets, IFormattableTextComponent>>> lines = new LinkedList<>();

        HashMap<ResourceLocation, TagSystem.TagPresets> presets = TagSystem.getRegisteredTagPresets();

        LinkedList<Tuple<TagSystem.TagPresets, IFormattableTextComponent>> operatingLine = new LinkedList<>();
        LinkedList<Integer> lineSizes = new LinkedList<>();
        int lineIDX = 0;
        int lineSize = 0;
        for (ResourceLocation resourceLocation : tags) {

            IFormattableTextComponent translationText = new TranslationTextComponent("desc."+resourceLocation.getNamespace()+".tags."+resourceLocation.getPath());
            int size = event.getFontRenderer().width(translationText) + 12;
            textSize[i] = size;

            Tuple<TagSystem.TagPresets, IFormattableTextComponent> currentElement = new Tuple<>(presets.getOrDefault(resourceLocation, TagSystem.TagPresets.DEFAULT), translationText);

            if ((lineSize + size) >= lineWidth) {
                lineSizes.add(lineSize);
                lines.add(operatingLine);
                operatingLine = new LinkedList<>();
                lineIDX++;
                lineSize = 0;
            }
            i++;
            lineSize += size + 6;
            operatingLine.add(currentElement);
        }

        if (!operatingLine.isEmpty()) {
            lines.add(operatingLine);
            lineSizes.add(lineSize);
        }

        i = 0;
        int lineIdx = 1;
        for (LinkedList<Tuple<TagSystem.TagPresets, IFormattableTextComponent>> line : lines) {
            int horizontalOffset = 0;
            event.getMatrixStack().pushPose();

            if (lineSizes.isEmpty())
                break;

            event.getMatrixStack().translate(offsX - lineSizes.removeFirst()/2f, offsY + 18 * (lineIdx - 1), 0);

            for (Tuple<TagSystem.TagPresets, IFormattableTextComponent> element : line) {
                Minecraft.getInstance().getTextureManager().bind(element.getA().getBackground());
                int offsetFactor = (textSize[i] / 64) + 1;
                AbstractGui.blit(event.getMatrixStack(), horizontalOffset, 0, offsetFactor + 1, 0, textSize[i] - offsetFactor, 16, Math.max(64,textSize[i] + offsetFactor), 16);

                // Blit the 2 edges
                AbstractGui.blit(event.getMatrixStack(), horizontalOffset, 0, 0, 0, 1, 16, 64, 16);
                AbstractGui.blit(event.getMatrixStack(), horizontalOffset + textSize[i] - offsetFactor, 0, 0, 0, 1, 16, 64, 16);

                event.getFontRenderer().draw(event.getMatrixStack(), element.getB(), 6 + horizontalOffset, 4, element.getA().getColor());
                horizontalOffset += 6 + textSize[i];
                i++;
            }
            event.getMatrixStack().popPose();

            lineIdx++;

            if (lineIdx > maxLines)
                break;
        }
    }

    @SafeVarargs
    private static int renderExtraAttributes(float offsX, float offsY, RenderTooltipEvent.PostBackground event, Tuple<String, Float>... values) {
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

            Minecraft.getInstance().getTextureManager().bind(TagSystem.TagPresets.SIMPLE.getBackground());
            AbstractGui.blit(event.getMatrixStack(), horizontalOffset, 0, 1, 0, textSize[i] +2, 16, textSize[i] +4, 16);
            AbstractGui.blit(event.getMatrixStack(), horizontalOffset + textSize[i]+2, 0, 0, 0, 1, 16, 64, 16);

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

        if (value > 1.1f) {
            event.getMatrixStack().pushPose();
            float correctedValue = (float) Math.min(1, (value * 1.4 - 1.4f) * 0.9f + (value - 1f) * 0.1f * Math.sin(gameTime * 0.1f));
            //RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.color4f(Math.min(correctedValue, 1),0,0, 1);


            Minecraft.getInstance().getTextureManager().bind(ICON_BORDER_GLOW);
            AbstractGui.blit(event.getMatrixStack(), - (19), - 14, 0, 0, 70, 70, 70, 70);

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
        event.getMatrixStack().translate((17f - size/(2.5f)), 0, 0);
        // 2 / 0.8f
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
