package net.m3tte.ego_weapons.gameasset.abilities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;

public class ItemAbility {

    public void trigger(PlayerEntity player, PlayerVariables playerVars) {
        if (canTrigger(player, playerVars)) {
            if (!player.level.isClientSide())
                player.displayClientMessage(new StringTextComponent("No ability.").withStyle(TextFormatting.ITALIC), (true));
        }
    }


    public boolean respectsEFStun(PlayerEntity player, PlayerVariables playerVars) {
        return true;
    }
    public boolean respectsGlobalCooldown(PlayerEntity player, PlayerVariables playerVars) {
        return true;
    }

    public boolean canTrigger(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars))
            return false;

        if (playerVars.globalcooldown > 0 && respectsGlobalCooldown(player, playerVars))
            return false;

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

        if (entitypatch != null) {
            if (!entitypatch.getEntityState().canUseSkill() && respectsEFStun(player, playerVars)) {
                return false;
            }
        }

        return true;
    }

    public ResourceLocation getOverlay(PlayerEntity player, PlayerVariables playerVars) {
        return null;
    }
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 0;
    }

    public static int deductLightDecreases(PlayerEntity player, AbilityUtils.AbilityType type, int inValue) {

        // Calculate Deductions via Udjat Vanguard
        int udjatPotency = EgoWeaponsEffects.UDJAT_VANGUARD.get().getPotency(player);
        if (udjatPotency > 0) {
            if (type.equals(AbilityUtils.AbilityType.RELOAD))
                inValue--;
            else if (type.equals(AbilityUtils.AbilityType.WEAPON)) {
                if (udjatPotency == 2)
                    inValue-=1;
                else if (udjatPotency == 3)
                    inValue-=2;
            }
        }

        return inValue;
    }

    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.globalcooldown > 0) {
            return 0.0f;
        }
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }

    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return null;
    }


    public AbilityTier getAbilityTier(PlayerEntity player, PlayerVariables playerVars) {
        return AbilityTier.NUUN;
    }

    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "";
    }
}
