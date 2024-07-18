package net.m3tte.tcorp.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class NoArmorToughnessMaterial {
    public static IArmorMaterial notoughness = new IArmorMaterial() {

        @Override
        public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlotType slot)  {
            return new int[]{0, 0, 22, 0}[slot.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }



        @Override
        public SoundEvent getEquipSound() {
            return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }


        @OnlyIn(Dist.CLIENT)
        @Override
        public String getName() {
            return "redmist";
        }

        @Override
        public float getToughness() {
            return 0f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0f;
        }
    };
}
