package net.m3tte.ego_weapons.client.renderer.modelBakery;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.m3tte.ego_weapons.client.models.wearable.TaggedModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;
import yesman.epicfight.api.client.model.*;
import yesman.epicfight.api.utils.math.Vec2f;
import yesman.epicfight.api.utils.math.Vec3f;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.minecraft.client.renderer.model.ModelRenderer.*;

@OnlyIn(Dist.CLIENT)
public class OpenModelBakery extends CustomModelBakery {


    // Due to access restrictions this class is essentially a copy of the one it extends :(

    static int indexCount = 0;
    static final Map<ResourceLocation, ClientModel> BAKED_MODELS = Maps.newHashMap();
    static final ModelBaker HEAD = new SimpleBaker(9);
    static final ModelBaker LEFT_FEET = new SimpleBaker(5);
    static final ModelBaker RIGHT_FEET = new SimpleBaker(2);
    static final ModelBaker LEFT_ARM = new Limb(16, 17, 19, 19.0F, false);
    static final ModelBaker LEFT_ARM_CHILD = new SimpleSeparateBaker(16, 17, 19.0F);
    static final ModelBaker RIGHT_ARM = new Limb(11, 12, 14, 19.0F, false);
    static final ModelBaker RIGHT_ARM_CHILD = new SimpleSeparateBaker(11, 12, 19.0F);
    static final ModelBaker LEFT_LEG = new Limb(4, 5, 6, 6.0F, true);
    static final ModelBaker LEFT_LEG_CHILD = new SimpleSeparateBaker(4, 5, 6.0F);
    static final ModelBaker RIGHT_LEG = new Limb(1, 2, 3, 6.0F, true);
    static final ModelBaker RIGHT_LEG_CHILD = new SimpleSeparateBaker(1, 2, 6.0F);
    static final ModelBaker CHEST = new Chest();
    static final ModelBaker CHEST_CHILD = new SimpleSeparateBaker(8, 7, 18.0F);


    // Another abolished rip of inner classes copied from epic fight. Truly despicable but it works.

    @OnlyIn(Dist.CLIENT)
    abstract static class ModelBaker {
        ModelBaker() {
        }

        public abstract void bakeCube(MatrixStack var1, ModelBox var2, List<CustomArmorVertex> var3, List<Integer> var4);
    }

    @OnlyIn(Dist.CLIENT)
    static class OpenModelPartition {
        final ModelBaker partBaker;
        final ModelBaker childBaker;
        final ModelRenderer part;

        private OpenModelPartition(ModelBaker partedBaker, ModelBaker childBaker, ModelRenderer modelRenderer) {
            this.partBaker = partedBaker;
            this.childBaker = childBaker;
            this.part = modelRenderer;
        }
    }

    static void resetRotation(ModelRenderer modelRenderer) {
        modelRenderer.xRot = 0.0F;
        modelRenderer.yRot = 0.0F;
        modelRenderer.zRot = 0.0F;
    }





    public static ClientModel bakeBipedCustomWearable(BipedModel<?> model, ResourceLocation item, boolean debuggingMode, ResourceLocation rl, LivingEntity entity) {
        List<OpenModelPartition> boxes = Lists.newArrayList();
        resetRotation(model.head);
        resetRotation(model.hat);
        resetRotation(model.body);
        resetRotation(model.rightArm);
        resetRotation(model.leftArm);
        resetRotation(model.rightLeg);
        resetRotation(model.leftLeg);

        if (model instanceof TaggedModel) {
            switch(((TaggedModel) model).getTag()) {
                case "blood_overlay":
                    boxes.add(new OpenModelPartition(CHEST, CHEST_CHILD, model.body));
                    boxes.add(new OpenModelPartition(LEFT_LEG, LEFT_LEG_CHILD, model.leftLeg));
                    boxes.add(new OpenModelPartition(RIGHT_LEG, RIGHT_LEG_CHILD, model.rightLeg));
                    boxes.add(new OpenModelPartition(RIGHT_ARM, LEFT_LEG_CHILD, model.rightArm));
                    boxes.add(new OpenModelPartition(LEFT_ARM, RIGHT_LEG_CHILD, model.leftArm));
                    boxes.add(new OpenModelPartition(HEAD, RIGHT_LEG_CHILD, model.head));


                    break;
            }
        }



        ClientModel customModel = new ClientModel(rl, bakeMeshFromCubes(boxes, debuggingMode));
        ClientModels.LOGICAL_CLIENT.register(rl, customModel);
        BAKED_MODELS.put(item, customModel);
        return customModel;
    }

