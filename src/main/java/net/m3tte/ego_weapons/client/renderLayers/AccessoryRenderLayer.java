package net.m3tte.ego_weapons.client.renderLayers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.client.models.wearable.SlimCompatibleModel;
import net.m3tte.ego_weapons.client.renderer.wearable.*;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import java.util.HashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
@OnlyIn(Dist.CLIENT)
public class AccessoryRenderLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    private final HashMap<String, WearableRenderer> rendererRegistry = new HashMap<>();

    private final WearableRenderer defaultRendererFallback;

    public HashMap<String, WearableRenderer> getRendererRegistry() { return rendererRegistry; }
    public WearableRenderer getRenderer(String identifier) { return rendererRegistry.getOrDefault(identifier, defaultRendererFallback); }

    public void registerModel(String identifier, WearableRenderer renderer) {
        rendererRegistry.put(identifier, renderer);
    }

    public AccessoryRenderLayer(IEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
        BipedModel<T> baseWearableModel = new BipedModel<>(0.4F);

        defaultRendererFallback = new BloodOverlayRenderer();
        registerModel("ardor_blossom_fire", new ArdorBlossomFireRenderer());
        registerModel("ardor_blossom_wings", new ArdorBlossomWingRenderer());
        registerModel("udjat_eye_glow", new LCAUdjatEyeGlowRenderer());
        registerModel("lamp_ego_eyes", new LampEgoEyesRenderer());
        registerModel("udjat_mask", new LCAUdjatMaskRenderer());
    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float viewBob, float yaw, float pitch) {

        if (entity instanceof LivingEntity) {
            if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().getRegistryName() != null) {
                String equippedItemChest = entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().getRegistryName().getPath();

                switch (equippedItemChest) {
                    case "ardor_blossom_suit":
                        getRenderer("ardor_blossom_fire").render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);

                        if (entity.hasEffect(EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get()))
                            getRenderer("ardor_blossom_wings").render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);
                        break;
                    case "lamp_suit":
                        getRenderer("lamp_ego_eyes").render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);

                        break;
                }
            }

            if (entity.getItemBySlot(EquipmentSlotType.HEAD).getItem().getRegistryName() != null) {
                String equippedItemHead = entity.getItemBySlot(EquipmentSlotType.HEAD).getItem().getRegistryName().getPath();

                switch (equippedItemHead) {
                    //case "lca_udjat_mask":
                    //    getRenderer("udjat_eye_glow").render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);
                    //    break;
                }

                if (entity.hasEffect(EgoWeaponsEffects.UDJAT_VANGUARD.get())) {
                    if (!equippedItemHead.equals("lca_udjat_mask")) {
                        getRenderer("udjat_mask").render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);
                    }
                    getRenderer("udjat_eye_glow").render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);
                }


            }
        }

        /*if (entity instanceof PlayerEntity && entityData != null) {
            if (entityData.injury_threshold > 0.7f) {
                bloodOverlayModel.render(entity, null, this.getParentModel(), matrixStack, buffer, combinedLight, animationPosition, animationSpeed, partialTick);

            }
        }*/
    }


    @OnlyIn(Dist.CLIENT)
    private static boolean isEntityPlayerWithAlexModel(Entity entity) {
        return entity instanceof AbstractClientPlayerEntity && ((AbstractClientPlayerEntity)entity).getModelName().equals("slim");
    }

    public static class SlimCompatibleModelRenderer<R extends LivingEntity, M extends BipedModel<R>> extends WearableRenderer<R, M, BipedModel<R>> {
        protected final SlimCompatibleModel<R> model;
        protected final ResourceLocation standardTexture;
        protected final ResourceLocation slimVariantTexture;

        public SlimCompatibleModelRenderer(float scale, ResourceLocation steveTexture, @Nullable ResourceLocation alexTexture) {
            this.model = new SlimCompatibleModel<>(scale);
            this.standardTexture = steveTexture;
            this.slimVariantTexture = alexTexture;
        }

        public SlimCompatibleModelRenderer(float scale, ResourceLocation texture) {
            this(scale, texture, (ResourceLocation)null);
        }

        public BipedModel<R> getWearableModel(R living) {
            return this.model;
        }

        public ResourceLocation getWearableTexture(R living) {
            return this.slimVariantTexture != null && isEntityPlayerWithAlexModel(living) ? this.slimVariantTexture : this.standardTexture;
        }

        @Override
        public ResourceLocation getRendererLocation() {
            return new ResourceLocation("ego_weapons", model.toString());
        }

        protected void preRender(R living, ItemStack wearable, M parentModel, SlimCompatibleModel<R> model, ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, float animationPosition, float animationSpeed, float partialTick) {
            if (isEntityPlayerWithAlexModel(living)) {
                this.model.applySlimVersion();
            } else {
                this.model.applyStandardVersion();
            }

        }
    }

    public static class simpleAccessoryRenderer<R extends LivingEntity, M extends BipedModel<R>, W extends BipedModel<R>> extends WearableRenderer<R, M, W> {
        protected final W model;
        protected final ResourceLocation texture;

        public simpleAccessoryRenderer(W model, ResourceLocation texture) {
            this.model = model;
            this.texture = texture;
        }

        public W getWearableModel(R living) {
            return this.model;
        }

        public ResourceLocation getWearableTexture(R living) {
            return this.texture;
        }

        @Override
        public ResourceLocation getRendererLocation() {
            return new ResourceLocation("ego_weapons", model.toString());
        }
    }


}

