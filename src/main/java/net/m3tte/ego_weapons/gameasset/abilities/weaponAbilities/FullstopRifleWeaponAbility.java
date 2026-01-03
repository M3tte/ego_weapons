package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class FullstopRifleWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("bullseye");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Bullseye";
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



            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }



            int ammoCount = AmmoSystem.getAmmoCount(player.getItemInHand(Hand.MAIN_HAND));
            boolean validAmmo = AmmoSystem.hasValidAmmoFor(player.getItemInHand(Hand.MAIN_HAND), player);

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;


            if (ammoCount >= 1 || validAmmo) {
                EgoWeaponsEffects.POISE.get().increment(player, 0, 7);
                player.addEffect(new EffectInstance(EgoWeaponsEffects.RESILIENCE.get(), 120, 3));
                player.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), 120, 2));
                EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 2, 1);
                playerVars.light -= getBlipCost(player, playerVars);

                DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.fullstop_rifle.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.BLUE);

                entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_SPECIAL, 0.01f);
            }


            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            AbilityUtils.applyBlipCooldown(8, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }


}
