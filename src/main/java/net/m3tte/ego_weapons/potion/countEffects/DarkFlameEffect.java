//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.particle.ShadowpuffParticle;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.UUID;

public class DarkFlameEffect extends CountPotencyStatus {
    public DarkFlameEffect() {
        super(EffectType.HARMFUL, "dark_burn",-16777216);
    }

    static AttributeModifier stunresMod = new AttributeModifier(UUID.fromString("e6c0ac44-96ef-43ce-b0a1-46018857f5a7"), "darkFlameStunDebuff", -0.1, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier armorMod = new AttributeModifier(UUID.fromString("e6c0ac44-96ef-43ce-b0a1-46018857f5a7"), "darkFlameArmorDebuff", -1, AttributeModifier.Operation.ADDITION);
    static AttributeModifier toughnessMod = new AttributeModifier(UUID.fromString("e6c0ac44-96ef-43ce-b0a1-46018857f5a7"), "darkFlametoughnessDebuff", -0.2, AttributeModifier.Operation.ADDITION);


    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);


        ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance stunres = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance toughness = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());

        if (armorInstance != null) {
            armorInstance.removeModifier(armorMod);
            armorInstance.addPermanentModifier(new AttributeModifier(armorMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(this.getPotency(living)-1, armorMod), armorMod.getOperation()));
        }
        if (stunres != null) {
            stunres.removeModifier(stunresMod);
            stunres.addPermanentModifier(new AttributeModifier(stunresMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(this.getPotency(living) -1, stunresMod), stunresMod.getOperation()));
        }
        if (toughness != null) {
            toughness.removeModifier(toughnessMod);
            toughness.addPermanentModifier(new AttributeModifier(toughnessMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(this.getPotency(living) -1, toughnessMod), toughnessMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ARMOR);
        ModifiableAttributeInstance stunres = attrman.getInstance(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance toughness = attrman.getInstance(EpicFightAttributes.STUN_ARMOR.get());
        if (armorInstance != null)
            armorInstance.removeModifier(armorMod);

        if (stunres != null)
            stunres.removeModifier(stunresMod);

        if (toughness != null)
            toughness.removeModifier(toughnessMod);

        attrman.save();
    }





    @Override
    public String getDescriptionId() {
        return "effect.dark_burn";
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

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Prevents effect from ticking down at all.

        boolean shouldUpdate = false;
        int duration = Objects.requireNonNull(entity.getEffect(this)).getDuration();


        if (duration > 140) {
            shouldUpdate = true;
            duration = 140;
        }


        if (entity.tickCount % 100 == 0 && !(entity.level.isClientSide()) && !entity.hasEffect(EgoWeaponsEffects.BURN.get())) {
            duration -= 20;
            shouldUpdate = true;
        }

        if (duration % 20 != 0) {
            if (duration % 20 < 10) {
                duration -= duration % 20;
            } else  {
                duration = duration - duration % 20 + 20;
            }
        }
        if (duration > 11) {

            if (shouldUpdate)
                entity.removeEffect(this);

            entity.addEffect(new EffectInstance(this,  duration + 1, amplifier));

            if (shouldUpdate)
                this.syncEffect(entity);

        } else {
            entity.removeEffect(this);
        }

    }

    @Override
    public int getPotency(EffectInstance ef) {
        return Math.min(ef.getDuration() / 20,99);
    }

    @Override
    public int getPotency(LivingEntity entity) {
        if (!entity.hasEffect(this))
            return 0;
        return this.getPotency(entity.getEffect(this));
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }

    @Override
    public int getCount(LivingEntity entity) {
        return 0;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public void setPotency(LivingEntity entity, int potency) {

        if (potency > 6)
            potency = 6;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, potency * 20, 0));
        } else {
            if (entity.getEffect(this).getDuration()/20 >= potency)
                return;
            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(this,  potency * 20, 0));
        }
        syncEffect(entity);
    }

    public void increment(LivingEntity entity, int potency) {
        if (potency > 6)
            potency = 6;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, potency * 20, 0));
        } else {
            entity.getEffect(this).update(new EffectInstance(this, potency * 20, 0));
        }
        syncEffect(entity);
    }
}
