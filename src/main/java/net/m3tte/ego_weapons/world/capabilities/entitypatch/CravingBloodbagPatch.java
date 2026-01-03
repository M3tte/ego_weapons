package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import io.netty.buffer.ByteBuf;
import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.entities.CravingBloodbagEntity;
import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.entities.CravingBloodbagAnims;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.potion.Staggered;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.ZombiePatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Behavior;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Health.Comparator;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

public class CravingBloodbagPatch extends MobPatch<CravingBloodbagEntity> implements StaggerableEntity {

    public static final CombatBehaviors.Builder<CravingBloodbagPatch> STANDARD = CombatBehaviors.<CravingBloodbagPatch>builder()
            .newBehaviorSeries(
                    BehaviorSeries.<CravingBloodbagPatch>builder().weight(500F).canBeInterrupted(false).looping(false).cooldown(160)
                            .nextBehavior(Behavior.<CravingBloodbagPatch>builder().animationBehavior(CravingBloodbagAnims.CRAVING_BLOODBAG_EXECUTE).withinAngleHorizontal(-30,30).withinEyeHeight().withinDistance(0.0D, 3.5D).custom((e) -> {
                                // Execute if the target is staggered
                                if (e.getTarget() != null) {
                                    return e.getTarget().hasEffect(Staggered.get());
                                }
                                return false;
                            }))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<CravingBloodbagPatch>builder().weight(100.0F).canBeInterrupted(false).looping(false)
                            .nextBehavior(Behavior.<CravingBloodbagPatch>builder().animationBehavior(CravingBloodbagAnims.CRAVING_BLOODBAG_ATTACK_1).withinAngleHorizontal(-50,50).withinEyeHeight().withinDistance(0.0D, 3.5D))
                            .nextBehavior(Behavior.<CravingBloodbagPatch>builder().animationBehavior(CravingBloodbagAnims.CRAVING_BLOODBAG_ATTACK_2).withinDistance(0.0D, 3.5D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<CravingBloodbagPatch>builder().weight(40.0F).canBeInterrupted(false).looping(false).cooldown(160)
                            .nextBehavior(Behavior.<CravingBloodbagPatch>builder().animationBehavior(CravingBloodbagAnims.CRAVING_BLOODBAG_COUNTER_START).withinAngleHorizontal(-30,30).withinEyeHeight().withinDistance(0.0D, 3.5D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<CravingBloodbagPatch>builder().weight(60.0F).canBeInterrupted(false).looping(false).cooldown(100)
                            .nextBehavior(Behavior.<CravingBloodbagPatch>builder().animationBehavior(CravingBloodbagAnims.CRAVING_BLOODBAG_DASH).withinAngleHorizontal(-30,30).withinEyeHeight().withinDistance(3.5D, 7.0D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<CravingBloodbagPatch>builder().weight(30.0F).canBeInterrupted(false).looping(false).cooldown(160)
                            .nextBehavior(Behavior.<CravingBloodbagPatch>builder().animationBehavior(CravingBloodbagAnims.CRAVING_BLOODBAG_EVADE).withinDistance(0.0D, 5.0D).custom((e) -> {

                                // Only actually trigger an evade when the target is attacking.

                                if (e.getTarget() != null) {
                                    LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) e.getTarget().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                                    if (sourcePatch.getServerAnimator() != null)
                                        if (sourcePatch.getServerAnimator().animationPlayer != null)
                                            return sourcePatch.getServerAnimator().animationPlayer.getAnimation() instanceof AttackAnimation;

                                }
                                return false;
                            }))
            );

    public CravingBloodbagPatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, CravingBloodbagAnims.CRAVING_BLOODBAG_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, CravingBloodbagAnims.CRAVING_BLOODBAG_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.CHASE, CravingBloodbagAnims.CRAVING_BLOODBAG_RUN);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, CravingBloodbagAnims.CRAVING_BLOODBAG_DEATH);
        clientAnimator.setCurrentMotionsAsDefault();
    }


    @Override
    public void processSpawnData(ByteBuf buf) {
        ClientAnimator animator = this.getClientAnimator();
        animator.addLivingAnimation(LivingMotions.IDLE, CravingBloodbagAnims.CRAVING_BLOODBAG_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, CravingBloodbagAnims.CRAVING_BLOODBAG_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, CravingBloodbagAnims.CRAVING_BLOODBAG_RUN);
        animator.addLivingAnimation(LivingMotions.DEATH, CravingBloodbagAnims.CRAVING_BLOODBAG_DEATH);
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
        this.original.goalSelector.addGoal(1, new TargetChasingGoal(this, this.original, 1.5D, false));
    }

    @Override
    public <M extends Model> M getEntityModel(Models<M> models) {
        return models.get(new ResourceLocation(EgoWeaponsMod.MODID, "entity/craving_bloodbag"));
    }

    @Override
    public void onHurtSomeone(Entity target, Hand handIn, ExtendedDamageSource damagesource, float amount, boolean succeed) {
        super.onHurtSomeone(target, handIn, damagesource, amount, succeed);

        if (target.isInvulnerable())
            return;

        DynamicAnimation currentanim = this.getServerAnimator().animationPlayer.getAnimation();


        if (!(target instanceof LivingEntity))
            return;

        if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
            String animationIdentifier = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

            LivingEntity targetLiving = (LivingEntity) target;

            AttackAnimation.Phase phase = null;
            if (currentanim instanceof EgoAttackAnimation) {
                phase = ((EgoAttackAnimation)currentanim).getPhaseByTime(this.getAnimator().getPlayerFor(currentanim).getElapsedTime());
            }

            if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
                String elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(null);

                if (elp != null)
                    animationIdentifier = elp;
            }

            LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            switch (animationIdentifier) {
                case "bloodbag_attack_1":
                case "bloodbag_attack_2":
                    EgoWeaponsEffects.BLEED.get().increment(targetLiving, 0, 2);
                    break;
                case "bloodbag_dash":
                    EgoWeaponsEffects.BLEED.get().increment(targetLiving, 2, 1);
                    break;
                case "bloodbag_counter":
                case "bloodbag_execute":
                    EgoWeaponsEffects.BLEED.get().increment(targetLiving, 2, 2);
                    break;
            }
        }

    }



    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        if (amount < this.getOriginal().getHealth() && amount < StaggerSystem.getStagger(this.getOriginal())) {
            if (this.getServerAnimator().animationPlayer.getAnimation().getId() == CravingBloodbagAnims.CRAVING_BLOODBAG_COUNTER_START.getId() && !damageSource.isMagic() && this.getOriginal().hasEffect(EgoWeaponsEffects.RESILIENCE.get())) {
                boolean isFront = false;
                boolean isRanged = false;

                if (damageSource.getDirectEntity() == null)
                    return super.tryHurt(damageSource, amount);

                LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) damageSource.getDirectEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

                if (sourcePatch != null) {
                    if (sourcePatch.getServerAnimator() != null) {
                        DynamicAnimation anim = sourcePatch.getServerAnimator().animationPlayer.getAnimation();
                        isRanged = anim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.ATTACK_MOVE_TYPE).orElse(AttackMoveType.MELEE) == AttackMoveType.RANGED;
                    }
                }

