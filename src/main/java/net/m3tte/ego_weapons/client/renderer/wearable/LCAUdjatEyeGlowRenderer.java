package net.m3tte.ego_weapons.client.renderer.wearable;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.models.wearable.ArdorBlossomFireModel;
import net.m3tte.ego_weapons.client.models.wearable.LCAUdjatEyeGlowModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class LCAUdjatEyeGlowRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, LCAUdjatEyeGlowModel<R>> {
    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/lca_udjat_armor.png");
    private final LCAUdjatEyeGlowModel<R> eyeGlowModel = new LCAUdjatEyeGlowModel();
    ResourceLocation location = new ResourceLocation("ego_weapons","udjat_eye_glow_loc");

    public LCAUdjatEyeGlowRenderer() {
    }



    public LCAUdjatEyeGlowModel<R> getWearableModel(R living) {
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
