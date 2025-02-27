
package net.m3tte.ego_weapons.potion;

import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.particle.RedFlame;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;
import java.util.UUID;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ManifestEgoPotionEffect {
	@ObjectHolder("ego_weapons:manifest_ego")
	public static final Effect potion = null;

	static AttributeModifier speedMod = new AttributeModifier(UUID.fromString("c1804411-ffcb-4b72-a4d7-ea21ba0828b4"),"kaliSpeed", 0.02, AttributeModifier.Operation.ADDITION);
	static AttributeModifier atkSpeedMod = new AttributeModifier(UUID.fromString("c1804411-ffcb-4b72-a4d7-ea21ba0828b4"),"kaliAtkSpeed", 0.2, AttributeModifier.Operation.ADDITION);
	static AttributeModifier dmgMod = new AttributeModifier(UUID.fromString("c1804411-ffcb-4b72-a4d7-ea21ba0828b4"),"kaliDMG", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);
	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		event.getRegistry().register(new EffectCustom());
	}

	public static class EffectCustom extends Effect {
		public EffectCustom() {
			super(EffectType.BENEFICIAL, -16777216);
			setRegistryName("manifest_ego");
		}

		@Override
		public String getDescriptionId() {
			return "effect.manifest_ego";
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
			ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ATTACK_DAMAGE);

			if (speedInstance != null) {
				speedInstance.removeModifier(speedMod);
				speedInstance.addPermanentModifier(new AttributeModifier(speedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, speedMod), speedMod.getOperation()));
			}

			if (atkSpeedInstance != null) {
				atkSpeedInstance.removeModifier(atkSpeedMod);
				atkSpeedInstance.addPermanentModifier(new AttributeModifier(atkSpeedMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, atkSpeedMod), atkSpeedMod.getOperation()));
			}

			if (armorInstance != null) {
				armorInstance.removeModifier(dmgMod);
				armorInstance.addPermanentModifier(new AttributeModifier(dmgMod.getId(), this.getDescriptionId() + " " + 0, this.getAttributeModifierValue(0, dmgMod), dmgMod.getOperation()));
			}

			attrman.save();
		}

		@Override
		public void removeAttributeModifiers(LivingEntity living, AttributeModifierManager attrman, int amplifier) {
			super.removeAttributeModifiers(living, attrman, amplifier);
			ModifiableAttributeInstance speedInstance = attrman.getInstance(Attributes.MOVEMENT_SPEED);
			ModifiableAttributeInstance atkSpeedInstance = attrman.getInstance(Attributes.ATTACK_SPEED);
			ModifiableAttributeInstance armorInstance = attrman.getInstance(Attributes.ATTACK_DAMAGE);

			if (speedInstance != null)
				speedInstance.removeModifier(speedMod);

			if (atkSpeedInstance != null)
				atkSpeedInstance.removeModifier(atkSpeedMod);

			if (armorInstance != null)
				armorInstance.removeModifier(dmgMod);

			if (living instanceof PlayerEntity) {

				EgoWeaponsModVars.PlayerVariables playerVars = living.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new EgoWeaponsModVars.PlayerVariables());

				playerVars.blips = 0;
				playerVars.blipcooldown = 20;

				playerVars.syncPlayerVariables(living);
			}

			attrman.save();
		}

		@Override
		public void applyEffectTick(LivingEntity entity, int amplifier) {
			World world = entity.level;
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();

			if (!(entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get())) || !(entity.getItemBySlot(EquipmentSlotType.LEGS).getItem().equals(EgoWeaponsItems.RED_MIST_EGO_LEGGINGS.get()))) {
				entity.removeEffect(ManifestEgoPotionEffect.potion);
			}

			LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			if (world instanceof ServerWorld) {
				Random random = new Random();
				EgoWeaponsAnimations.spawnArmatureParticle(entitypatch, 0, new Vector3d(-0.2 + random.nextFloat() * 0.4,-0.1 + random.nextFloat() * 0.6,-0.2 + random.nextFloat() * 0.5),90, RedFlame.particle,0,"Head",true);

			}

		}



		@Override
		public boolean isDurationEffectTick(int duration, int amplifier) {
			return true;
		}
	}
}
