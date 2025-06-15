//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.item.firefist.FirefistGauntlet;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import java.util.Random;

public class FuelIgnitionEffect extends CountPotencyStatus {
    public FuelIgnitionEffect() {
        super(EffectType.BENEFICIAL, "fuel_ignition",-16777216);
    }
    @Override
    public String getDescriptionId() {
        return "effect.fuel_ignition";
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }



    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Random ran = entity.getRandom();
        if (entity.tickCount % 2 == 0)
            entity.level.addParticle(ParticleTypes.LAVA, entity.getX() + ran.nextFloat() - 0.5f, entity.getY() + ran.nextFloat() + 0.5f, entity.getZ() + ran.nextFloat() - 0.5f, 0, 0, 0);

        for (int i = 0; i < 2; i++) {
            entity.level.addParticle(EgoWeaponsParticles.SIMPLE_EMBER.get(), (entity.getX() + entity.getRandom().nextFloat() * 1 - 0.5f), (entity.getY() + entity.getRandom().nextFloat() * 1 - 0.5f + entity.getBbHeight() / 2),
                    (entity.getZ() + entity.getRandom().nextFloat() * 1 - 0.5f), 0.05, 0.4f, 1);
        }


        if (!entity.hasEffect(this))
            return;

        if (entity.getEffect(this).getDuration() < 5) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                ItemStack mask = player.getItemBySlot(EquipmentSlotType.HEAD);
                if (!mask.isEmpty()) {
                    player.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                    player.inventory.add(mask);
                    player.inventory.setChanged();
                }

                int fuel = FirefistGauntlet.getFuel(entity);

                if (fuel > 5) {
                    EgoWeaponsEffects.BURN.get().increment(entity, 0,fuel/5);
                    FirefistGauntlet.reduceFuel(entity, fuel);
                }


                entity.removeEffect(this);
            }
        }
    }

    @Override
    public int getPotency(EffectInstance ef) {
        return super.getCount(ef) + 1;
    }

    @Override
    public int getPotency(LivingEntity entity) {
        return super.getCount(entity) + 1;
    }

    @Override
    public int getCount(EffectInstance ef) {
        return 0;
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }


}
