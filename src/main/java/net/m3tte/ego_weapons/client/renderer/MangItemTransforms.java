package net.m3tte.ego_weapons.client.renderer;

import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3f;

import java.util.HashMap;

public class MangItemTransforms {

    private Vector3f rotationOffs = null;
    private Vector3f positionOffs = null;
    private Hand hand;

    public Vector3f getRotationOffs() {
        return rotationOffs;
    }

    public Vector3f getPositionOffs() {
        return positionOffs;
    }

    public MangItemTransforms(Vector3f rotationOffs, Vector3f positionOffs, Hand hand) {
        this.rotationOffs = rotationOffs;
        this.positionOffs = positionOffs;
    }

    public static MangItemTransforms BASE_TRANSFORM = new MangItemTransforms(new Vector3f(0,0,0),new Vector3f(0,0,0), Hand.MAIN_HAND);
    public static MangItemTransforms TOOL_TRANSFORM = new MangItemTransforms(new Vector3f(110,0,0),new Vector3f(0,12,-16), Hand.MAIN_HAND);
    public static MangItemTransforms DEF_TOOL_TRANSFORM = new MangItemTransforms(new Vector3f(90,0,0),new Vector3f(0,8,-16), Hand.MAIN_HAND);
    private static HashMap<String, MangItemTransforms> SAVED_TRANSFORMS;

    public static HashMap<String, MangItemTransforms> getSavedTransforms() {
        if (SAVED_TRANSFORMS == null) {
            SAVED_TRANSFORMS = new HashMap<>();
            populateTransforms();
        }

        return SAVED_TRANSFORMS;
    }
    public static void populateTransforms() {

    }
}
