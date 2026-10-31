package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class MultiDescriptionPage extends EmptyDescriptionPage {

    private EmptyDescriptionPage[] pages;

    public MultiDescriptionPage(PAGE_TYPE identifier, boolean showTags, EmptyDescriptionPage... pages) {
        super(identifier, showTags);
        this.pages = pages;
    }

    public MultiDescriptionPage(PAGE_TYPE identifier, EmptyDescriptionPage... pages) {
        super(identifier);
        this.pages = pages;
    }

    @Override
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {

        int i = 0;
        if (EgoWeaponsKeybinds.isHoldingShift()) {
            this.pages[0].render(itemstack, world, list);
        } else {
            for (EmptyDescriptionPage page : this.pages) {
                page.render(itemstack, world, list);
                i++;
                if (i < this.pages.length) {
                    list.add(new StringTextComponent(" "));
                }
            }
        }



    }
}
