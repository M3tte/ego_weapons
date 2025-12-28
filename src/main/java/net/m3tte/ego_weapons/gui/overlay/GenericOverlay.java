
package net.m3tte.ego_weapons.gui.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gui.UIBubble;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.potion.countEffects.Shell;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.procedures.legacy.BlipwarninghandlerProcedure;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.WeaponAbilityProcedure;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.abilities.ArmorAbilityProcedure;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.ai.attributes.Attributes;
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
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import yesman.epicfight.client.gui.ModIngameGui;

import java.util.*;
import java.util.stream.Collectors;
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
	static ResourceLocation EMPTY_BULLET = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/empty_handgun.png");
	static ResourceLocation EMPTY_RIFLE_BULLET = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/empty_rifle.png");
	static ResourceLocation DEPARTED_BULLET = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/the_departed_bullet.png");
	static ResourceLocation LIVING_BULLET = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/gui/bullets/the_living_bullet.png");
	static long lastRenderedTick = 0;
	static boolean hasPlayedWeaponUnready = false;


	final static ResourceLocation base_ui = new ResourceLocation("ego_weapons:textures/screens/gui/base_ui.png");
	final static ResourceLocation healthbar = new ResourceLocation("ego_weapons:textures/screens/gui/healthbar.png");
	final static ResourceLocation creativehealthbaroverlay = new ResourceLocation("ego_weapons:textures/screens/gui/creative.png");
	final static ResourceLocation shieldbar = new ResourceLocation("ego_weapons:textures/screens/gui/shieldbar_anim.png");
	final static ResourceLocation healthbar_b = new ResourceLocation("ego_weapons:textures/screens/gui/healthbar_bright.png");
	final static ResourceLocation staggerbar = new ResourceLocation("ego_weapons:textures/screens/gui/staggerbar.png");
	final static ResourceLocation staggerbar_b = new ResourceLocation("ego_weapons:textures/screens/gui/staggerbar_bright.png");
	final static ResourceLocation magic_bullet = new ResourceLocation("ego_weapons:textures/screens/gui/magic_bullet_icon.png");
	final static ResourceLocation defense_level = new ResourceLocation("ego_weapons:textures/screens/gui/defense_level.png");
	final static ResourceLocation sanity = new ResourceLocation("ego_weapons:textures/screens/gui/sanitybar.png");
	final static ResourceLocation sanity_b = new ResourceLocation("ego_weapons:textures/screens/gui/sanitybar_bright.png");
	final static ResourceLocation emotion_bar = new ResourceLocation("ego_weapons:textures/screens/gui/emotion_bar.png");

	final static ResourceLocation d10fuelbg = new ResourceLocation("ego_weapons:textures/screens/gui/firefist/fuel_tank.png");
	final static ResourceLocation d10fuel = new ResourceLocation("ego_weapons:textures/screens/gui/firefist/district_10_fuel.png");
	final static ResourceLocation d10fuel_ignited = new ResourceLocation("ego_weapons:textures/screens/gui/firefist/burning_district_10_fuel.png");


	static ResourceLocation[] shellLeft = parseMultiStateTexture(1 ,5, "overlay/shell/shell_left");
	static ResourceLocation[] shellRight = parseMultiStateTexture(1 ,5, "overlay/shell/shell_right");
	static ResourceLocation[] shellHealthbar = parseMultiStateTexture(1 ,5, "overlay/shell/ui/healthbar_shell");
	static ResourceLocation[] emotionLevels = parseMultiStateTexture(0 ,5, "gui/emotion");

	static ResourceLocation[] trumpetLeft = parseMultiStateTexture(1, 4, "overlay/warnings/trumpet");
	static ResourceLocation[] trumpetRight = parseMultiStateTexture(1, 4, "overlay/warnings/trumpet","right");

	static ResourceLocation[] trumpetTextLeft = parseMultiStateTexture(1, 4, "overlay/warnings/texts/trumpet");
	static ResourceLocation[] trumpetTextRight = parseMultiStateTexture(1, 4, "overlay/warnings/texts/trumpet","inv");


	private static ResourceLocation[] parseMultiStateTexture(int min, int max, String prefix) {
		ResourceLocation[] array = new ResourceLocation[max - min + 1];

		int index = 0;
		for (int x = min; x <= max; x++) {
			array[index] = new ResourceLocation("ego_weapons:textures/screens/"+prefix+"_"+x+".png");
			index++;
		}
		return array;
	}

	private static ResourceLocation[] parseMultiStateTexture(int min, int max, String prefix, String suffix) {
		ResourceLocation[] array = new ResourceLocation[max - min + 1];

		int index = 0;
		for (int x = min; x <= max; x++) {
			array[index] = new ResourceLocation("ego_weapons:textures/screens/"+prefix+"_"+x+"_"+suffix+".png");
			index++;
		}
		return array;
	}
	static boolean drawnThisFrame = false;
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void preOverlay(RenderGameOverlayEvent.Pre event) {


		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
			drawnThisFrame = false;
			return;
		}

		if (Minecraft.getInstance().options.hideGui || drawnThisFrame)
			return;


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

		EgoWeaponsModVars.PlayerVariables playerVariables = entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());
		int energy = (int) playerVariables.light;

		//Fetch Render System

		GL11.glPushMatrix();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();

		MatrixStack matStack = event.getMatrixStack();

		// Blip UI

			/*matStack.pushPose();
			matStack.scale(2F, 2F, 2.0F);
			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/power_blip.png"));
			ModIngameGui.blit(matStack, posX , posY, 55, 55, 3, 9, 3, 9);
			matStack.popPose();*/


		//Minecraft.getInstance().font.draw(event.getMatrixStack(), "TCorp - Indev - Bugs are to be expected", 30, 20, -1);

		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blip_background.png"));
		blit(event.getMatrixStack(), w - (posX / 3) - 30, posY + 98, 0, 0, 64, 20, 64, 20);
		Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/power_blip.png"));
		for (int sc = 0; sc < Math.min(energy, 10); sc++) {
			blit(event.getMatrixStack(), w - (posX / 3) + 4 - 30 + sc * 6, posY + 102, 0, 0, 3, 9, 3, 9);
		}
		if (energy > 10) {
			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/golden_blip.png"));
			for (int sc = 10; sc < Math.min(energy, 20); sc++) {
				blit(event.getMatrixStack(), w - (posX / 3) + 3 - 30 + (sc - 10) * 6, posY + 101, 0, 0, 5, 11, 5, 11);
			}
		}
		ItemStack equippedArmor = entity.getItemBySlot(EquipmentSlotType.CHEST);
		ItemStack equippedItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND);

		// Main UI

		// UI

		renderStatisticUI(h-70, 2, entity, playerVariables, event);

		renderEmotionLevel(h-70, 2, entity, playerVariables, event);

		renderSolemnLament(w - 50, posY+ 78, entity, event);

		if (entity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof GunItem)
			renderGunAmmo(w - 40, posY+ 78, entity, event, entity.getItemInHand(Hand.MAIN_HAND));

		if (entity.getItemInHand(Hand.MAIN_HAND).getItem() instanceof GunItem)
			renderGunAmmo(w- 60, posY+ 78, entity, event, entity.getItemInHand(Hand.OFF_HAND));

		// Calculate Widget for D10 Fuel
		if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get()))
			renderDistrict10Fuel(w - 40, posY, entity, event, entity.getItemInHand(Hand.MAIN_HAND));

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
						entity.playSound(EgoWeaponsSounds.PAPER_FLIP, 1F, (float) (0.8+entity.getRandom().nextFloat()*0.4f));
					}
				} else {
					latestArmorUnready = entity.tickCount;
					hasPlayedUnready = false;
				}

				renderAbility(ability, -13, entity, playerVariables, event, h, icon, overlay, flashProgress);
			}
		}

		if (!equippedItem.isEmpty()) { // Calculate Ability Widget

			ItemAbility ability = null;

			if (entity.hasEffect(EgoWeaponsEffects.ASSIST_FIRE.get())) {
				ability = WeaponAbilityProcedure.getAssistForItem(equippedItem.getItem());
			}

			if (ability == null)
				ability = WeaponAbilityProcedure.getForItem(equippedItem.getItem());

			ResourceLocation icon = ability.getIconLocation(entity, playerVariables);
			ResourceLocation overlay = ability.getOverlay(entity, playerVariables);

			if (icon != null) {
				float availability = ability.getAvailability(entity, playerVariables);
				float flashProgress = 0;
				if (availability >= 1) {
					flashProgress = 1 - ((float) (entity.tickCount - latestWeaponUnready) / 11);

					if (!hasPlayedWeaponUnready) {
						hasPlayedWeaponUnready = true;
						entity.playSound(EgoWeaponsSounds.PAPER_FLIP, 1,  (float) (0.8+entity.getRandom().nextFloat()*0.4f));
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
			Minecraft.getInstance().getTextureManager().bind(new ResourceLocation("ego_weapons:textures/screens/blip_warning.png"));
			blit(event.getMatrixStack(), w - (posX / 3) - 30, posY + 98, 0, 0, 64, 20, 64, 20);
		}
		//

		if (entity.getItemInHand(Hand.MAIN_HAND).getItem().equals(EgoWeaponsItems.MAGIC_BULLET.get())) {
			renderMagicBullet(h / 2 - 10, 100, entity, event);
		}

		renderArmor(h - 32, 30, entity, event);

		// Overlays

		if (entity.hasEffect(Shell.get())) {
			int level = entity.getEffect(Shell.get()).getAmplifier();
			if (level > 4) level = 4;
			Minecraft.getInstance().getTextureManager().bind(shellLeft[level]);
			blit(event.getMatrixStack(), 0, 0, 0, 0, (int)(60 * ((float)h/90)), h, (int)(60 * ((float)h/90)), h);

			Minecraft.getInstance().getTextureManager().bind(shellRight[level]);
			blit(event.getMatrixStack(), w - (int)(60 * ((float)h/90)), 0, 0, 0, (int)(60 * ((float)h/90)), h, (int)(60 * ((float)h/90)), h);
		}

		renderWarningState(entity, event, w);

		drawnThisFrame = true;
		// End of the func
		GL11.glPopMatrix();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.defaultBlendFunc();
	}


	private static void renderWarningState(PlayerEntity player, RenderGameOverlayEvent.Pre event, int width) {

		World level = player.level;

		float scale = 2.2f;

		float fadeTickMult = (float) (player.tickCount%31) / 30f;

		float warningState = EgoWeaponsModVars.MapVariables.get(level).globalWarningTension;
		if (warningState >= 1) {
			warningState = Math.min(warningState - 1, 3);


			int defSize = scaleInt(120,scale);
			int defSizePart = scaleInt(48,scale);


			Minecraft.getInstance().getTextureManager().bind(trumpetLeft[(int)warningState]);
			blit(event.getMatrixStack(), 0, 0, 0, 0, defSize, defSizePart, defSize, defSizePart);
			Minecraft.getInstance().getTextureManager().bind(trumpetRight[(int)warningState]);
			blit(event.getMatrixStack(), width - defSize, 0, 0, 0, defSize, defSizePart, defSize, defSizePart);


			defSize = scaleInt(64,scale);
			defSizePart = scaleInt(42,scale);
			int halfSize = defSize / 2;

			Minecraft.getInstance().getTextureManager()
					.bind(trumpetTextLeft[(int)warningState]);
			blit(event.getMatrixStack(), -2, -1, halfSize * fadeTickMult, halfSize * (1-fadeTickMult), (int) defSizePart, defSizePart, (int) defSize, defSize);


			Minecraft.getInstance().getTextureManager()
					.bind(trumpetTextRight[(int)warningState]);
			blit(event.getMatrixStack(), (int) (width-halfSize-3), -6, halfSize * (1-fadeTickMult), halfSize * (1-fadeTickMult) , defSizePart, defSizePart, (int) defSize, defSize);
			String txt = "First";
			int color = 15519232;

			switch ((int)warningState) {
				case 1:
					txt = "Second";
					color = 16736512;
					break;
				case 2:
					txt = "Third";
					color = 12452608;
					break;

				case 3:
					txt = "Fourth";
					color = 3006207;
					break;
			}

			GL11.glPushMatrix();
			GL11.glTranslated(width - ((double) halfSize /4) - 5,  5,0); // -265,245
			GL11.glPushMatrix();
			GL11.glScaled(0.8f, 0.8f, 0.8f);
			GL11.glRotated(45,0,0,1);
			Minecraft.getInstance().font.draw(event.getMatrixStack(), txt, 0, 0, color);
			Minecraft.getInstance().font.draw(event.getMatrixStack(), "Trumpet", -2, 12, color);
			RenderSystem.enableBlend();
			GL11.glPopMatrix();
			GL11.glPopMatrix();

		}
	}



	private static int scaleInt(int input, float scale) {
		return (int) (input * scale);
	}

	private static void renderMagicBullet(int offsetY, int offsetX, PlayerEntity player, RenderGameOverlayEvent.Pre event) {
		int magicBullet = 0;

		if (player.hasEffect(EgoWeaponsEffects.MAGIC_BULLET.get())) {
			magicBullet = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(player);
		}

		GL11.glPushMatrix();
		GL11.glTranslated(offsetX, offsetY,0); // -265,245
		GL11.glRotated(7,0,0,1);
		Minecraft.getInstance().getTextureManager().bind(magic_bullet);
		blit(event.getMatrixStack(), 0, 0, 0, 0, 16, 16, 16, 16);
		Minecraft.getInstance().font.draw(event.getMatrixStack(), magicBullet+"", 6, 6,  (16773613));
		RenderSystem.enableBlend();
		GL11.glPopMatrix();
	}

	private static void renderArmor(int offsetY, int offsetX, PlayerEntity player, RenderGameOverlayEvent.Pre event) {
		double armor = 0;

		armor = player.getAttributeValue(Attributes.ARMOR);

		GL11.glPushMatrix();
		GL11.glTranslated(offsetX, offsetY,0); // -265,245
		GL11.glRotated(7,0,0,1);
		Minecraft.getInstance().getTextureManager().bind(defense_level);
		blit(event.getMatrixStack(), 0, 0, 0, 0, 26, 26, 26, 26);
		Minecraft.getInstance().font.draw(event.getMatrixStack(), ""+((armor % 1) == 0 ? ((int) armor) : (armor)), 16, 12,  (16773613));
		RenderSystem.enableBlend();
		GL11.glPopMatrix();
	}
	private static void renderEmotionLevel(int offsetY, int offsetX, PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVariables, RenderGameOverlayEvent.Pre event) {

		double emotionBarProgress = playerVariables.emotionLevelProgress / EmotionSystem.getEmotionRequired(playerVariables);

		if (emotionBarProgress > 1)
			emotionBarProgress = 1;

		if (playerVariables.emotionLevel >= 5) {
			emotionBarProgress = 1;
			playerVariables.emotionLevel = 5;
		}


		int offsets = (int)(35 * emotionBarProgress);
		if (emotionBarProgress > 0) {
			Minecraft.getInstance().getTextureManager().bind(emotion_bar);
			blit(event.getMatrixStack(), offsetX, offsetY + 6 + (35 - offsets), 0, 35 - offsets, 14, offsets, 14, 35);
		}


		Minecraft.getInstance().getTextureManager().bind(emotionLevels[playerVariables.emotionLevel]);
		blit(event.getMatrixStack(), offsetX + 4, offsetY + 6, 0, 0, 35, 35, 35, 35);

	}

	private static void renderStatisticUI(int offsetY, int offsetX, PlayerEntity player, EgoWeaponsModVars.PlayerVariables playerVariables, RenderGameOverlayEvent.Pre event) {
		double targetPercent = player.getHealth() != 0 ? player.getHealth() : -1;
		double targetStagger = playerVariables.stagger != 0 ? playerVariables.stagger : -1;
		double targetSanity = playerVariables.sanity != 0 ? playerVariables.sanity : -1;

		if (lastRenderedTick != player.tickCount) {
			// Calculate Percent
			if (player.getHealth() != 0)
				targetPercent = player.getHealth() / player.getMaxHealth();

			if (EgoWeaponsAttributes.getMaxStagger(player) != 0)
				targetStagger = playerVariables.stagger / EgoWeaponsAttributes.getMaxStagger(player);

			if (EgoWeaponsAttributes.getMaxSanity(player) != 0)
				targetSanity = playerVariables.sanity / EgoWeaponsAttributes.getMaxSanity(player);

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

		currentBrightSanityPercent = Math.min(1,currentBrightSanityPercent);
		currentSanityPercent = Math.min(1,currentSanityPercent);

		currentBrightStaggerPercent = Math.min(1,currentBrightStaggerPercent);
		currentStaggerPercent = Math.min(1,currentStaggerPercent);

		currentBrightHealthPercent = Math.min(1,currentBrightHealthPercent);
		currentHealthPercent = Math.min(1,currentHealthPercent);


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

		if (player.getAbsorptionAmount() > 0) {
			float ratio = (player.getAbsorptionAmount() / (player.getMaxHealth() + player.getAbsorptionAmount()));

			int time = (player.tickCount / 10) % 5;

			Minecraft.getInstance().getTextureManager().bind(shieldbar);
			blit(event.getMatrixStack(), offsetX+ 31, offsetY + 8, 0, 11 * time, (int)(154 * ratio * currentBrightHealthPercent), 11, 154, 55);

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

		renderHealthbarOverlays(offsetX, offsetY, player, event);
	}


	private static void renderHealthbarOverlays(int offsetX, int offsetY, PlayerEntity player, RenderGameOverlayEvent.Pre event) {
		if (player.hasEffect(Shell.get())) {
			Minecraft.getInstance().getTextureManager().bind(shellHealthbar[Math.max(Math.min(player.getEffect(Shell.get()).getAmplifier(),4),0)]);
			blit(event.getMatrixStack(), offsetX+ 29, offsetY + 8, 0, 0, 154, 11, 154, 11);
		}

		if (player.isCreative()) {
			Minecraft.getInstance().getTextureManager().bind(creativehealthbaroverlay);
			blit(event.getMatrixStack(), offsetX+ 31, offsetY + 8, 0, 0, 154, 11, 154, 11);
		}
	}


	private static final LinkedList<UIBubble> district10Bubbles = new LinkedList<>();
	private static float district10Value = 0;
	private static void renderDistrict10Fuel(int offsetX, int offsetY, PlayerEntity player, RenderGameOverlayEvent.Pre event, ItemStack item) {

		int district10Count = player.getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel");

		float targetQuantity = (float) district10Count / 100f;

		if (Math.abs(district10Value - targetQuantity) < 0.01) {
			district10Value = targetQuantity;
		} else {
			district10Value = (district10Value * 8 + targetQuantity) / 9;
		}

		int offset = (player.tickCount / 2) % (18);
		int offset2 = ((player.tickCount + 6) / 2) % (18);


		ResourceLocation texture = (district10Count > 50 && !player.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get())) ? d10fuel : d10fuel_ignited;

		Minecraft.getInstance().getTextureManager().bind(d10fuelbg);
		AbstractGui.blit(event.getMatrixStack(), offsetX, offsetY, 0, 0, 17, 83, 17, 83);

		// Minecraft.getInstance().getTextureManager().bind(d10fuelbg);
		// AbstractGui.blit(event.getMatrixStack(), offsetX, offsetY, 0, 0, 17, 83, 17, 83);

		if (district10Value > 0.01) {
			Minecraft.getInstance().getTextureManager().bind(texture);
			AbstractGui.blit(event.getMatrixStack(), offsetX + 3, offsetY + 9 + (int)(Math.ceil((71 * (1-district10Value)))), offset, 0, 11, (int)(71 * district10Value), 32, 71);
		}

		// AbstractGui.blit(event.getMatrixStack(), offsetX - 17 + 3, offsetY + 10 + ((int) (71 * (1-district10Value))), offset2, 0, 11, (int)(71 * district10Value), 32, 71);

		Random random = player.getRandom();

		if (player.tickCount % 8 == 0) {
			int size = 1 + random.nextInt(2);
			district10Bubbles.addFirst(new UIBubble(random.nextInt(9) + offsetX + 4,offsetY + 78, size, size, random.nextInt(20), random.nextInt(50)));
			//district10Bubbles.addFirst(new UIBubble(random.nextInt(11) + offsetX + 14,offsetY + 9 + 71, texture, random.nextInt(20), random.nextInt(20)));
		}

		if (district10Bubbles.size() > 40) {
			district10Bubbles.removeLast();
			//district10Bubbles.removeLast();
		}

		GL11.glPushMatrix();
		GL11.glTranslated(Math.sin(player.tickCount) * 0.5f, 0, 0);
		for (UIBubble bubble : district10Bubbles) {
			if (bubble.getLocationY() > offsetY + 9 + ((int) (71 * (1-district10Value)))) {
				AbstractGui.blit(event.getMatrixStack(), bubble.getLocationX(), bubble.getLocationY(), bubble.getTexX(), bubble.getTexY(), bubble.getSizeX(), bubble.getSizeY(), 32, 71);
				if (player.tickCount % 2 == 0)
					bubble.setLocationY(bubble.getLocationY()-1);
			}
		}

		GL11.glPopMatrix();
	}


	private static void renderGunAmmo(int offsetX, int offsetY, PlayerEntity player, RenderGameOverlayEvent.Pre event, ItemStack item) {

		if (!(item.getItem() instanceof GunItem))
			return;

		List<AmmoType> ammoList = Arrays.stream(item.getOrCreateTag().getIntArray("loadedAmmo")).mapToObj((i) -> AmmoType.values()[i]).collect(Collectors.toList());
		int maxAmmo = ((GunItem) item.getItem()).getMaxAmmo();

		int ammoIdx = 0;

		for (AmmoType ammo : ammoList) {
			Minecraft.getInstance().getTextureManager().bind(ammo.getAmmoTexture());
			AbstractGui.blit(event.getMatrixStack(), offsetX, offsetY - 10 * ammoIdx, 0, 0, 24, 24, 24, 24);

			ammoIdx++;
		}
		switch (((GunItem)item.getItem()).getCaliber()) {
			default:
				Minecraft.getInstance().getTextureManager().bind(EMPTY_BULLET);
				break;
			case SNIPER:
				Minecraft.getInstance().getTextureManager().bind(EMPTY_RIFLE_BULLET);
				break;
		}


		while (ammoIdx < maxAmmo) {
			AbstractGui.blit(event.getMatrixStack(), offsetX, offsetY - 10 * ammoIdx, 0, 0, 24, 24, 24, 24);
			ammoIdx++;
		}
	}

	private static void renderSolemnLament(int offsetX, int offsetY, PlayerEntity player, RenderGameOverlayEvent.Pre event) {


		Minecraft.getInstance().getTextureManager().bind(DEPARTED_BULLET);
		int departedcount = SolemnLamentEffects.getAmmoCount(player, SolemnLamentEffects.getDeparted());
		for (int x = 0; x < departedcount; x++) {
			AbstractGui.blit(event.getMatrixStack(), offsetX + 10, offsetY - 10 * x, 0, 0, 16, 16, 16, 16);
		}
		Minecraft.getInstance().getTextureManager().bind(LIVING_BULLET);
		int livingCount = SolemnLamentEffects.getAmmoCount(player, SolemnLamentEffects.getLiving());
		for (int x = 0; x < livingCount; x++) {
			AbstractGui.blit(event.getMatrixStack(), offsetX + 30, offsetY - 10 * x, 0, 0, 16, 16, 16, 16);
		}
	}


	private static void renderAbility(ItemAbility ability, int offsetY, PlayerEntity entity, EgoWeaponsModVars.PlayerVariables playerVariables, RenderGameOverlayEvent.Pre event, int h, ResourceLocation icon, ResourceLocation overlay, float flashProgress) {

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

			int missing = (int) Math.ceil(Math.max(0,cost - playerVariables.light));
			int present = (int) Math.min(playerVariables.light, cost);
			GL11.glPushMatrix();

				String abilityName = ability.getName(entity, playerVariables);
				String[] lines = null;
				float subTractor = Math.min(0.6f,abilityName.length() * 0.04f);
				GL11.glScaled(1.4f - subTractor,1.4f - subTractor,1.4f - subTractor);
				lines = abilityName.split("\\n");
				int position = (lines.length) * -2;

				for (String line : lines) {
					Minecraft.getInstance().font.draw(event.getMatrixStack(), line, 0, position, 0);
					position += 9;
				}

			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScaled(0.8f,0.8f,0.8f);
			GL11.glTranslated(30,  120,0); // -265,245
			GL11.glRotated(-13,0,0,1);
			GL11.glPushMatrix();
			float mult = 1.4f - 0.06f * cost;

			GL11.glScaled(mult,mult,mult);

			int breakpoint = 6;

			if (cost == 0) {
				Minecraft.getInstance().font.draw(event.getMatrixStack(), "\uE004", 0, 0,   50944);

			} else { // 4341827
				Minecraft.getInstance().font.draw(event.getMatrixStack(), StringUtils.repeat("\uE004",Math.min(breakpoint,present)), 0, 0,  16773613);

				if (present > breakpoint) {
					Minecraft.getInstance().font.draw(event.getMatrixStack(), StringUtils.repeat("\uE004",present - breakpoint), 0, 10,  16773613);
				}

				if (missing > 0) {

					int startPosX = present % (breakpoint);
					int startPosY = present / breakpoint;

					int charsLine1 = Math.min(breakpoint - startPosX, missing);

					Minecraft.getInstance().font.draw(event.getMatrixStack(), StringUtils.repeat("\uE004", charsLine1), startPosX * 9, startPosY * 10,  4341827);

					if (missing > charsLine1) {
						Minecraft.getInstance().font.draw(event.getMatrixStack(), StringUtils.repeat("\uE004",missing - charsLine1), 0, 10,  4341827);
					}
				}

			}
			RenderSystem.enableBlend();
			GL11.glPopMatrix();
			GL11.glPopMatrix();
			GL11.glPopMatrix();


			GL11.glPopMatrix();
		}
	}

}
