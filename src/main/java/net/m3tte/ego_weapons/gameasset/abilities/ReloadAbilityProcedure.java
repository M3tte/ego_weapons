package net.m3tte.ego_weapons.gameasset.abilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.abilities.reloadAbilities.FirefistReloadAbility;
import net.m3tte.ego_weapons.gameasset.abilities.reloadAbilities.FullstopReloadAbility;
import net.m3tte.ego_weapons.gameasset.abilities.reloadAbilities.FullstopRifleReloadAbility;
import net.m3tte.ego_weapons.gameasset.abilities.reloadAbilities.SolemnLamentReloadAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class ReloadAbilityProcedure {

	private static Map<Item, ReloadAbility> reloadAbilities;

	private static Map<Item, ReloadAbility> getReloadAbilities() {
		if (reloadAbilities == null) {
			setupWeaponAbilities();
		}
		return reloadAbilities;
	}

	public static ReloadAbility getForItem(Item item) {
		return getReloadAbilities().getOrDefault(item, new ReloadAbility());
	}

	public static void setupWeaponAbilities() {
		reloadAbilities = new HashMap<>();
		reloadAbilities.put(EgoWeaponsItems.FULLSTOP_REP_MACHETE.get(), new FullstopReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.FULLSTOP_REP_PISTOL.get(), new FullstopReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get(), new FullstopRifleReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.FIREFIST_GAUNTLET.get(), new FirefistReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_WHITE.get(), new SolemnLamentReloadAbility());
		reloadAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_BLACK.get(), new SolemnLamentReloadAbility());

	}

	public static void runReloadAbility(PlayerEntity entity) {
		EgoWeaponsModVars.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

		Item handItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND).getItem();

		getForItem(handItem).trigger(entity,playerVars, ItemStack.EMPTY);

	}
}
