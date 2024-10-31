package net.m3tte.ego_weapons.client.renderer.entities.mobs;

import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.m3tte.ego_weapons.client.models.entities.DawnOfGreenDoubtAModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DawnOfGreenDoubtRenderer {

    @OnlyIn(Dist.CLIENT)
    public static class GreenDoubtRenderer extends MobRenderer<DawnOfGreenDoubtEntity, DawnOfGreenDoubtAModel> {
        private static final ResourceLocation texture = new ResourceLocation("ego_weapons:textures/entities/abnos/doubt_a.png");

        public GreenDoubtRenderer(EntityRendererManager renderManager) {
            super(renderManager, new DawnOfGreenDoubtAModel(), 0.5f);
        }

        @Override
        public ResourceLocation getTextureLocation(DawnOfGreenDoubtEntity p_110775_1_) {
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
