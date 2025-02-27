
package net.m3tte.ego_weapons.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SolemnLamentEffects {
	@ObjectHolder("ego_weapons:the_living")
	public static final SolemnLamentAmmoEffect living = null;
	@ObjectHolder("ego_weapons:the_departed")
	public static final SolemnLamentAmmoEffect departed = null;

	public static SolemnLamentAmmoEffect getLiving() {
		Objects.requireNonNull(living, () -> "Registry Object not present");
		return living;
	}

	public static SolemnLamentAmmoEffect getDeparted() {
		Objects.requireNonNull(departed, () -> "Registry Object not present");
		return departed;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new TheLiving());
		event.getRegistry().register(new TheDeparted());
	}


	public static class TheLiving extends SolemnLamentAmmoEffect {
		public TheLiving() {
			super();
			setRegistryName("the_living");
		}
	}

	public static class TheDeparted extends SolemnLamentAmmoEffect {
		public TheDeparted() {
			super();
			setRegistryName("the_departed");
		}
	}

	public abstract static class SolemnLamentAmmoEffect extends Effect {
		public SolemnLamentAmmoEffect() {
			super(EffectType.BENEFICIAL, -16777216);
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

			/*if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(ShadowpuffParticle.particle, (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 2, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
			}*/
		}

	}

	public static void decrementEffect(LivingEntity entity, SolemnLamentAmmoEffect targetEffect) {
		if (entity.hasEffect(targetEffect)) {

			if (entity.hasEffect(EternalRestPotionEffect.get()) && entity.getRandom().nextFloat() < 0.25f)
				return;

			EffectInstance effect = entity.getEffect(targetEffect);
			int amplifier = entity.getEffect(targetEffect).getAmplifier();
			if (amplifier > 0) {
				entity.removeEffect(targetEffect);
				entity.addEffect(new EffectInstance(targetEffect, 2000, amplifier-1));
			} else {
				entity.removeEffect(targetEffect);
			}
		}
	}

	public static int getAmmoCount(LivingEntity entity, SolemnLamentAmmoEffect targetEffect) {
		if (entity.hasEffect(targetEffect)) {
			return entity.getEffect(targetEffect).getAmplifier() + 1;
		} else {
			return 0;
		}
	}


	public static void setAmmoCount(LivingEntity entity, SolemnLamentAmmoEffect targetEffect, int ammoCount) {
		if (entity.hasEffect(targetEffect)) {
			entity.removeEffect(targetEffect);
		}
		entity.addEffect(new EffectInstance(targetEffect, 2000, ammoCount-1));
	}
}
