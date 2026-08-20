package net.m3tte.ego_weapons.client.renderer.wearable;

import net.m3tte.ego_weapons.client.models.wearable.LCAUdjatMaskModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class LCAUdjatMaskRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, LCAUdjatMaskModel<R>> {
    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/lca_udjat_armor.png");
    private final LCAUdjatMaskModel<R> maskModel = new LCAUdjatMaskModel();
    ResourceLocation location = new ResourceLocation("ego_weapons","udjat_mask_loc");

    public LCAUdjatMaskRenderer() {
    }



    public LCAUdjatMaskModel<R> getWearableModel(R living) {
        return this.maskModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        return TEX_1;
    }

    @Override
    public ResourceLocation getRendererLocation() {
        return location;
    }
}
