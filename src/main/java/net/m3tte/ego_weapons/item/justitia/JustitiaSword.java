
package net.m3tte.ego_weapons.item.justitia;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.JustitiaMovesetAnims;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.DelayedEvent;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.m3tte.ego_weapons.world.capabilities.damage.DirectEgoDamageSource;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class JustitiaSword extends EgoWeaponsWeapon {

	private static IItemTier maoSwordTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 3.8f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 7;
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

	public JustitiaSword(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(maoSwordTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);

		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.justitia_cloak.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"sin"});
				else
					generateDescription(list,"justitia", "passive", 4);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale", "sin", "offense_up", "offense_down", "fragile", "sealed"});
				else
					generateDescription(list,"justitia", "ability", 12);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale", "sin", "rupture"});
				else {
					generateDescription(list,"justitia", "innate", 7);
				}
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale", "rupture", "sin", "offense_up"});
				else
					generateDescription(list,"justitia", "auto", 6);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"sin"});
				else
					generateDescription(list,"justitia", "guard", 6);
				break;
		}

		generateStatusHelp(list);
	}



	public static void onHitHaste(LivingEntity entity, int value, PlayerVariables entityData, int timeout) {
		if (entityData != null) {
			if (entityData.onHitCounter > 0)
				return;
			else {
				entityData.onHitCounter = timeout;
				entityData.syncPlayerVariables(entity);
			}
			EgoWeaponsEffects.SPEED_UP.get().increment(entity, 0, value);
		}
	}

	public static void onHitHasteConsumeLight(LivingEntity entity, PlayerVariables entityData, int timeout) {
		if (entityData != null) {

			if (EgoWeaponsEffects.speedMult(entity) >= 7)
				return;

			if (entityData.onHitCounter > 0 || entityData.light < 1)
				return;
			else {
				entityData.onHitCounter = timeout;
				entityData.light--;
				entityData.syncPlayerVariables(entity);
			}
			EgoWeaponsEffects.SPEED_UP.get().increment(entity, 0, 1);
		}
	}



	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();


		World world = target.level;

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));



			int sourceSpeed = EgoWeaponsEffects.speedMult(sourceentity);
			int targetSpeed = EgoWeaponsEffects.speedMult(target);

			int speedDif = Math.max(0, sourceSpeed - targetSpeed);

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			int targetSin = EgoWeaponsEffects.SIN.get().getPotency(target);
			int sourceSin = EgoWeaponsEffects.SIN.get().getPotency(sourceentity);

			switch (context.getAnimationIdentifier()) {
				case "justitia_auto1":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 1);

					break;
				case "justitia_auto2":
					if (targetSin >= 2) {
						EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(sourceentity, 3, 1);
					}
					break;
				case "justitia_auto3":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 2, 1);

					if (entityData != null) {
						if (entityData.light > 0) {
							entityData.light -= 1;

							EgoWeaponsEffects.SIN.get().increment(target, 0, 1);

							entityData.syncPlayerVariables(sourceentity);
						}
					}

					break;
				case "justitia_auto4":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 1);

					if (targetSin >= 7) {
						if (sourceentity instanceof PlayerEntity)
							EntityTick.regenerateLight((PlayerEntity) sourceentity, 1, true);
					}

					break;
				case "justitia_innate1":
					if (itemstack.getOrCreateTag().getInt("noTrigger") <= 0) {
						EgoWeaponsEffects.RUPTURE.get().increment(target, 3, 0);
						itemstack.getOrCreateTag().putInt("followUpHit", 1);
						if (targetPatch != null)
							SharedFunctions.hitstunEntity(targetPatch, 5, false, 0.3f);

						for (int i = 0; i < (targetSin/2); i++) {
							int finalI = i;
							new DelayedEvent(6 * (i + 1), (e) -> {

								if (sourceentity.level instanceof ServerWorld) {
									((ServerWorld) sourceentity.level).sendParticles(EgoWeaponsParticles.JUSTITIA_REUSE_STRIKE.get(), sourceentity.getX(), (sourceentity.getY() + 1), sourceentity.getZ(), (int) 1, 0, 0, 0, 0);
								}

								target.hurt(new DirectEgoDamageSource("", sourceentity, ExtendedDamageSource.StunType.NONE, JustitiaMovesetAnims.JUSTITIA_INNATE_1, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.PALE, "justitia"), (float) (sourceentity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f));
								target.playSound(EgoWeaponsSounds.JUSTITIA_INNATE_REUSE, 6f + 0.3f* finalI, 0.6f + 0.3f* finalI);
							});
						}
					}


					break;
				case "justitia_innate2":
					if (itemstack.getOrCreateTag().getInt("noTrigger") <= 0) {
						EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 3);
						itemstack.getOrCreateTag().putInt("followUpHit", 2);

						if (sourceSin > 0) {
							EgoWeaponsEffects.SIN.get().decrement(sourceentity, 0, 1);
							EgoWeaponsEffects.SIN.get().increment(target, 0, 1);
						}

						for (int i = 0; i < (targetSin/2); i++) {
							int finalI = i;
							new DelayedEvent(6 * (i + 1), (e) -> {

								if (sourceentity.level instanceof ServerWorld) {
									((ServerWorld) sourceentity.level).sendParticles(EgoWeaponsParticles.JUSTITIA_REUSE_STRIKE.get(), target.getX(), (target.getY() + 1), target.getZ(), (int) 1, 0, 0, 0, 0);
								}

								target.hurt(new DirectEgoDamageSource("", sourceentity, ExtendedDamageSource.StunType.NONE, JustitiaMovesetAnims.JUSTITIA_INNATE_2, GenericEgoDamage.AttackTypes.BLUNT, GenericEgoDamage.DamageTypes.PALE, "justitia"), (float) (sourceentity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.6f));
								target.playSound(EgoWeaponsSounds.JUSTITIA_INNATE_REUSE, 6f + 0.3f* finalI, 0.6f + 0.3f* finalI);
							});
						}
					}
					break;
			}
		}

		return true;
	}




	@Override
	public String getDefaultKillIdentifier() {
		return "justitia";
	}
	public static StaticAnimation.Event[] stigmatizeEventsB(float secondEvent) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];

		events[0] = StaticAnimation.Event.create(0.8f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			entity.level.addParticle(EgoWeaponsParticles.TEXTURED_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 2, 0);
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(secondEvent, (entitypatch) -> {


			entitypatch.playAnimationSynchronized(StigmaWorkshopMovesetAnims.STIGMA_SWORD_SPECIAL_2, 0.15f);
		}, StaticAnimation.Event.Side.SERVER);

		return events;
	}

}
