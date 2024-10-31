
package net.m3tte.ego_weapons.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MeatChunkParticle {
	public static final BasicParticleType particle = new BasicParticleType(false);

	@SubscribeEvent
	public static void registerParticleType(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().register(particle.setRegistryName("meat_chunk"));
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerParticle(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(particle, CustomParticleFactory::new);
	}

	@OnlyIn(Dist.CLIENT)
	private static class CustomParticle extends SpriteTexturedParticle {
		private final IAnimatedSprite spriteSet;
		private float angularVelocity;
		Random r = new Random();
		protected CustomParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite spriteSet) {
			super(world, x, y, z);
			this.angularVelocity = (float) this.r.nextFloat() - 0.5f;

			this.spriteSet = spriteSet;
			this.setSize((float) 0.6, (float) 0.6);
			this.quadSize *= (float) 1.2f;
			this.lifetime = 80;
			this.gravity = (float) 0.7;
			this.hasPhysics = true;
			this.xd = vx * 0.8;
			this.yd = vy * 0.4 + Math.abs(vy * 0.8);
			this.zd = vz * 0.8;
			this.setSprite(spriteSet.get(world.random));
		}


		@Override
		public IParticleRenderType getRenderType() {
			return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
		}

		@Override
		public void tick() {
			super.tick();
			this.oRoll = this.roll;
			this.roll += this.angularVelocity;

			if (this.age > 60) {
				this.alpha = 1 - ((float) (this.age - 40) / 20);
			}


			if (this.isAlive()) {
				this.xd *= 0.95;
				this.yd *= 0.95;
				this.zd *= 0.95;
				this.angularVelocity *= 0.95f;

				if (this.onGround) {
					this.xd *= 0.7;
					this.yd *= 0;
					this.zd *= 0.7;
					this.angularVelocity = 0;
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static class CustomParticleFactory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public CustomParticleFactory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Nullable
		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
				double zSpeed) {
			return new CustomParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}


	}
}
