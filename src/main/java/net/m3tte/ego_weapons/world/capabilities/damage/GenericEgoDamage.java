package net.m3tte.ego_weapons.world.capabilities.damage;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.util.ResourceLocation;

public interface GenericEgoDamage {

    AttackTypes getAttackType();
    DamageTypes getDamageType();

    float getResistanceMult();
    void setResistanceMult(float mult);

    float getBonusMult();
    void setBonusMult(float mult);

    boolean getCrit();
    void setCrit(boolean crit);


    void setConsumesStatus(boolean stat);
    boolean getConsumesStatus();

    enum AttackTypes {
        GENERIC("", null),
        SLASH("\uED22", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/slash.png")),
        PIERCE("\uED21", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/pierce.png")),
        BLUNT("\uED23", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/blunt.png")),
        HIDDEN("", null);

        String icon;
        ResourceLocation iconLoc;

        public static AttackTypes fromString(String str) {
            switch (str.toLowerCase()) {
                case "generic":
                    return GENERIC;
                case "slash":
                    return SLASH;
                case "pierce":
                    return PIERCE;
                case "blunt":
                    return BLUNT;
            }
            return null;
        }
        public String getIcon() {
            return icon;
        }

        public ResourceLocation getIconLoc() {
            return iconLoc;
        }

        AttackTypes(String icon, ResourceLocation iconLoc) {
            this.icon = icon;
            this.iconLoc = iconLoc;
        }
    }

    enum DamageTypes {
        RED("\uED01", "\uED11", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/red.png")),
        WHITE("\uED02", "\uED12", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/white.png")),
        BLACK("\uED03", "\uED13", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/black.png")),
        PALE("\uED04", "\uED14", new ResourceLocation(EgoWeaponsMod.MODID, "textures/damage_types/icons/pale.png"));

        String icon;
        String damageBorder;


        public static DamageTypes fromString(String str) {
            switch (str) {
                case "red":
                    return RED;
                case "white":
                    return WHITE;
                case "black":
                    return BLACK;
                case "pale":
                    return PALE;
            }
            return null;
        }

        public String getIcon() {
            return icon;
        }

        public String getDamageBorder() {
            return damageBorder;
        }

        public ResourceLocation getIconLoc() {
            return iconLoc;
        }

        ResourceLocation iconLoc;
        DamageTypes(String icon, String damageBorder, ResourceLocation iconLoc) {
            this.icon = icon;
            this.damageBorder = damageBorder;
            this.iconLoc = iconLoc;
        }
    }
}
