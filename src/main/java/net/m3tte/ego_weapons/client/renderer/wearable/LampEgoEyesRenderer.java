package net.m3tte.ego_weapons.client.renderer.wearable;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.client.models.wearable.LampEGOEyesModel;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem;
import net.m3tte.ego_weapons.client.renderer.RenderOverrideStates;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class LampEgoEyesRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, LampEGOEyesModel<R>> {
    private static final ResourceLocation TEX_N = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_normal.png");
    private static final ResourceLocation TEX_R = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_red.png");
    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_f_1.png");
    private static final ResourceLocation TEX_2 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_f_2.png");
    private static final ResourceLocation TEX_3 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_f_3.png");
    private static final ResourceLocation TEX_4 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_f_4.png");
    private static final ResourceLocation TEX_5 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_f_5.png");
    private static final ResourceLocation TEX_6 = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_f_6.png");
    private static final ResourceLocation TEX_R_G = new ResourceLocation("ego_weapons", "textures/entities/lamp/suit_eyes_red_g.png");
    private final LampEGOEyesModel<R> eyeGlowModel = new LampEGOEyesModel();
    ResourceLocation location = new ResourceLocation("ego_weapons","lamp_ego_eyes_loc");

    public LampEgoEyesRenderer() {
    }



    public LampEGOEyesModel<R> getWearableModel(R living) {
        return this.eyeGlowModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        RenderOverrideStates state = EgoWeaponsRenderSystem.getRenderColorOverrideState();

        if (living.hasEffect(EgoWeaponsEffects.SALVATION.get()))
            return state.equals(RenderOverrideStates.BLOOM) ? TEX_R_G : TEX_R;



        if (state.equals(RenderOverrideStates.BLOOM)) {
            int stack = EgoWeaponsEffects.LAMP.get().getPotency(living);

            if (stack >= 20)
                return TEX_N;

            switch (stack / 3) {
                case 1: return TEX_1;
                case 2: return TEX_2;
                case 3: return TEX_3;
                case 4: return TEX_4;
                case 5: return TEX_5;
                case 6: return TEX_6;
            }
        }



        return TEX_N;
    }

    @Override
    public ResourceLocation getRendererLocation() {
        return location;
    }
}
