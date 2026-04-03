package net.m3tte.ego_weapons.client.renderLayers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes;
import net.m3tte.ego_weapons.client.renderer.modelBakery.OpenModelBakery;
import net.m3tte.ego_weapons.client.renderer.wearable.BloodOverlayRenderer;
import net.m3tte.ego_weapons.client.renderer.wearable.WearableRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashMap;
import java.util.Map;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderTypes.getFullbrightAnimatedArmor;

@OnlyIn(Dist.CLIENT)
public class PatchedAccessoryRenderLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends BipedModel<E>> extends PatchedLayer<E, T, M, AccessoryRenderLayer<E, M>> {
    private static final Map<String, ClientModel> ARMOR_MODELS = new HashMap<String, ClientModel>();
    private static final Map<String, ResourceLocation> EPICFIGHT_OVERRIDING_TEXTURES = Maps.newHashMap();

    public static void clear() {
        ARMOR_MODELS.clear();
        EPICFIGHT_OVERRIDING_TEXTURES.clear();
    }
    final boolean doNotRenderHelment;

    public PatchedAccessoryRenderLayer() {
        this(false);
    }



    ResourceLocation defaultBloodLocation = new ResourceLocation("ego_weapons","blood_stage_1");

    ResourceLocation ardorFireLoc = new ResourceLocation("ego_weapons","ardor_blossom_fire_loc");
    ResourceLocation ardorWingLoc = new ResourceLocation("ego_weapons","ardor_blossom_wing_loc");

