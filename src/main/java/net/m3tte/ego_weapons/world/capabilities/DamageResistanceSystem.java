package net.m3tte.ego_weapons.world.capabilities;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import org.lwjgl.system.CallbackI;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.HashMap;

public class DamageResistanceSystem {

    private static final HashMap<EntityType<?>, DamageDefaults> mobDamageDefaults = new HashMap<>();
    private static final HashMap<Item, DamageDefaults> itemDamageDefaults = new HashMap<>();

    public static HashMap<EntityType<?>, DamageDefaults> getMobDefaults() {
        return mobDamageDefaults;
    }

    public static HashMap<Item, DamageDefaults> getItemDefaults() {
        return itemDamageDefaults;
    }

    public static void loadMobDamageDefaults() {
        registerDefaults(EgoWeaponsEntities.SUNSHOWER_UMBRELLA.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.WHITE);
        registerDefaults(EgoWeaponsEntities.NOTHING_THERE.get(), GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerDefaults(EgoWeaponsEntities.DAWN_OF_GREEN_DOUBT.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);

        registerDefaults(EntityType.PLAYER, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerDefaults(EntityType.WITHER_SKELETON, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.BLACK);
    }

    public static float calculateBurnResistanceFor(LivingEntity entity, float inValue) {
        Item chestItem = entity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

        if (chestItem.equals(EgoWeaponsItems.LIU_SOUTH_6_CHESTPLATE.get())) {
            inValue = Math.max(inValue - Math.max(inValue * 0.15f, 1),0);
        }

        if (chestItem.equals(EgoWeaponsItems.FIREFIST_SUIT.get())) {
            inValue = Math.max(inValue - Math.max(inValue * 0.25f, 1),0);
        }

        return inValue;
    }

    public static void loadItemDamageDefaults() {
        registerItemDefaults(Items.WOODEN_SWORD, GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.STONE_SWORD, GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.IRON_SWORD, GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.GOLDEN_SWORD, GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.DIAMOND_SWORD, GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.NETHERITE_SWORD, GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(Items.WOODEN_AXE, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.STONE_AXE, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.IRON_AXE, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.GOLDEN_AXE, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.DIAMOND_AXE, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(Items.NETHERITE_AXE, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(EpicFightItems.STONE_GREATSWORD.get(), GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.IRON_GREATSWORD.get(), GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.GOLDEN_GREATSWORD.get(), GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.DIAMOND_GREATSWORD.get(), GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.NETHERITE_GREATSWORD.get(), GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(EpicFightItems.STONE_SPEAR.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.IRON_SPEAR.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.GOLDEN_SPEAR.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.DIAMOND_SPEAR.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.NETHERITE_SPEAR.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(EpicFightItems.IRON_DAGGER.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.GOLDEN_DAGGER.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.DIAMOND_DAGGER.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.NETHERITE_DAGGER.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(EpicFightItems.IRON_TACHI.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.GOLDEN_TACHI.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.DIAMOND_TACHI.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.NETHERITE_TACHI.get(), GenericEgoDamage.AttackTypes.PIERCE, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(EpicFightItems.IRON_LONGSWORD.get(), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.GOLDEN_LONGSWORD.get(), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.DIAMOND_LONGSWORD.get(), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);
        registerItemDefaults(EpicFightItems.NETHERITE_LONGSWORD.get(), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.BLACK);

        registerItemDefaults(EpicFightItems.KATANA.get(), GenericEgoDamage.AttackTypes.SLASH, GenericEgoDamage.DamageTypes.RED);

    }

    private static void registerDefaults(EntityType<?> entity, GenericEgoDamage.AttackTypes attackType, GenericEgoDamage.DamageTypes damageType) {
        mobDamageDefaults.put(entity, new DamageDefaults(damageType, attackType));
    }

    private static void registerItemDefaults(Item item, GenericEgoDamage.AttackTypes attackType, GenericEgoDamage.DamageTypes damageType) {
        itemDamageDefaults.put(item, new DamageDefaults(damageType, attackType));
    }


    public static float processDamageForEntity(LivingEntity self, LivingEntity sourceEntity, float multiplier, DamageSource source, boolean staggered) {

        if (source instanceof GenericEgoDamage) {
            GenericEgoDamage.DamageTypes damageType = ((GenericEgoDamage) source).getDamageType();
            GenericEgoDamage.AttackTypes attackType = ((GenericEgoDamage) source).getAttackType();


            float baseMult = getFullyQualifiedResistanceMult(damageType, attackType, self);

            baseMult = Math.round(baseMult * 100)/100f;

            if (staggered) {
                float damageFactor = 1;

                if (self.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get())) {
                    damageFactor = 0.5f;
                }

                baseMult += damageFactor;
            }


            float fullMult = multiplier * baseMult;
            SharedFunctions.incrementResistanceDamage(source, baseMult-1);
            SharedFunctions.incrementBonusDamage(source, ((fullMult-multiplier)-(baseMult-1)));

            return fullMult;


        }


        return multiplier;
    }

    private static float getFullyQualifiedResistanceMult(GenericEgoDamage.DamageTypes damageType, GenericEgoDamage.AttackTypes attackType, LivingEntity target) {
        float mult = 1;

        mult *= EgoWeaponsAttributes.getDamageTypeResistance(damageType, target);

        if (!damageType.equals(GenericEgoDamage.DamageTypes.PALE))
            mult *= EgoWeaponsAttributes.getAttackTypeResistance(attackType, target);

        return mult;
    }
}
