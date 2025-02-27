package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import io.netty.buffer.ByteBuf;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsMobAnimations;
import net.m3tte.ego_weapons.potion.countEffects.Shell;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Behavior;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

public class NothingTherePatch extends MobPatch<NothingThere2Entity> implements StaggerableEntity {

    public static final CombatBehaviors.Builder<NothingTherePatch> STANDARD = CombatBehaviors.<NothingTherePatch>builder()
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(100.0F).canBeInterrupted(true).looping(true)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_AUTO_1).withinEyeHeight().withinDistance(0.0D, 3D))
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_AUTO_2).withinEyeHeight().withinDistance(0.0D, 6D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(50.0F).canBeInterrupted(true).looping(true)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_AUTO_B1).withinEyeHeight().withinDistance(0.0D, 3D))
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_AUTO_B2).withinEyeHeight().withinDistance(0.0D, 6D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(200.0F).canBeInterrupted(true).looping(true)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_AUTO_STAB).withinEyeHeight().withinDistance(0.0D, 1.8D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(60.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_DASH_C).withinEyeHeight().withinDistance(4D, 7D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(30.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_DASH_B).withinEyeHeight().withinDistance(4D, 7D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(20.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_GOODBYE).withinEyeHeight().withinDistance(0.0D, 3D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(5.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_SCREECH).withinEyeHeight().withinDistance(0.0D, 5D).custom((nt) -> !nt.original.hasEffect(Shell.get())))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(60.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(EgoWeaponsMobAnimations.NT_STOMP).withinEyeHeight().withinDistance(0.0D, 3D))
            );


    public NothingTherePatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, EgoWeaponsMobAnimations.NT_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, EgoWeaponsMobAnimations.NT_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.CHASE, EgoWeaponsMobAnimations.NT_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.FALL, EgoWeaponsMobAnimations.NT_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.MOUNT, EgoWeaponsMobAnimations.NT_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, EgoWeaponsMobAnimations.NT_IDLE);
        clientAnimator.setCurrentMotionsAsDefault();
    }


    @Override
    public void processSpawnData(ByteBuf buf) {
        ClientAnimator animator = this.getClientAnimator();
        animator.addLivingAnimation(LivingMotions.IDLE, EgoWeaponsMobAnimations.NT_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, EgoWeaponsMobAnimations.NT_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, EgoWeaponsMobAnimations.NT_WALK);
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
        return models.get(new ResourceLocation(EgoWeaponsMod.MODID, "entity/nothing_there"));
    }




    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {



        /*if (this.getServerAnimator().animationPlayer.getAnimation().getId() == TCorpMobAnimations.DOUBT_GUARD.getId()) {
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
                this.playSound(TCorpSounds.METAL_CLASH, 1, 1);
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(((ServerWorld) this.getOriginal().level), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, this.getOriginal(), damageSource.getDirectEntity());
                StaggerSystem.reduceStagger(this.getOriginal(), amount/5, true);
                return new AttackResult(AttackResult.ResultType.BLOCKED, amount);
            }

        }*/
        return super.tryHurt(damageSource, amount);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.original.getAttribute(EpicFightAttributes.IMPACT.get()).setBaseValue(5.0D);
        this.original.getAttribute(EpicFightAttributes.WEIGHT.get()).setBaseValue(999.0D);
        this.original.getAttribute(EpicFightAttributes.STUN_ARMOR.get()).setBaseValue(6.0D);
    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        switch (stunType) {
            default: return null;
        }
    }

    @Override
    public StaticAnimation getStaggerAnimation() {
        return null;
    }

    @Override
    public StaticAnimation getGroundAnimation(int strength) {
        return null;
    }

    @Override
    public StaticAnimation getStunAnimation(int strength) {
        return null;
    }

    @Override
    public StaticAnimation getLiftAnimation(int strength) {
        return null;
    }
}
