package net.m3tte.ego_weapons.client.renderer;

import net.minecraft.client.Minecraft;

public class ResponsiveDynamicFramebuffer extends DynamicFramebufferDef {
    private float baseHeightValue = 0;
    private float responsiveIncrements = 0;
    private float minimumMult = 1;

    // Base monitor height is 1080P - Best incremented in 500 or so
    public ResponsiveDynamicFramebuffer(float baseheightDiv, float basewidthDiv, String name, float baseHeightValue, float responsiveIncrements, float minimumMult) {
        super(baseheightDiv, basewidthDiv, name);
        this.baseHeightValue = baseHeightValue;
        this.responsiveIncrements = responsiveIncrements;
        this.minimumMult = minimumMult;
    }

    public float getResponsiveDivisor() {
        int height = Minecraft.getInstance().getMainRenderTarget().height;

        return (height - this.baseHeightValue) / responsiveIncrements;
    }

    public float getHeightDiv() {
        return Math.max(this.minimumMult,super.getHeightDiv() + Math.round(getResponsiveDivisor()));
    }

    public float getWidthDiv() {
        return Math.max(this.minimumMult,super.getWidthDiv() + Math.round(getResponsiveDivisor()));
    }
}
