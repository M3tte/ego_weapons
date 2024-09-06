package net.m3tte.tcorp.network.packages;

import net.m3tte.tcorp.TCorpParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class StaggerPackages {



    public static class SendStaggerMessage {

        int entityID;
        public SendStaggerMessage(int EntityID) {
            this.entityID = EntityID;
        }

        public SendStaggerMessage(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
        }

        public static void buffer(SendStaggerMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
        }

        public static void handler(SendStaggerMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            System.out.println("Handling...");
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);
                    if (target != null) {
                        System.out.println("Sent Particle");
                        Minecraft.getInstance().level.addParticle(TCorpParticleRegistry.STAGGER.get(), target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0);
                    } else {
                        System.out.println("Stagger Target Is Null");
                    }

                }
            });
            context.setPacketHandled(true);
        }
    }


}
