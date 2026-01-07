
package net.m3tte.ego_weapons.item.rat;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.DialogueSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class RatKnife extends EgoWeaponsWeapon {

	private static IItemTier ratTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.0f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 5;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 2;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}

	};

	public RatKnife(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(ratTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public String getDefaultKillIdentifier() {
		return "rat_shank";
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by ???").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 6) + 1) + "/6] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.10"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 6) {
			case 0:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"haste"});
                else
                    generateDescription(list, "rat_shank", "passive", 3);
                break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "haste"});
				else
					generateDescription(list,"rat_shank", "ability", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "haste"});
				else
					generateDescription(list,"rat_shank", "innate", 3);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "bleed", "haste"});
				else
					generateDescription(list,"rat_shank", "auto", 4);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "haste"});
				else
					generateDescription(list,"rat_shank", "dash", 2);
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"haste", "offense_up"});
				else
					generateDescription(list,"rat_shank", "guard", 3);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}



	public static void interruptedAttack(LivingEntity target) {

	}

	@Override
	public void inventoryTick(ItemStack item, World p_77663_2_, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.inventoryTick(item, p_77663_2_, entity, p_77663_4_, p_77663_5_);

		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;

			if (living.getHealth() / living.getMaxHealth() <= 0.35f && living.tickCount % 20 == 0) {
				if (((LivingEntity) entity).getItemInHand(Hand.MAIN_HAND).equals(item)) {
					((LivingEntity) entity).addEffect(new EffectInstance(EgoWeaponsEffects.SPEED_UP.get(), 300, 0));
				}
			}

		}
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();


		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);


		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String attackIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			// Different animation effects
			switch (attackIdentifier) {
				case "rat_shank_sp_1":
					if (EgoWeaponsEffects.SPEED_UP.get().getPotency(sourceentity) >= 4) {
						boolean success = SharedFunctions.hitstunEntity(targetPatch, 3, false, 0.5f);

						EgoWeaponsEffects.BLEED.get().increment(target, 1, 2);
						Vector3d dist = sourceentity.position().subtract(target.position()).multiply(0.1f, 0.1f, 0.1f);


						target.setDeltaMovement(dist.x,dist.y,dist.z);
						itemstack.getOrCreateTag().putInt("followUpHit", success ? 2 : 1);
						itemstack.getOrCreateTag().putInt("stickEntityId", target.getId());
					} else {
						itemstack.getOrCreateTag().putInt("followUpHit", 0);
						EgoWeaponsEffects.BLEED.get().increment(target, 1, 2);
					}
					break;
				case "rat_shank_sp_2a":
				case "rat_shank_sp_2b":
					DialogueSystem.speakEvalDialogue(sourceentity, "dialogue.ego_weapons.skills.rat_shank.2", DialogueSystem.DialogueTypes.SKILL, TextFormatting.WHITE);

					EgoWeaponsEffects.BLEED.get().increment(target, 2, 3);
					break;
				case "rat_shank_auto2":
					EgoWeaponsEffects.SPEED_UP.get().increment(sourceentity, 0, 1);

					int infliction = Math.min(2,EgoWeaponsEffects.SPEED_UP.get().getPotency(sourceentity) / 2);

					if (infliction > 0) {
						EgoWeaponsEffects.BLEED.get().increment(target, infliction, infliction);
					}
					break;
				case "rat_shank_auto3":
					EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
					break;
				case "rat_shank_dash":
					EgoWeaponsEffects.SPEED_UP.get().increment(sourceentity, 2, 1);
					break;
				case "rat_shank_innate":

					if (entityData != null && sourceentity instanceof PlayerEntity && EgoWeaponsEffects.SPEED_UP.get().getPotency(sourceentity) >= 3) {

						if (entityData.onHitCounter <= 0) {
							entityData.onHitCounter = 5;
							EntityTick.regenerateLight((PlayerEntity) sourceentity, 1, true);
						}
						entityData.syncPlayerVariables(sourceentity);
					}
				break;

			}


		}

		return true;
	}


	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


		int hasteStacks = EgoWeaponsEffects.SPEED_UP.get().getPotency(source);

		if (hasteStacks > 0) {
			SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.25f, 0.05f * hasteStacks));
			mult += Math.min(0.25f, 0.05f * hasteStacks);
		}

		switch (weaponIdentifier) {
			case "rat_shank_sp_2a":
			case "rat_shank_sp_2b":
				SharedFunctions.incrementBonusDamage(damageSource, 0.5f);
				mult += 0.5f;
				break;
			case "rat_shank_innate":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f, 0.15f * hasteStacks));
				mult += Math.min(0.5f, 0.15f * hasteStacks);
				break;
		}



		return mult;
	}

	public static StaticAnimation.Event[] counterEvent1() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.55f, (entitypatch) -> {
			entitypatch.playAnimationSynchronized(OeufiAssocMovesetAnims.OEUFI_PARRY_COUNTER_ATTACK, 0);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}



}
