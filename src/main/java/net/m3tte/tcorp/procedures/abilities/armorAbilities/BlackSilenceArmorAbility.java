package net.m3tte.tcorp.procedures.abilities.armorAbilities;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables.PlayerVariables;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.item.SuitItem;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.m3tte.tcorp.potion.OrlandoPotionEffect;
import net.m3tte.tcorp.procedures.abilities.AbilityTier;
import net.m3tte.tcorp.procedures.abilities.AbilityUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

import static net.m3tte.tcorp.procedures.abilities.AbilityUtils.applyBlipCooldown;

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
        return AbilityTier.ALPHA;
    }

    @Override
    public String getName(PlayerEntity player, PlayerVariables playerVars) {
        if (player.hasEffect(OrlandoPotionEffect.potion))
            return "Furioso";
        return "Orlando";
    }

    @Override
    public void trigger(PlayerEntity player, PlayerVariables playerVars) {

        if (player.hasEffect(OrlandoPotionEffect.potion) && playerVars.blips > 6) {
            playerVars.blips -= 5;
            furiosoEffect(player, playerVars);
            applyBlipCooldown(60, playerVars);

            playerVars.syncPlayerVariables(player);
        } else if (playerVars.blips > 9) {
            orlandoEffect(player);
            playerVars.syncPlayerVariables(player);
        }
    }

    private static void orlandoEffect(PlayerEntity player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        player.inventory.armor.set((int) 3, new ItemStack(SuitItem.helmet));
        player.inventory.setChanged();
        if (player.level instanceof ServerWorld) {
            ((ServerWorld) player.level).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
            ((ServerWorld) player.level).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
        }

        player.playSound(TCorpSounds.THAT_IS_THAT_AND_THIS_IS_THIS, 1, 1);
        player.addEffect(new EffectInstance(OrlandoPotionEffect.potion, (int) 3600, (int) 0, (false), (false)));
        if (!player.level.isClientSide()) {
            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            if (mcserv != null)
                mcserv.getPlayerList().broadcastMessage(
                        new StringTextComponent((player.getDisplayName().getString() + ">")).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(" Thats that... and this is this...").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
                        player.getUUID());
        }

        LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


        entitypatch.playAnimationSynchronized(TCorpAnimations.ORLANDO_TRIGGER, 0.2f);

    }

    private static void furiosoEffect(PlayerEntity player, PlayerVariables vars) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        World world = player.level;


        player.getPersistentData().putDouble("furiosohits", 0);
        player.getPersistentData().putDouble("furiosoattacks", 0);
        if (!world.isClientSide()) {
            if (world instanceof ServerWorld) {
                ((ServerWorld)world).sendParticles(BlipeffectParticle.particle, x, y + 1.0, z, 8, 0.4, 0.6, 0.4, 0.0);
                ((ServerWorld)world).sendParticles(BlacksilenceshadowParticle.particle, x, y + 1.0, z, 15, 0.2, 0.6, 0.2, 0.0);
            }
        }

        player.playSound(TCorpSounds.DICE_ROLL, 1, 1);

        player.addEffect(new EffectInstance(FuriosoPotionEffect.potion, 340, 0, false, false));
        player.addEffect(new EffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 340, 1, false, false));

        applyBlipCooldown(40, vars);
    }
}
