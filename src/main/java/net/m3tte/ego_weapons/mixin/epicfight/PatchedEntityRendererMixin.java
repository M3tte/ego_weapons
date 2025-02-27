package net.m3tte.ego_weapons.mixin.epicfight;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.client.renderer.EntityShake;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = PatchedEntityRenderer.class, remap = false)
public class PatchedEntityRendererMixin <E extends LivingEntity, T extends LivingEntityPatch<E>, R extends EntityRenderer<E>> {


    @Inject(at = @At(value = "HEAD"), method = "mulPoseStack(Lcom/mojang/blaze3d/matrix/MatrixStack;Lyesman/epicfight/api/model/Armature;Lnet/minecraft/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;F)V")
    public void modifyPosestackMixin(MatrixStack poseStack, Armature armature, E entityIn, T entitypatch, float partialTicks, CallbackInfo ci) {

        if (entityIn.getPersistentData().contains("shakeEffect")) {
            float value = entityIn.getPersistentData().getFloat("shakeEffect");
            value -= 0.035f;

            if (value > 0) {
                entityIn.getPersistentData().putFloat("shakeEffect", value);
            } else {
                entityIn.getPersistentData().remove("shakeEffect");
            }


            poseStack.translate(0, EntityShake.evaluateShakeFromValue(value), 0);
        }

    }
}
