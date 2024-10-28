package net.m3tte.tcorp.world.capabilities.entitypatch;

import yesman.epicfight.api.animation.types.StaticAnimation;

public interface StaggerableEntity {

    // This animation is to return a static animation that is designated as the stagger animation for the respective entity.
    public abstract StaticAnimation getStaggerAnimation();

    public abstract StaticAnimation getGroundAnimation(int strength);

    public abstract StaticAnimation getLiftAnimation(int strength);
}
