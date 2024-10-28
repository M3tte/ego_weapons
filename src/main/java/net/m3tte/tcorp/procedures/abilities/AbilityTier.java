package net.m3tte.tcorp.procedures.abilities;

import net.m3tte.tcorp.TcorpMod;
import net.minecraft.util.ResourceLocation;

public enum AbilityTier {
    OMEGA(new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/beta.png")),
    ZETA(new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/zeta.png")),
    DELTA(new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/delta.png")),
    GAMMA(new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/gamma.png")),
    BETA(new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/beta.png")),
    ALPHA(new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/alpha.png"));


    public static ResourceLocation baseBG = new ResourceLocation(TcorpMod.MODID, "textures/screens/abilities/bg/base.png");
    private ResourceLocation background;


    public ResourceLocation getBackground() {
        return background;
    }

    AbilityTier(ResourceLocation background) {
        this.background = background;
    }
}
