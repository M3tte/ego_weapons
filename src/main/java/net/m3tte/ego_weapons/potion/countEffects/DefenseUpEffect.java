//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class DefenseUpEffect extends PotencyOnlyStatus {
    public DefenseUpEffect() {
        super(EffectType.BENEFICIAL, "defense_up",-16777216, false, 99, 300);
    }


    static AttributeModifier stunresMod = new AttributeModifier(UUID.fromString("fc414f98-940e-4b92-88d9-6ce88ebff984"), "defenseDownStunRes", 0.1, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier armorMod = new AttributeModifier(UUID.fromString("fc414f98-940e-4c92-88d9-6ce88ebff984"), "defenseDownDefense", 1, AttributeModifier.Operation.ADDITION);
    static AttributeModifier toughnessMod = new AttributeModifier(UUID.fromString("fc414f98-940e-4c92-88d9-6ce88ebff984"), "defenseDownToughness", 0.2, AttributeModifier.Operation.ADDITION);


    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance toughness = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance armor = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance stunres = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());

        float value = this.getPotency(living);

        float processedPotency = value - 1;

        if (stunres != null) {
            stunres.removeModifier(stunresMod);
            stunres.addPermanentModifier(new AttributeModifier(stunresMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), stunresMod), stunresMod.getOperation()));
        }

        if (armor != null) {
            armor.removeModifier(armorMod);
            armor.addPermanentModifier(new AttributeModifier(armorMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), armorMod), armorMod.getOperation()));
        }

        if (toughness != null) {
            toughness.removeModifier(toughnessMod);
            toughness.addPermanentModifier(new AttributeModifier(toughnessMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), toughnessMod), toughnessMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance toughness = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance armor = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance stunres = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());

        if (stunres != null)
            stunres.removeModifier(stunresMod);
        if (armor != null)
            armor.removeModifier(armorMod);
        if (toughness != null)
            toughness.removeModifier(toughnessMod);

        attrman.save();
    }

}
