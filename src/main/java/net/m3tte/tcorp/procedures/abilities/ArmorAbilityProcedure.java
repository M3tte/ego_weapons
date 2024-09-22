package net.m3tte.tcorp.procedures.abilities;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.item.*;
import net.m3tte.tcorp.item.blackSilence.BlackSilenceArmor;
import net.m3tte.tcorp.procedures.abilities.armorAbilities.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;

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

		armorAbilities.put(TCorpItems.SUIT_OF_THE_BLACK_SILENCE.get(), new BlackSilenceArmorAbility());

		// armorAbilities.put(GalaxygarbItem.body.getItem(), new GalaxyGarbArmorAbility());

		// armorAbilities.put(CrimsonkimonoItem.body.getItem(), new CrimsonKimonoArmorAbility());

		armorAbilities.put(TCorpItems.JACKET_OF_THE_RED_MIST.get(), new RedMistArmorAbility());

		armorAbilities.put(TCorpItems.MIMICRY_CHESTPLATE.get(), new MimicryArmorAbility());

		armorAbilities.put(TCorpItems.RED_MIST_EGO_CHESTPLATE.get(), new RedMistEgoArmorAbility());

		armorAbilities.put(TCorpItems.SOLEMN_LAMENT_CLOAK.get(), new SolemnLamentArmorAbility());

	}

	public static void executeArmorAbility(Map<String, Object> dependencies) {
		PlayerEntity entity = (PlayerEntity) dependencies.get("entity");
		TcorpModVariables.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

		Item chestItem = entity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		getForItem(chestItem).trigger(entity,playerVars);
	}
}
