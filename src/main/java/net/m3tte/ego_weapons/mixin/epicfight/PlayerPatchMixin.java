package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

@Mixin(value = PlayerPatch.class, remap = false)
public abstract class PlayerPatchMixin<T extends LivingEntity> extends LivingEntityPatch<T> {


    @Shadow public abstract void initAnimator(ClientAnimator clientAnimator);

    @Inject(at = @At(value = "HEAD"), method = "getHitAnimation(Lyesman/epicfight/api/utils/ExtendedDamageSource$StunType;)Lyesman/epicfight/api/animation/types/StaticAnimation;", cancellable = true)
    private void overrideStunAnim(StunType stun, CallbackInfoReturnable<StaticAnimation> cir) {
        // If a player entity, do not ever display the bar as it is replaced.
        if (((PlayerEntity)this.original).getVehicle() == null) {
            WeaponCategory cat = this.getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory();
            if (cat instanceof EgoWeaponsCategories) {
                EgoWeaponsCategories egoCat = (EgoWeaponsCategories)cat;
                System.out.println("CAPABILITY IS : "+egoCat);
                switch (egoCat) {
                    case FULLSTOP_SNIPER:
                            switch (stun) {
                                case SHORT:
                                case HOLD:
                                    cir.setReturnValue(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_HIT_SHORT);
                                    return;
                                case LONG:
                                    cir.setReturnValue(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_HIT_LONG);
                                    return;
                            }
                        break;
                }
            }
        }
    }
}
