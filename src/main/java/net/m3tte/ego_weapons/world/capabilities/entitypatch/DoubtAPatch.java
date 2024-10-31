package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import io.netty.buffer.ByteBuf;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsMobAnimations;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Behavior;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Health.Comparator;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

public class DoubtAPatch extends MobPatch<DawnOfGreenDoubtEntity> implements StaggerableEntity {

    public static final CombatBehaviors.Builder<DoubtAPatch> STANDARD = CombatBehaviors.<DoubtAPatch>builder()
            .newBehaviorSeries(
                    BehaviorSeries.<DoubtAPatch>builder().weight(100.0F).canBeInterrupted(true).looping(false).cooldown(4)
                            .nextBehavior(Behavior.<DoubtAPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_DASH).withinDistance(2.0D, 4.0D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<DoubtAPatch>builder().weight(50.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<DoubtAPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_1).withinEyeHeight().withinDistance(0.0D, 2.25D))
                            .nextBehavior(Behavior.<DoubtAPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_2).withinEyeHeight().withinDistance(0.0D, 2.25D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<DoubtAPatch>builder().weight(10.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<DoubtAPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_B1).withinEyeHeight().withinDistance(0.0D, 2.25D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<DoubtAPatch>builder().weight(90.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<DoubtAPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_GUARD).withinEyeHeight().health(20, Comparator.LESS_ABSOLUTE).withinDistance(0.0D, 3D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<DoubtAPatch>builder().weight(10.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<DoubtAPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_BLUNT).withinEyeHeight().withinDistance(0.0D, 1D))
            );


    public DoubtAPatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, EgoWeaponsMobAnimations.DOUBT_A_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, EgoWeaponsMobAnimations.DOUBT_A_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.CHASE, EgoWeaponsMobAnimations.DOUBT_A_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.FALL, EgoWeaponsMobAnimations.DOUBT_A_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.MOUNT, EgoWeaponsMobAnimations.DOUBT_A_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, EgoWeaponsMobAnimations.DOUBT_DEATH);
        clientAnimator.setCurrentMotionsAsDefault();
    }


    @Override
    public void processSpawnData(ByteBuf buf) {
        ClientAnimator animator = this.getClientAnimator();
        animator.addLivingAnimation(LivingMotions.IDLE, EgoWeaponsMobAnimations.DOUBT_A_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, EgoWeaponsMobAnimations.DOUBT_A_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, EgoWeaponsMobAnimations.DOUBT_A_WALK);
        animator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonAggressiveMobUpdateMotion(b);
    }

    @Override
    protected void initAI() {
        super.initAI();
        this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, STANDARD.build(this)));
        this.original.goalSelector.addGoal(1, new TargetChasingGoal(this, this.original, 1.3D, false));
    }

    @Override
    public <M extends Model> M getEntityModel(Models<M> models) {
        return models.get(new ResourceLocation(EgoWeaponsMod.MODID, "entity/doubt_a"));
    }




    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {

        if (this.getServerAnimator().animationPlayer.getAnimation().getId() == EgoWeaponsMobAnimations.DOUBT_GUARD.getId()) {
            boolean isFront = false;
            Vector3d sourceLocation = damageSource.getSourcePosition();

            if (sourceLocation != null) {
                Vector3d viewVector = this.getOriginal().getViewVector(1.0F);
                Vector3d toSourceLocation = sourceLocation.subtract(this.getOriginal().position()).normalize();

                if (toSourceLocation.dot(viewVector) > 0.0D) {
                    isFront = true;
                }
            }

            if (isFront) {
                this.playSound(EgoWeaponsSounds.METAL_CLASH, 1, 1);
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(((ServerWorld) this.getOriginal().level), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, this.getOriginal(), damageSource.getDirectEntity());
                StaggerSystem.reduceStagger(this.getOriginal(), amount/5, true);
                return new AttackResult(AttackResult.ResultType.BLOCKED, amount);
            }

        }
        return super.tryHurt(damageSource, amount);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.original.getAttribute(EpicFightAttributes.IMPACT.get()).setBaseValue(1.0D);
        this.original.getAttribute(EpicFightAttributes.WEIGHT.get()).setBaseValue(50.0D);
    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        switch (stunType) {
            case HOLD:
            case SHORT:
                return this.original.getRandom().nextBoolean() ? EgoWeaponsMobAnimations.DOUBT_STUN_SHORT_1 : EgoWeaponsMobAnimations.DOUBT_STUN_SHORT_2;
            case LONG: return EgoWeaponsMobAnimations.DOUBT_STUN_LONG;
            case KNOCKDOWN:
            case FALL:
                return EgoWeaponsMobAnimations.DOUBT_STUN_KNOCKDOWN;
            default: return null;
        }
    }

    @Override
    public StaticAnimation getStaggerAnimation() {
        return EgoWeaponsMobAnimations.DOUBT_STUN_STAGGER;
    }

    @Override
    public StaticAnimation getGroundAnimation(int strength) {
        return EgoWeaponsMobAnimations.DOUBT_PINDOWN;
    }

    @Override
    public StaticAnimation getLiftAnimation(int strength) {
        return EgoWeaponsMobAnimations.DOUBT_LIFTUP;
    }
}
