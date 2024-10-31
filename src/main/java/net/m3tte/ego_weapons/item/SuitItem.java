
package net.m3tte.ego_weapons.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class SuitItem extends ArmorItem {

	public SuitItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
		super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
	}


	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "tcorp:textures/entities/suppression_unit_pants.png";
	}

	public static Item getArmorForSlot(EquipmentSlotType slot) {
		switch (slot) {
			default: return null;
			case LEGS: return legs;
		}
	}


	static Item legs = new SuitItem(NoArmorToughnessMaterial.notoughness, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
	};


}
