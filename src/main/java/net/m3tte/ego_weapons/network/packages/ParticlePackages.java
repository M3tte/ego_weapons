package net.m3tte.ego_weapons.network.packages;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class ParticlePackages {

    public static class SendShakeMessage {

        int entityID;
        float shake;
        public SendShakeMessage(int EntityID, float shake) {
            this.entityID = EntityID;
            this.shake = shake;
        }

        public SendShakeMessage(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            this.shake = buffer.readFloat();
        }

        public static void buffer(SendShakeMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeFloat(message.shake);
        }

        public static void handler(SendShakeMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);
                    if (target != null) {
                        target.getPersistentData().putFloat("shakeEffect", message.shake);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }

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
                        Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.STAGGER.get(), target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0);
                    } else {
                        System.out.println("Stagger Target Is Null");
                    }

                }
            });
            context.setPacketHandled(true);
        }
    }
    public static class SendTakeAimParticle {

        int entityID;
        public SendTakeAimParticle(int EntityID) {
            this.entityID = EntityID;
        }

        public SendTakeAimParticle(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
        }

        public static void buffer(SendTakeAimParticle message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
        }

        public static void handler(SendTakeAimParticle message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);
                    if (target != null) {
                        Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.TAKE_AIM.get(), target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0);
                    }

                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class DirectionalAttackParticle {

        int entityID;
        int sourceEntityID;
        ResourceLocation particleID;
        public DirectionalAttackParticle(int EntityID, int sourceEntityID, ResourceLocation particleID) {
            this.entityID = EntityID;
            this.sourceEntityID = sourceEntityID;
            this.particleID = particleID;
        }

        public DirectionalAttackParticle(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            this.sourceEntityID = buffer.readInt();
            this.particleID = buffer.readResourceLocation();
        }

        public static void buffer(DirectionalAttackParticle message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeInt(message.sourceEntityID);
            buffer.writeResourceLocation(message.particleID);
        }

        public static void handler(DirectionalAttackParticle message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            System.out.println("Handling...");
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);
                    Entity source = Minecraft.getInstance().level.getEntity(message.sourceEntityID);
                    if (target != null && source != null) {
                        System.out.println("SOURCE ENTITY ID: "+source.getId()+" - "+source.getName());
                        Minecraft.getInstance().level.addParticle((IParticleData) Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getValue(message.particleID)), target.getX(), target.getY(), target.getZ(), 0, target.getId(), source.getId());

                    } else {
                        System.out.println("Particle target or source is null");
                    }

                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class NumberLabelParticle {

        double x;
        double y;
        double z;
        float number;
        int type;
        public NumberLabelParticle(Vector3d pos, NumberParticleTypes type, float number) {
            this.x = pos.x();
            this.y = pos.y();
            this.z = pos.z();

            this.number = number;
            this.type = type.ordinal();
        }

        public NumberLabelParticle(PacketBuffer buffer) {
            this.x = buffer.readDouble();
            this.y = buffer.readDouble();
            this.z = buffer.readDouble();

            this.number = buffer.readFloat();
            this.type = buffer.readInt();

        }

        public static void buffer(NumberLabelParticle message, PacketBuffer buffer) {
            buffer.writeDouble(message.x);
            buffer.writeDouble(message.y);
            buffer.writeDouble(message.z);

            buffer.writeFloat(message.number);
            buffer.writeInt(message.type);
        }

        public static void handler(NumberLabelParticle message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            System.out.println("Handling...");
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer() && Minecraft.getInstance().level != null) {
                    Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.EFFECT_NUMBER.get(), message.x, message.y, message.z ,0, message.number, message.type);
                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class DamageLabelParticle {

        double x;
        double y;
        double z;
        float number;
        int typeMetadata;
        float mult;
        public DamageLabelParticle(Vector3d pos, DamageTypes damageType, AttackTypes attackType, boolean crit, float number, float mult, float bonusMult) {
            this.x = pos.x();
            this.y = pos.y();
            this.z = pos.z();

            this.number = number;
            this.typeMetadata = damageType.ordinal() + 1;
            this.typeMetadata += attackType.ordinal() * 10;
            this.typeMetadata += crit ? 100 : 0;

            if (mult < 0)
                this.typeMetadata *= -1;

            this.mult = Math.abs(mult);
            System.out.println("BONUSMULT IS : "+bonusMult);
            bonusMult = ((int)((bonusMult) * 100)) / 100f;

            this.mult += Math.abs(bonusMult) * 1000000;

            if (bonusMult < 0)
                this.mult *= -1;
        }

        public DamageLabelParticle(PacketBuffer buffer) {
            this.x = buffer.readDouble();
            this.y = buffer.readDouble();
            this.z = buffer.readDouble();

            this.number = buffer.readFloat();
            this.typeMetadata = buffer.readInt();
            this.mult = buffer.readFloat();

        }

        public static void buffer(DamageLabelParticle message, PacketBuffer buffer) {
            buffer.writeDouble(message.x);
            buffer.writeDouble(message.y);
            buffer.writeDouble(message.z);

            buffer.writeFloat(message.number);
            buffer.writeInt(message.typeMetadata);
            buffer.writeFloat(message.mult);
        }

        public static void handler(DamageLabelParticle message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            System.out.println("Handling... "+message.typeMetadata+" - "+message.mult);
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer() && Minecraft.getInstance().level != null) {
                    Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.DAMAGE_NUMBER.get(), message.x, message.y, message.z ,message.number, message.typeMetadata, message.mult);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
