
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.procedures.legacy.FuriosoEffectExpiresProcedure;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FuriosoPotionEffect {
	@ObjectHolder("ego_weapons:furioso")
	public static final Effect potion = null;

	static AttributeModifier atkSpeedMod = new AttributeModifier(UUID.fromString("6cf3c7c3-9cce-438f-a273-e1b4d28947fe"),"furiosoAtkSpeed", 0.2, AttributeModifier.Operation.ADDITION);

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -3407872);
			setRegistryName("furioso");
		}

		@Override
		public String getDescriptionId() {
			return "effect.furioso";
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
		public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
			super.removeAttributeModifiers(entity, attributeMapIn, amplifier);

			ModifiableAttributeInstance atkSpeedInstance = attributeMapIn.getInstance(Attributes.ATTACK_SPEED);

			if (atkSpeedInstance != null)
				atkSpeedInstance.removeModifier(atkSpeedMod);

			World world = entity.level;
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();

			FuriosoEffectExpiresProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
			attributeMapIn.save();
		}

		@Override
		public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.addAttributeModifiers(living, attrman, amplifier);

			ModifiableAttributeInstance atkSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);

			if (atkSpeedInstance != null) {
				atkSpeedInstance.removeModifier(atkSpeedMod);
				atkSpeedInstance.addPermanentModifier(new AttributeModifier(atkSpeedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, atkSpeedMod), atkSpeedMod.getOperation()));

			}

			attrman.save();
		}


		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}
	}
}
