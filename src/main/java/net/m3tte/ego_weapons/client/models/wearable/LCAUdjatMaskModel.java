package net.m3tte.ego_weapons.client.models.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class LCAUdjatMaskModel<T extends LivingEntity> extends BipedModel<T> implements TaggedModel {
    private final ModelRenderer Head;
    private final ModelRenderer Dummy;
    public LCAUdjatMaskModel() {
        super(0f);
        texWidth = 128;
        texHeight = 64;

        Dummy = new ModelRenderer(this);
        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
        Head.texOffs(0, 0).addBox(-4.0F, -8.575F, -4.5F, 8.0F, 9.0F, 1.0F, 0.0F, false);
        Head.texOffs(1, 10).addBox(-3.5F, -7.25F, -4.75F, 7.0F, 7.0F, 1.0F, 0.1F, false);

        this.hat = Head;
        this.head = Dummy;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public String getModelIdentity() {
        return "basic_hat";
    }

    @Override
    public String getIdentifier() {
        return "lca_udjat_mask";
    }
}
