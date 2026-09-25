package net.m3tte.ego_weapons.client.renderer.wearable;

import net.m3tte.ego_weapons.client.models.wearable.LampEGOEyesModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class LampEgoEyesRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, LampEGOEyesModel<R>> {
    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_normal.png");
    private final LampEGOEyesModel<R> eyeGlowModel = new LampEGOEyesModel();
    ResourceLocation location = new ResourceLocation("ego_weapons","lamp_ego_eyes_loc");

    public LampEgoEyesRenderer() {
    }



    public LampEGOEyesModel<R> getWearableModel(R living) {
        return this.eyeGlowModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        return TEX_1;
    }

    @Override
    public ResourceLocation getRendererLocation() {
        return location;
    }
}
