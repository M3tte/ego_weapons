package net.m3tte.ego_weapons.world.capabilities.damage;

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
        GENERIC(""),
        SLASH("\uED22"),
        PIERCE("\uED21"),
        BLUNT("\uED23"),
        HIDDEN("");

        String icon;

        public static AttackTypes fromString(String str) {
            switch (str) {
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

        AttackTypes(String icon) {
            this.icon = icon;
        }
    }

    enum DamageTypes {
        RED("\uED01", "\uED11"),
        WHITE("\uED02", "\uED12"),
        BLACK("\uED03", "\uED13"),
        PALE("\uED04", "\uED14");

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

        DamageTypes(String icon, String damageBorder) {
            this.icon = icon;
            this.damageBorder = damageBorder;
        }
    }
}
