package net.m3tte.ego_weapons.client.util.descriptionHandler;

import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class SkillDescriptionPage extends EmptyDescriptionPage {
    String gearIdentifier = "";
    String skillType = "";
    int lines = 0;

    String[] statusEffects;

    Effect[] assignedEffects = null;

    public SkillDescriptionPage(String gearIdentifier, String skillType, int lines, String[] statusEffects, Effect... assignedEffects) {
        this.gearIdentifier = gearIdentifier;
        this.skillType = skillType;
        this.lines = lines;
        this.statusEffects = statusEffects;
        this.assignedEffects = assignedEffects;
    }

    public SkillDescriptionPage(String gearIdentifier, String skillType, int lines, String[] statusEffects) {
        this.gearIdentifier = gearIdentifier;
        this.skillType = skillType;
        this.lines = lines;
        this.statusEffects = statusEffects;
    }

    @Override
    public void render(ItemStack itemstack, World world, List<ITextComponent> list) {
        if (EgoWeaponsKeybinds.isHoldingShift()) {
            generateStatusDescription(list, statusEffects);
        }
        else {
            if (assignedEffects != null) {
                generateDescription(list,gearIdentifier, skillType, lines, assignedEffects);
            } else {
                generateDescription(list,gearIdentifier, skillType, lines);
            }

        }
    }
}
