package net.m3tte.ego_weapons.item.relics;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.KeybindPackages;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.potion.MugaDeathPrevention;
import net.m3tte.ego_weapons.potion.countEffects.BleedEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.EmotionSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class Arayashiki extends EgoWeaponsWeapon {
	private static IItemTier ardorBlossomTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 3.6f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 6.5f;
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

	public Arayashiki(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(ardorBlossomTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.arayashiki.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		boolean firemode = Minecraft.getInstance().player != null ? KeybindPackages.getFireMode(Minecraft.getInstance().player) : false;


		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 7) + 1) + "/7] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.1"));
		list.add(new StringTextComponent(" "));


		if (firemode) {
			appendHoverTextUnsheath(itemstack, world, list);
		} else {
			appendHoverTextMain(itemstack, world, list);
		}


		generateStatusHelp(list);
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverTextMain(ItemStack itemstack, World world, List<ITextComponent> list) {

		boolean time = Minecraft.getInstance().player != null ? Minecraft.getInstance().player.tickCount % 300 <= 150 : false;

		boolean threshold = EmotionSystem.getEmotionLevel(Minecraft.getInstance().player) >= 3;

		switch (EgoWeaponsKeybinds.getUiPage() % 7) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "offense_up"});
				else
					generateDescription(list,"arayashiki", "passive", 3);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale" ,"loss_of_self", "sever_the_thread", "tianshia_star"});
				else
					generateDescription(list,"arayashiki", "passive2", 7);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"sinking", "blue_sand"});
				else
					generateDescription(list,"arayashiki", "passive3", 1);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise"});
				else
					generateDescription(list,"arayashiki", "guard", 3);
				break;
			case 4:

				if (time && threshold) {
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"red", "pale", "poise", "bleed", "loss_of_self"});
					else
						generateDescription(list, "arayashiki", "ability_b", 9, true);

				} else {
					if (EgoWeaponsKeybinds.isHoldingShift())
						generateStatusDescription(list, new String[]{"red", "poise", "bleed"});
					else
						generateDescription(list, "arayashiki", "ability", 9, true);

				}

				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise", "bleed"});
				else {
					generateDescription(list,"arayashiki", "innate", 6);
				}

				break;
			case 6:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise", "bleed"});
				else
					generateDescription(list,"arayashiki", "auto", 7);
				break;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverTextUnsheath(ItemStack itemstack, World world, List<ITextComponent> list) {
		switch (EgoWeaponsKeybinds.getUiPage() % 7) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "offense_up"});
				else
					generateDescription(list,"arayashiki", "passive", 3);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale" ,"loss_of_self", "sever_the_thread", "tianshia_star"});
				else
					generateDescription(list,"arayashiki", "passive2", 7);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"tianshia_star"});
				else
					generateDescription(list,"arayashiki", "passive3", 3);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"red", "poise"});
				else
					generateDescription(list,"arayashiki", "guard", 3);
				break;
			case 4:

				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale", "poise", "bleed", "loss_of_self", "sever_the_thread"});
				else
					generateDescription(list, "arayashiki_u", "ability", 11, true);

				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale", "poise", "bleed"});
				else {
					generateDescription(list,"arayashiki_u", "innate", 10);
				}

				break;
			case 6:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"pale", "poise", "bleed"});
				else
					generateDescription(list,"arayashiki_u", "auto", 12);
				break;
		}
	}

	public static void interruptedAttack(LivingEntity target) {

	}



	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);


		if (!target.level.isClientSide())
			EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.UDJAT_SAND.get(), 10, target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), 0f, 0.6f, 0.5, 0,0,0));

		//EgoWeaponsEffects.SEVER_THE_THREAD.get().increment(target, 100, 2);
		//EgoWeaponsEffects.LOSS_OF_SELF.get().increment(sourceentity, 100, 1);


		boolean unsheathedAttack = context.getLogicPredicate().equals(AttackLogicPredicate.MUGA) && context.triggersEffects();

		//System.out.println("MUGA EFFECT "+context.getLogicPredicate().equals(AttackLogicPredicate.MUGA)+ " // "+context.triggersEffects());

		int selfPoise = EgoWeaponsEffects.POISE.get().getPotency(sourceentity);


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			LivingEntityPatch<?> sourcePatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			switch (context.getAnimationIdentifier()) {

				case "arayashiki_sp_er_1":
				case "arayashiki_sp_er_2_1":
				case "arayashiki_sp_er_2_2":
				case "arayashiki_sp_er_2_3":
				case "arayashiki_sp_er_3_1":
				case "arayashiki_sp_er_3_2":
				case "arayashiki_sp_er_3_3":
				case "arayashiki_sp_er_4_1":
				case "arayashiki_sp_er_4_2":
				case "arayashiki_sp_er_4_3":
				case "arayashiki_sp_er_5_1":
				case "arayashiki_sp_er_5_2":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					if (targetPatch != null)
						targetPatch.knockBackEntity(sourceentity.position().add(0,0.1f,0), 0.08f);
					if (sourcePatch != null) {
						sourcePatch.knockBackEntity(target.position().add(0,0.1f,0), -0.1f);
					}

					break;

				case "arayashiki_sp_er_5_3":
				case "arayashiki_sp_er_6":
					SharedFunctions.staggerEntity(targetPatch, 1, false);
					if (targetPatch != null)
						targetPatch.knockBackEntity(sourceentity.position().add(0,1,0), -0.85f);
					break;

				case "arayashiki_auto_1_u":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 0, 1);
					break;
				case "arayashiki_auto_2_u":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 0, 1);
					break;
				case "arayashiki_auto_3_u":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 3, 0);

					if (selfPoise >= 25) {
						SharedFunctions.hitstunEntity(targetPatch, 1, false, 0.2f);
					} else {
						entitypatch.knockBackEntity(sourceentity.position(), 0.35f);
					}
					break;
				case "arayashiki_counter":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 2, 0);
					SharedFunctions.staggerEntity(targetPatch, 1, false);
					break;
				case "arayashiki_sp_1_S":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;
				case "arayashiki_sp_2_s_a":
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;

				case "arayashiki_sp_2_s_b":
					if (targetPatch != null) {
						SharedFunctions.staggerEntity(targetPatch, 1, false);
						targetPatch.knockBackEntity(sourceentity.position().add(0,1,0), -0.4f);
					}

					break;

				case "arayashiki_sp_3_s":
					SharedFunctions.staggerEntity(targetPatch, 1, false);

					break;
				case "arayashiki_sp_3_sb":
					EgoWeaponsEffects.LOSS_OF_SELF.get().increment(sourceentity, 100, 2);
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;
				case "arayashiki_auto_1_s":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 0, 1);
					break;
				case "arayashiki_auto_2_s":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 0, 2);
					break;
				case "arayashiki_auto_3_s":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 3, 0);
					break;
				case "arayashiki_innate_2":
				case "arayashiki_innate_1":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 0, 2);
					break;
				case "arayashiki_innate_1_u":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 2, 2);
					break;
				case "arayashiki_innate_2_u":
					EgoWeaponsEffects.POISE.get().increment(sourceentity, 4, 4);
					break;
			}
		}

		if (unsheathedAttack) {
			if (!entitypatch.getOriginal().level.isClientSide) {
				EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.ARAYASHIKI_REND_TARGET.get(), 1, target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0, 0, 0, 0));
			}

			EgoWeaponsEffects.LOSS_OF_SELF.get().increment(sourceentity, 100, 1);
		}

		return true;
	}


	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource, boolean crit) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			int targetSeverance = EgoWeaponsEffects.SEVER_THE_THREAD.get().getPotency(target);

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			int lossOnSelf = EgoWeaponsEffects.SEVER_THE_THREAD.get().getPotency(source);

			int poisePotency = EgoWeaponsEffects.POISE.get().getPotency(source);
			boolean unsheathedAttack = context.getLogicPredicate().equals(AttackLogicPredicate.MUGA) && context.triggersEffects();

			if (crit) {
				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.15f);

				if (context.isFinalCoin()) {
					EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 4, 1);
				}

			}

			if (context.isValidEgoAnimation()) {
				//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

				switch (context.getAnimationIdentifier()) {

					case "arayashiki_sp_er_1":
					case "arayashiki_sp_er_2_1":
					case "arayashiki_sp_er_2_2":
					case "arayashiki_sp_er_2_3":
					case "arayashiki_sp_er_3_1":
					case "arayashiki_sp_er_3_2":
					case "arayashiki_sp_er_3_3":
					case "arayashiki_sp_er_4_1":
					case "arayashiki_sp_er_4_2":
					case "arayashiki_sp_er_4_3":
					case "arayashiki_sp_er_5_1":
					case "arayashiki_sp_er_5_2":
					case "arayashiki_sp_er_5_3":
						target.addEffect(new EffectInstance(MugaDeathPrevention.get().getEffect(), 20, 0));
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.005f * targetSeverance);
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.005f * lossOnSelf);

						if (context.triggersEffects()) {
							EgoWeaponsEffects.BLEED.get().increment(target, 0, 2);
						}

						break;

					case "arayashiki_sp_er_6":
						if (targetSeverance >= 100)
							mult += SharedFunctions.incrementBonusDamage(damageSource, 1);

						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.015f * targetSeverance);
						break;
					case "arayashiki_innate_1_u":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 2);
						break;
					case "arayashiki_innate_2_u":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 4);
						mult += SharedFunctions.incrementBonusDamage(damageSource, 1);

						if (target.hasEffect(EgoWeaponsEffects.BLEED.get())) {
							BleedEffect.apply(target);
						}
						break;
					case "arayashiki_auto_1_u":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
						break;
					case "arayashiki_auto_2_u":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 2);
						break;
					case "arayashiki_auto_3_u":
						EgoWeaponsEffects.BLEED.get().increment(target, 2, 0);
						break;
					case "arayashiki_auto_4_u":
						EgoWeaponsEffects.DEFENSE_LEVEL_DOWN.get().decrement(target, 0, 3);
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.5f);
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.005f * targetSeverance);
						break;
					case "arayashiki_sp_1_s":
					case "arayashiki_sp_2_s_a":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
						break;
					case "arayashiki_sp_3_s":
						EgoWeaponsEffects.BLEED.get().increment(target, 2, 0);

						if (crit) {
							ItemStack weaponItem = source.getItemInHand(Hand.MAIN_HAND);


							if (!weaponItem.isEmpty()) {
								weaponItem.getOrCreateTag().putInt("specialCritEffect", target.getId());
							}

							mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.5f,0.01f * poisePotency));
						}

						break;
					case "arayashiki_sp_3_sb":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 3);
						mult += SharedFunctions.incrementBonusDamage(damageSource, 1f);
						if (target.hasEffect(EgoWeaponsEffects.BLEED.get())) {
							BleedEffect.apply(target);
						}
						break;

					case "arayashiki_innate_1":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 2);

						break;
					case "arayashiki_innate_2":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 2);
						EgoWeaponsEffects.OFFENSE_LEVEL_DOWN.get().increment(target, 0, 2);
						mult += SharedFunctions.incrementBonusDamage(damageSource, 0.25f);
						break;

					case "arayashiki_auto_1_s":
					case "arayashiki_auto_2_s":
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
						EgoWeaponsEffects.BLEED.get().increment(target, 0, 1);
						break;
					case "arayashiki_auto_3_s":
						EgoWeaponsEffects.BLEED.get().increment(target, 1, 0);
						break;
				}
			}

			if (unsheathedAttack) {
				if (!entitypatch.getOriginal().level.isClientSide) {
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.ARAYASHIKI_REND_TARGET.get(), 1, target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0, 0, 0, 0));
				}

				EgoWeaponsEffects.SEVER_THE_THREAD.get().increment(target, 100, 1);
			}
		}

		return mult;
	}

	public static float modifyDamageAmountTarget(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource, boolean crit) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			int targetSeverance = EgoWeaponsEffects.SEVER_THE_THREAD.get().getPotency(target);

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			int lossOnTarget = EgoWeaponsEffects.SEVER_THE_THREAD.get().getPotency(target);

			int poisePotency = EgoWeaponsEffects.POISE.get().getPotency(source);
			boolean unsheathedAttack = context.getLogicPredicate().equals(AttackLogicPredicate.MUGA) && context.triggersEffects();

			if (crit) {
				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.15f);

				if (context.isFinalCoin()) {
					EgoWeaponsEffects.OFFENSE_LEVEL_UP.get().increment(source, 4, 1);
				}

			} else {
				mult += SharedFunctions.incrementBonusDamage(damageSource, -0.15f);
			}



			if (unsheathedAttack) {
				if (!entitypatch.getOriginal().level.isClientSide) {
					EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendParticlesVelocity(EgoWeaponsParticles.ARAYASHIKI_REND_TARGET.get(), 1, target.getX(), target.getY(), target.getZ(), 0, target.getId(), 0, 0, 0, 0));
				}

				EgoWeaponsEffects.SEVER_THE_THREAD.get().increment(target, 100, 1);
			}
		}

		return mult;
	}


	@Override
	public String getDefaultKillIdentifier() {
		return "arayashiki";
	}

}
