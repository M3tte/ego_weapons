package net.m3tte.tcorp.procedures.abilities;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.item.blackSilence.weapons.*;
import net.m3tte.tcorp.procedures.abilities.weaponAbilities.*;
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
		weaponAbilities.put(TCorpItems.DURANDAL.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.ZELKOVA_AXE.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.ZELKOVA_MACE.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.ATELIER_LOGIC_PISTOLS.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.ATELIER_LOGIC_SHOTGUN.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.WHEELS_INDUSTRY.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.CRYSTAL_ATELIER.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.RANGA_DAGGER.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.RANGA_CLAW.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.OLD_BOYS_WORKSHOP.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.ALLAS_SPEAR.get(), bsWeaponAb);
		weaponAbilities.put(TCorpItems.MOOK_WORKSHOP.get(), bsWeaponAb);

		weaponAbilities.put(TCorpItems.MIMICRY.get(), new MimicryWeaponAbility());
		weaponAbilities.put(TCorpItems.MAGIC_BULLET.get(), new MagicBulletWeaponAbility());
		weaponAbilities.put(TCorpItems.SOLEMN_LAMENT_WHITE.get(), new SolemnLamentWeaponAbility());
		weaponAbilities.put(TCorpItems.SOLEMN_LAMENT_BLACK.get(), new SolemnLamentWeaponAbility());
		weaponAbilities.put(TCorpItems.SUNSHOWER.get(), new SunshowerWeaponAbility());
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		PlayerEntity entity = (PlayerEntity) dependencies.get("entity");
		TcorpModVariables.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

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
