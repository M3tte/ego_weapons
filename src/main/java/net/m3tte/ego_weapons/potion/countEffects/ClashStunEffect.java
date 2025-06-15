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
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class ClashStunEffect extends Effect {
    public ClashStunEffect() {
        super(EffectType.BENEFICIAL,-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.clashed";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return false;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return false;
    }


    static AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.fromString("fc416d93-930e-4b92-88d9-6ce88ebff984"), "speedMod", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance atSpInst = attrman.getInstance(Attributes.ATTACK_SPEED);


        //float processedPotency = value / (((value - 1) / 200) + 1) - 1;
        float processedPotency = amplifier;

        if (atSpInst != null) {
            atSpInst.removeModifier(attackSpeedModifier);
            atSpInst.addPermanentModifier(new AttributeModifier(attackSpeedModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), attackSpeedModifier), attackSpeedModifier.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.ATTACK_SPEED);

        if (speedInst != null)
            speedInst.removeModifier(attackSpeedModifier);

        attrman.save();
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

}
