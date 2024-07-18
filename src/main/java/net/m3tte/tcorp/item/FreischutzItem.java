
package net.m3tte.tcorp.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.item.UseAction;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.BlockState;

import net.m3tte.tcorp.procedures.FreischutzOnPlayerStoppedUsingProcedure;
import net.m3tte.tcorp.TcorpModElements;

import java.util.stream.Stream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;

@TcorpModElements.ModElement.Tag
public class FreischutzItem extends TcorpModElements.ModElement {
	@ObjectHolder("tcorp:freischutz")
	public static final Item block = null;

	public FreischutzItem(TcorpModElements instance) {
		super(instance, 173);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1).rarity(Rarity.COMMON));
			setRegistryName("freischutz");
		}

		@Override
		public UseAction getUseAnimation(ItemStack p_77661_1_)  {
			return UseAction.CROSSBOW;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 40;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(new StringTextComponent("It's true... this bullet can hit anything like you said"));
			list.add(new StringTextComponent("[Active] Magic Bullet"));
		}

		@Override
		public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
			ActionResult<ItemStack> ar = super.use(world, entity, hand);
			ItemStack itemstack = ar.getObject();
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();

			FreischutzOnPlayerStoppedUsingProcedure.executeProcedure(Stream
					.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x), new AbstractMap.SimpleEntry<>("y", y),
							new AbstractMap.SimpleEntry<>("z", z), new AbstractMap.SimpleEntry<>("entity", entity),
							new AbstractMap.SimpleEntry<>("itemstack", itemstack))
					.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
			return ar;
		}
	}
}
