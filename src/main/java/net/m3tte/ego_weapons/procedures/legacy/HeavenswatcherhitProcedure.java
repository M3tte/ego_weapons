package net.m3tte.ego_weapons.procedures.legacy;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.potion.PutridtouchPotionEffect;
import net.m3tte.ego_weapons.particle.ReddamageslashParticle;
import net.m3tte.ego_weapons.block.PutridrootsBlock;
import net.m3tte.ego_weapons.block.FleshyrootsBlock;
import net.m3tte.ego_weapons.EgoWeaponsMod;

import java.util.Map;
import java.util.Collection;

public class HeavenswatcherhitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency world for procedure Heavenswatcherhit!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency x for procedure Heavenswatcherhit!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency y for procedure Heavenswatcherhit!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency z for procedure Heavenswatcherhit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency entity for procedure Heavenswatcherhit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				EgoWeaponsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Heavenswatcherhit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(ReddamageslashParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.5),
					(entity.getZ()), (int) 1, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 3), (entity.getBbWidth() / 1.4), 0);
		}
		if (new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					Collection<EffectInstance> effects = ((LivingEntity) _entity).getActiveEffects();
					return ((LivingEntity) entity).hasEffect(PutridtouchPotionEffect.potion);
				}
				return false;
			}
		}.check(sourceentity)) {
			if (world instanceof World && !world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chorus_flower.grow")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chorus_flower.grow")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
			}
			if (world instanceof World && !world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker_fangs.attack")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			} else {
				((World) world).playLocalSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.evoker_fangs.attack")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
			}
			if (sourceentity instanceof LivingEntity) {
				((LivingEntity) sourceentity).removeEffect(PutridtouchPotionEffect.potion);
			}
			if (world.isEmptyBlock(new BlockPos(x, y, z)) && !world.isEmptyBlock(new BlockPos(x, y - 1, z))) {
				world.setBlock(new BlockPos(x, y, z), PutridrootsBlock.block.defaultBlockState(), 3);
			} else if (world.isEmptyBlock(new BlockPos(x, y - 1, z)) && !world.isEmptyBlock(new BlockPos(x, y - 2, z))) {
				world.setBlock(new BlockPos(x, y - 1, z), PutridrootsBlock.block.defaultBlockState(), 3);
			}
		} else {
			if (Math.random() < 0.3) {
				entity.setDeltaMovement(0, 0, 0);
				if (world instanceof World && !((World) world).isClientSide) {
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chorus_flower.grow")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1);
				} else {
					((World) world).playLocalSound(x, y, z,
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chorus_flower.grow")),
							SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
				}
				if (world.isEmptyBlock(new BlockPos(x, y, z)) && !world.isEmptyBlock(new BlockPos(x, y - 1, z))) {
					world.setBlock(new BlockPos(x, y, z), FleshyrootsBlock.block.defaultBlockState(), 3);
				} else if (world.isEmptyBlock(new BlockPos(x, y - 1, z)) && !world.isEmptyBlock(new BlockPos(x, y - 2, z))) {
					world.setBlock(new BlockPos(x, y - 1, z), FleshyrootsBlock.block.defaultBlockState(), 3);
				}
			}
		}
	}
}
