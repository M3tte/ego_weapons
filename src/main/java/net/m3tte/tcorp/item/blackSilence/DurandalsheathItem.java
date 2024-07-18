
package net.m3tte.tcorp.item.blackSilence;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.m3tte.tcorp.TcorpModElements;

@TcorpModElements.ModElement.Tag
public class DurandalsheathItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:durandalsheath")
	public static final Item block = null;

	public DurandalsheathItem(TcorpModElements instance) {
		super(instance, 190);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Properties().tab(null).stacksTo(1).rarity(Rarity.COMMON));
			setRegistryName("durandalsheath");
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
