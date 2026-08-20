
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MugaDeathPrevention {
	@ObjectHolder("ego_weapons:muga_death_prevention")
	public static final Effect potion = null;






	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: muga_death_prevention");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectImpl());
	}

	public static boolean testEffectPresence(LivingEntity ent, float amount) {
		if (ent.hasEffect(get())) {
			int potency = ent.getEffect(get()).getAmplifier();
			int time = ent.getEffect(get()).getDuration();
			// System.out.println("PREVENTED DEATH WITH : "+potency+"//"+time);
			ent.removeEffect(get());
			if (potency > 0) {
				ent.addEffect(new EffectInstance(get(), time, potency-1));
			}

			if (!ent.level.isClientSide())
				EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendShakeMessage(ent.getId(), 2 + (amount / 50f)));


			ent.setHealth(1);
			return true;
		}

		return false;
	}

	private static class EffectImpl extends Effect {


		public EffectImpl() {
			super(EffectType.HARMFUL, -16777216);
			setRegistryName("muga_death_prevention");
		}

		@Override
		public String getDescriptionId() {
			return "effect.muga_death_prevention";
		}

		@Override
		public boolean isBeneficial() {
			return false;
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
		public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
			super.applyEffectTick(entity, p_76394_2_);
		}

	}




}
