
package net.m3tte.ego_weapons.item.udjat;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsMod;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.network.packages.VFXPackages;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;

public class UdjatKhopesh extends EgoWeaponsWeapon {
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

	public UdjatKhopesh(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(ardorBlossomTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.udjat_khopesh.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 5) + 1) + "/5] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.3"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 5) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"sinking", "blue_sand"});
				else
					generateDescription(list,"udjat_khopesh", "passive", 2);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"protection", "offense_down"});
				else
					generateDescription(list,"udjat_khopesh", "passive2", 3);
				break;
			case 2:

				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"protection", "sinking", "blue_sand"});
				else
					generateDescription(list, "udjat_khopesh", "ability", 12, true);

				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"fragile", "protection","sinking", "blue_sand"});
				else {
					generateDescription(list,"udjat_khopesh", "innate", 6);
				}

				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"white", "sinking", "protection"});
				else
					generateDescription(list,"udjat_khopesh", "auto", 7);
				break;
		}

		generateStatusHelp(list);
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


		if (context.isValidEgoAnimation()) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));


			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			switch (context.getAnimationIdentifier()) {

				case "khopesh_innate":
					EgoWeaponsEffects.PROTECTION.get().increment(sourceentity, 5, 1);
					break;
				case "khopesh_auto_3":
					itemstack.getOrCreateTag().putInt("hitEntity", 1);
					EgoWeaponsEffects.PROTECTION.get().increment(sourceentity, 5, 1);
					break;
				case "khopesh_auto_3f":
					if (!sourceentity.level.isClientSide())
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendShakeMessage(target.getId(), 3));

					EgoWeaponsEffects.DEFENSE_LEVEL_UP.get().increment(sourceentity, 0, 1);
					break;
				case "khopesh_special_1":
					itemstack.getOrCreateTag().putInt("hitEntity", 1);
					SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;

				case "khopesh_special_2_a":
				case "khopesh_special_2_b":
					if (targetPatch != null)
						SharedFunctions.hitstunEntity(targetPatch, 1, false, 1);
					break;

				case "khopesh_special_2_c":
					if (targetPatch != null)
						SharedFunctions.staggerEntity(targetPatch, 1, false);
					itemstack.getOrCreateTag().putInt("hitEntity", 1);
					break;

				case "khopesh_special_3":
					if (!sourceentity.level.isClientSide())
						EgoWeaponsMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new VFXPackages.SendShakeMessage(target.getId(), 2));
					break;
			}
		}

		return true;
	}


	public static float modifyDamageAmount(LivingEntity target, LivingEntity source, float mult, DamageSource damageSource) {

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) source.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();



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

			LivingEntityPatch<?> targetPatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			int blueSandOnTarget = EgoWeaponsEffects.BLUE_SAND.get().getPotency(target);
			int protectionOnSelf = EgoWeaponsEffects.PROTECTION.get().getPotency(source);
			int sinkingOnTarget = EgoWeaponsEffects.SINKING.get().getPotency(target);
			if (blueSandOnTarget >= 0) {

				mult += SharedFunctions.incrementBonusDamage(damageSource, 0.10f);
			}

			mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.20f, 0.02f * sinkingOnTarget));
			mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(0.25f, 0.05f * protectionOnSelf));


			switch (weaponIdentifier) {
				case "khopesh_auto_1":
                case "khopesh_auto_3f":
                    EgoWeaponsEffects.SINKING.get().increment(target, 0, 1);
					break;
				case "khopesh_auto_2":
					EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
					break;
				case "khopesh_innate":
					EgoWeaponsEffects.SINKING.get().increment(target, 2, 0);
					EgoWeaponsEffects.FRAGILE.get().increment(target, 10, 1);
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 1);
					break;
				case "khopesh_special_1":
					EgoWeaponsEffects.SINKING.get().increment(target, blueSandOnTarget > 0 ? 1 : 0, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_2_a":
					EgoWeaponsEffects.SINKING.get().increment(target, blueSandOnTarget > 1 ? 1 : 0, 2);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_2_b":
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_2_c":
					EgoWeaponsEffects.SINKING.get().increment(target, blueSandOnTarget > 2 ? 2 : 1, 0);
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					break;
				case "khopesh_special_3":
					EgoWeaponsEffects.BLUE_SAND.get().increment(target, 3, 1);
					mult += SharedFunctions.incrementBonusDamage(damageSource, Math.min(1f, 0.15f * protectionOnSelf));
					StaggerSystem.reduceStagger(target, EgoWeaponsEffects.SINKING.get().getPotency(target), false);
					break;
            }
		}




		return mult;
	}



	@Override
	public String getDefaultKillIdentifier() {
		return "udjat_khopesh";
	}

}
