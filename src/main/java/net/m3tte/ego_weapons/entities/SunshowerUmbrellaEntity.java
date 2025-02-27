package net.m3tte.ego_weapons.entities;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.particle.ArmourupparticleParticle;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SunshowerUmbrellaEntity extends MobEntity {

    public SunshowerUmbrellaEntity(EntityType<SunshowerUmbrellaEntity> entityType, World world) {
        super(entityType, world);
    }

    LivingEntity source = null;

    @Override
    public boolean hurt(DamageSource source, float p_70097_2_) {
        int ownerID = this.getPersistentData().getInt("ownerEntity");


        if (source.getEntity() != null)
            if (source.getEntity().getId() == ownerID)
                return false;

        if (source.getDirectEntity() != null) {
            if (source.getDirectEntity().getId() == ownerID)
                return false;
        }






        return super.hurt(source, p_70097_2_);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getPersistentData() == null)
            return;

        int ownerID = this.getPersistentData().getInt("ownerEntity");
        int consumedSinking = this.getPersistentData().getInt("consumedSinking");

        if (consumedSinking >= 5) {
            this.addEffect(new EffectInstance(EgoWeaponsEffects.PROTECTION.get(), 20, consumedSinking / 5));
        }


        if (this.tickCount % 140 == 20) {

            if (!this.level.isClientSide()) {
                ((ServerWorld)this.level).sendParticles(EgoWeaponsParticles.PUDDLE_STOMP_RIPPLE.get(),this.getX(),this.getY()+0.01,this.getZ(),1,0, 0, 0,0);
            }

            this.getPersistentData().putInt("agedEffects", this.getPersistentData().getInt("agedEffects") + 1);
            List<Entity> _entfound = this.level
                    .getNearbyEntities(LivingEntity.class,
                            EntityPredicate.DEFAULT, null, new AxisAlignedBB(this.getX() - (7f), this.getY() - (7f), this.getZ() - (7f), this.getX() + (7f), this.getY() + (7f), this.getZ() + (7f)))
                    .stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
                        }
                    }.compareDistOf(this.getX(), this.getY(), this.getZ())).collect(Collectors.toList());


            if (_entfound.size() > 0) {
                for (Entity e : _entfound) {
                    if (e instanceof LivingEntity && !(e instanceof SunshowerUmbrellaEntity)) {
                        LivingEntity livingEntity = (LivingEntity) e;



                        if (livingEntity.getId() == ownerID) {
                            source = livingEntity;
                            if (!this.level.isClientSide()) {
                                ((ServerWorld)this.level).sendParticles(ArmourupparticleParticle.particle,livingEntity.getX(),livingEntity.getY()+livingEntity.getBbHeight()/2,livingEntity.getZ(),consumedSinking/5,0.01, livingEntity.getBbWidth()/2, livingEntity.getBbHeight()/2,livingEntity.getBbWidth()/2);
                            }
                            if (livingEntity.getAbsorptionAmount() < (float) consumedSinking / 2 + 5) {
                                livingEntity.setAbsorptionAmount(Math.min((float) consumedSinking / 2 + 5, livingEntity.getAbsorptionAmount() + (float) consumedSinking /5));
                            }
                        } else {
                            EgoWeaponsEffects.SINKING.get().increment(livingEntity, 1, 1);
                            livingEntity.hurt(DamageSource.mobAttack(this), 1);
                        }
                    }
                }
            }

            if (this.getPersistentData().getInt("agedEffects") > 5 + consumedSinking/4) {
                this.remove();
            }
        }
    }

    @Override
    public void die(DamageSource p_70645_1_) {
        super.die(p_70645_1_);

        if (p_70645_1_.getEntity() != null || p_70645_1_.getDirectEntity() != null) {
            if (source != null) {
                EgoWeaponsEffects.RUPTURE.get().increment(source, 1, 2);
            }
        }
    }

    public static AttributeModifierMap.MutableAttribute createMonsterAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 1D);
    }

    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    public void aiStep() {
        super.aiStep();
    }


    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity creatureentity = (CreatureEntity)this.getVehicle();
            this.yBodyRot = creatureentity.yBodyRot;
        }
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.5F;
    }


}
