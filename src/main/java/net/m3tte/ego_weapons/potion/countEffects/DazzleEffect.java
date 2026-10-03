//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;

public class DazzleEffect extends PotencyOnlyStatus {
    public DazzleEffect() {
        super(EffectType.HARMFUL, "dazzle",-16777216, false, 10, 600);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.level;
        if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
            decrement(entity, 0, 1);
        }

        if (!entity.hasEffect(EgoWeaponsEffects.SPEED_DOWN.get())) {
            EgoWeaponsEffects.SPEED_DOWN.get().increment(entity, 3, 3);
        }

    }
}
