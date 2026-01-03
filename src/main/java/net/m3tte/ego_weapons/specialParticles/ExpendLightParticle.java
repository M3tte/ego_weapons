
package net.m3tte.ego_weapons.specialParticles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ExpendLightParticle extends SpriteTexturedParticle {
	private final IAnimatedSprite spriteSet;
	Random r = new Random();

	float targetQuadSize = 0.1f;
	float rotationRate = 0;

	protected ExpendLightParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite spriteSet) {
		super(world, x, y, z);
		this.spriteSet = spriteSet;
		this.setSize((float) 0.2, (float) 0.2);
		this.quadSize = 0;
		this.lifetime = 18 + r.nextInt(15);
		this.gravity = (float) 0;
		this.hasPhysics = false;
		this.roll = r.nextFloat() * 360;
		this.rotationRate = r.nextFloat() * 5 - 2.5f;
		this.oRoll = roll;
		this.xd = vx * 1;
		this.yd = vy * 1;
		this.zd = vz * 1;
		this.setSprite(spriteSet.get(world.random));
	}


	@Override
	protected int getLightColor(float p_189214_1_) {
		return 15728880;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_LIT;
	}

	@Override
	public void tick() {
		this.xd *= 0.9f;
		this.yd *= 0.9f;
		this.zd *= 0.9f;

		this.oRoll = this.roll;
		this.rotationRate *= 0.9f;

		super.tick();
		this.setSpriteFromAge(this.spriteSet);
	}

	@Override
	public void render(IVertexBuilder p_225606_1_, ActiveRenderInfo p_225606_2_, float partialTick) {
		super.render(p_225606_1_, p_225606_2_, partialTick);

		this.roll = this.oRoll + (this.rotationRate / 10) * partialTick;


		this.quadSize = this.targetQuadSize * Math.min(1, (this.age + partialTick) / 10);
	}

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Provider(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Nullable
		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
									   double zSpeed) {
			return new ExpendLightParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}
}

