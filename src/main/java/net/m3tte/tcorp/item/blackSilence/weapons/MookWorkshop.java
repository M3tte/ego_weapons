
package net.m3tte.tcorp.item.blackSilence.weapons;

import net.m3tte.tcorp.TcorpModElements;
import net.m3tte.tcorp.execFunctions.BlackSilenceEvaluator;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.List;

public class MookWorkshop extends SwordItem {
	public MookWorkshop(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(mookTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}
	//@ObjectHolder("tcorp:mook_workshop")

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
		list.add(new StringTextComponent("Strikes of lightning"));
		list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));
		list.add(new StringTextComponent("[Parry] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Recover Stamina and penalty").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" +0.1 - 1.7 Stamina").withStyle(TextFormatting.GOLD)));
		list.add(new StringTextComponent("[Guard] ").withStyle(TextFormatting.DARK_RED).append(new StringTextComponent("Gain more penalty").withStyle(TextFormatting.RED)));
		list.add(new StringTextComponent("[Sheath] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Recover stamina and energy. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" + 8% Stamina / Hitstack").withStyle(TextFormatting.GOLD)));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitMook(entity.level, entity, sourceentity);
		return retval;
	}
	// elements.items.add(() -> new SwordItem(, 3, -2.2f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier mookTier = new IItemTier() {
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
			return 8;
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

		public Ingredient getRepairMaterial() {
			return Ingredient.EMPTY;
		}
	};
}
