package net.m3tte.ego_weapons.gameasset.abilities.weaponAbilities;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.gameasset.movesets.ArdorBlossomMovesetAnims;
import net.m3tte.ego_weapons.item.ardor_blossom.ArdorBlossomBat;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TeamLockedPredicate;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.SimpleEgoDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.List;

public class ArdorBlossomBatWeaponAbility extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        if (EgoWeaponsEffects.BURN.get().getPotency(player) >= 10)
            return 6;

        return 5;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("stoke_flames");

    }

    @Override
    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {

        if (EgoWeaponsEffects.BURN.get().getPotency(player) >= 25) {
            return AbilityUtils.getOverlay("warning");
        }
        return EgoWeaponsEffects.BURN.get().getPotency(player) >= 10 ? AbilityUtils.getOverlay("beta_glow") : null;
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.WAW;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "Stoking the\nFlames";
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

        if (playerVars.light >= getBlipCost(player, playerVars)) {
            int potency = EgoWeaponsEffects.BURN.get().getPotency(player);
            if (potency >= 10) {
                if (potency >= 25) useSpecial2(player, playerVars); else useSpecial1(player,playerVars);

            } else {
                useNormal(player, playerVars);

            }

        }


    }

    public void useNormal(PlayerEntity player, PlayerVariables playerVars) {
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

        EgoWeaponsEffects.BURN.get().increment(player, 0, 4);

        player.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_VOICE_1, 1, 1f);
        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.ardor_blossom_bat.unpowered.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

        player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 0));

        entitypatch.playAnimationSynchronized(ArdorBlossomMovesetAnims.ARDOR_BLOSSOM_SPECIAL_1, 0.0f);

            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
        AbilityUtils.applyBlipCooldown(8, playerVars);
        playerVars.syncPlayerVariables(player);
    }

    public void useSpecial2(PlayerEntity player, PlayerVariables playerVars) {
        playerVars.light -= getBlipCost(player, playerVars);
        World world = player.level;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
        }

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        playerVars.globalcooldown = 160;


        player.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_CORROSION_1, 1, 1f);
        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.ardor_blossom_bat.corrosion.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

        player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0));
        player.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), 60, 6));

        entitypatch.playAnimationSynchronized(ArdorBlossomMovesetAnims.ARDOR_BLOSSOM_SPECIAL_B_2, 0.0f);


            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
        AbilityUtils.applyBlipCooldown(8, playerVars);
        playerVars.syncPlayerVariables(player);
    }

    public void useSpecial1(PlayerEntity player, PlayerVariables playerVars) {
        playerVars.light -= getBlipCost(player, playerVars);
        World world = player.level;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, this.getBlipCost(player, playerVars), 0, 0.3, 0, 0.05);
        }

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        playerVars.globalcooldown = 160;



        if (EmotionSystem.getEmotionLevel(player) >= 5) {
            player.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_RAGE_1, 1, 1f);
            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.ardor_blossom_bat.rage.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
            EgoWeaponsEffects.POWER_UP.get().increment(player, 0, 2);
        } else {
            player.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_SPECIAL_VOICE_1, 1, 1f);
            DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.ardor_blossom_bat.unpowered.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);
        }
        player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 100, 0));

        entitypatch.playAnimationSynchronized(ArdorBlossomMovesetAnims.ARDOR_BLOSSOM_SPECIAL_B_1, 0.0f);


            /*if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }*/
        AbilityUtils.applyBlipCooldown(8, playerVars);
        playerVars.syncPlayerVariables(player);
    }


    public static void processWeaponAbility(LivingEntityPatch<?> patch, LivingEntity entity, boolean force) {


        if (entity.level instanceof ServerWorld) {
            patch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_INNATE_HIT_3, 1, 1);


            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), EgoWeaponsEffects.BURN.get().getPotency(entity), entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.01, 0.3f, 0.5f, 0.5f, 1f, 0.5f));
        }

        if (entity.hasEffect(EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get()) || force) {
            if (entity.level instanceof ServerWorld) {

                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.ARDOR_BLOSSOM_IMPACT.get(), EgoWeaponsEffects.BURN.get().getPotency(entity), entity.getX(), entity.getY() + 0.05, entity.getZ(), 0, 0f, 0f, 0f, 0f, 0f));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.SLASH_SHOCKWAVE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));

                EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), EgoWeaponsEffects.BURN.get().getPotency(entity), entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 0.01, 0.3f, 0.5f, 0.5f, 1f, 0.5f));
                castBurnExplosion(patch, entity, EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get().getPotency(entity));
            }

        }

        if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.ARDOR_BLOSSOM_STAR_SUIT.get())) {
            EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get().increment(entity, 5, 1);
        }



    }

    public static void castBurnExplosion(LivingEntityPatch<?> patch, LivingEntity entity, int stacks) {
        List<LivingEntity> targets = SharedFunctions.getLivingEntitiesRadius(entity.level, entity, entity.position(), 6, TeamLockedPredicate.ONLY_HOSTILES);
        System.out.println("CASTING BURN EXPLOSION ON ENTITIES : "+targets);
        for (LivingEntity target : targets) {
            //target.hurt(new SimpleEgoDamageSource("", entity, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED, "ardor_blossom_aoe"), (float) stacks);

           LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {
                SharedFunctions.hitstunEntity(entitypatch, 2, true, 2);
            }

            //DirectEgoDamageSource(String damageTypeIn, Entity damageSourceEntityIn, StunType stunType, StaticAnimation animation, AttackTypes attackType, DamageTypes damageType, String attackIdentifier)
        }
    }

    public static void castOverclockExplosion(LivingEntityPatch<?> patch, LivingEntity entity) {
        List<LivingEntity> targets = SharedFunctions.getLivingEntitiesRadius(entity.level, entity, entity.position(), 10, TeamLockedPredicate.ONLY_HOSTILES);
        System.out.println("CASTING BURN EXPLOSION ON ENTITIES : "+targets);

        if (!entity.level.isClientSide()) {
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.OUTGOING_EMBER.get(), 70, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 4f, 4f, 20, 0,0,0));

            patch.playSound(EgoWeaponsSounds.ARDOR_BLOSSOM_EXPLODE, 1f, 1, 1f);

            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.FIREFIST_STRIKE.get(), 1, entity.getX(), entity.getY() + entity.getBbHeight()/2, entity.getZ(), 4f, 4f, 20, 0,0,0));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.FIRE_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.FIRE_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
            EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(entity.getId(), entity.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));

        }

        float dmg = Math.max(1,EgoWeaponsEffects.BURN.get().getPotency(entity));

        DamageSource src1 = new SimpleEgoDamageSource("", null, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED, "ardor_blossom_self_explode");
        entity.hurt(src1, dmg);
        StaggerSystem.reduceStagger(entity, dmg, false);

        for (LivingEntity target : targets) {
            //target.hurt(new SimpleEgoDamageSource("", entity, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.RED, "ardor_blossom_aoe"), (float) stacks);

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);



            ArdorBlossomBat.triggerEmbersEffect(target, 1, true, entity);
            ArdorBlossomBat.triggerEmbersEffect(target, 6, false, entity);
        }
    }
}
