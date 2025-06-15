package net.m3tte.ego_weapons.procedures.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeRepMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
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

import java.util.ArrayList;
import java.util.List;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;

public class FullstopAssistFire extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 2;
    }


    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        return super.getOverlay(player, playerVars);
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
        return "Finish 'em";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        int blipCost = getBlipCost(player, playerVars);
        if (playerVars.light >= blipCost) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            int entityId = player.getPersistentData().getInt("lastTargetSpotted");

            if (entityId > 0) {
                Entity foundEntity = player.level.getEntity(entityId);
                if (foundEntity instanceof LivingEntity) {
                    LivingEntity targetEntity = (LivingEntity) foundEntity;
                    if (targetEntity.hasEffect(EgoWeaponsEffects.TARGET_SPOTTED.get())) {
                        entitypatch.playAnimationSynchronized(FullstopOfficeRepMovesetAnims.FULLSTOP_REP_ASSIST_FIRE, 0.1f);

                        if (player.level instanceof ServerWorld) {
                            ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, player.getX(), (player.getY() + 1), player.getZ(), (int) blipCost, 0.4, 0.6, 0.4, 0);
                        }





                        playerVars.light -= blipCost;
                        AbilityUtils.applyBlipCooldown(10, playerVars);
                        playerVars.syncPlayerVariables(player);
                    } else {
                        player.getPersistentData().remove("lastTargetSpotted");
                    }

                } else {
                    player.getPersistentData().remove("lastTargetSpotted");
                }



            }


        }
    }

    public static StaticAnimation.Event[] activateAssistFire() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.2f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.TARGET_SPOTTED,
                        SoundCategory.NEUTRAL, 1f, (float) 1);
            }

            int entityId = entity.getPersistentData().getInt("lastTargetSpotted");

            if (entityId > 0) {
                Entity foundEntity = world.getEntity(entityId);
                if (foundEntity instanceof LivingEntity) {
                    LivingEntity targetEntity = (LivingEntity) foundEntity;
                    if (targetEntity.hasEffect(EgoWeaponsEffects.TARGET_SPOTTED.get())) {
                        String voiceline = " Take care of them.";
                        if (targetEntity.getHealth() < targetEntity.getMaxHealth() / 3)
                            voiceline = " Finish them off.";
                        if (!world.isClientSide()) {
                            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
                            if (mcserv != null)
                                mcserv.getPlayerList().broadcastMessage(
                                        new StringTextComponent((entity.getDisplayName().getString() + ">")).withStyle(TextFormatting.DARK_BLUE).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(voiceline).withStyle(TextFormatting.BLUE).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
                                        entity.getUUID());
                        }

                        if (!world.isClientSide()) {
                            world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                                    voiceline.equals(" Take care of them.") ? EgoWeaponsSounds.FULLSTOP_REP_TAKE_CARE_OF_THEM : EgoWeaponsSounds.FULLSTOP_REP_FINISH_THEM_OFF,
                                    SoundCategory.PLAYERS, 1f, (float) 1);
                        }

                        // Remove all poise over 20

                        int excessPoise = Math.max(0, EgoWeaponsEffects.POISE.get().getPotency(entity) - 20);

                        if (excessPoise > 0)
                            EgoWeaponsEffects.POISE.get().decrement(entity, 0, excessPoise);

                        applyBuffsToNearbyAllies(entity, excessPoise, entityId);
                        entity.getPersistentData().remove("lastTargetSpotted");
                    }
                }

            }
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }


    static float hDist = 32;
    static float vDist = 4;
    private static List<LivingEntity> getNearbyTeammates(LivingEntity source) {
        return new ArrayList<>(source.level
                .getNearbyEntities(PlayerEntity.class,
                        EntityPredicate.DEFAULT.allowSameTeam(), source, new AxisAlignedBB(source.getX() - (hDist), source.getY() - (vDist), source.getZ() - (hDist), source.getX() + (hDist), source.getY() + (vDist), source.getZ() + (hDist))));
    }

    private static void applyBuffsToNearbyAllies(LivingEntity source, int poiseToSpread, int targetUUID) {
        List<LivingEntity> nearbyFriendlies = getNearbyTeammates(source);
        //nearbyFriendlies.add(source);

        if (nearbyFriendlies.isEmpty())
            return;

        int individualPoise = poiseToSpread / nearbyFriendlies.size();

        for (LivingEntity ent : nearbyFriendlies) {
            EgoWeaponsEffects.POISE.get().increment(ent, 0, individualPoise);
            ent.addEffect(new EffectInstance(EgoWeaponsEffects.ASSIST_FIRE.get(), 60, 0));
            ent.getPersistentData().putInt("assistFireTarget", targetUUID);
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
