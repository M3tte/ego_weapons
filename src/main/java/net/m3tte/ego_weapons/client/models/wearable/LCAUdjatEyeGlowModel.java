package net.m3tte.ego_weapons.client.models.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.gameasset.movesets.DurandalMovesetAnims;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class LCAUdjatEyeGlowModel<T extends LivingEntity> extends BipedModel<T> implements TaggedModel {
    private final ModelRenderer Head;
    private final ModelRenderer Dummy;
    public LCAUdjatEyeGlowModel() {
        super(0f);
        texWidth = 128;
        texHeight = 64;

        Dummy = new ModelRenderer(this);
        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
        Head.texOffs(57, 43).addBox(-5.0F, -8.75F, -6.8F, 10.0F, 10.0F, 4.0F, -1.8F, false);

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
        return "lca_udjat_eye_glow";
    }
}
