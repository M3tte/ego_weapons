package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ResistanceDescriptionPage extends EmptyDescriptionPage {


    float defenseValue = 0;
    float bonusStagger = 0;
    float bonusSanity = 0;
    float redRes = 1;
    float whiteRes = 1;
    float blackRes = 1;
    float paleRes = 1;
    float slashRes = 1;
    float bluntRes = 1;
    float pierceRes = 1;

    public ResistanceDescriptionPage(float defenseValue, float bonusStagger, float bonusSanity, float redRes, float whiteRes, float blackRes, float paleRes, float slashRes, float bluntRes, float pierceRes) {
        super(true);
        this.defenseValue = defenseValue;
        this.bonusStagger = bonusStagger;
        this.bonusSanity = bonusSanity;
        this.redRes = redRes;
        this.whiteRes = whiteRes;
        this.blackRes = blackRes;
        this.paleRes = paleRes;
        this.slashRes = slashRes;
        this.bluntRes = bluntRes;
        this.pierceRes = pierceRes;
    }

    @Override
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {


        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.title.1").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.defense_level").append(this.defenseValue+"").withStyle(TextFormatting.BLUE));
        if (this.bonusStagger > 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.stagger_bonus").append(((int)this.bonusStagger)+"").withStyle(TextFormatting.BLUE));
        } else if (this.bonusStagger < 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.stagger_bonus").append(((int)this.bonusStagger)+"").withStyle(TextFormatting.RED));
        }

        if (bonusSanity > 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.sanity_bonus").append(((int)this.bonusSanity)+"").withStyle(TextFormatting.BLUE));
        } else if (bonusSanity < 0) {
            list.add(new TranslationTextComponent("desc.ego_weapons.resistances.sanity_bonus").append(((int)this.bonusSanity)+"").withStyle(TextFormatting.RED));
        }

        list.add(new StringTextComponent(" "));

        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.title.2").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.red").append(this.redRes+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.white").append(this.whiteRes+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.black").append(this.blackRes+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.pale").append(this.paleRes+"x"));
        list.add(new StringTextComponent(" "));

        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.title.3").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.slash").append(this.slashRes+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.pierce").append(this.pierceRes+"x"));
        list.add(new TranslationTextComponent("desc.ego_weapons.resistances.blunt").append(this.bluntRes+"x"));
    }

}
