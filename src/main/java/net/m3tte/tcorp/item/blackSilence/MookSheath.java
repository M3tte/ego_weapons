
package net.m3tte.tcorp.item.blackSilence;

import net.m3tte.tcorp.TcorpModElements;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraftforge.registries.ObjectHolder;

@TcorpModElements.ModElement.Tag
public class MookSheath extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:mook_sheath")
	public static final Item block = null;

	public MookSheath(TcorpModElements instance) {
		super(instance, 190);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Properties().tab(null).stacksTo(1).rarity(Rarity.COMMON));
			setRegistryName("mook_sheath");
		}


		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
