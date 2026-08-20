package net.m3tte.ego_weapons.client.renderer;

import net.minecraft.entity.Entity;

public class EntityShake {


    public static float evaluateShakeFromValue(float value) {
        return (float) (Math.sin(value*38)*value) * 0.13f;
    }

}
