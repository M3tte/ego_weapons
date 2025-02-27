package net.m3tte.ego_weapons.entities;

import net.m3tte.ego_weapons.EgoWeaponsSounds;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class NothingThere2Entity extends MonsterEntity {

    public NothingThere2Entity(EntityType<NothingThere2Entity> entityType, World world) {
        super(entityType, world);
    }


    protected void registerGoals() {

        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractRaiderEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createMonsterAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1500.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
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
}
