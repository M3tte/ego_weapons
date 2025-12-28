//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;

public class TheLivingButterflyEffect extends CountPotencyStatus {
    public TheLivingButterflyEffect() {
        super(EffectType.HARMFUL, "the_living_b",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.butterfly_living";
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
    public boolean displaysOnHudNormally() {
        return false;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        int duration = Objects.requireNonNull(entity.getEffect(this)).getDuration();

        if (duration < 300 && !entity.hasEffect(EgoWeaponsEffects.THE_DEPARTED.get()) && !entity.level.isClientSide()) {

            entity.addEffect(new EffectInstance(EgoWeaponsEffects.THE_DEPARTED.get(), 300, amplifier));

            entity.removeEffect(this);
            syncEffect(entity);
        }
    }



    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void applyOnHit(LivingEntity target, LivingEntity source) {

        int potency = EgoWeaponsEffects.THE_LIVING.get().getPotency(target);
        if (source instanceof PlayerEntity) {
            SanitySystem.healSanity((PlayerEntity) source, ((float) potency) / 4f);
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

        int baseTime = 300;

        if (entity.hasEffect(EgoWeaponsEffects.THE_DEPARTED.get())) {
            baseTime = entity.getEffect(EgoWeaponsEffects.THE_DEPARTED.get()).getDuration() + 3;
        }

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, baseTime, potency-1));
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
            entity.addEffect(new EffectInstance(this, 600, potency-1));
        }
    }
}


