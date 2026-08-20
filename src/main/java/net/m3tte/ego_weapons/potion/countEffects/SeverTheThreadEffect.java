//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class SeverTheThreadEffect extends CountPotencyStatus {
    public SeverTheThreadEffect() {
        super(EffectType.HARMFUL, "sever_the_thread",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.sever_the_thread";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (entity.hasEffect(this)) {


            if (entity.getEffect(this).getDuration() < 10) {
                decrement(entity, 0, 1);
            }




        }

    }


    @Override
    public void increment(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 300, Math.min(Math.min(99,cap),Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(99,cap));

            entity.getEffect(this).update(new EffectInstance(this, 300, potency));

            syncEffect(entity);
        }
    }

    @Override
    public void decrement(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;



        if (entity.hasEffect(this)) {
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,99);
            entity.removeEffect(this);
            if (potency >= 0) {
                entity.addEffect(new EffectInstance(this, 300, potency));
                syncEffect(entity);
            }
        }
    }

    @Override
    public int getCount(LivingEntity entity) {
        return super.getCount(entity);
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }


    static AttributeModifier slashRes = new AttributeModifier(UUID.fromString("fca31f98-930e-3c62-78d9-6ce88ebff984"), "thread_slash_res", 0.1, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance slashResistance = attrman.getInstance(EgoWeaponsAttributes.SLASH_RESISTANCE.get());

        float value = this.getPotency(living);

        //float processedPotency = value / (((value - 1) / 200) + 1) - 1;
        float processedPotency = ((int) (value / 20)) - 1;


        if (slashResistance != null) {
            slashResistance.removeModifier(slashRes);
            if (processedPotency >= 0)
                slashResistance.addPermanentModifier(new AttributeModifier(slashRes.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (processedPotency), slashRes), slashRes.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance slashResistance = attrman.getInstance(EgoWeaponsAttributes.SLASH_RESISTANCE.get());

        if (slashResistance != null)
            slashResistance.removeModifier(slashRes);

        attrman.save();
    }

}
