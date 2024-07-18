package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Hand;
import net.minecraft.tags.ItemTags;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.DamagefxParticle;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.Comparator;

public class ZweilongswordabilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Zweilongswordability!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Zweilongswordability!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Zweilongswordability!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Zweilongswordability!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Zweilongswordability!");
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
							new AxisAlignedBB(x - (8 / 2d), y - (8 / 2d), z - (8 / 2d), x + (8 / 2d), y + (8 / 2d), z + (8 / 2d)), null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
						}
					}.compareDistOf(x, y, z)).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (entityiterator.isAlive() && (!(entityiterator instanceof PlayerEntity)
						|| (entityiterator.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new TcorpModVariables.PlayerVariables())).teamid != (entityiterator
										.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new TcorpModVariables.PlayerVariables())).teamid)) {
					target = entityiterator;
					break;
				}
			}
		}
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).swing(Hand.MAIN_HAND, true);
		}
		if (target.isAlive() && (!(target instanceof PlayerEntity) || (target.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).teamid != (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).teamid)) {
			{
				double _setval = ((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new TcorpModVariables.PlayerVariables())).blips - 4);
				entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blips = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (!world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(target.getX(), target.getY(), target.getZ()),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:metal_clash")),
						SoundCategory.PLAYERS, (float) 2, (float) 0.9);
				((World) world).playSound(null, new BlockPos(target.getX(), target.getY	(), target.getZ()),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.crit")),
						SoundCategory.PLAYERS, (float) 2, (float) 1.2);
				if (!((ItemStack.EMPTY)
						.getItem() == ((target instanceof LivingEntity) ? ((LivingEntity) target).getMainHandItem().getItem() : ItemStack.EMPTY)
						|| ItemTags.getAllTags().getTag(new ResourceLocation("tcorp:disarminvincible")).contains(
								((target instanceof LivingEntity) ? ((LivingEntity) target).getMainHandItem().getItem() : ItemStack.EMPTY.getItem())))) {
					if (Math.random() < 0.35) {
						((World) world).playSound(null, new BlockPos(target.getX(), target.getY(), target.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.break")),
								SoundCategory.PLAYERS, (float) 2, (float) 0.8);
						ItemEntity entityToSpawn = new ItemEntity((World) world, x, y, z,
								((target instanceof LivingEntity) ? ((LivingEntity) target).getMainHandItem() : ItemStack.EMPTY));
						entityToSpawn.setPickUpDelay((int) 40);
						world.addFreshEntity(entityToSpawn);
						if (target instanceof LivingEntity) {
							ItemStack _setstack = (ItemStack.EMPTY);
							_setstack.setCount((int) 1);
							((LivingEntity) target).setItemInHand(Hand.MAIN_HAND, _setstack);
							if (target instanceof ServerPlayerEntity)
								((ServerPlayerEntity) target).inventory.setChanged();
						}
					}
				}
			}
			if (target instanceof LivingEntity)
				((LivingEntity) target).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 60, (int) 2, (false), (false)));
			if (target instanceof LivingEntity)
				((LivingEntity) target).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 60, (int) 2, (false), (false)));
			if (target instanceof LivingEntity)
				((LivingEntity) target).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 30, (int) 1, (false), (false)));
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
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(DamagefxParticle.particle, (target.getX()), (target.getY() + 1), (target.getZ()),
						(int) 4, 0.1, 0.3, 0.1, 0);
			}
		}
	}
}
