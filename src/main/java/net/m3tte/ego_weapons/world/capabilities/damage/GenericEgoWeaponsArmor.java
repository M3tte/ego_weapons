package net.m3tte.ego_weapons.world.capabilities.damage;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class GenericEgoWeaponsArmor extends ArmorItem {

    float redResistance = 1;
    float whiteResistance = 1;
    float blackResistance = 1;
    float paleResistance = 1;

    float slashResistance = 1;
    float pierceResistance = 1;
    float bluntResistance = 1;

    float bonusStaggerThreshold = 0;
    float bonusSanity = 0;

    private Multimap<Attribute, AttributeModifier> attributes;



    public GenericEgoWeaponsArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props) {
        super(armorMaterial, slot, props);
    }

    public void resistanceMods(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {

        list.add(new StringTextComponent("Defensive Statistics:").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.defense_level").append(this.material.getDefenseForSlot(this.slot)+"").withStyle(TextFormatting.BLUE));
        if (bonusStaggerThreshold > 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.stagger_bonus").append(((int)this.bonusStaggerThreshold)+"").withStyle(TextFormatting.BLUE));
        } else if (bonusStaggerThreshold < 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.stagger_bonus").append(((int)this.bonusStaggerThreshold)+"").withStyle(TextFormatting.RED));
        }

        if (bonusSanity > 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.sanity_bonus").append(((int)this.bonusSanity)+"").withStyle(TextFormatting.BLUE));
        } else if (bonusSanity < 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.sanity_bonus").append(((int)this.bonusSanity)+"").withStyle(TextFormatting.RED));
        }

        list.add(new StringTextComponent(" "));

        list.add(new StringTextComponent("Damage Resistances:").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.red").append(this.redResistance+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.white").append(this.whiteResistance+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.black").append(this.blackResistance+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.pale").append(this.paleResistance+"x"));
        list.add(new StringTextComponent(" "));

        list.add(new StringTextComponent("Attack Type Resistances:").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.slash").append(this.slashResistance+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.pierce").append(this.pierceResistance+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.blunt").append(this.bluntResistance+"x"));

    }


    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {

        if(!this.slot.equals(EquipmentSlotType.CHEST))
            return super.getDefaultAttributeModifiers(slot);

        if(attributes == null) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            UUID uuid = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
            UUID uuidd = UUID.fromString("2AD3F246-dEE1-4E67-B886-69FD380BB150");
            builder.put(Attributes.ARMOR, new AttributeModifier(uuidd, "Armor modifier", this.material.getDefenseForSlot(this.slot), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuidd, "Toughness modifier", this.material.getToughness(), AttributeModifier.Operation.ADDITION));

            AttributeModifier redMod = new AttributeModifier(uuid,"chestplateRedMod", redResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            AttributeModifier whiteMod = new AttributeModifier(uuid,"chestplateWhiteMod", whiteResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            AttributeModifier blackMod = new AttributeModifier(uuid,"chestplateBlackMod", blackResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            AttributeModifier paleMod = new AttributeModifier(uuid,"chestplatePaleMod", paleResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);

            AttributeModifier slashMod = new AttributeModifier(uuid,"chestplateSlashMod", slashResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            AttributeModifier pierceMod = new AttributeModifier(uuid,"chestplatePierceMod", pierceResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            AttributeModifier bluntMod = new AttributeModifier(uuid,"chestplateBluntMod", bluntResistance-1, AttributeModifier.Operation.MULTIPLY_TOTAL);

            if (bonusStaggerThreshold != 0) {
                AttributeModifier staggerMod = new AttributeModifier(uuid,"chestplateStaggerMod", bonusStaggerThreshold, AttributeModifier.Operation.ADDITION);
                builder.put(EgoWeaponsAttributes.MAX_STAGGER.get(), staggerMod);
            }

            if (bonusSanity != 0) {
                AttributeModifier staggerMod = new AttributeModifier(uuid,"chestplateSanityMod", bonusSanity, AttributeModifier.Operation.ADDITION);
                builder.put(EgoWeaponsAttributes.MAX_SANITY.get(), staggerMod);
            }

            builder.put(EgoWeaponsAttributes.RED_RESISTANCE.get(), redMod);
            builder.put(EgoWeaponsAttributes.WHITE_RESISTANCE.get(), whiteMod);
            builder.put(EgoWeaponsAttributes.BLACK_RESISTANCE.get(), blackMod);
            builder.put(EgoWeaponsAttributes.PALE_RESISTANCE.get(), paleMod);
            builder.put(EgoWeaponsAttributes.SLASH_RESISTANCE.get(), slashMod);
            builder.put(EgoWeaponsAttributes.PIERCE_RESISTANCE.get(), pierceMod);
            builder.put(EgoWeaponsAttributes.BLUNT_RESISTANCE.get(), bluntMod);


            attributes = builder.build();
        }

        return slot == this.slot ? attributes : super.getDefaultAttributeModifiers(slot);
    }



    public GenericEgoWeaponsArmor(IArmorMaterial armorMaterial, EquipmentSlotType slot, Properties props, float redResistance, float whiteResistance, float blackResistance, float paleResistance, float slashResistance, float pierceResistance, float bluntResistance, float bonusStaggerThreshold, float bonusSanity) {
        super(armorMaterial, slot, props);
        this.redResistance = redResistance;
        this.whiteResistance = whiteResistance;
        this.blackResistance = blackResistance;
        this.paleResistance = paleResistance;
        this.slashResistance = slashResistance;
        this.pierceResistance = pierceResistance;
        this.bluntResistance = bluntResistance;
        this.bonusStaggerThreshold = bonusStaggerThreshold;
        this.bonusSanity = bonusSanity;
    }
}
