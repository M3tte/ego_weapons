package net.m3tte.ego_weapons.procedures;

import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.util.text.*;

import java.util.List;

public class TooltipFuncs {


    public static void generateDescription(List<ITextComponent> list, String weaponIdentifier, String typeIdentifier, int maxIndices, boolean flavorText) {
        list.add(new TranslationTextComponent("desc.ego_weapons."+weaponIdentifier+"."+typeIdentifier+".title"));
        for (int i = 1; i <= maxIndices; i++)
            list.add(new TranslationTextComponent("desc.ego_weapons."+weaponIdentifier+"."+typeIdentifier+"."+i));

        if (flavorText) {
            list.add(new StringTextComponent("   "));

            list.add(new TranslationTextComponent("desc.ego_weapons."+weaponIdentifier+"."+typeIdentifier+".desc").withStyle(style -> style.withColor(Color.fromRgb(0x8c5c3c))));
        }
    }

    public static void generateDescription(List<ITextComponent> list, String weaponIdentifier, String typeIdentifier, int maxIndices) {
        generateDescription(list, weaponIdentifier, typeIdentifier, maxIndices, false);
    }

    public static void generateItemDescription(List<ITextComponent> list, String identifier) {
        list.add(new TranslationTextComponent(identifier).withStyle(style -> style.withColor(Color.fromRgb(0x8c5c3c))));
    }


    public static void generateOffhandHelp(List<ITextComponent> list) {
        list.add(new TranslationTextComponent("desc.ego_weapons.info.offh.1").append(new KeybindTextComponent("key.ego_weapons.alt_ability").withStyle(TextFormatting.ITALIC).withStyle(EgoWeaponsKeybinds.isHoldingAltAbility() ? TextFormatting.GREEN : TextFormatting.WHITE)).append(new TranslationTextComponent("desc.ego_weapons.info.offh.2")));
    }

    public static void generateStatusHelp(List<ITextComponent> list) {
        list.add(new StringTextComponent("   "));
        list.add(new TranslationTextComponent("desc.ego_weapons.info.status.1").append(new KeybindTextComponent("key.ego_weapons.status_detail").withStyle(TextFormatting.ITALIC).withStyle(EgoWeaponsKeybinds.isHoldingShift() ? TextFormatting.GREEN : TextFormatting.WHITE)).append(new TranslationTextComponent("desc.ego_weapons.info.status.2")));
        list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
    }

    public static void generateStatusDescription(List<ITextComponent> list, String[] statuses) {
        list.add(new TranslationTextComponent("desc.ego_weapons.statuses.title"));

        for (String status : statuses) {
            list.add(new StringTextComponent("  "));
            switch (status) {
                case "sealed": generateDescription(list, "statuses", "sealed", 2); break;
                case "tianshia_star": generateDescription(list, "statuses", "tianshia_star", 4); break;
                case "loss_of_self": generateDescription(list, "statuses", "loss_of_self", 3); break;
                case "sever_the_thread": generateDescription(list, "statuses", "sever_the_thread", 3); break;
                case "cheseds_latency": generateDescription(list, "statuses", "cheseds_latency", 3); break;
                case "armor_regen_cycle": generateDescription(list, "statuses", "armor_regen_cycle", 3); break;
                case "target_mark_udjat": generateDescription(list, "statuses", "target_mark_udjat", 2); break;
                case "udjat_vanguard": generateDescription(list, "statuses", "udjat_vanguard", 9); break;
                case "sheut_fracture": generateDescription(list, "statuses", "sheut_fracture", 7, true); break;
                case "white_fragility": generateDescription(list, "statuses", "white_fragility", 2); break;
                case "blue_sand": generateDescription(list, "statuses", "blue_sand", 5, true); break;
                case "ego_att_ardor": generateDescription(list, "statuses", "ego_att_ardor", 5); break;
                case "embers": generateDescription(list, "statuses", "embers", 4); break;
                case "butterfly": generateDescription(list, "statuses", "butterfly", 10); break;
                case "imitation": generateDescription(list, "statuses", "imitation", 2); break;
                case "fragile": generateDescription(list, "statuses", "fragile", 2); break;
                case "deathrite_haste": generateDescription(list, "statuses", "deathrite_haste", 6); break;
                case "speed_up": generateDescription(list, "statuses", "speed_up", 2); break;
                case "speed_down": generateDescription(list, "statuses", "speed_down", 2); break;
                case "strider_mao": generateDescription(list, "statuses", "strider_mao", 5); break;
                case "speed": generateDescription(list, "statuses", "speed", 2); break;
                case "branding_blade": generateDescription(list, "statuses", "branding_blade", 3); break;
                case "d10fuel": generateDescription(list, "statuses", "d10fuel", 4); break;
                case "firefist_overdrive": generateDescription(list, "statuses", "firefist_overdrive", 6); break;
                case "orlando": generateDescription(list, "statuses", "orlando", 2); break;
                case "sin": generateDescription(list, "statuses", "sin", 4); break;
                case "furioso": generateDescription(list, "statuses", "furioso", 6); break;
                case "manifest_ego": generateDescription(list, "statuses", "manifest_ego", 2); break;
                case "terror": generateDescription(list, "statuses", "terror", 3, true); break;
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
                case "protection": generateDescription(list, "statuses", "protection", 2); break;
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
                case "target_marked": generateDescription(list, "statuses", "target_marked", 2); break;
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
