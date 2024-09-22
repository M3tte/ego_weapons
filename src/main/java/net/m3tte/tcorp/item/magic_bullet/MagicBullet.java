
package net.m3tte.tcorp.item.magic_bullet;

import net.m3tte.tcorp.TCorpItems;
import net.m3tte.tcorp.TCorpSounds;
import net.m3tte.tcorp.TcorpModVariables;
import net.m3tte.tcorp.gameasset.TCorpAnimations;
import net.m3tte.tcorp.procedures.BlipTick;
import net.m3tte.tcorp.world.capabilities.StaggerSystem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.client.util.ITooltipFlag;

import net.m3tte.tcorp.TcorpModElements;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.sql.Struct;
import java.util.List;

import static net.m3tte.tcorp.TcorpModVariables.PLAYER_VARIABLES_CAPABILITY;


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

		TcorpModVariables.PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		if (anim_id == TCorpAnimations.MAGIC_BULLET_AUTO_3.getId() && sourceentity instanceof PlayerEntity) {
			PlayerEntity sourcePlayer = (PlayerEntity) sourceentity;
			if (!(sourcePlayer.getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0)) {
				entitypatch.setStamina(Math.min(entitypatch.getStamina() + 1f, entitypatch.getMaxStamina()));
				StaggerSystem.healStagger(sourceentity, 0.4f);

				if (TCorpItems.MAGIC_BULLET_CLOAK.get().equals(sourcePlayer.getItemBySlot(EquipmentSlotType.CHEST).getItem())) {
					sourcePlayer.level.playSound((PlayerEntity) null, sourcePlayer.getX(), sourcePlayer.getY(), sourcePlayer.getZ(),
							TCorpSounds.RESULT_POSITIVE,
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
