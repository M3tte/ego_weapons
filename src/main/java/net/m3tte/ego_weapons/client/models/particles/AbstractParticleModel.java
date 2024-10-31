package net.m3tte.ego_weapons.client.models.particles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

// Concept from: https://github.com/bottomtextdanny/Effective-Forge-/blob/1.19.2/src/main/java/bottomtextdanny/effective_fg/util/ParticleModel.java#L6
public abstract class AbstractParticleModel {
    public int texWidth, texHeight;

    public int getTexWidth() {
        return texWidth;
    }

    public int getTexHeight() {
        return texHeight;
    }

    public abstract void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuffer, int light, float u0, float u1, float v0, float v1, float red, float green, float blue, float alpha);
}
