
package net.m3tte.ego_weapons.item.blackSilence.weapons;

import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResult;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.IItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.BlockState;

import net.m3tte.ego_weapons.procedures.legacy.AtelierlogicshotgunuseProcedure;

import javax.annotation.Nullable;
import java.util.stream.Stream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

// atelier_logic_shotgun
public class AtelierlogicshotgunItem extends EgoWeaponsWeapon {


	public AtelierlogicshotgunItem(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(shotgunTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Atelier Logic").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 3) + 1) + "/3] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 3) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{});
				else
					generateDescription(list, "blacksilenceweapon", "ability", 1);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black"});
				else
					generateDescription(list,"atelier_logic_shotgun", "auto", 1);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black"});
				else
					generateDescription(list,"atelier_logic_shotgun", "auto2", 3);
				break;
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
		ActionResult<ItemStack> retval = super.use(world, entity, hand);
		ItemStack itemstack = retval.getObject();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();

		AtelierlogicshotgunuseProcedure.executeProcedure(Stream
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

		AtelierlogicshotgunuseProcedure.executeProcedure(Stream
				.of(new AbstractMap.SimpleEntry<>("world", world), new AbstractMap.SimpleEntry<>("x", x),
						new AbstractMap.SimpleEntry<>("y", y), new AbstractMap.SimpleEntry<>("z", z),
						new AbstractMap.SimpleEntry<>("entity", entity))
				.collect(HashMap::new, (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		return retval;
	}

		//elements.items.add(() -> new SwordItem(, 3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier shotgunTier = new IItemTier() {
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
			return 1f;
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
