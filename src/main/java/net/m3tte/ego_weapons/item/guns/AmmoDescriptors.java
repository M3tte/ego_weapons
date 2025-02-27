package net.m3tte.ego_weapons.item.guns;

import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class AmmoDescriptors {
    public static void incendiaryBulletDescription(ItemStack itemstack, World world, List<ITextComponent> list) {
        list.add(new StringTextComponent("= - - - - - - - [Page: 1/1] - - - - - - - =").withStyle(TextFormatting.GRAY));

        if (EgoWeaponsKeybinds.isHoldingShift())
            generateStatusDescription(list, new String[]{"red", "ammo", "burn"});
        else {
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.incendiary.1"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.incendiary.2"));
            list.add(new StringTextComponent(" "));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.incendiary.3"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.incendiary.4"));
        }

        list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
    }

    public static void moonstoneBulletDescription(ItemStack itemstack, World world, List<ITextComponent> list) {
        list.add(new StringTextComponent("= - - - - - - - [Page: 1/1] - - - - - - - =").withStyle(TextFormatting.GRAY));

        if (EgoWeaponsKeybinds.isHoldingShift())
            generateStatusDescription(list, new String[]{"white", "ammo", "sinking"});
        else {
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.moonstone.1"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.moonstone.2"));
            list.add(new StringTextComponent(" "));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.moonstone.3"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.moonstone.4"));
        }

        list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
    }

    public static void ALHVBulletDescription(ItemStack itemstack, World world, List<ITextComponent> list) {

        list.add(new StringTextComponent("Manufactured by Atelier Logic").withStyle(TextFormatting.GRAY));
        list.add(new StringTextComponent("= - - - - - - - [Page: 1/1] - - - - - - - =").withStyle(TextFormatting.GRAY));

        if (EgoWeaponsKeybinds.isHoldingShift())
            generateStatusDescription(list, new String[]{"red", "ammo", "poise"});
        else {
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.no_status"));
            list.add(new StringTextComponent(" "));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.al_hv_pulv.1"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.al_hv_pulv.2"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.al_hv_pulv.3"));
            list.add(new StringTextComponent(" "));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.al_hv_pulv.4"));
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.al_hv_pulv.5"));
        }

        list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
    }

    public static void basicBulletDescriptor(ItemStack itemstack, World world, List<ITextComponent> list) {

        list.add(new StringTextComponent("= - - - - - - - [Page: 1/1] - - - - - - - =").withStyle(TextFormatting.GRAY));

        if (EgoWeaponsKeybinds.isHoldingShift())
            generateStatusDescription(list, new String[]{"red", "ammo"});
        else {
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets.no_status"));
        }

        list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
    }
}
