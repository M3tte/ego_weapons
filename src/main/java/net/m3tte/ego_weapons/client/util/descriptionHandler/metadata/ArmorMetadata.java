package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

public class ArmorMetadata extends PlaceholderMetadata {
    float defenseLevel = 0;
    float redRes = 1;
    float whiteRes = 1;
    float blackRes = 1;
    float paleRes = 1;

    float slashRes = 1;
    float pierceRes = 1;
    float bluntRes = 1;

    public ArmorMetadata(String itemIdentifier, float defenseLevel, float redRes, float whiteRes, float blackRes, float paleRes, float slashRes, float pierceRes, float bluntRes) {
        super(itemIdentifier);
        this.defenseLevel = defenseLevel;
        this.redRes = redRes;
        this.whiteRes = whiteRes;
        this.blackRes = blackRes;
        this.paleRes = paleRes;
        this.slashRes = slashRes;
        this.pierceRes = pierceRes;
        this.bluntRes = bluntRes;
    }

    public float getDefenseLevel() {
        return defenseLevel;
    }

    public float getRedRes() {
        return redRes;
    }

    public float getWhiteRes() {
        return whiteRes;
    }

    public float getBlackRes() {
        return blackRes;
    }

    public float getPaleRes() {
        return paleRes;
    }

    public float getSlashRes() {
        return slashRes;
    }

    public float getPierceRes() {
        return pierceRes;
    }

    public float getBluntRes() {
        return bluntRes;
    }
}
