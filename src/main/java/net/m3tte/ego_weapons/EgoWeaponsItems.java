package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.item.SuitItem;
import net.m3tte.ego_weapons.item.ZweiSwordItem;
import net.m3tte.ego_weapons.item.blackSilence.BlackSilenceArmor;
import net.m3tte.ego_weapons.item.blackSilence.weapons.*;
import net.m3tte.ego_weapons.item.fullstop_rep.FullstopRepArmor;
import net.m3tte.ego_weapons.item.fullstop_rep.FullstopRepWeapon;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperArmor;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperSuitcase;
import net.m3tte.ego_weapons.item.fullstop_sniper.FullstopSniperWeapon;
import net.m3tte.ego_weapons.item.guns.AmmoItem;
import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBullet;
import net.m3tte.ego_weapons.item.magic_bullet.MagicBulletArmor;
import net.m3tte.ego_weapons.item.mimicry.MimicryArmor;
import net.m3tte.ego_weapons.item.mimicry.MimicryItem;
import net.m3tte.ego_weapons.item.oeufi.OeufiArmor;
import net.m3tte.ego_weapons.item.oeufi.OeufiContractItem;
import net.m3tte.ego_weapons.item.oeufi.OeufiHalberd;
import net.m3tte.ego_weapons.item.redmist.RedMistEGOSuit;
import net.m3tte.ego_weapons.item.redmist.RedMistJacket;
import net.m3tte.ego_weapons.item.solemn_lament.SolemnLament;
import net.m3tte.ego_weapons.item.solemn_lament.SolemnLamentArmor;
import net.m3tte.ego_weapons.item.sunshower.Sunshower;
import net.m3tte.ego_weapons.item.sunshower.SunshowerArmor;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EgoWeaponsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EgoWeaponsMod.MODID);
    public static RegistryObject<Item>  ZWEI_ASSOCIATION_LONGSWORD = registerItem("zweilongsword", new ZweiSwordItem(ZweiSwordItem.zweiItemTier, 3, -2.7f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item>  MIMICRY = registerItem("mimicry", new MimicryItem(3, -2.65f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item>  MIMICRY_CHESTPLATE = registerItem("mimicry_chestplate", MimicryArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item>  MIMICRY_LEGGINGS = registerItem("mimicry_leggings", MimicryArmor.getArmorForSlot(EquipmentSlotType.LEGS));
    public static RegistryObject<Item>  SUIT_LEGGINGS = registerItem("suit_leggings", SuitItem.getArmorForSlot(EquipmentSlotType.LEGS));

    public static RegistryObject<Item> RED_MIST_EGO_CHESTPLATE = registerItem("red_mist_ego_chestplate", RedMistEGOSuit.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> RED_MIST_EGO_LEGGINGS = registerItem("red_mist_ego_leggings", RedMistEGOSuit.getArmorForSlot(EquipmentSlotType.LEGS));
    public static RegistryObject<Item> RED_MIST_EGO_HELMET = registerItem("red_mist_ego_helmet", RedMistEGOSuit.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> JACKET_OF_THE_RED_MIST = registerItem("jacket_of_the_red_mist", RedMistJacket.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> CIGARETTE = registerItem("cigarette", RedMistJacket.getArmorForSlot(EquipmentSlotType.HEAD));
    public static RegistryObject<Item> MAGIC_BULLET = registerItem("magic_bullet", new MagicBullet(6, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static RegistryObject<Item> MAGIC_BULLET_CLOAK = registerItem("magic_bullet_cloak", MagicBulletArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> MAGIC_BULLET_PIPE = registerItem("magic_bullet_pipe", MagicBulletArmor.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> SUIT_OF_THE_BLACK_SILENCE = registerItem("suit_of_the_black_silence", BlackSilenceArmor.getArmorForSlot(EquipmentSlotType.CHEST));

    public static RegistryObject<Item> PERCEPTION_BLOCKING_MASK = registerItem("perception_blocking_mask", BlackSilenceArmor.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> SOLEMN_LAMENT_CLOAK = registerItem("solemn_lament_cloak", SolemnLamentArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> SOLEMN_LAMENT_BUTTERFLY = registerItem("solemn_lament_butterfly", SolemnLamentArmor.getArmorForSlot(EquipmentSlotType.HEAD));


    public static RegistryObject<Item> ALLAS_SPEAR = registerItem("allas_spear", new AllasSpearItem(3, -3.1f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ATELIER_LOGIC_PISTOLS = registerItem("atelier_logic_pistols", new AtelierLogicRevolver(3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ATELIER_LOGIC_SHOTGUN = registerItem("atelier_logic_shotgun", new AtelierlogicshotgunItem(3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> CRYSTAL_ATELIER = registerItem("crystal_atelier", new CrystalatelierItem(3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> DURANDAL = registerItem("durandal", new DurandalItem(3, -2.9f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> DURANDAL_SHEATH = registerItem("durandal_sheath", new DurandalsheathItem());
    public static RegistryObject<Item> MOOK_SHEATH = registerItem("mook_sheath", new MookSheath());
    public static RegistryObject<Item> MOOK_WORKSHOP = registerItem("mook_workshop", new MookWorkshop(3, -2.2f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> OLD_BOYS_WORKSHOP = registerItem("old_boys_workshop", new OldBoysWorkshop( 1, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> RANGA_CLAW = registerItem("ranga_claw", new RangaclawItem( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> RANGA_CLAW_L = registerItem("ranga_claw_l", new RangaclawItem( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> WHEELS_INDUSTRY = registerItem("wheels_industry", new WheelsIndustry( 1, -3.1f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ZELKOVA_AXE = registerItem("zelkova_axe", new ZelkovaaxeItem( 1, -2.8f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> ZELKOVA_MACE = registerItem("zelkova_mace", new ZelkovamaceItem( 1, -2.9f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static RegistryObject<Item> SOLEMN_LAMENT_WHITE = registerItem("solemn_lament_living", new SolemnLament( 4, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), false));
    public static RegistryObject<Item> SOLEMN_LAMENT_BLACK = registerItem("solemn_lament_departed", new SolemnLament( 5, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), true));

    public static RegistryObject<Item> DOUBT_SPAWN_EGG = registerItem("doubt_spawn_egg", new ForgeSpawnEggItem(EgoWeaponsEntities.DAWN_OF_GREEN_DOUBT, 16777215, 16777215, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static RegistryObject<Item> NOTHING_THERE_SPAWN_EGG = registerItem("nothing_there_spawn_egg", new ForgeSpawnEggItem(EgoWeaponsEntities.NOTHING_THERE, 16777215, 16777215, new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static RegistryObject<Item> SUNSHOWER = registerItem("sunshower", new Sunshower( 3, -2.65f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static RegistryObject<Item> SUNSHOWER_CLOAK = registerItem("sunshower_cloak", SunshowerArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> SUNSHOWER_HOOD = registerItem("sunshower_hood", SunshowerArmor.getArmorForSlot(EquipmentSlotType.HEAD));

    public static RegistryObject<Item> OEUFI_HAT = registerItem("oeufi_association_hat", OeufiArmor.getArmorForSlot(EquipmentSlotType.HEAD));
    public static RegistryObject<Item> OEUFI_CHESTPLATE = registerItem("oeufi_association_vest", OeufiArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> OEUFI_LEGGINGS = registerItem("oeufi_association_pants", OeufiArmor.getArmorForSlot(EquipmentSlotType.LEGS));
    public static RegistryObject<Item> OEUFI_HALBERD = registerItem("oeufi_association_halberd", new OeufiHalberd( 3, -2.6f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static RegistryObject<Item> OEUFI_CONTRACT = registerItem("oeufi_contract", new OeufiContractItem());

    public static RegistryObject<Item> FULLSTOP_REP_PISTOL = registerItem("fullstop_atelier_pistol", new FullstopRepWeapon( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), GunCaliber.PISTOL, 9));
    public static RegistryObject<Item> FULLSTOP_REP_MACHETE = registerItem("fullstop_machete", new FullstopRepWeapon( 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), GunCaliber.NONE, 0));

    public static RegistryObject<Item> FULLSTOP_EARPIECE = registerItem("fullstop_office_earpiece", FullstopRepArmor.getArmorForSlot(EquipmentSlotType.HEAD));
    public static RegistryObject<Item> FULLSTOP_REP_CLOAK = registerItem("fullstop_office_rep_cloak", FullstopRepArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> FULLSTOP_REP_PANTS = registerItem("fullstop_office_rep_pants", FullstopRepArmor.getArmorForSlot(EquipmentSlotType.LEGS));

    public static RegistryObject<Item> BASIC_LIGHT_BULLET = registerItem("basic_light_bullet", new AmmoItem( new Item.Properties().tab(ItemGroup.TAB_COMBAT), 0, GunCaliber.PISTOL));
    public static RegistryObject<Item> INC_LIGHT_BULLET = registerItem("incendiary_light_bullet", new AmmoItem( new Item.Properties().tab(ItemGroup.TAB_COMBAT), 1, GunCaliber.PISTOL));
    public static RegistryObject<Item> MOONST_LIGHT_BULLET = registerItem("moonstone_light_bullet", new AmmoItem( new Item.Properties().tab(ItemGroup.TAB_COMBAT), 2, GunCaliber.PISTOL));

    public static RegistryObject<Item> BASIC_RIFLE_BULLET = registerItem("basic_rifle_bullet", new AmmoItem( new Item.Properties().tab(ItemGroup.TAB_COMBAT), 3, GunCaliber.SNIPER));
    public static RegistryObject<Item> AL_HV_PULVERIZATION_RIFLE_ROUND = registerItem("al_hv_pulverization_rifle_round", new AmmoItem( new Item.Properties().tab(ItemGroup.TAB_COMBAT), 4, GunCaliber.SNIPER));

    public static RegistryObject<Item> FULLSTOP_SNIPER_RAILGUN = registerItem("fullstop_office_railgun", new FullstopSniperWeapon( 6, -2.25f, new Item.Properties().tab(ItemGroup.TAB_COMBAT), GunCaliber.SNIPER, 8));
    public static RegistryObject<Item> FULLSTOP_SNIPER_SUITCASE = registerItem("fullstop_suitcase", new FullstopSniperSuitcase());

    public static RegistryObject<Item> FULLSTOP_SNIPER_SUIT = registerItem("fullstop_office_sniper_suit", FullstopSniperArmor.getArmorForSlot(EquipmentSlotType.CHEST));
    public static RegistryObject<Item> FULLSTOP_SNIPER_PANTS = registerItem("fullstop_office_sniper_pants", FullstopSniperArmor.getArmorForSlot(EquipmentSlotType.LEGS));


    private static RegistryObject<Item> registerItem(String registryName, Item i) {
        return ITEMS.register(registryName, () -> i);
    }
}
