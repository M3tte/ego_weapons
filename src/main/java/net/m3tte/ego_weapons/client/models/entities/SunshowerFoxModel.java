package net.m3tte.ego_weapons.client.models.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.entities.SunshowerFoxEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SunshowerFoxModel extends EntityModel<SunshowerFoxEntity> {
    private final ModelRenderer Torso;
    private final ModelRenderer Leg_B_L;
    private final ModelRenderer Leg_B_R;
    private final ModelRenderer Leg_F_L;
    private final ModelRenderer Leg_F_R;
    private final ModelRenderer Tail;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer Head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer Ear;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer Ear2;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;

    public SunshowerFoxModel() {
        texWidth = 64;
        texHeight = 64;

        Torso = new ModelRenderer(this);
        Torso.setPos(-0.5F, 20.0F, 0.0F);
        Torso.texOffs(0, 46).addBox(-2.5F, -5.5F, -6.5F, 8.0F, 6.0F, 12.0F, -0.2F, false);
        Torso.texOffs(0, 30).addBox(-2.0F, -5.0F, -6.0F, 7.0F, 5.0F, 11.0F, 0.0F, false);

        Leg_B_L = new ModelRenderer(this);
        Leg_B_L.setPos(3.5F, 0.0F, 3.25F);
        Torso.addChild(Leg_B_L);
        Leg_B_L.texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);

        Leg_B_R = new ModelRenderer(this);
        Leg_B_R.setPos(0.0F, 0.0F, 3.25F);
        Torso.addChild(Leg_B_R);
        Leg_B_R.texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        Leg_F_L = new ModelRenderer(this);
        Leg_F_L.setPos(3.5F, 0.0F, -3.0F);
        Torso.addChild(Leg_F_L);
        Leg_F_L.texOffs(32, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);

        Leg_F_R = new ModelRenderer(this);
        Leg_F_R.setPos(-0.5F, -1.0F, -3.0F);
        Torso.addChild(Leg_F_R);
        Leg_F_R.texOffs(32, 0).addBox(-1.0F, -0.041F, -1.2367F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        Tail = new ModelRenderer(this);
        Tail.setPos(1.0F, -2.5F, 5.0F);
        Torso.addChild(Tail);
        Tail.texOffs(46, 2).addBox(-2.0F, -2.0F, 0.0F, 5.0F, 4.0F, 3.0F, 0.0F, false);

        tail2 = new ModelRenderer(this);
        tail2.setPos(0.0F, 0.0F, 2.5F);
        Tail.addChild(tail2);
        tail2.texOffs(44, 10).addBox(-2.0F, -2.0F, 0.0F, 5.0F, 4.0F, 5.0F, 0.2F, false);

        tail3 = new ModelRenderer(this);
        tail3.setPos(0.0F, 0.0F, 5.0F);
        tail2.addChild(tail3);
        tail3.texOffs(45, 19).addBox(-2.0F, -2.0F, 0.0F, 5.0F, 4.0F, 4.0F, 0.0F, false);

        Head = new ModelRenderer(this);
        Head.setPos(1.0F, -3.5F, -5.5F);
        Torso.addChild(Head);
        Head.texOffs(0, 2).addBox(-3.0F, -4.7433F, -6.1949F, 7.0F, 5.0F, 6.0F, -0.1F, false);
        Head.texOffs(30, 6).addBox(-1.0F, -1.7433F, -7.9699F, 3.0F, 2.0F, 2.0F, -0.1F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-2.375F, 0.85F, -6.85F);
        Head.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.1745F);
        cube_r1.texOffs(0, 25).addBox(-2.0F, -4.0F, -1.0F, 5.0F, 4.0F, 1.0F, -0.5F, true);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(1.35F, 0.525F, -6.85F);
        Head.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.1745F);
        cube_r2.texOffs(0, 25).addBox(-1.0F, -4.0F, -1.0F, 5.0F, 4.0F, 1.0F, -0.5F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-0.5F, 0.9F, -0.425F);
        Head.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.5672F, 0.0F, 0.0F);
        cube_r3.texOffs(0, 15).addBox(-2.0F, -2.3933F, -2.4449F, 6.0F, 3.0F, 3.0F, -0.1F, false);

        Ear = new ModelRenderer(this);
        Ear.setPos(2.0F, -4.35F, -5.0F);
        Head.addChild(Ear);


        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, 0.0F, 5.0F);
        Ear.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.6109F);
        cube_r4.texOffs(9, 21).addBox(0.0F, -3.3933F, -5.4449F, 2.0F, 4.0F, 1.0F, -0.1F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.025F, 0.075F, 4.575F);
        Ear.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, 0.6109F);
        cube_r5.texOffs(15, 21).addBox(-1.0F, -4.3933F, -5.4449F, 4.0F, 5.0F, 1.0F, -0.5F, false);

        Ear2 = new ModelRenderer(this);
        Ear2.setPos(-2.65F, -3.275F, -5.0F);
        Head.addChild(Ear2);
        setRotationAngle(Ear2, 0.0F, 0.0F, -1.1781F);


        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(0.0F, 0.0F, 5.0F);
        Ear2.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.6109F);
        cube_r6.texOffs(9, 21).addBox(0.0F, -3.3933F, -5.4449F, 2.0F, 4.0F, 1.0F, -0.1F, true);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(0.025F, 0.075F, 4.575F);
        Ear2.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, 0.0F, 0.6109F);
        cube_r7.texOffs(15, 21).addBox(-1.0F, -4.3933F, -5.4449F, 4.0F, 5.0F, 1.0F, -0.5F, true);
    }

    @Override
    public void setupAnim(SunshowerFoxEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }



    public void setupCarryAnim(float ageInTicks){

        //previously the render function, render code was moved to a method below
        Torso.setPos(-2F, 28.5F, 3F);
        setRotationAngle(Torso, -1.0472F, 0.0F, 0.0F);
        Leg_B_L.setPos(3.5F, 0.0F, 3.25F);
        setRotationAngle(Leg_B_L, 1.2217F, 0.0F, 0.0F);

        Leg_B_R.setPos(0.0F, 0.0F, 3.25F);
        setRotationAngle(Leg_B_R, 0.9163F, 0.0F, 0.0F);

        Leg_F_L.setPos(3.5F, -1.0F, -4.0F);
        setRotationAngle(Leg_F_L, 0.2182F, 0.0F, 0.0F);

        Leg_F_R.setPos(-0.5F, -0.25F, -4.75F);
        setRotationAngle(Leg_F_R, -0.3054F, 0.0F, 0.0F);

        Tail.setPos(1.0F, -2.5F, 5.0F);
        setRotationAngle(Tail, 0.0F, -0.3054F, 0.0F);

        tail2.setPos(0.0F, 0.0F, 1.5F);
        setRotationAngle(tail2, 0.0F, -0.3927F, 0.0F);

        tail3.setPos(0.0F, 0.0F, 4.0F);
        setRotationAngle(tail3, 0.0F, -0.3054F, 0.0F);

        Head.setPos(1.0F, -3.5F, -5.5F);
        setRotationAngle(Head, 1.0908F, 0.0F, 0.0F);

        cube_r1.setPos(-2.375F, 0.85F, -6.85F);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.1745F);

        cube_r2.setPos(1.35F, 0.525F, -6.85F);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.1745F);

        cube_r3.setPos(-0.5F, 0.9F, -0.425F);
        setRotationAngle(cube_r3, -0.5672F, 0.0F, 0.0F);

        Ear.setPos(2.0F, -4.35F, -5.0F);

        cube_r4.setPos(0.0F, 0.0F, 5.0F);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.6109F);

        cube_r5.setPos(0.025F, 0.075F, 4.575F);
        setRotationAngle(cube_r5, 0.0F, 0.0F, 0.6109F);

        Ear2.setPos(-2.65F, -3.275F, -5.0F);
        setRotationAngle(Ear2, 0.0F, 0.0F, -1.1781F);

        cube_r6.setPos(0.0F, 0.0F, 5.0F);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.6109F);

        cube_r7.setPos(0.025F, 0.075F, 4.575F);
        setRotationAngle(cube_r7, 0.0F, 0.0F, 0.6109F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Torso.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.Torso);
    }

    public void renderOnShoulder(MatrixStack p_228284_1_, IVertexBuilder p_228284_2_, int p_228284_3_, int p_228284_4_, float p_228284_5_, float p_228284_6_, float p_228284_7_, float p_228284_8_, int p_228284_9_) {
        this.setupCarryAnim(0);
        this.parts().forEach((p_228285_4_) -> {
            p_228285_4_.render(p_228284_1_, p_228284_2_, p_228284_3_, p_228284_4_);
        });
    }
}