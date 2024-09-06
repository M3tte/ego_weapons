package net.m3tte.tcorp.procedures.legacy;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.ArmourupparticleParticle;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class ChainsofjusticecollideProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Chainsofjusticecollide!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Chainsofjusticecollide!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Chainsofjusticecollide!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Chainsofjusticecollide!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Chainsofjusticecollide!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (entity.isAlive() && ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) > 0) {
			if (!(entity instanceof PlayerEntity || entity instanceof ServerPlayerEntity) || new Object() {
				public double getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getBlockEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getDouble(tag);
					return -1;
				}
			}.getValue(world, new BlockPos(x, y, z), "team") != (entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new TcorpModVariables.PlayerVariables())).teamid) {
				entity.setDeltaMovement((entity.getDeltaMovement().x * 0), (entity.getDeltaMovement().y * 0), (entity.getDeltaMovement().z * 0));
				if (world instanceof ServerWorld) {
					((ServerWorld) world).sendParticles(ArmourupparticleParticle.particle, (entity.getX()),
							(entity.getY() + entity.getBbHeight() / 2), (entity.getZ()), (int) 1, (entity.getBbWidth() / 2),
							(entity.getBbHeight() / 2.5), (entity.getBbWidth() / 2), 0);
				}
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, (int) 10, (int) 3, (false), (false)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addEffect(new EffectInstance(Effects.WEAKNESS, (int) 10, (int) 3, (false), (false)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) 10, (int) 9, (false), (false)));
			}
		}
	}
}
