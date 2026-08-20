
package net.m3tte.ego_weapons.specialParticles.hit;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.m3tte.ego_weapons.client.renderer.EgoWeaponsRenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RendTargetParticle extends HitParticle {

	private float heightOffset;

	private Entity boundEntity = null;
	public RendTargetParticle(ClientWorld world, double x, double y, double z, double xSpeed, double entityUUID, double zSpeed, IAnimatedSprite animatedSprite) {
		super(world, x, y, z, animatedSprite);
		Random rand = new Random();
		float angle = rand.nextFloat() * 160 - 80;

		float totalRotRadians = (float) Math.toRadians(angle + 45);

		this.rCol = 0.5f + (float) (Math.cos(totalRotRadians) * 0.5f);
		this.gCol = 0.5f + (float) (Math.sin(totalRotRadians) * 0.5f);

		this.bCol = 0.6F;
		this.quadSize = 0.85F + rand.nextFloat() * 0.35f;
		this.lifetime = 400;


		if (entityUUID == 0) {
			if (world.isClientSide()) {
				boundEntity = Minecraft.getInstance().player;
			}
		} else {
			boundEntity = this.level.getEntity((int)entityUUID);
		}



		if (boundEntity != null) {
			float targetHeight = boundEntity.getBbHeight();

			this.heightOffset = targetHeight * 0.25f + rand.nextFloat() * targetHeight * 0.6f;
		}

		angle = (float)Math.toRadians(rand.nextFloat() * 20.0F + angle - 20f);
		this.oRoll = angle;
		this.roll = angle;

		synchronized (EgoWeaponsRenderSystem.getDistortionParticles()) {
			EgoWeaponsRenderSystem.getDistortionParticles().add(this);
		}
	}

	@Override
	public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float tickDelta) {
		this.alpha = (float) (1 - Math.sin(((this.age + tickDelta) / this.lifetime)*1.57f));

		super.render(vertexBuilder, renderInfo, tickDelta);
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.NO_RENDER;
	}

	@Override
	public void remove() {
		super.remove();

		synchronized (EgoWeaponsRenderSystem.getDistortionParticles()) {
			EgoWeaponsRenderSystem.getDistortionParticles().remove(this);
		}
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

		if (boundEntity != null && this.level.isClientSide()) {
			Vector3d entityPos = boundEntity.position();

			this.x = entityPos.x;
			this.y = entityPos.y + this.heightOffset;
			this.z = entityPos.z;

			if (!boundEntity.isAlive()) {
				this.remove();
			}
		} else {
			this.remove();
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
			RendTargetParticle particle = new RendTargetParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
			return particle;
		}
	}
}



