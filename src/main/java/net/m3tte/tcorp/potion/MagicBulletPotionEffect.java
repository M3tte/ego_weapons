
package net.m3tte.tcorp.potion;

import net.m3tte.tcorp.particle.ShadowpuffParticle;
import net.m3tte.tcorp.world.capabilities.EmotionSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagicBulletPotionEffect {
	@ObjectHolder("tcorp:magic_bullet")
	public static final Effect potion = null;

	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: MAGIC BULLET");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -16777216);
			setRegistryName("magic_bullet");
		}

		@Override
		public String getDescriptionId() {
			return "effect.magic_bullet";
		}

		@Override
		public boolean isBeneficial() {
			return true;
		}

		@Override
		public boolean isInstantenous() {
			return false;
		}

		@Override
		public boolean shouldRenderInvText(EffectInstance effect) {
			return false;
		}

		@Override
		public boolean shouldRender(EffectInstance effect) {
			return false;
		}

		@Override
		public boolean shouldRenderHUD(EffectInstance effect) {
			return false;
		}

		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}

		@Override
		public void applyEffectTick(LivingEntity entity, int amplifier) {
			super.applyEffectTick(entity, amplifier);
			World world = entity.level;

			if (!(entity instanceof PlayerEntity)) {
				return;
			}

			PlayerEntity player = (PlayerEntity) entity;

			if (EmotionSystem.getEmotionLevel(player) == 0 && EmotionSystem.getEmotionPoints(player) < 2 && EmotionSystem.getEmotionPoints(player) > 0) {
				entity.removeEffect(this);
			}
			else if (entity.getEffect(this).getDuration() <= 1000) {
				entity.getEffect(this).update(new EffectInstance(this, 2000, amplifier));
			}



			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(ShadowpuffParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 2, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
			}
		}

	}

	public static void increaseMagicBullet(LivingEntity entity) {
		if (entity.hasEffect(MagicBulletPotionEffect.get())) {
			entity.getEffect(MagicBulletPotionEffect.get()).update(new EffectInstance(MagicBulletPotionEffect.get(), 2000, Math.min(entity.getEffect(MagicBulletPotionEffect.get()).getAmplifier()+1,6)));
		} else {
			entity.addEffect(new EffectInstance(MagicBulletPotionEffect.get(), 2000, 0));
		}
	}
}
