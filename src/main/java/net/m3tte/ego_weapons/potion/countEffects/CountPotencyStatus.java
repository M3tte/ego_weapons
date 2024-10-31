package net.m3tte.ego_weapons.potion.countEffects;


import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Objects;

public abstract class CountPotencyStatus extends Effect {

    protected final ResourceLocation icon;
    public CountPotencyStatus(EffectType category, String potionName, int color) {
        super(category, color);
        this.icon = new ResourceLocation("tcorp", "textures/mob_effect/" + potionName + ".png");
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Prevents effect from ticking down at all.

        int duration = Objects.requireNonNull(entity.getEffect(this)).getDuration();

        if (duration % 20 != 0) {
            if (duration % 20 < 10) {
                duration -= duration % 20;
            } else  {
                duration = duration - duration % 20 + 20;
            }
        }


        Objects.requireNonNull(entity.getEffect(this))
                .update(new EffectInstance(this,  duration + 3, amplifier));

        if (entity.tickCount % 200 == 0)
            this.syncEffect(entity);
    }

    public void syncEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            for (ServerPlayerEntity p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> p), new EgoWeaponsModVars.SyncCountEffectMessage((LivingEntity) entity, ((LivingEntity) entity).getEffect(this)));
            }
        }
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public int getCount(EffectInstance ef) {
        return Math.min(ef.getDuration() / 20,99);
    }

    public int getPotency(EffectInstance ef) {
        return ef.getAmplifier()+1;
    }

    public void increment(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, (Math.max(count,1)) * 20, Math.max(potency-1,0)));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,99);
            count = Math.min(entity.getEffect(this).getDuration()/20 + count,99);

            entity.getEffect(this).update(new EffectInstance(this, count * 20, potency));

            syncEffect(entity);
        }
    }

    public void decrement(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;

        if (entity.hasEffect(this)) {
            potency = Math.min(entity.getEffect(this).getAmplifier() - potency,99);
            count = Math.min(entity.getEffect(this).getDuration()/20 - count,99);
            entity.removeEffect(this);
            if (count > 0 && potency >= 0) {
                entity.addEffect(new EffectInstance(this, count * 20, potency));
                syncEffect(entity);
            }
        }
    }

    public int getCount(LivingEntity entity) {
        if (!entity.hasEffect(this))
            return 0;
        return this.getCount(entity.getEffect(this));
    }

    public int getPotency(LivingEntity entity) {
        if (!entity.hasEffect(this))
            return 0;
        return this.getPotency(entity.getEffect(this));
    }

}
