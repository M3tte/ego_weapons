package net.m3tte.tcorp;

import net.m3tte.tcorp.network.packages.AbilityPackages;
import net.m3tte.tcorp.network.packages.StaggerPackages.SendStaggerMessage;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.world.IServerWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;
import java.util.function.Supplier;

public class TcorpModVariables {
	public TcorpModVariables() {
		/*elements.addNetworkMessage(WorldSavedDataSyncMessage.class, WorldSavedDataSyncMessage::buffer, WorldSavedDataSyncMessage::new,
				WorldSavedDataSyncMessage::handler);
		elements.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new,
				PlayerVariablesSyncMessage::handler);
		elements.addNetworkMessage(SyncStaggerMessage.class, SyncStaggerMessage::buffer, SyncStaggerMessage::new,
				SyncStaggerMessage::handler);
		elements.addNetworkMessage(SyncEmotionMessage.class, SyncEmotionMessage::buffer, SyncEmotionMessage::new,
				SyncEmotionMessage::handler);
		elements.addNetworkMessage(SendStaggerMessage.class, SendStaggerMessage::buffer, SendStaggerMessage::new,
				SendStaggerMessage::handler);
		elements.addNetworkMessage(AbilityPackages.SyncOnrushData.class, AbilityPackages.SyncOnrushData::buffer, AbilityPackages.SyncOnrushData::new,
				AbilityPackages.SyncOnrushData::handler);*/
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
	}

