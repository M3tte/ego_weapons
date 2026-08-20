package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.valids;

@Mixin(value = LivingEntityPatch.class, remap = false)
public abstract class LivingEntityPatchMixin<T extends LivingEntity> extends EntityPatch<T> {


    @Shadow public abstract CapabilityItem getHoldingItemCapability(Hand hand);

    // List of resource ids that should always render in the offhand as well...



    @Inject(at = @At(value = "HEAD"), method = "isOffhandItemValid()Z", cancellable = true)
    private void bypassOffhandHide(CallbackInfoReturnable<Boolean> cir) {
        String resourceName = this.original.getItemInHand(Hand.OFF_HAND).getItem().getRegistryName().toString();
        if (Arrays.asList(valids).contains(resourceName)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
