package net.m3tte.tcorp.execFunctions;

import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.item.blackSilence.AtelierlogicshotgunItem;
import net.m3tte.tcorp.potion.FuriosoPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class AtelierCooldownHandler {

	public static boolean hasFuriosoBonus(LivingEntity entity) {
		return entity.hasEffect(FuriosoPotionEffect.potion) && entity.getPersistentData().getDouble("furiosoattacks") < 15.0;
	}

	public static void executePistol(IWorld world, Entity entity, double x, double y, double z) {
		if (!world.isClientSide()) {
			world.playSound(null, new BlockPos(x, y, z),
                    TCorpSounds.ATELIER_SHOOT,
					SoundCategory.NEUTRAL, (float) 1, (float) 1);
		}

		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;
			TcorpModVariables.PlayerVariables capability = entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables());


			if (capability.gunMagSize == 1) {
				capability.gunMagSize = 2;
				capability.globalcooldown = hasFuriosoBonus(living) ? 10 : 30;
				if (!hasFuriosoBonus(living) && !world.isClientSide())
					capability.blips -= 2;


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
						world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:click")),
								SoundCategory.NEUTRAL, (float) 0.3, (float) 1);

						entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.gunMagSize = 0;
							capability.syncPlayerVariables(entity);
						});

						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 40);
			} else {
				{
					capability.gunMagSize = 1;
					if (!hasFuriosoBonus(living) && !world.isClientSide())
						capability.blips--;
				}
			}
			capability.syncPlayerVariables(living);
		}
	}

	public static void executeShotgun(IWorld world, Entity entity, double x, double y, double z) {

		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(x, y, z),
						TCorpSounds.BLACK_SILENCE_SHOTGUN,
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			}
			//entity.getPersistentData().putDouble("furiosoattacks", (entity.getPersistentData().getDouble("furiosoattacks") + 10));

			TcorpModVariables.PlayerVariables capability = entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TcorpModVariables.PlayerVariables());

			capability.gunMagSize = 1;

			if (hasFuriosoBonus(living))
				capability.globalcooldown = 10;
			else
				capability.globalcooldown = 50;

			if (!world.isClientSide() && !hasFuriosoBonus(living)) {
				capability.blips -= 2;
			}
			capability.syncPlayerVariables(entity);
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).getCooldowns().addCooldown(AtelierlogicshotgunItem.item, (int) 20);
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
					world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:click")),
							SoundCategory.NEUTRAL, (float) 0.3, (float) 1);

					entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.gunMagSize = 0;
						capability.syncPlayerVariables(entity);
					});

					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 50);






		}



	}

}
