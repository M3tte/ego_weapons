package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.FunctionObject;

import net.m3tte.tcorp.particle.PurpleteleportParticle;
import net.m3tte.tcorp.particle.ClockparticleParticle;
import net.m3tte.tcorp.particle.BlipeffectParticle;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class DisposalcutterabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Disposalcutterability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Disposalcutterability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Disposalcutterability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Disposalcutterability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Disposalcutterability!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity target = null;
		Entity targetentity = null;
		target = entity;
		{
			List<Entity> _entfound = world
					.getEntities(entity,
							new AxisAlignedBB(x - (20 / 2d), y - (20 / 2d), z - (20 / 2d), x + (20 / 2d), y + (20 / 2d), z + (20 / 2d)), null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
						}
					}.compareDistOf(x, y, z)).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof LivingEntity && (!(entityiterator instanceof PlayerEntity)
						|| (entityiterator.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).teamid != (entityiterator
										.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new TcorpModVariables.PlayerVariables())).teamid)) {
					target = entityiterator;
					break;
				}
			}
		}
		if (target.isAlive() && (!(target instanceof PlayerEntity) || (target.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).teamid != (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).teamid)) {
			{
				double _setval = ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips - 5);
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blips = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (!world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2);
				}
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(ClockparticleParticle.particle, x, (y + 1), z, (int) 8, 0.4, 0.6, 0.4, 0);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PurpleteleportParticle.particle, x, (y + 1), z, (int) 1, 0, 0, 0, 0);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.DAMAGE_BOOST, (int) 40, (int) 2, (false), (false)));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, (int) 40, (int) 2, (false), (false)));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.DIG_SPEED, (int) 40, (int) 1, (false), (false)));
			if ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new TcorpModVariables.PlayerVariables())).blipcooldown < 6) {
				{
					double _setval = 6;
					entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.blipcooldown = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
			entity.fallDistance = (float) (0);
			target.setDeltaMovement(0, 0, 0);
			{
				Entity _ent = entity;
				_ent.setPos((target.getX()), (target.getEyeY() + 4), (target.getZ()));
				if (_ent instanceof ServerPlayerEntity) {
					((ServerPlayerEntity) _ent).connection.teleport((target.getX()), (target.getY() + 4), (target.getZ()),
							((ServerPlayerEntity) _ent).xRot, _ent.yRot, Collections.emptySet());
				}
			}
			if (world instanceof World && !((World) world).isClientSide) {
				((World) world).playSound(null, new BlockPos(target.getX(), target.getY(), target.getZ()),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2);
			} else {
				((World) world).playLocalSound((target.getX()), (target.getY()), (target.getZ()),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2, false);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(PurpleteleportParticle.particle, (target.getX()), (target.getY() + 5), (target.getZ()),
						(int) 1, 0, 0, 0, 0);
			}
			if (!entity.level.isClientSide && entity.level.getServer() != null) {
				Optional<FunctionObject> _fopt = entity.level.getServer().getFunctions().get(new ResourceLocation("tcorp:disposalcutterfunc"));
				if (_fopt.isPresent()) {
					FunctionObject _fobj = _fopt.get();
					entity.level.getServer().getFunctions().execute(_fobj, entity.createCommandSourceStack());
				}
			}
		}
	}

