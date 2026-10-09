package net.m3tte.ego_weapons.client.renderer.wearable;

import net.m3tte.ego_weapons.client.models.wearable.MangModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class MangRingRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, MangModel<R>> {
    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/mang.png");
    private final MangModel<R> mangModel = new MangModel<>();
    ResourceLocation location = new ResourceLocation("ego_weapons","mang_glow_loc");

    public MangRingRenderer() {
    }



    public MangModel<R> getWearableModel(R living) {
        return this.mangModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        return TEX_1;
    }

    @Override
    public ResourceLocation getRendererLocation() {
        return location;
    }
}
