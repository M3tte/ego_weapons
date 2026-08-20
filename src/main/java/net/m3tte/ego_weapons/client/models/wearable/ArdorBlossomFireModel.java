package net.m3tte.ego_weapons.client.models.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ArdorBlossomFireModel<T extends LivingEntity> extends BipedModel<T> implements TaggedModel {


    private final ModelRenderer Body;
    private final ModelRenderer sideflameright_r1;
    private final ModelRenderer backflame_r1;
    private final ModelRenderer sideflameleft_r1;
    private final ModelRenderer LeftArm;
    private final ModelRenderer RightArm;
    public ArdorBlossomFireModel() {
        super(0f);
        texWidth = 64;
        texHeight = 32;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(6, 14).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, 0.5F, false);

        sideflameright_r1 = new ModelRenderer(this);
        sideflameright_r1.setPos(-11.55F, 16.0F, 0.5F);
        Body.addChild(sideflameright_r1);
        setRotationAngle(sideflameright_r1, 0.0F, 0.0F, 0.3927F);
        sideflameright_r1.texOffs(0, -9).addBox(5.984F, -5.1559F, -3.0F, 0.0F, 9.0F, 9.0F, 0.0F, false);

        backflame_r1 = new ModelRenderer(this);
        backflame_r1.setPos(0.2F, 20.25F, -1.25F);
        Body.addChild(backflame_r1);
        setRotationAngle(backflame_r1, 0.4349F, -0.0061F, 0.0058F);
        backflame_r1.texOffs(18, 1).addBox(-6.016F, -5.1559F, 6.0F, 12.0F, 10.0F, 0.0F, 0.0F, false);

        sideflameleft_r1 = new ModelRenderer(this);
        sideflameleft_r1.setPos(0.2F, 20.25F, 0.5F);
        Body.addChild(sideflameleft_r1);
        setRotationAngle(sideflameleft_r1, 0.0F, 0.0F, -0.3054F);
        sideflameleft_r1.texOffs(0, -9).addBox(5.984F, -5.1559F, -3.0F, 0.0F, 9.0F, 9.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
        LeftArm.texOffs(42, 0).addBox(-1.0F, 2.6F, -2.0F, 4.0F, 5.0F, 4.0F, 0.3F, true);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
        RightArm.texOffs(42, 0).addBox(-3.0F, 2.65F, -2.0F, 4.0F, 5.0F, 4.0F, 0.3F, false);

        this.body = Body;
        this.rightArm = RightArm;
        this.leftArm = LeftArm;
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public String getModelIdentity() {
        return "basic_torso";
    }

    @Override
    public String getIdentifier() {
        return "ardor_blossom_fire";
    }
}
