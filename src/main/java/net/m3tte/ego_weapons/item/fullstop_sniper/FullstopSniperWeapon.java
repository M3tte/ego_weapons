
package net.m3tte.ego_weapons.item.fullstop_sniper;

import net.m3tte.ego_weapons.EgoWeaponsEffects;
import net.m3tte.ego_weapons.EgoWeaponsSounds;
import net.m3tte.ego_weapons.gameasset.BasicEgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation;
import net.m3tte.ego_weapons.gameasset.EgoAttackAnimation.EgoWeaponsAttackProperty;
import net.m3tte.ego_weapons.gameasset.movesets.FullstopOfficeSniperMovesetAnims;
import net.m3tte.ego_weapons.item.guns.GunCaliber;
import net.m3tte.ego_weapons.item.guns.GunItem;
import net.m3tte.ego_weapons.keybind.EgoWeaponsKeybinds;
import net.m3tte.ego_weapons.procedures.EntityTick;
import net.m3tte.ego_weapons.procedures.SharedFunctions;
import net.m3tte.ego_weapons.world.capabilities.AmmoSystem;
import net.m3tte.ego_weapons.world.capabilities.AmmoType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Arrays;
import java.util.List;

import static net.m3tte.ego_weapons.EgoWeaponsModVars.PLAYER_VARIABLES_CAPABILITY;
import static net.m3tte.ego_weapons.EgoWeaponsModVars.PlayerVariables;
import static net.m3tte.ego_weapons.gameasset.EgoWeaponsAnimations.spawnArmatureParticle;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateDescription;
import static net.m3tte.ego_weapons.procedures.TooltipFuncs.generateStatusDescription;

public class FullstopSniperWeapon extends GunItem {

