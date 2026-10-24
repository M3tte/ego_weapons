package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder.PlaceholderType;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.PlaceholderMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class AttributeDescriptionPage extends EmptyDescriptionPage{

    public PlaceholderType getType() {
        return type;
    }

    public int getEmptyLines() {
        return emptyLines;
    }

    private PlaceholderType type;

    PlaceholderMetadata metadata;
    private int emptyLines = 0;
    public AttributeDescriptionPage(PlaceholderType type, int emptyLines, PlaceholderMetadata metadata) {
        this.type = type;
        this.emptyLines = emptyLines;
        this.metadata = metadata;
    }

    // If on an empty page. Set UI Page to 0.
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {
        list.add(new AttributeTextPlaceholder(this.type, this.emptyLines, this.metadata));
        for (int i = 0; i < this.emptyLines; i++) {
            list.add(new StringTextComponent(""));
        }

    }

}
