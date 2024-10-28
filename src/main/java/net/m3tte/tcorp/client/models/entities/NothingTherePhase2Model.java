package net.m3tte.tcorp.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.tcorp.entities.NothingThere2Entity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class NothingTherePhase2Model extends EntityModel<NothingThere2Entity> {
    private final ModelRenderer Head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer Hand;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer Torso;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer cube_r9;
    private final ModelRenderer cube_r10;
    private final ModelRenderer cube_r11;
    private final ModelRenderer cube_r12;
    private final ModelRenderer Leg_L;
    private final ModelRenderer cube_r13;
    private final ModelRenderer cube_r14;
    private final ModelRenderer Leg_R;
    private final ModelRenderer cube_r15;
    private final ModelRenderer cube_r16;
    private final ModelRenderer Arm_L;
    private final ModelRenderer cube_r17;
    private final ModelRenderer cube_r18;
    private final ModelRenderer cube_r19;
    private final ModelRenderer cube_r20;
    private final ModelRenderer cube_r21;
    private final ModelRenderer cube_r22;
    private final ModelRenderer cube_r23;
    private final ModelRenderer Arm_L2;
    private final ModelRenderer cube_r24;
    private final ModelRenderer cube_r25;
    private final ModelRenderer cube_r26;
    private final ModelRenderer cube_r27;
    private final ModelRenderer cube_r28;
    private final ModelRenderer Meatclump;
    private final ModelRenderer cube_r29;
    private final ModelRenderer cube_r30;
    private final ModelRenderer cube_r31;
    private final ModelRenderer cube_r32;
    private final ModelRenderer Blade;

    public NothingTherePhase2Model() {
        texWidth = 180;
        texHeight = 150;

        Head = new ModelRenderer(this);
        Head.setPos(-1.0F, -26.0F, 0.0F);
        Head.texOffs(6, 6).addBox(-5.5F, -18.0F, -5.0F, 11.0F, 16.0F, 11.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(4.45F, -15.05F, 0.0F);
        Head.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.1837F, -0.6147F, 1.2862F);
        cube_r1.texOffs(0, 8).addBox(-3.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-4.55F, -12.475F, 0.0F);
        Head.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.219F, -0.3286F, -1.3454F);
        cube_r2.texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        Hand = new ModelRenderer(this);
        Hand.setPos(-2.0F, -16.375F, 0.0F);
        Head.addChild(Hand);
        setRotationAngle(Hand, 0.0F, 0.0F, -0.2182F);
        Hand.texOffs(39, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-0.85F, -4.95F, -0.4F);
        Hand.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.3541F, 0.9128F, -0.437F);
        cube_r3.texOffs(39, 8).addBox(-3.0F, -5.0F, 1.0F, 3.0F, 5.0F, 0.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.425F, -5.225F, 0.55F);
        Hand.addChild(cube_r4);
        setRotationAngle(cube_r4, 2.6259F, -0.4937F, -2.2671F);
        cube_r4.texOffs(39, 8).addBox(-3.0F, -5.0F, 1.0F, 3.0F, 5.0F, 0.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.0F, -4.85F, -1.675F);
        Hand.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, -0.3054F, 0.0F);
        cube_r5.texOffs(39, 8).addBox(-3.0F, -5.0F, 1.0F, 3.0F, 5.0F, 0.0F, 0.0F, false);

        Torso = new ModelRenderer(this);
        Torso.setPos(-1.0F, 6.0F, 0.0F);
        Torso.texOffs(0, 33).addBox(-7.5F, -34.0F, -2.0F, 15.0F, 13.0F, 12.0F, 0.0F, false);
        Torso.texOffs(0, 78).addBox(-6.0F, -21.0F, -1.0F, 12.0F, 13.0F, 10.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-1.0F, -11.0F, -2.0F);
        Torso.addChild(cube_r6);
        setRotationAngle(cube_r6, -0.0436F, 0.0019F, 0.0436F);
        cube_r6.texOffs(76, 107).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 9.0F, 2.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(1.0F, -11.0F, -2.0F);
        Torso.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.0435F, -0.0038F, -0.0872F);
        cube_r7.texOffs(76, 106).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(-0.75F, -11.025F, 6.975F);
        Torso.addChild(cube_r8);
        setRotationAngle(cube_r8, -1.767F, 0.0025F, 0.0329F);
        cube_r8.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(-0.75F, -15.5F, 9.0F);
        Torso.addChild(cube_r9);
        setRotationAngle(cube_r9, -1.5488F, 0.0025F, 0.0329F);
        cube_r9.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(-0.75F, -22.5F, 9.75F);
        Torso.addChild(cube_r10);
        setRotationAngle(cube_r10, -1.3743F, 0.0025F, 0.0329F);
        cube_r10.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(-0.75F, -28.75F, 9.0F);
        Torso.addChild(cube_r11);
        setRotationAngle(cube_r11, -0.9816F, 0.0025F, 0.0329F);
        cube_r11.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(-5.75F, -28.0F, -1.25F);
        Torso.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.6981F, -0.3054F, 0.0F);
        cube_r12.texOffs(47, 0).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        Leg_L = new ModelRenderer(this);
        Leg_L.setPos(3.0F, 43.0F, 1.0F);
        setRotationAngle(Leg_L, 0.0F, 0.0F, -0.0436F);
        Leg_L.texOffs(56, 0).addBox(0.0F, -47.0F, -1.0F, 6.0F, 15.0F, 8.0F, 0.1F, false);

        cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(2.675F, -21.25F, 0.0F);
        Leg_L.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.0F, 0.0F, 0.0436F);
        cube_r13.texOffs(64, 34).addBox(-3.1674F, 0.1564F, -3.0F, 6.0F, 2.0F, 10.0F, 0.0F, false);

        cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(3.25F, -32.25F, 0.0F);
        Leg_L.addChild(cube_r14);
        setRotationAngle(cube_r14, 0.0F, 0.0F, 0.0436F);
        cube_r14.texOffs(48, 25).addBox(-2.7749F, 0.1478F, -1.0F, 5.0F, 11.0F, 8.0F, 0.0F, false);

        Leg_R = new ModelRenderer(this);
        Leg_R.setPos(-11.0F, 43.0F, 1.0F);
        setRotationAngle(Leg_R, 0.0F, 0.0F, 0.0436F);
        Leg_R.texOffs(56, 0).addBox(0.0F, -47.0F, -1.0F, 6.0F, 15.0F, 8.0F, 0.0F, true);

        cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(3.325F, -21.0F, 0.0F);
        Leg_R.addChild(cube_r15);
        setRotationAngle(cube_r15, 0.0F, 0.0F, -0.0436F);
        cube_r15.texOffs(64, 34).addBox(-2.8269F, -0.1053F, -3.0F, 6.0F, 2.0F, 10.0F, 0.0F, true);

        cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(2.75F, -32.0F, 0.0F);
        Leg_R.addChild(cube_r16);
        setRotationAngle(cube_r16, 0.0F, 0.0F, -0.0436F);
        cube_r16.texOffs(48, 25).addBox(-2.2204F, -0.0703F, -1.0F, 5.0F, 11.0F, 8.0F, 0.0F, true);

        Arm_L = new ModelRenderer(this);
        Arm_L.setPos(8.0F, 24.0F, 1.0F);


        cube_r17 = new ModelRenderer(this);
        cube_r17.setPos(6.25F, -50.5F, 3.75F);
        Arm_L.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.0F, 0.0F, 0.4363F);
        cube_r17.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 4.0F, 2.0F, -0.3F, false);

        cube_r18 = new ModelRenderer(this);
        cube_r18.setPos(1.75F, -51.25F, 3.75F);
        Arm_L.addChild(cube_r18);
        setRotationAngle(cube_r18, 0.0F, 0.0F, 0.2182F);
        cube_r18.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        cube_r19 = new ModelRenderer(this);
        cube_r19.setPos(24.75F, -51.5F, -7.0F);
        Arm_L.addChild(cube_r19);
        setRotationAngle(cube_r19, -0.3054F, 0.0F, -1.3963F);
        cube_r19.texOffs(0, 58).addBox(-3.0F, 0.0F, 7.0F, 7.0F, 10.0F, 0.0F, 0.0F, false);

        cube_r20 = new ModelRenderer(this);
        cube_r20.setPos(27.0F, -51.5F, -6.0F);
        Arm_L.addChild(cube_r20);
        setRotationAngle(cube_r20, 0.0F, 0.0F, -1.3963F);
        cube_r20.texOffs(15, 58).addBox(-3.0F, 0.0F, 7.0F, 12.0F, 16.0F, 0.0F, 0.0F, false);
        cube_r20.texOffs(15, 58).addBox(-3.0F, 0.0F, 9.5F, 12.0F, 16.0F, 0.0F, 0.0F, false);
        cube_r20.texOffs(15, 58).addBox(-3.0F, 0.0F, 12.0F, 12.0F, 16.0F, 0.0F, 0.0F, false);

        cube_r21 = new ModelRenderer(this);
        cube_r21.setPos(25.0F, -49.5F, 0.0F);
        Arm_L.addChild(cube_r21);
        setRotationAngle(cube_r21, 0.0F, 0.0F, -1.3963F);
        cube_r21.texOffs(76, 15).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 6.0F, 8.0F, 0.1F, false);

        cube_r22 = new ModelRenderer(this);
        cube_r22.setPos(13.0F, -49.0F, 0.0F);
        Arm_L.addChild(cube_r22);
        setRotationAngle(cube_r22, 0.0F, 0.0F, -1.5708F);
        cube_r22.texOffs(76, 46).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 13.0F, 8.0F, 0.0F, false);

        cube_r23 = new ModelRenderer(this);
        cube_r23.setPos(-2.0F, -49.0F, 0.0F);
        Arm_L.addChild(cube_r23);
        setRotationAngle(cube_r23, 0.0F, 0.0F, -1.5708F);
        cube_r23.texOffs(46, 50).addBox(-4.0F, 0.0F, -1.0F, 7.0F, 15.0F, 8.0F, 0.1F, false);

        Arm_L2 = new ModelRenderer(this);
        Arm_L2.setPos(-23.0F, 24.0F, 1.0F);


        cube_r24 = new ModelRenderer(this);
        cube_r24.setPos(14.0F, -51.75F, 3.75F);
        Arm_L2.addChild(cube_r24);
        setRotationAngle(cube_r24, 3.1034F, 0.1704F, 2.9202F);
        cube_r24.texOffs(86, 29).addBox(0.0F, -5.0F, -2.0F, 6.0F, 5.0F, 5.0F, 0.0F, false);

        cube_r25 = new ModelRenderer(this);
        cube_r25.setPos(1.75F, -51.25F, 2.75F);
        Arm_L2.addChild(cube_r25);
        setRotationAngle(cube_r25, 3.1034F, 0.1704F, 2.9202F);
        cube_r25.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

        cube_r26 = new ModelRenderer(this);
        cube_r26.setPos(-2.75F, -50.5F, 2.75F);
        Arm_L2.addChild(cube_r26);
        setRotationAngle(cube_r26, 3.0672F, 0.158F, 2.6994F);
        cube_r26.texOffs(47, 4).addBox(0.0F, -5.0F, -2.0F, 2.0F, 4.0F, 2.0F, -0.3F, false);

        cube_r27 = new ModelRenderer(this);
        cube_r27.setPos(-17.0F, -49.0F, 0.0F);
        Arm_L2.addChild(cube_r27);
        setRotationAngle(cube_r27, 0.0F, 0.0F, -1.5708F);
        cube_r27.texOffs(104, 46).addBox(-3.0F, 4.0F, -1.0F, 6.0F, 13.0F, 8.0F, 0.0F, false);

        cube_r28 = new ModelRenderer(this);
        cube_r28.setPos(-2.0F, -49.0F, 0.0F);
        Arm_L2.addChild(cube_r28);
        setRotationAngle(cube_r28, 0.0F, 0.0F, -1.5708F);
        cube_r28.texOffs(44, 73).addBox(-5.0F, 2.0F, -1.0F, 8.0F, 15.0F, 8.0F, 0.1F, false);

        Meatclump = new ModelRenderer(this);
        Meatclump.setPos(-20.25F, -49.5F, 0.0F);
        Arm_L2.addChild(Meatclump);
        setRotationAngle(Meatclump, 0.0F, 0.0F, -1.3526F);
        Meatclump.texOffs(70, 70).addBox(-3.5671F, -10.0474F, -1.0F, 9.0F, 18.0F, 8.0F, 0.1F, false);
        Meatclump.texOffs(62, 72).addBox(-4.5671F, -8.0474F, -2.0F, 11.0F, 13.0F, 10.0F, 0.1F, false);
        Meatclump.texOffs(56, 76).addBox(-5.5671F, -6.0474F, -3.0F, 13.0F, 8.0F, 12.0F, 0.1F, false);

        cube_r29 = new ModelRenderer(this);
        cube_r29.setPos(1.0F, -2.0F, 1.75F);
        Meatclump.addChild(cube_r29);
        setRotationAngle(cube_r29, -0.0049F, -0.1639F, 0.0602F);
        cube_r29.texOffs(-17, 101).addBox(-8.5671F, 2.6888F, -7.5175F, 18.0F, 0.0F, 17.0F, 0.0F, false);

        cube_r30 = new ModelRenderer(this);
        cube_r30.setPos(0.75F, -0.75F, 0.25F);
        Meatclump.addChild(cube_r30);
        setRotationAngle(cube_r30, 0.8737F, -0.192F, 0.0263F);
        cube_r30.texOffs(-17, 118).addBox(-8.5671F, 2.6888F, -7.5175F, 18.0F, 0.0F, 17.0F, 0.0F, false);

        cube_r31 = new ModelRenderer(this);
        cube_r31.setPos(0.75F, -6.75F, 5.25F);
        Meatclump.addChild(cube_r31);
        setRotationAngle(cube_r31, -1.0521F, -0.1639F, 0.0602F);
        cube_r31.texOffs(-17, 118).addBox(-8.5671F, 2.6888F, -7.5175F, 18.0F, 0.0F, 17.0F, 0.0F, false);

        cube_r32 = new ModelRenderer(this);
        cube_r32.setPos(1.0F, -6.75F, 3.0F);
        Meatclump.addChild(cube_r32);
        setRotationAngle(cube_r32, -0.354F, -0.1639F, 0.0602F);
        cube_r32.texOffs(-17, 101).addBox(-8.5671F, 2.6888F, -7.5175F, 18.0F, 0.0F, 17.0F, 0.0F, false);

        Blade = new ModelRenderer(this);
        Blade.setPos(-48.0F, 0.0F, 0.0F);
        Blade.texOffs(84, 0).addBox(-25.0F, -13.0F, 1.0F, 47.0F, 13.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(NothingThere2Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Torso.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg_L.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Leg_R.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm_L.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Arm_L2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Blade.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}