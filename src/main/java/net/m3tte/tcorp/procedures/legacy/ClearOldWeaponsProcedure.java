package net.m3tte.tcorp.procedures.legacy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.concurrent.atomic.AtomicReference;

public class ClearOldWeaponsProcedure {

	public static void executeProcedure(PlayerEntity entity) {
		AtomicReference<IItemHandler> _iitemhandlerref = new AtomicReference<>();
		entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> _iitemhandlerref.set(capability));
		if (_iitemhandlerref.get() != null) {
			for (int _idx = 0; _idx < _iitemhandlerref.get().getSlots(); _idx++) {
				ItemStack itemstackiterator = _iitemhandlerref.get().getStackInSlot(_idx);
				if (ItemTags.getAllTags().getTag(new ResourceLocation("tcorp:blacksilenceweapons"))
						.contains(itemstackiterator.getItem())) {
                    ItemStack _stktoremove = itemstackiterator;
                    entity.inventory.removeItem(_stktoremove);
                }
			}
		}

        entity.closeContainer();
	}
}
