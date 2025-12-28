package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Predicate;

@Mixin(EntityPredicates.class)
public class EntityPredicatesMixin {


    @Inject(at = @At(value = "HEAD"), method = "pushableBy", cancellable = true)
    private static void pushable(Entity entity, CallbackInfoReturnable<Predicate<Entity>> cir) {

        if (entity instanceof PlayerEntity) {
            LivingEntity otherEntity = (LivingEntity) entity;

            LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) otherEntity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

            if (entitypatch != null) {
                DynamicAnimation currentanim = otherEntity.level.isClientSide() ? entitypatch.getClientAnimator().baseLayer.animationPlayer.getAnimation() : entitypatch.getServerAnimator().animationPlayer.getAnimation();
                if (currentanim.getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.DISABLE_COLLISION).orElse(false)) {
                    cir.setReturnValue(entity1 -> false);
                }
            }
        }
    }
}