    // More rips of existant classes. If only accesstransformers worked...


    private static Mesh bakeMeshFromCubes(List<OpenModelPartition> partitions, boolean debuggingMode) {
        List<CustomArmorVertex> vertices = Lists.newArrayList();
        List<Integer> indices = Lists.newArrayList();
        MatrixStack poseStack = new MatrixStack();
        indexCount = 0;
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        poseStack.translate(0.0, -24.0, 0.0);
        Iterator var5 = partitions.iterator();

        while(var5.hasNext()) {
            OpenModelPartition modelpartition = (OpenModelPartition)var5.next();
            bake(poseStack, modelpartition, modelpartition.part, modelpartition.partBaker, vertices, indices, debuggingMode);
        }

        return CustomArmorVertex.loadVertexInformation(vertices, ArrayUtils.toPrimitive((Integer[])indices.toArray(new Integer[0])));
    }

    private static void bake(MatrixStack poseStack, OpenModelPartition modelpartition, ModelRenderer part, ModelBaker partBaker, List<CustomArmorVertex> vertices, List<Integer> indices, boolean debuggingMode) {
        poseStack.pushPose();
        poseStack.translate((double)part.x, (double)part.y, (double)part.z);


        if (part.zRot != 0.0F) {
            poseStack.mulPose(Vector3f.ZP.rotation(part.zRot));
        }

        if (part.yRot != 0.0F) {
            poseStack.mulPose(Vector3f.YP.rotation(part.yRot));
        }

        if (part.xRot != 0.0F) {
            poseStack.mulPose(Vector3f.XP.rotation(part.xRot));
        }

        ObjectListIterator var7 = part.cubes.iterator();

        if (part.visible) {
            while(var7.hasNext()) {
                ModelBox cube = (ModelBox)var7.next();
                partBaker.bakeCube(poseStack, cube, vertices, indices);
            }

            var7 = part.children.iterator();

            while(var7.hasNext()) {
                ModelRenderer childParts = (ModelRenderer)var7.next();
                bake(poseStack, modelpartition, childParts, modelpartition.childBaker, vertices, indices, debuggingMode);
            }
        }




        poseStack.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    static class Limb extends ModelBaker {
        final int upperJoint;
        final int lowerJoint;
        final int middleJoint;
        final float yClipCoord;
        final boolean bendInFront;

        public Limb(int upperJoint, int lowerJoint, int middleJoint, float yClipCoord, boolean bendInFront) {
            this.upperJoint = upperJoint;
            this.lowerJoint = lowerJoint;
            this.middleJoint = middleJoint;
            this.yClipCoord = yClipCoord;
            this.bendInFront = bendInFront;
        }

        static PositionTextureVertex getTranslatedVertex(PositionTextureVertex original, Matrix4f matrix) {
            Vector4f translatedPosition = new Vector4f(original.pos);
            translatedPosition.transform(matrix);
            return new PositionTextureVertex(translatedPosition.x(), translatedPosition.y(), translatedPosition.z(), original.u, original.v);
        }

        static Direction getDirectionFromVector(Vector3f directionVec) {
            Direction[] var1 = Direction.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Direction direction = var1[var3];
                Vector3f direcVec = new Vector3f(Float.compare(directionVec.x(), -0.0F) == 0 ? 0.0F : directionVec.x(), directionVec.y(), directionVec.z());
                if (direcVec.equals(direction.step())) {
                    return direction;
                }
            }

            return null;
        }

        public void bakeCube(MatrixStack poseStack, ModelBox cube, List<CustomArmorVertex> vertices, List<Integer> indices) {
            List<AnimatedPolygon> polygons = Lists.newArrayList();
            TexturedQuad[] var6 = cube.polygons;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                TexturedQuad quad = var6[var8];
                Matrix4f matrix = poseStack.last().pose();
                PositionTextureVertex pos0 = getTranslatedVertex(quad.vertices[0], matrix);
                PositionTextureVertex pos1 = getTranslatedVertex(quad.vertices[1], matrix);
                PositionTextureVertex pos2 = getTranslatedVertex(quad.vertices[2], matrix);
                PositionTextureVertex pos3 = getTranslatedVertex(quad.vertices[3], matrix);
                Direction direction = getDirectionFromVector(quad.normal);
                if (pos1.pos.y() > this.yClipCoord != pos2.pos.y() > this.yClipCoord) {
                    float distance = pos2.pos.y() - pos1.pos.y();
                    float textureV = pos1.v + (pos2.v - pos1.v) * ((this.yClipCoord - pos1.pos.y()) / distance);
                    PositionTextureVertex pos4 = new PositionTextureVertex(pos0.pos.x(), this.yClipCoord, pos0.pos.z(), pos0.u, textureV);
                    PositionTextureVertex pos5 = new PositionTextureVertex(pos1.pos.x(), this.yClipCoord, pos1.pos.z(), pos1.u, textureV);
                    int upperId;
                    int lowerId;
                    if (distance > 0.0F) {
                        upperId = this.lowerJoint;
                        lowerId = this.upperJoint;
                    } else {
                        upperId = this.upperJoint;
                        lowerId = this.lowerJoint;
                    }

                    polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos0, upperId), new AnimatedVertex(pos1, upperId), new AnimatedVertex(pos5, upperId), new AnimatedVertex(pos4, upperId)}, direction));
                    polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos4, lowerId), new AnimatedVertex(pos5, lowerId), new AnimatedVertex(pos2, lowerId), new AnimatedVertex(pos3, lowerId)}, direction));
                    boolean hasSameZ = pos4.pos.z() < 0.0F == pos5.pos.z() < 0.0F;
                    boolean isFront = hasSameZ && pos4.pos.z() < 0.0F == this.bendInFront;
                    if (isFront) {
                        polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos4, this.middleJoint), new AnimatedVertex(pos5, this.middleJoint), new AnimatedVertex(pos5, this.upperJoint), new AnimatedVertex(pos4, this.upperJoint)}, 0.001F, direction));
                        polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos4, this.lowerJoint), new AnimatedVertex(pos5, this.lowerJoint), new AnimatedVertex(pos5, this.middleJoint), new AnimatedVertex(pos4, this.middleJoint)}, 0.001F, direction));
                    } else if (!hasSameZ) {
                        boolean startFront = pos4.pos.z() > 0.0F;
                        int firstJoint = this.lowerJoint;
                        int secondJoint = this.lowerJoint;
                        int thirdJoint = startFront ? this.upperJoint : this.middleJoint;
                        int fourthJoint = startFront ? this.middleJoint : this.upperJoint;
                        int fifthJoint = this.upperJoint;
                        int sixthJoint = this.upperJoint;
                        polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos4, firstJoint), new AnimatedVertex(pos5, secondJoint), new AnimatedVertex(pos5, thirdJoint), new AnimatedVertex(pos4, fourthJoint)}, 0.001F, direction));
                        polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos4, fourthJoint), new AnimatedVertex(pos5, thirdJoint), new AnimatedVertex(pos5, fifthJoint), new AnimatedVertex(pos4, sixthJoint)}, 0.001F, direction));
                    }
                } else {
                    int jointId = pos0.pos.y() > this.yClipCoord ? this.upperJoint : this.lowerJoint;
                    polygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos0, jointId), new AnimatedVertex(pos1, jointId), new AnimatedVertex(pos2, jointId), new AnimatedVertex(pos3, jointId)}, direction));
                }
            }

            for(Iterator var31 = polygons.iterator(); var31.hasNext(); indexCount += 4) {
                AnimatedPolygon quad = (AnimatedPolygon)var31.next();
                Vector3f norm = quad.normal.copy();
                norm.transform(poseStack.last().normal());
                AnimatedVertex[] var34 = quad.animatedVertexPositions;
                int var35 = var34.length;

                for(int var36 = 0; var36 < var35; ++var36) {
                    AnimatedVertex vertex = var34[var36];
                    Vector4f pos = new Vector4f(vertex.pos);
                    vertices.add((new CustomArmorVertex()).setPosition((new Vec3f(pos.x(), pos.y(), pos.z())).scale(0.0625F)).setNormal(new Vec3f(norm.x(), norm.y(), norm.z())).setTextureCoordinate(new Vec2f(vertex.u, vertex.v)).setEffectiveJointIDs(new Vec3f((float)vertex.jointId.getX(), 0.0F, 0.0F)).setEffectiveJointWeights(new Vec3f(1.0F, 0.0F, 0.0F)).setEffectiveJointNumber(1));
                }

                putIndexCount(indices, indexCount);
                putIndexCount(indices, indexCount + 1);
                putIndexCount(indices, indexCount + 3);
                putIndexCount(indices, indexCount + 3);
                putIndexCount(indices, indexCount + 1);
                putIndexCount(indices, indexCount + 2);
            }

        }
    }

    static void putIndexCount(List<Integer> indices, int value) {
        for(int i = 0; i < 3; ++i) {
            indices.add(value);
        }

    }

    @OnlyIn(Dist.CLIENT)
    static class Chest extends ModelBaker {
        static final float X_PLANE = 0.0F;
        static final VertexWeight[] WEIGHT_ALONG_Y = new VertexWeight[]{new VertexWeight(13.6666F, 0.23F, 0.77F), new VertexWeight(15.8333F, 0.254F, 0.746F), new VertexWeight(18.0F, 0.5F, 0.5F), new VertexWeight(20.1666F, 0.744F, 0.256F), new VertexWeight(22.3333F, 0.77F, 0.23F)};

        Chest() {
        }

        public void bakeCube(MatrixStack poseStack, ModelBox cube, List<CustomArmorVertex> vertices, List<Integer> indices) {
            List<AnimatedPolygon> xClipPolygons = Lists.newArrayList();
            List<AnimatedPolygon> xyClipPolygons = Lists.newArrayList();
            TexturedQuad[] var7 = cube.polygons;
            int var8 = var7.length;

            VertexWeight vertexWeight;
            for(int var9 = 0; var9 < var8; ++var9) {
                TexturedQuad polygon = var7[var9];
                Matrix4f matrix = poseStack.last().pose();
                PositionTextureVertex pos0 = Limb.getTranslatedVertex(polygon.vertices[0], matrix);
                PositionTextureVertex pos1 = Limb.getTranslatedVertex(polygon.vertices[1], matrix);
                PositionTextureVertex pos2 = Limb.getTranslatedVertex(polygon.vertices[2], matrix);
                PositionTextureVertex pos3 = Limb.getTranslatedVertex(polygon.vertices[3], matrix);
                Direction direction = Limb.getDirectionFromVector(polygon.normal);
                VertexWeight pos0Weight = getYClipWeight(pos0.pos.y());
                vertexWeight = getYClipWeight(pos1.pos.y());
                VertexWeight pos2Weight = getYClipWeight(pos2.pos.y());
                VertexWeight pos3Weight = getYClipWeight(pos3.pos.y());
                if (pos1.pos.x() > 0.0F != pos2.pos.x() > 0.0F) {
                    float distance = pos2.pos.x() - pos1.pos.x();
                    float textureU = pos1.u + (pos2.u - pos1.u) * ((0.0F - pos1.pos.x()) / distance);
                    PositionTextureVertex pos4 = new PositionTextureVertex(0.0F, pos0.pos.y(), pos0.pos.z(), textureU, pos0.v);
                    PositionTextureVertex pos5 = new PositionTextureVertex(0.0F, pos1.pos.y(), pos1.pos.z(), textureU, pos1.v);
                    xClipPolygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos0, 8, 7, 0, pos0Weight.chestWeight, pos0Weight.torsoWeight, 0.0F), new AnimatedVertex(pos4, 8, 7, 0, pos0Weight.chestWeight, pos0Weight.torsoWeight, 0.0F), new AnimatedVertex(pos5, 8, 7, 0, vertexWeight.chestWeight, vertexWeight.torsoWeight, 0.0F), new AnimatedVertex(pos3, 8, 7, 0, pos3Weight.chestWeight, pos3Weight.torsoWeight, 0.0F)}, direction));
                    xClipPolygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos4, 8, 7, 0, pos0Weight.chestWeight, pos0Weight.torsoWeight, 0.0F), new AnimatedVertex(pos1, 8, 7, 0, vertexWeight.chestWeight, vertexWeight.torsoWeight, 0.0F), new AnimatedVertex(pos2, 8, 7, 0, pos2Weight.chestWeight, pos2Weight.torsoWeight, 0.0F), new AnimatedVertex(pos5, 8, 7, 0, vertexWeight.chestWeight, vertexWeight.torsoWeight, 0.0F)}, direction));
                } else {
                    xClipPolygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(pos0, 8, 7, 0, pos0Weight.chestWeight, pos0Weight.torsoWeight, 0.0F), new AnimatedVertex(pos1, 8, 7, 0, vertexWeight.chestWeight, vertexWeight.torsoWeight, 0.0F), new AnimatedVertex(pos2, 8, 7, 0, pos2Weight.chestWeight, pos2Weight.torsoWeight, 0.0F), new AnimatedVertex(pos3, 8, 7, 0, pos3Weight.chestWeight, pos3Weight.torsoWeight, 0.0F)}, direction));
                }
            }

            Iterator var25 = xClipPolygons.iterator();

            AnimatedPolygon polygon;
            AnimatedVertex vertex;
            int joint1;
            int joint2;
            while(var25.hasNext()) {
                polygon = (AnimatedPolygon)var25.next();
                boolean upsideDown = polygon.animatedVertexPositions[1].pos.y() > polygon.animatedVertexPositions[2].pos.y();
                AnimatedVertex pos0 = upsideDown ? polygon.animatedVertexPositions[2] : polygon.animatedVertexPositions[0];
                AnimatedVertex pos1 = upsideDown ? polygon.animatedVertexPositions[3] : polygon.animatedVertexPositions[1];
                AnimatedVertex pos2 = upsideDown ? polygon.animatedVertexPositions[0] : polygon.animatedVertexPositions[2];
                vertex = upsideDown ? polygon.animatedVertexPositions[1] : polygon.animatedVertexPositions[3];
                Direction direction = Limb.getDirectionFromVector(polygon.normal);
                List<VertexWeight> vertexWeights = getMiddleYClipWeights(pos1.pos.y(), pos2.pos.y());
                List<AnimatedVertex> animatedVertices = Lists.newArrayList();
                animatedVertices.add(pos0);
                animatedVertices.add(pos1);
                if (vertexWeights.size() > 0) {
                    Iterator var42 = vertexWeights.iterator();

                    while(var42.hasNext()) {
                        vertexWeight = (VertexWeight)var42.next();
                        float distance = pos2.pos.y() - pos1.pos.y();
                        float textureV = pos1.v + (pos2.v - pos1.v) * ((vertexWeight.yClipCoord - pos1.pos.y()) / distance);
                        PositionTextureVertex pos4 = new PositionTextureVertex(pos0.pos.x(), vertexWeight.yClipCoord, pos0.pos.z(), pos0.u, textureV);
                        PositionTextureVertex pos5 = new PositionTextureVertex(pos1.pos.x(), vertexWeight.yClipCoord, pos1.pos.z(), pos1.u, textureV);
                        animatedVertices.add(new AnimatedVertex(pos4, 8, 7, 0, vertexWeight.chestWeight, vertexWeight.torsoWeight, 0.0F));
                        animatedVertices.add(new AnimatedVertex(pos5, 8, 7, 0, vertexWeight.chestWeight, vertexWeight.torsoWeight, 0.0F));
                    }
                }

                animatedVertices.add(vertex);
                animatedVertices.add(pos2);

                for(joint1 = 0; joint1 < (animatedVertices.size() - 2) / 2; ++joint1) {
                    joint2 = joint1 * 2;
                    AnimatedVertex p0 = (AnimatedVertex)animatedVertices.get(joint2);
                    AnimatedVertex p1 = (AnimatedVertex)animatedVertices.get(joint2 + 1);
                    AnimatedVertex p2 = (AnimatedVertex)animatedVertices.get(joint2 + 3);
                    AnimatedVertex p3 = (AnimatedVertex)animatedVertices.get(joint2 + 2);
                    xyClipPolygons.add(new AnimatedPolygon(new AnimatedVertex[]{new AnimatedVertex(p0, 8, 7, 0, p0.weight.x, p0.weight.y, 0.0F), new AnimatedVertex(p1, 8, 7, 0, p1.weight.x, p1.weight.y, 0.0F), new AnimatedVertex(p2, 8, 7, 0, p2.weight.x, p2.weight.y, 0.0F), new AnimatedVertex(p3, 8, 7, 0, p3.weight.x, p3.weight.y, 0.0F)}, direction));
                }
            }

            for(var25 = xyClipPolygons.iterator(); var25.hasNext(); indexCount += 4) {
                polygon = (AnimatedPolygon)var25.next();
                Vector3f norm = polygon.normal.copy();
                norm.transform(poseStack.last().normal());
                AnimatedVertex[] var30 = polygon.animatedVertexPositions;
                int var32 = var30.length;

                for(int var34 = 0; var34 < var32; ++var34) {
                    vertex = var30[var34];
                    Vector4f pos = new Vector4f(vertex.pos);
                    float weight1 = vertex.weight.x;
                    float weight2 = vertex.weight.y;
                    joint1 = vertex.jointId.getX();
                    joint2 = vertex.jointId.getY();
                    int count = weight1 > 0.0F && weight2 > 0.0F ? 2 : 1;
                    if (weight1 <= 0.0F) {
                        joint1 = joint2;
                        weight1 = weight2;
                    }

                    vertices.add((new CustomArmorVertex()).setPosition((new Vec3f(pos.x(), pos.y(), pos.z())).scale(0.0625F)).setNormal(new Vec3f(norm.x(), norm.y(), norm.z())).setTextureCoordinate(new Vec2f(vertex.u, vertex.v)).setEffectiveJointIDs(new Vec3f((float)joint1, (float)joint2, 0.0F)).setEffectiveJointWeights(new Vec3f(weight1, weight2, 0.0F)).setEffectiveJointNumber(count));
                }

                putIndexCount(indices, indexCount);
                putIndexCount(indices, indexCount + 1);
                putIndexCount(indices, indexCount + 3);
                putIndexCount(indices, indexCount + 3);
                putIndexCount(indices, indexCount + 1);
                putIndexCount(indices, indexCount + 2);
            }

        }

        static VertexWeight getYClipWeight(float y) {
            if (y < WEIGHT_ALONG_Y[0].yClipCoord) {
                return new VertexWeight(y, 0.0F, 1.0F);
            } else {
                int index = -1;

                for(int i = 0; i < WEIGHT_ALONG_Y.length; ++i) {
                }

                if (index > 0) {
                    VertexWeight pair = WEIGHT_ALONG_Y[index];
                    return new VertexWeight(y, pair.chestWeight, pair.torsoWeight);
                } else {
                    return new VertexWeight(y, 1.0F, 0.0F);
                }
            }
        }

        static List<VertexWeight> getMiddleYClipWeights(float minY, float maxY) {
            List<VertexWeight> cutYs = Lists.newArrayList();
            VertexWeight[] var3 = WEIGHT_ALONG_Y;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                VertexWeight vertexWeight = var3[var5];
                if (vertexWeight.yClipCoord > minY && maxY >= vertexWeight.yClipCoord) {
                    cutYs.add(vertexWeight);
                }
            }

            return cutYs;
        }

        static class VertexWeight {
            final float yClipCoord;
            final float chestWeight;
            final float torsoWeight;

            public VertexWeight(float yClipCoord, float chestWeight, float torsoWeight) {
                this.yClipCoord = yClipCoord;
                this.chestWeight = chestWeight;
                this.torsoWeight = torsoWeight;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class AnimatedPolygon {
        public final AnimatedVertex[] animatedVertexPositions;
        public final Vector3f normal;

        public AnimatedPolygon(AnimatedVertex[] positionsIn, Direction directionIn) {
            this.animatedVertexPositions = positionsIn;
            this.normal = directionIn.step();
        }

        public AnimatedPolygon(AnimatedVertex[] positionsIn, float cor, Direction directionIn) {
            this.animatedVertexPositions = positionsIn;
            positionsIn[0] = new AnimatedVertex(positionsIn[0], positionsIn[0].u, positionsIn[0].v + cor, positionsIn[0].jointId, positionsIn[0].weight);
            positionsIn[1] = new AnimatedVertex(positionsIn[1], positionsIn[1].u, positionsIn[1].v + cor, positionsIn[1].jointId, positionsIn[1].weight);
            positionsIn[2] = new AnimatedVertex(positionsIn[2], positionsIn[2].u, positionsIn[2].v - cor, positionsIn[2].jointId, positionsIn[2].weight);
            positionsIn[3] = new AnimatedVertex(positionsIn[3], positionsIn[3].u, positionsIn[3].v - cor, positionsIn[3].jointId, positionsIn[3].weight);
            this.normal = directionIn.step();
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class AnimatedVertex extends PositionTextureVertex {
        final Vector3i jointId;
        final Vec3f weight;

        public AnimatedVertex(PositionTextureVertex posTexVertx, int jointId) {
            this(posTexVertx, jointId, 0, 0, 1.0F, 0.0F, 0.0F);
        }

        public AnimatedVertex(PositionTextureVertex posTexVertx, int jointId1, int jointId2, int jointId3, float weight1, float weight2, float weight3) {
            this(posTexVertx, new Vector3i(jointId1, jointId2, jointId3), new Vec3f(weight1, weight2, weight3));
        }

        public AnimatedVertex(PositionTextureVertex posTexVertx, Vector3i ids, Vec3f weights) {
            this(posTexVertx, posTexVertx.u, posTexVertx.v, ids, weights);
        }

        public AnimatedVertex(PositionTextureVertex posTexVertx, float u, float v, Vector3i ids, Vec3f weights) {
            super(posTexVertx.pos.x(), posTexVertx.pos.y(), posTexVertx.pos.z(), u, v);
            this.jointId = ids;
            this.weight = weights;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class SimpleSeparateBaker extends ModelBaker {
        final SimpleBaker upperBaker;
        final SimpleBaker lowerBaker;
        final float yClipCoord;

        public SimpleSeparateBaker(int upperJoint, int lowerJoint, float yClipCoord) {
            this.upperBaker = new SimpleBaker(upperJoint);
            this.lowerBaker = new SimpleBaker(lowerJoint);
            this.yClipCoord = yClipCoord;
        }

        public void bakeCube(MatrixStack poseStack, ModelBox cube, List<CustomArmorVertex> vertices, List<Integer> indices) {
            Vector4f cubeCenter = new Vector4f(cube.minX + (cube.maxX - cube.minX) * 0.5F, cube.minY + (cube.maxY - cube.minY) * 0.5F, cube.minZ + (cube.maxZ - cube.minZ) * 0.5F, 1.0F);
            cubeCenter.transform(poseStack.last().pose());
            if (cubeCenter.y() > this.yClipCoord) {
                this.upperBaker.bakeCube(poseStack, cube, vertices, indices);
            } else {
                this.lowerBaker.bakeCube(poseStack, cube, vertices, indices);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class SimpleBaker extends ModelBaker {
        final int jointId;

        public SimpleBaker(int jointId) {
            this.jointId = jointId;
        }

        public void bakeCube(MatrixStack poseStack, ModelBox cube, List<CustomArmorVertex> vertices, List<Integer> indices) {
            TexturedQuad[] var5 = cube.polygons;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                TexturedQuad quad = var5[var7];
                Vector3f norm = quad.normal.copy();
                norm.transform(poseStack.last().normal());
                PositionTextureVertex[] var10 = quad.vertices;
                int var11 = var10.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    PositionTextureVertex vertex = var10[var12];
                    Vector4f pos = new Vector4f(vertex.pos);
                    pos.transform(poseStack.last().pose());
                    vertices.add((new CustomArmorVertex()).setPosition((new Vec3f(pos.x(), pos.y(), pos.z())).scale(0.0625F)).setNormal(new Vec3f(norm.x(), norm.y(), norm.z())).setTextureCoordinate(new Vec2f(vertex.u, vertex.v)).setEffectiveJointIDs(new Vec3f((float)this.jointId, 0.0F, 0.0F)).setEffectiveJointWeights(new Vec3f(1.0F, 0.0F, 0.0F)).setEffectiveJointNumber(1));
                }

                putIndexCount(indices, indexCount);
                putIndexCount(indices, indexCount + 1);
                putIndexCount(indices, indexCount + 3);
                putIndexCount(indices, indexCount + 3);
                putIndexCount(indices, indexCount + 1);
                putIndexCount(indices, indexCount + 2);
                indexCount += 4;
            }

        }
    }
}
