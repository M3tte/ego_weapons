
package net.m3tte.ego_weapons.specialParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class StaggerParticle extends HitParticle {

	private float angularVelocity;
	private float angularAcceleration;

	private Entity boundEntity = null;
	public StaggerParticle(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite animatedSprite) {
		super(world, x, y, z, animatedSprite);
		this.rCol = 1.0F;
		this.gCol = 1.0F;
		this.bCol = 1.0F;
		this.quadSize = 1.4F;
		this.lifetime = 70;

		Random rand = new Random();

		float angle = rand.nextFloat() * 20 - 10;
		if (entityUUID == 0) {
			if (world.isClientSide()) {
				boundEntity = Minecraft.getInstance().player;
			}
		} else {
			boundEntity = this.level.getEntity((int)entityUUID);
		}
		angle = (float)Math.toRadians(rand.nextFloat() * 20.0F + angle - 20f);
		this.oRoll = angle;
		this.roll = angle;

	}

	@Override
	public void tick() {
		super.tick();
		this.oRoll = this.roll;
		this.roll += this.angularVelocity;
		/*if (this.age < 20) {
			this.alpha = (float) this.age / 20;
		} else if (this.age < 60) {
			this.alpha = 1;
		} else {
			this.alpha =  1-(float) (this.age - 60) / 20;
		}*/

		if (this.age > 50) {
			this.alpha =  1-(float) (this.age - 50) / 20;
		}

		if (boundEntity != null && this.level.isClientSide()) {
			Vector3d entityPos = boundEntity.position();
			Vector3d newPos = new Vector3d(0,0,0);
			if (Minecraft.getInstance().cameraEntity != null) {
				//System.out.println("Camera entity location: "+Minecraft.getInstance().cameraEntity.position() + " | player | " + Minecraft.getInstance().player.position());
				Vector3d cameraPos = Minecraft.getInstance().cameraEntity.position();
				newPos = entityPos.add(cameraPos.subtract(entityPos).normalize().scale(0.5f));
			}

			this.x = newPos.x;
			this.y = newPos.y + boundEntity.getBbHeight() * 0.7f;
			this.z = newPos.z;
		}


	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Provider(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			StaggerParticle particle = new StaggerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
			return particle;
		}
	}
}



