
package net.m3tte.ego_weapons.item.solemn_lament;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsParticles;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.potion.EthernalRestPotionEffect;
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
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;


public class SolemnLament extends SwordItem {
	public static final Item item = null;

	public SolemnLament(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, boolean isDeparted) {
		super(solemnLamentTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);

	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
		super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
		list.add(new StringTextComponent("[Ability] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Reload. ").withStyle(TextFormatting.GRAY)).append(new StringTextComponent(" 4E").withStyle(TextFormatting.AQUA)));
		list.add(new StringTextComponent("[Passive] ").withStyle(TextFormatting.GREEN).append(new StringTextComponent("Store the LIVING and the DEPARTED").withStyle(TextFormatting.GRAY)));
	}

	private static List<Integer> departedAnimations;
	private static List<Integer> livingAnimations;


	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);

		if (livingAnimations == null) {
			livingAnimations = Arrays.stream(new StaticAnimation[]{EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D0, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D3, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D5, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R0, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R1, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_R2, EgoWeaponsAnimations.SOLEMN_LAMENT_DASH_R, EgoWeaponsAnimations.SOLEMN_LAMENT_GUARD_COUNTERATTACK, EgoWeaponsAnimations.SOLEMN_LAMENT_SPECIAL_ATTACK}).map(StaticAnimation::getId).collect(Collectors.toList());
			departedAnimations = Arrays.stream(new StaticAnimation[] {EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D1, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_D4, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L0, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L1, EgoWeaponsAnimations.SOLEMN_LAMENT_AUTO_L2, EgoWeaponsAnimations.SOLEMN_LAMENT_DASH_L, EgoWeaponsAnimations.SOLEMN_LAMENT_GUARD_COUNTERATTACK, EgoWeaponsAnimations.SOLEMN_LAMENT_SPECIAL_ATTACK}).map(StaticAnimation::getId).collect(Collectors.toList());
		}

		EgoWeaponsModVars.PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		PlayerPatch<?> entitypatch = (PlayerPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim != null) {
			if (livingAnimations.contains(currentanim.getId())) {
				StaggerSystem.reduceStagger(entity, 4, sourceentity, false);
				EgoWeaponsEffects.SINKING.get().increment(entity, sourceentity.hasEffect(EthernalRestPotionEffect.get()) ? 1 : 0, 1);
			}
			if (departedAnimations.contains(currentanim.getId())) {
				entity.hurt(DamageSource.OUT_OF_WORLD, 1);
			}

			if (currentanim.getId() == EgoWeaponsAnimations.SOLEMN_LAMENT_SPECIAL_ATTACK.getId()) {


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

				EgoWeaponsEffects.SINKING.get().increment(entity, (int) (departedCount/2f), (int) (livingCount/2f));

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
