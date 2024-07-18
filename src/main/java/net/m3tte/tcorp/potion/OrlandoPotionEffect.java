
package net.m3tte.tcorp.potion;

import net.m3tte.tcorp.procedures.BlacksilenceOnEffectActiveTickProcedure;
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
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OrlandoPotionEffect {
	@ObjectHolder("tcorp:orlando")
	public static final Effect potion = null;

	static AttributeModifier speedMod = new AttributeModifier("orlandoSpeed", 0.015, AttributeModifier.Operation.ADDITION);
	static AttributeModifier atkSpeedMod = new AttributeModifier("orlandoAtkSpeed", 0.2, AttributeModifier.Operation.ADDITION);
	static AttributeModifier armorMod = new AttributeModifier("orlandoArmor", 6, AttributeModifier.Operation.ADDITION);
	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -16777216);
			setRegistryName("orlando");
		}

		@Override
		public String getDescriptionId() {
			return "effect.orlando";
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
		public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.addAttributeModifiers(living, attrman, amplifier);

			ModifiableAttributeInstance speedInstance = attrman.getInstance(Attributes.MOVEMENT_SPEED);
			ModifiableAttributeInstance atkSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);
			ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ARMOR);

			speedInstance.removeModifier(speedMod);
			speedInstance.addPermanentModifier(new AttributeModifier(speedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, speedMod), speedMod.getOperation()));
			atkSpeedInstance.removeModifier(atkSpeedMod);
			atkSpeedInstance.addPermanentModifier(new AttributeModifier(atkSpeedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, atkSpeedMod), atkSpeedMod.getOperation()));
			armorInstance.removeModifier(armorMod);
			armorInstance.addPermanentModifier(new AttributeModifier(armorMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, armorMod), armorMod.getOperation()));

			attrman.save();
		}

		@Override
		public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.removeAttributeModifiers(living, attrman, amplifier);
			ModifiableAttributeInstance speedInstance = attrman.getInstance(Attributes.MOVEMENT_SPEED);
			ModifiableAttributeInstance atkSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);
			ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ARMOR);

			speedInstance.removeModifier(speedMod);
			atkSpeedInstance.removeModifier(atkSpeedMod);
			armorInstance.removeModifier(armorMod);

			attrman.save();
		}

		@Override
		public void applyEffectTick(LivingEntity entity, int amplifier) {
			World world = entity.level;
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();

			BlacksilenceOnEffectActiveTickProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}

		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}
	}
}