	private void init(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(PlayerVariables.class, new PlayerVariablesStorage(), PlayerVariables::new);
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().level.isClientSide) {
			WorldSavedData mapdata = MapVariables.get(event.getPlayer().level);
			WorldSavedData worlddata = WorldVariables.get(event.getPlayer().level);
			if (mapdata != null)
				TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
						new WorldSavedDataSyncMessage(0, mapdata));
			if (worlddata != null)
				TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
						new WorldSavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.getPlayer().level.isClientSide) {
			WorldSavedData worlddata = WorldVariables.get(event.getPlayer().level);
			if (worlddata != null)
				TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
						new WorldSavedDataSyncMessage(1, worlddata));
		}
	}

	public static class WorldVariables extends WorldSavedData {
		public static final String DATA_NAME = "tcorp_worldvars";

		public WorldVariables() {
			super(DATA_NAME);
		}

		public WorldVariables(String s) {
			super(s);
		}

		@Override
		public void load(CompoundNBT nbt) {
		}

		@Override
		public CompoundNBT save(CompoundNBT nbt) {
			return nbt;
		}

		public void syncData(IWorld world) {
			this.setDirty();
			if (world instanceof World && !((World) world).isClientSide)
				TcorpMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(((World) world)::dimension),
						new WorldSavedDataSyncMessage(1, this));
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(IWorld world) {
			if (world instanceof ServerWorld) {
				return ((ServerWorld) world).getDataStorage().computeIfAbsent(WorldVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends WorldSavedData {
		public static final String DATA_NAME = "tcorp_mapvars";
		public String screenoverlaytype = "\"\"";
		public float globalWarningTension = 0;

		public MapVariables() {
			super(DATA_NAME);
		}

		public MapVariables(String s) {
			super(s);
		}

		@Override
		public void load(CompoundNBT nbt) {
			screenoverlaytype = nbt.getString("screenoverlaytype");
			globalWarningTension = nbt.getFloat("globalWarningTension");
		}

		@Override
		public CompoundNBT save(CompoundNBT nbt) {
			nbt.putString("screenoverlaytype", screenoverlaytype);
			nbt.putFloat("globalWarningTension", globalWarningTension);
			return nbt;
		}

		public void syncData(IWorld world) {
			this.setDirty();
			if (world instanceof World && !((World) world).isClientSide)
				TcorpMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new WorldSavedDataSyncMessage(0, this));
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(IWorld world) {
			if (world instanceof IServerWorld) {
				return ((IServerWorld) world).getLevel().getServer().getLevel(World.OVERWORLD).getDataStorage().computeIfAbsent(MapVariables::new,
						DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class WorldSavedDataSyncMessage {
		public int type;
		public WorldSavedData data;

		public WorldSavedDataSyncMessage(PacketBuffer buffer) {
			this.type = buffer.readInt();
			this.data = this.type == 0 ? new MapVariables() : new WorldVariables();
			this.data.load(buffer.readNbt());
		}

		public WorldSavedDataSyncMessage(int type, WorldSavedData data) {
			this.type = type;
			this.data = data;
		}

		public static void buffer(WorldSavedDataSyncMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.type);
			buffer.writeNbt(message.data.save(new CompoundNBT()));
		}

		public static void handler(WorldSavedDataSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					if (message.type == 0)
						MapVariables.clientSide = (MapVariables) message.data;
					else
						WorldVariables.clientSide = (WorldVariables) message.data;
				}
			});
			context.setPacketHandled(true);
		}
	}

	@CapabilityInject(PlayerVariables.class)
	public static Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = null;

	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
			event.addCapability(new ResourceLocation("tcorp", "player_variables"), new PlayerVariablesProvider());
	}

	private static class PlayerVariablesProvider implements ICapabilitySerializable<INBT> {
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(PLAYER_VARIABLES_CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public INBT serializeNBT() {
			return PLAYER_VARIABLES_CAPABILITY.getStorage().writeNBT(PLAYER_VARIABLES_CAPABILITY, this.instance.orElseThrow(RuntimeException::new),
					null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			PLAYER_VARIABLES_CAPABILITY.getStorage().readNBT(PLAYER_VARIABLES_CAPABILITY, this.instance.orElseThrow(RuntimeException::new), null,
					nbt);
		}
	}

	private static class PlayerVariablesStorage implements Capability.IStorage<PlayerVariables> {
		@Override
		public INBT writeNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putDouble("blips", instance.blips);
			nbt.putDouble("maxblips", instance.maxblips);
			nbt.putDouble("blipcooldown", instance.blipcooldown);
			nbt.putDouble("tickslower", instance.tickslower);
			nbt.putDouble("savedirx", instance.savedirx);
			nbt.putDouble("savediry", instance.savediry);
			nbt.putDouble("savedirz", instance.savedirz);
			nbt.putDouble("savedhealth", instance.savedhealth);
			nbt.putDouble("teamid", instance.teamid);
			nbt.putDouble("vitality", instance.vitality);
			nbt.putDouble("insight", instance.insight);
			nbt.putDouble("determination", instance.determination);
			nbt.putDouble("proficiency", instance.proficiency);
			nbt.putDouble("globalcooldown", instance.globalcooldown);
			nbt.putDouble("credits", instance.credits);
			nbt.putDouble("gunMagSize", instance.gunMagSize);
			nbt.putBoolean("firingMode", instance.firingMode);
			nbt.putDouble("blacksilence_ws", instance.blacksilence_ws);
			nbt.putDouble("iFrames", instance.iFrames);
			nbt.putDouble("maxStagger", instance.maxStagger);
			nbt.putDouble("stagger", instance.stagger);
			nbt.putDouble("maxSanity", instance.maxSanity);
			nbt.putDouble("sanity", instance.sanity);
			nbt.putInt("emotionLevel", instance.emotionLevel);
			nbt.putDouble("emotionLevelProgress", instance.emotionLevelProgress);
			return nbt;
		}

		@Override
		public void readNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side, INBT inbt) {
			CompoundNBT nbt = (CompoundNBT) inbt;
			instance.blips = nbt.getDouble("blips");
			instance.maxblips = nbt.getDouble("maxblips");
			instance.blipcooldown = nbt.getDouble("blipcooldown");
			instance.tickslower = nbt.getDouble("tickslower");
			instance.savedirx = nbt.getDouble("savedirx");
			instance.savediry = nbt.getDouble("savediry");
			instance.savedirz = nbt.getDouble("savedirz");
			instance.savedhealth = nbt.getDouble("savedhealth");
			instance.teamid = nbt.getDouble("teamid");
			instance.vitality = nbt.getDouble("vitality");
			instance.insight = nbt.getDouble("insight");
			instance.determination = nbt.getDouble("determination");
			instance.proficiency = nbt.getDouble("proficiency");
			instance.globalcooldown = nbt.getDouble("globalcooldown");
			instance.credits = nbt.getDouble("credits");
			instance.gunMagSize = nbt.getDouble("gunMagSize");
			instance.firingMode = nbt.getBoolean("firingMode");
			instance.blacksilence_ws = nbt.getDouble("blacksilence_ws");
			instance.iFrames = nbt.getDouble("iFrames");
			instance.maxStagger = nbt.getDouble("maxStagger");
			instance.stagger = nbt.getDouble("stagger");
			instance.maxSanity = nbt.getDouble("maxSanity");
			instance.sanity = nbt.getDouble("sanity");
			instance.emotionLevel = nbt.getInt("emotionLevel");
			instance.emotionLevelProgress = nbt.getDouble("emotionLevelProgress");
		}
	}

	public static class PlayerVariables {
		public double blips = 0;
		public double maxblips = 10;
		public double blipcooldown = 0;
		public double tickslower = 0;
		public double savedirx = 0;
		public double savediry = 0;
		public double savedirz = 0;
		public double savedhealth = 0;
		public double teamid = 0.0;
		public double vitality = 10.0;
		public double insight = 10.0;
		public double determination = 10.0;
		public double proficiency = 0.0;
		public double globalcooldown = 0;
		public double credits = 500.0;
		public double gunMagSize = 0;
		public boolean firingMode = true;
		public double blacksilence_ws = 0;
		public double iFrames = 0;
		public double maxStagger = 20;
		public double stagger = 20;

		public double maxSanity = 20;
		public double sanity = 20;

		public int emotionLevel = 0;
		public double emotionLevelProgress = 0;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayerEntity)
				TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity), new PlayerVariablesSyncMessage(this));
		}

		public void syncStagger(Entity entity) {
			if (entity instanceof ServerPlayerEntity) {
				for (ServerPlayerEntity p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
					TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> p), new SyncStaggerMessage(this, (ServerPlayerEntity)entity));
				}
			}
		}

		public void syncEmotionLevel(Entity entity) {
			if (entity instanceof ServerPlayerEntity) {
				for (ServerPlayerEntity p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
					TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> p), new SyncEmotionLevelMSG(this, (ServerPlayerEntity)entity));
				}
			}
		}

		public void syncSanity(Entity entity) {
			if (entity instanceof ServerPlayerEntity) {
				for (ServerPlayerEntity p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
					TcorpMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> p), new SyncSanityMessage(this, (ServerPlayerEntity)entity));
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().level.isClientSide)
			((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncPlayerVariables(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (!event.getPlayer().level.isClientSide())
			((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncPlayerVariables(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.getPlayer().level.isClientSide())
			((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncPlayerVariables(event.getPlayer());
	}

	@SubscribeEvent
	public void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new PlayerVariables()));
		PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
		clone.blips = original.blips;
		clone.maxblips = original.maxblips;
		clone.blipcooldown = original.blipcooldown;
		clone.tickslower = original.tickslower;
		clone.savedirx = original.savedirx;
		clone.savediry = original.savediry;
		clone.savedirz = original.savedirz;
		clone.savedhealth = original.savedhealth;
		clone.teamid = original.teamid;
		clone.vitality = original.vitality;
		clone.insight = original.insight;
		clone.determination = original.determination;
		clone.proficiency = original.proficiency;
		clone.globalcooldown = original.globalcooldown;
		clone.credits = original.credits;
		clone.gunMagSize = original.gunMagSize;
		clone.firingMode = original.firingMode;
		clone.blacksilence_ws = original.blacksilence_ws;
		if (!event.isWasDeath()) {
			clone.iFrames = original.iFrames;
			clone.maxStagger = original.maxStagger;
			clone.stagger = original.stagger;
			clone.maxSanity = original.maxSanity;
			clone.sanity = original.sanity;
			clone.emotionLevel = original.emotionLevel;
			clone.emotionLevelProgress = original.emotionLevelProgress;
		}
	}

	public static class PlayerVariablesSyncMessage {
		public PlayerVariables data;

		public PlayerVariablesSyncMessage(PacketBuffer buffer) {
			this.data = new PlayerVariables();
			new PlayerVariablesStorage().readNBT(null, this.data, null, buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, PacketBuffer buffer) {
			buffer.writeNbt((CompoundNBT) new PlayerVariablesStorage().writeNBT(null, message.data, null));
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new PlayerVariables()));
					variables.blips = message.data.blips;
					variables.maxblips = message.data.maxblips;
					variables.blipcooldown = message.data.blipcooldown;
					variables.tickslower = message.data.tickslower;
					variables.savedirx = message.data.savedirx;
					variables.savediry = message.data.savediry;
					variables.savedirz = message.data.savedirz;
					variables.savedhealth = message.data.savedhealth;
					variables.teamid = message.data.teamid;
					variables.vitality = message.data.vitality;
					variables.insight = message.data.insight;
					variables.determination = message.data.determination;
					variables.proficiency = message.data.proficiency;
					variables.globalcooldown = message.data.globalcooldown;
					variables.credits = message.data.credits;
					variables.gunMagSize = message.data.gunMagSize;
					variables.firingMode = message.data.firingMode;
					variables.blacksilence_ws = message.data.blacksilence_ws;
					variables.iFrames = message.data.iFrames;
					variables.maxStagger = message.data.maxStagger;
					variables.stagger = message.data.stagger;
					variables.maxSanity = message.data.maxSanity;
					variables.sanity = message.data.sanity;
					variables.emotionLevelProgress = message.data.emotionLevelProgress;
					variables.emotionLevel = message.data.emotionLevel;
				}
			});
			context.setPacketHandled(true);
		}
	}

	public static class SyncStaggerMessage {
		public double maxStagger;
		public double stagger;
		public UUID targetUUID;

		public SyncStaggerMessage(PacketBuffer buffer) {
			this.maxStagger = buffer.readDouble();
			this.stagger = buffer.readDouble();
			this.targetUUID = buffer.readUUID();
		}

		public SyncStaggerMessage(PlayerVariables data, PlayerEntity target) {
			this.maxStagger = data.maxStagger;
			this.stagger = data.stagger;
			this.targetUUID = target.getUUID();
		}

		public static void buffer(SyncStaggerMessage message, PacketBuffer buffer) {
			buffer.writeDouble(message.maxStagger);
			buffer.writeDouble(message.stagger);
			buffer.writeUUID(message.targetUUID);
		}

		public static void handler(SyncStaggerMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					if (Minecraft.getInstance().level.getPlayerByUUID(message.targetUUID) == null) {
						System.out.println("TARGET NULL");
						return;
					}
					PlayerVariables variables = Minecraft.getInstance().level.getPlayerByUUID(message.targetUUID).getCapability(PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new PlayerVariables());
					variables.maxStagger = message.maxStagger;
					variables.stagger = message.stagger;
					/*if (variables.stagger <= 0) {
						StaggerSystem.stagger(Minecraft.getInstance().player);
					}*/
				}
			});
			context.setPacketHandled(true);
		}
	}

	public static class SyncEmotionLevelMSG {
		public double emotionLevel;
		public double emotionPoints;
		public UUID targetUUID;

		public SyncEmotionLevelMSG(PacketBuffer buffer) {
			this.emotionLevel = buffer.readDouble();
			this.emotionPoints = buffer.readDouble();
			this.targetUUID = buffer.readUUID();
		}

		public SyncEmotionLevelMSG(PlayerVariables data, PlayerEntity target) {
			this.emotionLevel = data.maxStagger;
			this.emotionPoints = data.stagger;
			this.targetUUID = target.getUUID();
		}

		public static void buffer(SyncEmotionLevelMSG message, PacketBuffer buffer) {
			buffer.writeDouble(message.emotionLevel);
			buffer.writeDouble(message.emotionPoints);
			buffer.writeUUID(message.targetUUID);
		}

		public static void handler(SyncEmotionLevelMSG message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					if (Minecraft.getInstance().level.getPlayerByUUID(message.targetUUID) == null) {
						System.out.println("TARGET NULL");
						return;
					}
					PlayerVariables variables = Minecraft.getInstance().level.getPlayerByUUID(message.targetUUID).getCapability(PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new PlayerVariables());
					variables.emotionLevel = (int) message.emotionLevel;
					variables.emotionLevelProgress = message.emotionPoints;
					/*if (variables.stagger <= 0) {
						StaggerSystem.stagger(Minecraft.getInstance().player);
					}*/
				}
			});
			context.setPacketHandled(true);
		}
	}



	public static class SyncSanityMessage {
		public double sanity;
		public double maxSanity;
		public UUID targetUUID;

		public SyncSanityMessage(PacketBuffer buffer) {
			this.sanity = buffer.readDouble();
			this.maxSanity = buffer.readDouble();
			this.targetUUID = buffer.readUUID();
		}

		public SyncSanityMessage(PlayerVariables vars, PlayerEntity player) {
			this.sanity = vars.sanity;
			this.maxSanity = vars.maxSanity;
			this.targetUUID = player.getUUID();
		}

		public static void buffer(SyncSanityMessage message, PacketBuffer buffer) {
			buffer.writeDouble(message.sanity);
			buffer.writeDouble(message.maxSanity);
			buffer.writeUUID(message.targetUUID);
		}

		public static void handler(SyncSanityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					if (Minecraft.getInstance().level == null)
						return;
					PlayerVariables variables = Minecraft.getInstance().level.getPlayerByUUID(message.targetUUID).getCapability(PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new PlayerVariables());
					variables.sanity = message.sanity;
					variables.maxSanity = message.maxSanity;
				}
			});
			context.setPacketHandled(true);
		}
	}

}
