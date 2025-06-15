package net.m3tte.ego_weapons.mixin;

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

import static net.m3tte.ego_weapons.procedures.SharedFunctions.*;

@Mixin(PlayerEntity.class)
public class PlayerEntityDamageMixin {

    @Shadow @Final public PlayerInventory inventory;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V")
    public void applyStaggerDamage(DamageSource src, float amount, CallbackInfo ci) {
        LivingEntity self = ((LivingEntity) (Object)this);
        applyStaggerDamageGeneric(src, amount, ci, self);
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



}
