
package net.m3tte.ego_weapons.item.magic_bullet;

import net.m3tte.ego_weapons.*;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.potion.MagicBulletPotionEffect;
import net.m3tte.ego_weapons.potion.countEffects.DarkFlameEffect;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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

import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;


public class MagicBullet extends SwordItem {

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("This bullet can truly hit anything.").withStyle(TextFormatting.BLUE));
		list.add(new StringTextComponent("[Active] ").withStyle(TextFormatting.DARK_BLUE).append(new StringTextComponent(" Fire Magic Bullet").withStyle(TextFormatting.GRAY)));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity){
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();
		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		EgoWeaponsModVars.PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();



		if (anim_id == EgoWeaponsAnimations.MAGIC_BULLET_AUTO_1.getId()) {
			EgoWeaponsEffects.BURN.get().increment(target, 0, 1);
		}

		if (anim_id == EgoWeaponsAnimations.MAGIC_BULLET_SPIN_1.getId()) {

			if (target.hasEffect(EgoWeaponsEffects.DARK_BURN.get())) {
				EgoWeaponsEffects.BURN.get().increment(target, 0, target.getEffect(EgoWeaponsEffects.DARK_BURN.get()).getDuration()/20);
			}

			if (sourceentity.hasEffect(MagicBulletPotionEffect.get())) {
				((DarkFlameEffect) EgoWeaponsEffects.DARK_BURN.get()).setPotency(target, Math.min(sourceentity.getEffect(MagicBulletPotionEffect.get()).getAmplifier()+1,4));
			}
		}

		if (anim_id == EgoWeaponsAnimations.MAGIC_BULLET_AUTO_3.getId() && sourceentity instanceof PlayerEntity) {
			PlayerEntity sourcePlayer = (PlayerEntity) sourceentity;

			EgoWeaponsEffects.DARK_BURN.get().increment(target, 1, 0);

			if (!(sourcePlayer.getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0)) {
				entitypatch.setStamina(Math.min(entitypatch.getStamina() + 1f, entitypatch.getMaxStamina()));
				StaggerSystem.healStagger(sourceentity, 0.4f);

				if (EgoWeaponsItems.MAGIC_BULLET_CLOAK.get().equals(sourcePlayer.getItemBySlot(EquipmentSlotType.CHEST).getItem())) {
					sourcePlayer.level.playSound((PlayerEntity) null, sourcePlayer.getX(), sourcePlayer.getY(), sourcePlayer.getZ(),
							EgoWeaponsSounds.RESULT_POSITIVE,
							SoundCategory.PLAYERS, 1, 1.4f);
					StaggerSystem.healStagger(sourceentity, 2);
					entityData.blips = Math.min(entityData.blips + 1, entityData.maxblips); // Not affected by multipliers.
				}


				((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 2);
				entityData.globalcooldown = 2;
			}
		}
		entityData.syncPlayerVariables(sourceentity);
		return true;
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
