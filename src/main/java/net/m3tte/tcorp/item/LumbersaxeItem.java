
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
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.ITooltipFlag;

import net.m3tte.tcorp.procedures.WroughtaxehitProcedure;
import net.m3tte.tcorp.TcorpModElements;

import java.util.stream.Stream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;

@TcorpModElements.ModElement.Tag
public class LumbersaxeItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:lumbersaxe")
	public static final Item block = null;

	public LumbersaxeItem(TcorpModElements instance) {
		super(instance, 119);
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
				return 4f;
			}

			@Override
			public float getAttackDamageBonus() {
				return 14f;
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
		}, 3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
			@Override
			public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
				super.appendHoverText(itemstack, world, list, flag);
				list.add(new StringTextComponent("The axes mechanical parts hum in a menacing tone..."));
				list.add(new StringTextComponent("[Ability] Wrought Slash - 4E"));
			}

			@Override
			public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
				boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
				double x = entity.getX();
				double y = entity.getY();
				double z = entity.getZ();
				World world = entity.level;

				WroughtaxehitProcedure.executeProcedure(Stream
						.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x),
								new AbstractMap.SimpleEntry<>("y", y), new AbstractMap.SimpleEntry<>("z", z),
								new AbstractMap.SimpleEntry<>("entity", entity), new AbstractMap.SimpleEntry<>("sourceentity", sourceentity))
						.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
				return retval;
			}
		}.setRegistryName("lumbersaxe"));
	}
}
