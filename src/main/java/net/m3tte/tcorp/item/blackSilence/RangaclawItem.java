
package net.m3tte.tcorp.item.blackSilence;

import net.m3tte.tcorp.execFunctions.BlackSilenceEvaluator;
import net.minecraft.entity.LivingEntity;
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

import javax.annotation.Nullable;
import java.util.List;

@TcorpModElements.ModElement.Tag
public class RangaclawItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:ranga_claw")
	public static final Item block = null;

	public RangaclawItem(TcorpModElements instance) {
		super(instance, 192);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
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
				return 2;
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
		}, 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
				super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
				list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));
				list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Apply bleed on hit. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" + 1 Bleed Stack").withStyle(TextFormatting.RED)));
			}

			@Override
			public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
				boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
				BlackSilenceEvaluator.onHitRanga(entity.level, entity, sourceentity);
				return retval;
			}
		}.setRegistryName("ranga_claw"));
	}
}
