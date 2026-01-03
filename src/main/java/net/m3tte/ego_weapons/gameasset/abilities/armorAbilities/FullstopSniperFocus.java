package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class FullstopSniperFocus extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 2;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("assist_fire");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Focus";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        int blipCost = getBlipCost(player, playerVars);
        if (playerVars.light >= blipCost) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_FOCUS, 0.1f);

            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }





            playerVars.light -= blipCost;
            AbilityUtils.applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);


        }
    }

    public static StaticAnimation.Event[] activateFullstopFocus() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.4f, (entitypatch) -> {
            if (entitypatch.getOriginal() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();
                World world = player.level;

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()),
                            EgoWeaponsSounds.TARGET_SPOTTED,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }

                EgoWeaponsModVars.PlayerVariables playerVars = player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
                int consumedEnergy = (int) Math.min(10,playerVars.light);
                playerVars.light -= consumedEnergy;
                EgoWeaponsEffects.POISE.get().increment(player, consumedEnergy / 2, consumedEnergy);
                EmotionSystem.increaseEmotionPoints(player, 9 * consumedEnergy, false);
                playerVars.syncPlayerVariables(player);
            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }





    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }



}
