
package net.m3tte.ego_weapons.item.magic_bullet;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.*;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.MagicBulletPotionEffect;
import net.m3tte.ego_weapons.potion.countEffects.DarkFlameEffect;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.m3tte.ego_weapons.world.capabilities.damage.GenericEgoDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.client.util.ITooltipFlag;

import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;


public class MagicBullet extends SwordItem {

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("This bullet can truly hit anything...").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));

		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "dark_flame"});
				else
					generateDescription(list,"magic_bullet", "auto", 4);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "dark_flame", "magic_bullet"});
				else
					generateDescription(list,"magic_bullet", "innate", 3);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"burn", "dark_flame", "magic_bullet"});
				else {
					generateDescription(list,"magic_bullet", "ability", 5);
				}
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"magic_bullet"});
				else
					generateDescription(list,"magic_bullet", "guard", 2);
				break;
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}


	public static float damageMultiplier(LivingEntity sourceentity, LivingEntity target, float amount, DamageSource source) {
		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {

			AttackLogicPredicate type = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LOGIC_PREDICATE).orElse(AttackLogicPredicate.DEFAULT);

			if (type.equals(AttackLogicPredicate.MAGIC_BULLET_FIRE)) {
				if (source instanceof EpicFightDamageSource) {
					amount += 3.5f * EgoWeaponsEffects.MAGIC_BULLET.get().getPotency(entitypatch.getOriginal());

					float amountPredicate = (0.02f * Math.min(15, EgoWeaponsEffects.BURN.get().getPotency(target)));

					SharedFunctions.incrementBonusDamage(source, amountPredicate);

					amount *= 1 + amountPredicate;
				}
			}
		}
		return amount;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity){
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		EgoWeaponsModVars.PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation && entityData != null) {

			String weaponIdentifier;

			if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation) {
				weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
			} else {
				weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");
			}

			// Different animation effects
			switch (weaponIdentifier) {
				case "magic_bullet_auto_1":
				case "magic_bullet_auto_2":
					EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
					break;
				case "magic_bullet_auto_3":

					EgoWeaponsEffects.DARK_BURN.get().increment(target, 1, 0);
					if (sourceentity instanceof PlayerEntity) {
						PlayerEntity sourcePlayer = (PlayerEntity) sourceentity;
						if (!(sourcePlayer.getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0)) {
							entitypatch.setStamina(Math.min(entitypatch.getStamina() + 1f, entitypatch.getMaxStamina()));
							StaggerSystem.healStagger(sourceentity, 0.4f);

							if (EgoWeaponsItems.MAGIC_BULLET_CLOAK.get().equals(sourcePlayer.getItemBySlot(EquipmentSlotType.CHEST).getItem())) {
								sourceentity.level.playSound((PlayerEntity) null, sourcePlayer.getX(), sourcePlayer.getY(), sourcePlayer.getZ(),
										EgoWeaponsSounds.RESULT_POSITIVE,
										SoundCategory.PLAYERS, 1, 1.4f);
								StaggerSystem.healStagger(sourceentity, 2);
								entityData.blips = Math.min(entityData.blips + 1, entityData.maxblips); // Not affected by multipliers.
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



	public MagicBullet(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(magicBulletItem, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	static IItemTier magicBulletItem = new IItemTier() {
		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 3f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 7f;
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
}
