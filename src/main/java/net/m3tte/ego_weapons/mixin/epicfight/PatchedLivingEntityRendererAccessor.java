package net.m3tte.ego_weapons.mixin.epicfight;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(PatchedLivingEntityRenderer.class)
public interface PatchedLivingEntityRendererAccessor<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> {
    @Invoker("getRootJointIndex")
    int invokeGetRootJointIndex();

    @Invoker("getLayerCorrection")
    double invokeGetLayerCorrection();
    @Invoker("mulPoseStack")
    void invokeMulPoseStack(MatrixStack poseStack, Armature armature, E entityIn, T entitypatch, float partialTicks);


}
