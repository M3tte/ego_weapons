package net.m3tte.ego_weapons.item.guns;

import net.minecraft.util.ResourceLocation;

public enum GunCaliber {
    NONE("desc.ego_weapons.ammo.none", new ResourceLocation("ego_weapons","textures/items/bullets/basic_light_bullet.png")),
    LIGHT("desc.ego_weapons.ammo.light", new ResourceLocation("ego_weapons","textures/items/bullets/basic_light_bullet.png")),
    RIFLE("desc.ego_weapons.ammo.rifle", new ResourceLocation("ego_weapons","textures/items/bullets/basic_rifle_bullet.png")),
    UNIQUE("desc.ego_weapons.ammo.unique", new ResourceLocation("ego_weapons","textures/items/bullets/basic_light_bullet.png"));


    private String caliberIdentifier = "none";
    private ResourceLocation caliberIcon = null;

    public String getCaliber() {
        return caliberIdentifier;
    }

    public ResourceLocation getCaliberIcon() {
        return caliberIcon;
    }

    public String getCaliberIdentifier() {
        return caliberIdentifier;
    }

    GunCaliber(String caliberIdentifier, ResourceLocation caliberIcon) {
        this.caliberIdentifier = caliberIdentifier;
        this.caliberIcon = caliberIcon;
    }
}
