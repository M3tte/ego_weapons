//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

public class DarkFlameEffect extends CountPotencyStatus {
    public DarkFlameEffect() {
        super(EffectType.HARMFUL, "dark_burn",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.dark_burn";
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


        if (duration > 140) {
            shouldUpdate = true;
            duration = 140;
        }


        if (duration % 20 != 0) {
            if (duration % 20 < 10) {
                duration -= duration % 20;
            } else  {
                duration = duration - duration % 20 + 20;
            }
        }




        // Proc burn every 5 seconds.
        // Only decrements on itself if no burn
        if (!entity.hasEffect(EgoWeaponsEffects.BURN.get())) {
            if (entity.tickCount % 100 == 0 && entity.level instanceof ServerWorld) {
                duration -= 20;

                shouldUpdate = true;
                entity.removeEffect(this);
            }
        }




        if (duration > 6) {

            entity.addEffect(new EffectInstance(this,  duration + 1, amplifier));

            if (shouldUpdate)
                this.syncEffect(entity);

        }

    }

    @Override
    public int getPotency(EffectInstance ef) {
        return super.getCount(ef);
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public void setPotency(LivingEntity entity, int potency) {

        if (potency > 6)
            potency = 6;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, potency * 20, 0));
        } else {
            if (entity.getEffect(this).getDuration()/20 >= potency)
                return;
            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(this,  potency * 20, 0));
        }
        syncEffect(entity);
    }

    public void increment(LivingEntity entity, int potency) {
        if (potency > 6)
            potency = 6;

        if (!entity.hasEffect(this)) {
            entity.addEffect(new EffectInstance(this, potency * 20, 0));
        } else {
            entity.getEffect(this).update(new EffectInstance(this, potency * 20, 0));
        }
        syncEffect(entity);
    }
}
