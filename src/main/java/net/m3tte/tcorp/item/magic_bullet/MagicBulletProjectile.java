package net.m3tte.tcorp.item.magic_bullet;

import net.m3tte.tcorp.*;
import net.m3tte.tcorp.particle.ArmourupparticleParticle;
import net.m3tte.tcorp.particle.SonicwavefxParticle;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class MagicBulletProjectile {

    public static final EntityType<MagicBulletProj> bullet = (EntityType.Builder.<MagicBulletProj>of(MagicBulletProj::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).setCustomClientFactory(MagicBulletProj::new)
            .sized(0.5f, 0.5f)).build("projectile_magic_bullet");

    //@OnlyIn(value = Dist.CLIENT)
    public static class MagicBulletProj extends AbstractArrowEntity {
        public MagicBulletProj(FMLPlayMessages.SpawnEntity packet, World world) {
            super(bullet, world);
        }

        public MagicBulletProj(EntityType<? extends MagicBulletProj> type, World world) {
            super(type, world);
        }

        public MagicBulletProj(EntityType<? extends MagicBulletProj> type, double x, double y, double z, World world) {
            super(type, x, y, z, world);
        }

        public MagicBulletProj(EntityType<? extends MagicBulletProj> type, LivingEntity entity, World world) {
            super(type, entity, world);
        }

        @Override
        public IPacket<?> getAddEntityPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }


        @Override
        protected void doPostHurtEffects(LivingEntity entity) {
            super.doPostHurtEffects(entity);

            World world = entity.level;
            if (world instanceof ServerWorld) {
                ((ServerWorld) world).sendParticles(TCorpParticleRegistry.MAGIC_BULLET_IMPACT_HIT.get(), (entity.getX()), (entity.getY()),
                        (entity.getZ()), (int) 1, (entity.getBbWidth() / 7), (entity.getBbHeight() / 7), (entity.getBbWidth() / 7), 0);
            }

            //entity.setArrowCount(entity.getArrowCount() - 1);
        }

        @Override
        protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
            super.onHitEntity(entityRayTraceResult);


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

            world.addParticle(SonicwavefxParticle.particle, x,y,z, 0,0,0);
            world.addParticle(ArmourupparticleParticle.particle, x,y,z, 0,0,0);
            if (this.inGround)
                this.remove();
        }

        @Override
        protected ItemStack getPickupItem() {
            return ItemStack.EMPTY;
        }
    }

    public static void shoot(LivingEntity entity, int pierce, boolean hitself) {
        AbstractArrowEntity projectile = new MagicBulletProj(bullet, entity, entity.level);


        if (pierce <= 0 && hitself)
            return;

        float suitMult = 1;

        if (TCorpItems.MAGIC_BULLET_CLOAK.get().equals(entity.getItemBySlot(EquipmentSlotType.CHEST).getItem())); {
            suitMult = 1.25f;
        }

        projectile.shoot( entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3, 0);
        projectile.setSilent(true);
        projectile.setBaseDamage((7 + pierce * 1.2) * suitMult);
        projectile.setKnockback(0);
        projectile.setCritArrow(false);
        projectile.setPierceLevel((byte) (pierce - (hitself ? 1 : 0)));
        projectile.setNoGravity(true);
        entity.level.addFreshEntity(projectile);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        SoundEvent fireSound = TCorpSounds.MAGIC_BULLET_FIRE_1;

        if ((pierce+1) >= 6) {
            fireSound = TCorpSounds.MAGIC_BULLET_FIRE_3;
        } else if ((pierce+1) >= 3) {
            fireSound = TCorpSounds.MAGIC_BULLET_FIRE_2;
        }

        entity.level.playSound(null, x, y, z,
                fireSound,
                SoundCategory.PLAYERS, 2, 1f / (new Random().nextFloat() * 0.5f + 1));
    }
}
