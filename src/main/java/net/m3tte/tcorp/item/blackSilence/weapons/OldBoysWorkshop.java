
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

public class OldBoysWorkshop extends SwordItem {
	public OldBoysWorkshop(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(oldBoysTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}
	//@ObjectHolder("tcorp:old_boys_workshop")


	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
		list.add(new StringTextComponent("A hammer."));
		list.add(new StringTextComponent("Light and compatible for followup attacks."));
		list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));
		list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Regain stamina on hit. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" + 30% Stamina").withStyle(TextFormatting.GOLD)));
		list.add(new StringTextComponent("[Parry] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Drain attacker stamina").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" -2 Stamina").withStyle(TextFormatting.RED)));
		list.add(new StringTextComponent("[Guard] ").withStyle(TextFormatting.DARK_RED).append(new StringTextComponent("Ineffective").withStyle(TextFormatting.RED)));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitOldBoys(entity.level, entity, sourceentity);
		return retval;
	}

	// elements.items.add(() -> new PickaxeItem(, 1, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier oldBoysTier = new IItemTier() {
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

	};
}
