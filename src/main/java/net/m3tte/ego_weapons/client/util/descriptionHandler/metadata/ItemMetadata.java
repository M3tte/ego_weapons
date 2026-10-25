package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.List;

public class ItemMetadata extends PlaceholderMetadata {
    private Item targetItem = null;
    private CountPotencyStatus[] keywords = null;

    public CountPotencyStatus[] getKeywords() {
        return keywords;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public ItemMetadata(String itemIdentifier, Item targetItem, CountPotencyStatus... keywords) {
        super(itemIdentifier);
        this.targetItem = targetItem;
        this.keywords = keywords;
    }
}
