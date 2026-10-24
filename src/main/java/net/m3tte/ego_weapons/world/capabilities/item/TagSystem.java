package net.m3tte.ego_weapons.world.capabilities.item;


import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;

public class TagSystem {

    // Set<ResourceLocation> tags = stack.getItem().getTags();
    // Get tags. Map them to tag presets. Return the presets to the renderer.
    // TODO: Implement text placeholder type for item tags.

    public static class TagPreset {
        private ResourceLocation background;
        private int color = 0xFFFFFF;

        public TagPreset(ResourceLocation background, int color) {
            this.background = background;
            this.color = color;
        }
    }
}
