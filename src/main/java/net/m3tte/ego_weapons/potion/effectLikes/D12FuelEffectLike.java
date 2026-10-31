package net.m3tte.ego_weapons.potion.effectLikes;

import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.minecraft.util.ResourceLocation;

public class D12FuelEffectLike implements EffectLike {
    @Override
    public String getName() {
        return "desc.ego_weapons.statuses.d12fuel";
    }

    ResourceLocation iconLoc = new ResourceLocation("ego_weapons", "textures/mob_effect/district_12_fuel.png");
    @Override
    public ResourceLocation getIconLoc() {
        return iconLoc;
    }
}
