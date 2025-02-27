
package net.m3tte.ego_weapons.item.blackSilence.weapons;

import net.m3tte.ego_weapons.EgoWeaponsEntities;
import net.m3tte.ego_weapons.EgoWeaponsModVars;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.entities.AtelierPistolsBullet;
import net.m3tte.ego_weapons.execFunctions.BlackSilenceEvaluator;
import net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations;
import net.m3tte.ego_weapons.gameasset.movesets.AtelierLogicMovesetAnims;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.BlockState;

import net.m3tte.ego_weapons.procedures.legacy.AtelierLogicPistolUseProcedure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

//atelier_logic_pistols
public class AtelierLogicRevolver extends SwordItem {


	public AtelierLogicRevolver(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
		super(pistolTier, p_i48460_2_, p_i48460_3_, p_i48460_4_);
	}

	/*@Override
	public void initElements() {
		elements.items.add(() -> new SwordItem(, 3, -3f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {

		}.setRegistryName("atelier_logic_pistols"));
	}*/

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		BlackSilenceEvaluator.onHitAtelierPistol(entity.level, entity, sourceentity);
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


	public static StaticAnimation.Event[] firstRevolverFire(float time, String limb) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			int ammoCount = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");
			if (ammoCount != 2) {
				entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_AUTO_2a, 0.02f);

			}
		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, limb, false);

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo") - 1);
			BlackSilenceEvaluator.addToStatistic(entity, 6, "furiosohits");

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Zelkova Workshop").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 8) + 1) + "/8] - - - - - - - =").withStyle(TextFormatting.GRAY));

		switch (EgoWeaponsKeybinds.getUiPage() % 8) {
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
					generateDescription(list,"atelier_logic_pistols", "auto", 1);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "ammo"});
				else
					generateDescription(list,"atelier_logic_pistols", "auto2", 3);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "ammo"});
				else
					generateDescription(list,"atelier_logic_pistols", "dash", 2);
				break;
			case 4:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "ammo"});
				else
					generateDescription(list,"atelier_logic_pistols", "innate", 3);
				break;
			case 5:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "ammo"});
				else
					generateDescription(list,"atelier_logic_pistols", "innate2", 3);
				break;
			case 6:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "ammo"});
				else
					generateDescription(list,"atelier_logic_pistols", "guard", 2);
				break;
			case 7:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"black", "ammo"});
				else
					generateDescription(list,"atelier_logic_pistols", "guard2", 3);
				break;
		}

		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}
	public static StaticAnimation.Event[] revolverFire(float time, String limb) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, limb, false);

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo") - 1);
			BlackSilenceEvaluator.addToStatistic(entity, 6, "furiosohits");
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] dualRevolverFire(float time1, String limb1, float time2, String limb2, float reloadTime) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[3];
		events[0] = StaticAnimation.Event.create(time1, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, limb1, false);

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo") - 1);

		}, StaticAnimation.Event.Side.BOTH);

		events[1] = StaticAnimation.Event.create(time2, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-0.1,-0.4), 1, EpicFightParticles.HIT_BLUNT.get(), 0, limb2, false);

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo") - 1);

		}, StaticAnimation.Event.Side.BOTH);


		events[2] = StaticAnimation.Event.create(reloadTime, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			World world = entity.level;

			EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
			int ammo = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");

			if (entityData != null) {
				if (entityData.blips >= (2)) {
					if (ammo < 2) {
						entityData.blips -= 2;
						entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", 2);

						if (!world.isClientSide()) {
							world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
									EgoWeaponsSounds.CLICK,
									SoundCategory.NEUTRAL, 1f, (float) 0.8);

							world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
									EgoWeaponsSounds.PAPER_FLIP,
									SoundCategory.NEUTRAL, 0.5f, (float) 1.1);
						}
						entityData.syncPlayerVariables(entity);
					}
				}
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}



	public static StaticAnimation.Event[] revolverJumpEval() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.05f, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();

			int ammoCount = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");
			if (ammoCount == 2) {
				entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_JUMP_ATTACK_B, 0.1f);
			} else if (ammoCount == 1) {
				entitypatch.playAnimationSynchronized(AtelierLogicMovesetAnims.ATELIER_REVOLVER_JUMP_ATTACK_A, 0.1f);
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] revolverStandardReload() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.33f, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			World world = entity.level;

			EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
			int ammo = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");

			if (entityData != null) {
				if (entityData.blips >= (2-ammo+1)) {
					if (ammo < 2) {
						entityData.blips -= 2-ammo+1;
						entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", 2);

						if (!world.isClientSide()) {
							world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
									EgoWeaponsSounds.CLICK,
									SoundCategory.NEUTRAL, 1f, (float) 0.8);

							world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
									EgoWeaponsSounds.PAPER_FLIP,
									SoundCategory.NEUTRAL, 0.5f, (float) 1.1);
						}
						entityData.syncPlayerVariables(entity);
					}
				}
			}


		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] revolverAutoReload() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.33f, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			World world = entity.level;

			EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);
			int ammo = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");

			if (entityData != null) {
				if (entityData.blips >= (1)) {
					if (ammo < 2) {
						entityData.blips -= 1;
						entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", 2);

						if (!world.isClientSide()) {
							world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
									EgoWeaponsSounds.CLICK,
									SoundCategory.NEUTRAL, 1f, (float) 0.8);

							world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
									EgoWeaponsSounds.PAPER_FLIP,
									SoundCategory.NEUTRAL, 0.5f, (float) 1.1);
						}
						entityData.syncPlayerVariables(entity);
					}
				}
			}


		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] revolverParryReload() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.66f, (entitypatch) -> {
			Entity entity = entitypatch.getOriginal();
			World world = entity.level;

			EgoWeaponsModVars.PlayerVariables entityData = entity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

			int ammo = entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("ammo");

			if (entityData != null) {
				if (entityData.blips > 0) {
					if (ammo < 2) {

						entityData.blips -= 1;
					}

					entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("ammo", Math.min(2, ammo+1));
					if (!world.isClientSide()) {
						world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
								EgoWeaponsSounds.CLICK,
								SoundCategory.NEUTRAL, 1f, (float) 0.8);

						world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
								EgoWeaponsSounds.PAPER_FLIP,
								SoundCategory.NEUTRAL, 0.5f, (float) 1.1);
					}
					entityData.syncPlayerVariables(entity);
				}

			}

		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}
}
