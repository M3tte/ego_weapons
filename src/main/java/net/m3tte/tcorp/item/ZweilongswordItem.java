
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
public class ZweilongswordItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:zweilongsword")
	public static final Item block = null;

	public ZweilongswordItem(TcorpModElements instance) {
		super(instance, 161);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {

			@Override
			public int getUses() {
				return 300;
			}

			@Override
			public float getSpeed() {
				return 4f;
			}

			@Override
			public float getAttackDamageBonus() {
				return 5;
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

			public Ingredient getRepairMaterial() {
				return Ingredient.EMPTY;
			}
		}, 3, -2.7f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("A simple blade used by the zwei elite."));
				list.add(new StringTextComponent("[Ability] Disarm - 4E"));
			}
		}.setRegistryName("zweilongsword"));
	}
}
