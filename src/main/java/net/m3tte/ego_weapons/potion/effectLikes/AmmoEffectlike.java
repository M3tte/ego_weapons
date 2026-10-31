package net.m3tte.ego_weapons.potion.effectLikes;

import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.minecraft.util.ResourceLocation;

public class AmmoEffectlike implements EffectLike {
    @Override
    public String getName() {
        return "desc.ego_weapons.statuses.ammo";
    }

    ResourceLocation iconLoc = new ResourceLocation("ego_weapons", "textures/mob_effect/ammo.png");
    @Override
    public ResourceLocation getIconLoc() {
        return iconLoc;
    }
}
