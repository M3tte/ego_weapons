package net.m3tte.tcorp.client.renderer.entities.mobs;

import net.m3tte.tcorp.entities.NothingThere2Entity;
import net.m3tte.tcorp.client.models.entities.NothingTherePhase2Model;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NothingThereRenderer {

    @OnlyIn(Dist.CLIENT)
    public static class NothingThereRender extends MobRenderer<NothingThere2Entity, NothingTherePhase2Model> {
        private static final ResourceLocation texture = new ResourceLocation("tcorp:textures/entities/abnos/nothing_there.png");

        public NothingThereRender(EntityRendererManager renderManager) {
            super(renderManager, new NothingTherePhase2Model(), 0.5f);
        }

        @Override
        public ResourceLocation getTextureLocation(NothingThere2Entity p_110775_1_) {
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
