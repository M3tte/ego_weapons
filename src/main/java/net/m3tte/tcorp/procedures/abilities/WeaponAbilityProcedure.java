package net.m3tte.tcorp.procedures.abilities;

import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.item.mimicry.MimicryItem;
import net.m3tte.tcorp.item.blackSilence.*;
import net.m3tte.tcorp.procedures.abilities.armorAbilities.ItemAbility;
import net.m3tte.tcorp.procedures.abilities.weaponAbilities.BlackSilenceWeaponAbility;
import net.m3tte.tcorp.procedures.abilities.weaponAbilities.MimicryWeaponAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;

public class WeaponAbilityProcedure {

	private static Map<Item, ItemAbility> weaponAbilities;

	private static Map<Item, ItemAbility> getWeaponAbilities() {
		if (weaponAbilities == null) {
			setupWeaponAbilities();
		}
		return weaponAbilities;
	}

	public static ItemAbility getForItem(Item item) {
		return getWeaponAbilities().getOrDefault(item, new ItemAbility());
	}

	public static void setupWeaponAbilities() {
		weaponAbilities = new HashMap<>();
		BlackSilenceWeaponAbility bsWeaponAb = new BlackSilenceWeaponAbility();
		weaponAbilities.put(DurandalItem.block, bsWeaponAb);
		weaponAbilities.put(ZelkovaaxeItem.block, bsWeaponAb);
		weaponAbilities.put(ZelkovamaceItem.block, bsWeaponAb);
		weaponAbilities.put(AtelierlogicpistolsItem.block, bsWeaponAb);
		weaponAbilities.put(AtelierlogicshotgunItem.block, bsWeaponAb);
		weaponAbilities.put(WheelsIndustry.item, bsWeaponAb);
		weaponAbilities.put(CrystalatelierItem.block, bsWeaponAb);
		weaponAbilities.put(RangaclawItem.block, bsWeaponAb);
		weaponAbilities.put(RangadaggerItem.block, bsWeaponAb);
		weaponAbilities.put(OldBoysWorkshop.item, bsWeaponAb);
		weaponAbilities.put(AllasSpearItem.block, bsWeaponAb);
		weaponAbilities.put(MookWorkshop.block, bsWeaponAb);

		weaponAbilities.put(MimicryItem.block, new MimicryWeaponAbility());

	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		PlayerEntity entity = (PlayerEntity) dependencies.get("entity");
		TcorpModVariables.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		Item chestItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND).getItem();

		getForItem(chestItem).trigger(entity,playerVars);

		/*if (DefiledbladeItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			DefiledBladeSpecialProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (HeavenswatcherItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			HeavenswatcherabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (DisposalcutterItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 5) {
			DisposalcutterabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (ItemTags.getAllTags().getTag(new ResourceLocation("tcorp:blacksilenceweapons"))
				.contains(((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem()))
				&& ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 1 || entity.getPersistentData().getDouble("furiosohits") > 10)) {
			BlacksilenceswapweaponProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (GalaxyorbItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			GalaxyorbabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (CrimsonfanItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			CrimsonfanheavyprojProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (LumbersaxeItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			WroughtaxeabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (FairyspearItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			FairyspearblessingProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (JustiziaItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 9) {
			JustiziaabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (ZweiswordItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 3) {
			ZweiswordabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (ZweilongswordItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 4) {
			ZweilongswordabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (MimicryItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 7) {
			MimicryabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
		if (FreischutzItem.block == ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())
				&& (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips > 5) {
			FreischutzmagicabilityProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}*/
	}
}
