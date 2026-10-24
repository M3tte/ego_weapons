package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.LCARifleMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.LampCrossbowMovesetAnims;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
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

public class LampCrossbowWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return deductLightDecreases(player, AbilityUtils.AbilityType.WEAPON,6);
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("salvation_lamp");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier(PlayerEntity player, PlayerVariables playerVars) {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return  " Salvation for those                    \n" +
                "Enchanted by the Lamp                      ";
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




            int ammo = AmmoSystem.getAmmoCount(player.getItemInHand(Hand.MAIN_HAND));

            if (ammo < 4) {

                int reloaded = AmmoSystem.forceReloadGun(player, player.getItemInHand(Hand.MAIN_HAND), 4, 4, AmmoType.LAMP_CROSSBOW_BOLT);

                if (!world.isClientSide()) {

                    world.playSound(null, player,
                            EgoWeaponsSounds.PAPER_FLIP,
                            SoundCategory.PLAYERS, (float) 1, (float) 1.5);
                }
            }

            LivingEntity dazzleTarget = null;

            List<LivingEntity> nearbyEntities = SharedFunctions.getNearbyEntities(player, 20, 20, TeamLockedPredicate.ONLY_HOSTILES.additionalCondition((ent) -> {
                if (ent instanceof LivingEntity) {
                    return ((LivingEntity) ent).hasEffect(EgoWeaponsEffects.DAZZLE.get());
                }
                return false;
            }));

            if (!nearbyEntities.isEmpty()) {
                dazzleTarget = nearbyEntities.get(0);
            }

            // player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 20, 0));
            if (dazzleTarget instanceof LivingEntity) {
                LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) dazzleTarget.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                SharedFunctions.staggerEntity(targetPatch, 1, false);

                if (player.level instanceof ServerWorld) {
                    ((ServerWorld) player.level).sendParticles(EgoWeaponsParticles.LAMP_EMBERS_INV.get(), dazzleTarget.getX(), (dazzleTarget.getY() + dazzleTarget.getEyeHeight()), dazzleTarget.getZ(), 20, 0.1, 0.1, 0.1, 1);
                }

                entitypatch.playAnimationSynchronized(LampCrossbowMovesetAnims.LAMP_CB_SP_1B, 0.00f);
                boolean success = DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.lamp_crossbow.special_dazzle", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                if (!world.isClientSide() && success) {
                    world.playSound(null, player,
                            EgoWeaponsSounds.LAMP_CROSSBOW_VOICE_SPECIAL_DAZZLE,
                            SoundCategory.PLAYERS, (float) 1, (float) 1);
                }

            } else {
                boolean success = DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.lamp_crossbow.special", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                if (!world.isClientSide() && success) {
                    world.playSound(null, player,
                            EgoWeaponsSounds.LAMP_CROSSBOW_VOICE_SPECIAL,
                            SoundCategory.PLAYERS, (float) 1, (float) 1);
                }
                entitypatch.playAnimationSynchronized(LampCrossbowMovesetAnims.LAMP_CB_SP_1, 0.00f);

            }
            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
            AbilityUtils.applyBlipCooldown(8, playerVars);
            playerVars.syncPlayerVariables(player);

        }
    }


}
