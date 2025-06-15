package net.m3tte.ego_weapons.procedures.legacy;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Util;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;

public class AtelierLogicCooldownHandlerProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure AtelierLogicCooldownHandler!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure AtelierLogicCooldownHandler!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure AtelierLogicCooldownHandler!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure AtelierLogicCooldownHandler!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure AtelierLogicCooldownHandler!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (!world.isClientSide()) {
			MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
			if (mcserv != null)
				mcserv.getPlayerList()
						.broadcastMessage(new StringTextComponent(("- - - " + (entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new EgoWeaponsModVars.PlayerVariables())).gunMagSize)), ChatType.SYSTEM, Util.NIL_UUID);
		}
		if (!world.isClientSide()) {
			if (world instanceof World && !((World) world).isClientSide) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ateliershoot")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
			}
		}
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).gunMagSize == 1) {
			{
				double _setval = 0;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.gunMagSize = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = 40;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.globalcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).light - 1);
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.light = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).getCooldowns().addCooldown(EgoWeaponsItems.ATELIER_LOGIC_PISTOLS.get(), (int) 40);
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
						((World) world).playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:click")),
								SoundCategory.NEUTRAL, (float) 1, (float) 1);
					} else {
						((World) world).playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:click")),
								SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
					}
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 40);
		} else {
			{
				double _setval = 1;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.gunMagSize = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
