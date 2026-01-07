package net.m3tte.ego_weapons.mixin.epicfight;

import net.m3tte.ego_weapons.client.renderLayers.AccessoryRenderLayer;
import net.m3tte.ego_weapons.client.renderLayers.PatchedAccessoryRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;

@Mixin(PHumanoidRenderer.class)
public class PHumanoidMixin {


    // Lyesman/epicfight/client/renderer/patched/entity/PHumanoidRenderer;<init>()V
    @Inject(at = @At(value = "TAIL"), method = "<init>()V")
    private void init(CallbackInfo cbk) {
        PHumanoidRenderer<?, ?, ?> p = (PHumanoidRenderer<?, ?, ?>) (Object) this;


        p.addPatchedLayer(AccessoryRenderLayer.class, new PatchedAccessoryRenderLayer<>());
        System.out.println("PATCHED LAYER ADDED");
    }

}
