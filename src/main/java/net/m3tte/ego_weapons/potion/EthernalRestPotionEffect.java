
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EthernalRestPotionEffect {
	@ObjectHolder("ego_weapons:ethernal_rest")
	public static final Effect potion = null;

	public static Effect get() {
		return potion;
	}

	static AttributeModifier speedMod = new AttributeModifier("solemnSpeed", 0.02, AttributeModifier.Operation.ADDITION);
	static AttributeModifier atkSpeedMod = new AttributeModifier("solemnAtkSpeed", 0.3, AttributeModifier.Operation.ADDITION);
	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -16777216);
			setRegistryName("ethernal_rest");
		}

		@Override
		public String getDescriptionId() {
			return "effect.ethernal_rest";
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

			speedInstance.removeModifier(speedMod);
			speedInstance.addPermanentModifier(new AttributeModifier(speedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, speedMod), speedMod.getOperation()));
			atkSpeedInstance.removeModifier(atkSpeedMod);
			atkSpeedInstance.addPermanentModifier(new AttributeModifier(atkSpeedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, atkSpeedMod), atkSpeedMod.getOperation()));

			attrman.save();
		}

		@Override
		public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.removeAttributeModifiers(living, attrman, amplifier);
			ModifiableAttributeInstance speedInstance = attrman.getInstance(Attributes.MOVEMENT_SPEED);
			ModifiableAttributeInstance atkSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);

			speedInstance.removeModifier(speedMod);
			atkSpeedInstance.removeModifier(atkSpeedMod);

			attrman.save();
		}

		@Override
		public void applyEffectTick(LivingEntity entity, int amplifier) {
			World world = entity.level;
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();

			if (world instanceof ServerWorld && entity.tickCount % 3 == 0) {
				((ServerWorld) world).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_LIVING_BUTTERFLY.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 1, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0.05);

				((ServerWorld) world).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_DEPARTED_BUTTERFLY.get(), (entity.getX()), (entity.getY() + entity.getBbHeight() / 2),
						(entity.getZ()), (int) 1, (entity.getBbWidth() / 2.5), (entity.getBbHeight() / 3), (entity.getBbWidth() / 2.5), 0.05);

			}

		}



		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}
	}
}
