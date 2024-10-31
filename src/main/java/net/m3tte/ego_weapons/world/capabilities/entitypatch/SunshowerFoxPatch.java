package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import io.netty.buffer.ByteBuf;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.entities.SunshowerFoxEntity;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsMobAnimations;
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
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Behavior;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Health.Comparator;

public class SunshowerFoxPatch extends MobPatch<SunshowerFoxEntity> implements StaggerableEntity {

    public static final CombatBehaviors.Builder<SunshowerFoxPatch> STANDARD = CombatBehaviors.<SunshowerFoxPatch>builder()
            .newBehaviorSeries(
                    BehaviorSeries.<SunshowerFoxPatch>builder().weight(100.0F).canBeInterrupted(true).looping(false).cooldown(4)
                            .nextBehavior(Behavior.<SunshowerFoxPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_DASH).withinDistance(2.0D, 4.0D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<SunshowerFoxPatch>builder().weight(50.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<SunshowerFoxPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_1).withinEyeHeight().withinDistance(0.0D, 2.25D))
                            .nextBehavior(Behavior.<SunshowerFoxPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_2).withinEyeHeight().withinDistance(0.0D, 2.25D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<SunshowerFoxPatch>builder().weight(10.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<SunshowerFoxPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_B1).withinEyeHeight().withinDistance(0.0D, 2.25D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<SunshowerFoxPatch>builder().weight(90.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<SunshowerFoxPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_GUARD).withinEyeHeight().health(20, Comparator.LESS_ABSOLUTE).withinDistance(0.0D, 3D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<SunshowerFoxPatch>builder().weight(10.0F).canBeInterrupted(true).looping(false)
                            .nextBehavior(Behavior.<SunshowerFoxPatch>builder().animationBehavior(EgoWeaponsMobAnimations.DOUBT_AUTO_BLUNT).withinEyeHeight().withinDistance(0.0D, 1D))
            );


    public SunshowerFoxPatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, EgoWeaponsMobAnimations.SUNSHOWER_FOX_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, EgoWeaponsMobAnimations.SUNSHOWER_FOX_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.CHASE, EgoWeaponsMobAnimations.SUNSHOWER_FOX_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.FALL, EgoWeaponsMobAnimations.SUNSHOWER_FOX_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.MOUNT, EgoWeaponsMobAnimations.SUNSHOWER_FOX_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, EgoWeaponsMobAnimations.SUNSHOWER_FOX_IDLE);
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
        //this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, STANDARD.build(this)));
        //this.original.goalSelector.addGoal(1, new TargetChasingGoal(this, this.original, 1.3D, false));
    }

    @Override
    public <M extends Model> M getEntityModel(Models<M> models) {
        return models.get(new ResourceLocation(EgoWeaponsMod.MODID, "entity/sunshower_fox"));
    }




    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
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
            case LONG:
            case KNOCKDOWN:
            case FALL:
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
    public StaticAnimation getLiftAnimation(int strength) {
        return null;
    }
}
