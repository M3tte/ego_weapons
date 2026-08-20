package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.LCARifleMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.UdjatKhopeshMovesetAnims;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class LCAUdjatArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 7;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("eye_of_the_udjat");
    }

    @Override
    public AbilityTier getAbilityTier(PlayerEntity player, PlayerVariables playerVars) {
        return AbilityTier.ALEPH;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "  Through the \nEye of the Udjat  ";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        int blipCost = getBlipCost(player, playerVars);
        if (canTrigger(player, playerVars)) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            entitypatch.playAnimationSynchronized(LCARifleMovesetAnims.LCA_UDJAT_ARMOR_ABILITY, 0.1f);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }



            player.playSound(EgoWeaponsSounds.UDJAT_COMMAND, 1, 1f);
            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.udjat_armor.special", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);


            playerVars.light -= blipCost;
            AbilityUtils.applyBlipCooldown(20, playerVars);
            playerVars.syncPlayerVariables(player);


        }
    }

    public static StaticAnimation.Event[] activateUdjatArmor() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
            if (entitypatch.getOriginal() != null) {
                LivingEntity entity = entitypatch.getOriginal();

                EgoWeaponsEffects.PROTECTION.get().increment(entity, 5, 2);
                applyBuffsToNearbyAllies(entity);

                if (!entity.level.isClientSide())
                    entitypatch.playSound(SoundEvents.BELL_RESONATE, 1, 1, 1);
            }



        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }



    private static void applyBuffsToNearbyAllies(LivingEntity source) {
        List<LivingEntity> nearbyFriendlies = SharedFunctions.getNearbyEntities(source, 16, 4, TeamLockedPredicate.ONLY_ALLIES);
        //nearbyFriendlies.add(source);



        EgoWeaponsEffects.POWER_UP.get().increment(source, 0, 1);
        int protection = EgoWeaponsEffects.PROTECTION.get().getPotency(source);
        EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 0, protection);

        if (nearbyFriendlies.isEmpty())
            return;

        for (LivingEntity ent : nearbyFriendlies) {
            if (ent != source) {

                boolean udjatGear = ent.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.UDJAT_SUIT.get());
                EgoWeaponsEffects.PROTECTION.get().increment(ent, 5, udjatGear ? 2 : 1);
                EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(ent, 0, protection);

                if (udjatGear)
                    EgoWeaponsEffects.POWER_UP.get().increment(ent, 0, 1);
            }
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
