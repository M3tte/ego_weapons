
package net.m3tte.ego_weapons.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceButton;
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
public class BlackSilenceSwapWindow extends ContainerScreen<BlackSilenceSwapGUI.GuiContainerMod> {
	private World world;
	private int x, y, z;
	private PlayerEntity entity;
	private final static HashMap guistate = BlackSilenceSwapGUI.guistate;

	public BlackSilenceSwapWindow(BlackSilenceSwapGUI.GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.x = 217;
		this.y = 173;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
		drawGuiContainerForegroundLayer(ms, mouseX, mouseY);

	}

	@Override
	protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();



		RenderSystem.disableBlend();
	}



	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void tick() {
		super.tick();
	}

	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();

		// Bottom Middle
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/durandal.png"));
		this.blit(ms, this.leftPos + (int)(this.getXSize()/1.95-17), this.topPos + this.getYSize()-32, 0, 0, 32, 32, 32, 32);

		// Bottom Right + 1 offset due to texture
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/atelier_pistols.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*1.52f)-17, this.topPos + (int)((this.getYSize()/2)*1.83f)-32, 0, 0, 32, 32, 32, 32);

		// Lower Middle Right
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/allas.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*1.75f)-15, this.topPos + (int)((this.getYSize()/2)*1.4f)-31, 0, 0, 28, 28, 28, 28);

		// Upper Middle Right ( Old boys)
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/old_boys.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*1.75f)-15, this.topPos + (int)((this.getYSize()/2)*0.85f)-31, 0, 0, 27, 27, 27, 27);

		// Top Right (Mook)
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/mook.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*1.52f)-15, this.topPos + (int)((this.getYSize()/2)*0.42f)-30, 0, 0, 27, 27, 27, 27);

		// Top (Ranga)
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/ranga.png"));
		this.blit(ms, this.leftPos + (int)(this.getXSize()/1.95-18), this.topPos -9, 0, 0, 34, 34, 34, 34);

		// Top Left (Zelkova)
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/zelkova.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*0.55f)-16, this.topPos + (int)((this.getYSize()/2)*0.42f)-32, 0, 0, 28, 28, 28, 28);

		// Upper Middle Left (Wheels Industry)
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/wheels.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*0.31f)-14, this.topPos + (int)((this.getYSize()/2)*0.85f)-29, 0, 0, 26, 25, 25, 25);

		// Lower Middle Right
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/crystal_atelier.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*0.31f)-17, this.topPos + (int)((this.getYSize()/2)*1.4f)-31, 0, 0, 28, 28, 28, 28);

		// Bottom Left + 1 offset due to texture
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/atelier_shotgun.png"));
		this.blit(ms, this.leftPos + (int)((this.getXSize()/2)*0.55)-16, this.topPos + (int)((this.getYSize()/2)*1.83f)-32, 0, 0, 30, 30, 30, 30);

		// Bottom left
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blacksilence/icon.png"));
		this.blit(ms, this.leftPos + this.getXSize()/2-36, this.topPos + this.getYSize()/2-28, 0, 0, 70, 48, 72, 48);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		minecraft.keyboardHandler.setSendRepeatsToGui(true);
		// Bottom  ( Durandal )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)(this.getXSize()/1.95) - 24, this.topPos + (int)(this.getYSize()/1.3), 47, 47, new StringTextComponent("Durandal"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(0, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 0);
			}
		}));


		// Bottom Right ( Atelier Pistol )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*1.52f)-24, this.topPos + (int)((this.getYSize()/2)*1.83f)-40, 47, 47, new StringTextComponent("Atelier Logic: Pistol"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(1, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 1);
			}
		}));

		// Bottom Left ( Atelier Shotgun )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*0.55f)-25, this.topPos + (int)((this.getYSize()/2)*1.83f)-40, 47, 47, new StringTextComponent("Atelier Logic: Shotgun"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(9, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 9);
			}
		}));

		// Lower Middle Right ( Allas Workshop )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*1.75f)-24, this.topPos + (int)((this.getYSize()/2)*1.4f)-40, 47, 47, new StringTextComponent("Allas Workshop"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(2, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 2);
			}
		}));

		// Upper Middle Right ( Old Boys Workshop )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*1.75f)-24, this.topPos + (int)((this.getYSize()/2)*0.85f)-40, 47, 47, new StringTextComponent("Old Boys Workshop"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(3, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 3);
			}
		}));

		// Top Right ( Mook Workshop )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*1.52f)-24, this.topPos + (int)((this.getYSize()/2)*0.42f)-40, 47, 47, new StringTextComponent("Mook Workshop"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(4, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 4);
			}
		}));

		// Top ( Ranga Workshop )
		this.addButton(new BlackSilenceButton(this.leftPos + (int)(this.getXSize()/1.95) - 24, this.topPos - 17, 47, 47, new StringTextComponent("Ranga Workshop"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(5, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 5);
			}
		}));

		// Top Left ( Zelkova ) (int)((this.getXSize()/2)*0.55f)-16, (int)((this.getYSize()/2)*0.42f)-32,
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*0.55f)-25, this.topPos + (int)((this.getYSize()/2)*0.42f)-40, 47, 47, new StringTextComponent("Zelkova Workshop"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(6, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 6);
			}
		}));

		// Upper Middle Left ( Wheels ) (int)((this.getXSize()/2)*0.55f)-16, (int)((this.getYSize()/2)*0.42f)-32,
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*0.31f)-25, this.topPos + (int)((this.getYSize()/2)*0.85f)-40, 47, 47, new StringTextComponent("Wheels Industry"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(7, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 7);
			}
		}));

		// Lower Middle Left ( Crystal Atelier ) (int)((this.getXSize()/2)*0.55f)-16, (int)((this.getYSize()/2)*0.42f)-32,
		// (int)((this.getXSize()/2)*1.75f)-23, this.topPos + (int)((this.getYSize()/2)*1.4f)-40,
		this.addButton(new BlackSilenceButton(this.leftPos + (int)((this.getXSize()/2)*0.31f)-25, this.topPos + (int)((this.getYSize()/2)*1.4f)-40, 47, 47, new StringTextComponent("Crystal Atelier"), e -> {
			if (true) {
				EgoWeaponsMod.PACKET_HANDLER.sendToServer(new BlackSilenceSwapGUI.ButtonPressedMessage(8, x, y, z));
				BlackSilenceSwapGUI.handleWeaponSwap(entity, 8);
			}
		}));
	}
}
