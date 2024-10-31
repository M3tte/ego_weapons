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
        deligatedButtonRender(matrixStack, mouseX, mouseY, partialTicks);
    }

    private float hoverTime = 0;

    public void deligatedButtonRender(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilencebutton.png"));

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
        int j = getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);


    }

    public int heightMultiplier() {
        return 0;
    }
}
