//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.potion.countEffects;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.network.PacketDistributor;

public class DeathriteHasteEffect extends PotencyOnlyStatus {
    public DeathriteHasteEffect() {
        super(EffectType.HARMFUL, "deathrite_haste",-16777216, true, 99, 1000);
    }

    public static void applyOnHit(LivingEntity target, LivingEntity inflictor) {
        target.hurt(DamageSource.GENERIC,0.1f);
        float potency = EgoWeaponsEffects.DEATHRITE_HASTE.get().getPotency(target);
        // If entity has shield / absorption, decrement that first.
        EgoWeaponsEffects.RUPTURE.get().increment(target, 1, 0);

        if (potency <= 1) {
            float rupturePot = EgoWeaponsEffects.RUPTURE.get().getPotency(target);
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.NumberLabelParticle(target.position().add(target.getRandom().nextFloat() - 0.5f,1,target.getRandom().nextFloat() - 0.5f), NumberParticleTypes.DEATHRITE_HASTE, rupturePot));
            target.hurt(new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.HIDDEN, GenericEgoDamage.DamageTypes.PALE, "mao_branch_sword"), rupturePot);
            target.removeEffect(EgoWeaponsEffects.DEATHRITE_HASTE.get());
        } else {
            EgoWeaponsEffects.DEATHRITE_HASTE.get().decrement(target, 0, 1);
        }

    }
}
