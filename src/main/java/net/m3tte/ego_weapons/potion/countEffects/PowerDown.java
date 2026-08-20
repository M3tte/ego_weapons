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

public class PowerDown extends PotencyOnlyStatus {
    public PowerDown() {
        super(EffectType.HARMFUL, "power_down",-16777216, false, 99, 300);
    }


    static AttributeModifier damageMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b93-88d9-6ce88ebfc984"), "powerDownDamage", -1, AttributeModifier.Operation.ADDITION);
    static AttributeModifier impactMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b93-88d9-6ce88ebfc984"), "powerDownImpact", -0.4f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance damageInstance = attrman.getInstance(Attributes.ATTACK_DAMAGE);
        ModifiableAttributeInstance impactInstance = attrman.getInstance(EpicFightAttributes.IMPACT.get());
        ModifiableAttributeInstance offhandImpactInstance = attrman.getInstance(EpicFightAttributes.OFFHAND_IMPACT.get());

        float value = this.getPotency(living) - 1;

        if (damageInstance != null) {
            damageInstance.removeModifier(damageMod);
            damageInstance.addPermanentModifier(new AttributeModifier(damageMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (value), damageMod), damageMod.getOperation()));
        }


        if (impactInstance != null) {
            impactInstance.removeModifier(impactMod);
            impactInstance.addPermanentModifier(new AttributeModifier(impactMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (value), impactMod), impactMod.getOperation()));
        }

        if (offhandImpactInstance != null) {
            offhandImpactInstance.removeModifier(impactMod);
            offhandImpactInstance.addPermanentModifier(new AttributeModifier(impactMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (value), impactMod), impactMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance damageInstance = attrman.getInstance(Attributes.ATTACK_DAMAGE);
        ModifiableAttributeInstance impactInstance = attrman.getInstance(EpicFightAttributes.IMPACT.get());
        ModifiableAttributeInstance offhandImpactInstance = attrman.getInstance(EpicFightAttributes.OFFHAND_IMPACT.get());

        if (damageInstance != null)
            damageInstance.removeModifier(damageMod);
        if (impactInstance != null)
            impactInstance.removeModifier(impactMod);
        if (offhandImpactInstance != null)
            offhandImpactInstance.removeModifier(impactMod);

        attrman.save();
    }

}
