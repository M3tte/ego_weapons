package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.item.SuitItem;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.potion.OrlandoPotionEffect;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;
import java.util.Optional;

public class BlacksilencerushProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Blacksilencerush!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Blacksilencerush!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Blacksilencerush!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Blacksilencerush!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Blacksilencerush!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity target = null;
		if (entity instanceof LivingEntity) {
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).inventory.armor.set((int) 3, new ItemStack(SuitItem.helmet));
			else
				((LivingEntity) entity).setItemSlot(EquipmentSlotType.HEAD, new ItemStack(SuitItem.helmet));
			if (entity instanceof ServerPlayerEntity)
				((ServerPlayerEntity) entity).inventory.setChanged();
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, x, (y + 1), z, (int) 25, 0.2, 0.6, 0.2, 0);
		}
		if (!world.isClientSide()) {
			MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
			if (mcserv != null)
				mcserv.getPlayerList().broadcastMessage(
						new StringTextComponent((entity.getDisplayName().getString() + "> Thats that... and this is this...")), ChatType.SYSTEM,
						Util.NIL_UUID);
		}

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


		entitypatch.playAnimationSynchronized(TCorpAnimations.ORLANDO_TRIGGER, 0.2f);




		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(OrlandoPotionEffect.potion, (int) 3600, (int) 0, (false), (false)));
		/* Irrelevant due to orlando buffs
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 3600, (int) 1, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, (int) 3600, (int) 0, (false), (false)));
		if (entity instanceof LivingEntity)
			((LivingEntity) entity).addEffect(new EffectInstance(Effects.DIG_SPEED, (int) 3600, (int) 1, (false), (false)));*/
		if (!world.isClientSide()) {
			if (world instanceof World && !((World) world).isClientSide) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:thatsthatthisisthis")),
						SoundCategory.RECORDS, (float) 5, (float) 1);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:thatsthatthisisthis")),
						SoundCategory.RECORDS, (float) 5, (float) 1, false);
			}
			/*new Object() {
				private int ticks = 0;
				private float waitTicks;
				private IWorld world;

				public void start(IWorld world, int waitTicks) {
					this.waitTicks = waitTicks;
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}

				@SubscribeEvent
				public void tick(TickEvent.ServerTickEvent event) {
					if (event.phase == TickEvent.Phase.END) {
						this.ticks += 1;
						if (this.ticks >= this.waitTicks)
							run();
					}
				}

				private void run() {
					if (world instanceof World && !world.isRemote()) {
						((World) world).playSound(null, new BlockPos(x, y, z),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:roland")),
								SoundCategory.RECORDS, (float) 20, (float) 1);
					} else {
						((World) world).playSound(x, y, z,
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:roland")),
								SoundCategory.RECORDS, (float) 20, (float) 1, false);
					}
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 20);*/

			if (!entity.level.isClientSide && entity.level.getServer() != null) {
				Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("tcorp:blacksilencerush"));
				if (_fopt.isPresent()) {
					FunctionObject _fobj = _fopt.get();
					entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
				}
			}
		}
	}
}
