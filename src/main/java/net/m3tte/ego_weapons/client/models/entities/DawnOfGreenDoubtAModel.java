package net.m3tte.ego_weapons.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.entities.DawnOfGreenDoubtEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DawnOfGreenDoubtAModel extends EntityModel<DawnOfGreenDoubtEntity> {
    private final ModelRenderer Head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer Faceplate;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer Eye_r1;
    private final ModelRenderer Eye_r2;
    private final ModelRenderer cube_r5;
    private final ModelRenderer Eye_r3;
    private final ModelRenderer cube_r6;
    private final ModelRenderer Body;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer cube_r10;
    private final ModelRenderer LowerSpine;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;
    private final ModelRenderer Arm_R;
    private final ModelRenderer cube_r13;
    private final ModelRenderer cube_r14;
    private final ModelRenderer Hand_R;
    private final ModelRenderer cube_r15;
    private final ModelRenderer cube_r16;
    private final ModelRenderer cube_r17;
    private final ModelRenderer cube_r18;
    private final ModelRenderer cube_r19;
    private final ModelRenderer Leg_L;
    private final ModelRenderer Lower_Leg_L;
    private final ModelRenderer cube_r20;
    private final ModelRenderer cube_r21;
    private final ModelRenderer Feet_L;
    private final ModelRenderer Leg_R;
    private final ModelRenderer Lower_Leg_R;
    private final ModelRenderer cube_r22;
    private final ModelRenderer cube_r23;
    private final ModelRenderer Feet_R;

    public DawnOfGreenDoubtAModel() {
        texWidth = 64;
        texHeight = 64;

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, -3.0F, 0.0F);
        setRotationAngle(Head, 0.0F, -1.5708F, 0.0F);
        Head.texOffs(4, 0).addBox(-1.25F, -10.0F, 0.0F, 7.0F, 7.0F, 0.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(2.475F, -2.175F, 0.5F);
        Head.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.7418F);
        cube_r1.texOffs(0, 0).addBox(-1.0F, -7.0F, -1.0F, 1.0F, 8.0F, 1.0F, -0.1F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(2.75F, -2.0F, 0.5F);
        Head.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -0.4363F);
        cube_r2.texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 1.0F, 8.0F, 1.0F, 0.1F, false);

        Faceplate = new ModelRenderer(this);
        Faceplate.setPos(-2.25F, -5.5F, -0.75F);
        Head.addChild(Faceplate);
        setRotationAngle(Faceplate, 0.0F, 0.0873F, 0.0F);


        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, 0.0F, 0.0F);
        Faceplate.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.4471F, -0.355F, 0.6F);
        cube_r3.texOffs(8, 21).addBox(-1.5224F, -4.9455F, -1.2157F, 1.0F, 6.0F, 4.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-0.425F, -0.575F, 1.675F);
        Faceplate.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.9707F, -0.355F, 0.6F);
        cube_r4.texOffs(0, 7).addBox(-1.5224F, -6.6946F, 0.152F, 1.0F, 7.0F, 7.0F, 0.0F, false);

        Eye_r1 = new ModelRenderer(this);
        Eye_r1.setPos(1.075F, -2.05F, 1.15F);
        Faceplate.addChild(Eye_r1);
        setRotationAngle(Eye_r1, 1.5352F, -0.1128F, 0.2297F);
        Eye_r1.texOffs(0, 32).addBox(-2.9458F, 0.0302F, -1.2102F, 1.0F, 1.0F, 1.0F, -0.2F, false);

        Eye_r2 = new ModelRenderer(this);
        Eye_r2.setPos(1.475F, -3.3F, 0.225F);
        Faceplate.addChild(Eye_r2);
        setRotationAngle(Eye_r2, 0.9707F, -0.3549F, 0.6F);
        Eye_r2.texOffs(7, 33).addBox(-1.5224F, 0.3054F, -0.848F, 1.0F, 2.0F, 2.0F, -0.3F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-0.15F, -0.025F, 0.75F);
        Faceplate.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.4631F, 0.0479F, 0.3539F);
        cube_r5.texOffs(0, 21).addBox(-2.0586F, -8.048F, -0.0635F, 1.0F, 8.0F, 3.0F, 0.0F, false);

        Eye_r3 = new ModelRenderer(this);
        Eye_r3.setPos(-0.75F, 1.525F, 1.625F);
        Faceplate.addChild(Eye_r3);
        setRotationAngle(Eye_r3, 0.0F, -0.2618F, 0.0F);
        Eye_r3.texOffs(7, 33).addBox(-1.8112F, -1.0F, -0.8415F, 1.0F, 2.0F, 2.0F, -0.4F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(0.0F, 2.5F, 0.75F);
        Faceplate.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, -0.2182F, 0.0F);
        cube_r6.texOffs(0, 32).addBox(-1.8609F, -3.0F, -1.878F, 1.0F, 4.0F, 5.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, -3.5F, 0.0F);
        setRotationAngle(Body, 1.5708F, -1.4835F, -1.5708F);
        Body.texOffs(8, 7).addBox(1.5086F, -1.1305F, -3.0F, 2.0F, 1.0F, 6.0F, 0.2F, false);
        Body.texOffs(16, 14).addBox(1.0086F, 3.8695F, -3.5F, 4.0F, 2.0F, 7.0F, 0.0F, false);
        Body.texOffs(16, 0).addBox(0.5086F, -0.6305F, -4.0F, 5.0F, 5.0F, 8.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(3.4F, 0.625F, -1.0F);
        Body.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, 0.0F, 0.1309F);
        cube_r7.texOffs(24, 35).addBox(-3.0F, -1.0F, -3.0F, 2.0F, 21.0F, 8.0F, 0.1F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(6.775F, 1.75F, 1.0F);
        Body.addChild(cube_r8);
        setRotationAngle(cube_r8, -0.2182F, 0.0F, -0.3054F);
        cube_r8.texOffs(0, 58).addBox(-1.9063F, -4.629F, 0.8848F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(6.775F, 1.75F, -3.0F);
        Body.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.2182F, 0.0F, -0.3054F);
        cube_r9.texOffs(0, 58).addBox(-1.9063F, -4.1962F, 1.0678F, 4.0F, 6.0F, 0.0F, 0.0F, false);

        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(7.0F, 4.75F, -1.0F);
        Body.addChild(cube_r10);
        setRotationAngle(cube_r10, -0.1309F, 0.0F, 0.0F);
        cube_r10.texOffs(11, 58).addBox(-1.9914F, -3.2599F, 0.9744F, 2.0F, 4.0F, 0.0F, 0.0F, false);

        LowerSpine = new ModelRenderer(this);
        LowerSpine.setPos(4.0F, 6.9F, -1.5F);
        Body.addChild(LowerSpine);
        LowerSpine.texOffs(17, 28).addBox(-1.8914F, 0.3195F, 0.5F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(0.0F, 7.25F, -0.5F);
        LowerSpine.addChild(cube_r11);
        setRotationAngle(cube_r11, 0.0F, 0.0F, 0.0873F);
        cube_r11.texOffs(17, 23).addBox(-1.999F, -1.0436F, 0.0F, 2.0F, 2.0F, 4.0F, 0.2F, false);

        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(0.0F, 0.0F, 0.0F);
        LowerSpine.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.0F, 0.0F, -0.1309F);
        cube_r12.texOffs(17, 23).addBox(-1.9659F, -1.2588F, 0.0F, 2.0F, 2.0F, 3.0F, 0.2F, false);

        Arm_R = new ModelRenderer(this);
        Arm_R.setPos(3.75F, 0.05F, -5.05F);
        Body.addChild(Arm_R);
        setRotationAngle(Arm_R, -0.5672F, 0.0F, 0.0F);


        cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(-1.475F, 1.5407F, 1.7549F);
        Arm_R.addChild(cube_r13);
        setRotationAngle(cube_r13, -0.0873F, -0.0436F, 0.0F);
        cube_r13.texOffs(51, 0).addBox(-0.9568F, -2.716F, -0.2438F, 3.0F, 12.0F, 0.0F, 0.0F, false);

        cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(-1.0F, 1.5407F, 0.9049F);
        Arm_R.addChild(cube_r14);
        setRotationAngle(cube_r14, 0.0F, -0.0436F, 0.0F);
        cube_r14.texOffs(42, 0).addBox(-1.9568F, -2.6474F, -0.1842F, 4.0F, 12.0F, 0.0F, 0.0F, false);

        Hand_R = new ModelRenderer(this);
        Hand_R.setPos(1.0F, 12.0407F, -0.0951F);
        Arm_R.addChild(Hand_R);
        setRotationAngle(Hand_R, 0.0F, 0.0F, 1.3963F);


        cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(-0.3397F, -2.0922F, 1.625F);
        Hand_R.addChild(cube_r15);
        setRotationAngle(cube_r15, 0.0F, -0.0436F, 0.0F);
        cube_r15.texOffs(42, 17).addBox(-1.7752F, 0.864F, -1.1922F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(-0.6397F, 14.2078F, 2.125F);
        Hand_R.addChild(cube_r16);
        setRotationAngle(cube_r16, 0.0F, -0.0436F, 0.0F);
        cube_r16.texOffs(52, 22).addBox(-1.7752F, -1.136F, -1.1922F, 3.0F, 11.0F, 0.0F, 0.0F, false);

        cube_r17 = new ModelRenderer(this);
        cube_r17.setPos(-1.0147F, 5.6328F, 1.625F);
        Hand_R.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.0F, -0.0436F, 0.0F);
        cube_r17.texOffs(52, 12).addBox(-1.7752F, -1.136F, -1.1922F, 4.0F, 9.0F, 1.0F, -0.3F, false);

        cube_r18 = new ModelRenderer(this);
        cube_r18.setPos(0.6603F, -2.1172F, 1.125F);
        Hand_R.addChild(cube_r18);
        setRotationAngle(cube_r18, -0.0131F, -0.0416F, 0.3057F);
        cube_r18.texOffs(40, 20).addBox(-2.4795F, -0.9429F, -0.1922F, 6.0F, 6.0F, 0.0F, 0.0F, false);

        cube_r19 = new ModelRenderer(this);
        cube_r19.setPos(-0.8397F, 1.8828F, 1.625F);
        Hand_R.addChild(cube_r19);
        setRotationAngle(cube_r19, 0.0F, -0.0436F, 0.0F);
        cube_r19.texOffs(42, 12).addBox(-1.7752F, -1.136F, -1.1922F, 4.0F, 4.0F, 1.0F, 0.0F, false);

        Leg_L = new ModelRenderer(this);
        Leg_L.setPos(4.15F, 10.5F, 4.575F);
        setRotationAngle(Leg_L, -1.5708F, -0.3054F, 1.5708F);
        Leg_L.texOffs(0, 41).addBox(-1.3007F, -1.0463F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);
        Leg_L.texOffs(16, 49).addBox(0.1993F, -0.9713F, 1.0F, 3.0F, 8.0F, 0.0F, 0.0F, false);

        Lower_Leg_L = new ModelRenderer(this);
        Lower_Leg_L.setPos(0.2F, 7.525F, 0.0F);
        Leg_L.addChild(Lower_Leg_L);
        setRotationAngle(Lower_Leg_L, 0.0F, 0.0F, -0.3054F);


        cube_r20 = new ModelRenderer(this);
        cube_r20.setPos(1.55F, -0.25F, 0.0F);
        Lower_Leg_L.addChild(cube_r20);
        setRotationAngle(cube_r20, 0.0F, 0.0F, -1.6581F);
        cube_r20.texOffs(8, 41).addBox(-1.766F, -2.6428F, 0.0F, 2.0F, 8.0F, 2.0F, -0.2F, false);

        cube_r21 = new ModelRenderer(this);
        cube_r21.setPos(0.75F, -7.75F, 0.5F);
        Lower_Leg_L.addChild(cube_r21);
        setRotationAngle(cube_r21, 0.0F, 0.0F, 0.1745F);
        cube_r21.texOffs(1, 53).addBox(-1.4226F, 7.9063F, -1.0F, 4.0F, 2.0F, 3.0F, -0.2F, false);

        Feet_L = new ModelRenderer(this);
        Feet_L.setPos(7.425F, -0.85F, 0.0F);
        Lower_Leg_L.addChild(Feet_L);
        setRotationAngle(Feet_L, 0.0F, 0.0F, -0.7854F);
        Feet_L.texOffs(0, 41).addBox(-1.9848F, -1.8264F, 0.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Feet_L.texOffs(16, 57).addBox(-3.2348F, 0.1736F, 1.0F, 2.0F, 5.0F, 0.0F, 0.0F, false);
        Feet_L.texOffs(16, 42).addBox(-1.9848F, 1.4236F, 0.0F, 2.0F, 5.0F, 2.0F, -0.3F, false);

        Leg_R = new ModelRenderer(this);
        Leg_R.setPos(-2.0F, 10.5F, 5.225F);
        setRotationAngle(Leg_R, -1.5708F, -0.3054F, 1.5708F);
        Leg_R.texOffs(0, 41).addBox(-1.6014F, -0.0926F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);
        Leg_R.texOffs(16, 49).addBox(0.1993F, -0.9713F, 1.0F, 3.0F, 8.0F, 0.0F, 0.0F, false);

        Lower_Leg_R = new ModelRenderer(this);
        Lower_Leg_R.setPos(0.2F, 7.525F, 0.0F);
        Leg_R.addChild(Lower_Leg_R);
        setRotationAngle(Lower_Leg_R, 0.0F, 0.0F, -0.3054F);


        cube_r22 = new ModelRenderer(this);
        cube_r22.setPos(1.55F, -0.25F, 0.0F);
        Lower_Leg_R.addChild(cube_r22);
        setRotationAngle(cube_r22, 0.0F, 0.0F, -1.6581F);
        cube_r22.texOffs(8, 41).addBox(-1.766F, -2.6428F, 0.0F, 2.0F, 8.0F, 2.0F, -0.2F, false);

        cube_r23 = new ModelRenderer(this);
        cube_r23.setPos(0.75F, -7.75F, 0.5F);
        Lower_Leg_R.addChild(cube_r23);
        setRotationAngle(cube_r23, 0.0F, 0.0F, 0.1745F);
        cube_r23.texOffs(1, 53).addBox(-1.4226F, 7.9063F, -1.0F, 4.0F, 2.0F, 3.0F, -0.2F, false);

        Feet_R = new ModelRenderer(this);
        Feet_R.setPos(7.425F, -0.85F, 0.0F);
        Lower_Leg_R.addChild(Feet_R);
        setRotationAngle(Feet_R, 0.0F, 0.0F, -0.7854F);
        Feet_R.texOffs(0, 41).addBox(-1.9848F, -1.8264F, 0.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Feet_R.texOffs(16, 57).addBox(-3.2348F, 0.1736F, 1.0F, 2.0F, 5.0F, 0.0F, 0.0F, false);
        Feet_R.texOffs(16, 42).addBox(-1.9848F, 1.4236F, 0.0F, 2.0F, 5.0F, 2.0F, -0.3F, false);
    }



    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg_L.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg_R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(DawnOfGreenDoubtEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }
}
