package net.m3tte.ego_weapons.client.models.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ArdorBlossomWingsModel<T extends LivingEntity> extends BipedModel<T> implements TaggedModel {


    private final ModelRenderer Body;
    public ModelRenderer BodyLayer_r1;
    public ModelRenderer BodyLayer_r2;

    public ArdorBlossomWingsModel() {
        super(0f);
        texWidth = 64;
        texHeight = 32;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);


        BodyLayer_r1 = new ModelRenderer(this);
        BodyLayer_r1.setPos(0.75F, 3.0F, 2.0F);
        Body.addChild(BodyLayer_r1);
        setRotationAngle(BodyLayer_r1, 0.0F, 0.7854F, 0.0F);
        BodyLayer_r1.texOffs(13, -18).addBox(0.116F, -8.0F, 0.201F, 0.0F, 24.0F, 18.0F, 0.0F, false);

        BodyLayer_r2 = new ModelRenderer(this);
        BodyLayer_r2.setPos(0.25F, 3.0F, 2.0F);
        Body.addChild(BodyLayer_r2);
        setRotationAngle(BodyLayer_r2, 0.0F, -0.7854F, 0.0F);
        BodyLayer_r2.texOffs(13, -18).addBox(0.116F, -8.0F, 0.201F, 0.0F, 24.0F, 18.0F, 0.0F, false);


        this.body = Body;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public String getModelIdentity() {
        return "ardor_blossom_wings";
    }

    @Override
    public String getIdentifier() {
        return "ardor_blossom_wings";
    }
}
