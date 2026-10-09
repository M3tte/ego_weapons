package net.m3tte.ego_weapons.world.capabilities.threatlevel;

public enum ThreatLevels {
    ZAYIN,
    TETH,
    HE,
    WAW,
    ALEPH;

    public static ThreatLevels getFromString(String str) {
        switch (str) {
            case "ZAYIN": return ZAYIN;
            case "TETH": return TETH;
            case "HE": return HE;
            case "WAW": return WAW;
            case "ALEPH": return ALEPH;
        }

        return null;
    }
}
