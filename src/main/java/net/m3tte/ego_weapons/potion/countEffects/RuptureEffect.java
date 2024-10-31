//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;

public class RuptureEffect extends CountPotencyStatus {
    public RuptureEffect() {
        super(EffectType.HARMFUL, "rupture",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.rupture";
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void applyOnHit(LivingEntity target) {
        target.hurt(DamageSource.GENERIC,0.1f);
        target.setHealth(target.getHealth() - EgoWeaponsEffects.RUPTURE.get().getPotency(target));


        ((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.SUNSHOWER_OPEN.get(), (target.getX()), (target.getY() + target.getBbHeight() / 2),
                (target.getZ()), 1, 0, 0, 0, 0);

        EgoWeaponsEffects.RUPTURE.get().decrement(target, 1, 0);
    }
}
