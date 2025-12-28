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
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

public class SinkingEffect extends CountPotencyStatus {
    public SinkingEffect() {
        super(EffectType.HARMFUL, "sinking",-16777216);
    }

    @Override
    public String getDescriptionId() {
        return "effect.sinking";
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
    public int getCount(LivingEntity entity) {
        int actualSinking = super.getCount(entity);
        int departed = EgoWeaponsEffects.THE_DEPARTED.get().getPotency(entity);

        return  Math.max(actualSinking,departed);
    }

    @Override
    public int getPotency(LivingEntity entity) {

        int actualSinking = super.getPotency(entity);
        int living = EgoWeaponsEffects.THE_LIVING.get().getPotency(entity);

        return  Math.max(actualSinking,living);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void applyOnHit(LivingEntity target, LivingEntity source) {
        int potency = EgoWeaponsEffects.SINKING.get().getPotency(target);
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;
            SanitySystem.damageSanity(player, potency);
        } else {




            target.hurt(DamageSource.GENERIC,potency);
        }



        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.NumberLabelParticle(target.position().add(target.getRandom().nextFloat() - 0.5f,1,target.getRandom().nextFloat() - 0.5f), NumberParticleTypes.SINKING, potency));

        ((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.SINKING_APPLY.get(), (target.getX()), (target.getY() + target.getBbHeight() / 2),
                (target.getZ()), 1, 0, 0, 0, 0);

        EgoWeaponsEffects.SINKING.get().decrement(target, 1, 0);
    }
}
