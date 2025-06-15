
package net.m3tte.ego_weapons.item.blackSilence.weapons;

import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.client.util.ITooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class ZelkovamaceItem extends EgoWeaponsWeapon {
	//@ObjectHolder("tcorp:zelkova_mace")

	public ZelkovamaceItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(zelkovaMaceTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Zelkova Workshop").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{});
				else
					generateDescription(list, "blacksilenceweapon", "ability", 1);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor"});
				else
					generateDescription(list,"zelkova", "auto", 2);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor", "tremor_burst", "tremor_decay"});
				else
					generateDescription(list,"zelkova", "innate", 4);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor"});
				else
					generateDescription(list,"zelkova", "jump", 3);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitZelkova(entity.level, entity, sourceentity);
		return retval;
	}
	// elements.items.add(() -> new PickaxeItem(, 1, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier zelkovaMaceTier = new IItemTier() {
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
			return 6;
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
