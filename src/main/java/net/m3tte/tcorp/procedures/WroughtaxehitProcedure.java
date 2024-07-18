package net.m3tte.tcorp.procedures;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.particle.ReddamageslashParticle;
import net.m3tte.tcorp.potion.WroughtwindupPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class WroughtaxehitProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Wroughtaxehit!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Wroughtaxehit!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Wroughtaxehit!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Wroughtaxehit!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Wroughtaxehit!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				TcorpMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Wroughtaxehit!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(ReddamageslashParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
					(entity.getZ()), (int) 2, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
		}
		if (new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity) {
					return ((LivingEntity) entity).hasEffect(WroughtwindupPotionEffect.potion);
				}
				return false;
			}
		}.check(sourceentity)) {
			if (!world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
						SoundCategory.PLAYERS, (float) 1, (float) 1);
			}
			if (sourceentity instanceof LivingEntity) {
				((LivingEntity) sourceentity).removeEffect(WroughtwindupPotionEffect.potion);
			}
			if (sourceentity instanceof LivingEntity) {
				((LivingEntity) sourceentity).removeEffect(Effects.DAMAGE_BOOST);
			}
		}
	}
}
