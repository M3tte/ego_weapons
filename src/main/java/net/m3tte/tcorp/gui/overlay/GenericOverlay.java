
package net.m3tte.tcorp.gui.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.item.magic_bullet.MagicBullet;
import net.m3tte.tcorp.potion.MagicBulletPotionEffect;
import net.m3tte.tcorp.potion.Shell;
import net.m3tte.tcorp.procedures.legacy.BlipwarninghandlerProcedure;
import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.m3tte.tcorp.procedures.abilities.WeaponAbilityProcedure;
import net.m3tte.tcorp.procedures.abilities.ItemAbility;
import net.m3tte.tcorp.procedures.abilities.ArmorAbilityProcedure;
import net.m3tte.tcorp.world.capabilities.EmotionSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
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


@Mod.EventBusSubscriber({Dist.CLIENT})
public class GenericOverlay extends ModIngameGui {

	static long latestArmorUnready = 0;
	static boolean hasPlayedUnready = false;

	static double currentHealthPercent = -1;
	static double currentBrightHealthPercent = -1;

	static double currentStaggerPercent = -1;
	static double currentBrightStaggerPercent = -1;

	static double currentSanityPercent = -1;
	static double currentBrightSanityPercent = -1;
	static long latestWeaponUnready = 0;


	static long lastRenderedTick = 0;
	static boolean hasPlayedWeaponUnready = false;


	final static ResourceLocation base_ui = new ResourceLocation("tcorp:textures/screens/gui/base_ui.png");
	final static ResourceLocation healthbar = new ResourceLocation("tcorp:textures/screens/gui/healthbar.png");
	final static ResourceLocation healthbar_b = new ResourceLocation("tcorp:textures/screens/gui/healthbar_bright.png");
	final static ResourceLocation staggerbar = new ResourceLocation("tcorp:textures/screens/gui/staggerbar.png");
	final static ResourceLocation staggerbar_b = new ResourceLocation("tcorp:textures/screens/gui/staggerbar_bright.png");
	final static ResourceLocation magic_bullet = new ResourceLocation("tcorp:textures/screens/gui/magic_bullet_icon.png");
	final static ResourceLocation sanity = new ResourceLocation("tcorp:textures/screens/gui/sanitybar.png");
	final static ResourceLocation sanity_b = new ResourceLocation("tcorp:textures/screens/gui/sanitybar_bright.png");
	final static ResourceLocation emotion_bar = new ResourceLocation("tcorp:textures/screens/gui/emotion_bar.png");


	static ResourceLocation[] shellLeft = parseMultiStateTexture(1 ,5, "overlay/shell/shell_left");
	static ResourceLocation[] shellRight = parseMultiStateTexture(1 ,5, "overlay/shell/shell_right");
	static ResourceLocation[] shellHealthbar = parseMultiStateTexture(1 ,5, "overlay/shell/ui/healthbar_shell");
	static ResourceLocation[] emotionLevels = parseMultiStateTexture(0 ,5, "gui/emotion");

	private static ResourceLocation[] parseMultiStateTexture(int min, int max, String prefix) {
		ResourceLocation[] array = new ResourceLocation[max - min + 1];

		int index = 0;
		for (int x = min; x <= max; x++) {
			array[index] = new ResourceLocation("tcorp:textures/screens/"+prefix+"_"+x+".png");
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
			for (int sc = 0; sc < Math.min(energy, 10); sc++) {
                blit(event.getMatrixStack(), posX + 98 + sc * 6, posY + 102, 0, 0, 3, 9, 3, 9);
            }
			if (energy > 10) {
				Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("tcorp:textures/screens/golden_blip.png"));
				for (int sc = 10; sc < Math.min(energy, 20); sc++) {
					blit(event.getMatrixStack(), posX + 97 + (sc - 10) * 6, posY + 101, 0, 0, 5, 11, 5, 11);
				}
			}
			ItemStack equippedArmor = entity.getItemBySlot(EquipmentSlotType.CHEST);
			ItemStack equippedItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND);

			// Main UI

			// UI

			renderStatisticUI(h-70, 2, entity, playerVariables, event);

			renderEmotionLevel(h-70, 2, entity, playerVariables, event);



