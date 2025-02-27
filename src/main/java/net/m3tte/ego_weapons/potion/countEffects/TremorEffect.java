//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Iterator;
import java.util.Objects;

public class TremorEffect extends CountPotencyStatus {
    public TremorEffect() {
        super(EffectType.HARMFUL, "tremor",-16777216);
    }

    public TremorEffect(EffectType effectType, String name, int i) {
        super(effectType, name, i);
    }

    @Override
    public String getDescriptionId() {
        return "effect.tremor";
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




        // Tick tremor every 5 seconds
        if (entity.tickCount % 100 == 1 && entity.level instanceof ServerWorld) {
            duration -= 20;

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

    public void amplitudeConversion(LivingEntity entity) {
        TremorEffect tremorType = detectTremorType(entity);

        if (tremorType != null & tremorType != this) {
            EffectInstance inst = entity.getEffect(tremorType);
            assert inst != null;
            this.increment(entity, tremorType.getCount(inst), tremorType.getPotency(inst));
            entity.removeEffect(tremorType);
        }
    }

    public void burst(LivingEntity entity) {
        if (!entity.hasEffect(this))
            return;

        int potency = entity.hasEffect(this) ? this.getPotency(Objects.requireNonNull(entity.getEffect(this))) : 0;

        if (potency > 0) {
            StaggerSystem.reduceStagger(entity, potency, true);
        }

        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.NumberLabelParticle(entity.position().add(entity.getRandom().nextFloat() - 0.5f,1,entity.getRandom().nextFloat() - 0.5f), NumberParticleTypes.TREMOR, potency));
        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendShakeMessage(entity.getId(), 2 + (potency / 55f)));
        if (!entity.level.isClientSide()) {
            entity.level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                    EgoWeaponsSounds.TREMOR_BURST,
                    SoundCategory.NEUTRAL, 1f, (float) 1);
        }
    }

    public static void burstTremor(LivingEntity entity, boolean decrement) {
        TremorEffect tremorType = detectTremorType(entity);

        if (tremorType == null) {
            return;
        }
        tremorType.burst(entity);

        if (decrement)
            tremorType.decrement(entity, 1, 0);
    }

    public static TremorEffect incrementTremor(LivingEntity entity, int count, int potency) {
        TremorEffect tremorType = detectTremorType(entity);

        if (tremorType == null) {
            tremorType = (TremorEffect) EgoWeaponsEffects.TREMOR.get();
        }
        tremorType.increment(entity, count, potency);

        return tremorType;
    }

    public static void decrementTremor(LivingEntity entity, int count, int potency) {
        TremorEffect tremorType = detectTremorType(entity);

        if (tremorType == null) {
            return;
        }
        tremorType.decrement(entity, count, potency);
    }

    public static TremorEffect detectTremorType(LivingEntity entity) {

        for (EffectInstance effect : entity.getActiveEffects()) {
            if (effect.getEffect() instanceof TremorEffect) {
                return (TremorEffect) effect.getEffect();
            }
        }

        return null;
    }
}
