package net.m3tte.ego_weapons.procedures;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class TooltipFuncs {

    public static void generateDescription(List<ITextComponent> list, String weaponIdentifier, String typeIdentifier, int maxIndices) {
        list.add(new TranslationTextComponent("desc.ego_weapons."+weaponIdentifier+"."+typeIdentifier+".title"));
        for (int i = 1; i <= maxIndices; i++)
            list.add(new TranslationTextComponent("desc.ego_weapons."+weaponIdentifier+"."+typeIdentifier+"."+i));
    }



    public static void generateStatusDescription(List<ITextComponent> list, String[] statuses) {
        list.add(new TranslationTextComponent("desc.ego_weapons.statuses.title"));
        for (String status : statuses) {
            list.add(new StringTextComponent("  "));
            switch (status) {
                case "orlando": generateDescription(list, "statuses", "orlando", 2); break;
                case "furioso": generateDescription(list, "statuses", "furioso", 6); break;
                case "manifest_ego": generateDescription(list, "statuses", "manifest_ego", 2); break;
                case "terror": generateDescription(list, "statuses", "terror", 3); break;
                case "shell": generateDescription(list, "statuses", "shell", 2); break;
                case "power_up": generateDescription(list, "statuses", "power_up", 2); break;
                case "power_down": generateDescription(list, "statuses", "power_down", 2); break;
                case "offense_up": generateDescription(list, "statuses", "offense_up", 2); break;
                case "eternal_rest": generateDescription(list, "statuses", "eternal_rest", 6); break;
                case "offense_down": generateDescription(list, "statuses", "offense_down", 2); break;
                case "defense_up": generateDescription(list, "statuses", "defense_up", 2); break;
                case "defense_down": generateDescription(list, "statuses", "defense_down", 2); break;
                case "resilience": generateDescription(list, "statuses", "resilience", 2); break;
                case "obligation": generateDescription(list, "statuses", "obligation", 5); break;
                case "burn": generateDescription(list, "statuses", "burn", 2); break;
                case "rupture": generateDescription(list, "statuses", "rupture", 1); break;
                case "protection": generateDescription(list, "statuses", "protection", 1); break;
                case "bleed": generateDescription(list, "statuses", "bleed", 1); break;
                case "dark_flame": generateDescription(list, "statuses", "dark_flame", 3); break;
                case "magic_bullet": generateDescription(list, "statuses", "magic_bullet", 3); break;
                case "sinking": generateDescription(list, "statuses", "sinking", 2); break;
                case "tremor": generateDescription(list, "statuses", "tremor", 2); break;
                case "tremor_burst": generateDescription(list, "statuses", "tremor_burst", 1); break;
                case "tremor_decay": generateDescription(list, "statuses", "tremor_decay", 3); break;
                case "tremor_conversion": generateDescription(list, "statuses", "tremor_conversion", 1); break;
                case "poise": generateDescription(list, "statuses", "poise", 2); break;
                case "ammo": generateDescription(list, "statuses", "ammo", 2); break;
                case "target_marked": generateDescription(list, "statuses", "target_marked", 1); break;
                case "assist_fire": generateDescription(list, "statuses", "assist_fire", 2); break;
                case "living_departed": generateDescription(list, "statuses", "living_departed", 4); break;
                case "the_living": generateDescription(list, "statuses", "the_living", 2); break;
                case "the_departed": generateDescription(list, "statuses", "the_departed", 2); break;
                case "red": statusDAMAGE(list, "red_damage"); break;
                case "black": statusDAMAGE(list, "black_damage"); break;
                case "white": statusDAMAGE(list, "white_damage"); break;
                case "pale": statusDAMAGE(list, "pale_damage"); break;
            }
        }
    }


    private static void statusDAMAGE(List<ITextComponent> list, String damagetype) {
        list.add(new TranslationTextComponent("desc.ego_weapons.statuses."+damagetype+".title"));
    }

}
