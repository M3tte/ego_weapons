
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NoAmmo {
	@ObjectHolder("ego_weapons:no_ammo")
	public static final Effect potion = null;






	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: NO_AMMO");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectImpl());
	}

	private static class EffectImpl extends Effect {


		static AttributeModifier damageModifier = new AttributeModifier(UUID.fromString("fc415c98-930e-4c92-88d9-6ce83abff984"), "damageModStagger", -2, AttributeModifier.Operation.MULTIPLY_TOTAL);
		static AttributeModifier impactMod = new AttributeModifier(UUID.fromString("fc415c94-930e-4c92-88c9-6ce88ebfa984"), "impactModStagger", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);

		@Override
		public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.addAttributeModifiers(living, attrman, amplifier);



			ModifiableAttributeInstance damageInst = attrman.getInstance(Attributes.ATTACK_DAMAGE);
			ModifiableAttributeInstance impactInst = attrman.getInstance(EpicFightAttributes.IMPACT.get());




			if (damageInst != null) {
				damageInst.removeModifier(damageModifier);
				damageInst.addPermanentModifier(new AttributeModifier(damageModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(1, damageModifier), damageModifier.getOperation()));
			}



			if (impactInst != null) {
				impactInst.removeModifier(impactMod);
				impactInst.addPermanentModifier(new AttributeModifier(impactMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(1, impactMod), impactMod.getOperation()));
			}
			attrman.save();
		}

		@Override
		public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.removeAttributeModifiers(living, attrman, amplifier);
			ModifiableAttributeInstance damageInst = attrman.getInstance(Attributes.ATTACK_DAMAGE);
			ModifiableAttributeInstance impactInst = attrman.getInstance(EpicFightAttributes.IMPACT.get());



			if (damageInst != null)
				damageInst.removeModifier(damageModifier);



			if (impactInst != null)
				impactInst.removeModifier(impactMod);

			attrman.save();


			// Black Silence Unstagger Effect
			if (!living.hasEffect(this)) {
				if (living.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get())) {
					EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(living, 0, 2);
					EgoWeaponsEffects.POWER_UP.get().increment(living, 0, 2);
				}
			}
		}

		public EffectImpl() {
			super(EffectType.HARMFUL, -16777216);
			setRegistryName("no_ammo");
		}

		@Override
		public String getDescriptionId() {
			return "effect.no_ammo";
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
