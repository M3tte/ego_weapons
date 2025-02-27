
package net.m3tte.ego_weapons.item.oeufi;

import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.ServerAnimator;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class OeufiContractItem extends Item {
	//@ObjectHolder("tcorp:durandalsheath")

	public OeufiContractItem() {
		super(new Properties().tab(null).stacksTo(1).rarity(Rarity.COMMON));
		}


	@Override
	public void inventoryTick(ItemStack stack, World p_77663_2_, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.inventoryTick(stack, p_77663_2_, entity, p_77663_4_, p_77663_5_);

		if (entity instanceof LivingEntity && ((LivingEntity) entity).getItemInHand(Hand.OFF_HAND).getItem().equals(this)) {
			LivingEntity living = (LivingEntity) entity;

			LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) living.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

			if (!(entitypatch.getAnimator() instanceof ServerAnimator))
				return;


			DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();



			if (currentanim == null) {
				living.setItemInHand(Hand.OFF_HAND, Items.AIR.getDefaultInstance());
			}

			if (currentanim.getId() != OeufiAssocMovesetAnims.OEUFI_OPEN_CONTRACT.getId()) {
				living.setItemInHand(Hand.OFF_HAND, Items.AIR.getDefaultInstance());

			}
		}
	}
}
