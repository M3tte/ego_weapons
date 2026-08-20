
package net.m3tte.ego_weapons.item;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.movesets.OeufiAssocMovesetAnims;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.TooltipFuncs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import yesman.epicfight.api.animation.ServerAnimator;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.*;

public class ChesedsLatencyItem extends Item {
	//@ObjectHolder("tcorp:durandalsheath")

	public ChesedsLatencyItem() {
		super(new Properties().tab(null).stacksTo(1).rarity(Rarity.COMMON));
		}


	@Override
	public void inventoryTick(ItemStack stack, World p_77663_2_, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.inventoryTick(stack, p_77663_2_, entity, p_77663_4_, p_77663_5_);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		TooltipFuncs.generateItemDescription(list, "desc.ego_weapons.latency_emblem.desc");
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: 1/1] - - - - - - - =").withStyle(TextFormatting.GRAY));
		if (EgoWeaponsKeybinds.isHoldingShift())
			generateStatusDescription(list, new String[]{"cheseds_latency"});
		else {
			generateDescription(list, "latency_emblem", "ability", 1);
			list.add(new StringTextComponent(" "));
			generateDescription(list, "latency_emblem", "abilityb", 1);
		}

		generateStatusHelp(list);
	}


	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand p_77659_3_) {

		if (entity.isCrouching()) {
			entity.playSound(EgoWeaponsSounds.RESULT_POSITIVE, 1, 0.3f);
			EgoWeaponsEffects.CHESEDS_LATENCY.get().decrement(entity, 0, 1);
		} else {
			entity.playSound(EgoWeaponsSounds.RESULT_POSITIVE, 1, 1);
			EgoWeaponsEffects.CHESEDS_LATENCY.get().increment(entity, 0, 1);
		}

		return ActionResult.success(entity.getItemInHand(p_77659_3_));
	}
}
