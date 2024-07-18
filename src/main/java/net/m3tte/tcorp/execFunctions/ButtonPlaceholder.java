package net.m3tte.tcorp.execFunctions;
/*package net.m3tte.tcorp.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.execFunctions.BlackSilenceButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class BlacksilenceswapuiGuiWindow extends ContainerScreen<BlacksilenceswapuiGui.GuiContainerMod> {
    private World world;
    private int x, y, z;
    private PlayerEntity entity;
    private final static HashMap guistate = BlacksilenceswapuiGui.guistate;

    public BlacksilenceswapuiGuiWindow(BlacksilenceswapuiGui.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.xSize = 217;
        this.ySize = 173;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();



        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeScreen();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {

        // Bottom Middle
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/durandal.png"));
        this.blit(ms, this.xSize/2-16, this.ySize-32, 0, 0, 32, 32, 32, 32);

        // Bottom Right + 1 offset due to texture
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/atelier_pistols.png"));
        this.blit(ms, (int)((this.xSize/2)*1.55f)-16, (int)((this.ySize/2)*1.65f)-32, 0, 0, 32, 32, 32, 32);

        // Bottom Left
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/atelier_shotgun.png"));
        this.blit(ms, (int)((this.xSize/2)*0.45f)-14, (int)((this.ySize/2)*1.65f)-30, 0, 0, 28, 28, 28, 28);

        // Middle Right
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/allas.png"));
        this.blit(ms, (int)((this.xSize/2)*1.65f)-14, (int)((this.ySize/2)*0.9f)-31, 0, 0, 28, 28, 28, 28);

        // Middle Left
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/crystal_atelier.png"));
        this.blit(ms, (int)((this.xSize/2)*0.35f)-14, (int)((this.ySize/2)*0.9f)-30, 0, 0, 28, 28, 28, 28);

        // Top Right
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/ranga.png"));
        this.blit(ms, (int)((this.xSize/2)*1.3f)-17, (int)((this.ySize/2)*0.4f)-34, 0, 0, 34, 34, 34, 34);

        // Top Left
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/zelkova.png"));
        this.blit(ms, (int)((this.xSize/2)*0.7f)-16, (int)((this.ySize/2)*0.4f)-32, 0, 0, 28, 30, 28, 30);



        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("tcorp:textures/screens/blacksilence/icon.png"));
        this.blit(ms, this.xSize/2-36, this.ySize/2-28, 0, 0, 72, 48, 72, 48);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        minecraft.keyboardListener.enableRepeatEvents(true);
        // Bottom  ( Durandal )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)(this.xSize/2) - 23, this.guiTop + (int)(this.ySize/1.3), 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));


        // Bottom Right ( Atelier Pistol )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)((this.xSize/2)*1.55f)-23, this.guiTop + (int)((this.ySize/2)*1.65f)-40, 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));

        // Bottom Left ( Atelier Shotgun )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)((this.xSize/2)*0.45f)-25, this.guiTop + (int)((this.ySize/2)*1.65f)-40, 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));

        // Middle Right ( Allas Workshop )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)((this.xSize/2)*1.65f)-23, this.guiTop + (int)((this.ySize/2)*0.9f)-40, 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));

        // Middle Left ( Crystal Atelier )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)((this.xSize/2)*0.35f)-25, this.guiTop + (int)((this.ySize/2)*0.9f)-40, 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));

        // Top Right ( Ranga Workshop )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)((this.xSize/2)*1.3f)-23, this.guiTop + (int)((this.ySize/2)*0.4f)-40, 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));

        // Top Left ( Zelkova Atelier )
        this.addButton(new BlackSilenceButton(this.guiLeft + (int)((this.xSize/2)*0.7f)-25, this.guiTop + (int)((this.ySize/2)*0.4f)-40, 47, 47, new StringTextComponent(""), e -> {
            if (true) {
                TcorpMod.PACKET_HANDLER.sendToServer(new BlacksilenceswapuiGui.ButtonPressedMessage(0, x, y, z));
                BlacksilenceswapuiGui.handleButtonAction(entity, 0, x, y, z);
            }
        }));
    }
}*/
