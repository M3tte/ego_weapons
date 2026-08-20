package net.m3tte.ego_weapons.network.packages;

import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CapabilityPackages {
    public static class SyncOnrushData {

        int entityID;
        int timeLeft;
        public SyncOnrushData(int EntityID, int timeLeft) {
            this.entityID = EntityID;
            this.timeLeft = timeLeft;
        }

        public SyncOnrushData(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            this.timeLeft = buffer.readInt();
        }

        public static void buffer(SyncOnrushData message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeDouble(message.timeLeft);
        }

        public static void handler(SyncOnrushData message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);
                    if (message.timeLeft > 0) {
                        target.getPersistentData().putInt("onrushChain", message.timeLeft);
                    } else {
                        target.getPersistentData().remove("onrushChain");
                    }

                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class ApplyDialogueData {

        int entityID;
        String content;
        int format;

        boolean literal;

        int dialogueIndex;
        int priorityIndex;

        public ApplyDialogueData(int EntityID, String content, int formatOrdinal, boolean literal, int dialogueIndex, DialogueSystem.DialogueTypes type) {
            this.entityID = EntityID;
            this.content = content;
            this.format = formatOrdinal;
            this.literal = literal;
            this.dialogueIndex = dialogueIndex;
            this.priorityIndex = type.ordinal();

        }

        /***
         * Dialogue Data
         * @param EntityID  Entity to speak the dialogue
         * @param content   Basic Dialogue Content
         * @param formatOrdinal Dialogue Format
         * @param literal Whether the text is literal. Will bypass the lang system.
         * @param dialogueIndex The Dialogue Index. At -1 it will ignore suffixes (like dialogue.test.0 or dialogue.test.1). Otherwise it will count down from the suffix towards 1.
         * @param type
         */
        public ApplyDialogueData(int EntityID, String content, TextFormatting formatOrdinal, boolean literal, int dialogueIndex, DialogueSystem.DialogueTypes type) {
            this.entityID = EntityID;
            this.content = content;
            this.format = formatOrdinal.ordinal();
            this.literal = literal;
            this.dialogueIndex = dialogueIndex;
            this.priorityIndex = type.ordinal();
        }

        public ApplyDialogueData(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            int length = buffer.readInt();
            this.content = buffer.readUtf(length);
            this.format = buffer.readInt();
            this.literal = buffer.readBoolean();
            this.dialogueIndex = buffer.readInt();
            this.priorityIndex = buffer.readInt();
        }

        public static void buffer(ApplyDialogueData message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeInt(message.content.length());
            buffer.writeUtf(message.content);
            buffer.writeInt(message.format);
            buffer.writeBoolean(message.literal);
            buffer.writeInt(message.dialogueIndex);
            buffer.writeInt(message.priorityIndex);
        }

        public static void handler(ApplyDialogueData message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {

                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);
                    System.out.println("PROCESSED MESSAGE WITH ENTITY : "+target+ " - "+message.content);

                    if (message.literal)
                        DialogueSystem.speakDialogueLiteral(target, message.content, DialogueSystem.DialogueTypes.values()[message.priorityIndex], TextFormatting.getById(message.format));
                    else
                        DialogueSystem.speakDialogue(target, message.content, DialogueSystem.DialogueTypes.values()[message.priorityIndex], TextFormatting.getById(message.format), message.dialogueIndex);

                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class ChangePersonality {

        int entityID;
        String personality;


        public ChangePersonality(int EntityID, String personality) {
            this.entityID = EntityID;
            this.personality = personality;
        }

        public ChangePersonality(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            int length = buffer.readInt();
            this.personality = buffer.readUtf(length);
        }

        public static void buffer(ChangePersonality message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeInt(message.personality.length());
            buffer.writeUtf(message.personality);
        }

        public static void handler(ChangePersonality message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);

                    System.out.println("CHANGING PERSONALITY OF "+target+" TO "+ message.personality);
                    if (target != null) {
                        target.getPersistentData().putString("personality", message.personality);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
