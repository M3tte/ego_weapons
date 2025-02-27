package net.m3tte.ego_weapons.procedures.abilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.procedures.abilities.assistAttacks.FullstopRifleWeaponAssistAttack;
import net.m3tte.ego_weapons.procedures.abilities.assistAttacks.MagicBulletAssistAttack;
import net.m3tte.ego_weapons.procedures.abilities.reloadAbilities.FullstopReloadAbility;
import net.m3tte.ego_weapons.procedures.abilities.reloadAbilities.SolemnLamentReloadAbility;
import net.m3tte.ego_weapons.procedures.abilities.weaponAbilities.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class WeaponAbilityProcedure {

	private static Map<Item, ItemAbility> weaponAbilities;
	private static Map<Item, ItemAbility> assistAttacks;
	private static Map<Item, ItemAbility> getWeaponAbilities() {
		if (weaponAbilities == null) {
			setupWeaponAbilities();
		}
		return weaponAbilities;
	}

	public static Map<Item, ItemAbility> getAssistAttacks() {
		if (assistAttacks == null) {
			setupWeaponAssists();
		}
		return assistAttacks;
	}

	public static ItemAbility getAssistForItem(Item item) {
		return getAssistAttacks().getOrDefault(item, null);
	}
	public static ItemAbility getForItem(Item item) {
		return getWeaponAbilities().getOrDefault(item, new ItemAbility());
	}

	public static void setupWeaponAssists() {
		assistAttacks = new HashMap<>();

		assistAttacks.put(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get(), new FullstopRifleWeaponAssistAttack());
		assistAttacks.put(EgoWeaponsItems.MAGIC_BULLET.get(), new MagicBulletAssistAttack());
	}
	public static void setupWeaponAbilities() {
		weaponAbilities = new HashMap<>();
		BlackSilenceWeaponAbility bsWeaponAb = new BlackSilenceWeaponAbility();
		weaponAbilities.put(EgoWeaponsItems.DURANDAL.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.ZELKOVA_AXE.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.ZELKOVA_MACE.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.ATELIER_LOGIC_SHOTGUN.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.WHEELS_INDUSTRY.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.CRYSTAL_ATELIER.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.RANGA_CLAW.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.OLD_BOYS_WORKSHOP.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.ALLAS_SPEAR.get(), bsWeaponAb);
		weaponAbilities.put(EgoWeaponsItems.MOOK_WORKSHOP.get(), bsWeaponAb);

		weaponAbilities.put(EgoWeaponsItems.MIMICRY.get(), new MimicryWeaponAbility());
		weaponAbilities.put(EgoWeaponsItems.MAGIC_BULLET.get(), new MagicBulletWeaponAbility());
		weaponAbilities.put(EgoWeaponsItems.SUNSHOWER.get(), new SunshowerWeaponAbility());
		weaponAbilities.put(EgoWeaponsItems.OEUFI_HALBERD.get(), new OeufiHalberdWeaponAbility());
		weaponAbilities.put(EgoWeaponsItems.FULLSTOP_REP_MACHETE.get(), new FullstopWeaponAbility());
		weaponAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_BLACK.get(), new SolemnLamentReloadAbility());
		weaponAbilities.put(EgoWeaponsItems.SOLEMN_LAMENT_WHITE.get(), new SolemnLamentReloadAbility());
		weaponAbilities.put(EgoWeaponsItems.FULLSTOP_SNIPER_RAILGUN.get(), new FullstopRifleWeaponAbility());
	}

	public static void runWeaponAbility(PlayerEntity entity) {
		EgoWeaponsModVars.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		if (playerVars.globalcooldown > 0)
			return;

		Item handItem = entity.getItemBySlot(EquipmentSlotType.MAINHAND).getItem();


		ItemAbility ability = null;

		if (entity.hasEffect(EgoWeaponsEffects.ASSIST_FIRE.get())) {
			ability = getAssistForItem(handItem);
		}

		if (ability == null) {
			ability = getForItem(handItem);
		}

		ability.trigger(entity, playerVars);

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
