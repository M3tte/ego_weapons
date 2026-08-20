package net.m3tte.ego_weapons.world.capabilities.entitypatch;

import io.netty.buffer.ByteBuf;
import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.entities.NothingThere2Entity;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsMobAnimations;
import net.m3tte.ego_weapons.gameasset.mobMovesets.NothingThereMovesetAnimations;
import net.m3tte.ego_weapons.potion.Staggered;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Behavior;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.hitstunEntity;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.pummelDownEntity;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class NothingTherePatch extends MobPatch<NothingThere2Entity> implements StaggerableEntity {

    public static final CombatBehaviors.Builder<NothingTherePatch> STANDARD = CombatBehaviors.<NothingTherePatch>builder()
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(100.0F).canBeInterrupted(true).looping(true)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_AUTO_1).withinEyeHeight().withinDistance(0.0D, 5D))
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_AUTO_2).withinEyeHeight().withinDistance(0.0D, 7D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(50.0F).canBeInterrupted(true).looping(true)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_AUTO_B1).withinEyeHeight().withinDistance(0.0D, 5D))
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_AUTO_B2).withinEyeHeight().withinDistance(0.0D, 7D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(110.0F).canBeInterrupted(true).looping(true)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_AUTO_STAB).withinEyeHeight().withinDistance(0.0D, 3.5D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(60.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_DASH_C).withinEyeHeight().withinDistance(4D, 8D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(30.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_DASH_B).withinEyeHeight().withinDistance(4D, 8D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(25.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_CHARGE_RUN).withinEyeHeight().withinDistance(1D, 7D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(125.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_CHARGE_B_1).withinEyeHeight().withinDistance(0D, 6D).custom((nt) -> StaggerSystem.isStaggered(nt.getTarget())))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(25.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_CHARGE_B_1).withinEyeHeight().withinDistance(0D, 5D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(20.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_GOODBYE).withinEyeHeight().withinDistance(0.0D, 5D))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(90.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_GOODBYE_ENH).withinEyeHeight().withinDistance(0.0D, 6D).custom((nt) -> EgoWeaponsEffects.IMITATION.get().getPotency(nt.getOriginal()) >= 6))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(40.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_SCREECH).withinEyeHeight().withinDistance(0.0D, 7D).custom((nt) -> !nt.original.hasEffect(EgoWeaponsEffects.SHELL.get())))
            )
            .newBehaviorSeries(
                    BehaviorSeries.<NothingTherePatch>builder().weight(60.0F).canBeInterrupted(true).looping(true).cooldown(60)
                            .nextBehavior(Behavior.<NothingTherePatch>builder().animationBehavior(NothingThereMovesetAnimations.NT_STOMP).withinEyeHeight().withinDistance(0.0D, 3D))
            );


    public NothingTherePatch() {
        super(Faction.UNDEAD);
    }

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, NothingThereMovesetAnimations.NT_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, NothingThereMovesetAnimations.NT_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.CHASE, NothingThereMovesetAnimations.NT_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.FALL, NothingThereMovesetAnimations.NT_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.MOUNT, NothingThereMovesetAnimations.NT_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, NothingThereMovesetAnimations.NT_IDLE);
        clientAnimator.setCurrentMotionsAsDefault();
    }




    @Override
    public void processSpawnData(ByteBuf buf) {
        ClientAnimator animator = this.getClientAnimator();
        animator.addLivingAnimation(LivingMotions.IDLE, NothingThereMovesetAnimations.NT_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, NothingThereMovesetAnimations.NT_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, NothingThereMovesetAnimations.NT_WALK);
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
    protected void initAttributes() {
        super.initAttributes();
        this.original.getAttribute(EpicFightAttributes.IMPACT.get()).setBaseValue(5.0D);
        this.original.getAttribute(EpicFightAttributes.WEIGHT.get()).setBaseValue(999.0D);
        this.original.getAttribute(EpicFightAttributes.STUN_ARMOR.get()).setBaseValue(6.0D);
        this.original.getAttribute(EgoWeaponsAttributes.MAX_STAGGER.get()).setBaseValue(60.0D);

        this.original.getAttribute(EgoWeaponsAttributes.BLUNT_RESISTANCE.get()).setBaseValue(0.7D);
        this.original.getAttribute(EgoWeaponsAttributes.PIERCE_RESISTANCE.get()).setBaseValue(0.9D);
        this.original.getAttribute(EgoWeaponsAttributes.SLASH_RESISTANCE.get()).setBaseValue(0.8D);

        this.original.getAttribute(EgoWeaponsAttributes.RED_RESISTANCE.get()).setBaseValue(0.1D);
        this.original.getAttribute(EgoWeaponsAttributes.WHITE_RESISTANCE.get()).setBaseValue(0.5D);
        this.original.getAttribute(EgoWeaponsAttributes.BLACK_RESISTANCE.get()).setBaseValue(0.5D);
        this.original.getAttribute(EgoWeaponsAttributes.PALE_RESISTANCE.get()).setBaseValue(1.0D);
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
    public StaticAnimation getClashStunAnim(int strength) {
        return null;
    }

    @Override
    public StaticAnimation getStunAnimation(int strength) {
        return null;
    }

    @Override
    public StaticAnimation getHitstunAnimation(int strength) {
        return null;
    }

    @Override
    public StaticAnimation getLiftAnimation(int strength) {
        return null;
    }
}
