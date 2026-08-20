package net.m3tte.ego_weapons.entities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.ai.AbnormalityRetaliateGoal;
import net.m3tte.ego_weapons.gameasset.mobMovesets.NothingThereMovesetAnimations;
import net.m3tte.ego_weapons.potion.Staggered;
import net.m3tte.ego_weapons.potion.countEffects.BleedEffect;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.entitypatch.NothingTherePatch;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import javax.annotation.Nullable;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.hitstunEntity;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.pummelDownEntity;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class NothingThere2Entity extends MonsterEntity implements OnHitOnKillEntity, EGOTargetingEntity {

    float targetingTimestamp = 0;
    public NothingThere2Entity(EntityType<NothingThere2Entity> entityType, World world) {
        super(entityType, world);
    }


    protected void registerGoals() {

        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new AbnormalityRetaliateGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createMonsterAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1500.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 17D);

    }

    private int stepCounter = 0;
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        stepCounter++;

        if (stepCounter > 2) {
            stepCounter = 0;
            this.playSound(this.getStepSound(), 0.15F, 1.0F);
        }


    }

    protected SoundEvent getStepSound() {
        return EgoWeaponsSounds.NOTHING_THERE_WALK;
    }


    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    public void aiStep() {
        super.aiStep();
    }

    protected SoundEvent getAmbientSound() {
        int sound = random.nextInt(4);

        switch(sound) {
            default: return EgoWeaponsSounds.NOTHING_THERE_VOICE_1;
            case 1: return EgoWeaponsSounds.NOTHING_THERE_VOICE_2;
            case 2: return EgoWeaponsSounds.NOTHING_THERE_VOICE_3;
            case 3: return EgoWeaponsSounds.NOTHING_THERE_VOICE_4;
        }
    }

    @Override
    public void playAmbientSound() {
        super.playAmbientSound();

        this.playSound(this.random.nextBoolean() ? EgoWeaponsSounds.NOTHING_THERE_IDLE_1 : EgoWeaponsSounds.NOTHING_THERE_IDLE_2, this.getSoundVolume(), this.getVoicePitch());
    }

    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity creatureentity = (CreatureEntity)this.getVehicle();
            this.yBodyRot = creatureentity.yBodyRot;
        }
    }


    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        p_213386_4_ = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
        this.populateDefaultEquipmentSlots(p_213386_2_);
        this.populateDefaultEquipmentEnchantments(p_213386_2_);
        this.setCanPickUpLoot(false);

        return p_213386_4_;
    }

    protected AbstractArrowEntity getArrow(ItemStack p_213624_1_, float p_213624_2_) {
        return ProjectileHelper.getMobArrow(this, p_213624_1_, p_213624_2_);
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 3.4F;
    }

    public double getMyRidingOffset() {
        return -0.6D;
    }


    public float onHit(LivingEntity target, DamageSource source, float amount, float mult) {
        NothingTherePatch ntPatch = (NothingTherePatch) this.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        UtilitySystems.EGOAttackContext context = generateAttackContext(ntPatch);

        int imitations = EgoWeaponsEffects.IMITATION.get().getPotency(this);

        if (imitations > 0) {
            mult += SharedFunctions.incrementBonusDamage(source, 0.1f * imitations);
        }

        if (context.isValidEgoAnimation()) {
            switch (context.getAnimationIdentifier()) {
                case "nt_run_attack_1":
                    hitstunEntity(targetPatch, 2, true, 0.5f);
                    EgoWeaponsEffects.TREMOR.get().increment(target, 0, 3);
                    EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 5, 1);
                    TremorEffect.burstTremor(target, true);

                    break;
                case "nt_run_attack_2":
                    EgoWeaponsEffects.TREMOR.get().increment(target, 2, 6);
                    EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 5, 1);
                    TremorEffect.burstTremor(target, true);

                    break;
                case "nt_goodbye":
                    EgoWeaponsEffects.IMITATION.get().increment(this, 10, 1);
                    EgoWeaponsEffects.BLEED.get().increment(target, 0, 8);
                    EgoWeaponsEffects.FRAGILE.get().increment(target, 0, 1);
                    break;
                case "nt_goodbye_enh_1":
                    EgoWeaponsEffects.BLEED.get().increment(target, 0, 8);
                    EgoWeaponsEffects.FRAGILE.get().increment(target, 0, 1);
                    break;
                case "nt_goodbye_enh_2":
                    EgoWeaponsEffects.BLEED.get().increment(target, 0, 8);
                    EgoWeaponsEffects.FRAGILE.get().increment(target, 0, 1);
                    if (target.hasEffect(EgoWeaponsEffects.BLEED.get()) && !target.level.isClientSide()) {
                        BleedEffect.apply(target);
                    }
                    break;
                case "nt_auto_1":
                case "nt_auto_2":
                    EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
                    break;
                case "nt_auto_b_1":
                case "nt_auto_b_2":
                    EgoWeaponsEffects.TREMOR.get().increment(target, 0, 1);
                    break;

                case "nt_charge_b_1":
                    target.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                    EgoWeaponsEffects.TREMOR.get().increment(target, 2, 4);
                    pummelDownEntity(targetPatch, 2, true);
                    TremorEffect.burstTremor(target, true);
                    EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 5, 2);
                    break;

                case "nt_charge_b_2":
                    EgoWeaponsEffects.TREMOR.get().increment(target, 1, 2);
                    break;

                case "nt_auto_stab":
                    this.heal(10);
                    EgoWeaponsEffects.IMITATION.get().increment(this, 10, 1);
                    EgoWeaponsEffects.BLEED.get().increment(target, 3, 1);
                    EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().increment(target, 3, 1);
                    break;
                case "nt_dash_claw":
                    ntPatch.playAnimationSynchronized(NothingThereMovesetAnimations.NT_DASH_C_F, 0.01f);
                    EgoWeaponsEffects.BLEED.get().increment(target, 1, 1);
                    break;
                case "nt_dash_claw_f":
                    EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().increment(target, 5, 1);
                    EgoWeaponsEffects.BLEED.get().increment(target, 2, 1);
                    break;
                case "nt_dash_blunt":
                    pummelDownEntity(targetPatch, 2, true);
                    ntPatch.playAnimationSynchronized(NothingThereMovesetAnimations.NT_DASH_B_F, 0.01f);
                    StaggerSystem.reduceStagger(target, 6, false);
                    this.getPersistentData().putInt("pounceHits", 1);
                    EgoWeaponsEffects.TREMOR.get().increment(target, 1, 1);
                    break;
                case "nt_dash_blunt_f": // Repeat blunt attacks
                    int hitCount = 0;
                    if (this.getPersistentData().contains("pounceHits")) {
                        hitCount = this.getPersistentData().getInt("pounceHits");
                    }

                    if (hitCount < 3 && !this.level.isClientSide() && (this.level.random.nextFloat() < 0.75f || target.hasEffect(Staggered.get()))) {
                        target.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 0));
                        pummelDownEntity(targetPatch, 2, true);
                        ntPatch.playAnimationSynchronized(NothingThereMovesetAnimations.NT_DASH_B_F, 0.01f);
                        StaggerSystem.reduceStagger(target, 3, false);
                        this.getPersistentData().putInt("pounceHits", hitCount+1);

                        EgoWeaponsEffects.TREMOR.get().increment(target, 1, 1);
                    } else if (hitCount > 0)  {
                        TremorEffect.burstTremor(target, true);
                    }
                    break;
            }
        }

        int shell = EgoWeaponsEffects.SHELL.get().getPotency(this);

        if (shell > 0) {
            this.heal(shell * 0.8f);
        }

        return mult;
    }



    public void onKill(LivingEntity target) {
        NothingTherePatch ntPatch = (NothingTherePatch) this.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        UtilitySystems.EGOAttackContext context = generateAttackContext(ntPatch);

        if (context.isValidEgoAnimation()) {
            EgoWeaponsEffects.SHELL.get().increment(this, 5, 1);

            switch (context.getAnimationIdentifier()) {
                default:
                    EgoWeaponsEffects.IMITATION.get().increment(this, 10, 1);
                    break;
                case "nt_goodbye_enh_1":
                case "nt_goodbye_enh_2":
                    EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(this, 10, 2);
                    break;
                case "nt_goodbye":
                    EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(this, 10, 2);
                    EgoWeaponsEffects.IMITATION.get().increment(this, 10, 2);
                    break;
                case "nt_charge_b_1":
                    EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(this, 10, 1);
                    EgoWeaponsEffects.IMITATION.get().increment(this, 10, 2);
                    break;
                case "nt_auto_stab":
                    this.heal(10);
                    EgoWeaponsEffects.IMITATION.get().increment(this, 10, 1);
                    break;
            }
        }
    }

    @Override
    public float getTargetingResetTimestamp() {
        return this.targetingTimestamp;
    }

    @Override
    public void setTargetingResetTimestamp(int stamp) {
        this.targetingTimestamp = stamp;
    }
}
