package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.LCARifleMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.UdjatKhopeshMovesetAnims;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.List;

public class UdjatKhopeshWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {

        return deductLightDecreases(player, AbilityUtils.AbilityType.WEAPON, 5);
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("jamadhar");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier(PlayerEntity player, PlayerVariables playerVars) {
        return isAlternativeSkill(player, playerVars) ? AbilityTier.ALEPH : AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return isAlternativeSkill(player, playerVars) ? "Rushdown Maneuver" : "Jamadhar";
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

        if (canTrigger(player, playerVars)) {

            if (isAlternativeSkill(player, playerVars)) {
                triggerOffhandVariant(player, playerVars);
                return;
            }


            playerVars.light -= getBlipCost(player, playerVars);
            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 100;

            player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
            entitypatch.playAnimationSynchronized(UdjatKhopeshMovesetAnims.KHOPESH_SPECIAL_1, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            AbilityUtils.applyBlipCooldown(8, playerVars);
            playerVars.syncPlayerVariables(player);

            EgoWeaponsEffects.PROTECTION.get().increment(player, 5, 2);

            applyBuffsToNearbyAllies(player);
        }
    }

    public boolean isAlternativeSkill(LivingEntity inEntity, PlayerVariables playerVars) {
        return (playerVars.firingMode) && (inEntity.getItemInHand(Hand.OFF_HAND).getItem().equals(EgoWeaponsItems.LCA_RIFLE.get()) && inEntity.getItemInHand(Hand.MAIN_HAND).getItem().equals(EgoWeaponsItems.LCA_UDJAT_KHOPESH.get()));
    }


    public void triggerOffhandVariant(PlayerEntity player, PlayerVariables playerVars) {

        playerVars.light -= getBlipCost(player, playerVars);
        World world = player.level;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        int potency = 1;
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
        }

        if (player.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get())) {
            int ammo = AmmoSystem.getAmmoCount(player.getItemInHand(Hand.OFF_HAND));


            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.lca_rifle.reload", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(x, y, z),
                        EgoWeaponsSounds.UDJAT_DIALOGUE_RELOADING,
                        SoundCategory.PLAYERS, (float) 1, (float) 1);
            }

            if (ammo < 4) {
                AmmoSystem.reloadGun(player.getItemInHand(Hand.OFF_HAND), null, player, 4);
            }

            if (!world.isClientSide()) {
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        EgoWeaponsSounds.FULLSTOP_REP_RELOAD,
                        SoundCategory.PLAYERS, (float) 1, (float) 1.5);
            }
        }

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        playerVars.globalcooldown = 100;

        player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
        entitypatch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_RIFLE_SPECIAL_B_1, 0.1f);
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
        AbilityUtils.applyBlipCooldown(8, playerVars);
        playerVars.syncPlayerVariables(player);

    }

    private static void applyBuffsToNearbyAllies(LivingEntity source) {
        List<LivingEntity> nearbyFriendlies = SharedFunctions.getLivingEntitiesRadius(source.level, source, source.position(), 16, TeamLockedPredicate.ONLY_ALLIES);
        //nearbyFriendlies.add(source);
        if (nearbyFriendlies.isEmpty())
            return;

        for (LivingEntity ent : nearbyFriendlies) {
            if (ent != source)
                EgoWeaponsEffects.PROTECTION.get().increment(ent, 5, 1);
        }
    }
}
