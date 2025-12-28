package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = PatchedItemInHandLayer.class, remap = false)
public abstract class ItemInHandLayerMixin<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, LayerRenderer<E, M>> {

    @ModifyVariable(method = "renderLayer(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I[Lyesman/epicfight/api/utils/math/OpenMatrix4f;FFF)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int augmentBrightness(int packedLightIn, T entitypatch) {
        // Placeholder, return ~200 for items that should be light up, trust me bro.
        ItemStack item = entitypatch.getOriginal().getItemInHand(Hand.MAIN_HAND);

        if (item.getItem().equals(EgoWeaponsItems.FIREFIST_GAUNTLET.get())) {
            if (entitypatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.FIREFIST_SUIT.get())) {
                int fuel = entitypatch.getOriginal().getItemBySlot(EquipmentSlotType.CHEST).getOrCreateTag().getInt("d10fuel");

                if (entitypatch.getOriginal().hasEffect(EgoWeaponsEffects.FUEL_IGNITION.get()) && fuel > 0)
                    return 230;

                if (fuel <= 50 && fuel > 0)
                    return 230;
            }

            if (item.getOrCreateTag().getInt("ignition") > 0)
                return 230;
        }

        if (item.getItem().equals(EgoWeaponsItems.STIGMA_WORKSHOP_SWORD.get())) {
            if (item.getOrCreateTag().getInt("glow") > 0)
                return 230;
        }

        if (item.getItem().equals(EgoWeaponsItems.HEISHOU_MAO_SWORD.get())) {
            if (item.getOrCreateTag().getInt("active") > 0)
                return 230;
        }
        return packedLightIn;
    }
}
