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

import java.util.UUID;

public class SpeedDownEffect extends PotencyOnlyStatus {
    public SpeedDownEffect() {
        super(EffectType.HARMFUL, "speed_down",-16777216, false, 99, 300);
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }


    static AttributeModifier speedModifier = new AttributeModifier(UUID.fromString("fc414a98-930e-4b92-87d9-6ce88ebff984"), "speedMod", -0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);

        ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);

        float value = this.getPotency(living);

        //float processedPotency = value / (((value - 1) / 200) + 1) - 1;
        float processedPotency = value - 1;

        System.out.println("Processed Potency = "+processedPotency);

        if (speedInst != null) {
            speedInst.removeModifier(speedModifier);
            speedInst.addPermanentModifier(new AttributeModifier(speedModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), speedModifier), speedModifier.getOperation()));
        }




        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);

        if (speedInst != null)
            speedInst.removeModifier(speedModifier);

        attrman.save();
    }
}
