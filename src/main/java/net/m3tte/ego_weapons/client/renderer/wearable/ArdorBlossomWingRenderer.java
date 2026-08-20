package net.m3tte.ego_weapons.client.renderer.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.models.wearable.ArdorBlossomFireModel;
import net.m3tte.ego_weapons.client.models.wearable.ArdorBlossomWingsModel;
import net.m3tte.ego_weapons.item.ardor_blossom.ArdorBlossomSuit;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class ArdorBlossomWingRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, ArdorBlossomWingsModel<R>> {
    private static final ResourceLocation TEX_1 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_1.png");
    private static final ResourceLocation TEX_2 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_2.png");
    private static final ResourceLocation TEX_3 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_3.png");
    private static final ResourceLocation TEX_4 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_4.png");


    private static final ResourceLocation FTEX_1 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_flared_1.png");
    private static final ResourceLocation FTEX_2 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_flared_2.png");
    private static final ResourceLocation FTEX_3 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_flared_3.png");
    private static final ResourceLocation FTEX_4 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_flared_4.png");


    private static final ResourceLocation DISABLE_1 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_disable_1.png");
    private static final ResourceLocation DISABLE_2 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_disable_2.png");
    private static final ResourceLocation UNFLARE_1 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_deflare_1.png");
    private static final ResourceLocation UNFLARE_2 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_deflare_2.png");
    private static final ResourceLocation UNFLARE_3 = new ResourceLocation("ego_weapons", "textures/entities/ardor_blossom/ardor_wings_deflare_3.png");
    private final ArdorBlossomWingsModel<R> ardorFireWingModel = new ArdorBlossomWingsModel();

    ResourceLocation location = new ResourceLocation("ego_weapons","ardor_blossom_wings_loc");

    public ArdorBlossomWingRenderer() {
    }


    @Override
    protected void preRender(R living, ItemStack wearable, M parentModel, ArdorBlossomWingsModel<R> model, ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float animationPosition, float animationSpeed, float partialTick) {
        super.preRender(living, wearable, parentModel, model, texture, matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);

        float time = (living.tickCount + partialTick) / 10;

        model.setRotationAngle(model.BodyLayer_r2, 0.0F, (float) (-0.6 -0.254F * Math.sin(time)), 0.0F);
        model.setRotationAngle(model.BodyLayer_r1, 0.0F, (float) (0.6 + 0.254F * Math.sin(time)), 0.0F);

    }

    public ArdorBlossomWingsModel<R> getWearableModel(R living) {


        return this.ardorFireWingModel;
    }

    public ResourceLocation getWearableTexture(R living) {

        // EgoWeaponsModVars.PlayerVariables entityData = living.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

        if (living instanceof LivingEntity) {


            if (ArdorBlossomSuit.getWingActivationState(living)) {
                int fact = 5;

                switch ((living.tickCount % (fact * 4)) / fact) {
                    case 0:
                        return FTEX_1;
                    case 1:
                        return FTEX_2;
                    default:
                    case 2:
                        return FTEX_3;
                    case 3:
                        return FTEX_4;
                }
            } else {

                if (living.hasEffect(EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get())) {

                    int ticksRemaining = living.getEffect(EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get()).getDuration();

                    if (ticksRemaining < 10)
                        return DISABLE_2;

                    if (ticksRemaining < 20)
                        return DISABLE_1;

                }



                int timeDiff = living.tickCount - ArdorBlossomSuit.getWingMetadata(living).getInt("lastWingDeactivation");
                // System.out.println("TIMEDIF IS "+timeDiff+ " LAST ACT IS : "+living.getPersistentData().getInt("lastWingDeactivation"));
                if (timeDiff < 3)
                    return UNFLARE_1;

                if (timeDiff < 6)
                    return UNFLARE_2;

                if (timeDiff < 9)
                    return UNFLARE_3;


                int fact = 7;

                switch ((living.tickCount % (fact * 4)) / fact) {
                    case 0:
                        return TEX_1;
                    case 1:
                        return TEX_2;
                    default:
                    case 2:
                        return TEX_3;
                    case 3:
                        return TEX_4;

                }
            }

        }


        return TEX_1;
    }

    @Override
    public ResourceLocation getRendererLocation() {
        return location;
    }
}
