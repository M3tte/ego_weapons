package net.m3tte.ego_weapons.gameasset.abilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.abilities.reloadAbilities.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class ReloadAbilityProcedure {

	private static Map<Item, ReloadAbility> reloadAbilities;
	private static Map<Item, ReloadAbility> altReloadAbilities;

	private static Map<Item, ReloadAbility> getReloadAbilities() {
		if (reloadAbilities == null) {
			setupWeaponAbilities();
		}
		return reloadAbilities;
	}

	private static Map<Item, ReloadAbility> getAltReloadAbilities() {
		if (altReloadAbilities == null) {
			setupAltWeaponReloads();
		}
		return altReloadAbilities;
	}


	public static ReloadAbility NO_ABILITY = new ReloadAbility();

	public static ReloadAbility getForItem(Item item) {
		return getReloadAbilities().getOrDefault(item, NO_ABILITY);
	}

	public static ReloadAbility getAltForItem(Item item) {
		return getAltReloadAbilities().getOrDefault(item, NO_ABILITY);
	}

	public static void setupAltWeaponReloads() {
		altReloadAbilities = new HashMap<>();
		altReloadAbilities.put(EgoWeaponsItems.LCA_RIFLE.get(), new LCAReloadAbility());

	}
	public static void setupWeaponAbilities() {
		reloadAbilities = new HashMap<>();
		reloadAbilities.put(EgoWeaponsItems.FULLSTOP_REP_MACHETE.get(), new FullstopReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.FULLSTOP_REP_PISTOL.get(), new FullstopReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get(), new FullstopRifleReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.FIREFIST_GAUNTLET.get(), new FirefistReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_WHITE.get(), new SolemnLamentReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_BLACK.get(), new SolemnLamentReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.LCA_RIFLE.get(), new LCAReloadAbility());

	}

	public static void runAltReloadAbility(PlayerEntity entity) {
		EgoWeaponsModVars.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

		Item handItem = entity.getItemBySlot(EquipmentSlotType.OFFHAND).getItem();

		getAltForItem(handItem).trigger(entity,playerVars, ItemStack.EMPTY);
	}

	public static void runReloadAbility(PlayerEntity entity) {
		EgoWeaponsModVars.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

		Item handItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND).getItem();

		ReloadAbility mainAbility = getForItem(handItem);

		if (!mainAbility.equals(NO_ABILITY)) {
			mainAbility.trigger(entity,playerVars, ItemStack.EMPTY);
		} else {
			runAltReloadAbility(entity);
		}

	}
}
