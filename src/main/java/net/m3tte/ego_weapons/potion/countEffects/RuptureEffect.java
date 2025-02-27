//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

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
        float potency = EgoWeaponsEffects.RUPTURE.get().getPotency(target);

        // If entity has shield / absorption, decrement that first.
        if (target.getAbsorptionAmount() > 0) {
            if (target.getAbsorptionAmount() > potency) {
                target.setAbsorptionAmount(target.getAbsorptionAmount()-potency);
                potency = 0;
            } else {
                potency -= target.getAbsorptionAmount();
                target.setAbsorptionAmount(0);
            }
        }

        target.setHealth(target.getHealth() - potency);
        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.NumberLabelParticle(target.position().add(target.getRandom().nextFloat() - 0.5f,1,target.getRandom().nextFloat() - 0.5f), NumberParticleTypes.RUPTURE, potency));


        ((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.SUNSHOWER_OPEN.get(), (target.getX()), (target.getY() + target.getBbHeight() / 2),
                (target.getZ()), 1, 0, 0, 0, 0);

        EgoWeaponsEffects.RUPTURE.get().decrement(target, 1, 0);
    }
}