	private static IItemTier fullstopSniperTier = new IItemTier() {

		@Override
		public int getUses() {
			return 0;
		}

		@Override
		public float getSpeed() {
			return 4.0f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 5;
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

	public FullstopSniperWeapon(int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, GunCaliber caliber, int capacity) {
		super(fullstopSniperTier, p_i48460_2_, p_i48460_3_, p_i48460_4_, capacity, caliber);
	}




	@Override
	public void appendHoverText(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(new StringTextComponent("Manufactured by Atelier Logic").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
		list.add(new StringTextComponent(" ").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));

		list.add(new StringTextComponent("= - - - - - - - [Page: "+ ((EgoWeaponsKeybinds.getUiPage() % 4) + 1) + "/4] - - - - - - - =").withStyle(TextFormatting.GRAY));
		list.add(new TranslationTextComponent("desc.ego_weapons.risk.3"));
		list.add(new StringTextComponent(" "));
		switch (EgoWeaponsKeybinds.getUiPage() % 4) {
			case 0:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "ammo", "target_marked"});
				else
					generateDescription(list, "fullstop_sniper", "passive", 6);
				break;
			case 1:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "ammo"});
				else
					generateDescription(list,"fullstop_sniper", "auto", 6);
				break;
			case 2:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "ammo"});
				else
					generateDescription(list,"fullstop_sniper", "innate", 4);
				break;
			case 3:
				if (EgoWeaponsKeybinds.isHoldingShift())
					generateStatusDescription(list, new String[]{"poise", "ammo", "protection", "resilience"});
				else {
					generateDescription(list,"fullstop_sniper", "ability", 8);
				}
				break;
			}
		list.add(new StringTextComponent("= - - - - - - - - - - - - - - - - - - - - =").withStyle(TextFormatting.GRAY));
	}
	public static void interruptedAttack(LivingEntity target) {

	}


	@Override
	public String getDefaultKillIdentifier() {
		return "gun";
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity target, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, target, sourceentity);
		double x = target.getX();
		double y = target.getY();
		double z = target.getZ();


		World world = target.level;

		Item chestItem = sourceentity.getItemBySlot(EquipmentSlotType.CHEST).getItem();

		PlayerVariables entityData = sourceentity.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(null);

		LivingEntityPatch<?> entitypatch = (LivingEntityPatch<?>) sourceentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);

		DynamicAnimation currentanim = entitypatch.getServerAnimator().animationPlayer.getAnimation();

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");

			boolean consumesAmmo = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);

			boolean finale = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.LAST_OF_COMBO).orElse(false);





			if (consumesAmmo) {


				AmmoType lastFired = AmmoType.values()[sourceentity.getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("lastFired")];



				//itemstack.getOrCreateTag().remove("lastFired");
				System.out.println("AMMO USED IS: "+ lastFired);



				if (sourceentity.level instanceof ServerWorld) {
					((ServerWorld) sourceentity.level).sendParticles(lastFired.getHitParticle(), target.getX(), target.getY() + target.getBbHeight()/2, target.getZ(), (int) 1, 0, 0, 0, 0);
					switch (lastFired) {
						case Standard:
							break;
					}

				}
			}

		}

		return true;
	}


	public static float critDamageCalculations(LivingEntity target, LivingEntityPatch<?> sourcePatch, float amount, DamageSource source) {

		DynamicAnimation currentanim = sourcePatch.getServerAnimator().animationPlayer.getAnimation();

		AmmoType lastFired = AmmoType.values()[sourcePatch.getOriginal().getItemInHand(Hand.MAIN_HAND).getOrCreateTag().getInt("lastFired")];
		//itemstack.getOrCreateTag().remove("lastFired");
		System.out.println("AMMO USED IS: "+ lastFired);

		if (currentanim.getRealAnimation() instanceof BasicEgoAttackAnimation || currentanim.getRealAnimation() instanceof EgoAttackAnimation) {
			//System.out.println("IS BASIC EGO ATTACK ANIM" + (currentanim.getRealAnimation()).getProperty(BasicEgoAttackAnimation.EgoWeaponsAttackProperty.IDENTIFIER));

			boolean consumesAmmo = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.CONSUMES_AMMO).orElse(false);
			//boolean finale;
			String weaponIdentifier = (currentanim.getRealAnimation()).getProperty(EgoWeaponsAttackProperty.IDENTIFIER).orElse("");



			if (consumesAmmo && sourcePatch.getOriginal().getItemInHand(Hand.MAIN_HAND).getOrCreateTag().contains("lastFired")) {
				if (lastFired.equals(AmmoType.ALHVRifle)) {

					target.setHealth(target.getHealth() - 2);
					amount += 0.3f;
					SharedFunctions.incrementBonusDamage(source, 0.5f);
					int poisePotencyOver20 = Math.min(EgoWeaponsEffects.POISE.get().getPotency(sourcePatch.getOriginal()) - 20, 15);
					if (poisePotencyOver20 > 0) {
						amount += (0.02f * poisePotencyOver20);
						SharedFunctions.incrementBonusDamage(source, (0.02f * poisePotencyOver20));
					}
				}
			}


			switch (weaponIdentifier) {
				case "fs_sn_innate":
						amount += 0.4f;
						SharedFunctions.incrementBonusDamage(source, 0.4f);
						if (target.hasEffect(EgoWeaponsEffects.TARGET_SPOTTED.get()) && sourcePatch.getOriginal() instanceof PlayerEntity) {
							EntityTick.regenerateLight((PlayerEntity) sourcePatch.getOriginal(), 1);
						}
					break;
				case "fs_sn_special":
						amount += 0.5f;
						SharedFunctions.incrementBonusDamage(source, 0.5f);
						int poisePotencyOver20 = Math.min(EgoWeaponsEffects.POISE.get().getPotency(sourcePatch.getOriginal()) - 20, 25);
						if (poisePotencyOver20 > 0) {
							amount += (0.03f * poisePotencyOver20);
							SharedFunctions.incrementBonusDamage(source, (0.03f * poisePotencyOver20));
							EgoWeaponsEffects.POISE.get().decrement(sourcePatch.getOriginal(), 0, poisePotencyOver20);
						}
					break;
			}
		}
		return amount;
	}

	@Override
	public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {

		int charge = stack.getOrCreateTag().getInt("charge");
		if (charge > 0 && charge < 30) {
			stack.getOrCreateTag().putInt("charge", stack.getOrCreateTag().getInt("charge") + 1);
		} else if (charge >= 30)
			stack.getOrCreateTag().remove("charge");

		int dropped = stack.getOrCreateTag().getInt("dropped");
		if (dropped > 0 && dropped < 90) {
			stack.getOrCreateTag().putInt("dropped", stack.getOrCreateTag().getInt("dropped") + 1);
		} else if (dropped >= 90)
			stack.getOrCreateTag().remove("dropped");


		super.inventoryTick(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
	}

	public static StaticAnimation.Event[] fireFSRailgunSpecial(float chargeTime, float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[7];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_START,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.6f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("dropped", 1);
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FLIP,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(1.25f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_START,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[3] = StaticAnimation.Event.create(1.55f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			int[] retAmmo = AmmoSystem.loadLimitedFromRight(entity.getItemInHand(Hand.MAIN_HAND), null, entity, 1);

			System.out.println("RETAMMO RESULT: "+ Arrays.toString(retAmmo));

			if (!world.isClientSide() && retAmmo[1] > 0)
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_RELOAD,
						SoundCategory.PLAYERS, 1f, (float) 1);

			if (retAmmo[0] == 0)
				entitypatch.playAnimationSynchronized(FullstopOfficeSniperMovesetAnims.FULLSTOP_SNIPER_IDLE, 0.01f);
		}, StaticAnimation.Event.Side.BOTH);
		events[4] = StaticAnimation.Event.create(chargeTime, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 1);
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_SPECIAL_CHARGE,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[5] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.PLAYERS, 0.5f, (float) 0.8);
			}

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 0);
			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

			if (ammo != null) {
				if (!ammo.hasEffect()) {
					EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
				}

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

			}
		}, StaticAnimation.Event.Side.BOTH);
		events[6] = StaticAnimation.Event.create(time + 0.3f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_AFTER,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}

	public static StaticAnimation.Event[] fireFSRailgun(float time, boolean finalB) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.NEUTRAL, 0.5f, (float) 0.8);
			}

			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

			if (ammo != null) {

				int finalAdd = finalB ? 1 : 0;

				EgoWeaponsEffects.POISE.get().increment(entity, 1, ammo.hasEffect() ? finalAdd : 1 + finalAdd);

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
			}



			}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	public static StaticAnimation.Event[] fireFSRailgunInnate(float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[5];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;



			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_START,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[1] = StaticAnimation.Event.create(0.3f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			EgoWeaponsEffects.POISE.get().increment(entitypatch.getOriginal(), 0, 2);

			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_FLIP,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[2] = StaticAnimation.Event.create(0.5f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 1);
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_CHARGE,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		events[3] = StaticAnimation.Event.create(time, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;

			if (!world.isClientSide()) {
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.CLICK,
						SoundCategory.PLAYERS, 0.5f, (float) 0.8);
			}

			entitypatch.getValidItemInHand(Hand.MAIN_HAND).getOrCreateTag().putInt("charge", 0);
			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);
			if (ammo != null) {
				if (!ammo.hasEffect()) {
					EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
				}

				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getShockwaveParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");

			}
		}, StaticAnimation.Event.Side.BOTH);
		events[4] = StaticAnimation.Event.create(time + 0.3f, (entitypatch) -> {
			LivingEntity entity = entitypatch.getOriginal();
			World world = entity.level;
			if (!world.isClientSide())
				world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
						EgoWeaponsSounds.FULLSTOP_SNIPER_INNATE_AFTER,
						SoundCategory.PLAYERS, 1f, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}






	public static StaticAnimation.Event[] fireFSRailgunFirst(float time) {
		StaticAnimation.Event[] events = new StaticAnimation.Event[2];
		events[0] = StaticAnimation.Event.create(0, (entitypatch) -> {
					LivingEntity entity = entitypatch.getOriginal();
					World world = entity.level;
					if (!world.isClientSide()) {
						world.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()),
								EgoWeaponsSounds.FULLSTOP_SNIPER_AUTO_START,
								SoundCategory.NEUTRAL, 1f, (float) 1);
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

			AmmoType ammo = AmmoSystem.getAndRemovelastammo(entity.getItemInHand(Hand.MAIN_HAND), entity, true);

			if (ammo != null) {

				if (!ammo.hasEffect()) {
					EgoWeaponsEffects.POISE.get().increment(entity, 0, 1);
				}


				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireParticle(), 0, "Tool_R", false);
				spawnArmatureParticle(entitypatch, 0, new Vector3d(0,-1.3,-0.15), 1, ammo.getFireSideParticle(), new Vector3f(0, entity.getId(), entity.getId()), "Tool_R");
			}
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


	public static StaticAnimation.Event[] reloadEvent() {
		StaticAnimation.Event[] events = new StaticAnimation.Event[1];
		events[0] = StaticAnimation.Event.create(0.8f, (entitypatch) -> {
			World world = entitypatch.getOriginal().level;
			world.playSound(null, entitypatch.getOriginal().blockPosition(),
                    EgoWeaponsSounds.FULLSTOP_REP_RELOAD,
					SoundCategory.PLAYERS, (float) 2, (float) 1);
		}, StaticAnimation.Event.Side.BOTH);
		return events;
	}


}
