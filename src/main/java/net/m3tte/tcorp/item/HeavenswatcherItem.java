
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
public class HeavenswatcherItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:heavens_watcher")
	public static final Item block = null;

	public HeavenswatcherItem(TcorpModElements instance) {
		super(instance, 13);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(new IItemTier() {
			@Override
			public int getUses() {
				return 1000;
			}

			@Override
			public float getSpeed() {
				return 4f;
			}

			@Override
			public float getAttackDamageBonus() {
				return 13f;
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

		}, 3, -2.8f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("And so... its roots pierced yet another oblivious soul..."));
				list.add(new StringTextComponent("May entrap enemies in a root like block"));
				list.add(new StringTextComponent("[Ability] Putrid Root - 3E"));
			}
		}.setRegistryName("heavens_watcher"));
	}
}
