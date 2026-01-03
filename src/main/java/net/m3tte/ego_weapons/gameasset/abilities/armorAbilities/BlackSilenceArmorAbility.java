package net.m3tte.ego_weapons.gameasset.abilities.armorAbilities;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.particle.BlacksilenceshadowParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.potion.FuriosoPotionEffect;
import net.m3tte.ego_weapons.potion.OrlandoPotionEffect;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityTier;
import net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils;
import net.m3tte.ego_weapons.gameasset.abilities.ItemAbility;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import static net.m3tte.ego_weapons.gameasset.abilities.AbilityUtils.applyBlipCooldown;

public class BlackSilenceArmorAbility extends ItemAbility {

    @Override
    public int getBlipCost(PlayerEntity player, PlayerVariables playerVars) {
        if (player.hasEffect(OrlandoPotionEffect.potion))
            return 6;
        return 9;
    }

    @Override
    public ResourceLocation getIconLocation(PlayerEntity player, PlayerVariables vars) {
        if (player.hasEffect(OrlandoPotionEffect.potion))
            return AbilityUtils.getAbilityIcon("furioso");
        return AbilityUtils.getAbilityIcon("orlando");
    }

    @Override
    public AbilityTier getAbilityTier() {
        return AbilityTier.ALEPH;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        if (player.hasEffect(OrlandoPotionEffect.potion))
            return "Furioso";
        return "Orlando";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (player.hasEffect(OrlandoPotionEffect.potion) && playerVars.light >= 6) {
            playerVars.light -= 6;
            furiosoEffect(player, playerVars);
            applyBlipCooldown(60, playerVars);

            playerVars.syncPlayerVariables(player);
        } else if (playerVars.light >= 9) {
            orlandoEffect(player);
            playerVars.syncPlayerVariables(player);
        }
    }

    private static void orlandoEffect(PlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        player.inventory.armor.set((int) 3, EgoWeaponsItems.PERCEPTION_BLOCKING_MASK.get().getDefaultInstance());
        player.inventory.setChanged();
        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(EgoWeaponsParticles.EXPEND_LIGHT_PARTICLE.get(), x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0.05);
            ((ServerWorld) player.level).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
        }

        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.black_silence.1", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

        player.playSound(EgoWeaponsSounds.THAT_IS_THAT_AND_THIS_IS_THIS, 1, 1);
        player.addEffect(new EffectInstance(OrlandoPotionEffect.potion, (int) 3600, (int) 0, (false), (false)));


        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


        entitypatch.playAnimationSynchronized(BlackSilenceMovesetAnims.ORLANDO_TRIGGER, 0.2f);

    }

    private static void furiosoEffect(PlayerEntity player, PlayerVariables vars) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        World world = player.level;

        DialogueSystem.speakEvalDialogue(player, "dialogue.ego_weapons.skills.black_silence.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.RED);


        player.getPersistentData().putDouble("furiosohits", 0);
        player.getPersistentData().putDouble("furiosoattacks", 0);
        if (!world.isClientSide()) {
            if (world instanceof ServerWorld) {
                ((ServerWorld)world).sendParticles(BlipeffectParticle.particle, x, y + 1.0, z, 8, 0.4, 0.6, 0.4, 0.0);
                ((ServerWorld)world).sendParticles(BlacksilenceshadowParticle.particle, x, y + 1.0, z, 15, 0.2, 0.6, 0.2, 0.0);
            }
        }

        player.playSound(EgoWeaponsSounds.DICE_ROLL, 1, 1);

        player.addEffect(new EffectInstance(FuriosoPotionEffect.potion, 340, 0, false, false));
        player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 340, 1, false, false));

        applyBlipCooldown(40, vars);
    }
}
