
package net.m3tte.tcorp.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Shell {
	@ObjectHolder("tcorp:shell")
	public static final Effect potion = null;

	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: SHELL");
		return potion;
	}

	public static void incrementShell(LivingEntity entity, int amount) {
		if (!entity.hasEffect(get())) {
			entity.addEffect(new EffectInstance(get(), 200, amount-1));
			return;
		}


		int amplifier = Math.min(Objects.requireNonNull(entity.getEffect(get())).getAmplifier(), 4);
		entity.removeEffect(get());
		entity.addEffect(new EffectInstance(get(), 200, amplifier+amount));
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -16777216);
			setRegistryName("shell");
		}

		@Override
		public String getDescriptionId() {
			return "effect.shell";
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
			return true;
		}

		@Override
		public boolean shouldRender(EffectInstance effect) {
			return true;
		}

		@Override
		public boolean shouldRenderHUD(EffectInstance effect) {
			return true;
		}

		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}

		@Override
		public void applyEffectTick(LivingEntity entity, int amplifier) {
			super.applyEffectTick(entity, amplifier);
			World world = entity.level;
			if (entity.getEffect(this).getDuration() <= 10 && amplifier > 0) {
				entity.removeEffect(this);
				entity.addEffect(new EffectInstance(this, 100, amplifier-1));
				entity.playSound(SoundEvents.CHORUS_FLOWER_GROW, 1, 1);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).sendParticles(EpicFightParticles.BLOOD.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 2, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0);
			}
		}
	}


}
