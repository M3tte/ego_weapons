package net.m3tte.ego_weapons.mixin;

import net.m3tte.ego_weapons.client.renderLayers.FullstopSuitcaseRenderer;
import net.m3tte.ego_weapons.client.renderLayers.JustitiaRopeRenderer;
import net.m3tte.ego_weapons.client.renderLayers.SunshowerFoxRenderLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingRenderer.class)
public class LivingRenderLayersMixin {



    @Inject(at = @At(value = "TAIL"), method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererManager;Lnet/minecraft/client/renderer/entity/model/EntityModel;F)V")
    private void addCustomRenderLayers(EntityRendererManager renderMan, EntityModel<LivingEntity> model, float p_i50965_3_, CallbackInfo ci) {



        renderMan.renderers.values().forEach((val) -> {

            if (val instanceof LivingRenderer<?,?>) {
                ((LivingRenderer<?,?>)val).addLayer(new JustitiaRopeRenderer<>((IEntityRenderer)val));
            }

        });



    }



}
