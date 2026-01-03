package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.potion.ILoveYou;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;

public class MimicryArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        return 8;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        return AbilityUtils.getAbilityIcon("i_love_you");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALEPH;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        return "TERROR";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (playerVars.light >= 10) {
            playerVars.light -= 10;
            AbilityUtils.applyBlipCooldown(40, playerVars);
            giveEffect(player);
            playerVars.syncPlayerVariables(player);
        }
    }

    @Override
    public float getAvailability(PlayerEntity player, PlayerVariables playerVars) {
        if (playerVars.light < getBlipCost(player, playerVars)) {
            return (float) (playerVars.light / getBlipCost(player, playerVars));
        }

        return 1.0f;
    }


    private static void giveEffect(PlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        player.inventory.setChanged();
        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0.05);
            ((ServerWorld) player.level).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
        }

        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.mimicry_armor.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);
        player.playSound(EgoWeaponsSounds.NOTHING_THERE_LOVE_YOU, 1, 1);
        player.playSound(EgoWeaponsSounds.OMINOUS_EFFECT, 1, 1);
        player.addEffect(new EffectInstance(ILoveYou.get(), (int) 300, (int) 0, (false), (false)));
    }
}
