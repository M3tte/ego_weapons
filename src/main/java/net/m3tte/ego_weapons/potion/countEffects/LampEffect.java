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

public class LampEffect extends PotencyOnlyStatus {
    public LampEffect() {
        super(EffectType.BENEFICIAL, "lamp",-16777216, false, 20, 600);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.level;
        if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
            decrement(entity, 0, 1);
        }

    }
}
