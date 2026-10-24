package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.ArdorBlossomMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.LampCrossbowMovesetAnims;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LampArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("lamp_card_armor_bg");
    }

    @Override
    public AbilityTier getAbilityTier(PlayerEntity player, PlayerVariables playerVars) {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Eyes Watching\nthe Dark Forest     ";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (canTrigger(player, playerVars)) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            playerVars.light -= getBlipCost(player, playerVars);



            List<LivingEntity> nearbyFriendlies = SharedFunctions.getNearbyEntities(player, 16, 4, TeamLockedPredicate.ONLY_ALLIES);

            boolean nearbyAllies = !nearbyFriendlies.isEmpty();

            if (nearbyAllies) {
                boolean dialogueSuccess = DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.lamp_suit.special.ally", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                if (!world.isClientSide() && dialogueSuccess) {
                    entitypatch.playSound(EgoWeaponsSounds.LAMP_ARMOR_VOICE_SPECIAL_ALLY, 1, 1f);
                }
            } else {
                boolean dialogueSuccess = DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.lamp_suit.special.alone", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
                if (!world.isClientSide() && dialogueSuccess) {
                    entitypatch.playSound(EgoWeaponsSounds.LAMP_ARMOR_VOICE_SPECIAL_ALONE, 1, 1f);
                }
            }

            if (!player.level.isClientSide() && entitypatch != null) {
                entitypatch.playAnimationSynchronized(LampCrossbowMovesetAnims.LAMP_ARMOR_SP, 0);
            }

            playerVars.syncPlayerVariables(player);
        }
    }

    public static boolean isBird(LivingEntity entity) {
        // TODO:: ADD THE HANDLING FOR THE OUTFITS TAGGED AS "BIRD" ONCE THE TAG SYSTEM EXISTS
        return false;
    }

    public static void processArmorAbility(LivingEntityPatch<?> patch, LivingEntity entity) {
        List<LivingEntity> nearbyFriendlies = SharedFunctions.getNearbyEntities(entity, 16, 4, TeamLockedPredicate.ONLY_ALLIES).stream().sorted(Comparator.comparingInt((a) -> {
            return isBird(entity) ? 0 : 1;
        })).collect(Collectors.toList());

        List<LivingEntity> nearbyDazzle = SharedFunctions.getNearbyEntities(entity, 20, 10, TeamLockedPredicate.ONLY_HOSTILES.additionalCondition((ent) -> {
            if (ent instanceof LivingEntity) {
                return ((LivingEntity) ent).hasEffect(EgoWeaponsEffects.DAZZLE.get());
            }
            return false;
        }));

        // Apply 2 Fragile to nearest entity with dazzle.
        if (!nearbyDazzle.isEmpty()) {
            EgoWeaponsEffects.FRAGILE.get().increment(nearbyDazzle.get(0), 0, 2);
        }


        boolean nearbyAllies = !nearbyFriendlies.isEmpty();

        int previousLamp = EgoWeaponsEffects.LAMP.get().getPotency(entity);

        int appliedLamp = 6;

        appliedLamp -= Math.max((previousLamp + appliedLamp) - 20, 0);

        EgoWeaponsEffects.LAMP.get().increment(entity, 0, appliedLamp);

        float damageToDeal = entity.getMaxHealth() * 0.02f * appliedLamp;

        if (damageToDeal >= entity.getHealth() - 1) {
            damageToDeal -= (damageToDeal) - entity.getHealth() - 1;
        }

        entity.setHealth(entity.getHealth() - damageToDeal);
        entity.hurt(new DamageSource("egoweapons.lamp").bypassInvul().bypassArmor(), 0.01f);

        if (entity instanceof PlayerEntity) {
            SanitySystem.damageSanity((PlayerEntity) entity, EgoWeaponsAttributes.getMaxSanity((PlayerEntity) entity) * 0.02f * appliedLamp);
        }

        if (!entity.level.isClientSide())
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.NumberLabelParticle(entity.position().add(entity.getRandom().nextFloat() - 0.5f,1,entity.getRandom().nextFloat() - 0.5f), NumberParticleTypes.LAMP, damageToDeal));

        if (nearbyAllies) {
            int buffsApplied = 0;
            for (LivingEntity ally : nearbyFriendlies) {
                if (buffsApplied < 2) {
                    if (!ally.hasEffect(EgoWeaponsEffects.GUARDIAN_OF_THE_FOREST.get())) {
                        buffsApplied++;
                        boolean isBird = isBird(ally);
                        EgoWeaponsEffects.GUARDIAN_OF_THE_FOREST.get().increment(ally, 0, isBird ? 2 : 1);

                        if (isBird) {
                            EgoWeaponsEffects.POWER_UP.get().increment(ally, 0, 1);
                        }
                    }
                } else {
                    break;
                }
            }
        } else {
            EgoWeaponsEffects.GUARDIAN_OF_THE_FOREST.get().increment(entity, 0, 1);
            EgoWeaponsEffects.POWER_UP.get().increment(entity, 0, 1);
        }
    }


    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }



}
