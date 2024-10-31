
package net.m3tte.ego_weapons.entities;

import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;

import net.minecraft.world.World;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.network.IPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;

import net.m3tte.ego_weapons.procedures.legacy.AtelierPistolHitProcedure;

import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;

public class AtelierPistolsBullet {

	//@OnlyIn(value = Dist.CLIENT)
	public static class AtelierPistolBulletProj extends AbstractArrowEntity {
		public AtelierPistolBulletProj(FMLPlayMessages.SpawnEntity packet, World world) {
			super((EntityType<? extends AbstractArrowEntity>) EgoWeaponsEntities.ATELIER_PISTOL_BULLET.get(), world);
		}

		public AtelierPistolBulletProj(EntityType<? extends AtelierPistolBulletProj> type, World world) {
			super(type, world);
		}

		public AtelierPistolBulletProj(EntityType<? extends AtelierPistolBulletProj> type, double x, double y, double z, World world) {
			super(type, x, y, z, world);
		}

		public AtelierPistolBulletProj(EntityType<? extends AtelierPistolBulletProj> type, LivingEntity entity, World world) {
			super(type, entity, world);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void doPostHurtEffects(LivingEntity entity) {
			super.doPostHurtEffects(entity);
			//entity.setArrowCount(entity.getArrowCount() - 1);
		}

		@Override
		protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
			super.onHitEntity(entityRayTraceResult);
			Entity entity = entityRayTraceResult.getEntity();
			Entity sourceentity = this.getEntity();
			Entity immediatesourceentity = this;
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;

			AtelierPistolHitProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity),
							new AbstractMap.SimpleEntry<>("sourceentity", sourceentity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}


		@Override
		public void tick() {
			super.tick();
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;
			Entity entity = this.getEntity();
			Entity immediatesourceentity = this;
			if (this.inGround)
				this.remove();
		}

		@Override
		protected ItemStack getPickupItem() {
			return ItemStack.EMPTY;
		}
	}
}
