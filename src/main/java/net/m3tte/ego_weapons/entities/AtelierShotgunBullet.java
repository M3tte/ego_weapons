
package net.m3tte.ego_weapons.entities;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
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

import net.m3tte.ego_weapons.procedures.legacy.AteliershotgunhitProcedure;

import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;

public class AtelierShotgunBullet {

	//@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
	public static class AtelierShotgunSlugProj extends AbstractArrowEntity {
		public AtelierShotgunSlugProj(FMLPlayMessages.SpawnEntity packet, World world) {
			super(EgoWeaponsEntities.ATELIER_SHOTGUN_BULLET.get(), world);
		}

		public AtelierShotgunSlugProj(EntityType<? extends AtelierShotgunSlugProj> type, World world) {
			super(type, world);
		}

		public AtelierShotgunSlugProj(EntityType<? extends AtelierShotgunSlugProj> type, double x, double y, double z, World world) {
			super(type, x, y, z, world);
		}

		public AtelierShotgunSlugProj(EntityType<? extends AtelierShotgunSlugProj> type, LivingEntity entity, World world) {
			super(type, entity, world);
		}

		@Override
		public IPacket<?> getAddEntityPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void doPostHurtEffects(LivingEntity entity) {
			super.doPostHurtEffects(entity);
		}

		@Override
		public void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
			super.onHitEntity(entityRayTraceResult);
			Entity entity = entityRayTraceResult.getEntity();
			Entity sourceentity = this.getEntity();
			Entity immediatesourceentity = this;
			double x = this.getX();
			double y = this.getY();
			double z = this.getZ();
			World world = this.level;
			if (entity instanceof LivingEntity) {
				EgoWeaponsEffects.RUPTURE.get().increment((LivingEntity) entity, 0, 3);
				TremorEffect.burstTremor((LivingEntity) entity, true);
			}

			AteliershotgunhitProcedure.executeProcedure(Stream
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
		protected ItemStack getPickupItem()  {
			return ItemStack.EMPTY;
		}
	}

}
