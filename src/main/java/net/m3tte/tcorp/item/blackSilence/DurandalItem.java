
package net.m3tte.tcorp.item.blackSilence;

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

@TcorpModElements.ModElement.Tag
public class DurandalItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:durandal")
	public static final Item block = null;

	public DurandalItem(TcorpModElements instance) {
		super(instance, 77);
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
		}, 3, -2.9f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
				super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
				list.add(new StringTextComponent("Some say it is a holy sword..."));
				list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Swap to next weapon. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 1 E").withStyle(TextFormatting.AQUA)));
			}

			@Override
			public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
				boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
				BlackSilenceEvaluator.onHitDurandal(entity.level, entity, sourceentity);
				return retval;
			}
		}.setRegistryName("durandal"));
	}
}
