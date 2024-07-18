package net.m3tte.tcorp.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.m3tte.tcorp.particle.CrimsonfanparticleParticle;
import net.m3tte.tcorp.item.CrimsonfanprojItem;
import net.m3tte.tcorp.item.CrimsonfanItem;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.TcorpMod;

import java.util.Map;

public class CrimsonfanprojEntitySwingsItemProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				TcorpMod.LOGGER.warn("Failed to load dependency world for procedure CrimsonfanprojEntitySwingsItem!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				TcorpMod.LOGGER.warn("Failed to load dependency x for procedure CrimsonfanprojEntitySwingsItem!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				TcorpMod.LOGGER.warn("Failed to load dependency y for procedure CrimsonfanprojEntitySwingsItem!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				TcorpMod.LOGGER.warn("Failed to load dependency z for procedure CrimsonfanprojEntitySwingsItem!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TcorpMod.LOGGER.warn("Failed to load dependency entity for procedure CrimsonfanprojEntitySwingsItem!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				TcorpMod.LOGGER.warn("Failed to load dependency itemstack for procedure CrimsonfanprojEntitySwingsItem!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (!((entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new TcorpModVariables.PlayerVariables())).globalcooldown > 0)) {
			if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getOffhandItem() : ItemStack.EMPTY)
					.getItem() == CrimsonfanItem.block) {
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 10);
				{
					double _setval = 10;
					entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.globalcooldown = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			} else {
				if (entity instanceof PlayerEntity)
					((PlayerEntity) entity).getCooldowns().addCooldown(itemstack.getItem(), (int) 20);
				{
					double _setval = 20;
					entity.getCapability(TcorpModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.globalcooldown = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
			{
				Entity _shootFrom = entity;
				World projectileLevel = _shootFrom.level;
				if (!projectileLevel.isClientSide) {
					ProjectileEntity _entityToSpawn = new Object() {
						public ProjectileEntity getArrow(World world, float damage, int knockback) {
							AbstractArrowEntity entityToSpawn = new CrimsonfanprojItem.ArrowCustomEntity(CrimsonfanprojItem.arrow, world);

							entityToSpawn.setBaseDamage(damage);
							entityToSpawn.setKnockback(knockback);
							entityToSpawn.setSilent(true);

							return entityToSpawn;
						}
					}.getArrow(projectileLevel, 2, 1);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.4, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			if (!world.isClientSide()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("tcorp:woosh")),
						SoundCategory.NEUTRAL, (float) 1, (float) 0.8);
				((ServerWorld) world).sendParticles(CrimsonfanparticleParticle.particle, (entity.getX()), (entity.getY() + 1),
						(entity.getZ()), (int) 6, 0.1, 0.5, 0.1, 0.06);
			}
		}
	}
}
