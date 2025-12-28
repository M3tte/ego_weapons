package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeRepMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils.applyBlipCooldown;

public class FullstopWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("full_stop_to_life");

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
        return "Full Stop\nto Life";
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


            Hand hand = EgoWeaponsItems.FULLSTOP_REP_MACHETE.get().equals(player.getItemInHand(Hand.MAIN_HAND).getItem()) ? Hand.OFF_HAND : Hand.MAIN_HAND;

            int ammoCount = AmmoSystem.getAmmoCount(player.getItemInHand(hand));

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 70;

            player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 70, 0));

            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.fullstop_pistol.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);


            Vector3d lookV = player.getLookAngle();
            EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 2, 1);
            if (ammoCount >= 3) {
                entitypatch.playAnimationSynchronized(FullstopOfficeRepMovesetAnims.FULLSTOP_SPECIAL_G, 0.1f);

            } else {
                entitypatch.playAnimationSynchronized(FullstopOfficeRepMovesetAnims.FULLSTOP_SPECIAL_B, 0.1f);
                if (!world.isClientSide())
                    ((World) world).playSound(null, player.blockPosition().offset(lookV.x * 1.5, lookV.y * 1.5, lookV.z * 1.5),
                            (net.minecraft.util.SoundEvent) EgoWeaponsSounds.FULLSTOP_FULL_STOP_TO_LIFE,
                            SoundCategory.PLAYERS, (float) 3, (float) 1);
            }


            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            applyBlipCooldown(4, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }


}
