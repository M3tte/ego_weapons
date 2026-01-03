package net.m3tte.ego_weapons.gameasset.abilities.assistAttacks;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils.applyBlipCooldown;

public class FullstopRifleWeaponAssistAttack extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 3;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("bullseye");

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
        return "Got it.";
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

        int blipCost = getBlipCost(player, playerVars);

        if (playerVars.light >= blipCost) {

            World world = player.level;
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            int potency = 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            if (player.getRandom().nextBoolean())
                DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.fullstop_rifle.assist.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
            else
                DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.fullstop_rifle.assist.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);


            int ammoCount = AmmoSystem.getAmmoCount(player.getItemInHand(Hand.MAIN_HAND));
            boolean validAmmo = AmmoSystem.hasValidAmmoFor(player.getItemInHand(Hand.MAIN_HAND), player);

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            playerVars.globalcooldown = 20;

            if (ammoCount >= 1 || validAmmo) {
                entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_ASSIST, 0.01f);
                playerVars.light -= blipCost;
            }

            applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }

    private static void dealAssistAttackDamage(LivingEntity source, World world) {
        int entityId = source.getPersistentData().getInt("assistFireTarget");

        if (entityId > 0) {
            Entity foundEntity = world.getEntity(entityId);
            if (foundEntity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) foundEntity;

                source.removeEffect(EgoWeaponsEffects.ASSIST_FIRE.get());

                if (source instanceof PlayerEntity)
                    target.hurt(DamageSource.playerAttack((PlayerEntity) source), 19);
                else
                    target.hurt(DamageSource.mobAttack(source), 19);
            }
        }
    }

    public static StaticAnimation.Event[] fireFSRailgunAssistAttack(float time) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];
        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()) {
                int entityId = entity.getPersistentData().getInt("assistFireTarget");
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendTakeAimParticle(entityId));

                int randomNum = entity.getRandom().nextInt(3);
                SoundEvent callout = EgoWeaponsSounds.FULLSTOP_SNIPER_MOVE_ASIDE;
                switch(randomNum) {
                    case 0:
                            callout = EgoWeaponsSounds.FULLSTOP_SNIPER_TARGET_CONFIRMED;
                        break;
                    case 1:
                            callout = EgoWeaponsSounds.FULLSTOP_SNIPER_OKAY_SEE_EM;
                        break;
                }
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        callout,
                        SoundCategory.NEUTRAL, 5f, (float) 1);

                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.TARGET_SPOTTED,
                        SoundCategory.NEUTRAL, 1f, (float) 0.7f);
            }



            entitypatch.getOriginal().addEffect(new EffectInstance(EgoWeaponsEffects.ASSIST_FIRE.get(), 60, 0));

            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_START,
                        SoundCategory.PLAYERS, 1f, (float) 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.3f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 1, 2);

            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FLIP,
                        SoundCategory.PLAYERS, 1f, (float) 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 1);
            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_CHARGE,
                        SoundCategory.PLAYERS, 1f, (float) 1);
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(time, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.CLICK,
                        SoundCategory.PLAYERS, 0.5f, (float) 0.8);
            }

            entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 0);
            AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);
            if (ammo != null) {
                if (!ammo.hasEffect()) {
                    EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
                }
                dealAssistAttackDamage(entity, world);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

            }
        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(time + 0.3f, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_AFTER,
                        SoundCategory.PLAYERS, 1f, (float) 1);
        }, StaticAnimation.Event.Side.BOTH);
        return events;
    }
}
