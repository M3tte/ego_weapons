package net.m3tte.ego_weapons.client.util.descriptionHandler.metadata;

public class PlaceholderMetadata {
    String identifier = "";

    public PlaceholderMetadata(String itemIdentifier) {
        this.identifier = itemIdentifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
