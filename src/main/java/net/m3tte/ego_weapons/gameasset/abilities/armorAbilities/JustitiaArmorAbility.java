package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.JustitiaMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JustitiaArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("cleanse_sins");
    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        if (EgoWeaponsEffects.SIN.get().getPotency(player) >= 7) {
            return AbilityUtils.getOverlay("warning");
        }

        return super.getOverlay(player, playerVars);
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALEPH;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return " Cleansing \nMisdeeds";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= getBlipCost(player, playerVars)) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.justitia_armor.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

            playerVars.light -= getBlipCost(player, playerVars);
            AbilityUtils.applyBlipCooldown(40, playerVars);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }
            entitypatch.playAnimationSynchronized(JustitiaMovesetAnims.JUSTITIA_ARMOR_ABILITY, 0.1f);

            player.playSound(EgoWeaponsSounds.DICE_ROLL, 1, 1);
            new DelayedEvent(20, (e) -> {



                giveEffect(player);
            });


            playerVars.syncPlayerVariables(player);
        }
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }


    private static void giveEffect(PlayerEntity player) {

        Collection<EffectInstance> activeEffects = player.getActiveEffects();

        int totalHits = 0;
        int totalPowerUp = 0;


        List<CountPotencyStatus> toRemoveEffects = new LinkedList<>();

        player.playSound(EgoWeaponsSounds.STAGGER, 1, 1);

        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(EgoWeaponsParticles.JUSTITIA_SCALE_STRIKE.get(), player.getX(), (player.getY() + 1), player.getZ(), (int) 1, 0, 0, 0, 0);
        }

        for (EffectInstance inst : activeEffects) {
            if (inst.getEffect() instanceof CountPotencyStatus && !(inst.getEffect().equals(EgoWeaponsEffects.SIN.get()))) {

                CountPotencyStatus potencyStatus = (CountPotencyStatus) inst.getEffect();

                int potency = Math.min(7,potencyStatus.getPotency(player));

                if (potency > 0) {
                    totalHits += potency / 3;
                    totalPowerUp += potency / 7;

                }

                toRemoveEffects.add(potencyStatus);
            }
        }

        toRemoveEffects.forEach((effect -> effect.decrement(player, 0, 7)));

        if (totalHits > 0) {
            EgoWeaponsEffects.SIN.get().increment(player, 0, Math.min(totalHits, 7));
            EgoWeaponsEffects.POWER_UP.get().increment(player, 0, Math.min(totalPowerUp, 3));
        }

        int sin = EgoWeaponsEffects.SIN.get().getPotency(player);

        if (sin > 0) {
            EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(player, 0, sin);

            if (sin >= 7) {
                player.playSound(EgoWeaponsSounds.OMINOUS_BELL, 1, 1);
                player.playSound(EgoWeaponsSounds.OMINOUS_EFFECT,1,1);
                EgoWeaponsEffects.FRAGILE.get().increment(player, 0, 5);

            }
        }

        if (sin < 7) {
            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.justitia_armor.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.YELLOW);

        } else {
            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.justitia_armor.3", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);

        }

    }
}
