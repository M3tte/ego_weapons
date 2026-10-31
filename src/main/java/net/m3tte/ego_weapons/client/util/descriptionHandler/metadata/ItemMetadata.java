package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.List;

public class ItemMetadata extends PlaceholderMetadata {
    private Item targetItem = null;
    private EffectLike[] keywords = null;

    public EffectLike[] getKeywords() {
        return keywords;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public ItemMetadata(String itemIdentifier, Item targetItem, EffectLike... keywords) {
        super(itemIdentifier);
        this.targetItem = targetItem;
        this.keywords = keywords;
    }
}
