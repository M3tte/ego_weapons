//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class SheutFractureEffect extends CountPotencyStatus {
    public SheutFractureEffect() {
        super(EffectType.HARMFUL, "sheut_fracture",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.sheut_fracture";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (entity.hasEffect(this)) {

            if (amplifier >= 9) {

                entity.removeEffect(this);

                EgoWeaponsEffects.WHITE_FRAGILITY.get().increment(entity, 10, 1);

                int sinking = EgoWeaponsEffects.SINKING.get().getPotency(entity);

                if (sinking >= 0) {
                    DamageSource src1 = new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.HIDDEN, GenericEgoDamage.DamageTypes.WHITE, "sheut_fracture");
                    entity.hurt(src1, sinking);


                    if (!entity.level.isClientSide()) {
                        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.NumberLabelParticle(entity.position().add(entity.getRandom().nextFloat() - 0.5f,1,entity.getRandom().nextFloat() - 0.5f), NumberParticleTypes.SHEUT_FRACTURE, DamageResistanceSystem.processDamageForEntity(entity, null, 1, src1, StaggerSystem.isStaggered(entity)) * sinking));

                    }
                }

                syncEffect(entity);

                return;
            }

            if (entity.getEffect(this).getDuration() < 10) {
                decrement(entity, 0, 1);
            }




        }

    }

    static AttributeModifier whiteResistanceMod = new AttributeModifier(UUID.fromString("fb214f98-930e-4b92-83d1-adc88ebfc984"), "whiteResistanceMod", 0.01f, AttributeModifier.Operation.ADDITION);

    @Override
    public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.addAttributeModifiers(living, attrman, amplifier);


        ModifiableAttributeInstance whiteDMGInstance = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());


        if (whiteDMGInstance != null) {
            whiteDMGInstance.removeModifier(whiteResistanceMod);
            whiteDMGInstance.addPermanentModifier(new AttributeModifier(whiteResistanceMod.getId(), this.getDescriptionId() + " " + 0, whiteResistanceMod.getAmount() * (amplifier + 1D), whiteResistanceMod.getOperation()));
        }

        attrman.save();
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
        super.removeAttributeModifiers(living, attrman, amplifier);
        ModifiableAttributeInstance whiteDmg = attrman.getInstance(EgoWeaponsAttributes.WHITE_RESISTANCE.get());

        if (whiteDmg != null)
            whiteDmg.removeModifier(whiteResistanceMod);

        attrman.save();
    }


    @Override
    public void increment(LivingEntity entity, int cap, int potency) {
        if (entity.level.isClientSide)
            return;


        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 200, Math.min(Math.min(9,cap),Math.max(potency-1,0))));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(9,cap));

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
