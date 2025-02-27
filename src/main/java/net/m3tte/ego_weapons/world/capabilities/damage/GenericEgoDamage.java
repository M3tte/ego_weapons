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
        BLUNT("\uED23");

        String icon;

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
