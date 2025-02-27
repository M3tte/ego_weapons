package net.m3tte.ego_weapons.specialParticles.numberParticle;

public enum NumberParticleTypes {
    BURN(16742400, "\uE002"),
    DARK_FLAME(2660, "\uE009"),
    RUPTURE(52600, "\uE005"),
    BLEED(11796480, "\uE001"),
    SINKING(3947660, "\uE003"),
    TREMOR(12291840, "\uE006");


    NumberParticleTypes(int color, String icon) {
        this.color = color;
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }


    int color;
    String icon;
}
