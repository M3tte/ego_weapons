
package net.m3tte.tcorp.item;

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

import java.util.List;

@TcorpModElements.ModElement.Tag
public class CrimsonfanItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:crimsonfan")
	public static final Item block = null;

	public CrimsonfanItem(TcorpModElements instance) {
		super(instance, 108);
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
		}, 3, -2.4f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("The fans blade emit beautiful petals when swung..."));
				list.add(new StringTextComponent("emitting a sharp blade of air."));
				list.add(new StringTextComponent("[Ability] Hurricane Blade - 4E"));
			}
		}.setRegistryName("crimsonfan"));
	}
}
