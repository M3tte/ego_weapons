package net.m3tte.ego_weapons.execFunctions;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class BlackSilenceButton extends Button {
    public BlackSilenceButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    public BlackSilenceButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, width, height, title, pressedAction, onTooltip);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        delegatedButtonRender(matrixStack, mouseX, mouseY, partialTicks);
    }

    private final ResourceLocation BUTTON_RESOURCE_LOCATION = new ResourceLocation("ego_weapons:textures/screens/blacksilencebutton.png");

    private float hoverTime = 0;

    public void delegatedButtonRender(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(BUTTON_RESOURCE_LOCATION);

        if (this.isHovered && hoverTime < 10) {
            hoverTime += 0.8f;
        } else if (hoverTime > 0 && !this.isHovered) {
            hoverTime -= 0.9f;
        } else if (hoverTime < 0){
            hoverTime = 0;
        }
        //System.out.println("HoverTime: "+hoverTime);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.heightMultiplier();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, 0, 47, 47, 47, 94);

        if (hoverTime > 0) {
            this.blit(matrixStack, this.x + (int)(47f/2 * (1-hoverTime / 10)), this.y, (int)(47f/2 * (1-hoverTime / 10)), 47, (int)(47f * (hoverTime / 10)), 47, 47, 94);
        }

        this.renderBg(matrixStack, minecraft, mouseX, mouseY);

        if (minecraft.player == null)
            return;

        int j = getFGColor();
        RenderSystem.pushMatrix();
        float scale = Math.min(1,(hoverTime - 5) / 15f);
        RenderSystem.translated(this.x + this.width / 2f, (int)(this.y + (this.height - 8) / 2 + Math.sin((minecraft.player.tickCount / 10f) * (hoverTime / 5)) * 2), 0);
        RenderSystem.pushMatrix();
        RenderSystem.scaled(scale, scale, scale);
        if (hoverTime >= 5) {
            drawCenteredString(matrixStack, fontrenderer, this.getMessage(), 0,0, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        }
        RenderSystem.popMatrix();
        RenderSystem.popMatrix();
    }

    public int heightMultiplier() {
        return 0;
    }
}
