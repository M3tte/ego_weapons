package net.m3tte.ego_weapons.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public interface OnHitOnKillEntity {
    float onHit(LivingEntity target, DamageSource source, float amount, float mult);

    void onKill(LivingEntity target);
}
