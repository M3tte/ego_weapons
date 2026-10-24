package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class EmptyDescriptionPage {


    // If on an empty page. Set UI Page to 0.
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {
        EgoWeaponsKeybinds.setUiPage(0);
    }

}
