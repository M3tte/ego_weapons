//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class EmberEffect extends PotencyOnlyStatus {
    public EmberEffect() {
        super(EffectType.HARMFUL, "embers",-16777216, true, 99, 400);
    }

    static AttributeModifier redResistanceMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b92-83d1-6ce88ebfc984"), "redResistanceMod", 0.1f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance redDMGInstance = attrman.getInstance(EgoWeaponsAttributes.RED_RESISTANCE.get());

        if (redDMGInstance != null) {
            redDMGInstance.removeModifier(redResistanceMod);
            redDMGInstance.addPermanentModifier(new AttributeModifier(redResistanceMod.getId(), this.getDescriptionId() + " " + 0, redResistanceMod.getAmount(), redResistanceMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance redDMGInstance = attrman.getInstance(EgoWeaponsAttributes.RED_RESISTANCE.get());

        if (redDMGInstance != null)
            redDMGInstance.removeModifier(redResistanceMod);

        attrman.save();
    }

}
