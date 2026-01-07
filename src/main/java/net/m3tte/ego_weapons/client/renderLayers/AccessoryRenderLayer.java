package net.m3tte.ego_weapons.client.renderLayers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.client.models.wearable.BloodOverlayModel;
import net.m3tte.ego_weapons.client.models.wearable.SlimCompatibleModel;
import net.m3tte.ego_weapons.client.renderer.wearable.BloodOverlayRenderer;
import net.m3tte.ego_weapons.client.renderer.wearable.WearableRenderer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@OnlyIn(Dist.CLIENT)
public class AccessoryRenderLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    private WearableRenderer bloodOverlayModel = null;


    public WearableRenderer getBloodOverlayModel() {
        return bloodOverlayModel;
    }

    public AccessoryRenderLayer(IEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
        BipedModel<T> baseWearableModel = new BipedModel(0.4F);
        this.bloodOverlayModel = new BloodOverlayRenderer<>();



    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, T entity, float animationPosition, float animationSpeed, float partialTick, float viewBob, float yaw, float pitch) {
        EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

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
    }


}

