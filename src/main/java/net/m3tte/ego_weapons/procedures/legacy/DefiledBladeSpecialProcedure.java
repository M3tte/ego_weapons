package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.particle.ReddamageslashParticle;
import net.m3tte.ego_weapons.particle.DamagefxParticle;
import net.m3tte.ego_weapons.particle.BlipeffectParticle;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Collection;

public class DefiledBladeSpecialProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure DefiledBladeSpecial!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure DefiledBladeSpecial!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure DefiledBladeSpecial!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure DefiledBladeSpecial!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure DefiledBladeSpecial!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		double blipcooldown = 0;
		{
			double _setval = ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new EgoWeaponsModVars.PlayerVariables())).blips - 4);
			entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.blips = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		if (!world.isClientSide()) {
			if (world instanceof World && !((World) world).isClientSide) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
						SoundCategory.PLAYERS, (float) 2, (float) 2);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:fingersnap")),
						SoundCategory.PLAYERS, (float) 2, (float) 2, false);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlipeffectParticle.particle, x, (y + 1), z, (int) 8, 0.3, 0.6, 0.3, 0);
			}
		}
		{
			List<Entity> _entfound = world
					.getEntities(entity,
							new AxisAlignedBB(x - (14 / 2d), y - (14 / 2d), z - (14 / 2d), x + (14 / 2d), y + (14 / 2d), z + (14 / 2d)), null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.distanceToSqr(_x, _y, _z)));
						}
					}.compareDistOf(x, y, z)).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (new Object() {
					boolean check(Entity _entity) {
						if (_entity instanceof LivingEntity) {
							Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
							for (EffectInstance effect : effects) {
								if (effect.getEffect() == Effects.POISON)
									return true;
							}
						}
						return false;
					}
				}.check(entityiterator)) {
					if (world instanceof World && !((World) world).isClientSide) {
						((World) world).playSound(null, new BlockPos(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit")),
								SoundCategory.PLAYERS, (float) 2, (float) 1);
					} else {
						((World) world).playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()),
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.trident.hit")),
								SoundCategory.PLAYERS, (float) 2, (float) 1, false);
					}
					entityiterator.hurt(DamageSource.GENERIC, (float) 1);
					if (entity instanceof LivingEntity)
						((LivingEntity) entity)
								.setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) + 2));
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(ReddamageslashParticle.particle, (entityiterator.getX()),
								(entityiterator.getY() + entityiterator.getBbHeight() / 2), (entityiterator.getZ()), (int) 3,
								(entityiterator.getBbWidth() / 1.4), (entityiterator.getBbHeight() / 2.5), (entityiterator.getBbWidth() / 1.4), 0);
					}
					if (world instanceof ServerWorld) {
						((ServerWorld) world).sendParticles(DamagefxParticle.particle, (entityiterator.getX()),
								(entityiterator.getY() + entityiterator.getBbHeight() / 2), (entityiterator.getZ()), (int) 7,
								(entityiterator.getBbWidth() / 1.4), (entityiterator.getBbHeight() / 2.5), (entityiterator.getBbWidth() / 1.4), 0);
					}
					if (entityiterator instanceof LivingEntity) {
						((LivingEntity) entityiterator).removeEffect(Effects.POISON);
					}
				}
			}
		}
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) > ((entity instanceof LivingEntity)
				? ((LivingEntity) entity).getMaxHealth()
				: -1)) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMaxHealth() : -1));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).addEffect(new EffectInstance(Effects.ABSORPTION, (int) 100, (int) 1));
		}
		if ((entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new EgoWeaponsModVars.PlayerVariables())).blipcooldown < 6) {
			{
				double _setval = 6;
				entity.getCapability(EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.blipcooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
