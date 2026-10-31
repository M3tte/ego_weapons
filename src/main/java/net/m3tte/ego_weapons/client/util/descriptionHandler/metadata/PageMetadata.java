package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

import net.m3tte.ego_weapons.client.util.descriptionHandler.EmptyDescriptionPage;
import net.m3tte.ego_weapons.potion.countEffects.CountPotencyStatus;
import net.minecraft.item.Item;

import java.util.HashMap;

public class PageMetadata extends PlaceholderMetadata {
    HashMap<Integer, EmptyDescriptionPage> pages = null;
    int page = 0;

    public HashMap<Integer, EmptyDescriptionPage> getPages() {
        return this.pages;
    }

    public int getCurrPage() {
        return page;
    }

    public PageMetadata(String itemIdentifier, int totalPages, int page, HashMap<Integer, EmptyDescriptionPage> pages) {
        super(itemIdentifier);
        this.page = page;
        this.pages = pages;
    }
}
