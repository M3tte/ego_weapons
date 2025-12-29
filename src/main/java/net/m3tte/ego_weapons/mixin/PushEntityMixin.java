package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EgoWeaponsAttributeSupplier;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.procedures.SharedFunctions.applyStaggerDamageGeneric;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.evaluateDamageSource;

@Mixin(LivingEntity.class)
public abstract class PushEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "pushEntities", cancellable = true)
    public void pushEntity(CallbackInfo ci) {
        LivingEntity self = ((LivingEntity) (Object)this);

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) self.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch != null) {
            DynamicAnimation currentanim = self.level.isClientSide() ? entitypatch.getClientAnimator().baseLayer.animationPlayer.getAnimation() : entitypatch.getServerAnimator().animationPlayer.getAnimation();

            //System.out.println("ISCLIENTSIDE ? "+self.level.isClientSide()+" --ANIM : "+currentanim);

            if (currentanim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION).orElse(false)) {
                self.setDeltaMovement(0,0,0);
                ci.cancel();

            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "push", cancellable = true)
    public void pushEntityInner(Entity entity, CallbackInfo ci) {
        LivingEntity self = ((LivingEntity) (Object)this);

        if (entity instanceof LivingEntity) {
            LivingEntity otherEntity = (LivingEntity) entity;

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) otherEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {
                 DynamicAnimation currentanim = otherEntity.level.isClientSide() ? entitypatch.getClientAnimator().baseLayer.animationPlayer.getAnimation() : entitypatch.getServerAnimator().animationPlayer.getAnimation();


                if (currentanim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION).orElse(false)) {
                    self.setDeltaMovement(0,0,0);
                    otherEntity.setDeltaMovement(0,0,0);
                    ci.cancel();
                }
            }
        }
    }



}
