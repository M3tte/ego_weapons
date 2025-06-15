package net.m3tte.ego_weapons.procedures.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class FirefistWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("firefist");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.HE;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Firefist";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= getBlipCost(player, playerVars)) {


            playerVars.light -= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;

            player.getItemBySlot(EquipmentSlotType.MAINHAND).getOrCreateTag().remove("ffkilled");
            player.inventory.setChanged();

            player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));
            if (player.hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get()))
                entitypatch.playAnimationSynchronized(FirefistMovesetAnims.FIREFIST_SPECIAL_1B, 0.1f);
            else
                entitypatch.playAnimationSynchronized(FirefistMovesetAnims.FIREFIST_SPECIAL_1, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            applyBlipCooldown(8, playerVars);
            playerVars.syncPlayerVariables(player);
        }


    }


}
