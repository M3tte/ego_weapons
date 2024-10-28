package net.m3tte.tcorp.client.renderer.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.tcorp.entities.MagicBulletProjectile.MagicBulletProj;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagicBulletRenderer {

	@OnlyIn(Dist.CLIENT)
	public static class CustomRender extends EntityRenderer<MagicBulletProj> {
		private static final ResourceLocation texture = new ResourceLocation("tcorp:textures/entities/magic_bullet_entity.png");

		public CustomRender(EntityRendererManager renderManager) {
			super(renderManager);
		}

		@Override
		public ResourceLocation getTextureLocation(MagicBulletProj p_110775_1_) {
			return texture;
		}

		@Override
		public void render(MagicBulletProj entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
						   IRenderTypeBuffer bufferIn, int packedLightIn) {
			IVertexBuilder vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
			matrixStackIn.pushPose();
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRotO)));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90 + MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot)));
			EntityModel model = new MagicBulletModelRenderer();
			model.renderToBuffer(matrixStackIn, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
			matrixStackIn.popPose();
			super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		}

	}

	// Made with Blockbench 4.6.5
	// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
	// Paste this class into your mod and generate all required imports
	private static class MagicBulletModelRenderer extends EntityModel<Entity> {
		private final ModelRenderer bb_main;

		public MagicBulletModelRenderer() {
			texWidth = 16;
			texHeight = 16;

			bb_main = new ModelRenderer(this);
			bb_main.setPos(0.0F, 24.0F, 0.0F);
			bb_main.texOffs(0, 0).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 6.0F, -0.25F, false);
			bb_main.texOffs(0, 0).addBox(-0.5F, -1.5F, -5.725F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
			//previously the render function, render code was moved to a method below
		}

		@Override
		public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
			bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.xRot = x;
			modelRenderer.yRot = y;
			modelRenderer.zRot = z;
		}
	}

}
