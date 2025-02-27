package net.m3tte.ego_weapons.procedures.abilities.weaponAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.MimicryMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.DamagefxParticle;
import net.m3tte.ego_weapons.particle.RedflashParticle;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class MimicryWeaponAbility extends ItemAbility {

    private boolean isWearingMimicry(PlayerEntity player) {
        return (player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) || player.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get()));
    }


    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return isWearingMimicry(player) ? 7 : 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return isWearingMimicry(player) ? AbilityUtils.getAbilityIcon("split_horizontal") : AbilityUtils.getAbilityIcon("goodbye");

    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALPHA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return isWearingMimicry(player) ? "Split: Horiz." : "GOODBYE";
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.blips < getBlipCost(player, playerVars)) {
            return (float) (playerVars.blips / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.blips >= getBlipCost(player, playerVars)) {

            if (isWearingMimicry(player)) {
                playerVars.blips-= getBlipCost(player, playerVars);
                World world = player.level;
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();

                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
                }
                if (!world.isClientSide()) {
                    ((World) world).playSound(null, new BlockPos(x, y, z),
                            (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")),
                            SoundCategory.PLAYERS, (float) 2, (float) 1.5);
                    ((World) world).playSound(null, new BlockPos(x, y, z),
                            (net.minecraft.util.SoundEvent) EgoWeaponsSounds.OMINOUS_EFFECT,
                            SoundCategory.PLAYERS, (float) 2, (float) 1.5);
                }

                LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                player.getCooldowns().addCooldown(EgoWeaponsItems.MIMICRY.get(), (int) 20);
                playerVars.globalcooldown = 200;
                player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 80, (int) 4, (false), (false)));
                player.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), (int) 60, (int) 4, (false), (false)));
                player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 75, 1));
                entitypatch.playSound(EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_RING, 1, 1);
                entitypatch.playAnimationSynchronized(MimicryMovesetAnims.GREAT_SPLIT_HORIZONTAL, 0.1f);
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
                    EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0.2,0.2f,-0.4f),1, RedflashParticle.particle,0,"Head",true);
                }
                applyBlipCooldown(20, playerVars);
                playerVars.syncPlayerVariables(player);
            } else {
                playerVars.blips-= getBlipCost(player, playerVars);
                World world = player.level;
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();

                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
                }

                LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                player.getCooldowns().addCooldown(EgoWeaponsItems.MIMICRY.get(), (int) 20);
                playerVars.globalcooldown = 200;
                player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 80, (int) 4, (false), (false)));
                player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 75, 1));
                entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_GOODBYE, 1, 1);
                entitypatch.playAnimationSynchronized(MimicryMovesetAnims.MIMICRY_GOODBYE, 0.1f);
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
                    EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(0.2,0.2f,-0.4f),1, RedflashParticle.particle,0,"Head",true);
                }
                applyBlipCooldown(20, playerVars);
                playerVars.syncPlayerVariables(player);
            }


        }


    }


}
