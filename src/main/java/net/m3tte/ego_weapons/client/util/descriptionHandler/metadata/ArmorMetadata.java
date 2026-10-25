package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.minecraft.item.Item;

public class ArmorMetadata extends ItemMetadata {
    private float defenseLevel = 0;
    private float redRes = 1;
    private float whiteRes = 1;
    private float blackRes = 1;
    private float paleRes = 1;

    private float slashRes = 1;
    private float pierceRes = 1;
    private float bluntRes = 1;
    private float bonusSanity = 0;
    private float bonusStagger = 0;

    public ArmorMetadata(String itemIdentifier, Item item, CountPotencyStatus[] keywords, float defenseLevel, float redRes, float whiteRes, float blackRes, float paleRes, float slashRes, float pierceRes, float bluntRes, float bonusSanity, float bonusStagger) {
        super(itemIdentifier, item, keywords);
        this.defenseLevel = defenseLevel;
        this.redRes = redRes;
        this.whiteRes = whiteRes;
        this.blackRes = blackRes;
        this.paleRes = paleRes;
        this.slashRes = slashRes;
        this.pierceRes = pierceRes;
        this.bluntRes = bluntRes;
        this.bonusSanity = bonusSanity;
        this.bonusStagger = bonusStagger;
    }

    public float getBonusSanity() {
        return bonusSanity;
    }

    public float getBonusStagger() {
        return bonusStagger;
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