                Vector3d sourceLocation = damageSource.getSourcePosition();

                if (sourceLocation != null) {
                    Vector3d viewVector = this.getOriginal().getViewVector(1.0F);
                    Vector3d toSourceLocation = sourceLocation.subtract(this.getOriginal().position()).normalize();

                    if (toSourceLocation.dot(viewVector) > 0.0D) {
                        isFront = true;
                    }
                }

                if (isFront && (!isRanged)) {
                    this.playAnimationSynchronized(CravingBloodbagAnims.CRAVING_BLOODBAG_COUNTER_TRIGGER, 0.05f);
                    SharedFunctions.hitstunEntity(sourcePatch, 1, false, 1);
                }
            }
        }


        return super.tryHurt(damageSource, amount);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.original.getAttribute(EpicFightAttributes.IMPACT.get()).setBaseValue(3.2D);
        this.original.getAttribute(EpicFightAttributes.WEIGHT.get()).setBaseValue(50.0D);

        this.original.getAttribute(EgoWeaponsAttributes.MAX_STAGGER.get()).setBaseValue(30D);

        this.original.getAttribute(EgoWeaponsAttributes.BLUNT_RESISTANCE.get()).setBaseValue(1.5D);
        this.original.getAttribute(EgoWeaponsAttributes.PIERCE_RESISTANCE.get()).setBaseValue(1.0D);
        this.original.getAttribute(EgoWeaponsAttributes.SLASH_RESISTANCE.get()).setBaseValue(1.0D);

        this.original.getAttribute(EgoWeaponsAttributes.RED_RESISTANCE.get()).setBaseValue(1D);
        this.original.getAttribute(EgoWeaponsAttributes.WHITE_RESISTANCE.get()).setBaseValue(0.7D);
        this.original.getAttribute(EgoWeaponsAttributes.BLACK_RESISTANCE.get()).setBaseValue(1.5D);
        this.original.getAttribute(EgoWeaponsAttributes.PALE_RESISTANCE.get()).setBaseValue(2D);
    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        switch (stunType) {
            case LONG: return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_LONG;
            case KNOCKDOWN:
            case FALL:
                return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_KNOCKDOWN;
            case HOLD:
            case SHORT: return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_SHORT;
            default: return null;
        }
    }

    @Override
    public StaticAnimation getStaggerAnimation() {
        return CravingBloodbagAnims.CRAVING_BLOODBAG_STAGGER;
    }

    @Override
    public StaticAnimation getGroundAnimation(int strength) {
        return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_DOWN;
    }

    @Override
    public StaticAnimation getClashStunAnim(int strength) {
        return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_LONG;
    }

    @Override
    public StaticAnimation getStunAnimation(int strength) {
        return CravingBloodbagAnims.CRAVING_BLOODBAG_STAGGER;
    }

    @Override
    public StaticAnimation getHitstunAnimation(int strength) {
        return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_HIT;
    }

    @Override
    public StaticAnimation getLiftAnimation(int strength) {
        return CravingBloodbagAnims.CRAVING_BLOODBAG_STUN_UP;
    }
}
