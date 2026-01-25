package net.m3tte.ego_weapons;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EgoWeaponsCreativeTabs {
    public static final ItemGroup EGO_WEAPONS = new ItemGroup(EgoWeaponsMod.MODID + ".items") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(EgoWeaponsItems.ICON_ITEM.get());
        }
    };
}
