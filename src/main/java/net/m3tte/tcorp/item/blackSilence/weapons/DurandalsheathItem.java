
package net.m3tte.tcorp.item.blackSilence.weapons;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.m3tte.tcorp.TcorpModElements;

public class DurandalsheathItem extends Item {
	//@ObjectHolder("tcorp:durandalsheath")

	public DurandalsheathItem() {
		super(new Properties().tab(null).stacksTo(1).rarity(Rarity.COMMON));
	}
}
