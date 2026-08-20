//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.UUID;

public class UdjatVanguard extends CountPotencyStatus {
    public UdjatVanguard() {
        super(EffectType.BENEFICIAL, "udjat_vanguard",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.udjat_vanguard";
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
        World world = entity.level;

        int udjatStack = amplifier + 1;

        if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(this, 400, amplifier-1));


        }

        int protPotency = EgoWeaponsEffects.PROTECTION.get().getPotency(entity);

        if (protPotency < udjatStack) {
            int remainingEffectDuration = entity.getEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get()).getDuration();

            if (remainingEffectDuration > 300 || udjatStack > 1) {
                entity.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), 300, Math.min(1,udjatStack-1)));
            } else {
                entity.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), remainingEffectDuration, Math.min(1,udjatStack-1)));
            }
        }

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(EgoWeaponsParticles.UDJAT_SYMBOLS.get(), (entity.getX()), (entity.getY() + entity.getEyeHeight() - 0.15f),
                    (entity.getZ()), (int) 1, 0.2f, 0.2f, 0.2f, 0);
        }

    }

    @Override
    public void increment(LivingEntity entity, int limit, int potency) {
        if (entity.level.isClientSide)
            return;

        int hardLimit = 3;

        if (entity instanceof PlayerEntity) {
            hardLimit = Math.min(3, EmotionSystem.getEmotionLevel((PlayerEntity) entity));
        }

        if (limit == 0 || limit >= hardLimit)
            limit = hardLimit;

        if (potency > limit)
            potency = limit;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, 400, potency-1));
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
            entity.addEffect(new EffectInstance(this, 300, previousPotency - potency -1));
        }
    }

    static AttributeModifier speedModifier = new AttributeModifier(UUID.fromString("fc415d98-930e-4b92-88d9-6cb68ebff984"), "speedMod", 0.07, AttributeModifier.Operation.MULTIPLY_BASE);
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }
}
