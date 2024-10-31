
package net.m3tte.ego_weapons.item.mimicry;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsItems;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.procedures.BlipTick;
import net.m3tte.ego_weapons.procedures.legacy.MimicryhitentityProcedure;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.execFunctions.HitProcedure.hitStunEffect;
import static net.m3tte.ego_weapons.procedures.SharedFunctions.pummelDownEntity;

public class MimicryItem extends SwordItem {

	private static IItemTier mimicryTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.2f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 14;
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

	public MimicryItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(mimicryTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("A disgusting reminder to what shall not be."));
		list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Horizontal Split").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 7 E").withStyle(TextFormatting.AQUA)));
		list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Heal when dealing damage").withStyle(TextFormatting.GRAY)));

	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();
		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		if ((chestItem.equals(EgoWeaponsItems.JACKET_OF_THE_RED_MIST.get()) || chestItem.equals(EgoWeaponsItems.RED_MIST_EGO_CHESTPLATE.get())) && entityData != null) {


			if (anim_id == EgoWeaponsAnimations.KALI_DASH.getId()) {
				if (sourceentity instanceof PlayerEntity) {
					if (entityData.blips > 2) {
						entityData.blips -= 2;
						entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.KALI_REND, 0);

						entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
					}
				}
			}

			if (anim_id == EgoWeaponsAnimations.KALI_JUMP.getId()) {
				entitypatch.playAnimationSynchronized(EgoWeaponsAnimations.KALI_IMPACT, 0);
				LivingEntityPatch<?> targetentitypatch = (LivingEntityPatch<?>) target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

				if (targetentitypatch != null) {
					pummelDownEntity(entitypatch, 2);
				}

				entitypatch.playSound(EgoWeaponsSounds.SWORD_STAB, 1, 1, 1);
			}

			if (anim_id == EgoWeaponsAnimations.KALI_AUTO_4.getId()) {
				entitypatch.setStamina(Math.min(entitypatch.getStamina() + 1f, entitypatch.getMaxStamina()));
				sourceentity.heal(1);
			}

			if (anim_id == EgoWeaponsAnimations.GREAT_SPLIT_HORIZONTAL.getId()) {
				if (sourceentity instanceof PlayerEntity) {
					if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
						sourceentity.heal(2);
						((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 15);
						entitypatch.playSound(EgoWeaponsSounds.KALI_SPLIT_HORIZONTAL_HIT, 1,1);
						entityData.globalcooldown = 15;
					}
				}

				entitypatch.setStamina(Math.min(entitypatch.getStamina() + 0.8f, entitypatch.getMaxStamina()));
				sourceentity.heal(1);
			}

			if (anim_id == EgoWeaponsAnimations.KALI_IMPACT.getId()) {
				BlipTick.chargeBlips((PlayerEntity) sourceentity, entityData, 1, true);
			}
		} else {
			if (anim_id == EgoWeaponsAnimations.MIMICRY_HELLO.getId()) {
				if (entityData.globalcooldown <= 0) {
					entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_BLUNT, 1,1);
					entityData.globalcooldown = 1;
				}
				LivingEntityPatch<?> patch = (LivingEntityPatch)target.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
				if (patch != null) {
					hitStunEffect(patch, 0f);
				}
			}
			else if (anim_id == EgoWeaponsAnimations.MIMICRY_GOODBYE.getId()) {
				entitypatch.playSound(EgoWeaponsSounds.NOTHING_THERE_SLASH, 1,1);

			}
		}

		if (chestItem.equals(EgoWeaponsItems.MIMICRY_CHESTPLATE.get())){
			if (anim_id == EgoWeaponsAnimations.MIMICRY_HELLO.getId()) {
				if (entityData.globalcooldown == 1) {
					BlipTick.chargeBlips((PlayerEntity) sourceentity, entityData, 1, true);

					sourceentity.heal(4);
					((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
					entityData.globalcooldown = 5;
				}

			}

			if (anim_id == EgoWeaponsAnimations.MIMICRY_AUTO_3.getId()) {
				target.level.playSound(null,target.blockPosition(), EgoWeaponsSounds.KALI_SPLIT_VERTICAL_SLASH, SoundCategory.PLAYERS, 1, 1);
				target.addEffect(new EffectInstance(EgoWeaponsEffects.BLEED.get(), 40, 1));
			}

			if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
				sourceentity.heal(2);
				((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
				entityData.globalcooldown = 5;
			}
		} else {
			if (sourceentity instanceof PlayerEntity) {
				if (!((PlayerEntity) sourceentity).getCooldowns().isOnCooldown(this.getItem()) && entityData.globalcooldown <= 0) {
					sourceentity.heal(1);
					((PlayerEntity) sourceentity).getCooldowns().addCooldown(itemstack.getItem(), (int) 5);
					entityData.globalcooldown = 5;
				}
			}
		}


		entityData.syncPlayerVariables(sourceentity);


		MimicryhitentityProcedure.executeProcedure(Stream
				.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("entity", target),
						new AbstractMap.SimpleEntry<>("sourceentity", sourceentity), new AbstractMap.SimpleEntry<>("itemstack", itemstack))
				.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		return retval;
	}



}
