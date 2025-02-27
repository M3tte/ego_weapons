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

public class PowerUp extends CountPotencyStatus {
    public PowerUp() {
        super(EffectType.HARMFUL, "power_up",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.power_up";
    }

    @Override
    public boolean isBeneficial() {
        return true;
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


    static AttributeModifier damageMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b92-88d9-6ce88ebfc984"), "powerUpDamage", 1, AttributeModifier.Operation.ADDITION);
    static AttributeModifier impactMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b92-88d9-6ce88ebfc984"), "powerUpImpact", 0.4f, AttributeModifier.Operation.ADDITION);

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
