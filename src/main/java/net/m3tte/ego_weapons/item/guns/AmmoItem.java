package net.m3tte.ego_weapons.item.guns;

import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class AmmoItem extends Item {

    private GunCaliber caliber;

    private String[] statusEffects;
    private int dialogueLines;
    boolean flavourText = false;
    int ammoType = 0;
    String identifier = "";

    public AmmoType getAmmoType() {
        return AmmoType.values()[this.ammoType];
    }

    public GunCaliber getCaliber() {
        return caliber;
    }

    public AmmoItem(Properties props, GunCaliber caliber, int ammoType, String[] statusEffects, int dialogueLines, String identifier, boolean flavourText) {
        super(props);
        this.caliber = caliber;
        this.statusEffects = statusEffects;
        this.dialogueLines = dialogueLines;
        this.identifier = identifier;
        this.flavourText = flavourText;
        this.ammoType = ammoType;

    }

    public void processHoverText(ItemStack stack, World world, List<ITextComponent> list) {
        if (flavourText) {
            list.add(new TranslationTextComponent("desc.ego_weapons.bullets."+identifier+".desc"));
            list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY));
        }

        list.add(new StringTextComponent("= - - - - - - - [Page: 1/1] - - - - - - - =").withStyle(TextFormatting.GRAY));
        list.add(new TranslationTextComponent("desc.ego_weapons.ammo").append(new TranslationTextComponent(this.caliber.getCaliber())));
        list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY));
        if (EgoWeaponsKeybinds.isHoldingShift())
            generateStatusDescription(list, statusEffects);
        else {
            for(int i = 1; i <= dialogueLines; i++) {
                list.add(new TranslationTextComponent("desc.ego_weapons.bullets."+identifier+"."+i));
            }
        }

        list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag tooltipflag) {
        super.appendHoverText(stack, world, list, tooltipflag);

        processHoverText(stack, world, list);

    }
}
