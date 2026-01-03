package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.client.renderer.CustomRenderTypes;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
@Mixin(value = PatchedLivingEntityRenderer.class, remap = false)
public abstract class LivingRendererMixin<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedEntityRenderer<E, T, LivingRenderer<E, M>> {

    // Placeholder Module
    @Inject(at = @At(value = "HEAD"), method = "getRenderType", cancellable = true)
    private void overrideReturn(E entityIn, T entitypatch, LivingRenderer<E, M> renderer, boolean isVisible, boolean isVisibleToPlayer, boolean isGlowing, CallbackInfoReturnable<RenderType> cir) {
        ResourceLocation resourcelocation = (this).getEntityTexture(entitypatch, renderer);
        if (entityIn.getY() > 20)
            cir.setReturnValue(CustomRenderTypes.outline(resourcelocation));
    }
}
