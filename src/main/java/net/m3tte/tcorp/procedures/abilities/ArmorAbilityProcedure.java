package net.m3tte.tcorp.procedures.abilities;

import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.item.*;
import net.m3tte.tcorp.item.blackSilence.BlacksilencesuitItem;
import net.m3tte.tcorp.item.mimicry.MimicryarmorItem;
import net.m3tte.tcorp.item.redmist.RedMistEGOSuit;
import net.m3tte.tcorp.item.redmist.RedMistJacket;
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

		armorAbilities.put(CursedlabcoatItem.body.getItem(), new CursedLabCoatArmorAbility());

		armorAbilities.put(HeavensprotectorItem.body.getItem(), new HeavensProtectorArmorAbility());

		armorAbilities.put(JustiziaarmorItem.body.getItem(), new JustiziaArmorAbility());

		armorAbilities.put(BetasuppressionsuitItem.body.getItem(), new BetaSuitArmorAbility());

		armorAbilities.put(BlacksilencesuitItem.body.getItem(), new BlackSilenceArmorAbility());

		armorAbilities.put(GalaxygarbItem.body.getItem(), new GalaxyGarbArmorAbility());

		armorAbilities.put(CrimsonkimonoItem.body.getItem(), new CrimsonKimonoArmorAbility());

		armorAbilities.put(RedMistJacket.body.getItem(), new RedMistArmorAbility());

		armorAbilities.put(MimicryarmorItem.body.getItem(), new MimicryArmorAbility());

		armorAbilities.put(RedMistEGOSuit.body.getItem(), new RedMistEgoArmorAbility());

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