    @Override
    public void renderLayer(T t, E entity, AccessoryRenderLayer<E, M> emAccessoryRenderLayer, MatrixStack poseStack, IRenderTypeBuffer buf, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks) {
        if (entity instanceof PlayerEntity) {
            boolean hasEffect = true;



            if (entity instanceof LivingEntity) {
                if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().getRegistryName() != null) {
                    String equippedItemChest = entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().getRegistryName().getPath();

                    switch (equippedItemChest) {
                        case "ardor_blossom_suit":
                            WearableRenderer<?,?,?> fireRenderer = emAccessoryRenderLayer.getArdorFireModel();
                            WearableRenderer<?,?,?> wingRenderer = emAccessoryRenderLayer.getArdorWingModel();

                            int burnPotency = EgoWeaponsEffects.BURN.get().getPotency(entity);

                            ResourceLocation fireTexture = this.getWearableTexture(entity, fireRenderer);
                            ClientModel fireModel = this.getWearableModel(fireRenderer, entity, ardorFireLoc, fireTexture, true, partialTicks);
                            IVertexBuilder fireVertex = getArmorVertexBuilder(buf, getFullbrightAnimatedArmor(fireTexture));

                            this.renderWearable(poseStack, buf, packedLightIn, hasEffect, fireModel, 1.0F, 1.0F, 1.0F, fireTexture, poses, fireVertex);
                            if (burnPotency >= 10) {
                                this.renderWearable(poseStack, buf, packedLightIn, hasEffect, fireModel, 1.0F, 1.0F, 1.0F, fireTexture, poses, buf.getBuffer(EgoWeaponsRenderTypes.getFireGlintDirect()));
                            }

                            if (entity.hasEffect(EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get())) {
                                ResourceLocation wingTexture = this.getWearableTexture(entity, wingRenderer);
                                ClientModel wingModel = this.getWearableModel(wingRenderer, entity, ardorWingLoc, wingTexture, true, partialTicks);
                                IVertexBuilder wingVertex = getArmorVertexBuilder(buf, getFullbrightAnimatedArmor(wingTexture));




                                this.renderWearable(poseStack, buf, packedLightIn, hasEffect, wingModel, 1.0F, 1.0F, 1.0F, wingTexture, poses, wingVertex);
                                if (burnPotency >= 10) {
                                    this.renderWearable(poseStack, buf, packedLightIn, hasEffect, wingModel, 1.0F, 1.0F, 1.0F, wingTexture, poses, buf.getBuffer(EgoWeaponsRenderTypes.getFireGlintDirect()));
                                }
                            }



                            break;

                    }

                }


            }




        }
    }




    public PatchedAccessoryRenderLayer(boolean doNotRenderHelment) {
        this.doNotRenderHelment = doNotRenderHelment;
    }

    public static IVertexBuilder getArmorVertexBuilder(IRenderTypeBuffer buffer, RenderType renderType) {

        return buffer.getBuffer(renderType);
        //return (bloodIndex > -1) ? VertexBuilderUtils.create(buffer.getBuffer(EgoWeaponsRenderTypes.bloodOverlay(bloodIndex)), buffer.getBuffer(renderType)) : buffer.getBuffer(renderType);
    }
    private void renderWearable(MatrixStack matStack, IRenderTypeBuffer multiBufferSource, int packedLightIn, boolean hasEffect, ClientModel model, float r, float g, float b, ResourceLocation armorTexture, OpenMatrix4f[] poses, IVertexBuilder vertexBuilderIn) {

        model.drawAnimatedModel(matStack, vertexBuilderIn, packedLightIn, r, g, b, 1.0F, OverlayTexture.NO_OVERLAY, poses);
        //model.drawRawModel(matStack, vertexConsumer, packedLightIn, r, g, b, 1.0F, OverlayTexture.NO_OVERLAY);
    }


    /*protected boolean isLivingMoving(LivingEntity living) {
        if (!(living instanceof PlayerEntity)) {
            return living.getDeltaMovement().lengthSqr() > 0.009999999776482582;
        } else {
            PlayerEntity player = (PlayerEntity)living;
            double xCloakOffset = MathHelper.lerp(1.0, player.xCloakO, player.xCloak) - MathHelper.lerp(1.0, player.xo, player.getX());
            double yCloakOffset = Math.max(MathHelper.lerp(1.0, player.yCloakO, player.yCloak) - MathHelper.lerp(1.0, player.yo, player.getY()), 0.0);
            double zCloakOffset = MathHelper.lerp(1.0, player.zCloakO, player.zCloak) - MathHelper.lerp(1.0, player.zo, player.getZ());
            float yBodyRot = AdvancedMathHelper.toRadians(player.yBodyRot);
            double sin = (double)MathHelper.sin(yBodyRot);
            double cos = (double)(-MathHelper.cos(yBodyRot));
            return xCloakOffset * sin + zCloakOffset * cos > 0.0 && xCloakOffset * xCloakOffset + yCloakOffset * yCloakOffset + zCloakOffset * zCloakOffset > 0.0010000000474974513;
        }
    }*/

    private BipedModel<?> getDefaultModel(WearableRenderer renderer, LivingEntity entity) {
        return renderer.getWearableModel(entity);
    }

    private ClientModel getWearableModel(WearableRenderer<? extends LivingEntity, ? extends BipedModel<? extends LivingEntity>, ? extends BipedModel<? extends LivingEntity>> wearableRenderer, LivingEntity entityliving, ResourceLocation registryName, ResourceLocation rl, boolean forceRender, float partialTicks) {
        boolean debuggingMode = ClientEngine.instance.isArmorModelDebuggingMode();

        // Create a searching key, allows saving armor models for things like odmg
        String searchingKey = registryName.toString();

        // If its a cape, check if the player is moving so it can apply the default model otherwise
        /*if (wearableItem instanceof CapeItem) {
            searchingKey = isLivingMoving(entityliving) + searchingKey;
        }*/


        if (ARMOR_MODELS.containsKey(searchingKey) && !debuggingMode) {
            return ARMOR_MODELS.get(searchingKey);
        } else {
            // HEAVILY Edited
            BipedModel<?> defaultModel = getDefaultModel(wearableRenderer, entityliving);

            // Needs a custom-custom model bakery
            ClientModel model = OpenModelBakery.bakeBipedCustomWearable((BipedModel<?>)defaultModel, registryName, debuggingMode, rl, entityliving, partialTicks);

            // These types of items should always be rendered


            if (!forceRender) {
                // None for now, just save them as is.
                ARMOR_MODELS.put(searchingKey, model);

            }


            return model;
        }
    }

    private ResourceLocation getWearableTexture(E e, WearableRenderer wearableRenderer) {
        // Simplified, I will pray to god this one works.
        return wearableRenderer.getWearableTexture(e);
    }


}
