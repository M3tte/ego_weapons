package net.m3tte.ego_weapons.client.renderer;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.*;

import java.util.HashMap;

public class StatusEffectIconHandler {

    private static HashMap<Effect, Tuple<ResourceLocation, Character>> effectRegistry;

    public static HashMap<Effect, Tuple<ResourceLocation, Character>> getEffectRegistry() {

        if (effectRegistry == null)
            generateEffectRegistry();

        return effectRegistry;
    }

    // Generate effect registry for status effects.

    public static final ResourceLocation ICONPAK1 = new ResourceLocation("ego_weapons", "icon_package_1");

    public static void generateEffectRegistry() {

        effectRegistry = new HashMap<>();

        generateEffectRegistryEntry(EgoWeaponsEffects.BLEED.get(), ICONPAK1, '\uE001');
        generateEffectRegistryEntry(EgoWeaponsEffects.EGO_ATTUNEMENT_ARDOR_BLOSSOM.get(), ICONPAK1, '\uE050');
        generateEffectRegistryEntry(EgoWeaponsEffects.EMBERS.get(), ICONPAK1, '\uE051');

    }

    public static void generateEffectRegistryEntry(Effect effect, ResourceLocation iconPak, char seq) {
        if (effectRegistry != null) {
            effectRegistry.put(effect, new Tuple<>(iconPak, seq));
        }
    }

    public static ITextComponent[] generateTranslateArguments(Effect... effects) {
        ITextComponent[] outArray = new ITextComponent[effects.length];
        int x = 0;
        for (Effect e : effects) {

            Tuple<ResourceLocation, Character> metadata = getEffectRegistry().getOrDefault(e, new Tuple<>(Style.DEFAULT_FONT, '?'));

            IFormattableTextComponent comp = new StringTextComponent(metadata.getB()+" ").withStyle(Style.EMPTY.withFont(metadata.getA()));

            comp.append(new TranslationTextComponent(e.getDescriptionId()).withStyle(Style.EMPTY.withFont(Style.DEFAULT_FONT)).withStyle(TextFormatting.RED));

            outArray[x] = comp;
            x++;
        }

        return outArray;
    }

}
