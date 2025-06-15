
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsAttributes;
import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
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

import java.util.Objects;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Staggered {
	@ObjectHolder("ego_weapons:staggered")
	public static final Effect potion = null;

	public static Effect get() {
		Objects.requireNonNull(potion, () -> "Registry Object not present: STAGGER");
		return potion;
	}

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.HARMFUL, -16777216);
			setRegistryName("staggered");
		}

		@Override
		public String getDescriptionId() {
			return "effect.staggered";
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
					entity.getPersistentData().putDouble("stagger",0);
				}
			}

		}

		@Override
		public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager p_111187_2_, int p_111187_3_) {
			super.removeAttributeModifiers(entity, p_111187_2_, p_111187_3_);

			if (!entity.hasEffect(this)) {
				if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.SUIT_OF_THE_BLACK_SILENCE.get())) {
					EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(entity, 0, 2);
					EgoWeaponsEffects.POWER_UP.get().increment(entity, 0, 2);
				}
			}


		}
	}




}
