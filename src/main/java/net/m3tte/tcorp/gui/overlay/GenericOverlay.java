
package net.m3tte.tcorp.gui.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.potion.Shell;
import net.m3tte.tcorp.procedures.BlipwarninghandlerProcedure;
import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.m3tte.tcorp.procedures.abilities.WeaponAbilityProcedure;
import net.m3tte.tcorp.procedures.abilities.armorAbilities.ItemAbility;
import net.m3tte.tcorp.procedures.abilities.ArmorAbilityProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import yesman.epicfight.client.gui.ModIngameGui;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;


@Mod.EventBusSubscriber({Dist.CLIENT})
public class GenericOverlay extends ModIngameGui {

	static long latestArmorUnready = 0;
	static boolean hasPlayedUnready = false;

	static double currentHealthPercent = -1;
	static double currentBrightHealthPercent = -1;

	static double currentStaggerPercent = -1;
	static double currentBrightStaggerPercent = -1;
	static long latestWeaponUnready = 0;


	static long lastRenderedTick = 0;
	static boolean hasPlayedWeaponUnready = false;


	final static ResourceLocation base_ui = new ResourceLocation("tcorp:textures/screens/gui/base_ui.png");
	final static ResourceLocation healthbar = new ResourceLocation("tcorp:textures/screens/gui/healthbar.png");
	final static ResourceLocation healthbar_b = new ResourceLocation("tcorp:textures/screens/gui/healthbar_bright.png");
	final static ResourceLocation staggerbar = new ResourceLocation("tcorp:textures/screens/gui/staggerbar.png");
	final static ResourceLocation staggerbar_b = new ResourceLocation("tcorp:textures/screens/gui/staggerbar_bright.png");


	static ResourceLocation[] shellLeft = parseMultiStateTexture(1 ,5, "shell_left");
	static ResourceLocation[] shellRight = parseMultiStateTexture(1 ,5, "shell_right");
	static ResourceLocation[] shellHealthbar = parseMultiStateTexture(1 ,5, "ui/healthbar_shell");

	private static ResourceLocation[] parseMultiStateTexture(int min, int max, String prefix) {
		ResourceLocation[] array = new ResourceLocation[max - min + 1];

		int index = 0;
		for (int x = min; x <= max; x++) {
			array[index] = new ResourceLocation("tcorp:textures/screens/overlay/shell/"+prefix+"_"+x+".png");
			index++;
		}
		return array;
	}


	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
			int w = event.getWindow().getGuiScaledWidth();
			int h = event.getWindow().getGuiScaledHeight();
			int posX = w / 2;
			int posY = h / 2;
			World _world = null;
			double _x = 0;
			double _y = 0;
			double _z = 0;
			PlayerEntity entity = !(Minecraft.getInstance().getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity)Minecraft.getInstance().getCameraEntity();
			if (entity != null) {
				_world = entity.level;
				_x = entity.getX();
				_y = entity.getY();
				_z = entity.getZ();
			}
			World world = _world;
			double x = _x;
			double y = _y;
			double z = _z;
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();

