package net.m3tte.ego_weapons.client.renderer.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.models.wearable.ArdorBlossomFireModel;
import net.m3tte.ego_weapons.client.models.wearable.BloodOverlayModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class ArdorBlossomFireRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, ArdorBlossomFireModel<R>> {

    ResourceLocation location = new ResourceLocation("ego_weapons","ardor_blossom_fire_loc");

    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_blossom_fire_1.png");
    private static final ResourceLocation TEX_2 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_blossom_fire_2.png");
    private static final ResourceLocation TEX_3 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_blossom_fire_3.png");
    private final ArdorBlossomFireModel<R> ardorFireModel = new ArdorBlossomFireModel();

    public ArdorBlossomFireRenderer() {
    }



    public ArdorBlossomFireModel<R> getWearableModel(R living) {
        return this.ardorFireModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        EgoWeaponsModVars.PlayerVariables entityData = living.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

        if (living instanceof LivingEntity) {

            int fact = 7;

            switch ((living.tickCount % (fact * 3)) / fact) {
                case 0:
                    return TEX_1;
                case 1:
                    return TEX_2;
                default:
                case 2:
                    return TEX_3;

            }
        }


        return TEX_1;
    }

    @Override
    public ResourceLocation getRendererLocation() {
        return location;
    }
}
