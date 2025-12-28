//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCapabilityPresets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;

public class TheDepartedButterflyEffect extends CountPotencyStatus {
    public TheDepartedButterflyEffect() {
        super(EffectType.HARMFUL, "the_departed_b",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.butterfly_departed";
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

        if (duration < 3 && !entity.level.isClientSide()) {

            EgoWeaponsEffects.SINKING.get().increment(entity, 0, amplifier+1);
            entity.removeEffect(this);

            if (entity.hasEffect(EgoWeaponsEffects.THE_LIVING.get())) {
                entity.addEffect(new EffectInstance(EgoWeaponsEffects.THE_DEPARTED.get(), 300, EgoWeaponsEffects.THE_LIVING.get().getPotency(entity)-1));
                entity.removeEffect(EgoWeaponsEffects.THE_LIVING.get());
            }

            syncEffect(entity);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void applyOnHit(LivingEntity target, LivingEntity source) {

        int potency = EgoWeaponsEffects.SINKING.get().getPotency(target);
        int departed = EgoWeaponsEffects.THE_DEPARTED.get().getPotency(target);
        float mult = (float) target.getAttributeValue(EgoWeaponsAttributes.BLACK_RESISTANCE.get());

        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.NumberLabelParticle(target.position().add(target.getRandom().nextFloat() - 0.5f,1,target.getRandom().nextFloat() - 0.5f), NumberParticleTypes.BUTTERFLY, (int) (((potency * departed) / 4f) * mult)));


        target.hurt(new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.HIDDEN, GenericEgoDamage.DamageTypes.BLACK, "sinking"), (float) (potency * departed) / 4f);
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

        if (entity.hasEffect(EgoWeaponsEffects.THE_LIVING.get())) {
            baseTime = entity.getEffect(EgoWeaponsEffects.THE_LIVING.get()).getDuration() - 3;
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
            entity.addEffect(new EffectInstance(this, 300, potency-1));
        }
    }
}


