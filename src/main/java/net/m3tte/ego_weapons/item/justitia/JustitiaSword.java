
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

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
		list.add(new StringTextComponent("A sword wrapped with the blinding bandages of Justice. For the scale always tips just.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
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
					generateStatusDescription(list, new String[]{"pale", "sin", "offense_up", "offense_down"});
				else
					generateDescription(list,"justitia", "ability", 9);
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

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
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

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		//EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));


		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			AttackAnimation.Phase phase = null;
			if (currentanim instanceof EgoAttackAnimation) {
				phase = ((EgoAttackAnimation)currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
			}

			if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
				String elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(null);

				if (elp != null)
					weaponIdentifier = elp;

			}

			int sourceSpeed = EgoWeaponsEffects.speedMult(sourceentity);
			int targetSpeed = EgoWeaponsEffects.speedMult(target);

			int speedDif = Math.max(0, sourceSpeed - targetSpeed);

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			int targetSin = EgoWeaponsEffects.SIN.get().getPotency(target);
			int sourceSin = EgoWeaponsEffects.SIN.get().getPotency(sourceentity);

			switch (weaponIdentifier) {
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
							EntityTick.chargeBlips((PlayerEntity) sourceentity, 1, true);
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

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);


		int sourceSpeed = EgoWeaponsEffects.speedMult(source);
		int targetSpeed = EgoWeaponsEffects.speedMult(target);

		int speedDif = sourceSpeed - targetSpeed;

		if (speedDif < 0) {
			if (sourceSpeed >= 5) {
				SharedFunctions.incrementBonusDamage(damageSource, -0.25f);
				mult -= 0.25f;
			}

			speedDif = 0;
		}

		int hastePot = EgoWeaponsEffects.SPEED_UP.get().getPotency(source);

		if (hastePot > 0) {
			SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.03f * hastePot, 0.3f));
			mult += Math.min(0.03f * hastePot, 0.3f);
		}

		switch (weaponIdentifier) {
			case "heishou_mao_special_fin":
				if (sourceSpeed >= 5) {
					SharedFunctions.incrementBonusDamage(damageSource, 0.25f);
					mult += 0.25f;
				}

			case "heishou_mao_special_1":
			case "heishou_mao_special_2":
			case "heishou_mao_special_3":
			case "heishou_mao_special_4":
				float buffFactor = Math.max(0, speedDif * EgoWeaponsEffects.RUPTURE.get().getPotency(target) * 0.01f);

				if (buffFactor > 0) {
					SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f, buffFactor));
					mult += Math.min(0.5f, buffFactor);
				}

				break;

			case "heishou_mao_innate_1":
			case "heishou_mao_innate_2":
			case "heishou_mao_innate_3":
				float buffFactor2 = Math.max(0, speedDif * EgoWeaponsEffects.RUPTURE.get().getPotency(target) * 0.01f);

				if (buffFactor2 > 0) {
					SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.3f, buffFactor2));
					mult += Math.min(0.3f, buffFactor2);
				}

				float striderMaoPotency = Math.max(0, EgoWeaponsEffects.STRIDER_MAO.get().getPotency(target) * 0.1f);

				if (striderMaoPotency > 0) {
					SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.3f, striderMaoPotency));
					mult += Math.min(0.3f, striderMaoPotency);
				}
				break;
		}



		return mult;
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
