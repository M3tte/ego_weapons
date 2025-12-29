package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.m3tte.ego_weapons.world.capabilities.EgoWeaponsAttributeSupplier;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
public abstract class LivingEntityMixin {


    @Inject(at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", cancellable = true)
    private void egoWeaponsConstructor(EntityType<?> entityType, World level, CallbackInfo info) {
        LivingEntity self = (LivingEntity)((Object)this);

        self.getAttributes().supplier = new EgoWeaponsAttributeSupplier(self.getAttributes().supplier);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeHooks;onLivingDamage(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/DamageSource;F)F"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V")
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




    @Inject(method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", at = @At("TAIL"))
    private void sendDialogueOnDamage(DamageSource src, float amnt, CallbackInfo ci) {

        DialogueSystem.onHitDialogueEvaluation(((LivingEntity)(Object)this), src);
    }




    /*
    Placeholder for implementing damage types
    @ModifyVariable(at = @At(value = "HEAD"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", ordinal = 0, argsOnly = true)
    public DamageSource modifyDamageSourceMixin(DamageSource source) {

        return source;
    }*/

    @ModifyVariable(at = @At(value = "HEAD"), method = "actuallyHurt(Lnet/minecraft/util/DamageSource;F)V", ordinal = 0, argsOnly = true)
    public float modifyDamageMixin(float amount, DamageSource source) {

        LivingEntity self = ((LivingEntity) (Object)this);
        return SharedFunctions.modifyDamageGeneric(amount, source, self);
    }



}
