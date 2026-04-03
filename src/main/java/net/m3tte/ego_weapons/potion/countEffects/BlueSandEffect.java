//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;

public class BlueSandEffect extends CountPotencyStatus {
    public BlueSandEffect() {
        super(EffectType.HARMFUL, "blue_sand",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.blue_sand";
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

            if (!entity.level.isClientSide()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.UDJAT_BLUE_SAND.get(), 1, entity.getX(), entity.getY() + entity.getEyeHeight() - 0.1f, entity.getZ(), entity.getId(), 0.1, 0, 0.0,0.0,0.0));
            }
        }

    }

    static AttributeModifier whiteResistanceMod = new AttributeModifier(UUID.fromString("fb214f98-930e-4b92-83d1-6ce88ebfc984"), "whiteResistanceMod", 0.1f, AttributeModifier.Operation.ADDITION);
    static AttributeModifier speedModifier = new AttributeModifier(UUID.fromString("fc414a98-921e-4b92-87d9-6ce88ebff984"), "speedModSand", -0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    static AttributeModifier impactMod = new AttributeModifier(UUID.fromString("fb414f98-930e-4b93-88d9-6ce88ebfc942"), "powerDownImpact", -0.4f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);


        ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);
        ModifiableAttributeInstance impactInstance = attrman.getInstance(EpicFightAttributes.IMPACT.get());
        ModifiableAttributeInstance redDMGInstance = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());

        if (impactInstance != null) {
            impactInstance.removeModifier(impactMod);
            impactInstance.addPermanentModifier(new AttributeModifier(impactMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (0), impactMod), impactMod.getOperation()));
        }

        if (speedInst != null) {
            speedInst.removeModifier(speedModifier);
            speedInst.addPermanentModifier(new AttributeModifier(speedModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue((int) (0), speedModifier), speedModifier.getOperation()));
        }

        if (redDMGInstance != null) {
            redDMGInstance.removeModifier(whiteResistanceMod);
            redDMGInstance.addPermanentModifier(new AttributeModifier(whiteResistanceMod.getId(), this.getDescriptionId() + " " + 0, whiteResistanceMod.getAmount(), whiteResistanceMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);
        ModifiableAttributeInstance impactInstance = attrman.getInstance(EpicFightAttributes.IMPACT.get());
        ModifiableAttributeInstance whiteDmg = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());

        if (whiteDmg != null)
            whiteDmg.removeModifier(whiteResistanceMod);

        if (speedInst != null)
            speedInst.removeModifier(speedModifier);

        if (impactInstance != null)
            impactInstance.removeModifier(impactMod);

        attrman.save();
    }


    @Override
    public void increment(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 200, Math.min(Math.min(2,cap),Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(2,cap));

            entity.getEffect(this).update(new EffectInstance(this, 200, potency));

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
                entity.addEffect(new EffectInstance(this, 200, potency));
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

}
