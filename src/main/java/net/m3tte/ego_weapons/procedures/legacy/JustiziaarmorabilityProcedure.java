package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;

import net.m3tte.ego_weapons.particle.PowerdownParticle;
import net.m3tte.ego_weapons.particle.GalaxyparticleParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.block.ChainsofjusticeBlock;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

public class JustiziaarmorabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Justiziaarmorability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure Justiziaarmorability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure Justiziaarmorability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure Justiziaarmorability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Justiziaarmorability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		double limit = 0;
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + entity.getBbHeight() / 2), z, (int) 4, 0.1, 0.4, 0.1, 0.04);
		}
		{
			List<Entity> _entfound = world
					.getEntities(entity,
							new AxisAlignedBB(x - (16 / 2d), y - (16 / 2d), z - (16 / 2d), x + (16 / 2d), y + (16 / 2d), z + (16 / 2d)), null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
						}
					}.compareDistOf(x, y, z)).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof LivingEntity
						&& (!(entityiterator instanceof PlayerEntity || entityiterator instanceof ServerPlayerEntity)
								|| (entityiterator.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new EgoWeaponsModVars.PlayerVariables())).teamid != (entity
												.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
												.orElse(new EgoWeaponsModVars.PlayerVariables())).teamid)) {
					entityiterator.setDeltaMovement(0, 0, 0);
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(GalaxyparticleParticle.particle, (entityiterator.getX()),
								(entityiterator.getY() + entityiterator.getBbHeight() / 2), (entityiterator.getZ()), (int) 8, 0.1, 0.1, 0.1,
								0.04);
					}
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(PowerdownParticle.particle, (entityiterator.getX()),
								(entityiterator.getY() + entityiterator.getBbHeight() / 2), (entityiterator.getZ()), (int) 1, 0.1, 0.1, 0.1, 0);
					}
					{
						Entity _ent = entityiterator;
						_ent.setPos((Math.floor(entityiterator.getX()) + 0.5), (entityiterator.getY()),
								(Math.floor(entityiterator.getZ()) + 0.5));
						if (_ent instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) _ent).connection.teleport((Math.floor(entityiterator.getX()) + 0.5),
									(entityiterator.getY()), (Math.floor(entityiterator.getZ()) + 0.5), _ent.xRot, _ent.yRot,
									Collections.emptySet());
						}
					}
					if (entityiterator instanceof LivingEntity)
						((LivingEntity) entityiterator).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 70, (int) 8, (false), (false)));
					limit = (limit + 1);
					if (!world.isClientSide()) {
						((World) world).playSound(null,
								new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
										.getValue(new ResourceLocation("entity.evoker_fangs.attack")),
								SoundCategory.PLAYERS, (float) 2, (float) 1.2);
						((World) world).playSound(null,
								new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:ominousbell")),
								SoundCategory.PLAYERS, (float) 2, (float) 1);
						((World) world)
								.playSound(null, new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("tcorp:ominouseffect")),
										SoundCategory.PLAYERS, (float) 2, (float) 1);


						if (world.isEmptyBlock(new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ())) && !world
								.isEmptyBlock(new BlockPos(entityiterator.getX(), entityiterator.getY() - 1, entityiterator.getZ()))) {
							world.setBlock(new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
									ChainsofjusticeBlock.block.defaultBlockState(), 3);
							if (!((World) world).isClientSide) {
								BlockPos _bp = new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ());
								TileEntity _tileEntity = world.getBlockEntity(_bp);
								BlockState _bs = world.getBlockState(_bp);
								if (_tileEntity != null)
									_tileEntity.getTileData().putDouble("team",
											((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
													.orElse(new EgoWeaponsModVars.PlayerVariables())).teamid));
								if (world instanceof World)
									((World) world).sendBlockUpdated(_bp, _bs, _bs, 3);
							}
						} else if (world.isEmptyBlock(new BlockPos(entityiterator.getX(), entityiterator.getY() - 1, entityiterator.getZ()))
								&& !world
										.isEmptyBlock(new BlockPos(entityiterator.getX(), entityiterator.getY() - 2, entityiterator.getZ()))) {
							world.setBlock(new BlockPos(entityiterator.getX(), entityiterator.getY() - 1, entityiterator.getZ()),
									ChainsofjusticeBlock.block.defaultBlockState(), 3);
							if (!((World) world).isClientSide) {
								BlockPos _bp = new BlockPos(entityiterator.getX(), entityiterator.getY() - 1, entityiterator.getZ());
								TileEntity _tileEntity = world.getBlockEntity(_bp);
								BlockState _bs = world.getBlockState(_bp);
								if (_tileEntity != null)
									_tileEntity.getTileData().putDouble("team",
											((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
													.orElse(new EgoWeaponsModVars.PlayerVariables())).teamid));
								if (world instanceof World)
									((World) world).sendBlockUpdated(_bp, _bs, _bs, 3);
							}
						}
					}
					if (limit > 4) {
						break;
					}
				}
			}
		}
		if (limit > 0) {
			{
				double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new EgoWeaponsModVars.PlayerVariables())).light - 3);
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.light = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			EgoWeaponsModVars.MapVariables.get(world).screenoverlaytype = "entrapment";
			EgoWeaponsModVars.MapVariables.get(world).syncData(world);
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
					EgoWeaponsModVars.MapVariables.get(world).screenoverlaytype = "";
					EgoWeaponsModVars.MapVariables.get(world).syncData(world);
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 60);
			{
				List<? extends PlayerEntity> _players = new ArrayList<>(world.players());
				for (Entity entityiterator : _players) {
					if (entityiterator instanceof LivingEntity)
						((LivingEntity) entityiterator).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 70, (int) 4, (false), (false)));
					if (entityiterator instanceof LivingEntity)
						((LivingEntity) entityiterator).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 4, (false), (false)));
				}
			}
		}
		{
			double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).light - 3);
			entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.light = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).blipcooldown < 25) {
			{
				double _setval = 25;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
