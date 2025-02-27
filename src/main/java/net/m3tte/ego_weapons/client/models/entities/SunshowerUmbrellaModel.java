package net.m3tte.ego_weapons.client.models.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.entities.SunshowerFoxEntity;
import net.m3tte.ego_weapons.entities.SunshowerUmbrellaEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SunshowerUmbrellaModel extends EntityModel<SunshowerUmbrellaEntity> {
    private final ModelRenderer bone;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public SunshowerUmbrellaModel() {
        texWidth = 64;
        texHeight = 128;

        bone = new ModelRenderer(this);
        bone.setPos(8.0F, 2.5F, 2.25F);
        setRotationAngle(bone, -2.618F, 0.0F, 0.0F);
        bone.texOffs(0, 0).addBox(-8.0F, -20.925F, 7.0F, 1.0F, 29.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-9.875F, -15.625F, 16.0F);
        bone.addChild(cube_r1);
        setRotationAngle(cube_r1, -1.5708F, -1.0036F, 1.5708F);
        cube_r1.texOffs(-23, 48).addBox(-8.875F, 1.0782F, -14.0F, 18.0F, 0.0F, 23.0F, 0.0F, true);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-15.875F, -15.625F, 4.0F);
        bone.addChild(cube_r2);
        setRotationAngle(cube_r2, -3.1416F, 0.0F, 2.5744F);
        cube_r2.texOffs(-23, 72).addBox(-8.875F, 1.0782F, -15.0F, 18.0F, 0.0F, 23.0F, 0.0F, true);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-3.875F, -15.625F, -1.0F);
        bone.addChild(cube_r3);
        setRotationAngle(cube_r3, 1.5708F, 1.0036F, 1.5708F);
        cube_r3.texOffs(-23, 48).addBox(-8.875F, 1.0782F, -15.0F, 18.0F, 0.0F, 23.0F, 0.0F, true);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(1.2F, -15.625F, 4.975F);
        bone.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.5672F);
        cube_r4.texOffs(-23, 72).addBox(-8.875F, 1.0782F, -9.0F, 18.0F, 0.0F, 23.0F, 0.0F, true);
    }

    @Override
    public void setupAnim(SunshowerUmbrellaEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}