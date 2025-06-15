package net.m3tte.ego_weapons.procedures.abilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.procedures.abilities.armorAbilities.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class ArmorAbilityProcedure {

	private static Map<Item, ItemAbility> armorAbilities;

	private static Map<Item, ItemAbility> getArmorAbilities() {
		if (armorAbilities == null) {
			setupArmorAbilities();
		}
		return armorAbilities;
	}

	public static ItemAbility getForItem(Item item) {
		return getArmorAbilities().getOrDefault(item, new ItemAbility());
	}

	public static void setupArmorAbilities() {
		armorAbilities = new HashMap<>();

		// armorAbilities.put(CursedlabcoatItem.body.getItem(), new CursedLabCoatArmorAbility());

		// armorAbilities.put(HeavensprotectorItem.body.getItem(), new HeavensProtectorArmorAbility());

		// armorAbilities.put(JustiziaarmorItem.body.getItem(), new JustiziaArmorAbility());

		// armorAbilities.put(BetasuppressionsuitItem.body.getItem(), new BetaSuitArmorAbility());

		armorAbilities.put(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get(), new BlackSilenceArmorAbility());

		// armorAbilities.put(GalaxygarbItem.body.getItem(), new GalaxyGarbArmorAbility());

		// armorAbilities.put(CrimsonkimonoItem.body.getItem(), new CrimsonKimonoArmorAbility());

		armorAbilities.put(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get(), new RedMistArmorAbility());
		armorAbilities.put(EgoWeaponsItems.MIMICRY_CHESTPLATE.get(), new MimicryArmorAbility());
		armorAbilities.put(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get(), new RedMistEgoArmorAbility());
		armorAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_CLOAK.get(), new SolemnLamentArmorAbility());
		armorAbilities.put(EgoWeaponsItems.SUNSHOWER_CLOAK.get(), new SunshowerArmorAbility());
		armorAbilities.put(EgoWeaponsItems.OEUFI_CHESTPLATE.get(), new ObligationFullfillment());
		armorAbilities.put(EgoWeaponsItems.FULLSTOP_REP_CLOAK.get(), new FullstopAssistFire());
		armorAbilities.put(EgoWeaponsItems.FULLSTOP_SNIPER_SUIT.get(), new FullstopSniperFocus());
		armorAbilities.put(EgoWeaponsItems.MAGIC_BULLET_CLOAK.get(), new MagicBulletPipe());
		armorAbilities.put(EgoWeaponsItems.FIREFIST_SUIT.get(), new FirefistIgniteFuel());
		armorAbilities.put(EgoWeaponsItems.LIU_SOUTH_6_CHESTPLATE.get(), new FocusEmbers());
		armorAbilities.put(EgoWeaponsItems.STIGMA_WORKSHOP_SUIT.get(), new RuefulEventide());
		armorAbilities.put(EgoWeaponsItems.HEISHOU_MAO_ROBE.get(), new TriggerStriderMao());

	}

	public static void runArmorAbility(PlayerEntity entity) {
		EgoWeaponsModVars.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

		Item chestItem = entity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		getForItem(chestItem).trigger(entity,playerVars);
	}
}
