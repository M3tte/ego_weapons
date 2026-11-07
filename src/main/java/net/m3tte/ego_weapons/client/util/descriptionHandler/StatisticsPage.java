package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder.PlaceholderType;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.ItemMetadata;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.PlaceholderMetadata;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.EffectLike;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class StatisticsPage extends EmptyDescriptionPage{

    public PlaceholderType getType() {
        return type;
    }

    public int getEmptyLines() {
        return emptyLines;
    }

    private PlaceholderType type;

    PlaceholderMetadata metadata;
    private int emptyLines = 0;

    private String[] status_effects = {};

    public StatisticsPage(PAGE_TYPE identifier, PlaceholderType type, int emptyLines, PlaceholderMetadata metadata) {
        super(identifier, true);
        this.type = type;
        this.emptyLines = emptyLines;
        this.metadata = metadata;

        if (this.metadata instanceof ItemMetadata) {
            this.status_effects = createEffectMetadata(((ItemMetadata) metadata).getKeywords());
        }
    }

    private String[] createEffectMetadata(EffectLike[] effects) {
        String[] returnArray = new String[effects.length];

        for (int i = 0; i < effects.length; i++) {
            String[] finalString = effects[i].getName().split("\\.");

            if (finalString.length > 0) {
                String sel = finalString[finalString.length-1];
                returnArray[i] = sel;
            } else {
                returnArray[i] = "EFFECT CONVERT ERROR";
            }

        }
        return returnArray;
    }

    // If on an empty page. Set UI Page to 0.
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {

        // When holding shift if there are status effects. Show the status effect descriptions
        if (EgoWeaponsKeybinds.isHoldingShift() && status_effects.length > 0) {
            generateStatusDescription(list, status_effects);
            return;
        }

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
