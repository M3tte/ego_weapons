
package net.m3tte.ego_weapons.item.solemn_lament;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.ParticlePackages;
import net.m3tte.ego_weapons.potion.EternalRestPotionEffect;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;


public class SolemnLament extends EgoWeaponsWeapon {
	public static final Item item = null;

	public SolemnLament(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, boolean isDeparted) {
		super(solemnLamentTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);

	}

	@Override
	public String getDefaultKillIdentifier() {
		return "solemn_lament";
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("It is merely that butterflies, both living and dead, bloom from where the gun points.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking", "butterfly"});
				else {

					generateDescription(list,"solemn_lament", "auto", 6);
					list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
					generateDescription(list, "solemn_lament", "auto2", 6);
				}
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking", "butterfly"});
				else
					generateDescription(list,"solemn_lament", "innate", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking", "butterfly"});
				else {
					generateDescription(list, "solemn_lament", "ability", 13);
					list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
					generateDescription(list, "solemn_lament", "ability2", 2);
				}
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking"});
				else
					generateDescription(list,"solemn_lament", "guard", 5);
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed"});
				else {
					generateDescription(list, "solemn_lament", "reload", 1);
					list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
					generateDescription(list, "solemn_lament", "reload2", 3);
				}
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));



	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		if (currentanim != null) {

			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			AttackAnimation.Phase phase = null;
			if (currentanim instanceof EgoAttackAnimation) {
				phase = ((EgoAttackAnimation)currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
			}

			if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
				String elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(null);

				if (elp != null)
					weaponIdentifier = elp;

			}

			int butterflyPotencyOnTarget = EgoWeaponsEffects.THE_LIVING.get().getPotency(target);
			int butterflyCountOnTarget = EgoWeaponsEffects.THE_DEPARTED.get().getPotency(target);

			switch (weaponIdentifier) {
				case "solemn_lament_e0":
				case "solemn_lament_e1":
				case "solemn_lament_e2":
					EgoWeaponsEffects.SINKING.get().increment(target, 0,1);
					break;
				case "solemn_lament_e3":
					EgoWeaponsEffects.SINKING.get().increment(target, 2,1);
					break;
				case "solemn_lament_special_a":
					SharedFunctions.staggerEntity(targetPatch, 1, false);

					StaggerSystem.reduceStagger(target, butterflyPotencyOnTarget+butterflyCountOnTarget, false);

					break;

				case "solemn_lament_special_1":
                case "solemn_lament_special_3":
                    SharedFunctions.hitstunEntity(targetPatch, 1, false, 0.2f);
					EgoWeaponsEffects.THE_DEPARTED.get().increment(target, 15,1);
					break;
				case "solemn_lament_special_2":
				case "solemn_lament_special_4":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 0.2f);
					EgoWeaponsEffects.THE_LIVING.get().increment(target, 15,1);
					break;
				case "solemn_lament_special_fin":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 0.2f);
					EgoWeaponsEffects.THE_DEPARTED.get().increment(target, 15,1);
					EgoWeaponsEffects.THE_LIVING.get().increment(target, 15,1);
					break;

				case "solemn_lament_dual":
					EgoWeaponsEffects.THE_DEPARTED.get().increment(target, 15,1);
					EgoWeaponsEffects.THE_LIVING.get().increment(target, 15,1);

					break;
				case "solemn_lament_living":
					EgoWeaponsEffects.THE_LIVING.get().increment(target, 15,1);
					break;
				case "solemn_lament_departed":

					EgoWeaponsEffects.THE_DEPARTED.get().increment(target, 15, 1);
					break;

				case "solemn_lament_special":
					if (!sourceentity.getPersistentData().contains("solemnLamentChargeHit")) {
						sourceentity.getPersistentData().putBoolean("solemnLamentChargeHit", true);
						target.level.playSound((PlayerEntity) null, target.getX(), target.getY(), target.getZ(),
								EgoWeaponsSounds.SOLEMN_LAMENT_SPECIAL_IMPACT,
								SoundCategory.PLAYERS, 1, 1.4f);

						if (target.level instanceof ServerWorld) {
							((ServerWorld) target.level).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_BURST_HIT.get(), target.getX(), target.getY(), target.getZ(), (int) 1, 0, 0, 0, 0);
						}
					}
					int livingCount = SolemnLamentEffects.getAmmoCount(sourceentity, SolemnLamentEffects.getLiving());
					int departedCount = SolemnLamentEffects.getAmmoCount(sourceentity, SolemnLamentEffects.getDeparted());

					EgoWeaponsEffects.THE_DEPARTED.get().increment(target, 15, (int) (departedCount / 1.5f));
					EgoWeaponsEffects.THE_LIVING.get().increment(target, 15, (int) (livingCount / 1.5f));

					SolemnLamentEffects.decrementEffect(sourceentity, SolemnLamentEffects.getLiving(), (int) (livingCount / 1.5f));
					SolemnLamentEffects.decrementEffect(sourceentity, SolemnLamentEffects.getDeparted(), (int) (departedCount / 1.5f));

					if (!sourceentity.level.isClientSide()) {
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), target.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), target.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new ParticlePackages.DirectionalAttackParticle(target.getId(), target.getId(), EgoWeaponsParticles.HORIZONTAL_SHOCKWAVE.get().getRegistryName()));
						((ServerWorld) sourceentity.level).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_SHOCKWAVE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 0, 0, 0, 0, 1);

					}

					if (!sourceentity.getPersistentData().contains("solemnLamentChargeStaggered") && (StaggerSystem.isStaggered(target) || !target.isAlive())) {
						sourceentity.getPersistentData().putBoolean("solemnLamentChargeStaggered", true);
						target.level.playSound((PlayerEntity) null, target.getX(), target.getY(), target.getZ(),
								EgoWeaponsSounds.SOLEMN_LAMENT_SPECIAL_RELOAD,
								SoundCategory.PLAYERS, 1, 1.4f);
					}

					break;
			}


		}


		return retval;
	}

	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

		EgoWeaponsModVars.PlayerVariables entityData = source.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		int sinkingPotencyOnTarget = EgoWeaponsEffects.SINKING.get().getPotency(target);
		int sinkingCountOnTarget = EgoWeaponsEffects.SINKING.get().getCount(target);

		int butterflyPotencyOnTarget = EgoWeaponsEffects.THE_LIVING.get().getPotency(target);
		int butterflyCountOnTarget = EgoWeaponsEffects.THE_DEPARTED.get().getPotency(target);

		AttackAnimation.Phase phase = null;
		if (currentanim instanceof EgoAttackAnimation) {
			phase = ((EgoAttackAnimation)currentanim).getPhaseByTime(entitypatch.getAnimator().getPlayerFor(currentanim).getElapsedTime());
		}

		if (phase instanceof EgoAttackAnimation.EgoAttackPhase) {
			String elp = ((EgoAttackAnimation.EgoAttackPhase) phase).getProperty(EgoAttackAnimation.EgoAttackPhase.EgoWeaponsAttackPhaseProperty.IDENTIFIER).orElse(null);

			if (elp != null)
				weaponIdentifier = elp;
		}

		switch (weaponIdentifier) {
			case "solemn_lament_special_1":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.45f, sinkingCountOnTarget * 0.03f));
				mult += Math.min(0.45f, sinkingCountOnTarget * 0.03f);
				break;
			case "solemn_lament_special_2":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.45f, sinkingPotencyOnTarget * 0.03f));
				mult += Math.min(0.45f, sinkingPotencyOnTarget * 0.03f);
				break;
			case "solemn_lament_special_3":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.45f, sinkingCountOnTarget * 0.05f));
				mult += Math.min(0.45f, sinkingCountOnTarget * 0.05f);
				break;
			case "solemn_lament_special_4":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.45f, sinkingPotencyOnTarget * 0.05f));
				mult += Math.min(0.45f, sinkingPotencyOnTarget * 0.05f);
				break;

			case "solemn_lament_special_fin":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.75f, (sinkingPotencyOnTarget + sinkingCountOnTarget) * 0.05f));
				mult += Math.min(0.75f, (sinkingPotencyOnTarget + sinkingCountOnTarget) * 0.05f);
				break;
			case "solemn_lament_special":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, (butterflyPotencyOnTarget + butterflyCountOnTarget) * 0.05f));
				mult += Math.min(1f, (butterflyPotencyOnTarget + butterflyCountOnTarget) * 0.05f);
				break;
			case "solemn_lament_e3":
				SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f, (sinkingPotencyOnTarget + sinkingCountOnTarget) * 0.025f));
				mult += Math.min(0.5f, (sinkingPotencyOnTarget + sinkingCountOnTarget) * 0.025f);
				break;
		}



		return mult;
	}

	// elements.items.add(() -> new SwordItem(, 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier solemnLamentTier = new IItemTier() {
		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4;
		}

		@Override
		public float getAttackDamageBonus() {
			return 2;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}
	};
}
