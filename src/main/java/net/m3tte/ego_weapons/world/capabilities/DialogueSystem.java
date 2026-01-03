package net.m3tte.ego_weapons.world.capabilities;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.network.packages.AbilityPackages;
import net.m3tte.ego_weapons.world.capabilities.gamerules.EgoWeaponsGamerules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Random;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

public class DialogueSystem {

    public static enum DialogueTypes {
        FORCE,
        SKILL,
        FILLER
    }
    public static void speakDialogue(Entity entity, String lang, DialogueTypes dialogueTypes, TextFormatting formatting) {
        speakDialogue(entity, lang, dialogueTypes, formatting, -1);
    }

    public static void speakDialogue(Entity entity, String lang, DialogueTypes dialogueTypes, TextFormatting formatting, int dialogueIndex) {

        int detailLevel = entity.level.getGameRules().getInt(EgoWeaponsGamerules.DIALOGUE_DENSITY);

        if (detailLevel == 0 && !dialogueTypes.equals(DialogueTypes.FORCE))
            return;

        if (detailLevel == 1 && dialogueTypes.equals(DialogueTypes.FILLER))
            return;

        if (!entity.level.isClientSide())
            return;

        if (dialogueIndex > -1) {
            lang = clientEvalDialogue(entity.level.getRandom(), lang, dialogueIndex);
        }

        String text = LanguageMap.getInstance().getOrDefault(lang);

        if (text.isEmpty())
            return;

        if (!entity.level.isClientSide() && !entity.level.getGameRules().getBoolean(EgoWeaponsGamerules.DIALOGUE_BUBBLES)) {
            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            if (mcserv != null)
                mcserv.getPlayerList().broadcastMessage(
                        new StringTextComponent((entity.getDisplayName().getString() + " > ")).withStyle(formatting).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(text).withStyle(TextFormatting.WHITE).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
                        entity.getUUID());
        }


        if (entity.level.getGameRules().getBoolean(EgoWeaponsGamerules.DIALOGUE_BUBBLES)) {
            if (!entity.getPersistentData().contains("dialogueMetaData"))
                entity.getPersistentData().put("dialogueMetaData", new CompoundNBT());

            entity.getPersistentData().getCompound("dialogueMetaData").putString(((Integer)entity.tickCount).toString(), formatting.ordinal()+"||"+text);

        }

    }

    public static void speakDialogueLiteral(Entity entity, String text, DialogueTypes dialogueTypes, TextFormatting formatting) {


        int detailLevel = entity.level.getGameRules().getInt(EgoWeaponsGamerules.DIALOGUE_DENSITY);

        if (detailLevel == 0 && !dialogueTypes.equals(DialogueTypes.FORCE))
            return;

        if (detailLevel == 1 && dialogueTypes.equals(DialogueTypes.FILLER))
            return;


        if (!entity.level.isClientSide() && !entity.level.getGameRules().getBoolean(EgoWeaponsGamerules.DIALOGUE_BUBBLES)) {
            MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
            if (mcserv != null)
                mcserv.getPlayerList().broadcastMessage(
                        new StringTextComponent((entity.getDisplayName().getString() + " > ")).withStyle(formatting).withStyle(TextFormatting.ITALIC).append(new StringTextComponent(text).withStyle(TextFormatting.WHITE).withStyle(TextFormatting.ITALIC)), ChatType.CHAT,
                        entity.getUUID());
        }


        if (entity.level.getGameRules().getBoolean(EgoWeaponsGamerules.DIALOGUE_BUBBLES)) {
            if (!entity.getPersistentData().contains("dialogueMetaData"))
                entity.getPersistentData().put("dialogueMetaData", new CompoundNBT());



            entity.getPersistentData().getCompound("dialogueMetaData").putString(((Integer)entity.tickCount).toString(), formatting.ordinal()+"||"+text);

        }
    }




