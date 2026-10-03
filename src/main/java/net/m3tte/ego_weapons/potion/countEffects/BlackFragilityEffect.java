//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class BlackFragilityEffect extends PotencyOnlyStatus {
    public BlackFragilityEffect() {
        super(EffectType.HARMFUL, "black_fragility",-16777216);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {


    }

    static AttributeModifier blackResistanceMod = new AttributeModifier(UUID.fromString("fb214f98-930e-4b93-83d1-adc88ebfc984"), "blackResistanceMod", 0.1f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);


        ModifiableAttributeInstance whiteDMGInstance = attrman.getInstance(EgoWeaponsAttributes.BLACK_RESISTANCE.get());

        if (whiteDMGInstance != null) {
            whiteDMGInstance.removeModifier(blackResistanceMod);
            whiteDMGInstance.addPermanentModifier(new AttributeModifier(blackResistanceMod.getId(), this.getDescriptionId() + " " + 0, blackResistanceMod.getAmount() * (amplifier + 1D), blackResistanceMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance whiteDmg = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());

        if (whiteDmg != null)
            whiteDmg.removeModifier(blackResistanceMod);

        attrman.save();
    }

}
