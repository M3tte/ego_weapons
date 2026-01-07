package net.m3tte.ego_weapons.client.renderer.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.models.wearable.BloodOverlayModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class BloodOverlayRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, BloodOverlayModel<R>> {
    private static final ResourceLocation TEX_INJURED_0 = new ResourceLocation("ego_weapons", "textures/accessories/blood_stage_0.png");
    private static final ResourceLocation TEX_INJURED_1 = new ResourceLocation("ego_weapons", "textures/accessories/blood_stage_1.png");
    private static final ResourceLocation TEX_INJURED_2 = new ResourceLocation("ego_weapons", "textures/accessories/blood_stage_2.png");
    private static final ResourceLocation TEX_INJURED_3 = new ResourceLocation("ego_weapons", "textures/accessories/blood_stage_3.png");
    private static final ResourceLocation TEX_INJURED_4 = new ResourceLocation("ego_weapons", "textures/accessories/blood_stage_4.png");
    private final BloodOverlayModel<R> bloodOverlayModel = new BloodOverlayModel();

    public BloodOverlayRenderer() {
    }

    protected void preRender(R living, ItemStack wearable, M parentModel, BloodOverlayModel<R> model, ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float animationPosition, float animationSpeed, float partialTick) {

    }

    public BloodOverlayModel<R> getWearableModel(R living) {
        return this.bloodOverlayModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        EgoWeaponsModVars.PlayerVariables entityData = living.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

        if (living instanceof PlayerEntity && entityData != null) {

            if (entityData.injury_threshold > 2.3f) {
                return TEX_INJURED_4;
            }

            if (entityData.injury_threshold > 1.6f) {
                return TEX_INJURED_3;
            }

            if (entityData.injury_threshold > 1.1f) {
                return TEX_INJURED_2;
            }

            if (entityData.injury_threshold > 0.7f) {
                return TEX_INJURED_1;
            }

        }


        return TEX_INJURED_0;
    }
}
