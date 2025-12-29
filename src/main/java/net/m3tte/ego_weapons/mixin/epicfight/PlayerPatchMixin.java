package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.JustitiaMovesetAnims;
import net.m3tte.ego_weapons.world.capabilities.item.EgoWeaponsCategories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource.StunType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.effect.EpicFightMobEffects;

@Mixin(value = PlayerPatch.class, remap = false)
public abstract class PlayerPatchMixin<T extends LivingEntity> extends LivingEntityPatch<T> {


    // @Shadow public abstract void initAnimator(ClientAnimator clientAnimator);

    @Inject(at = @At(value = "HEAD"), method = "getHitAnimation(Lyesman/epicfight/api/utils/ExtendedDamageSource$StunType;)Lyesman/epicfight/api/animation/types/StaticAnimation;", cancellable = true)
    private void overrideStunAnim(StunType stun, CallbackInfoReturnable<StaticAnimation> cir) {
        // If a player entity, do not ever display the bar as it is replaced.

        if (this.original == null)
            return;

        if (!this.original.isAlive())
            return;


        if (this.original.hasEffect(EgoWeaponsEffects.RESILIENCE.get())) {
            cir.setReturnValue(null);
            return;
        }

        if (((PlayerEntity)this.original).getVehicle() == null && !this.original.hasEffect(EpicFightMobEffects.STUN_IMMUNITY.get())) {
            WeaponCategory cat = this.getHoldingItemCapability(Hand.MAIN_HAND).getWeaponCategory();
            if (cat instanceof EgoWeaponsCategories) {
                EgoWeaponsCategories egoCat = (EgoWeaponsCategories)cat;
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

                switch (egoCat) {
                    case JUSTITIA:
                        switch (stun) {
                            case SHORT:
                            case HOLD:
                                cir.setReturnValue(JustitiaMovesetAnims.JUSTITIA_HIT_SHORT);
                                return;
                            case LONG:
                                cir.setReturnValue(JustitiaMovesetAnims.JUSTITIA_HIT_LONG);
                                return;
                        }
                        break;
                }
            }
        }
    }
}
