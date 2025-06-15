package net.m3tte.ego_weapons.procedures.abilities.assistAttacks;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.entities.MagicBulletProjectile;
import net.m3tte.ego_weapons.gameasset.movesets.MagicBulletMovesetAnims;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.particle.DamagefxParticle;
import net.m3tte.ego_weapons.particle.MagicBulletAimParticle;
import net.m3tte.ego_weapons.particle.MagicBulletShell;
import net.m3tte.ego_weapons.potion.countEffects.DarkFlameEffect;
import net.m3tte.ego_weapons.procedures.abilities.AbilityTier;
import net.m3tte.ego_weapons.procedures.abilities.AbilityUtils;
import net.m3tte.ego_weapons.procedures.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Random;

import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.abilities.AbilityUtils.applyBlipCooldown;

public class MagicBulletAssistAttack extends ItemAbility {



    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        int extra = 0;

        return 6;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("magic_bullet_fire");

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
        return "Destined\nBullet";
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
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            int potency = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(player) + 1;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }
            if (!world.isClientSide()) {
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        SoundEvents.WITHER_SHOOT,
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);
                ((World) world).playSound(null, new BlockPos(x, y, z),
                        potency >= 3 ? EgoWeaponsSounds.MAGIC_BULLET_AIM_2 : EgoWeaponsSounds.MAGIC_BULLET_AIM_1,
                        SoundCategory.PLAYERS, (float) 2, (float) 1.5);
            }


            player.getCooldowns().addCooldown(EgoWeaponsItems.MAGIC_BULLET.get(), (int) 20);
            playerVars.globalcooldown = 100;
            entitypatch.getOriginal().addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0));

            entitypatch.playAnimationSynchronized((potency >= 6) ? MagicBulletMovesetAnims.MAGIC_BULLET_ASSIST_2 : MagicBulletMovesetAnims.MAGIC_BULLET_ASSIST_1, 0.1f);
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(DamagefxParticle.particle, x, (y + 1), z, (int) 4, 0.4, 0.6, 0.4, 0);
            }
            playerVars.light -= blipCost;

            applyBlipCooldown(10, playerVars);
            playerVars.syncPlayerVariables(player);


        }


    }

    private static void dealAssistAttackDamage(LivingEntity source, World world, int mbAmpl) {
        int entityId = source.getPersistentData().getInt("assistFireTarget");

        if (entityId > 0) {
            Entity foundEntity = world.getEntity(entityId);
            if (foundEntity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) foundEntity;

                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (target.getX()), (target.getY()),
                            (target.getZ()), (int) 1, (target.getBbWidth() / 7), (target.getBbHeight() / 7), (target.getBbWidth() / 7), 0);
                }

                source.removeEffect(EgoWeaponsEffects.ASSIST_FIRE.get());
                ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(target, mbAmpl);
                if (source instanceof PlayerEntity)
                    target.hurt(DamageSource.playerAttack((PlayerEntity) source), 16 + 2 * mbAmpl);
                else
                    target.hurt(DamageSource.mobAttack(source), 16 + 2 * mbAmpl);
            }
        }
    }

    public static StaticAnimation.Event[] magicBulletAssistFire1() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[5];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_DIALOGUE_ASSIST,
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

        events[1] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            if (ampl >= 3)
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[3] = StaticAnimation.Event.create(1F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_DIALOGUE_FIRE,
                        SoundCategory.PLAYERS, 1f, (float) 1);

        }, StaticAnimation.Event.Side.BOTH);

        events[4] = StaticAnimation.Event.create(1.37F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            dealAssistAttackDamage(entity, world, ampl);


            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE.get(), 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_FIRE_1,
                        SoundCategory.PLAYERS, 1f, (float) 1);

            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                } else {
                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }
                    ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(entity, ampl-1);
                    entity.hurt(DamageSource.playerAttack((PlayerEntity) entity).setMagic(), (7 + ampl) * 0.7f);
                }
            } else {
                MagicBulletProjectile.shoot(entity, ampl-1, false);
            }



        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }


    public static StaticAnimation.Event[] magicBulletAssistFire2() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[6];

        events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_DIALOGUE_ASSIST,
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

        events[1] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.5), 1, MagicBulletShell.particle, 0.4f, "Tool_R", false);
        }, StaticAnimation.Event.Side.BOTH);

        events[2] = StaticAnimation.Event.create(0.7F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();

            EgoWeaponsEffects.MAGIC_BULLET.get().increment(entity, 0, 1);

            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-2.5,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);
            if (ampl >= 6)
                spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-3,-0.7), 1, MagicBulletAimParticle.particle, 0, "Tool_R", false);

        }, StaticAnimation.Event.Side.BOTH);
        events[3] = StaticAnimation.Event.create(1.28F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            entity.level.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
                    EgoWeaponsSounds.MAGIC_BULLET_FIRE_SWING,
                    SoundCategory.PLAYERS, 2, 1f / (new Random().nextFloat() * 0.5f + 1));
        }, StaticAnimation.Event.Side.BOTH);
        events[4] = StaticAnimation.Event.create(1.6F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        EgoWeaponsSounds.MAGIC_BULLET_DIALOGUE_FIRE,
                        SoundCategory.PLAYERS, 2f, (float) 1);

        }, StaticAnimation.Event.Side.BOTH);
        events[5] = StaticAnimation.Event.create(2.08F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;




            int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entity);

            if (!world.isClientSide())
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        ampl >= 6 ? EgoWeaponsSounds.MAGIC_BULLET_FIRE_3 : EgoWeaponsSounds.MAGIC_BULLET_FIRE_2,
                        SoundCategory.PLAYERS, 1f, (float) 1);

            dealAssistAttackDamage(entity, world, ampl);

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE.get(), 0, "Tool_R", false);
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.5,-0.15), 1, EgoWeaponsParticles.MAGIC_BULLET_FIRE_SIDE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.7,-0.15), 1, EgoWeaponsParticles.RIFLE_SHOCKWAVE.get(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

            spawnArmatureParticle(entitypatch, 0, new Vector3d(0.4,-1.5,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_R", false);
            if (entity instanceof PlayerEntity) {
                if (SanitySystem.getSanity((PlayerEntity) entity) > ampl * 1.5f) {
                    SanitySystem.damageSanity((PlayerEntity) entity, ampl * 1.5f);
                } else {

                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).sendParticles(EgoWeaponsParticles.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                                (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
                    }
                    ((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(entity, ampl-1);
                    entity.hurt(DamageSource.playerAttack((PlayerEntity) entity).setMagic(), (7 + ampl) * 0.7f);
                }
            } else {
                MagicBulletProjectile.shoot(entity, ampl-1, false);
            }
            if (ampl >= 7) {
                entity.removeEffect(EgoWeaponsEffects.MAGIC_BULLET.get());

                if (entitypatch instanceof PlayerPatch<?>) {
                    PlayerPatch<?> playerPatch = (PlayerPatch<?>) entitypatch;

                    playerPatch.setStamina(Math.min(playerPatch.getStamina() + 1,playerPatch.getMaxStamina()));
                }
            }
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

}
