//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.m3tte.ego_weapons.gameasset;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.entities.AtelierShotgunBullet;
import net.m3tte.ego_weapons.execFunctions.AtelierCooldownHandler;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.movesets.*;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

import static net.m3tte.ego_weapons.item.sunshower.Sunshower.setOpenstate;
import static yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;

public class EgoWeaponsAnimations {



    public static StaticAnimation STAGGER;

    public static StaticAnimation DURANDAL_EQUIP;
    public static StaticAnimation DUAL_EQUIP;

    public static StaticAnimation BS_DODGE;



    public static StaticAnimation PUMMEL_DOWN;

    public static StaticAnimation LONG_HITSTUN;

    public static StaticAnimation CLASH_STUN_BASIC;







    public static StaticAnimation LIFT_UP;

    public EgoWeaponsAnimations() {
    }

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        System.out.println("Starting E.G.O Animations...");
        event.getRegistryMap().put(EgoWeaponsMod.MODID, EgoWeaponsAnimations::build);
    }



    private static void build() {
        System.out.println("Building E.G.O Animations...");
        Models<?> models = FMLEnvironment.dist == Dist.CLIENT ? ClientModels.LOGICAL_CLIENT : Models.LOGICAL_SERVER;
        Model biped = models.biped;

        EgoWeaponsMobAnimations.build();
        DurandalMovesetAnims.build(biped);
        AtelierLogicMovesetAnims.build(biped);
        BlackSilenceMovesetAnims.build(biped);
        LiuSouth6MovesetAnims.build(biped);
        OeufiAssocMovesetAnims.build(biped);
        FullstopOfficeRepMovesetAnims.build(biped);
        FullstopOfficeSniperMovesetAnims.build(biped);
        MagicBulletMovesetAnims.build(biped);
        SolemnLamentMovesetAnims.build(biped);
        SunshowerMovesetAnims.build(biped);
        MimicryMovesetAnims.build(biped);
        FirefistMovesetAnims.build(biped);
        StigmaWorkshopMovesetAnims.build(biped);
        HeishouMaoBranchAnims.build(biped);

        PUMMEL_DOWN = new PushDownAnimation(0.05f, "biped/generic/pummel_down", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 0.7f);

        STAGGER = new LongHitAnimation(0.05f, "biped/generic/stagger", biped);
        BS_DODGE = new DodgeAnimation(0f, "biped/generic/bs_evade", 0.5f, 0.5f, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(StaticAnimationProperty.PLAY_SPEED,1.5f)
                .addProperty(StaticAnimationProperty.EVENTS, genericDodgeEvent());


        LIFT_UP = new PushDownAnimation(0.05f, "biped/sunshower/lift_up", biped).addProperty(StaticAnimationProperty.PLAY_SPEED, 0.7f);

        LONG_HITSTUN = new PushDownAnimation(0.05f, "biped/generic/biped_stun", biped).addProperty(StaticAnimationProperty.PLAY_SPEED, 0.5f);


        CLASH_STUN_BASIC = new PushDownAnimation(0.05f, "biped/generic/clash_lose", biped).addProperty(StaticAnimationProperty.PLAY_SPEED, 1f);
    }

    public static void spawnArmatureParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult, String jointName) {
        spawnArmatureParticle(entityPatch,partialTicks,offsets,amount,particle,speedmult, jointName,false);
    }



    public static void playArmatureSound(LivingEntityPatch<?> entityPatch, int partialTicks, SoundEvent sound, SoundCategory cat, String jointName) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        Vector3d particlePos = OpenMatrix4f.transform(middleJointTf, new Vector3d(0,0,0));
        l.playSound(null, new BlockPos(particlePos.x, particlePos.y, particlePos.z), sound, cat, 1, 1);
        System.out.println("PLAYING SOUND AT: "+particlePos);
    }

    public static void spawnArmatureParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult, String jointName, boolean acceptServerSide) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        float speedMultHalf = speedmult / 2;
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        Vector3d particlePos = OpenMatrix4f.transform(middleJointTf, offsets);
        for (int x = 0; x < amount; x++) {
            if (l.isClientSide()) {
                l.addParticle(particle, particlePos.x, particlePos.y, particlePos.z, r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf); //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
            } else if (acceptServerSide) {
                ((ServerWorld)l).sendParticles(particle,particlePos.x,particlePos.y,particlePos.z, 1, 0,0,0,speedmult);
            }
        }
    }

    public static void spawnArmatureParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, Vector3f speeds, String jointName) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        Vector3d particlePos = OpenMatrix4f.transform(middleJointTf, offsets);
        for (int x = 0; x < amount; x++) {
            if (l.isClientSide()) {
                l.addParticle(particle, particlePos.x, particlePos.y, particlePos.z, speeds.x(),speeds.y(),speeds.z()); //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
            }
        }
    }
    public static void spawnBlockImpactParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult) {
        spawnBlockImpactParticle(entityPatch, partialTicks, offsets, amount, particle, speedmult, new Vector3i(0,0,0));
    }



    public static Vector3d getArmaturePosition(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, float speedmult, String jointName) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        float speedMultHalf = speedmult / 2;
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;
        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), jointName).mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), jointName).mulFront(middleModelTf);

        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        return OpenMatrix4f.transform(middleJointTf, offsets);
    }

    private static void spawnBlockImpactParticle(LivingEntityPatch<?> entityPatch, int partialTicks, Vector3d offsets, int amount, IParticleData particle, float speedmult, Vector3i particleOffSets) {
        Pose currentPose = entityPatch.getAnimator().getPose(partialTicks);
        float speedMultHalf = speedmult / 2;
        World l = entityPatch.getOriginal().level;
        Random r = l.random;
        Vector3d posMid = entityPatch.getOriginal().position();

        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(partialTicks)));
        OpenMatrix4f middleJointTf;

        if (l.isClientSide)
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature(), "Tool_R").mulFront(middleModelTf);
        else
            middleJointTf = Animator.getBindedJointTransformByName(currentPose,entityPatch.getEntityModel(ClientModels.LOGICAL_SERVER).getArmature(), "Tool_R").mulFront(middleModelTf);
        //entityPatch.getAnimator().getPose((float) (i + r.nextInt(3) - 1) / 10F).getJointTransformData().get("Tool_R").toMatrix().mulFront(middleModelTf);

        Vector3d originalPos = OpenMatrix4f.transform(middleJointTf, offsets).add(0, 0.2f, 0);

        BlockPos pos = new BlockPos(originalPos.x, originalPos.y -0.4f, originalPos.z);

        BlockPos particlePos = new BlockPos(originalPos.x, originalPos.y -0.4f, originalPos.z).offset(particleOffSets);
        LivingEntity entity = entityPatch.getOriginal();
        if (!l.isEmptyBlock(pos)) {
            if (particle != null)
                l.addParticle(particle, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0,0,0);
            float blastResistance = l.getBlockState(pos).getBlock().getExplosionResistance();
            if (blastResistance > 2) {
                l.addParticle(EpicFightParticles.HIT_BLUNT.get(), particlePos.getX(), particlePos.getY() + 1, particlePos.getZ(), 0,0,0); //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
                entity.playSound(SoundEvents.ANVIL_LAND, 0.7f, 1.3f);
            } else if (blastResistance > 0.2f) {
                for (int x = 0; x < amount; x++) {
                    l.addParticle(new BlockParticleData(ParticleTypes.BLOCK, l.getBlockState(pos)), particlePos.getX() + r.nextFloat() * 0.2 - 0.1, particlePos.getY() + r.nextFloat() * 0.2 + 1, particlePos.getZ() + r.nextFloat() * 0.2 - 0.1, r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf,r.nextFloat() * speedmult - speedMultHalf);
                    if (x % 3 == 0)
                        l.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particlePos.getX() + r.nextFloat() * 0.2 - 0.1, particlePos.getY() + r.nextFloat() * 0.2 + 1, particlePos.getZ() + r.nextFloat() * 0.2 - 0.1, r.nextFloat() * speedmult * 2 - speedMultHalf,r.nextFloat() * speedmult / 2 - speedMultHalf / 2,r.nextFloat() * speedmult * 2 - speedMultHalf);
                }
                 //r.nextFloat() * 0.2 - 0.1, r.nextFloat() * 0.2 - 0.1, );
                entity.playSound(l.getBlockState(pos).getSoundType().getBreakSound(), 0.7f, 1f);
            }
        }
    }

    public static StaticAnimation.Event[] attackSound(SoundEvent e, float timeStamp) {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];

        events[0] = StaticAnimation.Event.create(timeStamp, (entitypatch) -> {
            entitypatch.playSound(e, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }





    static Random r = new Random();
    public static float ranBetween(float min, float max) {
        return r.nextFloat()*(max-min) + min;
    }









    public static StaticAnimation.Event[] genericDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            if (entity.level instanceof ServerWorld) {
                ((ServerWorld)entity.level).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }

            entity.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }

    public static StaticAnimation.Event[] standardDodgeEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.01F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

            if (entity.level instanceof ServerWorld) {
                ((ServerWorld)entity.level).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }

            entity.playSound(EgoWeaponsSounds.BLACK_SILENCE_EVADE, 1, 1);


        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }





    public static StaticAnimation.Event[] furiosoSheatheEvents() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[2];

        events[0] = StaticAnimation.Event.create(0F, (entitypatch) -> {
            LivingEntity entity = entitypatch.getOriginal();
            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()), (entity.getY() + 1), (entity.getZ()),
                        30, 0.2, 0.5, 0.2, 0);
            }
            entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.iFrames = 15;
                capability.syncPlayerVariables(entity);
            });

        }, StaticAnimation.Event.Side.BOTH);

        events[1] = StaticAnimation.Event.create(0.4F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            entity.playSound(EpicFightSounds.SWORD_IN, 1, 1);
        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }





    public static StaticAnimation.Event[] atelierShotgunEvent() {
        StaticAnimation.Event[] events = new StaticAnimation.Event[1];
        events[0] = StaticAnimation.Event.create(0.55F, (entitypatch) -> {
            Entity entity = entitypatch.getOriginal();
            World world = entity.level;
            ServerWorld serverWorld = null;
            if (!world.isClientSide())
                serverWorld = (ServerWorld) world;
            Entity _shootFrom = entity;
            World projectileLevel = _shootFrom.level;
            if (!projectileLevel.isClientSide()) {
                ProjectileEntity _entityToSpawn = new Object() {
                    public ProjectileEntity getArrow(World world, Entity shooter, float damage, int knockback) {
                        AbstractArrowEntity entityToSpawn = new AtelierShotgunBullet.AtelierShotgunSlugProj(EgoWeaponsEntities.ATELIER_SHOTGUN_BULLET.get(), world);
                        entityToSpawn.setOwner(shooter);
                        entityToSpawn.setBaseDamage(damage);
                        entityToSpawn.setKnockback(knockback);
                        entityToSpawn.setSilent(true);

                        return entityToSpawn;
                    }
                }.getArrow(projectileLevel, entity, 2f, 1);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("blacksilence.atelier.shotgun")),
                        SoundCategory.NEUTRAL, (float) 0.3, (float) 1);
            }

            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.1,0), 1, EpicFightParticles.HIT_BLUNT.get(), 0, "Tool_L", false);
            AtelierCooldownHandler.executeShotgun(world, entity, entity.getX(), entity.getY(), entity.getZ());
            BlackSilenceEvaluator.furiosoAddAttackStat(entity, 20);

        }, StaticAnimation.Event.Side.BOTH);

        return events;
    }




}
