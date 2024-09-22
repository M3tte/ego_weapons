
package net.m3tte.tcorp.item.blackSilence.weapons;

import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResult;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.BlockState;

import net.m3tte.tcorp.procedures.legacy.AtelierLogicPistolUseProcedure;
import net.m3tte.tcorp.TcorpModElements;

import javax.annotation.Nullable;
import java.util.stream.Stream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;
//atelier_logic_pistols
public class AtelierlogicpistolsItem extends SwordItem {


	public AtelierlogicpistolsItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(pistolTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	/*@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(, 3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

		}.setRegistryName("atelier_logic_pistols"));
	}*/

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
		ActionResult<ItemStack> retval = super.use(world, entity, hand);
		ItemStack itemstack = retval.getObject();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();

		AtelierLogicPistolUseProcedure.executeProcedure(Stream
				.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x),
						new AbstractMap.SimpleEntry<>("y", y), new AbstractMap.SimpleEntry<>("z", z),
						new AbstractMap.SimpleEntry<>("entity", entity))
				.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		return retval;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		ActionResultType retval = super.useOn(context);
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		PlayerEntity entity = context.getPlayer();
		Direction direction = context.getClickedFace();
		BlockState blockstate = world.getBlockState(pos);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		ItemStack itemstack = context.getItemInHand();

		AtelierLogicPistolUseProcedure.executeProcedure(Stream
				.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x),
						new AbstractMap.SimpleEntry<>("y", y), new AbstractMap.SimpleEntry<>("z", z),
						new AbstractMap.SimpleEntry<>("entity", entity))
				.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		return retval;
	}

	static IItemTier pistolTier = new IItemTier() {
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
			return 2f;
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

	};
}
