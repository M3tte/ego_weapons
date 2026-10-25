package net.m3tte.ego_weapons.world.capabilities.item;


import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class TagSystem {

    // Set<ResourceLocation> tags = stack.getItem().getTags();
    // Get tags. Map them to tag presets. Return the presets to the renderer.
    // TODO: Implement text placeholder type for item tags.



    private static HashMap<ResourceLocation, TagPresets> registeredTagPresets = null;

    public static HashMap<ResourceLocation, TagPresets> getRegisteredTagPresets() {
        if (registeredTagPresets == null) {
            setupTagPresets();
        }

        return registeredTagPresets;
    }

    public static void setupTagPresets() {
        registeredTagPresets = new HashMap<>();

        registeredTagPresets.put(getTag(EgoWeaponsMod.MODID, "bird"), TagPresets.GOLD);
        registeredTagPresets.put(getTag(EgoWeaponsMod.MODID, "waw"), TagPresets.WAW);
    }

    static ResourceLocation PLAQUE_SILVER = new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/plaque_silver.png");
    public static ResourceLocation getPlaque(String suffix) {
        return new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/plaques/plaque_"+suffix+".png");
    }

    public static ResourceLocation getTag(String modid, String tag) {
        return new ResourceLocation(modid, tag);
    }
    public enum TagPresets {
        ZAYIN(getPlaque("zayin"), 0x99d468),
        TETH(getPlaque("teth"), 0x5dbdd2),
        HE(getPlaque("he"), 0xd5ee6b),
        WAW(getPlaque("waw"), 0xdc68dd),
        ALEPH(getPlaque("aleph"), 0xd84162),
        GOLD(getPlaque("gold"), 0xd5ee6b),
        SIMPLE(getPlaque("simple"), 0xFFFFFF),
        WHITE_BORDER(getPlaque("white_border"), 0xFFFFFF),
        DEFAULT(PLAQUE_SILVER, 0xFFFFFF);
        private ResourceLocation background;
        private int color = 0xFFFFFF;

        TagPresets(ResourceLocation background, int color) {
            this.background = background;
            this.color = color;
        }

        public ResourceLocation getBackground() {
            return background;
        }

        public int getColor() {
            return color;
        }
    }
}