			if (!equippedArmor.isEmpty()) { // Calculate Ability Widget
				ItemAbility ability = ArmorAbilityProcedure.getForItem(equippedArmor.getItem());
				ResourceLocation icon = ability.getIconLocation(entity, playerVariables);
				ResourceLocation overlay = ability.getOverlay(entity, playerVariables);

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

					renderAbility(ability, -13, entity, playerVariables, event, h, icon, overlay, flashProgress);
				}
			}

			if (!equippedItem.isEmpty()) { // Calculate Ability Widget
				ItemAbility ability = WeaponAbilityProcedure.getForItem(equippedItem.getItem());
				ResourceLocation icon = ability.getIconLocation(entity, playerVariables);
				ResourceLocation overlay = ability.getOverlay(entity, playerVariables);

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

					renderAbility(ability, -63, entity, playerVariables, event, h, icon, overlay, flashProgress);
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

			if (entity.getItemInHand(Hand.MAIN_HAND).getItem().equals(MagicBullet.item.getItem())) {
				renderMagicBullet(h / 2 - 10, 100, entity, event);
			}

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

	private static void renderMagicBullet(int offsetY, int offsetX, PlayerEntity player, RenderGameOverlayEvent.Post event) {
		int magicBullet = 0;

		if (player.hasEffect(MagicBulletPotionEffect.get())) {
			magicBullet = player.getEffect(MagicBulletPotionEffect.get()).getAmplifier() + 1;
		}

		GL11.glPushMatrix();
		GL11.glTranslated(offsetX, offsetY,0); // -265,245
		GL11.glRotated(7,0,0,1);
		Minecraft.getInstance().getTextureManager().bind(magic_bullet);
		blit(event.getMatrixStack(), 0, 0, 0, 0, 16, 16, 16, 16);
		Minecraft.getInstance().font.draw(event.getMatrixStack(), magicBullet+"", 6, 6,  (16773613));
		GL11.glPopMatrix();
	}
	private static void renderEmotionLevel(int offsetY, int offsetX, PlayerEntity player, TcorpModVariables.PlayerVariables playerVariables, RenderGameOverlayEvent.Post event) {

		double emotionBarProgress = playerVariables.emotionLevelProgress / EmotionSystem.getEmotionRequired(playerVariables);

		if (emotionBarProgress > 1)
			emotionBarProgress = 1;

		if (playerVariables.emotionLevel >= 5)
			emotionBarProgress = 1;

		int offsets = (int)(35 * emotionBarProgress);
		if (emotionBarProgress > 0) {
			Minecraft.getInstance().getTextureManager().bind(emotion_bar);
			blit(event.getMatrixStack(), offsetX, offsetY + 6 + (35 - offsets), 0, 35 - offsets, 14, offsets, 14, 35);
		}


		Minecraft.getInstance().getTextureManager().bind(emotionLevels[playerVariables.emotionLevel]);
		blit(event.getMatrixStack(), offsetX + 4, offsetY + 6, 0, 0, 35, 35, 35, 35);

	}

	private static void renderStatisticUI(int offsetY, int offsetX, PlayerEntity player, TcorpModVariables.PlayerVariables playerVariables, RenderGameOverlayEvent.Post event) {
		double targetPercent = player.getHealth() != 0 ? player.getHealth() : -1;
		double targetStagger = playerVariables.stagger != 0 ? playerVariables.stagger : -1;
		double targetSanity = playerVariables.sanity != 0 ? playerVariables.sanity : -1;

		if (lastRenderedTick != player.tickCount) {
			// Calculate Percent
			if (player.getHealth() != 0)
				targetPercent = player.getHealth() / player.getMaxHealth();

			if (playerVariables.maxStagger != 0)
				targetStagger = playerVariables.stagger / playerVariables.maxStagger;

			if (playerVariables.maxSanity != 0)
				targetSanity = playerVariables.sanity / playerVariables.maxSanity;

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

			// Calc Sanity
			if (currentSanityPercent == -1)
				currentSanityPercent = targetSanity;
			else {
				if (Math.abs(currentSanityPercent - targetSanity) < 0.01) {
					currentSanityPercent = targetSanity;
				} else {
					currentSanityPercent = (currentSanityPercent * 3 + targetSanity) / 4;
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

			// Calc Bright Sanity
			if (currentBrightSanityPercent == -1)
				currentBrightSanityPercent = targetSanity;
			else {
				if (Math.abs(currentBrightSanityPercent - targetSanity) < 0.01) {
					currentBrightSanityPercent = targetSanity;
				} else {
					currentBrightSanityPercent = (currentBrightSanityPercent * 8 + targetSanity) / 9;
				}
			}

			lastRenderedTick = player.tickCount;
		}



		Minecraft.getInstance().getTextureManager().bind(base_ui);
		blit(event.getMatrixStack(), offsetX, offsetY, 0, 0, 200, 48, 200, 48);

		if (targetPercent != -1) {
			if (currentHealthPercent <= currentBrightHealthPercent) {
				Minecraft.getInstance().getTextureManager().bind(healthbar_b);
				blit(event.getMatrixStack(), offsetX+ 31, offsetY + 8, 0, 0, (int)(154 * currentBrightHealthPercent), 11, 154, 11);
			}
			Minecraft.getInstance().getTextureManager().bind(healthbar);
			blit(event.getMatrixStack(), offsetX+ 31, offsetY + 8, 0, 0, (int)(154 * currentHealthPercent), 11, 154, 11);
		}

		if (targetStagger != -1) {
			if (currentStaggerPercent <= currentBrightStaggerPercent) {
				Minecraft.getInstance().getTextureManager().bind(staggerbar_b);
				blit(event.getMatrixStack(), offsetX+ 31, offsetY + 31, 0, 0, (int)(136 * currentBrightStaggerPercent), 8, 136, 8);
			}
			Minecraft.getInstance().getTextureManager().bind(staggerbar);
			blit(event.getMatrixStack(), offsetX+ 31, offsetY + 31, 0, 0, (int)(136 * currentStaggerPercent), 8, 136, 8);
		}

		if (targetSanity != -1) {
			if (currentSanityPercent <= currentBrightSanityPercent) {
				Minecraft.getInstance().getTextureManager().bind(sanity_b);
				blit(event.getMatrixStack(), offsetX+ 38, offsetY + 20, 0, 0, (int)(149 * currentBrightSanityPercent), 10, 149, 10);
			}
			Minecraft.getInstance().getTextureManager().bind(sanity);
			blit(event.getMatrixStack(), offsetX+ 38, offsetY + 20, 0, 0, (int)(149 * currentSanityPercent), 10, 149, 10);
		}
		renderShellOverlayBar(offsetX, offsetY, player, event);
	}


	private static void renderShellOverlayBar(int offsetX, int offsetY, PlayerEntity player,  RenderGameOverlayEvent.Post event) {
		if (player.hasEffect(Shell.get())) {
			Minecraft.getInstance().getTextureManager().bind(shellHealthbar[Math.max(Math.min(player.getEffect(Shell.get()).getAmplifier(),4),0)]);
			blit(event.getMatrixStack(), offsetX+ 29, offsetY + 8, 0, 0, 154, 11, 154, 11);
		}
	}

	private static void renderAbility(ItemAbility ability, int offsetY, PlayerEntity entity, TcorpModVariables.PlayerVariables playerVariables, RenderGameOverlayEvent.Post event, int h, ResourceLocation icon,ResourceLocation overlay,  float flashProgress) {

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
			if (overlay != null) {
				Minecraft.getInstance().getTextureManager().bind(overlay);
				blit(event.getMatrixStack(), 8, (int)(h / 1.2f) - 41  - 48, 0, 0, 68, 47, 68, 47);
			}

			GL11.glPushMatrix();
			GL11.glScaled(0.5f,0.5f,0.5f);
			GL11.glTranslated(26,  h * 1.7-131,0); // -265,245
			GL11.glRotated(-67,0,0,1);
			int cost = ability.getBlipCost(entity, playerVariables);
			Minecraft.getInstance().font.draw(event.getMatrixStack(), ability.getName(entity, playerVariables), 0, 0, 0);
			GL11.glPushMatrix();
			GL11.glScaled(0.8f,0.8f,0.8f);
			GL11.glTranslated(30,  120,0); // -265,245
			GL11.glRotated(-13,0,0,1);
			Minecraft.getInstance().font.draw(event.getMatrixStack(), cost + " Energy", 0, 0,  (cost == 0) ? 50944 : 16773613);
			GL11.glPopMatrix();
			GL11.glPopMatrix();



			GL11.glPopMatrix();
		}
	}
}
