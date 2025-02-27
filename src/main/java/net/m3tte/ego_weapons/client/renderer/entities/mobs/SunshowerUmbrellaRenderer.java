package net.m3tte.ego_weapons.client.renderer.entities.mobs;

import net.m3tte.ego_weapons.client.models.entities.SunshowerUmbrellaModel;
import net.m3tte.ego_weapons.entities.SunshowerUmbrellaEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunshowerUmbrellaRenderer {

    @OnlyIn(Dist.CLIENT)
    public static class SURenderer extends MobRenderer<SunshowerUmbrellaEntity, SunshowerUmbrellaModel> {
        public static final ResourceLocation texture = new ResourceLocation("ego_weapons:textures/entities/passive/sunshower_umbrella.png");

        public SURenderer(EntityRendererManager renderManager) {
            super(renderManager, new SunshowerUmbrellaModel(), 0.5f);
        }

        @Override
        public ResourceLocation getTextureLocation(SunshowerUmbrellaEntity p_110775_1_) {
            return texture;
        }

       /* @Override
        public void render(DawnOfGreenDoubtEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
                           IRenderTypeBuffer bufferIn, int packedLightIn) {
            IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
            matrixStackIn.pushPose();
            EntityModel model = new DoubtModelA();
            model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
            matrixStackIn.popPose();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }*/

    }

    // Made with Blockbench 4.10.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports



}
