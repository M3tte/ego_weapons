package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.FunctionObject;

import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.particle.ArmourupparticleParticle;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Optional;
import java.util.Map;

public class FreischutzmagicabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Freischutzmagicability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Freischutzmagicability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Freischutzmagicability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Freischutzmagicability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Freischutzmagicability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (!((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).globalcooldown > 0)) {
			if (!entity.level.isClientSide && entity.level.getServer() != null) {
				Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("tcorp:freischutzmagic"));
				if (_fopt.isPresent()) {
					FunctionObject _fobj = _fopt.get();
					entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
				}
			}
			{
				double _setval = ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips - 5);
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blips = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 60, (int) 4, (false), (false)));
			{
				double _setval = 100;
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.globalcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlipeffectParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 4, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
			}
			if (!world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ominouseffect")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.beacon.activate")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			}
			if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new TcorpModVariables.PlayerVariables())).blipcooldown < 60) {
				{
					double _setval = 60;
					entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.blipcooldown = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
			new Object() {
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
					if (!entity.level.isClientSide && entity.level.getServer() != null) {
						Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions()
								.get(new ResourceLocation("tcorp:freischutzmagicfire"));
						if (_fopt.isPresent()) {
							FunctionObject _fobj = _fopt.get();
							entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
						}
					}
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).getCooldowns().addCooldown(
								((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMainHandItem().getItem() : ItemStack.EMPTY.getItem()),
								(int) 40);
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(ArmourupparticleParticle.particle, (entity.getX()),
								(entity.getY() + entity.getBbHeight() / 2), (entity.getZ()), (int) 16, (entity.getBbWidth() / 4),
								(entity.getBbHeight() / 2.5), (entity.getBbWidth() / 4), 0.2);
					}
					if (!world.isClientSide()) {
						((World) world).playSound(null, new BlockPos(x, y, z),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
								SoundCategory.NEUTRAL, (float) 1, (float) 1);
						((World) world).playSound(null, new BlockPos(x, y, z),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
								SoundCategory.NEUTRAL, (float) 1, (float) 0.5);
						((World) world).playSound(null, new BlockPos(x, y, z),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
								SoundCategory.NEUTRAL, (float) 1, (float) 0.3);
						((World) world)
								.playSound(null, new BlockPos(x, y, z),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("block.beacon.deactivate")),
										SoundCategory.NEUTRAL, (float) 1, (float) 1);
						((World) world).playSound(null, new BlockPos(x, y, z),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
										.getValue(new ResourceLocation("block.beacon.deactivate")),
								SoundCategory.NEUTRAL, (float) 1, (float) 0.5);
						new Object() {
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
								if (world instanceof World && !((World) world).isClientSide) {
									((World) world).playSound(null, new BlockPos(x, y, z),
											(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
													.getValue(new ResourceLocation("item.armor.equip_chain")),
											SoundCategory.NEUTRAL, (float) 1, (float) 1);
								} else {
									((World) world).playLocalSound(x, y, z,
											(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
													.getValue(new ResourceLocation("item.armor.equip_chain")),
											SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
								}
								MinecraftForge.EVENT_BUS.unregister(this);
							}
						}.start(world, (int) 40);
					}
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 60);
		}
	}
}
