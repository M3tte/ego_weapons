
package net.m3tte.ego_weapons.item.mimicry;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.movesets.MimicryMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.legacy.MimicryhitentityProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.execFunctions.HitProcedure.hitStunEffect;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.pummelDownEntity;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class MimicryItem extends EgoWeaponsWeapon {

	private static IItemTier mimicryTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.2f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 14;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 2;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}

	};

	public MimicryItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(mimicryTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	public void appendHoverTextALT(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		list.add(new StringTextComponent("I can't stop here...").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 7) + 1) + "/7] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.1"));
		list.add(new StringTextComponent(" "));

		switch (EgoWeaponsKeybinds.getUiPage() % 7) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{});
				else
					generateDescription(list, "mimicry", "passive", 1);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "offense_down", "defense_down"});
				else
					generateDescription(list,"kali_mimicry", "auto", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "defense_down", "offense_down"});
				else
					generateDescription(list,"kali_mimicry", "innate", 8);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "defense_down"});
				else
					generateDescription(list,"kali_mimicry", "ability", 6);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "rupture"});
				else {
					generateDescription(list,"kali_mimicry", "dash", 5);
				}
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "offense_down"});
				else {
					generateDescription(list,"kali_mimicry", "guard", 3);
				}
				break;
			case 6:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "rupture", "offense_down"});
				else {
					generateDescription(list,"kali_mimicry", "jump", 7);
				}
				break;
		}
		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);

		if (Minecraft.getInstance().player != null) {
			Item chestItem = Minecraft.getInstance().player.getItemBySlot(EquipmentSlotType.CHEST).getItem();
			if (chestItem.equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) || chestItem.equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get())) {
				appendHoverTextALT(itemstack, world, list, flag);
				return;
			}
		}

		list.add(new StringTextComponent("And the many shells cried out one word...").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
		list.add(new StringTextComponent(" "));

		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{});
				else
					generateDescription(list, "mimicry", "passive", 1);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "offense_up", "imitation"});
				else
					generateDescription(list,"mimicry", "auto", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "defense_up", "bleed", "imitation"});
				else
					generateDescription(list,"mimicry", "innate", 6);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed"});
				else
					generateDescription(list,"mimicry", "jump", 2);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "defense_down", "imitation"});
				else {
					generateDescription(list,"mimicry", "ability", 10);
				}
				break;
		}
		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();
		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();
		if (currentanim instanceof StaticAnimation) {
			String weaponIdentifier = currentanim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
			String secondIdentifier = "";
			if (currentanim instanceof AttackAnimation) {
				AttackAnimation.Phase phase = ((AttackAnimation)currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());

				if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
					EgoAttackAnimation.EgoAttackPhase modPhase = (EgoAttackAnimation.EgoAttackPhase) phase;

					secondIdentifier = modPhase.getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse("");
				}
			}



			switch (weaponIdentifier) {
				case "kali_dash":
					EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
					if (entityData != null) {
						if (entityData.light > 2) {
							entityData.light -= 2;
							entitypatch.playAnimationSynchronized(MimicryMovesetAnims.KALI_REND, 0);

							entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
						}
					}
					break;
				case "kali_rend":
					EgoWeaponsEffects.BLEED.get().increment(target, 1, 1);
					EgoWeaponsEffects.RUPTURE.get().increment(target, 1, 1);

					break;
				case "kali_jump":
					if (target.hasEffect(EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get())) {
						entitypatch.playAnimationSynchronized(MimicryMovesetAnims.KALI_IMPACT, 0);
					}

					LivingEntityPatch<?> targetentitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

					if (targetentitypatch != null) {
						pummelDownEntity(targetentitypatch, 2);
					}

					EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
					entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
					break;
				case "kali_impact":
					EntityTick.regenerateLight((PlayerEntity) sourceentity, entityData, 1, true);
					EgoWeaponsEffects.BLEED.get().increment(target, 1, 1);
					EgoWeaponsEffects.RUPTURE.get().increment(target, 1, 1);
					EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().decrement(target, 0, 1);

					break;
				case "kali_auto_1":
				case "kali_ego_auto_1":
					EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().increment(target, 0, 1);
					break;
				case "kali_auto_2":
				case "kali_ego_auto_2":
				case "kali_auto_3":
					EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
					break;
				case "kali_auto_4":

					if (entitypatch instanceof PlayerPatch) {
						((PlayerPatch<?>)entitypatch).setStamina(Math.min(((PlayerPatch<?>)entitypatch).getStamina() + 1f, ((PlayerPatch<?>)entitypatch).getMaxStamina()));
					}


					sourceentity.heal(1);
					EgoWeaponsEffects.BLEED.get().increment(target, 1, 1);
					EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 0, 1);
					break;
				case "kali_great_split_horizontal":
					if (sourceentity instanceof PlayerEntity) {
						if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
							entitypatch.playSound(EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_HIT, 1,1);
						}
					}
					if (entitypatch instanceof PlayerPatch) {
						((PlayerPatch<?>)entitypatch).setStamina(Math.min(((PlayerPatch<?>)entitypatch).getStamina() + 0.8f, ((PlayerPatch<?>)entitypatch).getMaxStamina()));
					}

					if (secondIdentifier.equals("kali_great_split_horizontal_1")) {
						EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 0, 1);
						if (entityData.globalcooldown <= 0) {
							EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(sourceentity, 0, 1);
						}
					} else {
						EgoWeaponsEffects.BLEED.get().increment(target, 3, 5);
					}
					break;

				case "kali_great_split_vertical":
						switch (secondIdentifier) {
							case "kali_great_split_vertical_1":
							case "kali_great_split_vertical_2":
								EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
								EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 0, 2);
								break;
							case "kali_great_split_vertical_3":
								EgoWeaponsEffects.BLEED.get().increment(target, 1, 1);
								EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().increment(target, 0, 2);
								break;
						}
					break;
				case "mimicry_auto_1":
				case "mimicry_auto_2":
					EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
					break;
				case "mimicry_auto_3":
					EgoWeaponsEffects.BLEED.get().increment(target, 1, 1);
					EgoWeaponsEffects.IMITATION.get().increment(sourceentity, 0, 1);
					break;
				case "mimicry_hello":
					if (entityData.globalcooldown <= 0) {
						entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_BLUNT, 1,1);
					}
					LivingEntityPatch<?> patch = (LivingEntityPatch)target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
					if (patch != null) {
						hitStunEffect(patch, 0f);
					}

					break;
				case "mimicry_goodbye":
					EgoWeaponsEffects.BLEED.get().increment(target, 3, 3);



					int potency = Math.min(EgoWeaponsEffects.BLEED.get().getPotency(target) / 3,5);

					if (potency > 0) {
						EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 0, potency);
					}
					break;

				case "mimicry_goodbye_enh":
					EgoWeaponsEffects.BLEED.get().increment(target, 3, 8);

					if (entityData.globalcooldown <= 0) {
						entitypatch.playSound(EgoWeaponsSounds.HEAVY_SLASH, 1,1);
					}

					int potency2 = Math.min(EgoWeaponsEffects.BLEED.get().getPotency(target) / 3,5);

					if (potency2 > 0) {
						EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 0, potency2);
					}
					break;
			}




		}

		/*
		Migrated to damage calculation
		int healFactor = EgoWeaponsItems.MIMICRY_CHESTPLATE.get().equals(sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem()) ? 2 : 1;

		if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
			sourceentity.heal(1);
			((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
			entityData.globalcooldown = 5;
		}

		if ((chestItem.equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) || chestItem.equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get())) && entityData != null) {

		 */



		entityData.syncPlayerVariables(sourceentity);


		MimicryhitentityProcedure.executeProcedure(Stream
				.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("entity", target),
						new AbstractMap.SimpleEntry<>("sourceentity", sourceentity), new AbstractMap.SimpleEntry<>("itemstack", itemstack))
				.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		return retval;
	}

	public static float modifyDamageNormal(LivingEntity target, LivingEntity source, float amount, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (currentanim != null && entityData != null) {
			String weaponIdentifier = currentanim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			switch (weaponIdentifier) {
				case "mimicry_hello":
					float potency = Math.min(EgoWeaponsEffects.BLEED.get().getPotency(target) / 2f,4);
					EgoWeaponsEffects.DEFENSE_LEVEL_UP.get().increment(source, 4, 4);
					EgoWeaponsEffects.IMITATION.get().increment(source, 0, 2);
					if (potency > 0) {
						if (entityData.globalcooldown <= 0) {
							source.heal(potency);

						}
					}
					break;
				case "mimicry_goodbye_enh":

				case "mimicry_goodbye":
					SharedFunctions.incrementBonusDamage(damageSource, 0.5f);
					amount += 0.5f;
					float potencyv = Math.min(EgoWeaponsEffects.BLEED.get().getPotency(target) * 0.02f,0.4f);

					if (potencyv > 0) {
						SharedFunctions.incrementBonusDamage(damageSource, potencyv);
						amount += potencyv;
					}
					break;

				case "kali_great_split_vertical":

					float gsvPot = Math.min(EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().getPotency(target) * 0.1f,0.5f);

					if (gsvPot > 0) {
						SharedFunctions.incrementBonusDamage(damageSource, gsvPot);
						amount += gsvPot;
					}
					break;
				case "kali_great_split_horizontal":

					float gshPot = Math.min(EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().getPotency(target) * 0.05f,0.3f);

					if (gshPot > 0) {
						SharedFunctions.incrementBonusDamage(damageSource, gshPot);
						amount += gshPot;
					}
					break;
			}
		}

		return amount;
	}

	public static void getDamageHeal(LivingEntity target, LivingEntity source, float amount, DamageSource damageSource) {
		// Heal Calc is here now.
		int healFactor = EgoWeaponsItems.MIMICRY_CHESTPLATE.get().equals(source.getItemBySlot(EquipmentSlotType.CHEST).getItem()) ? 2 : 1;
		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);



		// Applies cooldown after. Locks all other on hit effects from triggering as well.
		if (entityData != null) {
			if (entityData.globalcooldown <= 0) {
				source.heal(Math.min(1.5f, amount * 0.07f) * healFactor);
				entityData.globalcooldown = 5;
				entityData.syncPlayerVariables(source);
			}
		}
	}





}
