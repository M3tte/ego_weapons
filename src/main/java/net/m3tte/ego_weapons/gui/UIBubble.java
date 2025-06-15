package net.m3tte.ego_weapons.gui;

import net.minecraft.util.ResourceLocation;

public class UIBubble {
    int locationX;
    int locationY;
    int sizeX;
    int sizeY;

    int texX;
    int texY;

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }


    public int getTexX() {
        return texX;
    }

    public void setTexX(int texX) {
        this.texX = texX;
    }

    public int getTexY() {
        return texY;
    }

    public void setTexY(int texY) {
        this.texY = texY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public UIBubble(int locationX, int locationY, int sizeX, int sizeY, int texX, int texY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.texX = texX;
        this.texY = texY;
    }
}
