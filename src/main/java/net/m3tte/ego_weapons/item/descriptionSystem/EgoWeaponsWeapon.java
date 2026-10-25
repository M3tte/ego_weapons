package net.m3tte.ego_weapons.item.descriptionSystem;

import net.m3tte.ego_weapons.client.util.descriptionHandler.ArmorStatisticPage;
import net.m3tte.ego_weapons.client.util.descriptionHandler.AttributeTextPlaceholder;
import net.m3tte.ego_weapons.client.util.descriptionHandler.EmptyDescriptionPage;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.client.util.descriptionHandler.metadata.PlaceholderMetadata;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.HashMap;
import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;

public class EgoWeaponsWeapon extends SwordItem {
    public EgoWeaponsWeapon(IItemTier tier, int dmg, float attackSpeed, Properties props) {
        super(tier, dmg, attackSpeed, props);
    }



    public String getDefaultKillIdentifier() {
        return null;
    }

    public StaticAnimation getDefaultStunAnim(int strength) {
        return EgoWeaponsAnimations.CLASH_STUN_BASIC;
    }

    protected String identifier = "";
    protected EmptyDescriptionPage defaultPageResult = new EmptyDescriptionPage();
    protected HashMap<Integer, EmptyDescriptionPage> pages = null;
    protected String tierText = "";

    public void addPage(EmptyDescriptionPage page) {
        pages.put(pages.size(), page);
    }
    public void setupWeaponDescription() {
        addPage(new ArmorStatisticPage(AttributeTextPlaceholder.PlaceholderType.WEAPON, 3, new PlaceholderMetadata("weaponData")));
    }




    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);

        if (pages == null) {
            this.pages = new HashMap<>();
            setupWeaponDescription();
        }


        if (EgoWeaponsKeybinds.getUiPage() >= pages.size()) {
            this.defaultPageResult.render(itemstack, world, list);
        }


        TooltipFuncs.generateItemDescription(list, "desc.ego_weapons."+identifier+".desc");
        list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

        list.add(new TranslationTextComponent("item.ego_weapons.generic.page", EgoWeaponsKeybinds.getUiPage()+1, pages.size()));
        list.add(new TranslationTextComponent(tierText));
        list.add(new StringTextComponent(" "));

        EmptyDescriptionPage page = this.pages.getOrDefault(EgoWeaponsKeybinds.getUiPage(), defaultPageResult);

        page.render(itemstack, world, list);

        generateStatusHelp(list);
    }

    public String getIdentifier() {
        return identifier;
    }

    public EmptyDescriptionPage getDefaultPageResult() {
        return defaultPageResult;
    }

    public HashMap<Integer, EmptyDescriptionPage> getPages() {
        return pages;
    }

    public String getTierText() {
        return tierText;
    }
}
