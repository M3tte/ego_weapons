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

public class OffenseUpEffect extends PotencyOnlyStatus {
    public OffenseUpEffect() {
        super(EffectType.BENEFICIAL, "offense_up",-16777216, false, 99, 300);
    }


    @Override
    public boolean isBeneficial() {
        return true;
    }


    static AttributeModifier damageMod = new AttributeModifier(UUID.fromString("fc414f98-930e-4b92-88d9-6ce88ebff984"), "offenseUpDamage", 0.03, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier attackSpeedMod = new AttributeModifier(UUID.fromString("fc414f98-930e-4b92-88d9-6ce88ebff984"), "offenseUpAttackSpeed", 0.045, AttributeModifier.Operation.ADDITION);
    static AttributeModifier impactMod = new AttributeModifier(UUID.fromString("fc414f98-930e-4b92-88d9-6ce88ebff984"), "offenseUpImpact", 0.05, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance damageInstance = attrman.getInstance(Attributes.ATTACK_DAMAGE);
        ModifiableAttributeInstance attackSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);
        ModifiableAttributeInstance impactInstance = attrman.getInstance(EpicFightAttributes.IMPACT.get());
        ModifiableAttributeInstance offhandImpactInstance = attrman.getInstance(EpicFightAttributes.OFFHAND_IMPACT.get());

        float value = this.getPotency(living);

        //float processedPotency = value / (((value - 1) / 200) + 1) - 1;
        float processedPotency = value - 1;

        System.out.println("Processed Potency = "+processedPotency);

        if (damageInstance != null) {
            damageInstance.removeModifier(damageMod);
            damageInstance.addPermanentModifier(new AttributeModifier(damageMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), damageMod), damageMod.getOperation()));
        }

        if (attackSpeedInstance != null) {
            attackSpeedInstance.removeModifier(attackSpeedMod);
            attackSpeedInstance.addPermanentModifier(new AttributeModifier(attackSpeedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), impactMod), impactMod.getOperation()));
        }

        if (impactInstance != null) {
            impactInstance.removeModifier(impactMod);
            impactInstance.addPermanentModifier(new AttributeModifier(impactMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), impactMod), impactMod.getOperation()));
        }

        if (offhandImpactInstance != null) {
            offhandImpactInstance.removeModifier(impactMod);
            offhandImpactInstance.addPermanentModifier(new AttributeModifier(impactMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), impactMod), impactMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance damageInstance = attrman.getInstance(Attributes.ATTACK_DAMAGE);
        ModifiableAttributeInstance attackSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);
        ModifiableAttributeInstance impactInstance = attrman.getInstance(EpicFightAttributes.IMPACT.get());
        ModifiableAttributeInstance offhandImpactInstance = attrman.getInstance(EpicFightAttributes.OFFHAND_IMPACT.get());

        if (damageInstance != null)
            damageInstance.removeModifier(damageMod);
        if (attackSpeedInstance != null)
            attackSpeedInstance.removeModifier(attackSpeedMod);
        if (impactInstance != null)
            impactInstance.removeModifier(impactMod);
        if (offhandImpactInstance != null)
            offhandImpactInstance.removeModifier(impactMod);

        attrman.save();
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.
    }

}
