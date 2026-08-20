//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.particle.EpicFightParticles;

public class ImitationEffect extends PotencyOnlyStatus {
    public ImitationEffect() {
        super(EffectType.BENEFICIAL, "imitation",-16777216, false, 10, 600);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.level;
        if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
            entity.playSound(SoundEvents.CHORUS_FLOWER_GROW, 1, 1);

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                        (entity.getZ()), (int) 20, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
            }

            decrement(entity, 0, 1);
        }

    }

    @Override
    public void increment(LivingEntity entity, int cap, int potency) {

        if (cap == 0)
            cap = 99;

        // Decay time is 5x longer than default for nothing there due to it being harder to stack
        // Otherwise it is the default 600 ticks
        int duration = entity instanceof NothingThere2Entity ? 1500 : getEffectDuration();


        if (entity.level.isClientSide)
            return;
        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, duration, Math.min(Math.min(getLimit()-1,cap-1),Math.max(potency,0))));
        } else {
            potency = Math.min(entity.getEffect(this).getAmplifier() + potency,Math.min(getLimit()-1,cap-1));

            entity.getEffect(this).update(new EffectInstance(this, duration, potency));
        }
        syncEffect(entity);
    }
}
