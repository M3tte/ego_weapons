package net.m3tte.ego_weapons.potion.countEffects;


import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class CountPotencyStatus extends Effect {

    protected final ResourceLocation icon;
    public CountPotencyStatus(EffectType category, String potionName, int color) {
        super(category, color);
        this.icon = new ResourceLocation("ego_weapons", "textures/mob_effect/" + potionName + ".png");
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new LinkedList<>();
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
        if (entity instanceof LivingEntity && ((LivingEntity) entity).isAffectedByPotions()) {
            if (ServerLifecycleHooks.getCurrentServer() == null)
                return;


            for (ServerPlayerEntity p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> p), new EgoWeaponsModVars.SyncCountEffectMessage((LivingEntity) entity, ((LivingEntity) entity).getEffect(this)));
            }
        }
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    /***
     * Proxy instance that allows getting count via an effect instance.
     * @param ef effect instance to get.
     * @return Count, > 0, or 0 in the case of the effect not being present.
     */
    public int getCount(EffectInstance ef) {
        return Math.min(ef.getDuration() / 20,99);
    }

    /***
     * Proxy instance that allows getting potency via an effect instance.
     * @param ef effect instance to get.
     * @return Potency, Generally equals amplifier + 1 or 0 in the case of the effect not being present.
     */
    public int getPotency(EffectInstance ef) {
        return ef.getAmplifier()+1;
    }

    /***
     * Increases the targets count and / or potency by the given values. Limit is 99 for both values.
     * When first applying the effect, it starts out with at least one count.
     * ! For Potency only Effects (Such as Defense Up) the count represets the max potency that can be applied by this operation.
     *
     * @param entity Target entity that will get the effect
     * @param count Amount of count that is added to the effect. (Positive INT)
     * @param potency Amount of potency that is added to the effect (Positive INT)
     */
    public void increment(LivingEntity entity, int count, int potency) {
        if (entity.level.isClientSide)
            return;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, (Math.max(count,1)) * 20, Math.max(potency-1,0)));
            syncEffect(entity);
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,98);


            count = Math.min(entity.getEffect(this).getDuration()/20 + count,98);

            entity.getEffect(this).update(new EffectInstance(this, count * 20, potency));

            syncEffect(entity);
        }
    }

    /***
     * Decreases the targets count and / or potency by the given values.
     * if either count or potency reach 0 due to this operation, remove the effect.
     *
     * @param entity Target entity that will get the effect
     * @param count Amount of count that is added to the effect. (Positive INT)
     * @param potency Amount of potency that is added to the effect (Positive INT)
     */
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

    /***
     * Gets the targets count of an effect.
     * @param entity Target to get count for.
     * @return Potency, Generally equals amplifier + 1 or 0 in the case of the effect not being present.
     */
    public int getCount(LivingEntity entity) {
        if (!entity.hasEffect(this))
            return 0;
        return this.getCount(entity.getEffect(this));
    }

    /***
     * Gets the targets potency of an effect.
     * @param entity Target to get potency for.
     * @return Potency, Generally equals amplifier + 1 or 0 in the case of the effect not being present.
     */
    public int getPotency(LivingEntity entity) {
        if (!entity.hasEffect(this))
            return 0;
        return this.getPotency(entity.getEffect(this));
    }



}
