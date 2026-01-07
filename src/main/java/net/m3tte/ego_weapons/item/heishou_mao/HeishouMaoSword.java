
package net.m3tte.ego_weapons.item.heishou_mao;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.StigmaWorkshopMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class HeishouMaoSword extends EgoWeaponsWeapon {

	private static IItemTier maoSwordTier = new IItemTier() {

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
			return 3;
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

	public HeishouMaoSword(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(maoSwordTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A sword commonly used by the hares of the mao branch.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 8) + 1) + "/8] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.4"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 8) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"deathrite_haste", "strider_mao"});
				else
					generateDescription(list,"heishou_mao_sword", "passive", 2);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"speed_up"});
				else
					generateDescription(list,"heishou_mao_sword", "passive2", 4);
				break;
			case 2:
                if (EgoWeaponsKeybinds.isHoldingShift())
                    generateStatusDescription(list, new String[]{"black", "speed", "rupture", "deathrite_haste"});
                else
                    generateDescription(list, "heishou_mao_sword", "ability", 12);
                break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "speed", "rupture","strider_mao"});
				else {
					generateDescription(list,"heishou_mao_sword", "innate", 7);
				}

				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "speed", "rupture", "speed_up"});
				else
					generateDescription(list,"heishou_mao_sword", "auto", 7);
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "speed_up", "speed", "rupture"});
				else
					generateDescription(list,"heishou_mao_sword", "guard", 5);
				break;
			case 6:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "rupture", "speed_up"});
				else
					generateDescription(list,"heishou_mao_sword", "dash", 4);
				break;
			case 7:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "rupture"});
				else
					generateDescription(list,"heishou_mao_sword", "jump", 2);
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

		// EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendParticlesVelocity(EgoWeaponsParticles.SIMPLE_EMBER.get(), 8, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0.05, 0.6f, 1.5f, 0.5f, 1f, 0.5f));


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
			switch (weaponIdentifier) {
				case "heishou_mao_innate_1":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 2);

					break;
				case "heishou_mao_auto1":
				case "heishou_mao_auto2":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 1);
					break;
				case "heishou_mao_auto3":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 0, 1);
					onHitHaste(sourceentity, 1 ,entityData, 5);
					target.setDeltaMovement(target.getDeltaMovement().add(new Vector3d(0,0.5f,0)));

					if (EgoWeaponsEffects.speedMult(sourceentity) >= 5)
						SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);


					break;
				case "heishou_mao_auto4":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 2, 2);
					break;
				case "heishou_mao_jump":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 1, 1);
					break;
				case "heishou_mao_counter":
					SharedFunctions.staggerEntity(targetPatch, 3, false);
					EgoWeaponsEffects.RUPTURE.get().increment(target, 2, 0);
					entitypatch.playSound(EgoWeaponsSounds.HEISHOU_MAO_PARRY, -0.05F, 0.1F);
					if (sourceentity.level instanceof ServerWorld) {
						EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)sourceentity.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, sourceentity, target);
						EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerWorld)sourceentity.level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, sourceentity, target);
					}
					break;
				case "heishou_mao_dash":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 1, 1);
					onHitHasteConsumeLight(sourceentity, entityData, 5);
					if (speedDif >= 5)
						SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);

					break;
				case "heishou_mao_special_1":
					EgoWeaponsEffects.DEATHRITE_HASTE.get().increment(target, 3, 3);



					EgoWeaponsEffects.RUPTURE.get().increment(target, 2, Math.min(3, speedDif/3));
					SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
					target.addEffect(new EffectInstance(Effects.SLOW_FALLING, 20, 0));
					target.setDeltaMovement(target.getDeltaMovement().add(new Vector3d(0,0.7f,0)));
					break;
				case "heishou_mao_special_2":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 2, Math.min(3, speedDif/3));
					SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
					target.addEffect(new EffectInstance(Effects.SLOW_FALLING, 20, 0));
					target.setDeltaMovement(0, 0.3f, 0);
					break;
				case "heishou_mao_special_3":
                case "heishou_mao_special_4":
                    SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
					target.setDeltaMovement(0, 0.3f, 0);
					target.addEffect(new EffectInstance(Effects.SLOW_FALLING, 20, 0));
					break;
                case "heishou_mao_special_fin":
					EgoWeaponsEffects.RUPTURE.get().increment(target, 2, Math.min(3, speedDif/3));
					SharedFunctions.hitstunEntity(targetPatch, 3, false, 0);
					target.setDeltaMovement(0, 0.3f, 0);
					target.addEffect(new EffectInstance(Effects.SLOW_FALLING, 20, 0));
					sourceentity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("lastTargeted", target.getId());
					if (!world.isClientSide())
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.SendShakeMessage(target.getId(), 3));
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
		return "mao_branch_sword";
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
