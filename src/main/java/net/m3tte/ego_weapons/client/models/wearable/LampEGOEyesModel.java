package net.m3tte.ego_weapons.client.models.wearable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class LampEGOEyesModel<T extends LivingEntity> extends BipedModel<T> implements TaggedModel {
    private final ModelRenderer Body;
    private final ModelRenderer EyesLayer_r1;
    private final ModelRenderer EyesLayer_r2;
    private final ModelRenderer EyesLayer_r3;
    private final ModelRenderer EyesLayer_r4;
    private final ModelRenderer EyesLayer_r5;
    private final ModelRenderer EyesLayer_r6;
    private final ModelRenderer EyesLayer_r7;
    private final ModelRenderer EyesLayer_r8;
    private final ModelRenderer EyesLayer_r9;
    private final ModelRenderer EyesLayer_r10;
    private final ModelRenderer EyesLayer_r11;
    private final ModelRenderer EyesLayer_r12;
    private final ModelRenderer EyesLayer_r13;
    private final ModelRenderer EyesLayer_r14;
    private final ModelRenderer EyesLayer_r15;
    private final ModelRenderer EyesLayer_r16;
    private final ModelRenderer EyesLayer_r17;
    private final ModelRenderer EyesLayer_r18;
    private final ModelRenderer EyesLayer_r19;
    private final ModelRenderer EyesLayer_r20;
    private final ModelRenderer EyesLayer_r21;
    private final ModelRenderer EyesLayer_r22;
    private final ModelRenderer EyesLayer_r23;
    private final ModelRenderer RightArm;
    private final ModelRenderer EyesLayer_r24;
    private final ModelRenderer EyesLayer_r25;
    private final ModelRenderer EyesLayer_r26;
    private final ModelRenderer EyesLayer_r27;
    private final ModelRenderer EyesLayer_r28;
    private final ModelRenderer EyesLayer_r29;
    private final ModelRenderer EyesLayer_r30;
    private final ModelRenderer EyesLayer_r31;
    private final ModelRenderer LeftArm;
    private final ModelRenderer EyesLayer_r32;
    private final ModelRenderer EyesLayer_r33;
    private final ModelRenderer EyesLayer_r34;
    private final ModelRenderer EyesLayer_r35;
    private final ModelRenderer EyesLayer_r36;
    private final ModelRenderer EyesLayer_r37;
    private final ModelRenderer EyesLayer_r38;
    private final ModelRenderer EyesLayer_r39;


    public LampEGOEyesModel() {
        super(0f);
        texWidth = 128;
        texHeight = 128;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);


        EyesLayer_r1 = new ModelRenderer(this);
        EyesLayer_r1.setPos(-5.65F, 16.0F, 0.75F);
        Body.addChild(EyesLayer_r1);
        setRotationAngle(EyesLayer_r1, -0.0873F, 0.0F, 0.0F);
        EyesLayer_r1.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r2 = new ModelRenderer(this);
        EyesLayer_r2.setPos(-5.25F, 13.0F, -1.25F);
        Body.addChild(EyesLayer_r2);
        setRotationAngle(EyesLayer_r2, -0.0873F, 0.0F, 0.0F);
        EyesLayer_r2.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r3 = new ModelRenderer(this);
        EyesLayer_r3.setPos(-5.25F, 11.25F, 0.75F);
        Body.addChild(EyesLayer_r3);
        setRotationAngle(EyesLayer_r3, 0.2182F, 0.0F, 0.0F);
        EyesLayer_r3.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r4 = new ModelRenderer(this);
        EyesLayer_r4.setPos(1.0F, 16.0F, 3.6F);
        Body.addChild(EyesLayer_r4);
        setRotationAngle(EyesLayer_r4, 0.0F, 1.5708F, -0.1745F);
        EyesLayer_r4.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 12.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r5 = new ModelRenderer(this);
        EyesLayer_r5.setPos(3.0F, 10.25F, 2.65F);
        Body.addChild(EyesLayer_r5);
        setRotationAngle(EyesLayer_r5, 0.0F, 1.5708F, 0.2182F);
        EyesLayer_r5.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 10.0F, 7.0F, 7.0F, -1.5F, false);

        EyesLayer_r6 = new ModelRenderer(this);
        EyesLayer_r6.setPos(2.0F, 5.25F, 2.775F);
        Body.addChild(EyesLayer_r6);
        setRotationAngle(EyesLayer_r6, 0.0F, 1.5708F, 0.0873F);
        EyesLayer_r6.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 11.0F, 7.0F, 7.0F, -1.8F, false);

        EyesLayer_r7 = new ModelRenderer(this);
        EyesLayer_r7.setPos(0.75F, 1.25F, 3.0F);
        Body.addChild(EyesLayer_r7);
        setRotationAngle(EyesLayer_r7, 0.0F, 1.5708F, 0.0873F);
        EyesLayer_r7.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 10.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r8 = new ModelRenderer(this);
        EyesLayer_r8.setPos(-2.5F, 16.0F, 3.5F);
        Body.addChild(EyesLayer_r8);
        setRotationAngle(EyesLayer_r8, 0.0F, 1.5708F, 0.1309F);
        EyesLayer_r8.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 12.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r9 = new ModelRenderer(this);
        EyesLayer_r9.setPos(-1.25F, 12.25F, 3.175F);
        Body.addChild(EyesLayer_r9);
        setRotationAngle(EyesLayer_r9, 0.0F, 1.5708F, -0.1309F);
        EyesLayer_r9.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 11.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r10 = new ModelRenderer(this);
        EyesLayer_r10.setPos(-1.25F, 5.25F, 3.175F);
        Body.addChild(EyesLayer_r10);
        setRotationAngle(EyesLayer_r10, 0.0F, 1.5708F, -0.1309F);
        EyesLayer_r10.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 11.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r11 = new ModelRenderer(this);
        EyesLayer_r11.setPos(-3.5F, 12.5F, -2.25F);
        Body.addChild(EyesLayer_r11);
        setRotationAngle(EyesLayer_r11, 0.0F, 1.5708F, 0.1745F);
        EyesLayer_r11.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.6F, false);

        EyesLayer_r12 = new ModelRenderer(this);
        EyesLayer_r12.setPos(-2.75F, 8.25F, 3.175F);
        Body.addChild(EyesLayer_r12);
        setRotationAngle(EyesLayer_r12, 0.0F, 1.5708F, 0.0F);
        EyesLayer_r12.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 10.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r13 = new ModelRenderer(this);
        EyesLayer_r13.setPos(-5.0F, 8.25F, 1.25F);
        Body.addChild(EyesLayer_r13);
        setRotationAngle(EyesLayer_r13, 0.2618F, 0.0F, 0.0F);
        EyesLayer_r13.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r14 = new ModelRenderer(this);
        EyesLayer_r14.setPos(-5.25F, 8.25F, -1.25F);
        Body.addChild(EyesLayer_r14);
        setRotationAngle(EyesLayer_r14, -0.1309F, 0.0F, 0.0F);
        EyesLayer_r14.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r15 = new ModelRenderer(this);
        EyesLayer_r15.setPos(-5.25F, 5.0F, -1.0F);
        Body.addChild(EyesLayer_r15);
        setRotationAngle(EyesLayer_r15, -0.0873F, 0.0F, 0.0F);
        EyesLayer_r15.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r16 = new ModelRenderer(this);
        EyesLayer_r16.setPos(-5.25F, 2.25F, 0.75F);
        Body.addChild(EyesLayer_r16);
        setRotationAngle(EyesLayer_r16, 0.2182F, 0.0F, 0.0F);
        EyesLayer_r16.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r17 = new ModelRenderer(this);
        EyesLayer_r17.setPos(5.25F, 8.25F, -1.25F);
        Body.addChild(EyesLayer_r17);
        setRotationAngle(EyesLayer_r17, 3.0107F, 0.0F, 3.1416F);
        EyesLayer_r17.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r18 = new ModelRenderer(this);
        EyesLayer_r18.setPos(5.25F, 2.25F, 0.75F);
        Body.addChild(EyesLayer_r18);
        setRotationAngle(EyesLayer_r18, -2.9234F, 0.0F, -3.1416F);
        EyesLayer_r18.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r19 = new ModelRenderer(this);
        EyesLayer_r19.setPos(5.65F, 16.25F, 0.75F);
        Body.addChild(EyesLayer_r19);
        setRotationAngle(EyesLayer_r19, -2.9234F, 0.0F, -3.1416F);
        EyesLayer_r19.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r20 = new ModelRenderer(this);
        EyesLayer_r20.setPos(5.25F, 11.25F, 0.75F);
        Body.addChild(EyesLayer_r20);
        setRotationAngle(EyesLayer_r20, -2.9234F, 0.0F, -3.1416F);
        EyesLayer_r20.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r21 = new ModelRenderer(this);
        EyesLayer_r21.setPos(5.0F, 8.25F, 1.25F);
        Body.addChild(EyesLayer_r21);
        setRotationAngle(EyesLayer_r21, -2.8798F, 0.0F, 3.1416F);
        EyesLayer_r21.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.25F, false);

        EyesLayer_r22 = new ModelRenderer(this);
        EyesLayer_r22.setPos(5.25F, 5.0F, -1.0F);
        Body.addChild(EyesLayer_r22);
        setRotationAngle(EyesLayer_r22, 3.0543F, 0.0F, 3.1416F);
        EyesLayer_r22.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r23 = new ModelRenderer(this);
        EyesLayer_r23.setPos(5.25F, 13.0F, -1.25F);
        Body.addChild(EyesLayer_r23);
        setRotationAngle(EyesLayer_r23, 3.0543F, 0.0F, 3.1416F);
        EyesLayer_r23.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        setRotationAngle(RightArm, -0.1745F, 0.0F, 0.0F);


        EyesLayer_r24 = new ModelRenderer(this);
        EyesLayer_r24.setPos(-4.025F, 3.25F, 1.0F);
        RightArm.addChild(EyesLayer_r24);
        setRotationAngle(EyesLayer_r24, 0.2182F, 0.0F, 0.0F);
        EyesLayer_r24.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.7F, false);

        EyesLayer_r25 = new ModelRenderer(this);
        EyesLayer_r25.setPos(-1.0F, 0.0F, -2.25F);
        RightArm.addChild(EyesLayer_r25);
        setRotationAngle(EyesLayer_r25, 0.0F, 1.5708F, 0.4363F);
        EyesLayer_r25.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.1F, false);

        EyesLayer_r26 = new ModelRenderer(this);
        EyesLayer_r26.setPos(0.0F, 3.0F, -1.95F);
        RightArm.addChild(EyesLayer_r26);
        setRotationAngle(EyesLayer_r26, 0.0F, 1.5708F, -0.2182F);
        EyesLayer_r26.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.4F, false);

        EyesLayer_r27 = new ModelRenderer(this);
        EyesLayer_r27.setPos(-1.0F, 2.5F, 2.475F);
        RightArm.addChild(EyesLayer_r27);
        setRotationAngle(EyesLayer_r27, 0.0F, 1.5708F, 0.0873F);
        EyesLayer_r27.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.1F, false);

        EyesLayer_r28 = new ModelRenderer(this);
        EyesLayer_r28.setPos(-1.75F, 5.75F, -1.9F);
        RightArm.addChild(EyesLayer_r28);
        setRotationAngle(EyesLayer_r28, 0.0F, 1.5708F, 0.2182F);
        EyesLayer_r28.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r29 = new ModelRenderer(this);
        EyesLayer_r29.setPos(-1.75F, 5.75F, 2.925F);
        RightArm.addChild(EyesLayer_r29);
        setRotationAngle(EyesLayer_r29, 0.0F, 1.5708F, -0.1309F);
        EyesLayer_r29.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r30 = new ModelRenderer(this);
        EyesLayer_r30.setPos(-3.575F, 0.0F, 0.0F);
        RightArm.addChild(EyesLayer_r30);
        setRotationAngle(EyesLayer_r30, 0.2182F, 0.0F, 0.0F);
        EyesLayer_r30.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.2F, false);

        EyesLayer_r31 = new ModelRenderer(this);
        EyesLayer_r31.setPos(-3.825F, 5.0F, -0.75F);
        RightArm.addChild(EyesLayer_r31);
        setRotationAngle(EyesLayer_r31, -0.0873F, 0.0F, 0.0F);
        EyesLayer_r31.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        setRotationAngle(LeftArm, 0.2094F, 0.0F, 0.0F);


        EyesLayer_r32 = new ModelRenderer(this);
        EyesLayer_r32.setPos(2.7F, 3.0F, 0.5F);
        LeftArm.addChild(EyesLayer_r32);
        setRotationAngle(EyesLayer_r32, -0.2182F, 0.0F, 0.0F);
        EyesLayer_r32.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.7F, false);

        EyesLayer_r33 = new ModelRenderer(this);
        EyesLayer_r33.setPos(2.0F, -0.5F, -2.275F);
        LeftArm.addChild(EyesLayer_r33);
        setRotationAngle(EyesLayer_r33, 0.0F, 1.5708F, 0.2182F);
        EyesLayer_r33.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.1F, false);

        EyesLayer_r34 = new ModelRenderer(this);
        EyesLayer_r34.setPos(0.5F, 2.75F, -1.975F);
        LeftArm.addChild(EyesLayer_r34);
        setRotationAngle(EyesLayer_r34, 0.0F, 1.5708F, 0.3054F);
        EyesLayer_r34.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.4F, false);

        EyesLayer_r35 = new ModelRenderer(this);
        EyesLayer_r35.setPos(1.5F, 0.75F, 2.45F);
        LeftArm.addChild(EyesLayer_r35);
        setRotationAngle(EyesLayer_r35, 0.0F, 1.5708F, 0.0873F);
        EyesLayer_r35.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.1F, false);

        EyesLayer_r36 = new ModelRenderer(this);
        EyesLayer_r36.setPos(2.25F, 5.25F, -1.925F);
        LeftArm.addChild(EyesLayer_r36);
        setRotationAngle(EyesLayer_r36, 0.0F, 1.5708F, 0.2182F);
        EyesLayer_r36.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r37 = new ModelRenderer(this);
        EyesLayer_r37.setPos(0.5F, 6.0F, 2.9F);
        LeftArm.addChild(EyesLayer_r37);
        setRotationAngle(EyesLayer_r37, 0.0F, 1.5708F, -0.1309F);
        EyesLayer_r37.texOffs(0, 0).addBox(-2.0F, -3.5F, -3.75F, 5.0F, 7.0F, 7.0F, -2.5F, false);

        EyesLayer_r38 = new ModelRenderer(this);
        EyesLayer_r38.setPos(3.15F, 0.0F, 0.25F);
        LeftArm.addChild(EyesLayer_r38);
        setRotationAngle(EyesLayer_r38, 0.2182F, 0.0F, 0.0F);
        EyesLayer_r38.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.2F, false);

        EyesLayer_r39 = new ModelRenderer(this);
        EyesLayer_r39.setPos(2.9F, 5.5F, -0.5F);
        LeftArm.addChild(EyesLayer_r39);
        setRotationAngle(EyesLayer_r39, -0.0873F, 0.0F, 0.0F);
        EyesLayer_r39.texOffs(0, 16).addBox(-2.0F, -3.5F, -3.75F, 6.0F, 7.0F, 7.0F, -2.5F, false);

        this.body = Body;
        this.rightArm = RightArm;
        this.leftArm = LeftArm;

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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
        return "lamp_ego_eyes_md";
    }
}