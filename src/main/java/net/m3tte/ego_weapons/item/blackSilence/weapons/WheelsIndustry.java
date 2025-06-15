
package net.m3tte.ego_weapons.item.blackSilence.weapons;

import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.BlackSilenceMovesetAnims;
import net.m3tte.ego_weapons.item.EgoWeaponsWeapon;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.countEffects.TremorEffect;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class WheelsIndustry extends EgoWeaponsWeapon {
	public WheelsIndustry(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(wheelsTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}
	//@ObjectHolder("tcorp:wheels_industry")

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Wheels Industry").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.aleph"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{});
				else
					generateDescription(list, "blacksilenceweapon", "ability", 1);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor"});
				else
					generateDescription(list,"wheels_industry", "auto", 4);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor"});
				else
					generateDescription(list,"wheels_industry", "innate", 3);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "tremor", "tremor_burst"});
				else
					generateDescription(list,"wheels_industry", "guard", 2);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitWheels(entity.level, entity, sourceentity);

		TremorEffect.incrementTremor(entity, 0, 1);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		final int anim_id = currentanim.getId();

		if (anim_id == BlackSilenceMovesetAnims.WHEELS_AUTO_3.getId() || anim_id == BlackSilenceMovesetAnims.WHEELS_AUTO_2.getId()) {
			TremorEffect.incrementTremor(entity, 1, 1);
		} else {
			TremorEffect.incrementTremor(entity, 0, 1);
		}

		return retval;
	}
// elements.items.add(() -> new SwordItem(, 1, -3.1f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier wheelsTier = new IItemTier() {
		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4;
		}

		@Override
		public float getAttackDamageBonus() {
			return 10;
		}

		@Override
		public int getLevel() {
			return 1;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.EMPTY;
		}
	};
}
