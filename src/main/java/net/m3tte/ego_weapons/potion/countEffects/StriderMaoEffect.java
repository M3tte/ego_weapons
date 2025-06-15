//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
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

public class StriderMaoEffect extends CountPotencyStatus {
    public StriderMaoEffect() {
        super(EffectType.BENEFICIAL, "strider_mao",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.strider_mao";
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




    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.

        // Less than 4 Haste

        int hasteCount = EgoWeaponsEffects.SPEED_UP.get().getPotency(entity);
        if (hasteCount < 5) {
            entity.removeEffect(EgoWeaponsEffects.SPEED_UP.get());
            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(EgoWeaponsEffects.SPEED_UP.get(), 300, hasteCount + 4));
            if (amplifier > 0)
                entity.addEffect(new EffectInstance(this, 999999, amplifier - 1));
        }

        if (!entity.level.isClientSide()) {
            if (entity.tickCount % (6 - amplifier * 2) == 0)
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.MAO_PARTICLE.get(), 1, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), entity.getId(), 0, 0, 0.5f, 1f, 0.5f));

        }

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
            entity.addEffect(new EffectInstance(this, 999999, potency-1));
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
            entity.addEffect(new EffectInstance(this, 9999999, potency-1));
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
