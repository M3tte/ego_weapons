package net.m3tte.ego_weapons.client.models.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class MangModel<T extends LivingEntity> extends BipedModel<T> implements TaggedModel {

    public final ModelRenderer Ring_R1;

    public MangModel() {
        super(0);
        texWidth = 48;
        texHeight = 16;

        Ring_R1 = new ModelRenderer(this);
        Ring_R1.setPos(-5.0F, 2.0F, 0.0F);
        setRotationAngle(Ring_R1, -0.1745F, 0.0F, 0.0F);
        Ring_R1.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        Ring_R1.texOffs(0, 0).addBox(-6.0F, 6.0F, -5.0F, 10.0F, 1.0F, 10.0F, -0.5F, false);


        this.rightArm = Ring_R1;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Ring_R1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public String getModelIdentity() {
        return "mang_ring";
    }

    @Override
    public String getIdentifier() {
        return "mang_rings";
    }
}