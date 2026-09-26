
package net.m3tte.ego_weapons.item.lamp;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.AttackLogicPredicate;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.DarkFlameEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.UtilitySystems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;
import static net.m3tte.ego_weapons.world.capabilities.UtilitySystems.generateAttackContext;


public class LampCrossbow extends EgoWeaponsWeapon {

	@Override
	public void appendHoverText(@NotNull ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.magic_bullet.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.waw"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "dark_flame"});
				else
					generateDescription(list,"magic_bullet", "auto", 5);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "dark_flame", "magic_bullet"});
				else
					generateDescription(list,"magic_bullet", "innate", 4);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "dark_flame", "magic_bullet"});
				else {
					generateDescription(list,"magic_bullet", "ability", 8);
				}
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"magic_bullet"});
				else
					generateDescription(list,"magic_bullet", "guard", 2);
				break;
		}

		generateStatusHelp(list);
	}


	public static float damageMultiplier(LivingEntity target, LivingEntity sourceentity, float multiplier, DamageSource source) {
		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);



		if (entitypatch == null)
			return multiplier;

		if (entitypatch.getServerAnimator() == null)
			return multiplier;

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {

			AttackLogicPredicate type = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE).orElse(AttackLogicPredicate.DEFAULT);

			if (type.equals(AttackLogicPredicate.MAGIC_BULLET_FIRE)) {

				int burnOnTarget = EgoWeaponsEffects.BURN.get().getPotency(target);

				multiplier += SharedFunctions.incrementBonusDamage(source, Math.min(1,0.05f * burnOnTarget));


				int potency = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entitypatch.getOriginal());

				if (potency == 0)
					potency = 7;

				multiplier += SharedFunctions.incrementBonusDamage(source, 0.20f * potency);

				float amountPredicate = (0.02f * Math.min(15, EgoWeaponsEffects.BURN.get().getPotency(target)));

				SharedFunctions.incrementBonusDamage(source, amountPredicate);

				multiplier += 1 + amountPredicate;
			}
		}
		return multiplier;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity){
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);

		EgoWeaponsModVars.PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		if (entitypatch == null)
			return retval;

		if (entitypatch.getServerAnimator() == null || entityData == null)
			return retval;

		UtilitySystems.EGOAttackContext context = generateAttackContext(entitypatch);



		if (context.isValidEgoAnimation() && entityData != null) {

			// Different animation effects
			switch (context.getAnimationIdentifier()) {
				case "magic_bullet_auto_1":
				case "magic_bullet_auto_2":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					break;
				case "magic_bullet_auto_3":

					EgoWeaponsEffects.DARK_BURN.get().increment(target, 1, 0);
					if (sourceentity instanceof PlayerEntity) {
						PlayerEntity sourcePlayer = (PlayerEntity) sourceentity;
						if (!(sourcePlayer.getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0)) {
							if (entitypatch instanceof PlayerPatch) {
								((PlayerPatch<?>)entitypatch).setStamina(Math.min(((PlayerPatch<?>)entitypatch).getStamina() + 1f, ((PlayerPatch<?>)entitypatch).getMaxStamina()));

							}
							StaggerSystem.healStagger(sourceentity, 0.4f);

							if (EgoWeaponsItems.MAGIC_BULLET_CLOAK.get().equals(sourcePlayer.getItemBySlot(EquipmentSlotType.CHEST).getItem())) {
								sourceentity.level.playSound((PlayerEntity) null, sourcePlayer.getX(), sourcePlayer.getY(), sourcePlayer.getZ(),
										EgoWeaponsSounds.RESULT_POSITIVE,
										SoundCategory.PLAYERS, 1, 1.4f);
								StaggerSystem.healStagger(sourceentity, 2);
								entityData.light = Math.min(entityData.light + 1, EgoWeaponsAttributes.getMaxLight(sourcePlayer)); // Not affected by multipliers.
							}
							((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 2);
							entityData.globalcooldown = 2;
						}
					}

					break;
				case "magic_bullet_spin_1":

					if (sourceentity.hasEffect(EgoWeaponsEffects.MAGIC_BULLET.get())) {
						((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(target, Math.min(EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(sourceentity),4));
					}

					if (target.hasEffect(EgoWeaponsEffects.DARK_BURN.get())) {
						EgoWeaponsEffects.BURN.get().increment(target, 1, Math.min(4,target.getEffect(EgoWeaponsEffects.DARK_BURN.get()).getDuration()/20));
					}
					break;
				case "magic_bullet_fire_1":
				case "magic_bullet_fire_2":
						int ampl = EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(sourceentity);

						if (ampl > 0) {
							((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(target, (EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(sourceentity)));
						}
					break;
			}


		}

		entityData.syncPlayerVariables(sourceentity);
		return retval;
	}



	public LampCrossbow(IItemTier tier, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(tier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}


}
