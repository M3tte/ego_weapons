package net.m3tte.ego_weapons.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.entities.CravingBloodbagEntity;
import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CravingBloodbagModel extends EntityModel<CravingBloodbagEntity> {
    private final ModelRenderer Head;
    private final ModelRenderer Head_r1;
    private final ModelRenderer Head_r2;
    private final ModelRenderer Head_r3;
    private final ModelRenderer Body;
    private final ModelRenderer RightArm;
    private final ModelRenderer Claw_r1;
    private final ModelRenderer Claw_r2;
    private final ModelRenderer Claw_r3;
    private final ModelRenderer Claw_r4;
    private final ModelRenderer LeftArm;
    private final ModelRenderer Claw_r5;
    private final ModelRenderer Claw_r6;
    private final ModelRenderer Claw_r7;
    private final ModelRenderer Claw_r8;
    private final ModelRenderer LeftArm_r1;
    private final ModelRenderer RightLeg;
    private final ModelRenderer LeftLeg;

    public CravingBloodbagModel() {
        texWidth = 100;
        texHeight = 100;

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        setRotationAngle(Head, -0.1047F, 0.0873F, 0.0F);
        Head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        Head.texOffs(39, 7).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 1.0F, 0.5F, false);
        Head.texOffs(16, 32).addBox(-4.5F, -8.5F, -6.5F, 9.0F, 1.0F, 11.0F, 0.1F, false);
        Head.texOffs(19, 35).addBox(-4.0F, -10.75F, -4.0F, 8.0F, 2.0F, 8.0F, 0.3F, false);

        Head_r1 = new ModelRenderer(this);
        Head_r1.setPos(6.25F, -4.75F, -2.5F);
        Head.addChild(Head_r1);
        setRotationAngle(Head_r1, -1.1937F, 1.3961F, -1.4483F);
        Head_r1.texOffs(66, 0).addBox(1.0F, -2.0F, -4.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);

        Head_r2 = new ModelRenderer(this);
        Head_r2.setPos(2.5F, -4.0F, 4.0F);
        Head.addChild(Head_r2);
        setRotationAngle(Head_r2, -0.1561F, 0.0493F, 0.1743F);
        Head_r2.texOffs(66, 3).addBox(1.0F, -2.0F, -4.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);

        Head_r3 = new ModelRenderer(this);
        Head_r3.setPos(5.0F, -5.0F, 0.5F);
        Head.addChild(Head_r3);
        setRotationAngle(Head_r3, -0.1893F, 0.538F, -0.3578F);
        Head_r3.texOffs(66, 0).addBox(1.0F, -2.0F, -4.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        Body.texOffs(36, 46).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 14.0F, 5.0F, -0.25F, false);
        Body.texOffs(0, 64).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 16.0F, 5.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);
        RightArm.texOffs(28, 69).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, true);

        Claw_r1 = new ModelRenderer(this);
        Claw_r1.setPos(-4.0F, 11.5F, 2.0F);
        RightArm.addChild(Claw_r1);
        setRotationAngle(Claw_r1, -3.0802F, -0.1636F, 2.8359F);
        Claw_r1.texOffs(58, 6).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        Claw_r2 = new ModelRenderer(this);
        Claw_r2.setPos(-4.5F, 11.0F, 0.0F);
        RightArm.addChild(Claw_r2);
        setRotationAngle(Claw_r2, -3.1022F, 0.0359F, -3.0106F);
        Claw_r2.texOffs(58, 0).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        Claw_r3 = new ModelRenderer(this);
        Claw_r3.setPos(-4.5F, 11.0F, -1.75F);
        RightArm.addChild(Claw_r3);
        setRotationAngle(Claw_r3, -3.1021F, -0.0513F, -3.0141F);
        Claw_r3.texOffs(58, 0).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        Claw_r4 = new ModelRenderer(this);
        Claw_r4.setPos(-1.5F, 10.75F, -4.0F);
        RightArm.addChild(Claw_r4);
        setRotationAngle(Claw_r4, -2.5662F, 1.3155F, -2.5098F);
        Claw_r4.texOffs(58, 12).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);
        LeftArm.texOffs(28, 69).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);

        Claw_r5 = new ModelRenderer(this);
        Claw_r5.setPos(2.0F, 10.75F, -4.0F);
        LeftArm.addChild(Claw_r5);
        setRotationAngle(Claw_r5, -0.3571F, 1.2734F, -0.3984F);
        Claw_r5.texOffs(58, 12).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        Claw_r6 = new ModelRenderer(this);
        Claw_r6.setPos(4.5F, 11.0F, -1.5F);
        LeftArm.addChild(Claw_r6);
        setRotationAngle(Claw_r6, 0.0629F, 0.1801F, 0.1806F);
        Claw_r6.texOffs(58, 0).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        Claw_r7 = new ModelRenderer(this);
        Claw_r7.setPos(4.5F, 11.0F, 0.25F);
        LeftArm.addChild(Claw_r7);
        setRotationAngle(Claw_r7, 0.0622F, 0.093F, 0.1751F);
        Claw_r7.texOffs(58, 0).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        Claw_r8 = new ModelRenderer(this);
        Claw_r8.setPos(4.0F, 11.5F, 2.5F);
        LeftArm.addChild(Claw_r8);
        setRotationAngle(Claw_r8, 0.0623F, -0.2694F, 0.3216F);
        Claw_r8.texOffs(58, 6).addBox(-3.1481F, -1.9151F, -0.1164F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        LeftArm_r1 = new ModelRenderer(this);
        LeftArm_r1.setPos(0.5F, 22.0F, 6.5F);
        LeftArm.addChild(LeftArm_r1);
        setRotationAngle(LeftArm_r1, 0.0F, 1.5708F, 0.0F);
        LeftArm_r1.texOffs(43, 68).addBox(4.0F, -25.0F, -2.0F, 5.0F, 5.0F, 5.0F, -0.1F, false);

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        setRotationAngle(RightLeg, 0.192F, 0.0F, 0.0349F);
        RightLeg.texOffs(16, 57).addBox(-2.45F, 10.4F, -2.75F, 5.0F, 2.0F, 5.0F, -0.1F, false);
        RightLeg.texOffs(0, 32).addBox(-1.95F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        setRotationAngle(LeftLeg, -0.1745F, 0.0F, -0.0349F);
        LeftLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        LeftLeg.texOffs(16, 57).addBox(-2.5F, 10.4F, -2.75F, 5.0F, 2.0F, 5.0F, -0.1F, false);
    }

    @Override
    public void setupAnim(CravingBloodbagEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
