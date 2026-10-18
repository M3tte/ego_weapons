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
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.incrementBonusDamage;

public class SalvationEffect extends PotencyOnlyStatus {
    public SalvationEffect() {
        super(EffectType.BENEFICIAL, "salvation",-16777216, true, 20, 32);
    }


    @Override
    public boolean isBeneficial() {
        return true;
    }


    static AttributeModifier stunresMod = new AttributeModifier(UUID.fromString("fc414f98-940e-4b92-88d9-6ce88ebff984"), "salvationStunres", 0.1, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier armorMod = new AttributeModifier(UUID.fromString("fc414f98-940e-4c92-88d9-6ce88ebff984"), "salvationDefense", 1, AttributeModifier.Operation.ADDITION);
    static AttributeModifier toughnessMod = new AttributeModifier(UUID.fromString("fc414f98-940e-4c92-88d9-6ce88ebff984"), "salvationToughness", 0.2, AttributeModifier.Operation.ADDITION);


    public static float applyOnHit(LivingEntityPatch<?> sourcePatch, LivingEntity target, float oldMultFactor, DamageSource src) {
        if (target.hasEffect(EgoWeaponsEffects.DAZZLE.get())) {
            oldMultFactor += incrementBonusDamage(src, 0.15f);
        }
        return oldMultFactor;
    }
    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);



        ModifiableAttributeInstance toughness = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance armor = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance stunres = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());

        float processedPotency = 1;

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
    public void expirationEffect(LivingEntity entity) {
        EgoWeaponsEffects.LAMP.get().increment(entity, 1, 1);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.
        super.applyEffectTick(entity,amplifier);
        entity.removeEffect(EgoWeaponsEffects.LAMP.get());
    }





}
