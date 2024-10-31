package net.m3tte.ego_weapons.network.packages;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityPackages {
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
}
