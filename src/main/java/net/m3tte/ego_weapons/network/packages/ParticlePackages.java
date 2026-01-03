package net.m3tte.ego_weapons.network.packages;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.specialParticles.numberParticle.NumberParticleTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.AttackTypes;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage.DamageTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;
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

    public static class SendInsanityMessage {
        int entityID;
        public SendInsanityMessage(int EntityID) {
            this.entityID = EntityID;
        }

        public SendInsanityMessage(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
        }

        public static void buffer(SendInsanityMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
        }

        public static void handler(SendInsanityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);

                    System.out.println("SENT INSANITY MSG TO -> "+target+" at "+target.level.isClientSide());

                    if (target != null) {
                        // Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.STAGGER.get(), target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0);

                        target.playSound(EgoWeaponsSounds.INSANITY_HEARTBEAT, 0.5f, 1);
                        target.playSound(EgoWeaponsSounds.INSANITY_RINGING, 0.5f, 1);


                    } else {
                        System.out.println("Insanity Target Is Null");
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

    public static class MagicBulletAimPacket {

        int entityID;
        float targetOffset;
        float scaleMult;
        ResourceLocation particleID;
        public MagicBulletAimPacket(int EntityID, float targetOffset, float scaleMult, ResourceLocation particleID) {
            this.entityID = EntityID;
            this.targetOffset = targetOffset;
            this.scaleMult = scaleMult;
            this.particleID = particleID;
        }

        public MagicBulletAimPacket(PacketBuffer buffer) {
            this.entityID = buffer.readInt();
            this.targetOffset = buffer.readFloat();
            this.scaleMult = buffer.readFloat();
            this.particleID = buffer.readResourceLocation();
        }

        public static void buffer(MagicBulletAimPacket message, PacketBuffer buffer) {
            buffer.writeInt(message.entityID);
            buffer.writeFloat(message.targetOffset);
            buffer.writeFloat(message.scaleMult);
            buffer.writeResourceLocation(message.particleID);
        }

        public static void handler(MagicBulletAimPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            System.out.println("Handling...");
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    Entity target = Minecraft.getInstance().level.getEntity(message.entityID);

                    if (target != null) {
                        Minecraft.getInstance().level.addParticle((IParticleData) Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getValue(message.particleID)), target.getX(), target.getY(), target.getZ(), message.scaleMult, target.getId(), message.targetOffset);

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

    public static class SendParticlesVelocity {

        double x;
        double y;
        double z;

        double velX;
        double velY;
        double velZ;

        double rangeX;
        double rangeY;
        double rangeZ;
        int count;
        ResourceLocation type;
        public SendParticlesVelocity(ParticleType<?> particleType, int count, double x, double y, double z, double velX, double velY, double velZ, double rangeX, double rangeY, double rangeZ) {
            this.x = x;
            this.y = y;
            this.z = z;

            this.velX = velX;
            this.velY = velY;
            this.velZ = velZ;

            this.rangeX = rangeX;
            this.rangeY = rangeY;
            this.rangeZ = rangeZ;

            this.count = count;
            this.type = particleType.getRegistryName();
        }

        public SendParticlesVelocity(PacketBuffer buffer) {
            this.x = buffer.readDouble();
            this.y = buffer.readDouble();
            this.z = buffer.readDouble();

            this.velX = buffer.readDouble();
            this.velY = buffer.readDouble();
            this.velZ = buffer.readDouble();

            this.rangeX = buffer.readDouble();
            this.rangeY = buffer.readDouble();
            this.rangeZ = buffer.readDouble();

            this.count = buffer.readInt();
            this.type = buffer.readResourceLocation();

        }

        public static void buffer(SendParticlesVelocity message, PacketBuffer buffer) {
            buffer.writeDouble(message.x);
            buffer.writeDouble(message.y);
            buffer.writeDouble(message.z);

            buffer.writeDouble(message.velX);
            buffer.writeDouble(message.velY);
            buffer.writeDouble(message.velZ);

            buffer.writeDouble(message.rangeX);
            buffer.writeDouble(message.rangeY);
            buffer.writeDouble(message.rangeZ);

            buffer.writeInt(message.count);
            buffer.writeResourceLocation(message.type);
        }

        public static void handler(SendParticlesVelocity message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer() && Minecraft.getInstance().level != null) {
                    Random r = Minecraft.getInstance().level.random;
                    //System.out.println("SENDING PARTICLES WITH PARAMS : "+message.toString());
                    for (int i = 0; i < message.count; i++) {
                        //System.out.println("TEST SAMPLE : "+(message.x + fetchPositiveNegative(r, message.rangeX)+ " - " + message.y + fetchPositiveNegative(r, message.rangeY) + " - " + ));
                        Minecraft.getInstance().level.addParticle((IParticleData) Objects.requireNonNull(ForgeRegistries.PARTICLE_TYPES.getValue(message.type)), message.x + fetchPositiveNegative(r, message.rangeX), message.y + fetchPositiveNegative(r, message.rangeY), message.z + fetchPositiveNegative(r, message.rangeZ) ,message.velX, message.velY, message.velZ);

                    }

                }
            });
            context.setPacketHandled(true);
        }

        @Override
        public String toString() {
            return "SendParticlesVelocity{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", velX=" + velX +
                    ", velY=" + velY +
                    ", velZ=" + velZ +
                    ", rangeX=" + rangeX +
                    ", rangeY=" + rangeY +
                    ", rangeZ=" + rangeZ +
                    ", count=" + count +
                    ", type=" + type +
                    '}';
        }

        private static double fetchPositiveNegative(Random r, double bounds) {
            return r.nextFloat() * bounds - bounds/2;
        }
    }

    public static class DamageLabelParticle {

        double x;
        double y;
        double z;
        float number;
        int typeMetadata;
        double mult;
        public DamageLabelParticle(Vector3d pos, DamageTypes damageType, AttackTypes attackType, boolean crit, float number, double mult, double bonusMult) {
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

            bonusMult = (Math.round(bonusMult * 100)) / 100d;

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
            this.mult = buffer.readDouble();

        }

        public static void buffer(DamageLabelParticle message, PacketBuffer buffer) {
            buffer.writeDouble(message.x);
            buffer.writeDouble(message.y);
            buffer.writeDouble(message.z);

            buffer.writeFloat(message.number);
            buffer.writeInt(message.typeMetadata);
            buffer.writeDouble(message.mult);
        }

        public static void handler(DamageLabelParticle message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer() && Minecraft.getInstance().level != null) {
                    System.out.println("TRANSFERRING MULTIPLIER VALUE : "+message.mult);
                    Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.DAMAGE_NUMBER.get(), message.x, message.y, message.z ,message.number, message.typeMetadata, message.mult);
                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class ClashLabelParticle {

        double x;
        double y;
        double z;
        float number;
        int typeMetadata;
        boolean cracked;
        public ClashLabelParticle(Vector3d pos, DamageTypes damageType, boolean cracked, float number) {
            this.x = pos.x();
            this.y = pos.y();
            this.z = pos.z();

            this.number = number;
            this.typeMetadata = damageType.ordinal();
            this.cracked = cracked;
        }

        public ClashLabelParticle(PacketBuffer buffer) {
            this.x = buffer.readDouble();
            this.y = buffer.readDouble();
            this.z = buffer.readDouble();

            this.number = buffer.readFloat();
            this.typeMetadata = buffer.readInt();
            this.cracked = buffer.readBoolean();

        }

        public static void buffer(ClashLabelParticle message, PacketBuffer buffer) {
            buffer.writeDouble(message.x);
            buffer.writeDouble(message.y);
            buffer.writeDouble(message.z);

            buffer.writeFloat(message.number);
            buffer.writeInt(message.typeMetadata);
            buffer.writeBoolean(message.cracked);
        }

        public static void handler(ClashLabelParticle message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer() && Minecraft.getInstance().level != null) {
                    Minecraft.getInstance().level.addParticle(EgoWeaponsParticles.CLASH_NUMBER.get(), message.x, message.y, message.z ,message.number, message.typeMetadata, message.cracked ? 1 : 0);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
