//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.network.PacketDistributor;

public class StriderMaoEffect extends PotencyOnlyStatus {
    public StriderMaoEffect() {
        super(EffectType.BENEFICIAL, "strider_mao",-16777216, false, 3, 999999);
    }

    @Override
    public String getDescriptionId() {
        return "effect.strider_mao";
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }




    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // No ticking needed as time is handled normally.

        // Less than 4 Haste

        int hasteCount = EgoWeaponsEffects.SPEED_UP.get().getPotency(entity);
        if (hasteCount < 5) {
            entity.removeEffect(EgoWeaponsEffects.SPEED_UP.get());
            entity.removeEffect(this);
            entity.addEffect(new EffectInstance(EgoWeaponsEffects.SPEED_UP.get(), 300, hasteCount + 4));
            if (amplifier > 0)
                entity.addEffect(new EffectInstance(this, 999999, amplifier - 1));
        }

        if (!entity.level.isClientSide()) {
            if (entity.tickCount % (6 - amplifier * 2) == 0)
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.MAO_PARTICLE.get(), 1, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), entity.getId(), 0, 0, 0.5f, 1f, 0.5f));
        }

    }
}