    private static String clientEvalDialogue(Random rand, String baseText, int dialogueID) {
        System.out.println("CLIENT DIALOGUE EVALUATION TRIGGERED...");
        String base = "";
        String evaluated = "";
        base = baseText+dialogueID;
        evaluated = LanguageMap.getInstance().getOrDefault(base);


        while (base.equals(evaluated) && dialogueID > 1) {
            dialogueID = rand.nextInt(dialogueID);
            base = baseText+dialogueID;
            evaluated = LanguageMap.getInstance().getOrDefault(base);
        }

        if (base.equals(evaluated)) {
            base = baseText+1;
            evaluated = LanguageMap.getInstance().getOrDefault(base);
        }

        return evaluated;
    }
    public static void speakEvalDialogue(LivingEntity target, String baseText, String personality, int dialogue, TextFormatting format, DialogueTypes type) {

        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                new AbilityPackages.ApplyDialogueData(target.getId(), baseText+personality+".", format, false, dialogue, type));
    }

    public static void speakEvalDialogue(LivingEntity target, String langText, DialogueTypes type, TextFormatting format) {


        if (target.level.isClientSide())
            return;

        EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                new AbilityPackages.ApplyDialogueData(target.getId(), langText, format, false, -1, type));
    }

    public static String getPersonality(LivingEntity target) {

        if (target == null)
            return null;

        String personality = "";

        if (target.getPersistentData().contains("personality")) {
            personality = target.getPersistentData().getString("personality");
        }
        else if (target instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = target.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            personality = entityData.personality;
        }
        if ((!(target instanceof PlayerEntity)) && personality.isEmpty())
            return "";


        return personality;
    }


    public static void onEmotionUpDialogueEvaluation(LivingEntity target, int newLevel) {



        String personality = "";

        if (target.getPersistentData().contains("personality")) {
            personality = target.getPersistentData().getString("personality");
        }
        else if (target instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = target.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            personality = entityData.personality;
        }
        if ((!(target instanceof PlayerEntity)) && personality.isEmpty())
            return;

        int dialogue = target.getRandom().nextInt(5);

        if (personality.isEmpty()) {
            personality = "default";
        }

        if (newLevel == 3) {
            speakEvalDialogue(target, "dialogue.ego_weapons.generic.emotion.3.",personality, dialogue, TextFormatting.WHITE, DialogueTypes.FILLER);
        }

        if (newLevel == 5) {
            speakEvalDialogue(target, "dialogue.ego_weapons.generic.emotion.5.",personality, dialogue, TextFormatting.WHITE, DialogueTypes.FILLER);
        }
    }

    public static void onHitDialogueEvaluation(LivingEntity target, DamageSource source) {

        if (target.level.isClientSide())
            return;




        boolean downChange = false;
        int damageTier = target.getPersistentData().getInt("damageDialogueTier");
        float healthPercent = target.getHealth() / target.getMaxHealth();

        String personality = "";



        if (target.getPersistentData().contains("personality")) {
            personality = target.getPersistentData().getString("personality");
        }
        else if (target instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = target.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            personality = entityData.personality;
        }
        if ((!(target instanceof PlayerEntity)) && personality.isEmpty())
            return;

        if (personality.isEmpty()) {
            personality = "default";
        }



        int dialogue = target.getRandom().nextInt(5);

        if (!target.isAlive()) {

            return;
        }

        if (damageTier < 1 && healthPercent <= 0.95) {

            System.out.println("Speaking Tier1");

            speakEvalDialogue(target, "dialogue.ego_weapons.generic.hurt.1.",personality, dialogue, TextFormatting.WHITE, DialogueTypes.FILLER);
            target.getPersistentData().putInt("damageDialogueTier", 1);
            downChange = true;
        }

        if (damageTier < 2 && healthPercent <= 0.6) {

            System.out.println("Speaking Tier2");

            speakEvalDialogue(target, "dialogue.ego_weapons.generic.hurt.2.",personality, dialogue, TextFormatting.WHITE, DialogueTypes.FILLER);
            target.getPersistentData().putInt("damageDialogueTier", 2);
            downChange = true;
        }

        if (damageTier < 3 && healthPercent <= 0.2) {

            System.out.println("Speaking Tier3");

            speakEvalDialogue(target, "dialogue.ego_weapons.generic.hurt.3.",personality, dialogue, TextFormatting.RED, DialogueTypes.FILLER);
            target.getPersistentData().putInt("damageDialogueTier", 3);
            downChange = true;
        }


        if (downChange) {
            target.getPersistentData().putInt("lastDownChange", target.tickCount);
        }
    }

    public static void onHitSanityDialogueEvaluation(LivingEntity target, float sanityPercent) {

        if (target.level.isClientSide())
            return;




        boolean downChange = false;
        int sanity = target.getPersistentData().getInt("sanityDialogueTier");

        String personality = "";



        if (target.getPersistentData().contains("personality")) {
            personality = target.getPersistentData().getString("personality");
        }
        else if (target instanceof PlayerEntity) {
            EgoWeaponsModVars.PlayerVariables entityData = target.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
            personality = entityData.personality;
        }
        if ((!(target instanceof PlayerEntity)) && personality.isEmpty())
            return;

        if (personality.isEmpty()) {
            personality = "default";
        }



        int dialogue = target.getRandom().nextInt(5);

        if (!target.isAlive()) {

            return;
        }


        if (sanity < 1 && sanityPercent <= 0.6) {


            speakEvalDialogue(target, "dialogue.ego_weapons.generic.sanity.1.",personality, dialogue, TextFormatting.WHITE, DialogueTypes.FILLER);
            target.getPersistentData().putInt("sanityDialogueTier", 1);
            downChange = true;
        }

        if (sanity < 2 && sanityPercent <= 0.2) {


            speakEvalDialogue(target, "dialogue.ego_weapons.generic.sanity.2.",personality, dialogue, TextFormatting.RED, DialogueTypes.FILLER);
            target.getPersistentData().putInt("sanityDialogueTier", 2);
            downChange = true;
        }


        if (downChange) {
            target.getPersistentData().putInt("lastDownChangeSanity", target.tickCount);
        }
    }
}
