package net.m3tte.ego_weapons.procedures.abilities;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.util.ResourceLocation;

public enum AbilityTier {
    OMEGA(new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/beta.png")),
    ZETA(new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/zeta.png")),
    DELTA(new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/delta.png")),
    GAMMA(new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/gamma.png")),
    BETA(new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/beta.png")),
    ALPHA(new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/alpha.png"));


    public static ResourceLocation baseBG = new ResourceLocation(EgoWeaponsMod.MODID, "textures/screens/abilities/bg/base.png");
    private ResourceLocation background;


    public ResourceLocation getBackground() {
        return background;
    }

    AbilityTier(ResourceLocation background) {
        this.background = background;
    }
}
