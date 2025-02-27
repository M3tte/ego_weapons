package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.MagicBulletMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class MagicBulletPipe extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("assist_fire");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.BETA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Marksmans Smoking Pipe";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        int blipCost = getBlipCost(player, playerVars);
        if (playerVars.blips >= blipCost) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            entitypatch.playAnimationSynchronized(MagicBulletMovesetAnims.MAGIC_BULLET_PIPE, 0.1f);

            if (player.level instanceof ServerWorld) {
                ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, player.getX(), (player.getY() + 1), player.getZ(), (int) blipCost, 0.4, 0.6, 0.4, 0);
            }





            playerVars.blips -= blipCost;
            AbilityUtils.applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);


        }
    }

    public static StaticAnimation.Event[] magicBulletPipe() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.4f, (entitypatch) -> {
            if (entitypatch.getOriginal() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entitypatch.getOriginal();
                World world = player.level;

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()),
                            EgoWeaponsSounds.MAGIC_BULLET_BREATHE,
                            SoundCategory.NEUTRAL, 1f, (float) 1);
                }

                EgoWeaponsModVars.PlayerVariables playerVars = player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

                playerVars.blips -= 5;

                int bonus = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(player);

                int halfBonus = bonus/2;

                EgoWeaponsEffects.POISE.get().increment(player, 1, 5 + bonus);

                if (halfBonus > 0) {
                    EgoWeaponsEffects.POWER_UP.get().increment(player, 3, halfBonus);
                }

                playerVars.syncPlayerVariables(player);
            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }




    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }



}
