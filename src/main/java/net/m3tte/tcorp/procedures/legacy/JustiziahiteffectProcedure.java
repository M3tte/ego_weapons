package net.m3tte.tcorp.procedures.legacy;

import net.m3tte.tcorp.TcorpMod;
import net.m3tte.tcorp.particle.BlackdamageParticle;
import net.m3tte.tcorp.particle.BlacksilenceshadowParticle;
import net.m3tte.tcorp.particle.JustiziahitParticle;
import net.m3tte.tcorp.particle.JustiziaparticleParticle;
import net.m3tte.tcorp.potion.JustiziafxPotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class JustiziahiteffectProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure Justiziahiteffect!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure Justiziahiteffect!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure Justiziahiteffect!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure Justiziahiteffect!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure Justiziahiteffect!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				TcorpMod.LOGGER.warn("Failed to load dependency sourceentity for procedure Justiziahiteffect!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).sendParticles(JustiziaparticleParticle.particle, (entity.getX()), (entity.getY() + entity.getZ() / 2),
					(entity.getZ()), (int) 4, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
		}
		if (new Object() {
			boolean check(Entity _entity) {
				if (_entity instanceof LivingEntity)
					return (((LivingEntity) entity).hasEffect(JustiziafxPotionEffect.potion));

				return false;
			}
		}.check(sourceentity)) {
			if (!world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
						SoundCategory.PLAYERS, (float) 1, (float) 0.9);
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:heavyslash")),
						SoundCategory.PLAYERS, (float) 1, (float) 0.7);
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:cross_slash")),
						SoundCategory.PLAYERS, (float) 1, (float) 0.3);
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.resonate")),
						SoundCategory.PLAYERS, (float) 1, (float) 0.4);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlacksilenceshadowParticle.particle, (entity.getX()),
						(entity.getY() + entity.getBbHeight() / 2), (entity.getZ()), (int) 60, (entity.getBbWidth() / 1.4),
						(entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0.05);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(JustiziahitParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 1.7),
						(entity.getZ()), (int) 1, 0, 0, 0, 0);
			}
			if (sourceentity instanceof LivingEntity) {
				((LivingEntity) sourceentity).removeEffect(JustiziafxPotionEffect.potion);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) ((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1)
						- ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getMaxHealth() : -1) * 0.3) - 20));
		} else {
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(BlackdamageParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 2, (entity.getBbWidth() / 1.4), (entity.getBbHeight() / 2.5), (entity.getBbWidth() / 1.4), 0);
			}
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) - 3));
		}
	}
}
