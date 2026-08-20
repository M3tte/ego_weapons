package net.m3tte.ego_weapons.item.guns;

public enum GunCaliber {
    NONE("desc.ego_weapons.ammo.none"),
    LIGHT("desc.ego_weapons.ammo.light"),
    RIFLE("desc.ego_weapons.ammo.rifle");


    private String caliberIdentifier = "none";

    public String getCaliber() {
        return caliberIdentifier;
    }

    GunCaliber(String caliberIdentifier) {
        this.caliberIdentifier = caliberIdentifier;
    }
}
