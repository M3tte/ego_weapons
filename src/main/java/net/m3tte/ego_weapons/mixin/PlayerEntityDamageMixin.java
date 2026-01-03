package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.potion.Panic;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.*;

@Mixin(PlayerEntity.class)
public class PlayerEntityDamageMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V")
    public void applyStaggerDamage(DamageSource src, float amount, CallbackInfo ci) {
        LivingEntity self = ((LivingEntity) (Object)this);
        applyStaggerDamageGeneric(src, amount, ci, self);
    }



    @Shadow @Final public PlayerInventory inventory;

    @Inject(at = @At(value = "HEAD"), method = "canHarmPlayer(Lnet/minecraft/entity/player/PlayerEntity;)Z", cancellable = true)
    public void overrideFriendlyFire(PlayerEntity target, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity self = ((PlayerEntity) (Object)this);

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

        EgoWeaponsModVars.PlayerVariables entityData = self.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


        AttackLogicPredicate predicate = currentanim.getRealAnimation().getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.LOGIC_PREDICATE).orElse(AttackLogicPredicate.DEFAULT);

        if (predicate.equals(AttackLogicPredicate.MAGIC_BULLET_FIRE) && SanitySystem.getSanity(self) / EgoWeaponsAttributes.getMaxSanity(self) <= 0.5f) {
            cir.setReturnValue(true);
        }

        int sinOnSelf = EgoWeaponsEffects.SIN.get().getPotency(self);
        int sinOnTarget = EgoWeaponsEffects.SIN.get().getPotency(target);

        if (sinOnSelf >= 4 ||sinOnTarget >= 4)
            cir.setReturnValue(true);


        if (self.hasEffect(Panic.get()))
            cir.setReturnValue(true);

        if (target.hasEffect(Panic.get()))
            cir.setReturnValue(true);



    }

    @ModifyVariable(method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", at = @At("HEAD"), ordinal =0, argsOnly = true)
    private DamageSource damageSourceModifier(DamageSource value) {

        if (value instanceof GenericEgoDamage)
            return value;

        return evaluateDamageSource(value);
    }

    @ModifyVariable(at = @At(value = "HEAD"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", ordinal = 0, argsOnly = true)
    public float modifyDamageMixin(float amount, DamageSource source) {

        LivingEntity self = ((LivingEntity) (Object)this);
        // Modifier for Mimicry


        return modifyDamageGeneric(amount, source, self);
    }

    @Inject(method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", at = @At("TAIL"))
    private void sendDialogueOnDamage(DamageSource src, float amnt, CallbackInfo ci) {

        DialogueSystem.onHitDialogueEvaluation(((PlayerEntity)(Object)this), src);
    }

}
