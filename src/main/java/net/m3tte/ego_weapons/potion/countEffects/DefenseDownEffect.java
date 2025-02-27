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

public class DefenseDownEffect extends CountPotencyStatus {
    public DefenseDownEffect() {
        super(EffectType.HARMFUL, "defense_down",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.defense_down";
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
        return true;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return true;
    }


    static AttributeModifier stunresMod = new AttributeModifier(UUID.fromString("fc414f98-920e-4b92-88d9-6ce88ebff984"), "defenseDownStunRes", -0.1, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier armorMod = new AttributeModifier(UUID.fromString("fc414f98-920e-4c92-88d9-6ce88ebff984"), "defenseDownDefense", -1, AttributeModifier.Operation.ADDITION);
    static AttributeModifier toughnessMod = new AttributeModifier(UUID.fromString("fc414f98-920e-4c92-88d9-6ce88ebff984"), "defenseDownToughness", -0.2, AttributeModifier.Operation.ADDITION);


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


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.
    }

    @Override
    public void increment(LivingEntity entity, int limit, int potency) {
        if (entity.level.isClientSide)
            return;

        if (limit == 0)
            limit = 98;

        if (potency > limit)
            potency = limit;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 300, potency-1));
        } else {
            entity.getEffect(this).update(new EffectInstance(this, entity.getEffect(this).getDuration(), Math.min(entity.getEffect(this).getAmplifier() + potency, limit-1)));
        }
        syncEffect(entity);
    }

    @Override
    public void decrement(LivingEntity entity, int limit, int potency) {
        if (entity.level.isClientSide)
            return;

        int previousPotency = 0;
        if (entity.hasEffect(this)) {
            previousPotency = entity.getEffect(this).getAmplifier()+1;
            entity.removeEffect(this);
        }

        if ((previousPotency - potency) > 0) {
            entity.addEffect(new EffectInstance(this, 300, potency-1));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }
}
