package net.m3tte.ego_weapons;

import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.EgoAttribute;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EgoWeaponsAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, EgoWeaponsMod.MODID);


    // Resistance Values for Attack Types
    public static final RegistryObject<Attribute> BLUNT_RESISTANCE = ATTRIBUTES.register("blunt_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".blunt_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> PIERCE_RESISTANCE = ATTRIBUTES.register("pierce_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".pierce_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SLASH_RESISTANCE = ATTRIBUTES.register("slash_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".slash_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));

    // Resistance Values for Damage Types
    public static final RegistryObject<Attribute> RED_RESISTANCE = ATTRIBUTES.register("red_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".red_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> WHITE_RESISTANCE = ATTRIBUTES.register("white_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".white_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> BLACK_RESISTANCE = ATTRIBUTES.register("black_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".black_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> PALE_RESISTANCE = ATTRIBUTES.register("pale_resistance", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".pale_resistance", 1.0D, -40.0D, 100.0D).setSyncable(true));

    // Other Attributes
    public static final RegistryObject<Attribute> MAX_LIGHT = ATTRIBUTES.register("max_light", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".max_light", 5.0D, 0.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAX_STAGGER = ATTRIBUTES.register("max_stagger", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".max_stagger", 20.0D, -40.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAX_SANITY = ATTRIBUTES.register("max_sanity", () -> new EgoAttribute("attribute.name." + EgoWeaponsMod.MODID + ".max_sanity", 20.0D, -40.0D, 100.0D).setSyncable(true));


    // Same way epic fight handles it, probably works fine I.I.H.T.G.


    @SubscribeEvent
    public static void modifyExistingMobs(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            EgoWeaponsMod.LOGGER.info("Assigning Attributes for: "+type.toString());
            if (type.equals(EntityType.PLAYER)) {
                player(type, event);
            } else {
                commonCreature(type, event);
            }
        }

        DamageResistanceSystem.loadMobDamageDefaults();
        DamageResistanceSystem.loadItemDamageDefaults();
    }

    private static void commonCreature(EntityType<? extends LivingEntity> entityType, EntityAttributeModificationEvent event) {
        event.add(entityType, EgoWeaponsAttributes.BLUNT_RESISTANCE.get());
        event.add(entityType, EgoWeaponsAttributes.PIERCE_RESISTANCE.get());
        event.add(entityType, EgoWeaponsAttributes.SLASH_RESISTANCE.get());

        event.add(entityType, EgoWeaponsAttributes.RED_RESISTANCE.get());
        event.add(entityType, EgoWeaponsAttributes.WHITE_RESISTANCE.get());
        event.add(entityType, EgoWeaponsAttributes.BLACK_RESISTANCE.get());
        event.add(entityType, EgoWeaponsAttributes.PALE_RESISTANCE.get());

        event.add(entityType, EgoWeaponsAttributes.MAX_STAGGER.get());
    }

    private static void player(EntityType<? extends LivingEntity> entityType, EntityAttributeModificationEvent event) {
        commonCreature(entityType, event);
        event.add(entityType, EgoWeaponsAttributes.MAX_STAGGER.get());
        event.add(entityType, EgoWeaponsAttributes.MAX_LIGHT.get());
        event.add(entityType, EgoWeaponsAttributes.MAX_SANITY.get());

    }

    public static float getMaxSanity(PlayerEntity player) {
        ModifiableAttributeInstance value = player.getAttribute(EgoWeaponsAttributes.MAX_SANITY.get());
        return (float)(value == null ? 0 : value.getValue());
    }

    public static float getMaxStagger(LivingEntity target) {
        ModifiableAttributeInstance value = target.getAttribute(EgoWeaponsAttributes.MAX_STAGGER.get());
        return (float)(value == null ? 0 : value.getValue());
    }

    public static int getMaxLight(PlayerEntity player) {
        ModifiableAttributeInstance value = player.getAttribute(EgoWeaponsAttributes.MAX_LIGHT.get());
        return (int)(value == null ? 0 : value.getValue());
    }

    public static float getDamageTypeResistance(GenericEgoDamage.DamageTypes type, LivingEntity target) {
        ModifiableAttributeInstance value = null;
        switch (type) {
            case RED:
                value = target.getAttribute(EgoWeaponsAttributes.RED_RESISTANCE.get());
                break;
            case BLACK:
                value = target.getAttribute(EgoWeaponsAttributes.BLACK_RESISTANCE.get());
                break;
            case PALE:
                value = target.getAttribute(EgoWeaponsAttributes.PALE_RESISTANCE.get());
                break;
            case WHITE:
                value = target.getAttribute(EgoWeaponsAttributes.WHITE_RESISTANCE.get());
                break;
            default:
                return 1;
        }
        return (float)(value == null ? 1 : value.getValue());
    }

    public static float getAttackTypeResistance(GenericEgoDamage.AttackTypes type, LivingEntity target) {
        ModifiableAttributeInstance value = null;
        switch (type) {
            case SLASH:
                value = target.getAttribute(EgoWeaponsAttributes.SLASH_RESISTANCE.get());
                break;
            case PIERCE:
                value = target.getAttribute(EgoWeaponsAttributes.PIERCE_RESISTANCE.get());
                break;
            case BLUNT:
                value = target.getAttribute(EgoWeaponsAttributes.BLUNT_RESISTANCE.get());
                break;
            case GENERIC:
                return 1;

        }
        return (float)(value == null ? 1 : value.getValue());
    }
}
