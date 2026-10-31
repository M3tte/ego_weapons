package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.world.capabilities.item.TagSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class EmptyDescriptionPage {


    // If on an empty page. Set UI Page to 0.

    boolean showTags = false;
    private PAGE_TYPE identifier;

    public PAGE_TYPE getIdentifier() {
        return identifier;
    }

    public EmptyDescriptionPage(PAGE_TYPE identifier) {
        this.identifier = identifier;
    }

    public boolean isShowTags() {
        return showTags;
    }

    public EmptyDescriptionPage(PAGE_TYPE identifier, boolean showTags) {
        this.showTags = showTags;
        this.identifier = identifier;
    }

    public EmptyDescriptionPage() {
        this.identifier = PAGE_TYPE.ERR;
    }

    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {
        EgoWeaponsKeybinds.setUiPage(0);
    }

    public static ResourceLocation genIcon(String suffix) {
        return new ResourceLocation(EgoWeaponsMod.MODID, "textures/gui/description_elements/header_icons/"+suffix+"_icon.png");
    }
    public enum PAGE_TYPE {
        SPECIAL(genIcon("special")),
        INNATE(genIcon("innate")),
        PASSIVE(genIcon("passive")),
        DEFENSE(genIcon("defense")),
        ARMOR_INFO(genIcon("armor_info")),
        INFO(genIcon("info")),
        ERR(genIcon("err")),
        AUTO(genIcon("attack"));

        private final ResourceLocation icon;

        public ResourceLocation getIcon() {
            return icon;
        }

        PAGE_TYPE(ResourceLocation icon) {
            this.icon = icon;
        }
    }
}
