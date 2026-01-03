package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.gameasset.movesets.RatShankMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils.applyBlipCooldown;

public class RatKnifeWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("throat_slash");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }


    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Throat   \nSlash";
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
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.rat_shank.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);


            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;
            EgoWeaponsEffects.SPEED_UP.get().increment(player, 0, 1);
            player.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("followUpHit");
            player.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().remove("stickEntityId");
            entitypatch.playAnimationSynchronized(RatShankMovesetAnims.RAT_KNIFE_SPECIAL_1, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            applyBlipCooldown(8, playerVars);
            playerVars.syncPlayerVariables(player);
        }


    }


}
