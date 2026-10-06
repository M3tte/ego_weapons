package net.m3tte.ego_weapons.client.renderer;

public class DynamicFramebufferDef {
    private float heightDiv = 1;
    private float widthDiv = 1;
    private String name = "";

    public DynamicFramebufferDef(float heightDiv, float widthDiv, String name) {
        this.heightDiv = heightDiv;
        this.widthDiv = widthDiv;
        this.name = name;
    }



    public float getHeightDiv() {
        return heightDiv;
    }

    public float getWidthDiv() {
        return widthDiv;
    }

    public String getName() {
        return name;
    }
}
