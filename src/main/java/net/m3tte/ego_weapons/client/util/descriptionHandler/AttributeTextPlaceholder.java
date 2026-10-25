package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.PlaceholderMetadata;
import net.minecraft.util.text.StringTextComponent;

public class AttributeTextPlaceholder extends StringTextComponent {

    PlaceholderType type = PlaceholderType.OTHER;
    int emptyLines = 0;

    PlaceholderMetadata metadata;

    public int getEmptyLines() {
        return emptyLines;
    }

    public PlaceholderType getType() {
        return type;
    }

    public AttributeTextPlaceholder(PlaceholderType type, int emptyLines, PlaceholderMetadata metadata) {
        super(" ");
        this.type = type;
        this.emptyLines = emptyLines;
        this.metadata = metadata;
    }

    public PlaceholderMetadata getMetadata() {
        return metadata;
    }

    public enum PlaceholderType {
        ARMOR_STATISTICS,
        TAGS,
        WEAPON,
        KEYWORDS,
        OTHER;
    }
}
