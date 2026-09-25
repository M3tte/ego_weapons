//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class ArmorRegenerativeCycle extends CountPotencyStatus {
    public ArmorRegenerativeCycle() {
        super(EffectType.BENEFICIAL, "alloy_regenerative_cycle",-16777216);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }

    @Override
    public int getPotency(EffectInstance ef) {
        return 0;
    }

    @Override
    public int getPotency(LivingEntity entity) {
        return 0;
    }

    static AttributeModifier speedModifier = new AttributeModifier(UUID.fromString("fc415d98-930e-4a63-88d9-6cb65ebff984"), "speedMod", 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);

        float value = this.getPotency(living);

        //float processedPotency = value / (((value - 1) / 200) + 1) - 1;
        float processedPotency = value - 1;

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

    @Override
    public int getCount(EffectInstance ef) {
        return super.getCount(ef) + 1;
    }

    @Override
    public int getCount(LivingEntity entity) {
        return super.getCount(entity) + 1;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
