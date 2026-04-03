package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.ArdorBlossomMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.FirefistMovesetAnims;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.StatsRenaming;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;

public class ArdorBlossomArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("raising_abs");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Raising \nAttunement";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 6) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            entitypatch.playAnimationSynchronized(ArdorBlossomMovesetAnims.ARDOR_BLOSSOM_ARMOR_SKILL, 0.0f);


            World world = player.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), player.getX(), (player.getY() + 1), player.getZ(), this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
            }

            playerVars.light -= getBlipCost(player, playerVars);

            if (EgoWeaponsEffects.BURN.get().getPotency(player) >= 25) {
                DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.ardor_blossom_armor.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);

            } else {
                DialogueSystem.speakEvalDialogue(entitypatch.getOriginal(), "dialogue.ego_weapons.skills.ardor_blossom_armor.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

            }

            playerVars.syncPlayerVariables(player);
        }
    }

    public static void processArmorAbility(LivingEntityPatch<?> patch, LivingEntity entity) {
        int missingBurnP = Math.max(0,10 - EgoWeaponsEffects.BURN.get().getPotency(entity));
        int missingBurnC = Math.max(0,3 - EgoWeaponsEffects.BURN.get().getCount(entity));

        if (missingBurnC > 0 || missingBurnP > 0) {
            EgoWeaponsEffects.BURN.get().increment(entity, missingBurnC, missingBurnP);
        }

        if (entity.level instanceof ServerWorld) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), EgoWeaponsEffects.BURN.get().getPotency(entity), entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.01, 0.3f, 0.5f, 0.5f, 1f, 0.5f));
        }

        int consumedBurn = Math.min(15,(int) (EgoWeaponsEffects.BURN.get().getPotency(entity) * 0.5f));


        EgoWeaponsEffects.BURN.get().decrement(entity, 0, consumedBurn);
        EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get().increment(entity, 5, Math.max(consumedBurn / 3,1));

        if (consumedBurn >= 6) {


            patch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_HIT_3, 1, 1);
            if (entity.level instanceof ServerWorld) {
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.ARDOR_BLOSSOM_IMPACT.get(), EgoWeaponsEffects.BURN.get().getPotency(entity), entity.getX(), entity.getY() + 0.05, entity.getZ(), 0, 0f, 0f, 0f, 0f, 0f));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
            }
            castBurnExplosion(patch, entity, EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get().getPotency(entity));
        }

    }



    public static void castBurnExplosion(LivingEntityPatch<?> patch, LivingEntity entity, int stacks) {
        List<LivingEntity> targets = SharedFunctions.getLivingEntitiesRadius(entity.level, entity, entity.position(), 6, TeamLockedPredicate.ONLY_HOSTILES);

        for (LivingEntity target : targets) {
            //target.hurt(new SimpleEgoDamageSource("", entity, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED, "ardor_blossom_aoe"), (float) stacks);

            target.hurt(new DirectEgoDamageSource("", entity, ExtendedDamageSource.StunType.SHORT, ArdorBlossomMovesetAnims.ARDOR_BLOSSOM_ARMOR_SKILL, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED, "ardor_blossom_aoe"), (float) stacks);
            EgoWeaponsEffects.BURN.get().increment(target, 0, stacks);
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {
                entitypatch.knockBackEntity(entity.position(), 1.5f);
            }

            //DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, AttackTypes attackType, DamageTypes damageType, String attackIdentifier)
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
