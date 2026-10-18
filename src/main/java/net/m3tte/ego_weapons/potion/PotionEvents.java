package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EgoWeaponsMod.MODID)
public class PotionEvents {

    // Event hook when a potion effect fully expires.
    @SubscribeEvent
    public static void onPotionExpire(PotionEvent.PotionExpiryEvent event) {
        LivingEntity entity = event.getEntityLiving();
        EffectInstance effect = event.getPotionEffect();

        if (effect != null) {
            if (effect.getEffect() instanceof CountPotencyStatus) {
                ((CountPotencyStatus) effect.getEffect()).expirationEffect(entity);
            }
        }

    }
}
