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
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;

import static net.m3tte.ego_weapons.world.capabilities.DamageResistanceSystem.calculateBurnResistanceFor;

public class BurnEffect extends CountPotencyStatus {
    public BurnEffect() {
        super(EffectType.HARMFUL, "burn",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.burn";
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

        if (duration % 20 != 0) {
            if (duration % 20 < 10) {
                duration -= duration % 20;
            } else  {
                duration = duration - duration % 20 + 20;
            }
        }



        // Proc burn every 5 seconds.
        if (entity.tickCount % 100 == 0 && !entity.level.isClientSide() && entity.tickCount > 0) {


            duration -= 20;
            float burnMult = 1;
            if (entity.hasEffect(EgoWeaponsEffects.DARK_BURN.get())) {

                burnMult += EgoWeaponsEffects.DARK_BURN.get().getPotency(entity);
                ((ServerWorld) entity.level).sendParticles(ShadowpuffParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), 6, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 2), (entity.getBbWidth() / 2.5), 0);
                ((ServerWorld) entity.level).sendParticles(EgoWeaponsParticles.DARK_BURN_APPLY.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), 1, 0, 0, 0, 0);
                EgoWeaponsEffects.DARK_BURN.get().decrement(entity, 1, 0);
            }
            else {

                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), amplifier * 2, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.05, 0.3f, 1.5f, 0.7f, 1f, 0.7f));

                ((ServerWorld) entity.level).sendParticles(ParticleTypes.FLAME, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), amplifier+1, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
                ((ServerWorld) entity.level).sendParticles(EgoWeaponsParticles.BURN_APPLY.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), 1, 0, 0, 0, 0);
            }


            float burnVal = (amplifier + 1)*(burnMult-1);
            float trueBurnVal = calculateBurnResistanceFor(entity, amplifier + 1);

            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.NumberLabelParticle(entity.position().add(entity.getRandom().nextFloat() - 0.5f,1,entity.getRandom().nextFloat() - 0.5f), burnMult > 1 ? NumberParticleTypes.DARK_FLAME : NumberParticleTypes.BURN, trueBurnVal + burnVal));

            // If entity has shield / absorption, decrement that first.
            if (entity.getAbsorptionAmount() > 0) {
                if (entity.getAbsorptionAmount() > trueBurnVal) {
                    entity.setAbsorptionAmount(entity.getAbsorptionAmount()-trueBurnVal);
                    trueBurnVal = 0;
                } else {
                    trueBurnVal -= entity.getAbsorptionAmount();
                    entity.setAbsorptionAmount(0);
                }
            }
            entity.setHealth(entity.getHealth() - trueBurnVal);
            entity.hurt(DamageSource.IN_FIRE, burnVal + 0.01f);

            StaggerSystem.reduceStagger(entity, trueBurnVal, true);

            shouldUpdate = true;
            entity.removeEffect(this);
        }


        if (duration > 10) {

            entity.addEffect(new EffectInstance(this,  duration + 1, amplifier));

            if (shouldUpdate)
                this.syncEffect(entity);

        }

    }



    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
