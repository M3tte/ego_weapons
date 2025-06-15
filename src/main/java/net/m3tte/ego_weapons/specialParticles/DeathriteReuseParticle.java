
package net.m3tte.ego_weapons.specialParticles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class DeathriteReuseParticle extends HitParticle {


	public DeathriteReuseParticle(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite animatedSprite) {
		super(world, x, y, z, animatedSprite);
		this.rCol = 1.0F;
		this.gCol = 1.0F;
		this.bCol = 1.0F;
		this.quadSize = 1.1F;
		this.lifetime = 15;

		Random rand = new Random();

		float angle = rand.nextFloat() * 20 - 10;
		this.oRoll = angle;
		this.roll = angle;

	}


	@Override
	public void tick() {
		super.tick();
		/*if (this.age < 20) {
			this.alpha = (float) this.age / 20;
		} else if (this.age < 60) {
			this.alpha = 1;
		} else {
			this.alpha =  1-(float) (this.age - 60) / 20;
		}*/

		if (this.age > 10) {
			this.alpha =  1-(float) (this.age - 10) / 5;
		}
	}

	@Override
	public boolean shouldCull() {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Provider(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			DeathriteReuseParticle particle = new DeathriteReuseParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
			return particle;
		}
	}
}



