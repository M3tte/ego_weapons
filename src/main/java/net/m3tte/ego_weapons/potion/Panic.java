
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.UUID;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Panic {
	@ObjectHolder("ego_weapons:panic")
	public static final Effect potion = null;






	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: PANIC");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {


		static AttributeModifier speedModifier = new AttributeModifier(UUID.fromString("fc415d98-930e-4a92-88d9-6ce88ebff984"), "speedModInsanity", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		static AttributeModifier damageModifier = new AttributeModifier(UUID.fromString("fc415c98-930e-4a92-88d9-6ce88ebff984"), "damageModInsanity", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);
		static AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.fromString("fc415c98-920e-4c92-88c9-6ce88ebff984"), "atSpeedModInsanity", -0.6, AttributeModifier.Operation.MULTIPLY_TOTAL);
		static AttributeModifier impactMod = new AttributeModifier(UUID.fromString("fc415c94-930e-4c92-83c9-6ce88ebff984"), "impactModInsanity", -1, AttributeModifier.Operation.MULTIPLY_TOTAL);

		@Override
		public void addAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.addAttributeModifiers(living, attrman, amplifier);



			ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);
			ModifiableAttributeInstance damageInst = attrman.getInstance(Attributes.ATTACK_DAMAGE);
			ModifiableAttributeInstance attackSpInst = attrman.getInstance(Attributes.ATTACK_SPEED);
			ModifiableAttributeInstance impactInst = attrman.getInstance(EpicFightAttributes.IMPACT.get());


			if (speedInst != null) {
				speedInst.removeModifier(speedModifier);
				speedInst.addPermanentModifier(new AttributeModifier(speedModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(1, speedModifier), speedModifier.getOperation()));
			}

			if (damageInst != null) {
				damageInst.removeModifier(damageModifier);
				damageInst.addPermanentModifier(new AttributeModifier(damageModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(1, damageModifier), damageModifier.getOperation()));
			}

			if (attackSpInst != null) {
				attackSpInst.removeModifier(attackSpeedModifier);
				attackSpInst.addPermanentModifier(new AttributeModifier(attackSpeedModifier.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(1, attackSpeedModifier), attackSpeedModifier.getOperation()));
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
			ModifiableAttributeInstance speedInst = attrman.getInstance(Attributes.MOVEMENT_SPEED);
			ModifiableAttributeInstance damageInst = attrman.getInstance(Attributes.ATTACK_DAMAGE);
			ModifiableAttributeInstance attackSpInst = attrman.getInstance(Attributes.ATTACK_SPEED);
			ModifiableAttributeInstance impactInst = attrman.getInstance(EpicFightAttributes.IMPACT.get());

			if (speedInst != null)
				speedInst.removeModifier(speedModifier);

			if (damageInst != null)
				damageInst.removeModifier(damageModifier);

			if (attackSpInst != null)
				attackSpInst.removeModifier(attackSpeedModifier);

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

		public EffectCustom() {
			super(EffectType.HARMFUL, -16777216);
			setRegistryName("panic");
		}

		@Override
		public String getDescriptionId() {
			return "effect.panic";
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

			if (entity instanceof PlayerEntity) {
				EgoWeaponsModVars.PlayerVariables playerVars = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
				PlayerPatch<?> entitypatch = (PlayerPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
				if (entity != null && playerVars != null) {
					if (playerVars.globalcooldown <= 5) {
						playerVars.globalcooldown = 5;
						playerVars.syncPlayerVariables(entity);
						if (entitypatch != null) {
							entitypatch.setStamina(0);
						}

					}

					if (entity.getEffect(this).getDuration() < 5) {
						playerVars.stagger = EgoWeaponsAttributes.getMaxStagger(entity);
						playerVars.syncStagger(entity);
					}
				}

			} else {
				if (entity.getEffect(this).getDuration() < 5) {
					entity.getPersistentData().putDouble("panic",0);
				}
			}

		}

	}




}
