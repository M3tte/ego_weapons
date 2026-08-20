//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.particle.EpicFightParticles;

public class ShellEffect extends PotencyOnlyStatus {
    public ShellEffect() {
        super(EffectType.BENEFICIAL, "shell",-16777216);
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        World world = entity.level;
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
                    (entity.getZ()), (int) 2, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
        }
    }


}