			TcorpModVariables.PlayerVariables playerVariables = entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables());
			int energy = (int) playerVariables.blips;

			MatrixStack matStack = event.getMatrixStack();


			// Blip UI

			/*matStack.pushPose();
			matStack.scale(2F, 2F, 2.0F);
			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("tcorp:textures/screens/power_blip.png"));
			ModIngameGui.blit(matStack, posX , posY, 55, 55, 3, 9, 3, 9);
			matStack.popPose();*/


			//Minecraft.getInstance().font.draw(event.getMatrixStack(), "TCorp - Indev - Bugs are to be expected", 30, 20, -1);

			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("tcorp:textures/screens/blip_background.png"));
			blit(event.getMatrixStack(), posX + 94, posY + 98, 0, 0, 64, 20, 64, 20);
			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("tcorp:textures/screens/power_blip.png"));
			for (int sc = 0; sc < energy; sc++) {
                blit(event.getMatrixStack(), posX + 98 + sc * 6, posY + 102, 0, 0, 3, 9, 3, 9);
            }
			ItemStack equippedArmor = entity.getItemBySlot(EquipmentSlotType.CHEST);
			ItemStack equippedItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND);

			// Main UI

			// UI

			renderStatisticUI(h-70, 2, entity, playerVariables, event);



			if (!equippedArmor.isEmpty()) { // Calculate Ability Widget
				ItemAbility ability = ArmorAbilityProcedure.getForItem(equippedArmor.getItem());
				ResourceLocation icon = ability.getIconLocation(entity, playerVariables);

				if (icon != null) {
					float availability = ability.getAvailability(entity, playerVariables);
					float flashProgress = 0;
					if (availability >= 1) {
						flashProgress = 1 - ((float) (entity.tickCount - latestArmorUnready) / 11);

						if (!hasPlayedUnready) {
							hasPlayedUnready = true;
							entity.playSound(TCorpSounds.PAPER_FLIP, 1F, (float) (0.8+entity.getRandom().nextFloat()*0.4f));
						}
					} else {
						latestArmorUnready = entity.tickCount;
						hasPlayedUnready = false;
					}

					renderAbility(ability, -13, entity, playerVariables, event, h, icon, flashProgress);
				}
			}

			if (!equippedItem.isEmpty()) { // Calculate Ability Widget
				ItemAbility ability = WeaponAbilityProcedure.getForItem(equippedItem.getItem());
				ResourceLocation icon = ability.getIconLocation(entity, playerVariables);

				if (icon != null) {
					float availability = ability.getAvailability(entity, playerVariables);
					float flashProgress = 0;
					if (availability >= 1) {
						flashProgress = 1 - ((float) (entity.tickCount - latestWeaponUnready) / 11);

						if (!hasPlayedWeaponUnready) {
							hasPlayedWeaponUnready = true;
							entity.playSound(TCorpSounds.PAPER_FLIP, 1,  (float) (0.8+entity.getRandom().nextFloat()*0.4f));
						}
					} else {
						latestWeaponUnready = entity.tickCount;
						hasPlayedWeaponUnready = false;
					}

					renderAbility(ability, -63, entity, playerVariables, event, h, icon, flashProgress);
				}
			}


            if (BlipwarninghandlerProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity)).collect(HashMap::new,
                    (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll))) {
                Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("tcorp:textures/screens/blip_warning.png"));
                blit(event.getMatrixStack(), posX + 94, posY + 98, 0, 0, 64, 20, 64, 20);
            }
            RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);



			// Overlays

			if (entity.hasEffect(Shell.get())) {
				int level = entity.getEffect(Shell.get()).getAmplifier();
				if (level > 4) level = 4;
				Minecraft.getInstance().getTextureManager().bind(shellLeft[level]);
				blit(event.getMatrixStack(), 0, 0, 0, 0, (int)(60 * ((float)h/90)), h, (int)(60 * ((float)h/90)), h);

				Minecraft.getInstance().getTextureManager().bind(shellRight[level]);
				blit(event.getMatrixStack(), w - (int)(60 * ((float)h/90)), 0, 0, 0, (int)(60 * ((float)h/90)), h, (int)(60 * ((float)h/90)), h);

			}


		}
	}

	private static void renderHealth() {

	}

	private static void renderStatisticUI(int offsetY, int offsetX, PlayerEntity player, TcorpModVariables.PlayerVariables playerVariables, RenderGameOverlayEvent.Post event) {
		double targetPercent = player.getHealth() != 0 ? player.getHealth() : -1;
		double targetStagger = playerVariables.stagger != 0 ? playerVariables.stagger : -1;

		if (lastRenderedTick != player.tickCount) {
			if (player.getHealth() != 0)
				targetPercent = player.getHealth() / player.getMaxHealth();

			if (playerVariables.maxStagger != 0)
				targetStagger = playerVariables.stagger / playerVariables.maxStagger;

			// Calc Health
			if (currentHealthPercent == -1)
				currentHealthPercent = targetPercent;
			else {
				if (Math.abs(currentHealthPercent - targetPercent) < 0.01) {
					currentHealthPercent = targetPercent;
				} else {
					currentHealthPercent = (currentHealthPercent * 3 + targetPercent) / 4;
				}
			}
			// Calc Stagger
			if (currentStaggerPercent == -1)
				currentStaggerPercent = targetStagger;
			else {
				if (Math.abs(currentStaggerPercent - targetStagger) < 0.01) {
					currentStaggerPercent = targetStagger;
				} else {
					currentStaggerPercent = (currentStaggerPercent * 3 + targetStagger) / 4;
				}
			}

			// Calc Bright Health
			if (currentBrightHealthPercent == -1)
				currentBrightHealthPercent = targetPercent;
			else {
				if (Math.abs(currentBrightHealthPercent - targetPercent) < 0.01) {
					currentBrightHealthPercent = targetPercent;
				} else {
					currentBrightHealthPercent = (currentBrightHealthPercent * 8 + targetPercent) / 9;
				}
			}

			// Calc Bright Stagger
			if (currentBrightStaggerPercent == -1)
				currentBrightStaggerPercent = targetStagger;
			else {
				if (Math.abs(currentBrightStaggerPercent - targetStagger) < 0.01) {
					currentBrightStaggerPercent = targetStagger;
				} else {
					currentBrightStaggerPercent = (currentBrightStaggerPercent * 8 + targetStagger) / 9;
				}
			}

			lastRenderedTick = player.tickCount;
		}



		Minecraft.getInstance().getTextureManager().bind(base_ui);
		blit(event.getMatrixStack(), offsetX, offsetY, 0, 0, 200, 48, 200, 48);

		if (targetPercent != -1) {
			if (currentHealthPercent <= currentBrightHealthPercent) {
				Minecraft.getInstance().getTextureManager().bind(healthbar_b);
				blit(event.getMatrixStack(), offsetX+ 29, offsetY + 8, 0, 0, (int)(154 * currentBrightHealthPercent), 11, 154, 11);
			}
			Minecraft.getInstance().getTextureManager().bind(healthbar);
			blit(event.getMatrixStack(), offsetX+ 29, offsetY + 8, 0, 0, (int)(154 * currentHealthPercent), 11, 154, 11);
		}

		if (targetPercent != -1) {
			if (currentHealthPercent <= currentBrightHealthPercent) {
				Minecraft.getInstance().getTextureManager().bind(staggerbar_b);
				blit(event.getMatrixStack(), offsetX+ 29, offsetY + 31, 0, 0, (int)(136 * currentBrightStaggerPercent), 8, 136, 11);
			}
			Minecraft.getInstance().getTextureManager().bind(staggerbar);
			blit(event.getMatrixStack(), offsetX+ 29, offsetY + 31, 0, 0, (int)(136 * currentStaggerPercent), 8, 136, 11);
		}


		renderShellOverlayBar(offsetX, offsetY, player, event);
	}


	private static void renderShellOverlayBar(int offsetX, int offsetY, PlayerEntity player,  RenderGameOverlayEvent.Post event) {
		if (player.hasEffect(Shell.get())) {
			Minecraft.getInstance().getTextureManager().bind(shellHealthbar[Math.min(player.getEffect(Shell.get()).getAmplifier(),4)]);
			blit(event.getMatrixStack(), offsetX+ 29, offsetY + 8, 0, 0, 154, 11, 154, 11);
		}
	}

	private static void renderAbility(ItemAbility ability, int offsetY, PlayerEntity entity, TcorpModVariables.PlayerVariables playerVariables, RenderGameOverlayEvent.Post event, int h, ResourceLocation icon, float flashProgress) {

		float availability = ability.getAvailability(entity, playerVariables);



		if (icon != null) {
			GL11.glPushMatrix();

			GL11.glTranslated(0,offsetY,0);
			if (flashProgress > 0) {
				GL11.glTranslated(32 * flashProgress,-20 * flashProgress,0);
				GL11.glRotated(10 * flashProgress, 0,0,1);
				GL11.glScaled(1.2 + 0.15f * flashProgress,1.2f + 0.15f * flashProgress,1.2f + 0.15f * flashProgress);
			} else {
				if (availability < 1) {
					GL11.glTranslated(32,-10,0);
					GL11.glRotated(10, 0,0,1);
					GL11.glScaled(1.2f,1.2f,1.2f);
				} else {
					GL11.glScaled(1.2f,1.2f,1.2f);
				}
			}


			Minecraft.getInstance().getTextureManager().bind(AbilityTier.baseBG);
			blit(event.getMatrixStack(), 10, (int)(h / 1.2f) - 40  - 48, 0, 0, 64, 48, 64, 48);
			if (availability >= 1) {
				Minecraft.getInstance().getTextureManager().bind(ability.getAbilityTier().getBackground());
				blit(event.getMatrixStack(), 10, (int)(h / 1.2f) - 40  - 48, 0, 0, 64, 48, 64, 48);
			}
			else if ( availability > 0) {
				Minecraft.getInstance().getTextureManager().bind(ability.getAbilityTier().getBackground());
				blit(event.getMatrixStack(), 10, (int)(h / 1.2 - 40 - Math.ceil(48 * availability)), 0, (int)(48 - 48 * availability), 64, (int)(48 * (availability)), 64, 48);
			}
			Minecraft.getInstance().getTextureManager().bind(icon);
			blit(event.getMatrixStack(), 10, (int)(h / 1.2f) - 40  - 48, 0, 0, 64, 48, 64, 48);
			GL11.glPushMatrix();
			GL11.glScaled(0.5f,0.5f,0.5f);
			GL11.glTranslated(26,  h * 1.7-131,0); // -265,245
			GL11.glRotated(-67,0,0,1);
			Minecraft.getInstance().font.draw(event.getMatrixStack(), ability.getName(entity, playerVariables), 0, 0, 0);
			GL11.glPushMatrix();
			GL11.glScaled(0.8f,0.8f,0.8f);
			GL11.glTranslated(30,  120,0); // -265,245
			GL11.glRotated(-13,0,0,1);
			Minecraft.getInstance().font.draw(event.getMatrixStack(), ability.getBlipCost(entity, playerVariables) + " Energy", 0, 0, 16773613);
			GL11.glPopMatrix();
			GL11.glPopMatrix();



			GL11.glPopMatrix();
		}
	}
}
