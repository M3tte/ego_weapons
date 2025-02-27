package net.m3tte.ego_weapons.client.renderer;

import net.minecraft.entity.Entity;

public class EntityShake {


    public static float evaluateShakeFromValue(float value) {
        return (float) (Math.sin(value*33)*value) * 0.1f;
    }

}
