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
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.IndirectSimpleEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

public class DeathriteHasteEffect extends CountPotencyStatus {
    public DeathriteHasteEffect() {
        super(EffectType.HARMFUL, "deathrite_haste",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.deathrite_haste";
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
    public int getCount(EffectInstance ef) {
        return 0;
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
            entity.addEffect(new EffectInstance(this, 999999, potency-1));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void applyOnHit(LivingEntity target, LivingEntity inflictor) {
        target.hurt(DamageSource.GENERIC,0.1f);
        float potency = EgoWeaponsEffects.DEATHRITE_HASTE.get().getPotency(target);
        // If entity has shield / absorption, decrement that first.
        EgoWeaponsEffects.RUPTURE.get().increment(target, 1, 0);

        if (potency <= 1) {
            float rupturePot = EgoWeaponsEffects.RUPTURE.get().getPotency(target);
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.NumberLabelParticle(target.position().add(target.getRandom().nextFloat() - 0.5f,1,target.getRandom().nextFloat() - 0.5f), NumberParticleTypes.DEATHRITE_HASTE, rupturePot));
            target.hurt(new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.HIDDEN, GenericEgoDamage.DamageTypes.PALE, "mao_branch_sword"), rupturePot);
            target.removeEffect(EgoWeaponsEffects.DEATHRITE_HASTE.get());
        } else {
            EgoWeaponsEffects.DEATHRITE_HASTE.get().decrement(target, 0, 1);
        }

    }
}
