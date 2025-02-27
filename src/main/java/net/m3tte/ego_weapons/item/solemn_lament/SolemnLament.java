
package net.m3tte.ego_weapons.item.solemn_lament;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.potion.EternalRestPotionEffect;
import net.m3tte.ego_weapons.potion.SolemnLamentEffects;
import net.m3tte.ego_weapons.world.capabilities.SanitySystem;
import net.m3tte.ego_weapons.world.capabilities.StaggerSystem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;


public class SolemnLament extends SwordItem {
	public static final Item item = null;

	public SolemnLament(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, boolean isDeparted) {
		super(solemnLamentTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);

	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("It is merely that butterflies, both living and dead, bloom from where the gun points.").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));

		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking"});
				else
					generateDescription(list,"solemn_lament", "auto", 8);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking"});
				else
					generateDescription(list,"solemn_lament", "innate", 5);

				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking"});
				else
					generateDescription(list, "solemn_lament", "ability", 1);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"living_departed", "the_living", "the_departed", "sinking"});
				else
					generateDescription(list,"solemn_lament", "guard", 5);
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));



	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();


		if (currentanim != null) {

			String animTag = currentanim.getRealAnimation().getProperty(EgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			switch (animTag) {
				case "solemn_lament_dual":
					StaggerSystem.reduceStagger(entity, 4, sourceentity, false);
					EgoWeaponsEffects.SINKING.get().increment(entity, sourceentity.hasEffect(EternalRestPotionEffect.get()) ? 1 : 0, 1);
					entity.hurt(DamageSource.OUT_OF_WORLD, 1);
					if (sourceentity.hasEffect(EternalRestPotionEffect.get())) {
						EgoWeaponsEffects.SINKING.get().increment(entity, 1, 0);
					}
					break;
				case "solemn_lament_living":
					StaggerSystem.reduceStagger(entity, 1, sourceentity, false);
					EgoWeaponsEffects.SINKING.get().increment(entity, sourceentity.hasEffect(EternalRestPotionEffect.get()) ? 1 : 0, 1);
					break;
				case "solemn_lament_departed":
					entity.hurt(DamageSource.OUT_OF_WORLD, 1);
					if (sourceentity.hasEffect(EternalRestPotionEffect.get())) {
						EgoWeaponsEffects.SINKING.get().increment(entity, 1, 0);
					}
					break;

				case "solemn_lament_special":
					if (!sourceentity.getPersistentData().contains("solemnLamentChargeHit")) {
						sourceentity.getPersistentData().putBoolean("solemnLamentChargeHit", true);
						entity.level.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
								EgoWeaponsSounds.SOLEMN_LAMENT_SPECIAL_IMPACT,
								SoundCategory.PLAYERS, 1, 1.4f);

						if (entity.level instanceof ServerWorld) {
							((ServerWorld) entity.level).sendParticles(EgoWeaponsParticles.SOLEMN_LAMENT_BURST_HIT.get(), entity.getX(), entity.getY(), entity.getZ(), (int) 1, 0, 0, 0, 0);
						}
					}
					int livingCount = SolemnLamentEffects.getAmmoCount(sourceentity, SolemnLamentEffects.getLiving());
					int departedCount = SolemnLamentEffects.getAmmoCount(sourceentity, SolemnLamentEffects.getDeparted());

					EgoWeaponsEffects.SINKING.get().increment(entity, (int) (departedCount/1.5f), (int) (livingCount/1.5f));

					if (livingCount > 0) {
						StaggerSystem.reduceStagger(entity, 2f * livingCount, sourceentity, false);
						if (sourceentity.getEffect(SolemnLamentEffects.getLiving()).getDuration() > 3) {
							sourceentity.removeEffect(SolemnLamentEffects.getLiving());
							sourceentity.addEffect(new EffectInstance(SolemnLamentEffects.getLiving(), 3, livingCount-1));
						}



						if (entity instanceof PlayerEntity) {
							SanitySystem.damageSanity((PlayerEntity) entity, 1f * livingCount);
						}
					}

					if (departedCount > 0) {
						entity.hurt(DamageSource.OUT_OF_WORLD, 2f * departedCount);
						if (sourceentity.getEffect(SolemnLamentEffects.getDeparted()).getDuration() > 3) {
							sourceentity.removeEffect(SolemnLamentEffects.getDeparted());
							sourceentity.addEffect(new EffectInstance(SolemnLamentEffects.getDeparted(), 3, departedCount - 1));
						}
					}

					if (!sourceentity.getPersistentData().contains("solemnLamentChargeStaggered") && (StaggerSystem.isStaggered(entity) || !entity.isAlive())) {
						sourceentity.getPersistentData().putBoolean("solemnLamentChargeStaggered", true);
						entity.level.playSound((PlayerEntity) null, entity.getX(), entity.getY(), entity.getZ(),
								EgoWeaponsSounds.SOLEMN_LAMENT_SPECIAL_RELOAD,
								SoundCategory.PLAYERS, 1, 1.4f);
					}

					break;
			}


		}


		return retval;
	}

	// elements.items.add(() -> new SwordItem(, 3, -2.3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

	static IItemTier solemnLamentTier = new IItemTier() {
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
			return 2;
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
