
package net.m3tte.ego_weapons.world.capabilities.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// mook_sheath
public class DisabledItem extends Item {


	public DisabledItem() {
		super(new Properties().tab(null).stacksTo(1).rarity(Rarity.COMMON));

	}

	@Override
	public void inventoryTick(ItemStack item, World p_77663_2_, Entity ent, int p_77663_4_, boolean p_77663_5_) {
		super.inventoryTick(item, p_77663_2_, ent, p_77663_4_, p_77663_5_);
		item.shrink(99);
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> text, ITooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, text, p_77624_4_);

		text.add(new StringTextComponent("THIS ITEM IS CURRENTLY NOT IMPLEMENTED").withStyle(TextFormatting.RED));
	}
}
