
package net.m3tte.ego_weapons.gui.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber({Dist.CLIENT})
public class AmmoOverlay {


	private static ResourceLocation BULLET = new ResourceLocation("ego_weapons:textures/screens/bullet.png");
	private static ResourceLocation EMPTY_BULLET = new ResourceLocation("ego_weapons:textures/screens/bullet_empty.png");

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
			int w = event.getWindow().getGuiScaledWidth();
			int h = event.getWindow().getGuiScaledHeight();
			int posX = w / 2;
			int posY = h / 2;
			PlayerEntity entity = Minecraft.getInstance().player;
			World world = entity.level;
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			String itemRegistry = entity.getMainHandItem().getItem().getRegistryName().toString();

			if (EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get().equals(entity.getMainHandItem().getItem())) {
				if (world.isClientSide) {
					double ammo = entity.getMainHandItem().getOrCreateTag().getInt("ammo");

					Minecraft.getInstance().getTextureManager().bind(ammo >= 1 ? BULLET : EMPTY_BULLET);
					AbstractGui.blit(event.getMatrixStack(), posX - 15, posY + -20, 0, 0, 16, 16, 16, 16);

					Minecraft.getInstance().getTextureManager().bind(ammo >= 2 ? BULLET : EMPTY_BULLET);
					AbstractGui.blit(event.getMatrixStack(), posX, posY + -20, 0, 0, 16, 16, 16, 16);

					if (!((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new EgoWeaponsModVars.PlayerVariables())).firingMode)) {
						Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/fire_off.png"));
						AbstractGui.blit(event.getMatrixStack(), posX - 15, posY + -20, 0, 0, 16, 16, 16, 16);
						AbstractGui.blit(event.getMatrixStack(), posX, posY + -20, 0, 0, 16, 16, 16, 16);

					}
				}
			}
			else if (EgoWeaponsItems.ATELIER_LOGIC_SHOTGUN.get().equals(entity.getMainHandItem().getItem())) {

				double capacity = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).gunMagSize);

				Minecraft.getInstance().getTextureManager().bind(capacity == 0 ? BULLET : EMPTY_BULLET);
				AbstractGui.blit(event.getMatrixStack(), posX, posY, 0, -10, 16, 16, 16, 16);

				if (!((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).firingMode)) {
					Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/fire_off.png"));
					AbstractGui.blit(event.getMatrixStack(), posX, posY, 0, -10, 16, 16, 16, 16);
				}

			}



			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
