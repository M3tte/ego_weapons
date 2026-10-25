package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder.PlaceholderType;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.PlaceholderMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ArmorStatisticPage extends EmptyDescriptionPage{

    public PlaceholderType getType() {
        return type;
    }

    public int getEmptyLines() {
        return emptyLines;
    }

    private PlaceholderType type;

    PlaceholderMetadata metadata;
    private int emptyLines = 0;
    public ArmorStatisticPage(PlaceholderType type, int emptyLines, PlaceholderMetadata metadata) {
        super(true);
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
        list.add(new AttributeTextPlaceholder(PlaceholderType.KEYWORDS, 2, this.metadata));
        for (int i = 0; i < 1; i++) {
            list.add(new StringTextComponent(""));
        }
    }

}
